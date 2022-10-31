package com.naya.naya.controller;

import com.naya.naya.dto.Request.UserRequestDto;
import com.naya.naya.dto.UserDto;
import com.naya.naya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;

@Controller
@ResponseBody
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping
    public UserDto save(@RequestBody UserRequestDto dto){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(System.currentTimeMillis());
        UserDto user=UserDto.builder().userId(dto.getUserId()).email(dto.getEmail()).joinDate(nowTime).build();
        return userService.save(user);
    }
}
