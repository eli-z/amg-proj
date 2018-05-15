package acc.edu.kube.comm;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import acc.edu.kube.discovery.ServiceDiscovery;
import acc.edu.kube.exceptions.DiscoveryException;
import acc.edu.kube.exceptions.RestException;

@Service
public class RestTemplateTool {
	@Autowired
	private RestTemplate restTemplate;
	
	public <Req,Resp> Resp doExchange(HttpMethod method, String serviceName, String path, Req requestBody, Class<Resp> respClass, Object... urlParams) throws RestException {
		URI uri;
		try {
			uri = createUri(serviceName, path, urlParams);
		} catch (DiscoveryException e) {
			throw new RestException(e.getMessage(), e);
		}
		RequestEntity<Req> request = new RequestEntity<>(requestBody, method, uri);
		ResponseEntity<Resp> response = restTemplate.exchange(request, respClass);
		if(response.getStatusCode() == HttpStatus.OK)
			return response.getBody();
		throw new RestException("Request failed with error code " + response.getStatusCode());
	}

	public static URI createUri(String serviceName, String path, Object... urlParams) throws DiscoveryException {
		return UriComponentsBuilder.newInstance().scheme("http").host(ServiceDiscovery.discoverServiceHost(serviceName))
		.port(ServiceDiscovery.discoverServicePort(serviceName))
		.path(path).buildAndExpand(urlParams).toUri();
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	
}
