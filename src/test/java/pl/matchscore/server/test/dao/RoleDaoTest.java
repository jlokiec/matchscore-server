package pl.matchscore.server.test.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.matchscore.server.dao.RoleDao;
import pl.matchscore.server.models.Role;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class RoleDaoTest {
    @Autowired
    RoleDao dao;

    @BeforeAll
    public void init() {
        dao.save(new Role("ROLE_USER"));
    }

    @Test
    public void testFindByRoleName() {
        Role role = dao.findByRoleName("ROLE_USER");
        assertNotNull(role);
        assertEquals("ROLE_USER", role.getRoleName());
    }

    @Test
    public void testFindByRoleName_InvalidRoleName() {
        Role role = dao.findByRoleName("ROLE_TEST");
        assertNull(role);
    }
}
