package com.insta.jangstagram.repository;

import com.insta.jangstagram.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {
}
