package br.com.meli.PIFrescos.controller.mercadoPago;

import br.com.meli.PIFrescos.controller.mercadoPago.dtos.CreditCardDTO;
import br.com.meli.PIFrescos.controller.mercadoPago.form.PaymentForm;
import br.com.meli.PIFrescos.controller.mercadoPago.service.CreditCardService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.customer.CustomerCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/mercado-pago/credit-card")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @PostMapping
    public ResponseEntity<CustomerCard> save(@RequestBody @Valid PaymentForm paymentForm) throws MPException, MPApiException {
        CustomerCard customerCard = creditCardService.save(paymentForm);
        return ResponseEntity.ok(customerCard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) throws MPException, MPApiException {
        creditCardService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Stream<CreditCardDTO>> list() throws MPException, MPApiException {
        List<CustomerCard> list = creditCardService.list();
        return ResponseEntity.ok(CreditCardDTO.convertList(list));
    }

}
