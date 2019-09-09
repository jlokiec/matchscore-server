package pl.matchscore.server.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role() {
        users = new ArrayList<>();
    }

    public Role(String roleName) {
        this.roleName = roleName;
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }
}
