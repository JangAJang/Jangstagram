package repository;

import entity.Comment;
import entity.Hashtag;
import entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Optional<List<Hashtag>> findAllByPost(Post post);

    Optional<List<Hashtag>> findAllByComment(Comment comment);
}
