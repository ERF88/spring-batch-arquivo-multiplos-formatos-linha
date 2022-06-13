package com.github.erf88.step;

import com.github.erf88.model.Arquivo;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("ALL")
@Configuration
public class ArquivoClienteCartaoStep {

    @Bean
    public Step clienteCartaoStep(
            StepBuilderFactory stepBuilderFactory,
            FlatFileItemReader<Arquivo> clienteCartaoReader,
            ItemProcessor<Arquivo, Arquivo> clienteCartaoProcessor,
            CompositeItemWriter<Arquivo> clienteCartaoWriter) {

        return stepBuilderFactory
                .get("clienteCartaoStep")
                .<Arquivo, Arquivo>chunk(50)
                .reader(clienteCartaoReader)
                .processor(clienteCartaoProcessor)
                .writer(clienteCartaoWriter)
                .build();
    }

}
