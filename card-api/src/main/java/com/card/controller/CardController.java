package com.card.controller;

import com.card.dto.UpdateCard;
import com.card.model.Card;
import com.card.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("card")
@Tag(name = "Card Controller", description = "Controller for Card Operations")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @Operation(summary = "Save Card", description = "Endpoint to save a new card")
    @PostMapping("/save/{customerId}")
    public ResponseEntity<Card> saveCard(@PathVariable("customerId") String customerId, @Valid @RequestBody Card card) {
        Card savedCard = cardService.saveCard(customerId, card);
        return new ResponseEntity<>(savedCard, HttpStatus.OK);
    }

    @Operation(summary = "Get Card", description = "Endpoint to get a existing card")
    @GetMapping("/get/{cardNumber}")
    public ResponseEntity<Card> getCard(@PathVariable("cardNumber") String cardNumber) {
        Card card = cardService.getCard(cardNumber);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @Operation(summary = "Update Card", description = "Endpoint to update a existing card")
    @PatchMapping("/update/{cardNumber}")
    public ResponseEntity<Card> updateCard(
            @PathVariable("cardNumber") String cardNumber, @Valid @RequestBody UpdateCard updateCard) {
        Card card = cardService.updateCard(cardNumber, updateCard);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @Operation(summary = "Delete Card", description = "Endpoint to delete a card")
    @DeleteMapping("/delete/{cardNumber}")
    public ResponseEntity<String> deleteCard(@PathVariable("cardNumber") String cardNumber) {
        String deletedCard = cardService.deleteCard(cardNumber);
        return new ResponseEntity<>(deletedCard, HttpStatus.OK);
    }
}
