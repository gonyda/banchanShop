package com.bbsk.banchanshop.admin.dto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseRecently6MonthsOrderCount {

    private String month;
    private int orderCount;
}
