package com.task.task2.repository;

import com.task.task2.entity.Comment;
import com.task.task2.entity.User;
import com.task.task2.entity.UserLikedComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLikedCommentRepository extends JpaRepository<UserLikedComment, Long> {
    Long countByComment(Comment comment);

    Optional<UserLikedComment> findByCommentAndUser(Comment comment, User user);
}
