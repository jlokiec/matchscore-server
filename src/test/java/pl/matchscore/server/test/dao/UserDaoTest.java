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
import pl.matchscore.server.dao.UserDao;
import pl.matchscore.server.models.User;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserDaoTest {
    @Autowired
    private UserDao dao;

    @BeforeAll
    public void init() {
        User user = new User();
        user.setUsername("jsmith");
        user.setEmail("john.smith@domain.com");
        user.setRegistrationId("91c9a14d-c832-4762-a7b0-06edaeb4479a");
        dao.save(user);
    }

    @Test
    public void testFindByUsername() {
        User user = dao.findByUsername("jsmith");
        assertNotNull(user);
    }

    @Test
    public void testFindByUsername_InvalidUsername() {
        User user = dao.findByUsername("johndoe");
        assertNull(user);
    }

    @Test
    public void testFindByEmail() {
        User user = dao.findByEmail("john.smith@domain.com");
        assertNotNull(user);
    }

    @Test
    public void testFindByEmail_InvalidEmail() {
        User user = dao.findByEmail("john.doe@mail.com");
        assertNull(user);
    }

    @Test
    public void testFindByRegistrationId() {
        User user = dao.findByRegistrationId("91c9a14d-c832-4762-a7b0-06edaeb4479a");
        assertNotNull(user);
    }

    @Test
    public void testFindByRegistrationId_InvalidRegistrationId() {
        User user = dao.findByRegistrationId("271edf8d-6b3a-4069-bb39-61b79bb32494");
        assertNull(user);
    }
}
