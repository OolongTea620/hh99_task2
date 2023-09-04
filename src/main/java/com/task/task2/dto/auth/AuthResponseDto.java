package com.task.task2.dto.auth;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class AuthResponseDto {
    private String msg;
    private Integer statusCode;
}