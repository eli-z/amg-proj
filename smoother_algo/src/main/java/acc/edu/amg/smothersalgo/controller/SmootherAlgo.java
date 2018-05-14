package acc.edu.amg.smothersalgo.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import acc.edu.amg.data.Equation;
import acc.edu.amg.data.EquationData;
import acc.edu.amg.data.MatrixData;
import acc.edu.amg.data.convertors.DataConverter;
import acc.edu.amg.exceptions.CalculationException;
import acc.edu.kube.comm.RestTemplateTool;
import acc.edu.kube.comm.responses.SmoothResponse;
import acc.edu.kube.comm.responses.SmootherAlgoResponse;
import acc.edu.kube.exceptions.RestException;

@RestController
@RequestMapping("/")
public class SmootherAlgo {
	private static Logger logger = LoggerFactory.getLogger(SmootherAlgo.class);
	
	@Autowired
	private RestTemplateTool restTool;
	
	@PostMapping(path="jacobi/{iterations}")
	public ResponseEntity<SmootherAlgoResponse> smoothJ(@RequestParam int iterations, @RequestBody MatrixData matrix) throws CalculationException, InterruptedException, ExecutionException{
		if(matrix.validate())
		{
			SmootherAlgoResponse response = new SmootherAlgoResponse();
			logger.info("Startring Jacobi for " + iterations + " iterations");
			long startTime = System.currentTimeMillis();
			CompletableFuture<SmoothResponse>[] futuresArray = new CompletableFuture[matrix.dimention()];
			SmoothResponse sResp;
			long smoothIterationTime;
			List<CompletableFuture<SmoothResponse>> futures = new ArrayList<>(matrix.dimention());			
			for(int i = 0; i < iterations; i++) {
				futures.clear();
				EquationData[] equations = DataConverter.convertEquations(matrix.getEquations(), matrix.getxVector());
				for(int index = 0; index < equations.length; index++) {
					final int ind = index;
					futures.add(CompletableFuture.supplyAsync(() ->{
						try {
							return restTool.doExchange(HttpMethod.POST, "smoother", "/smoother", equations[ind], SmoothResponse.class);
						} catch (RestException e) {
							logger.error("Error during external call", e);
							return new SmoothResponse(matrix.getxVector()[ind], 0);
						}
					}));
					
				}
				CompletableFuture.allOf(futures.toArray(futuresArray)).get();
				smoothIterationTime = 0;
				for(int index = 0; index < equations.length; index++) {
					sResp = futuresArray[index].get();
					smoothIterationTime += sResp.getTimeTook();
					matrix.getxVector()[index] = sResp.getValue();
				}
				response.setAvgIterationsTime(response.getAvgIterationsTime() + (double)(smoothIterationTime) / (double)equations.length);
			}
			response.setDimention(matrix.dimention());
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
