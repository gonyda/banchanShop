package com.bbsk.banchanshop.payment.service;

import com.bbsk.banchanshop.payment.dto.CardStrategy;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public void startPayToOrder(CardStrategy card) {
        card.startPayToOrder(card);
    }
}
