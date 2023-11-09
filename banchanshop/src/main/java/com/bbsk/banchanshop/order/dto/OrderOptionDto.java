package com.bbsk.banchanshop.order.dto;

import com.bbsk.banchanshop.contant.OrderOption;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderOptionDto {
    private Long orderItemId;
    private OrderOption optionAmount;
    private OrderOption optionSpicy;
    private LocalDateTime optionPickUp;
}
