package com.zenvia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = { "com.zenvia" })
public class SpringBootBenchmarkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBenchmarkApplication.class, args);
	}
}
