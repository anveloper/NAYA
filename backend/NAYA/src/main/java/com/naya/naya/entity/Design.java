package com.naya.naya.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.naya.naya.dto.DesignDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Design {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "design_id")
    private Long designId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "naya_card_id")
    private NayaCard nayaCardId;

    @Column(name = "column_no")
    private int columnNo;

    @Column(name = "text_size")
    private int textSize;

    @Column(name = "text_font")
    private String textFont;

    @Column(name = "text_row")
    private double textRow;

    @Column(name = "text_col")
    private double textCol;

    @Column(name = "text_color_r")
    private int textColorR;

    @Column(name = "text_color_g")
    private int textColorG;

    @Column(name = "text_color_b")
    private int textColorB;

    public static Design create(Long designId) {
        if(ObjectUtils.isEmpty(designId)) return null;
        Design design = new Design();
        design.designId = designId;

        return design;
    }

    public static Design create(DesignDto dto) {
        if(ObjectUtils.isEmpty(dto)) return null;
        Design design = new Design();
//        design.designId = dto.getDesignId();
        design.nayaCardId = NayaCard.create(dto.getNayaCardId());
        design.columnNo = dto.getColumnNo();
        design.textSize = dto.getTextSize();
        design.textFont = dto.getTextFont();
        design.textRow = dto.getTextRow();
        design.textCol = dto.getTextCol();
        design.textColorR = dto.getTextColorG();
        design.textColorG = dto.getTextColorG();
        design.textColorB = dto.getTextColorB();

        return design;
    }

    public static Design create(DesignDto dto, int columnNo) {
        if(ObjectUtils.isEmpty(dto)) return null;
        Design design = new Design();
//        design.designId = dto.getDesignId();
        design.nayaCardId = NayaCard.create(dto.getNayaCardId());
        design.columnNo = columnNo;
        design.textSize = dto.getTextSize();
        design.textFont = dto.getTextFont();
        design.textRow = dto.getTextRow();
        design.textCol = dto.getTextCol();
        design.textColorR = dto.getTextColorG();
        design.textColorG = dto.getTextColorG();
        design.textColorB = dto.getTextColorB();

        return design;
    }

    public void update(DesignDto dto){
        this.columnNo = dto.getColumnNo();
        this.textSize = dto.getTextSize();
        this.textFont = dto.getTextFont();
        this.textRow = dto.getTextRow();
        this.textCol = dto.getTextCol();
        this.textColorR = dto.getTextColorR();
        this.textColorG = dto.getTextColorG();
        this.textColorB = dto.getTextColorB();

//        return this;
    }
}
