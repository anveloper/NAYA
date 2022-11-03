package com.naya.naya.service;

import com.naya.naya.dto.UserDto;

public interface UserService {

    UserDto save(UserDto dto);

    UserDto findByEmail(String email);
}
