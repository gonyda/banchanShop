package com.bbsk.banchanshop.admin.dto;

import com.bbsk.banchanshop.admin.mapping.Recently6MonthsOrderCountMapping;
import com.bbsk.banchanshop.admin.mapping.Recently7DaysOrderCountMapping;
import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.cart.dto.ResponseBanchanDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class ResponseDashboardDto {

    private int todayOrderCount; // 당일 주문건수

    private List<ResponseRecently7DaysOrderCount> recently7DaysOrderCount = new ArrayList<>(); // 최근 7일 주문건수
    private List<ResponseRecently6MonthsOrderCount> recently6MonthsOrderCount = new ArrayList<>(); // 최근 6개월 주문건수

    private List<ResponseBanchanInfoDto> banchanInfoDtoList = new ArrayList<>();

    /**
     * 당일 주문건수
     * @param orderCount
     */
    public void updateTodayOrderCount(int orderCount) {
        this.todayOrderCount = orderCount;
    }

    /**
     * 최근 7일 주문건수
     * @param list
     */
    public void updateRecently7DaysOrderCount(List<Recently7DaysOrderCountMapping> list) {
        for (Recently7DaysOrderCountMapping e : list) {
            recently7DaysOrderCount.add(new ResponseRecently7DaysOrderCount(e.getDay(), e.getOrderCount()));
        }
    }

    public void updateRecently6MonthsOrderCount(List<Recently6MonthsOrderCountMapping> list) {
        for (Recently6MonthsOrderCountMapping e : list) {
            recently6MonthsOrderCount.add(new ResponseRecently6MonthsOrderCount(e.getMonth(), e.getOrderCount()));
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
