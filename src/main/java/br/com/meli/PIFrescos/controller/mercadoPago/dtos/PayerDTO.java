package br.com.meli.PIFrescos.controller.mercadoPago.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PayerDTO {
    @NotNull
    private String email;

    @NotNull
    private PayerIdentificationDTO identification;
}
