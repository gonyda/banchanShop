package com.bbsk.banchanshop.banchan.dto;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ResponseBanchanDto {

    private int banchanStockQuantity;
    private int banchanPrice;
    private String banchanName;
    private Long banchanId;
    private LocalDateTime createDate;

    /**
     * 반찬 리스트 페이지
     * @param banchanId
     * @param banchanName
     * @param banchanPrice
     * @param banchanStockQuantity
     */
    public ResponseBanchanDto(Long banchanId, String banchanName, int banchanPrice, int banchanStockQuantity) {
        this.banchanId = banchanId;
        this.banchanName = banchanName;
        this.banchanPrice = banchanPrice;
        this.banchanStockQuantity = banchanStockQuantity;
    }

    /**
     * 반찬 상세페이지
     * @param banchan
     */
    public ResponseBanchanDto(BanchanEntity banchan) {
        this.banchanId = banchan.getBanchanId();
        this.banchanName = banchan.getBanchanName();
        this.banchanPrice = banchan.getBanchanPrice();
        this.banchanStockQuantity = banchan.getBanchanStockQuantity();
        this.createDate = banchan.getCreateDate();
    }
}
