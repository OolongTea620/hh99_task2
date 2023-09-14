package com.task.task2.service;

import com.task.task2.dto.comment.CommentRequestDto;
import com.task.task2.dto.comment.CommentResponseDto;
import com.task.task2.entity.Comment;
import com.task.task2.entity.Post;
import com.task.task2.entity.User;
import com.task.task2.entity.UserRoleEnum;
import com.task.task2.repository.CommentRepository;
import com.task.task2.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDto create(CommentRequestDto.Create requestDto, User user) {
        Post post = postRepository.findById(requestDto.getPostId())
                .orElseThrow(() -> new NullPointerException("게시글이 존재하지 않습니다"));
        Comment comment = new Comment(requestDto, post, user);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto update(Long commentId, CommentRequestDto.Update requestDto, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NullPointerException("수정할 댓글이 존재하지 않습니다."));

        if (!user.getRole().equals(UserRoleEnum.ADMIN)
                && !comment.getUser().getId().equals(user.getId())
        ) {
            throw new IllegalArgumentException("권한이 없는 유저입니다");
        }
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    public CommentResponseDto.Message delete(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NullPointerException("삭제할 댓글이 존재하지 않습니다."));

        if (!user.getRole().equals(UserRoleEnum.ADMIN)
                && !comment.getUser().getId().equals(user.getId())
        ) {
            throw new IllegalArgumentException("권한이 없는 유저입니다");
        }
        commentRepository.delete(comment);

        return CommentResponseDto.Message.builder()
                .msg("삭제 성공")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

}