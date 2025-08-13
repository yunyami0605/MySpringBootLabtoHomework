package com.homework1.MySpringbootLab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MySpringbootLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringbootLabApplication.class, args);
	}

}
