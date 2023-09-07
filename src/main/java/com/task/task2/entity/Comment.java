package com.task.task2.entity;

import com.task.task2.dto.comment.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Comment(CommentRequestDto.Create requestDto, Post post) {
        this.post = post;
        this.content = requestDto.getContent();
    }

    public void update(CommentRequestDto.Update requestDto) {
        this.content = requestDto.getContent();
    }
}
