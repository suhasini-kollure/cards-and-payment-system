package com.payment.controller;

import com.payment.dto.DateFilter;
import com.payment.model.Card;
import com.payment.model.Customer;
import com.payment.model.Payment;
import com.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final RestTemplate restTemplate;

    @Autowired
    public PaymentController(PaymentService paymentService, RestTemplate restTemplate) {
        this.paymentService = paymentService;
        this.restTemplate = restTemplate;
    }

    @Operation(summary = "Process Payment", description = "Endpoint to process a payment by cardNumber")
    @PostMapping("/process/{cardNumber}")
    public ResponseEntity<Payment> processPayment(
            @Valid @RequestBody Payment payment, @PathVariable("cardNumber") String cardNumber) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> body = new HttpEntity<>(headers);

        ResponseEntity<Card> card =
                restTemplate.exchange("http://localhost:8082/card/get/" + cardNumber, HttpMethod.GET, body, Card.class);
        payment.setCard(card.getBody());

        ResponseEntity<Customer> customer = restTemplate.exchange(
                "http://localhost:8081/customer/getCustomerByCardNumber/" + cardNumber,
                HttpMethod.GET,
                body,
                Customer.class);
        payment.setCustomer(customer.getBody());

        payment.setStatus("Open");

        Payment processedPayment = paymentService.processPayment(payment);
        return new ResponseEntity<>(processedPayment, HttpStatus.OK);
    }

    @Operation(summary = "Get Transaction", description = "Endpoint to get a transaction by transactionId")
    @GetMapping("/getTransaction/{transactionId}")
    public ResponseEntity<Payment> getTransaction(@PathVariable("transactionId") String transactionId) {
        Payment transaction = paymentService.getTransaction(transactionId);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @Operation(summary = "Get All Transactions of Card", description = "Endpoint to get all the transactions of card")
    @GetMapping("/getAllTransactionsOfCard/{cardNumber}")
    public ResponseEntity<List<Payment>> getAllTransactionOfCard(@PathVariable("cardNumber") String cardNumber) {
        List<Payment> allTransactionsOfCard = paymentService.getAllTransactionsOfCard(cardNumber);
        return new ResponseEntity<>(allTransactionsOfCard, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Transactions of Customer",
            description = "Endpoint to get all the transactions of Customer")
    @GetMapping("/getAllTransactionsOfCustomer/{customerId}")
    public ResponseEntity<List<Payment>> getAllTransactionsOfCustomer(@PathVariable("customerId") String customerId) {
        List<Payment> allTransactionsOfCustomer = paymentService.getAllTransactionsOfCustomer(customerId);
        return new ResponseEntity<>(allTransactionsOfCustomer, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Specific Transactions of Card",
            description = "Endpoint to get all the specific transactions of card")
    @GetMapping("/getAllSpecificTransactionsOfCard/{cardNumber}")
    public ResponseEntity<List<Payment>> getAllSpecificTransactionsOfCard(
            @PathVariable("cardNumber") String cardNumber, @Valid @RequestBody DateFilter dateFilter) {
        List<Payment> allSpecificTransactionsOfCard =
                paymentService.getAllSpecificTransactionsOfCard(cardNumber, dateFilter);
        return new ResponseEntity<>(allSpecificTransactionsOfCard, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Specific Transactions of Customer",
            description = "Endpoint to get all the specific transaction of Customer")
    @GetMapping("/getAllSpecificTransactionsOfCustomer/{customerId}")
    public ResponseEntity<List<Payment>> getAllSpecificTransactionsOfCustomer(
            @PathVariable("customerId") String customerId, @Valid @RequestBody DateFilter dateFilter) {
        List<Payment> allSpecificTransactionsOfCustomer =
                paymentService.getAllSpecificTransactionsOfCustomer(customerId, dateFilter);
        return new ResponseEntity<>(allSpecificTransactionsOfCustomer, HttpStatus.OK);
    }
}
