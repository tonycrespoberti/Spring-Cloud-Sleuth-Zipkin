package com.cloud.netflix.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SpringCloudNetflixProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudNetflixProjectApplication.class, args);
	}

}
