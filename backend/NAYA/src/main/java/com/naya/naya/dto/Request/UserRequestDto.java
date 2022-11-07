package com.naya.naya.dto.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String userId;
    private String email;
    private String joinDate;
}
