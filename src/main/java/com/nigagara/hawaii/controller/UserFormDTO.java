package com.nigagara.hawaii.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Setter @Getter // DTO 필수
public class UserFormDTO {
    /**
     *  유저가 회원가입창에서 생성할 데이터 DTO
     */
    @NotEmpty(message = "회원 이름은 필수에요")
    private String userName;
    private String password;
    private String email;
    private String email2;

    private String pwdQuestion;
    private String pwdAnswer;
    private String pwdHint;
}
