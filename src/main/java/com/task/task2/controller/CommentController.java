package com.task.task2.controller;

import com.task.task2.dto.comment.CommentRequestDto;
import com.task.task2.dto.comment.CommentResponseDto;
import com.task.task2.security.UserDetailsImpl;
import com.task.task2.service.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestBody @Valid CommentRequestDto.Create requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.create(requestDto, userDetails.getUser()));
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @RequestParam Long commentId,
            @RequestBody @Valid CommentRequestDto.Update requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.update(commentId,requestDto, userDetails.getUser()));
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDto.Message> deleteComment(
            @RequestParam Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.delete(commentId, userDetails.getUser()));
    }
}
