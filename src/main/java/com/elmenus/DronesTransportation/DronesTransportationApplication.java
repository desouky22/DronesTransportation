package com.elmenus.DronesTransportation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DronesTransportationApplication {
	public static void main(String[] args) {
		SpringApplication.run(DronesTransportationApplication.class, args);
	}
}
