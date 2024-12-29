package com.card.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.Data;

@Data
public class UpdateCard {

    @Pattern(regexp = "Visa|Master Card|Amex", message = "Card type must be either Visa, Master Card, or Amex.")
    private String cardType;

    @Future(message = "Expiration date must be in the future.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;

    @Pattern(
            regexp = "^[a-zA-Z ]{2,20}$",
            message = "Name must only contain letters and be between 2 and 20 characters.")
    private String nameOnCard;

    @Pattern(regexp = "\\d{3}", message = "CVV number must be exactly 3 digits.")
    private String cvv;
}
