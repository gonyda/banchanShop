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

    private String name; // 주문자 이름
    private String address; // 배송지
    private String email; // 주문자 이메일
    private String phoneNumber; // 주문자 전화번호
    private int totalPrice; // 총 주문금액

    private PaymentType paymentType; // 결제방법

    private OrderType orderType; // 주문종류
    private List<RequestOrderOptionDto> requestOrderOptionDto; // 주문 옵션

    public void initOrderOption() {
        requestOrderOptionDto.forEach(RequestOrderOptionDto::setInit);
    }

}
