package br.com.meli.PIFrescos.controller.mercadoPago.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CardPaymentDTO {
    @NotNull
    private String token;
    private String issuerId;
    @NotNull
    private String paymentMethodId;
    @NotNull
    private BigDecimal transactionAmount;
    @NotNull
    private Integer installments;
    @NotNull
    @JsonProperty("description")
    private String productDescription;
    @NotNull
    private PayerDTO payer;
}
