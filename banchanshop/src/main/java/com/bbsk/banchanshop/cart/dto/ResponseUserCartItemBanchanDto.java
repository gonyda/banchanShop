package com.bbsk.banchanshop.cart.dto;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseUserCartItemBanchanDto {

    private Long banchanId; // 반찬 아이디
    private String banchanName; // 반찬이름
    private int banchanPrice; // 반찬가격

    public ResponseUserCartItemBanchanDto(BanchanEntity banchan) {
        this.banchanId = banchan.getBanchanId();
        this.banchanName = banchan.getBanchanName();
        this.banchanPrice = banchan.getBanchanPrice();
    }
}
