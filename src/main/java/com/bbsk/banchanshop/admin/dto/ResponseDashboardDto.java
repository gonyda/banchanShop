package com.bbsk.banchanshop.admin.dto;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.querydsl.core.Tuple;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class ResponseDashboardDto {

    private Long todayOrderCount; // 당일 주문건수

    private List<ResponseRecently7DaysOrderCount> recently7DaysOrderCount = new ArrayList<>(); // 최근 7일 주문건수
    private List<ResponseRecently6MonthsOrderCount> recently6MonthsOrderCount = new ArrayList<>(); // 최근 6개월 주문건수

    private List<ResponseBanchanInfoDto> banchanInfoDtoList = new ArrayList<>();

    /**
     * 당일 주문건수
     * @param orderCount
     */
    public void updateTodayOrderCount(Long orderCount) {
        this.todayOrderCount = orderCount;
    }

    /**
     * 최근 7일 주문건수
     * @param list
     */
    public void updateRecently7DaysOrderCount(List<Tuple> list) {
        for (Tuple e : list) {

            recently7DaysOrderCount.add(new ResponseRecently7DaysOrderCount(e.get(0, String.class), e.get(1, Long.class)));
        }
    }

    public void updateRecently6MonthsOrderCount(List<Tuple> list) {
        for (Tuple e : list) {
            recently6MonthsOrderCount.add(new ResponseRecently6MonthsOrderCount(e.get(0, String.class), e.get(1, Long.class)));
        }
    }

    public void updateBanchanInfoDtoList(List<BanchanEntity> list) {
        for (BanchanEntity banchan : list) {
            banchanInfoDtoList.add(
                ResponseBanchanInfoDto.builder()
                        .name(banchan.getBanchanName())
                        .quantity(banchan.getBanchanStockQuantity())
                        .createDate(banchan.getCreateDate())
                        .expirationDate(banchan.getExpirationDate())
                        .build()
            );
        }
    }
}
