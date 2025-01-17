package pl.matchscore.server.services;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.matchscore.server.dao.RoleDao;
import pl.matchscore.server.dao.UserDao;
import pl.matchscore.server.models.Role;
import pl.matchscore.server.models.User;
import pl.matchscore.server.models.dto.UserRegistrationDto;
import pl.matchscore.server.services.exceptions.EmailTakenException;
import pl.matchscore.server.services.exceptions.UserNotFoundException;
import pl.matchscore.server.services.exceptions.UsernameTakenException;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserService {
    private static final String USER_ROLE_NAME = "ROLE_USER";
    private static final int USER_INITIAL_REPUTATION = 0;

    private UserDao userDao;
    private RoleDao roleDao;
    private PasswordEncoder bCryptEncoder;
    private EmailService emailService;

    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public User register(UserRegistrationDto userToRegister) throws UsernameTakenException, EmailTakenException {
        String username = userToRegister.getUsername();
        String email = userToRegister.getEmail();

        if (isUsernameTaken(username)) {
            throw new UsernameTakenException("Username " + username + " is already taken.");
        }

        if (isEmailTaken(email)) {
            throw new EmailTakenException("Email " + email + " is already taken.");
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPasswordHash(hashPassword(userToRegister.getPassword()));
        user.setFirstName(userToRegister.getFirstName());
        user.setLastName(userToRegister.getLastName());
        user.setEnabled(false);
        user.setCreatedAt(Instant.now().getEpochSecond());
        user.setRegistrationId(UUID.randomUUID().toString());
        user.setRoles(Lists.newArrayList(getUserRole()));
        user.setReputation(USER_INITIAL_REPUTATION);

        user = userDao.save(user);
        sendCompleteRegistrationEmail(user);

        return user;
    }

    public void completeRegistration(String registrationId) throws UserNotFoundException {
        User user = userDao.findByRegistrationId(registrationId);

        if (user == null) {
            throw new UserNotFoundException("User with registration id " + registrationId + " not found.");
        }

        user.setRegistrationId(null);
        user.setEnabled(true);
        userDao.save(user);
    }

    private String hashPassword(String passwordInPlainText) {
        return bCryptEncoder.encode(passwordInPlainText);
    }

    private boolean isUsernameTaken(String username) {
        User userWithGivenUsername = userDao.findByUsername(username);
        return userWithGivenUsername != null;
    }

    private boolean isEmailTaken(String email) {
        User userWithGivenEmail = userDao.findByEmail(email);
        return userWithGivenEmail != null;
    }

    private Role getUserRole() {
        Role userRole = roleDao.findByRoleName(USER_ROLE_NAME);

        if (userRole != null) {
            return userRole;
        } else {
            return roleDao.save(new Role(USER_ROLE_NAME));
        }
    }

    private void sendCompleteRegistrationEmail(User user) {
        new Thread(() -> emailService.sendConfirmationEmail(user)).start();
    }
}
