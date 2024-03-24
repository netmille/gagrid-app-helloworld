package com.netmillennium.gagrid.app.helloworld.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.s3.TcpDiscoveryS3IpFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.netmillennium.gagrid.services.helloworld.HelloWorldFitnessFunction;
import com.netmillennium.gagrid.services.helloworld.HelloWorldTerminateCriteria;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.netmillennium.gagrid.model.Gene;
import com.netmillennium.gagrid.parameter.GAConfiguration;
import com.netmillennium.gagrid.parameter.GAGrid;

@Configuration
public class HelloWorldConfig {

	
	@Bean ("gaConfiguration")
	public GAConfiguration gaConfiguration(@Autowired Ignite ignite)
	{
        // Create GAConfiguration
        GAConfiguration gaConfig = new GAConfiguration();

        // set Gene Pool
        List<Gene> genes = this.getGenePool();

        // set the Chromosome Length to '11' since 'HELLO WORLD' contains 11 characters.
        gaConfig.setChromosomeLen(11);

        // initialize gene pool
        gaConfig.setGenePool(genes);

        // create and set Fitness function
        HelloWorldFitnessFunction function = new HelloWorldFitnessFunction();
        gaConfig.setFitnessFunction(function);

        // create and set TerminateCriteria
        HelloWorldTerminateCriteria termCriteria = new HelloWorldTerminateCriteria(ignite);
        gaConfig.setTerminateCriteria(termCriteria);

        return gaConfig;
	}
	
	@Bean("gaGrid")
	public GAGrid gagrid(@Autowired GAConfiguration gaConfiguration, @Autowired Ignite ignite)
	{
		GAGrid gaGrid = new GAGrid(gaConfiguration, ignite);
		
		return gaGrid;
	}
	 /**
     * Helper routine to initialize Gene pool
     * 
     * In typical usecase genes may be stored in database.
     * 
     * @return List<Gene>
     */
    private List<Gene> getGenePool() {
        List<Gene> list = new ArrayList();

        char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ' };

        for (int i = 0; i < chars.length; i++) {
            Gene gene = new Gene(new Character(chars[i]));
            list.add(gene);
        }
        return list;
    }	TcpDiscoverySpi spi = new TcpDiscoverySpi();

    
    @Bean("ignite")
    public Ignite ignite(@Autowired Environment environment)
    {
    	 List lprofiles =  Arrays.asList(environment.getActiveProfiles());  
  
    	 if (lprofiles.contains("aws"))
    		 return (s3discovery());
    	 else  
    		 return (multicastDiscovery());			 
    }
    
    
    /**
     * AWS
     * 
     * @return Ignite
     */
    private Ignite s3discovery()
      {
	    AWSCredentialsProvider instanceProfileCreds = new InstanceProfileCredentialsProvider(false);

	    TcpDiscoveryS3IpFinder ipFinder = new TcpDiscoveryS3IpFinder();
	    ipFinder.setAwsCredentialsProvider(instanceProfileCreds);
	    ipFinder.setBucketName("gagrid");

	    spi.setIpFinder(ipFinder);

	    IgniteConfiguration cfg = new IgniteConfiguration();

	    // Override default discovery SPI.
	    cfg.setDiscoverySpi(spi);

	    // Start a node.
	    return (Ignition.start(cfg));
      }
    
    /**
     * Local
     * 
     * @return Ignite
     */
    private Ignite multicastDiscovery()
    {
  		 IgniteConfiguration cfg = new IgniteConfiguration();
  		 return Ignition.start(cfg);
  	 }
}
