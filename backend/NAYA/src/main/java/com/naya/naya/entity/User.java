package com.naya.naya.entity;

import com.naya.naya.dto.UserDto;
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
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private String userId;
    @Column(name = "email")
    private String email;
    @Column(name = "joinDate")
    private String joinDate;
    @OneToMany(mappedBy = "userId")
    private List<NayaCard> cardList = new ArrayList<>();

    public static User create(String userId){
        if(ObjectUtils.isEmpty(userId)) return null;
        User user = new User();
        user.userId = userId;
        return user;
    }

    public static User create(UserDto userDto, List<NayaCard> cardList) {
        if(ObjectUtils.isEmpty(userDto)) return null;
        User user = new User();
        user.userId = userDto.getUserId();
        user.joinDate = userDto.getJoinDate();
        user.email = userDto.getEmail();
        user.cardList = cardList;
        return user;
    }

    public void update(UserDto dto){
        this.joinDate = dto.getJoinDate();
        this.email = dto.getEmail();
    }
}
