package com.task.task2.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class CommentRequestDto {
    @Getter
    public static class Create {
        @NotNull
        private Long postId;
        @NotBlank
        private String content;
    }

    @Getter
    public static class Update {
        @NotBlank
        private String content;
    }
}
