package com.task.task2.controller;

import com.task.task2.dto.auth.AuthRequestDto;
import com.task.task2.dto.auth.AuthResponseDto;
import com.task.task2.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입 API 입니다")
    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signup(
         @RequestBody @Valid AuthRequestDto.SignUp requestDto
    ) {
        AuthResponseDto result =  userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
