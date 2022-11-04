package com.naya.naya.dto.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserRqDto {
    private Long userId;
    private String joinDate;
    private String email;
}
