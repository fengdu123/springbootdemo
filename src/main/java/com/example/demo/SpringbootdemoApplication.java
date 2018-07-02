package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;

@SpringBootApplication
public class SpringbootdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootdemoApplication.class, args);
	}

	/** 是spring boot能够快速的加载一些额外的组件*/
	@Bean
	public PersistenceAnnotationBeanPostProcessor paPostProcessor(){
		return new PersistenceAnnotationBeanPostProcessor();
	}
}
