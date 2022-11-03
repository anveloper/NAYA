package com.naya.naya.dto;

import com.naya.naya.dto.Request.UserRequestDto;
import com.naya.naya.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String email;
    private String joinDate;

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
}
