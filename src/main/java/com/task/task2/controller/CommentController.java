package com.task.task2.controller;

import com.task.task2.dto.comment.CommentRequestDto;
import com.task.task2.dto.comment.CommentResponseDto;
import com.task.task2.entity.UserLikedComment;
import com.task.task2.security.UserDetailsImpl;
import com.task.task2.service.CommentService;
import com.task.task2.service.LikedService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment(댓글)", description = "댓글 CRUD API")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final LikedService likedService;
    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestBody @Valid CommentRequestDto.Create requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.create(requestDto, userDetails.getUser()));
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid CommentRequestDto.Update requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.update(commentId,requestDto, userDetails.getUser()));
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDto.Message> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.delete(commentId, userDetails.getUser()));
    }

    @PostMapping("/comment/{commentId}/like")
    public ResponseEntity<CommentResponseDto.Message> likeComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(likedService.checkComment(commentId, userDetails.getUser()));
    }
}
