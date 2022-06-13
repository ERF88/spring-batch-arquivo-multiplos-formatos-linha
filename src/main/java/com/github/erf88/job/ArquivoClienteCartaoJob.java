package com.github.erf88.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class ArquivoClienteCartaoJob {

	@Bean
	public Job clienteCartaoJob(
			JobBuilderFactory jobBuilderFactory,
			Step clienteCartaoStep) {

		return jobBuilderFactory
				.get("clienteCartaoJob")
				.incrementer(new RunIdIncrementer())
				.start(clienteCartaoStep)
				.build();
	}

}
