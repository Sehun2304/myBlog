package com.sparta.myblog.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Setter
@Getter
public class SignupRequestDto {
    // 회원가입 요청에서 필요한 정보를 담는 DTO

    // username과 password의 정규식을 설정해준다

    @NotBlank
    @Size(min = 4, max = 10, message = "4자에서 10자")
    @Pattern(regexp = "^[a-z0-9]*$",message = "알파벳 소문자(a~z), 숫자(0~9)")
    private String username;


    @NotBlank
    @Size(min = 8, max = 15 , message = "8자에서 15자")
    @Pattern(regexp = "^[a-zA-Z0-9]*$",message = "알파벳 소문자(a~z),알파벳 대문자(A~Z), 숫자(0~9)")
    private String password;
}