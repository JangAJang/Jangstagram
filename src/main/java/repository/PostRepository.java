package repository;

import entity.Post;
import entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAll();

    Optional<List<Post>> findAllByWriter(User writer);
}
