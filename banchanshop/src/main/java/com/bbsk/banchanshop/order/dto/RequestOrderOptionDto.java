package com.bbsk.banchanshop.order.dto;

import com.bbsk.banchanshop.contant.OrderOption;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
@Setter
public class RequestOrderOptionDto {

    private Long banchanId;
    private OrderOption amount;
    private OrderOption spicy;
    private LocalDateTime pickUp;

}
