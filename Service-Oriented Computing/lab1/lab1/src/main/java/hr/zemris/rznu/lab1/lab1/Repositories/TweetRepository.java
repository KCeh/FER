package hr.zemris.rznu.lab1.lab1.Repositories;

import hr.zemris.rznu.lab1.lab1.Models.Tweet;
import hr.zemris.rznu.lab1.lab1.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
public interface TweetRepository  extends JpaRepository<Tweet, Integer> {
    Tweet findById(int id);

    List<Tweet> findAll();

    List<Tweet> findByUser(User user);

    List<Tweet> findByDate(Date date);

    Tweet findByText(String text);
}
