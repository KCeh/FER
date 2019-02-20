package hr.zemris.rznu.lab1.lab1.Controllers;

import hr.zemris.rznu.lab1.lab1.Models.Tweet;
import hr.zemris.rznu.lab1.lab1.Services.TweetService;
import hr.zemris.rznu.lab1.lab1.Services.UserService;
import hr.zemris.rznu.lab1.lab1.Util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = Constants.Api.Tweet.tweetRoot)
@Api(value = "Tweet Controller API", produces = MediaType.APPLICATION_JSON_VALUE,
        description = "Controller for operations with tweets, protected by Basic Authentication")
public class TweetRestController {
    private static final String jsonResponseType = "application/json";

    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;

    /**
     * Method for getting list of all tweets
     *
     * @return list of tweets (it can be empty)
     */
    @GetMapping(value = Constants.Api.Tweet.getAllTweets)
    @ApiOperation("Fetch all tweets")
    @ApiResponse(code = 200, message = "OK", response = List.class)
    public ResponseEntity<List<Tweet>> getAllTweets() {
        return new ResponseEntity<>(tweetService.getAllTweet(), HttpStatus.OK);
    }

    /**
     * Method for getting user with id or email
     *
     * @param id id
     * @return tweet ili not found
     */
    @GetMapping(value = Constants.Api.Tweet.tweetBy)
    @ApiOperation("Fetch user with id or email")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Tweet.class), @ApiResponse(code = 404, message = "Not Found")})
    public ResponseEntity<Tweet> getTweetById(@PathVariable(value = "id") Integer id) {
        Tweet tweet = tweetService.findById(id);

        if (tweet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(tweet);
    }

    /**
     * Method for creating new tweet
     *
     * @param tweet tweet
     * @return tweet
     */
    @PostMapping(value = Constants.Api.Tweet.saveTweet)
    @ApiOperation("Create new tweet")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created", response = Tweet.class), @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<Tweet> createTweet(@Valid @RequestBody Tweet tweet, HttpServletRequest request) {

        try {
            String location = request.getContextPath();
            tweet=tweetService.saveTweet(tweet);
            location += location.endsWith("/") ? "" + tweet.getId() : "/" + tweet.getId();
            return ResponseEntity.created(URI.create(location)).body(tweet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Method for updating user data
     *
     * @param id id
     * @param tweetDetails tweet object with changed data
     * @return ok or not found
     */
    @PutMapping(value = Constants.Api.Tweet.tweetBy)
    @ApiOperation("Updating tweet data. You can change: text")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Tweet.class),
            @ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found")})
    public ResponseEntity<Tweet> updateTweet(@PathVariable(value = "id") Integer id,
                                                 @Valid @RequestBody Tweet tweetDetails) {
        Tweet tweet = tweetService.findById(id);

        if (tweet == null) {
            return ResponseEntity.notFound().build();
        }
        if (tweetDetails.getText() != null) {
            tweet.setText(tweetDetails.getText());
            tweet.setDate(new Date());
        }

        try {
            tweet=tweetService.updateTweet(tweet);
            return ResponseEntity.ok(tweet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Method for deleting tweet
     *
     * @param id id
     * @return ok or not found
     */
    @DeleteMapping(value = Constants.Api.Tweet.tweetBy)
    @ApiOperation("Delete tweet with id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Tweet.class),
            @ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found")})
    public ResponseEntity<Tweet> deleteTweet(@PathVariable(value = "id") Integer id) {
        Tweet tweet = tweetService.findById(id);
        if (tweet == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            tweetService.deleteTweet(tweet);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
