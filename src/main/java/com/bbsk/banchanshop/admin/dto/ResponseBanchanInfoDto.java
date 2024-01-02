package com.bbsk.banchanshop.admin.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResponseBanchanInfoDto {

    private String name;
    private int quantity; // 재고수량
    private LocalDateTime createDate; // 요리일
    private LocalDateTime expirationDate; // 유통기한
}
