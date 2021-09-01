package com.nigagara.hawaii.controller.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Setter @Getter // DTO 필수
public class UserFormDTOUpdate {
    /**
     *  유저가 회원가입창에서 생성할 데이터 DTO
     */
    @NotEmpty(message = "회원 이름은 필수에요")
    private String userName;
    @NotEmpty(message = "비밀번호 입력해")
    private String password;

    private String pwdQuestion;
    private String pwdAnswer;
    private String pwdHint;
}
