package br.com.meli.PIFrescos.controller.mercadoPago.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcessPaymentDTO {
    String status;
    String message;
}
