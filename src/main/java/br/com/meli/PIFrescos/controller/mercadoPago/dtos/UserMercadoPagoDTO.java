package br.com.meli.PIFrescos.controller.mercadoPago.dtos;

import com.mercadopago.resources.customer.Customer;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Stream;

@Data
public class UserMercadoPagoDTO {
    String id;
    String email;
    String firstName;
    String lastName;
    String phone;
    OffsetDateTime dateRegistered;
    String description;

    public UserMercadoPagoDTO(Customer customer) {
        this.id = customer.getId();
        email = customer.getEmail();
        firstName = customer.getEmail();
        lastName = customer.getLastName();
        phone = customer.getPhone().getNumber();
        dateRegistered = customer.getDateRegistered();
        description = customer.getDescription();
    }

    public static Stream<UserMercadoPagoDTO> convertList(List<Customer> users){
        return users.stream().map(UserMercadoPagoDTO::new);
    }

}
