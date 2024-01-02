package com.bbsk.banchanshop.admin.dto;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResponseBanchanDto {

    private Long id;
    private String name;
    private int quantity;
    private int price;
    private LocalDateTime createDate;
    private LocalDateTime expirationDate;

    public ResponseBanchanDto(BanchanEntity banchan) {
        this.id = banchan.getBanchanId();
        this.name = banchan.getBanchanName();
        this.quantity = banchan.getBanchanStockQuantity();
        this.price = banchan.getBanchanPrice();
        this.createDate = banchan.getCreateDate();
        this.expirationDate = banchan.getExpirationDate();
    }
}
