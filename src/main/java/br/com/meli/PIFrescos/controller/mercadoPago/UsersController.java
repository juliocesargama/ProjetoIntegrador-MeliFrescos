package br.com.meli.PIFrescos.controller.mercadoPago;

import br.com.meli.PIFrescos.config.security.TokenService;
import br.com.meli.PIFrescos.controller.mercadoPago.dtos.ProcessPaymentDTO;
import br.com.meli.PIFrescos.controller.mercadoPago.dtos.UserMercadoPagoDTO;
import br.com.meli.PIFrescos.controller.mercadoPago.service.MercadoPagoService;
import br.com.meli.PIFrescos.controller.mercadoPago.dtos.CardPaymentDTO;
import br.com.meli.PIFrescos.controller.mercadoPago.dtos.PaymentResponseDTO;
import br.com.meli.PIFrescos.models.PurchaseOrder;
import br.com.meli.PIFrescos.models.User;
import br.com.meli.PIFrescos.service.UserService;
import br.com.meli.PIFrescos.service.interfaces.IPurchaseOrderService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/mercado-pago/users")
public class UsersController {

    @Autowired
    private IPurchaseOrderService service;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private final MercadoPagoService mercadoPagoService;

    @Autowired
    public UsersController(MercadoPagoService mercadoPagoService) {
        this.mercadoPagoService = mercadoPagoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getDetailsUser(@PathVariable String id) throws MPException, MPApiException {
        Customer userByEMail = mercadoPagoService.getUserById(id);
        return ResponseEntity.ok(userByEMail);
    }

    @GetMapping
    public ResponseEntity<Stream<UserMercadoPagoDTO>> getAllUsers(@RequestParam(required = false) String email) throws MPException, MPApiException {
        List<Customer> users = new ArrayList<>();

        if(email != null){
            users.add(mercadoPagoService.getUserByEmail(email));
        }else{
            users = mercadoPagoService.getAllUsers().getResults();
        }

        return ResponseEntity.ok(UserMercadoPagoDTO.convertList(users));
    }

    @PostMapping
    public ResponseEntity<ProcessPaymentDTO> processPayment(@RequestBody @Valid CardPaymentDTO cardPaymentDTO) throws MPException, MPApiException {
        User userLogged = tokenService.getUserLogged();
        PurchaseOrder purchaseOrder = service.getPurchaseOrderByUserIdAndStatusIsOpened(userLogged.getId());
        PaymentResponseDTO payment = mercadoPagoService.processPayment(cardPaymentDTO, purchaseOrder);

        if (payment.getStatus().equals("approved"))
            service.updateOrderStatus(purchaseOrder.getId());

        return ResponseEntity.ok().body(new ProcessPaymentDTO(payment.getStatus(), payment.getDetail()));
    }
}
