package acc.edu.amg.smothers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import acc.edu.amg.data.EquationData;
import acc.edu.amg.exceptions.CalculationException;
import acc.edu.amg.smoothers.JacobiSmoother;
import acc.edu.kube.comm.responses.SmoothResponse;
import acc.edu.kube.comm.responses.SmoothResponseBulk;

@RestController
@RequestMapping("/")
public class Smoother {
	private static Logger logger = LoggerFactory.getLogger(Smoother.class);
	@PostMapping
	public ResponseEntity<SmoothResponse> smooth(@RequestBody EquationData data) throws CalculationException{
		long timestamp = System.nanoTime();
		double newX = JacobiSmoother.smooth(data.getaVector(), data.getxVector(), data.getB(), data.getiIndex());
		logger.info("New value: " + newX);
		SmoothResponse response = new SmoothResponse(newX, System.nanoTime() - timestamp);
		logger.info("Calcualtions took {} nanoseconds for equation of size {}", response.getTimeTook(), data.getaVector().length);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(path="bulk")
	public ResponseEntity<SmoothResponseBulk> smoothBulk(@RequestBody EquationData[] data) throws CalculationException{
		long timestamp = System.nanoTime();
		SmoothResponse[] responses = new SmoothResponse[data.length];
		for(int i = 0; i < data.length; i++) {
			responses[i] = smooth(data[i]).getBody();
		}
		SmoothResponseBulk  response = new SmoothResponseBulk();
		response.setResponces(responses);
		response.setTimeTook(System.nanoTime() - timestamp);
		logger.info("Calcualtions took {} nanoseconds for {} equations", response.getTimeTook(), data.length);
		return ResponseEntity.ok(response);
	}


}
