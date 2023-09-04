package com.task.task2.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class PostRequestDto{
    @Getter
    public static class Create {
        @NotBlank
        private String title;
        @NotBlank
        private String content;
    }

    @Getter
    public static class Edit {
        private String title;
        private String content;
    }
}
