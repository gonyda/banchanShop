package com.bbsk.banchanshop.user.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class RequestUserDto {

    private String userId;
    private String userPw;
    private String name;
    private String userEmail;
    private String phoneNumber;
    private String address;

}
