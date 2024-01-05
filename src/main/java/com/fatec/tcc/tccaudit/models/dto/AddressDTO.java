package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddressDTO(@JsonProperty("logradouro") String street, @JsonProperty("localidade") String city,
        @JsonProperty("uf") String state) implements Serializable {
    private static final long serialVersionUID = 1L;
}