package com.naya.naya.service;

import com.naya.naya.dto.UserDto;
import com.naya.naya.entity.User;
import com.naya.naya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDto save(UserDto dto) {
        User res=userRepository.saveAndFlush(dto.toEntity());
        return UserDto.builder().userId(res.getUserId()).email(res.getEmail()).joinDate(res.getJoinDate()).build();
    }
}
