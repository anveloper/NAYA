package com.naya.naya.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.naya.naya.dto.NayaCardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NayaCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "naya_card_id")
    private Long nayaCardId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "name")
    private String name;

    @Column(name = "kind")
    private String kind;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "address")
    private String address;

    @Column(name = "company")
    private String company;

    @Column(name = "team")
    private String team;

    @Column(name = "role")
    private String role;

    @Column(name = "background")
    private String background;

    @Column(name = "logo")
    private String logo;

    @Column(name = "fax")
    private String fax;

    @Column(name = "tel")
    private String tel;

    @Column(name = "extra1")
    private String extra1;

    @Column(name = "extra2")
    private String extra2;

    @Column(name = "extra3")
    private String extra3;

    @Column(name = "memo_content")
    private String memoContent;

    @JsonManagedReference
    @OneToMany(mappedBy = "designId", cascade = CascadeType.PERSIST)
    private List<Design> designList = new ArrayList<>();


    public static NayaCard create(Long nayaCardId){
        if(ObjectUtils.isEmpty(nayaCardId)) return null;
        NayaCard nayaCard = new NayaCard();
        nayaCard.nayaCardId = nayaCardId;

        return nayaCard;
    }

    public static NayaCard create(NayaCardDto nayaCardDto){
        if(ObjectUtils.isEmpty(nayaCardDto)) return null;
        NayaCard nayaCard = new NayaCard();
        nayaCard.nayaCardId = nayaCardDto.getNayaCardId();
        nayaCard.userId = User.create(nayaCardDto.getUserId());
        nayaCard.name = nayaCardDto.getName();
        nayaCard.kind = nayaCardDto.getKind();
        nayaCard.email = nayaCardDto.getEmail();
        nayaCard.mobile = nayaCardDto.getMobile();
        nayaCard.address = nayaCardDto.getAddress();
        nayaCard.company = nayaCardDto.getCompany();
        nayaCard.team = nayaCardDto.getTeam();
        nayaCard.role = nayaCardDto.getRole();
        nayaCard.background = nayaCardDto.getBackground();
        nayaCard.logo = nayaCardDto.getLogo();
        nayaCard.fax = nayaCardDto.getFax();
        nayaCard.extra1 = nayaCardDto.getMemo1();
        nayaCard.extra2 = nayaCardDto.getMemo2();
        nayaCard.extra3 = nayaCardDto.getMemo3();
        nayaCard.memoContent = nayaCardDto.getMemoContent();

        return nayaCard;
    }
//    @Builder
//    public NayaCard update(Long nayaCardId, User user, String name, String kind, String email, String mobile, String address, String company, String team, String role, String background, String logo, String fax, String memo1, String memo2, String memo3, String memo_content){

//    public NayaCard(Long nayaCardId) {this.nayaCardId = nayaCardId; }

    public void update(NayaCardDto dto){
        this.nayaCardId = dto.getNayaCardId();
        this.userId = User.create(dto.getUserId());
        this.name = dto.getName();
        this.kind = dto.getKind();
        this.email = dto.getEmail();
        this.mobile = dto.getMobile();
        this.address = dto.getAddress();
        this.company = dto.getCompany();
        this.team = dto.getTeam();
        this.role = dto.getRole();
        this.background = dto.getBackground();
        this.logo = dto.getLogo();
        this.fax = dto.getFax();
        this.extra1 = dto.getMemo1();
        this.extra2 = dto.getMemo2();
        this.extra3 = dto.getMemo3();
        this.memoContent = dto.getMemoContent();
    }

//    public void update(NayaCardRqDto dto){
//        this.nayaCardId = dto.getNayaCardId();
//        this.userId = User.create(dto.getUserId());
//        this.name = dto.getName();
//        this.kind = dto.getKind();
//        this.email = dto.getEmail();
//        this.mobile = dto.getMobile();
//        this.address = dto.getAddress();
//        this.company = dto.getCompany();
//        this.team = dto.getTeam();
//        this.role = dto.getRole();
//        this.background = dto.getBackground();
//        this.logo = dto.getLogo();
//        this.fax = dto.getFax();
//        this.extra1 = dto.getMemo1();
//        this.extra2 = dto.getMemo2();
//        this.extra3 = dto.getMemo3();
//        this.memoContent = dto.getMemoContent();
//
////        return this;
//    }

    public void deleteDesign(Design design){

        // nayacard

        // entity -> dto -> setDesignList null -> entity
        System.out.println("this: " + this.getDesignList());
//        NayaCardDto nayaCardDto = NayaCardDto.from(this);
//        nayaCardDto.setDesignList(null);

        // design
        // entity -> dto -> setNaycard null -> entity

//        this.getDesignList().remove(design);
//        design
    }
}
