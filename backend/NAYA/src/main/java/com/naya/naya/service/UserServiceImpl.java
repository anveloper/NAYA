package com.naya.naya.service;

import com.naya.naya.dto.UserDto;
import com.naya.naya.entity.User;
import com.naya.naya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public UserDto save(UserDto dto) {

        log.debug("userService save method, parameter UserDto, dto " + dto);

        User res=userRepository.saveAndFlush(dto.toEntity());

        return UserDto.builder().userId(res.getUserId()).email(res.getEmail()).joinDate(res.getJoinDate()).build();
    }

    @Override
    public UserDto findByEmail(String email) {

        log.debug("userService findByEmail method, parameter String, email " + email);

        User res=userRepository.findByEmail(email);
        return UserDto.builder().userId(res.getUserId()).email(res.getEmail()).joinDate(res.getJoinDate()).build();
    }
}
