package com.example.receipeboard.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "ID는 필수 항목입니다")
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수 항목입니다")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수 항목입니다")
    private String password2;

    private String name;
}
