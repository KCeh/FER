package hr.zemris.rznu.lab1.lab1.Services;

import hr.zemris.rznu.lab1.lab1.Models.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);

    User findById(int id);

    User updateUser(User user);

    User saveUser(User user);

    void deleteUser(User user);

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    List<User> findAll();
}
