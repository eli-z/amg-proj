package acc.edu.amg.smothersalgo.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import acc.edu.amg.data.EquationData;
import acc.edu.amg.data.MatrixData;
import acc.edu.amg.data.convertors.DataConverter;
import acc.edu.amg.exceptions.CalculationException;
import acc.edu.amg.smothersalgo.Application;
import acc.edu.kube.comm.RestTemplateTool;
import acc.edu.kube.comm.responses.SmoothResponse;
import acc.edu.kube.comm.responses.SmoothResponseBulk;
import acc.edu.kube.comm.responses.SmootherAlgoResponse;
import acc.edu.kube.exceptions.RestException;

@RestController
@RequestMapping("/")
public class SmootherAlgo {
	private static Logger logger = LoggerFactory.getLogger(SmootherAlgo.class);
	private static int bulkFactor = 10;
	
	@Autowired
	private RestTemplateTool restTool;
	
	@PostMapping(path="jacobi/{iterations}")
	public ResponseEntity<SmootherAlgoResponse> smoothJ(@PathVariable("iterations") Integer iterations, @RequestParam(name="bulkSize", required=false) Integer bulkSizeOverride, @RequestBody MatrixData matrix) throws CalculationException, InterruptedException, ExecutionException{
		if(matrix.validate())
		{
			SmootherAlgoResponse response = new SmootherAlgoResponse();
			logger.info("Startring Jacobi for " + iterations + " iterations");
			long startTime = System.currentTimeMillis();
			CompletableFuture<SmoothResponseBulk>[] futuresArray;
			SmoothResponseBulk sResp;
			
			int bulkSize = matrix.dimention() / bulkFactor;
			if(bulkSize == 0)
				bulkSize = matrix.dimention();
			//Override bulk factor
			if(bulkSizeOverride != null && bulkSize > bulkSizeOverride.intValue()) {
				logger.info("Overriding bulk size to be " + bulkSizeOverride);
				bulkSize = bulkSizeOverride;
				futuresArray = new CompletableFuture[(matrix.dimention() / bulkSize) + ((matrix.dimention() % bulkSize) == 0 ? 0 : 1)];
			}
			else
				futuresArray = new CompletableFuture[bulkFactor];
			long smoothIterationTime;
			List<CompletableFuture<SmoothResponseBulk>> futures = new ArrayList<>(matrix.dimention());			
			for(int i = 0; i < iterations; i++) {
				logger.info("Startring iteration " + i + " out of " + iterations + " iterations");
				futures.clear();
				logger.info("Creating objects");
				List<EquationData[]> equations = DataConverter.convertEquations(matrix.getEquations(), matrix.getxVector(), bulkSize);
				logger.info("Creating async requests");
				for(int index = 0; index < equations.size(); index++) {
					final int ind = index;
					futures.add(CompletableFuture.supplyAsync(() ->{
						try {
							return restTool.doExchange(HttpMethod.POST, "smoother", "/smoother/bulk", null, equations.get(ind), SmoothResponseBulk.class);
						} catch (RestException e) {
							logger.error("Error during external call", e);
							return null;
						}
					}, Application.getExecutorService()));
					
				}
				logger.info("Waiting for async requests");
				CompletableFuture.allOf(futuresArray = futures.toArray(futuresArray)).get();
				smoothIterationTime = 0;
				logger.info("Updating results");
				SmoothResponse[] sRespTmp;
				for(int index = 0; index < equations.size(); index++) {
					sResp = futuresArray[index].get();
					smoothIterationTime += sResp.getTimeTook();
					sRespTmp = sResp.getResponces();
					for(int j = 0; j < sRespTmp.length; j++) {
						matrix.getxVector()[index * bulkSize + j] = sRespTmp[j].getValue();	
					}
					
				}
				response.setAvgIterationsTime(response.getAvgIterationsTime() + (double)(smoothIterationTime) / (double)equations.size());
			}
			response.setDimention(matrix.dimention());
			response.setMatrix(matrix);
			response.setIterations(iterations);
			response.setAvgIterationsTime(response.getAvgIterationsTime() / (double)iterations);
			response.setTimeTookMilli(System.currentTimeMillis() - startTime);
			return ResponseEntity.ok(response);
		}
			
		return ResponseEntity.badRequest().body(null);
	}
	
	@PostMapping(path="gauss-siedel/{iterations}")
	public ResponseEntity<SmootherAlgoResponse> smoothGS(@RequestParam int iterations, @RequestBody MatrixData data) throws CalculationException{
		//List<Map<Integer,EquationData>> independentSetsList = createIndependentSetsList(data);
		//independentSetsList.forEach(data -> );
		return ResponseEntity.ok(null);
	}

	private List<Map<Integer, EquationData>> createIndependentSetsList(Map<Integer, EquationData> data) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
