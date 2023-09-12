package com.task.task2.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

public class AuthRequestDto {
    @Schema(name = "회원 가입 위한 입력 정보")
    @Getter
    public static class SignUp {
        //최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)
        @Schema(description = "유저명(다른 회원과 중복 불가능)", nullable = false, minLength = 4, maxLength = 10)
        @NotBlank
        @Pattern(regexp = "^[a-z0-9]{4,10}$")
        private String username;

        //최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9) + 특수문자
        @Schema(description = "비밀번호(알파벳 대소문자,숫자,특수문자 사용가능)", nullable = false, minLength = 8, maxLength = 15)
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z\\d`~!@#$%^&*()-_=+]{8,15}$")
        private String password;

        @Schema(hidden = true)
        private boolean Isadmin = false;

        @Schema(hidden = true)
        private String AdminCode = "";
    }

    @Schema(name = "로그인 위한 입력 정보")
    @Getter
    public static class Login {
        
        @Schema(description = "로그인 아이디", nullable = false, minLength = 4, maxLength = 10)
        @NotBlank
        private String username;
        @Schema(description = "비밀번호", nullable = false, minLength = 8, maxLength = 15)
        @NotBlank
        private String password;
    }
}
