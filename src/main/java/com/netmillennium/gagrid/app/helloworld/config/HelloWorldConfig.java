package com.netmillennium.gagrid.app.helloworld.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.ignite.Ignite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.netmillennium.gagrid.services.helloworld.HelloWorldFitnessFunction;
import com.netmillennium.gagrid.services.helloworld.HelloWorldTerminateCriteria;
import com.netmillennium.gagrid.model.Gene;
import com.netmillennium.gagrid.parameter.GAConfiguration;
import com.netmillennium.gagrid.parameter.GAGrid;

@Configuration
public class HelloWorldConfig {

	@Autowired
	private Ignite ignite;
	
	@Autowired
    private ApplicationContext applicationContext;
	
	@Bean ("gaConfiguration")
	public GAConfiguration gaConfiguration()
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
	@DependsOn("gaConfiguration")
	public GAGrid gagrid()
	{
		GAGrid gaGrid = new GAGrid((GAConfiguration)applicationContext.getBean("gaConfiguration"), ignite);
		
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
    }
    

}
