package br.com.meli.PIFrescos.controller.mercadoPago.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponseDTO {
    private String status;
    private String detail;
}
