package com.naya.naya.dto.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DesignRqDto {
    private Long designId;
    private Long nayaCardId;
    private int columnNo;
    private int textSize;
    private String textFont;
    private double textRow;
    private double textCol;
    private int textColorR;
    private int textColorG;
    private int textColorB;
}
