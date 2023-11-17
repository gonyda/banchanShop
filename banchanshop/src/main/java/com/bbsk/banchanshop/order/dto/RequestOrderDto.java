package com.bbsk.banchanshop.order.dto;

import com.bbsk.banchanshop.contant.PaymentType;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
public class RequestOrderDto {

    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private PaymentType paymentType;
}
