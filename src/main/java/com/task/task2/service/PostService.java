package com.task.task2.service;

import com.task.task2.dto.post.PostRequestDto;
import com.task.task2.dto.post.PostRequestDto.Create;
import com.task.task2.dto.post.PostResponseDto;
import com.task.task2.entity.Post;
import com.task.task2.entity.User;
import com.task.task2.entity.UserRoleEnum;
import com.task.task2.repository.PostRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostResponseDto create(Create requestDto, User user) {
        Post createdPost = new Post(requestDto);
        createdPost.setWriter(user.getUsername());
        createdPost = postRepository.save(createdPost);
        return new PostResponseDto(createdPost);
    }

    public List<PostResponseDto> getList() {
        var result = postRepository.findAllByOrderByCreatedAtDesc();
        return result.stream().map(PostResponseDto::new).toList();
    }

    public PostResponseDto getOne(Long id) {
        Post post = this.findById(id);
        return new PostResponseDto(post);
    }

    public PostResponseDto update(Long id, PostRequestDto.Edit requestDto, User user) {
        Post post = this.findById(id);
        if (user.getRole().equals(UserRoleEnum.ADMIN)
                || post.getWriter().equals(user.getUsername())
        ) {
            post.update(requestDto);
            return new PostResponseDto(post);
        } else {
            throw new IllegalArgumentException("유효하지 않는 사용자");
        }
    }

    public PostResponseDto.Delete delete(Long id, User user) {
        String msg = "";
        HttpStatus status = HttpStatus.OK;

        try {
            Post post = findById(id);
            if (user.getRole().equals(UserRoleEnum.ADMIN)
                    || post.getWriter().equals(user.getUsername())
            ) {
                postRepository.delete(post);
                msg = "게시글 삭제 성공";
            } else {
                throw new IllegalAccessException("삭제 권한이 없는 유저입니다.");
            }
        } catch (IllegalArgumentException e) {
            msg = e.getMessage();
            status = HttpStatus.SERVICE_UNAVAILABLE;
        } catch (IllegalAccessException ae) {
            msg = ae.getMessage();
            status = HttpStatus.UNAUTHORIZED;
        }
        return PostResponseDto.Delete.builder()
                .msg(msg)
                .statusCode(status.value())
                .build();
    }

    private Post findById(Long id) throws IllegalArgumentException {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 가진 포스트가 존재하지 않습니다"));
    }
}