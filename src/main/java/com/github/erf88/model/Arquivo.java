package com.github.erf88.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Arquivo {

    private Integer idCliente;
    private String nome;
    private String sobrenome;
    private String email;
    private String status;
    private String idCartao;
    private String numeroCartao;
    private String tipoCartao;
    private String dataValidade;
    private String dataProcessamento;

}
