package pl.matchscore.server.test.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.matchscore.server.config.jwt.SecurityConstants;
import pl.matchscore.server.dao.UserDao;
import pl.matchscore.server.models.User;
import pl.matchscore.server.models.dto.UserRegistrationDto;
import pl.matchscore.server.services.UserService;
import pl.matchscore.server.services.exceptions.EmailTakenException;
import pl.matchscore.server.services.exceptions.UsernameTakenException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private UserDao dao;
    private UserService service;
    private PasswordEncoder bCryptEncoder;

    @BeforeEach
    public void init() {
        dao = mock(UserDao.class);
        bCryptEncoder = new BCryptPasswordEncoder(SecurityConstants.BCRYPT_ROUNDS);
        service = new UserService(dao, bCryptEncoder);
    }

    @AfterEach
    public void clean() {
        dao = null;
        service = null;
        bCryptEncoder = null;
    }

    @Test
    public void testRegisterUser() throws EmailTakenException, UsernameTakenException {
        String username = "test";
        String email = "test@domain.com";

        UserRegistrationDto userDto = new UserRegistrationDto();
        userDto.setUsername(username);
        userDto.setEmail(email);
        userDto.setPassword("testpassword");

        when(dao.findByUsername(username)).thenReturn(null);
        when(dao.findByEmail(email)).thenReturn(null);
        when(dao.save(any(User.class))).thenReturn(new User());

        User registeredUser = service.register(userDto);

        assertNotNull(registeredUser, "registered user is null");
    }

    @Test
    public void testRegisterUser_UsernameTaken() {
        String username = "test";
        String email = "test@domain.com";

        UserRegistrationDto userDto = new UserRegistrationDto();
        userDto.setUsername(username);
        userDto.setEmail(email);

        when(dao.findByUsername(username)).thenReturn(new User());
        when(dao.findByEmail(email)).thenReturn(null);
        when(dao.save(any(User.class))).thenReturn(new User());

        UsernameTakenException exception = assertThrows(UsernameTakenException.class, () -> service.register(userDto));
        assertEquals("Username test is already taken.", exception.getMessage());
    }

    @Test
    public void testRegisterUser_EmailTaken() {
        String username = "test";
        String email = "test@domain.com";

        UserRegistrationDto userDto = new UserRegistrationDto();
        userDto.setUsername(username);
        userDto.setEmail(email);

        when(dao.findByUsername(username)).thenReturn(null);
        when(dao.findByEmail(email)).thenReturn(new User());
        when(dao.save(any(User.class))).thenReturn(new User());

        EmailTakenException exception = assertThrows(EmailTakenException.class, () -> service.register(userDto));
        assertEquals("Email test@domain.com is already taken.", exception.getMessage());
    }
}
