package br.com.meli.PIFrescos.controller.mercadoPago.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PayerIdentificationDTO {
    @NotNull
    private String type;

    @NotNull
    private String number;
}
