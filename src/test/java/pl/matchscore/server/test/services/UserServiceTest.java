package pl.matchscore.server.test.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.matchscore.server.config.jwt.SecurityConstants;
import pl.matchscore.server.dao.RoleDao;
import pl.matchscore.server.dao.UserDao;
import pl.matchscore.server.models.User;
import pl.matchscore.server.models.dto.UserRegistrationDto;
import pl.matchscore.server.services.UserService;
import pl.matchscore.server.services.exceptions.EmailTakenException;
import pl.matchscore.server.services.exceptions.UsernameTakenException;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceTest {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    private UserService service;

    @BeforeEach
    public void init() {
        PasswordEncoder bCryptEncoder = new BCryptPasswordEncoder(SecurityConstants.BCRYPT_ROUNDS);
        service = new UserService(userDao, roleDao, bCryptEncoder);
    }

    @Test
    public void testRegister() throws EmailTakenException, UsernameTakenException {
        UserRegistrationDto userDto = initUser();

        long timestampBeforeRegistration = Instant.now().getEpochSecond();
        User registeredUser = service.register(userDto);
        long timestampAfterRegistration = Instant.now().getEpochSecond();

        assertNotNull(registeredUser);
        assertEquals(1, registeredUser.getId());
        assertEquals("jsmith@domain.com", registeredUser.getEmail());
        assertEquals("John", registeredUser.getFirstName());
        assertEquals("Smith", registeredUser.getLastName());
        assertTrue(registeredUser.isEnabled());
        assertTrue(registeredUser.getCreatedAt() >= timestampBeforeRegistration);
        assertTrue(registeredUser.getCreatedAt() <= timestampAfterRegistration);
        assertNotNull(registeredUser.getRoles());
        assertEquals(1, registeredUser.getRoles().size());
        assertEquals("ROLE_USER", registeredUser.getRoles().get(0).getRoleName());
    }

    @Test
    public void testRegister_UsernameTaken() {
        User user = new User();
        user.setUsername("jsmith");
        userDao.save(user);

        UserRegistrationDto userDto = initUser();

        UsernameTakenException exception = assertThrows(UsernameTakenException.class, () -> service.register(userDto));
        assertEquals("Username jsmith is already taken.", exception.getMessage());
    }

    @Test
    public void testRegister_EmailTaken() {
        User user = new User();
        user.setEmail("jsmith@domain.com");
        userDao.save(user);

        UserRegistrationDto userDto = initUser();

        EmailTakenException exception = assertThrows(EmailTakenException.class, () -> service.register(userDto));
        assertEquals("Email jsmith@domain.com is already taken.", exception.getMessage());
    }

    private UserRegistrationDto initUser() {
        UserRegistrationDto userDto = new UserRegistrationDto();
        userDto.setUsername("jsmith");
        userDto.setEmail("jsmith@domain.com");
        userDto.setFirstName("John");
        userDto.setLastName("Smith");
        userDto.setPassword("testpassword");

        return userDto;
    }
}
