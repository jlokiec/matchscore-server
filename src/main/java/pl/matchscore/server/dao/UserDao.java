package pl.matchscore.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matchscore.server.models.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
}
