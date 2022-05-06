package br.com.meli.PIFrescos.controller.mercadoPago.service;

import br.com.meli.PIFrescos.controller.mercadoPago.dtos.CardPaymentDTO;
import br.com.meli.PIFrescos.controller.mercadoPago.dtos.PaymentResponseDTO;
import br.com.meli.PIFrescos.models.Address;
import br.com.meli.PIFrescos.models.PurchaseOrder;
import br.com.meli.PIFrescos.models.User;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.customer.*;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.customer.Customer;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
public class MercadoPagoService {

    @Value("${spring.mercadopago.token}")
    private String token;

    public MercadoPagoService() {
        MercadoPagoConfig.setAccessToken(token);
    }

    public void registerUser(User user) throws MPException, MPApiException {
        CustomerRequest customerRequest = createUserRequest(user);
        new CustomerClient().create(customerRequest);
    }

    public Customer getUserById(String id) throws MPException, MPApiException {
        Map<String, Object> filters = new HashMap<>();
        filters.put("id", id);
        return getByFilter(filters);
    }

    public Customer getUserByEmail(String email) throws MPException, MPApiException {
        Map<String, Object> filters = new HashMap<>();
        filters.put("email", email);
        return getByFilter(filters);
    }

    public MPResultsResourcesPage<Customer> getAllUsers() throws MPException, MPApiException {
        MPSearchRequest searchRequest = MPSearchRequest.builder().offset(0).limit(0).build();
        return new CustomerClient().search(searchRequest);
    }

    private Customer getByFilter(Map<String, Object> filters) throws MPException, MPApiException {
        MPSearchRequest searchRequest = MPSearchRequest.builder().offset(0).limit(0).filters(filters).build();

        MPResultsResourcesPage<Customer> search = new CustomerClient().search(searchRequest);

        if (search.getResults().size() < 1)
            throw new EntityNotFoundException("User not found in paid market");

        return search.getResults().get(0);
    }

    public PaymentResponseDTO processPayment(CardPaymentDTO cardPaymentDTO, PurchaseOrder purchaseOrder) throws MPException, MPApiException {

        PaymentCreateRequest paymentCreateRequest =
                PaymentCreateRequest.builder()
                        .transactionAmount(purchaseOrder.getTotalPrice())
                        .token(cardPaymentDTO.getToken())
                        .description(cardPaymentDTO.getProductDescription())
                        .installments(cardPaymentDTO.getInstallments())
                        .paymentMethodId(cardPaymentDTO.getPaymentMethodId())
                        .payer(
                                PaymentPayerRequest.builder()
                                        .email(cardPaymentDTO.getPayer().getEmail())
                                        .identification(
                                                IdentificationRequest.builder()
                                                        .type(cardPaymentDTO.getPayer().getIdentification().getType())
                                                        .number(cardPaymentDTO.getPayer().getIdentification().getNumber())
                                                        .build())
                                        .build())
                        .build();

        Payment createdPayment = new PaymentClient().create(paymentCreateRequest);

        return new PaymentResponseDTO(
                String.valueOf(createdPayment.getStatus()),
                createdPayment.getStatusDetail());
    }

    private CustomerRequest createUserRequest(User user) {
        IdentificationRequest identificationRequest = IdentificationRequest.builder()
                .number(user.getId().toString())
                .type("Id")
                .build();

        return CustomerRequest.builder()
                .identification(identificationRequest)
                .email(user.getEmail())
                .description("User MeliFrescos")
                .dateRegistred(OffsetDateTime.now())
                .firstName(user.getFullname())
                .address(null)
                .build();
    }


    private CustomerAddressRequest createAddressRequest(User user) {
        Address address = user.getAddress();

        if (address.getId() == null)
            return null;

        CustomerAddressRequest addressRequest = CustomerAddressRequest.builder()
                .id(address.getId().toString())
                .streetName(address.getStreet())
                .streetNumber(Integer.parseInt(address.getNumber()))
                .zipCode(address.getZipcode())
                .build();

        return addressRequest;
    }
}
