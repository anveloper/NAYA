package com.naya.naya.dto.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendCardRequestDto {

    private Long sendCardId;
    private Long userId;
    private String cardUrl;
    private String sendDatetime;
    private String expiredDatetime;
}
