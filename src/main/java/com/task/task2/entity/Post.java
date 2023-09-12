package com.task.task2.entity;

import com.task.task2.dto.post.PostRequestDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.Modifying;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "posts")
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 250, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    public void setWriter(String username) {
        this.writer = username;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Post(PostRequestDto.Create dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public void update(PostRequestDto.Edit requestDto) {
        this.title = Optional.of(requestDto.getTitle()).orElse(this.content);
        this.content = Optional.of(requestDto.getContent()).orElse(this.content);
    }
}