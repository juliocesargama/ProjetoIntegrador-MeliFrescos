package br.com.meli.PIFrescos.controller.mercadoPago.service;

import br.com.meli.PIFrescos.config.security.TokenService;
import br.com.meli.PIFrescos.controller.mercadoPago.form.PaymentForm;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.customer.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.customer.Customer;
import com.mercadopago.resources.customer.CustomerCard;
import com.mercadopago.resources.customer.CustomerCardIssuer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CreditCardService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @Value("${spring.mercadopago.token}")
    private String token;

    public CreditCardService() throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken(token);
    }

    public CustomerCard save(PaymentForm paymentForm) throws MPException, MPApiException {
        Customer userMepa = getUserMepa();

        CustomerCardIssuer issuer = CustomerCardIssuer.builder()
                .id(LocalDateTime.now().toString())
                .build();

        CustomerCardCreateRequest cardCreateRequest = CustomerCardCreateRequest.builder()
                .token(paymentForm.getToken())
                .issuer(issuer)
                .paymentMethodId(paymentForm.getPaymentMethodId())
                .build();

        return new CustomerCardClient().create(userMepa.getId(), cardCreateRequest);
    }

    public CustomerCard delete(String id) throws MPException, MPApiException {
        Customer userMepa = getUserMepa();

        return new CustomerCardClient().delete(userMepa.getId(), id);
    }

    public List<CustomerCard> list() throws MPException, MPApiException {
        Customer userMepa = getUserMepa();
        return userMepa.getCards();
    }

    private Customer getUserMepa() throws MPException, MPApiException {
        String emailUserLogged = tokenService.getUserLogged().getEmail();
        return mercadoPagoService.getUserByEmail(emailUserLogged);
    }
}
