package com.github.erf88.writer;

import com.github.erf88.model.Arquivo;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.Arrays;

@SuppressWarnings("ALL")
@Configuration
public class ArquivoClienteCartaoWriter {

    @Value("${spring.batch.file.clientes.out}")
    private Resource clientesResource;

    @Value("${spring.batch.file.cartoes.out}")
    private Resource cartoesResource;

    @Bean
    public CompositeItemWriter<Arquivo> clienteCartaoWriter() {
        FlatFileItemWriter<Arquivo> clienteWriter = clienteWriter();
        FlatFileItemWriter<Arquivo> cartaoWriter = cartaoWriter();
        return new CompositeItemWriterBuilder()
                .delegates(Arrays.asList(clienteWriter, cartaoWriter))
                .build();
    }
    private FlatFileItemWriter<Arquivo> clienteWriter() {
        return new FlatFileItemWriterBuilder<Arquivo>()
                .name("clienteWriter")
                .resource(clientesResource)
                .headerCallback(header -> header.write("ID;NOME;SOBRENOME;EMAIL;STATUS;DATA_PROCESSAMENTO"))
                .delimited()
                .delimiter(";")
                .names(new String[] { "idCliente", "nome", "sobrenome", "email", "status", "dataProcessamento" })
                .build();
    }

    private FlatFileItemWriter<Arquivo> cartaoWriter() {
        return new FlatFileItemWriterBuilder<Arquivo>()
                .name("cartaoWriter")
                .resource(cartoesResource)
                .headerCallback(header -> header.write("ID;ID_CLIENTE;NUMERO_CARTAO;TIPO_CARTAO;DATA_VALIDADE;DATA_PROCESSAMENTO"))
                .delimited()
                .delimiter(";")
                .names(new String[] { "idCartao", "idCliente", "numeroCartao", "tipoCartao", "dataValidade", "dataProcessamento" })
                .build();
    }

}
