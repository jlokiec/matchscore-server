package pl.matchscore.server.test.services;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.matchscore.server.dao.RoleDao;
import pl.matchscore.server.dao.UserDao;
import pl.matchscore.server.models.Role;
import pl.matchscore.server.models.User;
import pl.matchscore.server.services.DatabaseUserDetailsService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DatabaseUserDetailsServiceTest {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    private DatabaseUserDetailsService service;

    @BeforeAll
    public void init() {
        Role userRole = new Role();
        userRole.setId(1);
        userRole.setRoleName("ROLE_USER");
        userRole = roleDao.save(userRole);

        User user = new User();
        user.setId(1);
        user.setEmail("test@mail.com");
        user.setUsername("testUser");
        user.setPasswordHash("$2y$11$/wk6fSjj.EdQZC/CC8/b8ebHXY8j6ZCfEC8/ae7NPqlgAASJx37SG");
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setEnabled(true);
        user.setRoles(Lists.newArrayList(userRole));
        userDao.save(user);

        service = new DatabaseUserDetailsService(userDao);
    }

    @Test
    public void testLoadUserByUsername() {
        UserDetails user = service.loadUserByUsername("testUser");
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("invalidUsername"));
        assertEquals("invalidUsername", exception.getMessage());
    }
}
