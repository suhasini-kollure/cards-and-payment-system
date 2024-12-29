package com.card.service;

import com.card.dto.UpdateCard;
import com.card.model.Card;
import com.card.model.Customer;
import com.card.repository.CardRepository;
import jakarta.ws.rs.NotFoundException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, RestTemplate restTemplate) {
        this.cardRepository = cardRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Card saveCard(String customerId, Card card) {
        if (cardRepository.existsById(card.getCardNumber())) {
            log.error("Card already exists with cardNumber : {}", card.getCardNumber());
            throw new DataIntegrityViolationException(
                    String.format("Card already exists with cardNumber : %s", card.getCardNumber()));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> body = new HttpEntity<>(headers);

        ResponseEntity<Customer> customer = restTemplate.exchange(
                "http://localhost:8081/customer/getCustomerById/" + customerId, HttpMethod.GET, body, Customer.class);

        log.info("Setting customer details to card and saving the card to the repository.");
        card.setCustomer(customer.getBody());
        Card savedCard = cardRepository.save(card);
        log.info("Card saved successfully with cardNumber : {}", savedCard.getCardNumber());
        return savedCard;
    }

    @Override
    public Card getCard(String cardNumber) {
        Optional<Card> optionalCard = cardRepository.findById(cardNumber);
        if (optionalCard.isPresent()) {
            log.info("Card found with cardNumber : {}", cardNumber);
            return optionalCard.get();
        } else {
            throw new NotFoundException(String.format("Card not found with CardNumber : %s", cardNumber));
        }
    }

    @Override
    public Card updateCard(String cardNumber, UpdateCard updateCard) {
        Card card = getCard(cardNumber);

        if (updateCard.getCardType() != null) {
            card.setCardType(updateCard.getCardType());
        }
        if (updateCard.getNameOnCard() != null) {
            card.setNameOnCard(updateCard.getNameOnCard());
        }
        if (updateCard.getCvv() != null) {
            card.setCvv(updateCard.getCvv());
        }
        if (updateCard.getExpirationDate() != null) {
            card.setExpirationDate(updateCard.getExpirationDate());
        }
        Card savedCard = cardRepository.save(card);
        log.info("Card updated for cardNumber : {}", savedCard.getCardNumber());
        return savedCard;
    }

    @Override
    public String deleteCard(String cardNumber) {
        Card card = getCard(cardNumber);
        cardRepository.delete(card);
        log.info("Card deleted with cardNumber : {}", card.getCardNumber());
        return "Card deleted with card number : " + card.getCardNumber();
    }
}
