package com.naya.naya.controller;

import com.naya.naya.dto.Request.UserRequestDto;
import com.naya.naya.dto.UserDto;
import com.naya.naya.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;

@Controller
@ResponseBody
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto save(@RequestBody UserRequestDto dto){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(System.currentTimeMillis());
        UserDto user=UserDto.builder().userId(dto.getUserId()).email(dto.getEmail()).joinDate(nowTime).build();

        log.debug("usercontroller save method, parameter UserRequestDto, email: " + dto.getEmail());

        return userService.save(user);
    }

    @PostMapping("/login")
    public UserDto findByEmail(@RequestBody UserRequestDto dto){
        String email=dto.getEmail();

        log.debug("usercontroller findByEmail method, parameter UserRequestDto, email: " + dto.getEmail());

        return userService.findByEmail(email);
    }
}
