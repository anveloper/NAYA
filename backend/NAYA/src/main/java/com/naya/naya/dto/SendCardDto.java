package com.naya.naya.dto;

import com.naya.naya.dto.Request.SendCardRequestDto;
import com.naya.naya.entity.SendCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendCardDto {

    private Long sendCardId;
    private String userId;
    private String cardUrl;
    private String sendDatetime;
    private String expiredDatetime;

    public SendCardDto from(SendCardRequestDto dto){
        SendCardDto sendCardDto=new SendCardDto();
        sendCardDto.sendCardId=dto.getSendCardId();
        sendCardDto.userId=dto.getUserId();
        sendCardDto.sendDatetime=dto.getSendDatetime();
        sendCardDto.expiredDatetime=dto.getExpiredDatetime();
        return sendCardDto;
    }

    public SendCard toEntity(){
        return SendCard.builder()
                .sendCardId(sendCardId).userId(userId).cardUrl(cardUrl)
                .sendDatetime(sendDatetime).expiredDatetime(expiredDatetime)
                .build();
    }
}
