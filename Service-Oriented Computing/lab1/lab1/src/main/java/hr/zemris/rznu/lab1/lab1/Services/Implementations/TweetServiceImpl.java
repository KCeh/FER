package hr.zemris.rznu.lab1.lab1.Services.Implementations;

import hr.zemris.rznu.lab1.lab1.Models.Tweet;
import hr.zemris.rznu.lab1.lab1.Models.User;
import hr.zemris.rznu.lab1.lab1.Repositories.TweetRepository;
import hr.zemris.rznu.lab1.lab1.Services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {
    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public Tweet findById(int id) {
        return tweetRepository.findById(id);
    }

    @Override
    public Tweet saveTweet(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public void deleteTweet(Tweet tweet) {
        tweetRepository.delete(tweet);
    }

    @Override
    public Tweet updateTweet(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> getAllTweet() {
        return tweetRepository.findAll();
    }

    @Override
    public List<Tweet> findByUser(User user) {
        return tweetRepository.findByUser(user);
    }

    @Override
    public List<Tweet> findByDate(Date date) {
        return tweetRepository.findByDate(date);
    }

    @Override
    public Tweet findByText(String text) {
        return tweetRepository.findByText(text);
    }
}
