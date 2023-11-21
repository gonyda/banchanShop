package com.bbsk.banchanshop.payment.service.card;

import com.bbsk.banchanshop.contant.CardCompany;
import com.bbsk.banchanshop.payment.service.card.CardProcess;
import com.bbsk.banchanshop.payment.service.card.CardStrategy;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ShinhanCard extends CardProcess implements CardStrategy {

    private Long cardNumber;
    private int cardPw;
    private int cardCvc;
    private CardCompany cardCompany;

    /**
     * 카드번호와 CVC를 카드사에게 넘겨 유효성 체크
     * @param cardNumber
     * @param cardCvs
     * @return
     */
    @Override
    protected boolean cardNumberAndCardCvcCheck(Long cardNumber, int cardCvs) {
        System.out.println("카드번호 : " + cardNumber + " / " + "CVC : " + cardCvs);
        System.out.println(cardCompany + " - 카드번호, CVC 체크");
        return true;
    }

    /**
     * 결제가능한 잔액이 있는지 카드사에게 확인 요청
     * @return
     */
    @Override
    protected boolean balanceCheck() {
        System.out.println(cardCompany + " - 잔액체크 통과");
        return true;
    }

    /**
     * 카드사에게 결제요청
     * @return
     */
    @Override
    protected boolean requestPay() {
        System.out.println(cardCompany + " - 결제요청");
        return true;
    }

    /**
     * 결제 프로세스 진행 후 결제가 완료되면 주문테이블 INSERT(주문완료)
     *
     * @return
     */
    @Override
    public boolean startPayProcess() {
        if(this.processPay(this.cardNumber, this.cardCvc)) {
            System.out.println(cardCompany + " - 결제가 완료되었습니다.");
            return true;
        }
        return false;
    }
}
