package com.nigagara.hawaii.controller.DTO;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class TypeMismatchTest {

    @NotNull(message = " 입력을 하세요1 ")
    @Range(min = 10, max = 10000, message = "10~10000 사이")
    private Integer price1;
    @NotNull(message = " 입력을 하세요2 ")
    @Range(min = 10, max = 10000, message = "10~10000 사이")
    private Integer price2;
}
