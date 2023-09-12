package com.task.task2.dto.post;

import com.task.task2.dto.comment.CommentResponseDto;
import com.task.task2.entity.Post;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String username;
    private final LocalDateTime createdAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();
    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.username = entity.getWriter();
        this.createdAt = entity.getCreatedAt();
        this.commentList = entity.getCommentList().stream()
                .map(CommentResponseDto::new)
                .toList();
    }
    @Builder
    @Getter
    public static class Delete {
        private String msg;
        private Integer statusCode;
    }
}
