package com.bbsk.banchanshop.order.dto;

import com.bbsk.banchanshop.contant.OrderOption;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class OrderOptionDto {

    private LocalDateTime pickUp;

    @Enumerated(value = EnumType.STRING)
    private OrderOption amount;

    @Enumerated(value = EnumType.STRING)
    private OrderOption spicy;
}
