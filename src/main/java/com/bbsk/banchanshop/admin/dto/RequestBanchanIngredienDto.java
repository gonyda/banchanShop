package com.bbsk.banchanshop.admin.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class RequestBanchanIngredienDto {
    private String ingredientName;
    private int ingredientQuantity;
    private LocalDateTime ingredientInputDate;
    private LocalDateTime ingredientExpirationDate;
}
