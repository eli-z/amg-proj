package acc.edu.kube;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import acc.edu.kube.comm.RestTemplateTool;
import acc.edu.kube.exceptions.RestException;

public class Test {

	public static void main(String[] args) throws RestException {
		RestTemplateTool rt = new RestTemplateTool();
		rt.setRestTemplate(new RestTemplate());
		String resp = rt.doExchange(HttpMethod.POST, "smoother", "/smoother", null, null, String.class);
		System.out.println(resp);

	}

}
