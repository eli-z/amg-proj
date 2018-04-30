package acc.edu.amg.smothersalgo.controller;


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
import acc.edu.amg.exceptions.CalculationException;
import acc.edu.amg.smothersalgo.data.ResponseData;
import acc.edu.kube.comm.RestTemplateTool;
import acc.edu.kube.exceptions.RestException;

@RestController
@RequestMapping("/")
public class SmootherAlgo {
	private static Logger logger = LoggerFactory.getLogger(SmootherAlgo.class);
	
	@Autowired
	private RestTemplateTool restTool;
	
	@PostMapping(path="jacobi/{iterations}")
	public ResponseEntity<ResponseData> smoothJ(@RequestParam int iterations, @RequestBody MatrixData matrix) throws CalculationException, InterruptedException, ExecutionException{
		if(matrix.validate())
		{
			ResponseData response = new ResponseData();
			logger.info("Startring Jacobi for " + iterations + " iterations");
			long startTime = System.currentTimeMillis();
			double[]xVec = new double[matrix.dimention()];
			double[] resVector = new double[matrix.dimention()];
			EquationData[] equations = createData(matrix);
			for(int i = 0; i < iterations; i++) {
				CompletableFuture<EquationData>[] futures = new CompletableFuture[equations.length];
				for(int index = 0; index < equations.length; index++) {
					final int ind = index;
					futures[index] = CompletableFuture.supplyAsync(() ->{
						try {
							return restTool.doExchange(HttpMethod.POST, "smoother", "/smoother", equations[ind], EquationData.class);
						} catch (RestException e) {
							logger.error("Error during external call", e);
							return equations[ind];
						}
					});
					CompletableFuture.allOf(futures).get();
				}
				response.setDimention(equations.length);
				response.setIterations(iterations);
				response.setTimeTookMilli(System.currentTimeMillis() - startTime);
			}
			return ResponseEntity.ok(response);
		}
			
		return ResponseEntity.badRequest().body(null);
	}
	
	private EquationData[] createData(MatrixData matrix) {
		EquationData[] result = new EquationData[matrix.dimention()];
		EquationData tmp;
		Equation[] eqs = matrix.getEquations();
		double[] bVector = matrix.getbVector();
		for(int i = 0; i < matrix.dimention(); i++) {
			tmp = new EquationData();
			tmp.setaVector(eqs[i].getaVector());
			tmp.setxVector(new double[eqs[i].getaVector().length]);
			tmp.setB(bVector[i]);
			tmp.setiIndex(i);
			result[i] = tmp;
		}
		return result;
	}

	@PostMapping(path="gauss-siedel/{iterations}")
	public ResponseEntity<ResponseData> smoothGS(@RequestParam int iterations, @RequestBody MatrixData data) throws CalculationException{
		//List<Map<Integer,EquationData>> independentSetsList = createIndependentSetsList(data);
		//independentSetsList.forEach(data -> );
		return ResponseEntity.ok(null);
	}

	private List<Map<Integer, EquationData>> createIndependentSetsList(Map<Integer, EquationData> data) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
