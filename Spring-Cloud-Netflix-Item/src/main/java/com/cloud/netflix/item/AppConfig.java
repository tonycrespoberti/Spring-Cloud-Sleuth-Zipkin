package com.cloud.netflix.item;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
//As a configuration Spring, we are creating a Bean Rest Template as a resource to access Producto API
//This Bean has a name "clienteRest"
public class AppConfig {

	@Bean("clienteRest")
	@LoadBalanced //Used for Rest Client
	public RestTemplate registrarRestTemplate() {
		
		return new RestTemplate();
	}
}
