package com.bbsk.banchanshop.cart.dto;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.user.entity.UserEntity;
import lombok.*;

@Getter
@AllArgsConstructor
@ToString
public class InsertCartDto {

    private Long banchanId;
    private int quantity;

}
