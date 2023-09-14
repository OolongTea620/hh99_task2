package com.task.task2.repository;

import com.task.task2.entity.Post;
import com.task.task2.entity.User;
import com.task.task2.entity.UserLikedPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLikedPostRepository extends JpaRepository<UserLikedPost, Long> {
    Optional<UserLikedPost> findUserLikedPostByPostAndUser(Post post, User user);

    Long countByPost(Post post);
}