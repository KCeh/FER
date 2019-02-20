package hr.zemris.rznu.lab1.lab1.Repositories;

import hr.zemris.rznu.lab1.lab1.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findById(int id);

    List<User> findAll();

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);
}
