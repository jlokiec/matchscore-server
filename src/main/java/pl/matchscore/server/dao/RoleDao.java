package pl.matchscore.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.matchscore.server.models.Role;

@Service
public interface RoleDao extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
