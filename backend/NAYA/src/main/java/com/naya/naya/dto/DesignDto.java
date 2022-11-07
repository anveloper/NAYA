package com.naya.naya.dto;

import com.naya.naya.dto.Request.DesignRqDto;
import com.naya.naya.entity.Design;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class DesignDto {

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

    public static DesignDto from(Long designId){
        DesignDto dto = new DesignDto();
        dto.designId = designId;

        return dto;
    }

    public static DesignDto from(DesignRqDto dto){
        DesignDto designDto = new DesignDto();
        designDto.designId = dto.getDesignId();
        designDto.nayaCardId = dto.getNayaCardId();
        designDto.columnNo = dto.getColumnNo();
        designDto.textFont = dto.getTextFont();
        designDto.textSize = dto.getTextSize();
        designDto.textRow = dto.getTextRow();
        designDto.textCol = dto.getTextCol();
        designDto.textColorR = dto.getTextColorR();
        designDto.textColorG = dto.getTextColorG();
        designDto.textColorB = dto.getTextColorB();

        return designDto;
    }

    public static DesignDto from(Design entity){
        DesignDto designDto = new DesignDto();
        designDto.designId = entity.getDesignId();
        designDto.nayaCardId = entity.getNayaCardId().getNayaCardId();
        designDto.columnNo = entity.getColumnNo();
        designDto.textFont = entity.getTextFont();
        designDto.textSize = entity.getTextSize();
        designDto.textRow = entity.getTextRow();
        designDto.textCol = entity.getTextCol();
        designDto.textColorR = entity.getTextColorR();
        designDto.textColorG = entity.getTextColorG();
        designDto.textColorB = entity.getTextColorB();

        return designDto;
    }

    public static DesignDto of(Long nayaCardId, int columnNo, String textFont, int textSize,
                               int textColorR, int textColorG, int textColorB, double textRow, double textCol){
        DesignDto dto = new DesignDto();
        dto.nayaCardId = nayaCardId;
        dto.columnNo = columnNo;
        dto.textFont = textFont;
        dto.textSize = textSize;
        dto.textColorR = textColorR;
        dto.textColorG = textColorG;
        dto.textColorB = textColorB;
        dto.textRow = textRow;
        dto.textCol = textCol;

        return dto;
    }



//    public DesignDto(Design design){
//        this.designId = design.getDesignId();
//        this.nayaCardId = design.getNayaCardId().getNayaCardId();
//        this.columnNo = design.getColumnNo();
//        this.textSize = design.getTextSize();
//        this.textFont = design.getTextFont();
//        this.textRow = design.getTextRow();
//        this.textCol = design.getTextCol();
//        this.textColorR = design.getTextColorR();
//        this.textColorG = design.getTextColorG();
//        this.textColorB = design.getTextColorB();
//    }
//
//    public Design toEntity(){
//
//        return Design.builder()
//                .nayaCardId(new NayaCard(nayaCardId))
//                .columnNo(columnNo)
//                .textSize(textSize)
//                .textFont(textFont)
//                .textRow(textRow)
//                .textCol(textCol)
//                .textColorR(textColorR)
//                .textColorG(textColorG)
//                .textColorB(textColorB)
//                .build();
//    }
}
