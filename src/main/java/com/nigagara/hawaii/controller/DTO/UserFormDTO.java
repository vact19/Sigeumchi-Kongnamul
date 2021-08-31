package com.nigagara.hawaii.controller.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Setter @Getter // DTO 필수
public class UserFormDTO {
    /**
     *  유저가 회원가입창에서 생성할 데이터 DTO
     */
    @NotEmpty(message = "회원 이름은 필수에요")
    private String userName;
    @NotEmpty(message = "비밀번호 입력해")
    private String password;
    @NotEmpty(message = " 입력해요 ")
    private String email1;
    @NotEmpty(message = "입력해")
    private String email2;

    // regular expression 설정 없는 기본값은 공백 빼고 모두 허용
    //  {영문숫자@영문숫자.영문}이 규칙인듯
    @Email(message = "이메일 형식을 지켜주세요",
    regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;   // email 1, 2를 더해서 3 검증. 초기값은 hidden에서 날아옴


    private String pwdQuestion;
    private String pwdAnswer;
    private String pwdHint;
}
