package com.task.task2.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

public class AuthRequestDto {
    @Getter
    public static class SignUp {

        //최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)
        @NotBlank
        @Pattern(regexp = "^[a-z0-9]{4,10}$")
        private String username;

        //최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9) + 특수문자
        
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
        private String password;

        private boolean admin = false;
    }

    @Getter
    public static class Login {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }
}
