package com.bbsk.banchanshop.order.dto;

import com.bbsk.banchanshop.contant.OrderType;
import com.bbsk.banchanshop.contant.PaymentType;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
@Setter
public class RequestOrderDto {

    private String name;
    private String address;
    private String email;
    private String phoneNumber;

    private PaymentType paymentType;
    private OrderType orderType;

    private List<RequestOrderOptionDto> requestOrderOptionDto;

}
