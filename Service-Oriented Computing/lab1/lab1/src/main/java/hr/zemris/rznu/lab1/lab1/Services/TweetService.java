package hr.zemris.rznu.lab1.lab1.Services;

import hr.zemris.rznu.lab1.lab1.Models.Tweet;
import hr.zemris.rznu.lab1.lab1.Models.User;

import java.util.Date;
import java.util.List;

public interface TweetService {
    Tweet saveTweet(Tweet tweet);

    void deleteTweet(Tweet tweet);

    Tweet updateTweet(Tweet tweet);

    List<Tweet> getAllTweet();

    Tweet findById(int id);

    List<Tweet> findByUser(User user);

    List<Tweet> findByDate(Date date);

    Tweet findByText(String text);
}
