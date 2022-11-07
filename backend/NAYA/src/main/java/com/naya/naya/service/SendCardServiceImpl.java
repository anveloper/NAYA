package com.naya.naya.service;

import com.naya.naya.dto.SendCardDto;
import com.naya.naya.entity.SendCard;
import com.naya.naya.repository.SendCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendCardServiceImpl implements SendCardService{

    private final SendCardRepository sendCardRepository;

    @Override
    public SendCardDto save(SendCardDto dto) {

        log.debug("sendService save method, parameter SendCardDto, dto " + dto);

        SendCard res=sendCardRepository.saveAndFlush(dto.toEntity());
        return SendCardDto.builder()
                .sendCardId(res.getSendCardId()).userId(res.getUserId())
                .cardUrl(res.getCardUrl()).sendDatetime(res.getSendDatetime())
                .expiredDatetime(res.getExpiredDatetime())
                .build();
    }

    @Override
    public String findBySendCardId(long sendCardId) {

        log.debug("sendService findBySendCardId method, parameter sendCardId, Long " + sendCardId);

        SendCard res=sendCardRepository.findBySendCardId(sendCardId);

        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String nowTime=dtf.format(LocalDateTime.now());
        String expired=res.getExpiredDatetime().replace(" ","T");
        if(LocalDateTime.parse(nowTime,dtf).isBefore(LocalDateTime.parse(expired,dtf)))
            return res.getCardUrl();
        return "링크가 만료되었습니다.";
    }
}
