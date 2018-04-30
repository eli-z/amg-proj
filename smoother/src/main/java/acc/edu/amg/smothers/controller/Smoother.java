package acc.edu.amg.smothers.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import acc.edu.amg.exceptions.CalculationException;
import acc.edu.amg.smothers.concrete.JacobiSmoother;
import acc.edu.amg.data.EquationData;

@RestController
@RequestMapping("/")
public class Smoother {
	private static Logger logger = LoggerFactory.getLogger(Smoother.class);
	@PostMapping
	public ResponseEntity<EquationData> smooth(@RequestBody EquationData data) throws CalculationException{
		double newX = JacobiSmoother.smooth(data.getaVector(), data.getxVector(), data.getB(), data.getiIndex());
		logger.info("New value: " + data.getxVector()[data.getiIndex()]);
		return ResponseEntity.ok(data);
	}
/*	@PostMapping
	public ResponseEntity<Map<Integer,EquationData>> smooth(@RequestBody Map<Integer,EquationData> data) throws CalculationException{
		double newX = JacobiSmoother.smooth(data.getaVector(), data.getxVector(), data.getB(), data.getiIndex());
		logger.info("New value: " + data.getxVector()[data.getiIndex()]);
		return ResponseEntity.ok(data);
	}*/
}
