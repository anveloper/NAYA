package com.naya.naya.controller;

import com.naya.naya.dto.Request.SendCardRequestDto;
import com.naya.naya.dto.SendCardDto;
import com.naya.naya.service.SendCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
@ResponseBody
@RequestMapping("/sendCard")
@RequiredArgsConstructor
@Slf4j
public class SendCardController {

    private final SendCardService sendCardService;

    @PostMapping
    public SendCardDto save(@RequestBody SendCardRequestDto dto){
        log.debug("sendCardController save method, parameter SendCardRequestDto, dto " + dto);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        String nowTime=sdf.format(System.currentTimeMillis());
        cal.add(Calendar.MINUTE, 10);
        String endTime=sdf.format(cal.getTime());
        SendCardDto sendCard= SendCardDto.builder()
                .sendCardId(dto.getSendCardId()).userId(dto.getUserId())
                .cardUrl(dto.getCardUrl()).sendDatetime(nowTime)
                .expiredDatetime(endTime).build();
        return sendCardService.save(sendCard);
    }

    @GetMapping
    public String findBySendCardId(@RequestParam("userId") String userId, @RequestParam("sendCardId") long sendCardId){
        log.debug("sendCardController findBySendCardId method, parameter String, userId "+userId+" / Long sendCardId " + sendCardId);
        return sendCardService.findByUserIdAndSendCardId(userId,sendCardId);
    }
}
