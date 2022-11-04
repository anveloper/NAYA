package com.naya.naya.dto;

import com.naya.naya.dto.Request.NayaCardRqDto2;
import com.naya.naya.entity.NayaCard;
import com.naya.naya.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class NayaCardDto {

    private Long nayaCardId;
    private Long userId;
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

    private List<DesignDto> designList = new ArrayList<>();

    public static NayaCardDto from(Long cardId){
        NayaCardDto nayaCardDto = new NayaCardDto();
        nayaCardDto.nayaCardId = cardId;

        return nayaCardDto;
    }

//    public static NayaCardDto from(NayaCardRqDto dto) {
//        NayaCardDto nayaCardDto = new NayaCardDto();
//        nayaCardDto.nayaCardId = dto.getNayaCardId();
////        nayaCardDto.user = User.create(dto.getUserId());
//        nayaCardDto.userId = User.create(dto.getUserId()).getUserId();
//        nayaCardDto.name = dto.getName();
//        nayaCardDto.kind = dto.getKind();
//        nayaCardDto.email = dto.getEmail();
//        nayaCardDto.mobile = dto.getMobile();
//        nayaCardDto.address = dto.getAddress();
//        nayaCardDto.company = dto.getCompany();
//        nayaCardDto.team = dto.getTeam();
//        nayaCardDto.role = dto.getRole();
//        nayaCardDto.background = dto.getBackground();
//        nayaCardDto.logo = dto.getLogo();
//        nayaCardDto.fax = dto.getFax();
//        nayaCardDto.tel = dto.getTel();
//        nayaCardDto.memo1 = dto.getMemo1();
//        nayaCardDto.memo2 = dto.getMemo2();
//        nayaCardDto.memo3 = dto.getMemo3();
//        nayaCardDto.memoContent = dto.getMemoContent();
//
//        return nayaCardDto;
//    }

    public static NayaCardDto from(NayaCardRqDto2 dto) {
//        NayaCardDto nayaCardDto = dto.getNayaCardDto();
        NayaCardDto nayaCardDto = new NayaCardDto();
        nayaCardDto.nayaCardId = dto.getNayaCardId();
//        nayaCardDto.user = entity.getUserId();
        nayaCardDto.userId = dto.getUserId();
        nayaCardDto.name = dto.getName();
        nayaCardDto.kind = dto.getKind();
        nayaCardDto.email = dto.getEmail();
        nayaCardDto.mobile = dto.getMobile();
        nayaCardDto.address = dto.getAddress();
        nayaCardDto.company = dto.getCompany();
        nayaCardDto.team = dto.getTeam();
        nayaCardDto.role = dto.getRole();
        nayaCardDto.background = dto.getBackground();
        nayaCardDto.logo = dto.getLogo();
        nayaCardDto.fax = dto.getFax();
        nayaCardDto.tel = dto.getTel();
        nayaCardDto.memo1 = dto.getMemo1();
        nayaCardDto.memo2 = dto.getMemo2();
        nayaCardDto.memo3 = dto.getMemo3();
        nayaCardDto.memoContent = dto.getMemoContent();
        nayaCardDto.designList = dto.getDesignList();
        return nayaCardDto;
    }

    public static NayaCardDto from(NayaCard entity) {
        NayaCardDto nayaCardDto = new NayaCardDto();
        nayaCardDto.nayaCardId = entity.getNayaCardId();
//        nayaCardDto.user = entity.getUserId();
        nayaCardDto.userId = entity.getUserId().getUserId();
        nayaCardDto.name = entity.getName();
        nayaCardDto.kind = entity.getKind();
        nayaCardDto.email = entity.getEmail();
        nayaCardDto.mobile = entity.getMobile();
        nayaCardDto.address = entity.getAddress();
        nayaCardDto.company = entity.getCompany();
        nayaCardDto.team = entity.getTeam();
        nayaCardDto.role = entity.getRole();
        nayaCardDto.background = entity.getBackground();
        nayaCardDto.logo = entity.getLogo();
        nayaCardDto.fax = entity.getFax();
        nayaCardDto.tel = entity.getTel();
        nayaCardDto.memo1 = entity.getExtra1();
        nayaCardDto.memo2 = entity.getExtra2();
        nayaCardDto.memo3 = entity.getExtra3();
        nayaCardDto.memoContent = entity.getMemoContent();

        return nayaCardDto;
    }


    public static NayaCardDto of(NayaCard entity, List<DesignDto> designList){
        NayaCardDto dto = from(entity);
        dto.designList = designList;
        return dto;
    }

//    public static NayaCardDto of(NayaCardRqDto rqDto, List<DesignDto> designList){
//        NayaCardDto dto = from(rqDto);
//        dto.designList = designList;
//        return dto;
//    }



//    public NayaCardDto(NayaCard nayaCard) {
//        this.nayaCardId = nayaCard.getNayaCardId();
//        this.user = nayaCard.getUserId();
//        this.userId = nayaCard.getUserId().getUserId();
//        this.name = nayaCard.getName();
//        this.kind = nayaCard.getKind();
//        this.email = nayaCard.getEmail();
//        this.mobile = nayaCard.getMobile();
//        this.address = nayaCard.getAddress();
//        this.company = nayaCard.getCompany();
//        this.team = nayaCard.getTeam();
//        this.role = nayaCard.getRole();
//        this.background = nayaCard.getBackground();
//        this.logo = nayaCard.getLogo();
//        this.fax = nayaCard.getFax();
//        this.tel = nayaCard.getTel();
//        this.memo1 = nayaCard.getMemo1();
//        this.memo2 = nayaCard.getMemo2();
//        this.memo3 = nayaCard.getMemo3();
//        this.memoContent = nayaCard.getMemoContent();
//  }

//    public NayaCard toEntity(){
//        return NayaCard.builder()
//                .userId(new User(userId))
//                .name(name)
//                .kind(kind)
//                .email(email)
//                .mobile(mobile)
//                .address(address)
//                .company(company)
//                .team(team)
//                .role(role)
//                .background(background)
//                .logo(logo)
//                .fax(fax)
//                .tel(tel)
//                .memo1(memo1)
//                .memo2(memo2)
//                .memo3(memo3)
//                .memoContent(memoContent)
//                .build();
//    }
}
