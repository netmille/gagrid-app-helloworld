package com.netmillennium.gagrid.app.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.netmillennium.gagrid.model.Chromosome;
import com.netmillennium.gagrid.parameter.GAGrid;

@SpringBootApplication
public class HelloWorldApplication implements CommandLineRunner {
	   
	@Autowired
	private GAGrid gaGrid;
	  
	public static void main(String[] args) {
	   SpringApplication.run(HelloWorldApplication.class, args);
	  }
	 @Override
	 public void run(String... args) {  
		Chromosome solution =  gaGrid.evolve();
	 }
}
