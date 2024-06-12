package com.skenariolabs.propertyviewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.skenariolabs")
public class PropertyViewerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropertyViewerApplication.class, args);
	}

}
