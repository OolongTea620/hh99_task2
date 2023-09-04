package com.task.task2.dto.post;

import com.task.task2.entity.Post;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.username = entity.getWriter();
        this.createdAt = entity.getCreatedAt();
    }

    @Builder
    public static class Delete {
        private String msg;
        private Integer statusCode;
    }
}
