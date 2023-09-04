package com.task.task2.controller;

import com.task.task2.dto.post.PostRequestDto;
import com.task.task2.dto.post.PostResponseDto;
import com.task.task2.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/api/post")
    public ResponseEntity<PostResponseDto> create(
        @RequestBody @Valid PostRequestDto.Create postRequestDto,
        HttpServletRequest req
        ) {
        PostResponseDto result = postService.create(postRequestDto, req);
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
        HttpServletRequest req
    ) {
        PostResponseDto result = postService.update(id,requestDto,req);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/api/post/{id}")
    public ResponseEntity<PostResponseDto.Delete> deleteOne(
        @PathVariable Long id,
        HttpServletRequest req
    ) {
        PostResponseDto.Delete result = postService.delete(id, req);
        return ResponseEntity.ok(result);
    }
}
