package br.com.meli.PIFrescos.controller.mercadoPago.dtos;

import com.mercadopago.resources.customer.CustomerCard;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Stream;

@Data
public class CreditCardDTO {
    String id;
    String customerId;
    Integer expirationMonth;
    Integer expirationYear;
    String firstSixDigits;
    String lastFourDigits;
    OffsetDateTime dateCreated;
    OffsetDateTime dateLastUpdated;
    String userId;
    PaymentMethodDTO paymentMethod;
    CardholderDTO cardholder;

    public CreditCardDTO(CustomerCard customerCard) {
        id = customerCard.getId();
        customerId = customerCard.getCustomerId();
        expirationMonth = customerCard.getExpirationMonth();
        expirationYear = customerCard.getExpirationYear();
        firstSixDigits = customerCard.getFirstSixDigits();
        lastFourDigits = customerCard.getLastFourDigits();
        dateCreated = customerCard.getDateCreated();
        dateLastUpdated = customerCard.getDateLastUpdated();
        userId = customerCard.getUserId();
        paymentMethod = new PaymentMethodDTO(customerCard.getPaymentMethod());
        cardholder = new CardholderDTO(customerCard.getCardholder());
    }

    public static Stream convertList(List<CustomerCard> list){
        return list.stream().map(CreditCardDTO::new);
    }
}
