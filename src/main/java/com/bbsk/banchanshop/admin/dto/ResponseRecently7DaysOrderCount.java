package com.bbsk.banchanshop.admin.dto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseRecently7DaysOrderCount {

    private String day;
    private Long orderCount;
}
