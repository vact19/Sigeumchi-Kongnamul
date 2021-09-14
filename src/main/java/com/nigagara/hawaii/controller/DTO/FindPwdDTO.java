package com.nigagara.hawaii.controller.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter @Getter
public class FindPwdDTO {

    @NotEmpty(message = "값을 입력해 주세요")
    private String username;

    @NotEmpty(message = "값을 입력해 주세요")
    private String pwdQuestion;

    @NotEmpty(message = "값을 입력해 주세요")
    private String pwdAnswer;
}

