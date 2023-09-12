package com.task.task2.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
@Schema(description = "인증-인가 Response 확인")
@Getter
@Builder
public class AuthResponseDto {
    @Schema(description = "결과 메시지", type = "string")
    private String msg;
    @Schema(description = "상태 코드", type = "Number")
    private Integer statusCode;
}