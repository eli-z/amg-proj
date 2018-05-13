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

@RestController
@RequestMapping("/")
public class Smoother {
	private static Logger logger = LoggerFactory.getLogger(Smoother.class);
	@PostMapping
	public ResponseEntity<SmoothResponse> smooth(@RequestBody EquationData data) throws CalculationException{
		long timestamp = System.currentTimeMillis();
		double newX = JacobiSmoother.smooth(data.getaVector(), data.getxVector(), data.getB(), data.getiIndex());
		logger.info("New value: " + newX);
		SmoothResponse response = new SmoothResponse(newX, System.currentTimeMillis() - timestamp);
		logger.info("Calcualtions took {} milliseconds for equation of size {}", response.getTimeTook(), data.getaVector().length);
		return ResponseEntity.ok(response);
	}
/*	@PostMapping
	public ResponseEntity<Map<Integer,EquationData>> smooth(@RequestBody Map<Integer,EquationData> data) throws CalculationException{
		double newX = JacobiSmoother.smooth(data.getaVector(), data.getxVector(), data.getB(), data.getiIndex());
		logger.info("New value: " + data.getxVector()[data.getiIndex()]);
		return ResponseEntity.ok(data);
	}*/
}
