package com.naya.naya.dto.Request;

import com.naya.naya.dto.DesignDto;
import com.naya.naya.dto.NayaCardDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NayaCardRsDto {
    private Long nayaCardId;
    private String userId;
//    private User user;
    private String name;
    private String kind;
    private String email;
    private String mobile;
    private String address;
    private String company;
    private String team;
    private String role;
    private String background;
    private String logo;
    private String fax;
    private String tel;
    private String memo1;
    private String memo2;
    private String memo3;
    private String memoContent;
//    private List<DesignRqDto> designList = new ArrayList<>();
    private List<DesignDto> designList = new ArrayList<>();

    public static NayaCardRsDto of(NayaCardDto nayaCardDto, List<DesignDto> designList){
        NayaCardRsDto dto = new NayaCardRsDto();
        dto.nayaCardId = nayaCardDto.getNayaCardId();
//        dto.user = entity.getUserId();
        dto.userId = nayaCardDto.getUserId();
        dto.name = nayaCardDto.getName();
        dto.kind = nayaCardDto.getKind();
        dto.email = nayaCardDto.getEmail();
        dto.mobile = nayaCardDto.getMobile();
        dto.address = nayaCardDto.getAddress();
        dto.company = nayaCardDto.getCompany();
        dto.team = nayaCardDto.getTeam();
        dto.role = nayaCardDto.getRole();
        dto.background = nayaCardDto.getBackground();
        dto.logo = nayaCardDto.getLogo();
        dto.fax = nayaCardDto.getFax();
        dto.tel = nayaCardDto.getTel();
        dto.memo1 = nayaCardDto.getMemo1();
        dto.memo2 = nayaCardDto.getMemo2();
        dto.memo3 = nayaCardDto.getMemo3();
        dto.memoContent = nayaCardDto.getMemoContent();
        dto.designList = designList;
        return dto;
    }

    // of, 파라미터 list
}
