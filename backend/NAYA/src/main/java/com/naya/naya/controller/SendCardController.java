package com.naya.naya.controller;

import com.naya.naya.dto.Request.SendCardRequestDto;
import com.naya.naya.dto.SendCardDto;
import com.naya.naya.service.SendCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
@ResponseBody
@RequestMapping("/sendCard")
@RequiredArgsConstructor
public class SendCardController {

    @Autowired
    private final SendCardService sendCardService;

    @PostMapping
    public SendCardDto save(@RequestBody SendCardRequestDto dto){
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
    public String findBySendCardId(@RequestParam("sendCardId") long sendCardId){
        return sendCardService.findBySendCardId(sendCardId);
    }
}
