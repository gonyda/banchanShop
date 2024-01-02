package com.bbsk.banchanshop.order.dto;

import com.bbsk.banchanshop.contant.OrderType;
import com.bbsk.banchanshop.contant.PaymentType;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CreateOrderDto {
    private String userId;
    private PaymentType paymentType;
    private String paymentCompanyName;
    private OrderType orderType;
    private List<RequestOrderOptionDto> requestOrderOptionDtoList;
}
