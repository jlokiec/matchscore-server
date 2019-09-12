package pl.matchscore.server.test.services;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.matchscore.server.dao.UserDao;
import pl.matchscore.server.models.Role;
import pl.matchscore.server.models.User;
import pl.matchscore.server.services.DatabaseUserDetailsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DatabaseUserDetailsServiceTest {
    private UserDao dao;
    private DatabaseUserDetailsService service;

    @BeforeEach
    public void init() {
        dao = mock(UserDao.class);
        service = new DatabaseUserDetailsService(dao);
    }

    @AfterEach
    public void clean() {
        dao = null;
        service = null;
    }

    @Test
    public void testLoadUserByUsername() {
        String username = "testUser";
        when(dao.findByUsername(username)).thenReturn(initUser());

        UserDetails user = service.loadUserByUsername(username);

        assertNotNull(user, "user is null");
        assertEquals(username, user.getUsername(), "user username is incorrect");
        assertEquals("$2y$11$/wk6fSjj.EdQZC/CC8/b8ebHXY8j6ZCfEC8/ae7NPqlgAASJx37SG", user.getPassword(), "user password is incorrect");
        assertTrue(user.isEnabled(), "user enabled is incorrect");
        assertEquals(1, user.getAuthorities().size(), "user authorities size is incorrect");
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String username = "testUser";
        when(dao.findByUsername(username)).thenThrow(new UsernameNotFoundException(username));

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(username));
        assertEquals(username, exception.getMessage());
    }

    private User initUser() {
        Role userRole = new Role("ROLE_USER");
        userRole.setId(1);


        User user = new User();
        user.setId(1);
        user.setEmail("test@mail.com");
        user.setUsername("testUser");
        user.setPasswordHash("$2y$11$/wk6fSjj.EdQZC/CC8/b8ebHXY8j6ZCfEC8/ae7NPqlgAASJx37SG");
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setEnabled(true);
        user.setRoles(Lists.newArrayList(userRole));

        userRole.addUser(user);

        return user;
    }
}
