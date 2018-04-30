package acc.edu.amg.smothers.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

	@GetMapping(path="/is-alive")
	public ResponseEntity<String> isAlive() {
		return ResponseEntity.ok("alive");
	}
}
