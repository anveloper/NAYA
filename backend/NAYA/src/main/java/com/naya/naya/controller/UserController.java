package com.naya.naya.controller;

import com.naya.naya.dto.Request.UserRequestDto;
import com.naya.naya.dto.UserDto;
import com.naya.naya.entity.User;
import com.naya.naya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Controller
@ResponseBody
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping
    public UserDto save(@RequestBody UserRequestDto dto){
        System.out.println("controller: "+dto.getEmail());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(System.currentTimeMillis());
        UserDto user=UserDto.builder().userId(dto.getUserId()).email(dto.getEmail()).joinDate(nowTime).build();
        return userService.save(user);
    }

    @PostMapping("/login")
    public UserDto findByEmail(@RequestBody UserRequestDto dto){
        System.out.println("controller: "+dto.getEmail());
        String email=dto.getEmail();
        return userService.findByEmail(email);
    }
}
