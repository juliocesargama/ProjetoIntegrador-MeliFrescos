package br.com.meli.PIFrescos.controller.mercadoPago.dtos;

import com.mercadopago.resources.customer.CustomerCardCardholder;
import lombok.Data;

@Data
public class CardholderDTO {
    String name;
    String type;
    String number;

    public CardholderDTO(CustomerCardCardholder cardholder) {
        name = cardholder.getName();
        type = cardholder.getIdentification().getType();
        number = cardholder.getIdentification().getNumber();
    }
}
