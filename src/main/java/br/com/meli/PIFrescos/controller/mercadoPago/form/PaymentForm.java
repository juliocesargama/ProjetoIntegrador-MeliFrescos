package br.com.meli.PIFrescos.controller.mercadoPago.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PaymentForm {

    @NotBlank(message = "token nao pode ser vazio")
    String token;

    @NotBlank(message = "paymentMethodId nao pode ser vazio")
    String paymentMethodId;
}
