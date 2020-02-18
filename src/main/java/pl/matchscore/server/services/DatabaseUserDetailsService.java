package pl.matchscore.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.matchscore.server.dao.UserDao;
import pl.matchscore.server.models.User;
import pl.matchscore.server.models.UserSecurityDetails;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    private UserDao dao;

    @Autowired
    public DatabaseUserDetailsService(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = dao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserSecurityDetails(user);
    }
}
