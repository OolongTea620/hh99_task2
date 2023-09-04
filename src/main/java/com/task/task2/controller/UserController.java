package com.task.task2.controller;

import com.task.task2.dto.auth.AuthRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/signup")
    public void signup(
        @RequestBody @Valid AuthRequestDto.SignUp requestDto,
        HttpServletResponse res
    ) {

    }

    public void login(@RequestBody @Valid AuthRequestDto.Login requestDto) {
    }

}
