package com.example.receipeboard.data.entity;

import com.example.receipeboard.data.common.BaseTimeEntity;
import com.example.receipeboard.data.common.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Getter
@Setter
@Component
@NoArgsConstructor
@Entity
@Table
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(unique = true)
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole = MemberRole.USER;

    @Builder
    public Member(String nickname, String password, String name) {
        this.nickname = nickname;
        this.password = password;
        this.name = name;
    }

}
