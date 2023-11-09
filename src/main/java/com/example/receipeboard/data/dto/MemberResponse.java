package com.example.receipeboard.data.dto;

import com.example.receipeboard.data.common.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponse extends BaseTimeEntity {
    private Integer id;
    private String nickname;
    private String password;
    private String name;
}
