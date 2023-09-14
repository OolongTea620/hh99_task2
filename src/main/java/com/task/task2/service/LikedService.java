package com.task.task2.service;

import com.task.task2.dto.comment.CommentResponseDto;
import com.task.task2.dto.post.PostResponseDto;
import com.task.task2.entity.*;
import com.task.task2.repository.CommentRepository;
import com.task.task2.repository.PostRepository;
import com.task.task2.repository.UserLikedCommentRepository;
import com.task.task2.repository.UserLikedPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikedService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserLikedPostRepository userLikedPostRepository;
    private final UserLikedCommentRepository userLikedCommentRepository;

    @Transactional
    public PostResponseDto.Like checkPost(Long id, User user) {
        String message = "좋아요를 누르셨습니다.";
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("게시글이 존재하지 않습니다"));

        Optional<UserLikedPost> userLikedPost = userLikedPostRepository
                .findUserLikedPostByPostAndUser(post, user);

        if (userLikedPost.isPresent()) {
            userLikedPostRepository.delete(userLikedPost.get());
            message = "좋아요를 해제했습니다.";
        }else {
            userLikedPostRepository.save(new UserLikedPost(post, user));
        }

        return PostResponseDto.Like.builder()
                .msg(message)
                .build();
    }

    @Transactional
    public CommentResponseDto.Message checkComment(Long id, User user) {
        String message = "좋아요를 누르셨습니다.";
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("댓글이 존재하지 않습니다"));

        Optional<UserLikedComment> userLikedComment = userLikedCommentRepository
                .findByCommentAndUser(comment, user);

        if (userLikedComment.isPresent()) {
            userLikedCommentRepository.delete(userLikedComment.get());
            message = "좋아요를 해제했습니다.";
        }else {
            userLikedCommentRepository.save(new UserLikedComment(comment, user));
        }

        return CommentResponseDto.Message.builder()
                .msg(message)
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    public Long countPostLiked(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NullPointerException("게시글이 존재하지 않습니다"));
        return userLikedPostRepository.countByPost(post);
    }

    public Long countCommentLiked(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NullPointerException("댓글이 존재하지 않습니다"));
        return userLikedCommentRepository.countByComment(comment);
    }
}
