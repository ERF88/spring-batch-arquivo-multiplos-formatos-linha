package com.github.erf88.reader;

import com.github.erf88.model.Arquivo;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@SuppressWarnings("ALL")
@Configuration
public class ArquivoClienteCartaoReader {

    @Value("${spring.batch.file.clientes-cartoes.in}")
    private Resource resource;

    @Bean
    public FlatFileItemReader<Arquivo> clienteCartaoReader() {
        return new FlatFileItemReaderBuilder<Arquivo>()
                .name("clienteCartaoReader")
                .resource(resource)
                .lineMapper((linha, numeroLinha) -> lineMapper(linha, numeroLinha))
                .build();
    }

    private Arquivo lineMapper(String linha, int numeroLinha) {
        int indiceCartao = linha.indexOf("[");
        String linhaCliente = linha.substring(0, indiceCartao-1);
        String linhaCartao = linha.substring(indiceCartao).replace("[", "").replace("]", "");
        Arquivo arquivo = new Arquivo();
        FieldSet clienteFieldSet = tokenizerCliente(linhaCliente);
        FieldSet cartaoFieldSet = tokenizerCartao(linhaCartao);
        arquivo.setIdCliente(clienteFieldSet.readInt("id"));
        arquivo.setNome(clienteFieldSet.readString("nome"));
        arquivo.setSobrenome(clienteFieldSet.readString("sobrenome"));
        arquivo.setEmail(clienteFieldSet.readString("email"));
        arquivo.setStatus(clienteFieldSet.readString("status"));
        arquivo.setIdCartao(cartaoFieldSet.readString("id"));
        arquivo.setNumeroCartao(cartaoFieldSet.readString("numeroCartao"));
        arquivo.setTipoCartao(cartaoFieldSet.readString("tipoCartao"));
        arquivo.setDataValidade(cartaoFieldSet.readString("dataValidade"));
        return arquivo;
    }

    private FieldSet tokenizerCliente(String linha) {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("^");
        lineTokenizer.setNames("id", "nome", "sobrenome", "email", "status");
        return lineTokenizer.tokenize(linha);
    }

    private FieldSet tokenizerCartao(String linha) {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setNames("id", "idCliente", "numeroCartao", "tipoCartao", "dataValidade");
        return lineTokenizer.tokenize(linha);
    }

}