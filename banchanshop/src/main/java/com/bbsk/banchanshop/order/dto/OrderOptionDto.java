package com.bbsk.banchanshop.order.dto;

import com.bbsk.banchanshop.contant.OrderOption;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

@Embeddable
public class OrderOptionDto {

    private LocalDate pickUp;

    @Enumerated(value = EnumType.STRING)
    private OrderOption amount;

    @Enumerated(value = EnumType.STRING)
    private OrderOption spicy;

}
