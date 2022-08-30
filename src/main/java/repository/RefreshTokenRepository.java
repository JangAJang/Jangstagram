package repository;

import entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository {
    Optional<RefreshToken> findByKey(String key);
}
