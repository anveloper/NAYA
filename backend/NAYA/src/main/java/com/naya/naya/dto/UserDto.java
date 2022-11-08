package com.naya.naya.dto;

import com.naya.naya.dto.Request.UserRequestDto;
import com.naya.naya.dto.Request.UserRqDto;
import com.naya.naya.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String userId;
    private String email;
    private String joinDate;
    private List<NayaCardDto> cardList = new ArrayList<>();

    public UserDto from(UserRequestDto dto){
        UserDto userDto=new UserDto();
        userDto.userId= dto.getUserId();
        userDto.email=dto.getEmail();
        userDto.joinDate=dto.getJoinDate();
        return userDto;
    }

    public User toEntity(){
        return User.builder().userId(userId).email(email).joinDate(joinDate).build();
    }

    public static UserDto from(String userId){
        UserDto userDto = new UserDto();
        userDto.userId = userId;

        return userDto;
    }

    public static UserDto from(UserRqDto dto){
        UserDto userDto = new UserDto();
        userDto.userId = dto.getUserId();
        userDto.joinDate = dto.getJoinDate();
        userDto.email = dto.getEmail();
        return userDto;
    }

    public static UserDto from(User entity){
        UserDto userDto = new UserDto();
        userDto.userId = entity.getUserId();
        userDto.joinDate = entity.getJoinDate();
        userDto.email = entity.getEmail();
        return userDto;
    }
}
