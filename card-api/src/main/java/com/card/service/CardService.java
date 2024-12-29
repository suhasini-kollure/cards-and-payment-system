package com.card.service;

import com.card.dto.UpdateCard;
import com.card.model.Card;

public interface CardService {

    Card saveCard(String customerId, Card card);

    Card getCard(String cardNumber);

    Card updateCard(String cardNumber, UpdateCard updateCard);

    String deleteCard(String cardNumber);
}
