package com.example.demo;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ClientApp {

	private Logger logger = Logger.getLogger("BANKCLIENT");
	
	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private LoadBalancerClient loadBalancer;

	public String fallBack() {
		return "fallback";
	}

	@RequestMapping(path = "clientversion", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "fallBack")
	public String clientVersion() {
		logger.info("The Client Entry ");
		// you need to access the other service
		// 1. hardcoding is bad
		// 2. i may have more than one instance how to balance ?
		// *******
		System.out
				.println("Total Bank Service Instance :::::::: " + discoveryClient.getInstances("BANKSERVICE").size());
		// here you will have to do it manually
		discoveryClient.getInstances("BANKSERVICE").forEach(service -> {
			// on exception i will try the next url
		});

		// the load balancer way
		ServiceInstance instance = loadBalancer.choose("BANKSERVICE");
		System.out.println("Instance Selected is on port " + instance.getPort());
		RestTemplate client = new RestTemplate();
		ResponseEntity<String> response = client
				.getForEntity("http://" + instance.getHost() + 
						":" + instance.getPort() + "/version", String.class);
		return "Thank you " + response.getBody();
	}
}



