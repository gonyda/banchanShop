package com.bbsk.banchanshop.payment.service.card;

import com.bbsk.banchanshop.contant.CardCompany;
import com.bbsk.banchanshop.payment.service.PaymentStrategy;
import com.bbsk.banchanshop.payment.service.card.CardProcess;
import com.bbsk.banchanshop.payment.service.card.CardStrategy;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class KakaoCard extends CardProcess implements CardStrategy {

    /*
     * 1. 카드번호 및 CVC 유효성 검사 (카카오페이 API 호출)
     * 2. 카드잔액 확인 (카카오페이 API 호출)
     * 3. 결제 요청 (카카오페이 API 호출)
     * 4. 주문 생성
     * */

    private Long cardNumber;
    private int cardCvc;
    private final CardCompany cardCompany = CardCompany.KAKAOPAY;

    private String userName;
    private String userPhoneNumber;
    private String userAddress;
    private String requestCompany;

    /**
     * 카드번호와 CVC를 카드사에게 넘겨 유효성 체크
     * @param cardNumber
     * @param cardCvs
     * @return
     */
    @Override
    protected boolean cardNumberAndCardCvcCheck(Long cardNumber, int cardCvs) {
        System.out.println("카드번호 : " + cardNumber + " / " + "CVC : " + cardCvs);
        System.out.println("카카오페이 - 카드번호, CVC 체크");
        return true;
    }

    /**
     * 결제가능한 잔액이 있는지 카드사에게 확인 요청
     * @return
     */
    @Override
    protected boolean balanceCheck() {
        System.out.println("카카오페이 - 잔액체크 통과");
        return true;
    }

    /**
     * 카드사에게 결제요청
     * @return
     */
    @Override
    protected boolean requestPay() {
        System.out.println("카카오페이 - 결제요청");
        return true;
    }

    @Override
    public boolean startPayProcess() {
        if(this.processPay(cardNumber, cardCvc)) {
            System.out.println("카카오페이 - 결제가 완료되었습니다.");
            return true;
        }
        return false;
    }
}
