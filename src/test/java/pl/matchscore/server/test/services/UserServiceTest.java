package pl.matchscore.server.test.services;

import com.google.common.base.Strings;
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
import pl.matchscore.server.services.EmailService;
import pl.matchscore.server.services.UserService;
import pl.matchscore.server.services.exceptions.EmailTakenException;
import pl.matchscore.server.services.exceptions.UserNotFoundException;
import pl.matchscore.server.services.exceptions.UsernameTakenException;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        EmailService emailService = mock(EmailService.class);
        doNothing().when(emailService).sendConfirmationEmail(any(User.class));
        service = new UserService(userDao, roleDao, bCryptEncoder, emailService);
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
        assertFalse(registeredUser.isEnabled());
        assertTrue(registeredUser.getCreatedAt() >= timestampBeforeRegistration);
        assertTrue(registeredUser.getCreatedAt() <= timestampAfterRegistration);
        assertFalse(Strings.isNullOrEmpty(registeredUser.getRegistrationId()));
        assertNotNull(registeredUser.getRoles());
        assertEquals(1, registeredUser.getRoles().size());
        assertEquals("ROLE_USER", registeredUser.getRoles().get(0).getRoleName());
    }

    @Test
    public void testRegister_UsernameTaken() {
        User user = new User();
        user.setUsername("jsmith");

        UserDao userDao = mock(UserDao.class);
        when(userDao.findByUsername("jsmith")).thenReturn(user);

        PasswordEncoder bCryptEncoder = new BCryptPasswordEncoder(SecurityConstants.BCRYPT_ROUNDS);
        EmailService emailService = mock(EmailService.class);
        doNothing().when(emailService).sendConfirmationEmail(any(User.class));
        UserService service = new UserService(userDao, roleDao, bCryptEncoder, emailService);

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

    @Test
    public void testCompleteRegistration() throws UserNotFoundException {
        User user = new User();
        user.setUsername("jsmith");
        user.setEnabled(false);
        user.setRegistrationId("dbccad73-71ea-4380-a53e-ab95ea861655");
        userDao.save(user);

        service.completeRegistration("dbccad73-71ea-4380-a53e-ab95ea861655");

        User enabledUser = userDao.findByUsername("jsmith");
        assertTrue(enabledUser.isEnabled());
        assertNull(enabledUser.getRegistrationId());
    }

    @Test
    public void testCompleteRegistration_InvalidRegistrationId() {
        User user = new User();
        user.setRegistrationId("dbccad73-71ea-4380-a53e-ab95ea861655");
        userDao.save(user);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> service.completeRegistration("49b2b71d-e5fc-4d6a-9b4b-a94f20f99e2a"));
        assertEquals("User with registration id 49b2b71d-e5fc-4d6a-9b4b-a94f20f99e2a not found.", exception.getMessage());
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
