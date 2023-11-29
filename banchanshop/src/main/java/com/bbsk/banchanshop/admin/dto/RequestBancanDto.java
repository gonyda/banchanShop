package com.bbsk.banchanshop.admin.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class RequestBancanDto {

    private String name;
    private int quantity;
    private int price;
    private LocalDateTime createDate;
    private LocalDateTime expirationDate;
    private List<RequestBanchanIngredienDto> ingredientList;
}
