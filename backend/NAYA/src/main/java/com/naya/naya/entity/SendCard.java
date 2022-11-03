package com.naya.naya.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SENDCARD")
public class SendCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sendCardId")
    private Long sendCardId;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "cardUrl")
    private String cardUrl;
    @Column(name = "sendDatetime")
    private String sendDatetime;
    @Column(name = "expiredDatetime")
    private String expiredDatetime;
}
