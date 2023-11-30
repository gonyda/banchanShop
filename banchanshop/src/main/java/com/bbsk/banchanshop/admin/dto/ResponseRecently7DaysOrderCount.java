package com.bbsk.banchanshop.admin.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseRecently7DaysOrderCount {

    private String day;
    private Long orderCount;
}
