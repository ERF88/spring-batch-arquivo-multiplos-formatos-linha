package com.github.erf88.processor;

import com.github.erf88.model.Arquivo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClienteCartaoProcessor implements ItemProcessor<Arquivo, Arquivo> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final LocalDate dataProcessamento = LocalDate.now();

    @Override
    public Arquivo process(Arquivo arquivo) {
        arquivo.setNome(removeAcentos(arquivo.getNome()));
        arquivo.setSobrenome(removeAcentos(arquivo.getSobrenome()));
        arquivo.setDataProcessamento(dataProcessamento.format(formatter));
        return arquivo;
    }

    public static String removeAcentos(final String str) {
        String strSemAcentos = Normalizer.normalize(str, Normalizer.Form.NFD);
        return strSemAcentos.replaceAll("[^\\p{ASCII}]", "");
    }

}
