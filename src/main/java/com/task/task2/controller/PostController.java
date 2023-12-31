package com.task.task2.controller;

import com.task.task2.dto.post.PostRequestDto;
import com.task.task2.dto.post.PostResponseDto;
import com.task.task2.security.UserDetailsImpl;
import com.task.task2.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Post(게시글)", description = "게시글 CRUD API")
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/api/post")
    public ResponseEntity<PostResponseDto> create(
        @RequestBody @Valid PostRequestDto.Create postRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
        ) {
        PostResponseDto result = postService.create(postRequestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/api/posts")
    public ResponseEntity<List<PostResponseDto>> getList() {
        List<PostResponseDto> result = postService.getList();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/api/post/{id}")
    public ResponseEntity<PostResponseDto> getOne (@PathVariable Long id) {
        PostResponseDto result = postService.getOne(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/api/post/{id}")
    public ResponseEntity<PostResponseDto> edit(
        @PathVariable Long id,
        @RequestBody PostRequestDto.Edit requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        PostResponseDto result = postService.update(id,requestDto,userDetails.getUser());

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/api/post/{id}")
    public ResponseEntity<PostResponseDto.Delete> deleteOne(
        @PathVariable Long id,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        PostResponseDto.Delete result = postService.delete(id, userDetails.getUser());
        return ResponseEntity.ok(result);
    }
}
