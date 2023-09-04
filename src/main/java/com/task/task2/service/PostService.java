package com.task.task2.service;

import com.task.task2.dto.post.PostRequestDto.Create;
import com.task.task2.dto.post.PostRequestDto.Edit;
import com.task.task2.dto.post.PostResponseDto;
import com.task.task2.entity.Post;
import com.task.task2.entity.User;
import com.task.task2.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public PostResponseDto create(Create requestDto, HttpServletRequest req) {
        Post createdPost = new Post(requestDto);
        User user = (User) req.getAttribute("user");
        createdPost.setWriter(user.getUsername()); // 임시 -> 나중에 token에서 username 가져올 예정
        createdPost = postRepository.save(createdPost);
        return new PostResponseDto(createdPost);
    }

    public List<PostResponseDto> getList() {
        var result = postRepository.getPostsOrderByCreatedAtDesc();
        return result.stream().map(PostResponseDto::new).toList();
    }

    public PostResponseDto getOne(Long id) {
        Post post = this.findById(id);
        return new PostResponseDto(post);
    }

    public PostResponseDto update(Long id, Edit requestDto, HttpServletRequest req) {
        User editor = (User) req.getAttribute("user");
        Post post =this.findById(id);

        try {
            if (!post.getWriter().equals(editor.getUsername())) {
                throw new IllegalAccessException("UnAuthorization");
            }
            post = postRepository.update(post);
            return new PostResponseDto(post);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } 
    }

    public PostResponseDto.Delete delete(Long id, HttpServletRequest req) {
        Post post = findById(id);
        User user = (User) req.getAttribute("user");
        String msg = "";
        int status = HttpStatus.OK.value();

        try {
            if (post.getWriter().equals(user.getUsername())) {
                throw new IllegalAccessException("UnAuthorized user");
            }
            postRepository.delete(post);
            msg = "게시글 삭제 성공";
            status = HttpStatus.OK.value();
        } catch (IllegalArgumentException e) {
            msg = e.getMessage();
            status = HttpStatus.SERVICE_UNAVAILABLE.value();
        } catch (IllegalAccessException ae) {
            msg = ae.getMessage();
            status = HttpStatus.UNAUTHORIZED.value();
        }
        return PostResponseDto.Delete.builder()
                .msg(msg)
                .statusCode(status)
                .build();
    }

    private Post findById (Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 가진 포스트가 존재하지 않습니다"));
    }
}
