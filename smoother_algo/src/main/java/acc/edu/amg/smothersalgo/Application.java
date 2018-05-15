package acc.edu.amg.smothersalgo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"acc.edu"})

public class Application extends SpringBootServletInitializer{

	private static ExecutorService eSrv = Executors.newFixedThreadPool(10);
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	public static ExecutorService getExecutorService() {
		return eSrv;
	}
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
