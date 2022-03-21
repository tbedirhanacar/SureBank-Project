package com.bank.sure;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.NamingConventions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SurebankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurebankApplication.class, args);
	}
	
	//It is a bean, when you run the application
	//SpringBoot creates a bean from the modelMapper and put it to spring ioc container
	//then you can get this object by using @Autowired annotation
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
		.setFieldMatchingEnabled(true)
		.setFieldAccessLevel(AccessLevel.PRIVATE)
		.setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
		return modelMapper;
	}
	
}
