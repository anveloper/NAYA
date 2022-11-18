package com.naya.naya.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapDto {

    private String x;
    private String y;
    private String roadAddress;
    private String jibunAddress;
}
