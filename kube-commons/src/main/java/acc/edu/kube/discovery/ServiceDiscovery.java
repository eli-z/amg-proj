package acc.edu.kube.discovery;

import acc.edu.kube.exceptions.DiscoveryException;

public class ServiceDiscovery {
	private static final String HOST_POSTFIX = "_SERVICE_HOST";
	private static final String PORT_POSTFIX = "_SERVICE_PORT";
	
	public static String discoverServiceHost(String serviceName) throws DiscoveryException {
		String result = System.getenv(preprocess(serviceName) + HOST_POSTFIX);
		if(result == null)
			throw new DiscoveryException("Service " + serviceName + " not found");
		return result;
	}
	
	public static int discoverServicePort(String serviceName) throws DiscoveryException {
		try {
			return Integer.parseInt(System.getenv(preprocess(serviceName) + PORT_POSTFIX));
		}catch(NumberFormatException e) {
			
		}
		throw new DiscoveryException("Service port of " + serviceName + " not found");
	}

	private static String preprocess(String serviceName) {
		if(serviceName != null)
			serviceName = serviceName.toUpperCase().replace("-", "_");
		return serviceName;
	}
}
