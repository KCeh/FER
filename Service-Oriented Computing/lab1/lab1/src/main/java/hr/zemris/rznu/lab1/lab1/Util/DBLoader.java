package hr.zemris.rznu.lab1.lab1.Util;

import hr.zemris.rznu.lab1.lab1.Models.Tweet;
import hr.zemris.rznu.lab1.lab1.Models.User;
import hr.zemris.rznu.lab1.lab1.Services.TweetService;
import hr.zemris.rznu.lab1.lab1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Date;

@Service
public class DBLoader {
    @Autowired
    private UserService userService;

    @Autowired
    private TweetService tweetService;

    private boolean loaded;

    public DBLoader()
    {
        loaded = false;
    }

    @Transactional
    @PostConstruct
    public void initData()
    {
        if (loaded)
        {
            return;
        }

        User user1 = createUser("readDonald@mail.com", "Donald", "Trump");
        User user2 = createUser("korisnik2@mail.com", "Pero", "Perić");
        User user3 = createUser("korisnik3@mail.com", "Korisnik", "Perić");

        createTweet (1,"Worst trade deal in history", new Date(),  user1);
        createTweet(2,"Make America great again", new Date(),  user1);
        createTweet( 3,"Hihihi", new Date(), user2);

        loaded = true;
    }

    @Transactional
    public void createTweet(int id, String text, Date date, User user)
    {
        Tweet tweet = tweetService.findById(id);

        if (ObjectUtils.isEmpty(tweet))
        {
            tweet = new Tweet();
            tweet.setText(text);
            tweet.setDate(date);
            tweet.setUser(user);
            tweetService.saveTweet(tweet);
        }
    }

    @Transactional
    public User createUser(String email, String firstName, String lastName)
    {
        User user = userService.findByEmail(email);

        if (ObjectUtils.isEmpty(user))
        {
            user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            userService.saveUser(user);
        }

        return user;
    }

}
