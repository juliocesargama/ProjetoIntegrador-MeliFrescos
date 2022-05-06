package br.com.meli.PIFrescos.controller.mercadoPago.dtos;

import com.mercadopago.resources.customer.CustomerCardPaymentMethod;
import lombok.Data;

@Data
public class PaymentMethodDTO {
    String id;
    String name;
    String paymentTypeId;
    String thumbnail;
    String secureThumbnail;

    public PaymentMethodDTO(CustomerCardPaymentMethod paymentMethod) {
        id = paymentMethod.getId();
        name = paymentMethod.getName();
        paymentTypeId = paymentMethod.getPaymentTypeId();
        thumbnail = paymentMethod.getThumbnail();
        secureThumbnail = paymentMethod.getSecureThumbnail();
    }
}
