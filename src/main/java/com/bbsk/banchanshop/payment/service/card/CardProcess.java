package com.bbsk.banchanshop.payment.service.card;

/**
 * 카드결제 알고리즘 구조
 */
public abstract class CardProcess {

    /**
     * 결제 프로세스 순서
     * @param cardNumber
     * @param cardCVC
     * @return
     */
    public boolean processPay(Long cardNumber, int cardCVC) {
        if (cardNumberAndCardCvcCheck(cardNumber, cardCVC)) {
            if (balanceCheck()) {
                return requestPay();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 카드번호와 CVC를 카드사에게 넘겨 유효성 체크
     * @param cardNumber
     * @param cardCvs
     * @return
     */
    protected abstract boolean cardNumberAndCardCvcCheck(Long cardNumber, int cardCvs);

    /**
     * 결제가능한 잔액이 있는지 카드사에게 확인 요청
     * @return
     */
    protected abstract boolean balanceCheck();

    /**
     * 카드사에게 결제요청
     * @return
     */
    protected abstract boolean requestPay();
}
