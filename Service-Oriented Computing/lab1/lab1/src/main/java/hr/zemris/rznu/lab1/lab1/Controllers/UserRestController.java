package hr.zemris.rznu.lab1.lab1.Controllers;

import hr.zemris.rznu.lab1.lab1.Models.Tweet;
import hr.zemris.rznu.lab1.lab1.Models.User;
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
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Controller for operations with users.
 */
@RestController
@RequestMapping(value = Constants.Api.User.userRoot)
@Api(value = "User Controller API", produces = MediaType.APPLICATION_JSON_VALUE,
        description = "Controller for operations with users, protected by Basic Authentication")
public class UserRestController {
    private static final String jsonResponseType = "application/json";

    @Autowired
    private UserService userService;

    @Autowired
    private TweetService tweetService;

    /**
     * Method for getting list of all users
     *
     * @return list of users (it can be empty)
     */
    @GetMapping(value = Constants.Api.User.getAllUsers)
    @ApiOperation("Fetch all users")
    @ApiResponse(code = 200, message = "OK", response = List.class)
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    /**
     * Method for getting user with id or email
     *
     * @param var id or email
     * @return user ili not found
     */
    @GetMapping(value = Constants.Api.User.userByIdOrEmail)
    @ApiOperation("Fetch user with id or email")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = User.class), @ApiResponse(code = 404, message = "Not Found")})
    public ResponseEntity<User> getUserByIdOrUsername(@PathVariable(value = "var") String var) {
        User user;
        try {
            user = userService.findById(Integer.parseInt(var));
        } catch (NumberFormatException e) {
            user = userService.findByEmail(var);
        }

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    /**
     * Method for creating new user
     *
     * @param user user
     * @return user
     */
    @PostMapping(value = Constants.Api.User.saveUser)
    @ApiOperation("Create new user")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created", response = User.class), @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, HttpServletRequest request) {
        try {
            User savedUser = userService.findByEmail(user.getEmail());
            if (ObjectUtils.isEmpty(savedUser)) {
                Optional<Integer> maxId=userService.findAll().stream().map(u->u.getId()).max(Comparator.comparing(i->i));
                if(maxId.isPresent()){
                    user.setId(maxId.get()+1);
                }else {
                    user.setId(1);
                }
                savedUser = userService.saveUser(user);
                String location = request.getContextPath();
                location += location.endsWith("/") ? "" + savedUser.getId() : "/" + savedUser.getId();
                return ResponseEntity.created(URI.create(location)).body(savedUser);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Method for updating user data
     *
     * @param var   user id or email
     * @param userDetails user object with changed data
     * @return ok or not found
     */
    @PutMapping(value = Constants.Api.User.userByIdOrEmail)
    @ApiOperation("Updating user data. You can change: email, first name and last name")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = User.class),
            @ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found")})
    public ResponseEntity<User> updateUser(@PathVariable(value = "var") String var, @RequestBody User userDetails) {
        User user;
        try {
            user = userService.findById(Integer.parseInt(var));
        } catch (NumberFormatException e) {
            user = userService.findByEmail(var);
        }

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if (userDetails.getEmail() != null)
            user.setEmail(userDetails.getEmail());
        if (userDetails.getFirstName() != null)
            user.setFirstName(userDetails.getFirstName());
        if (userDetails.getLastName() != null)
            user.setLastName(userDetails.getLastName());

        try {
            return ResponseEntity.ok(userService.updateUser(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Method for deleting user
     *
     * @param var user id or email
     * @return ok or not found
     */
    @DeleteMapping(value = Constants.Api.User.userByIdOrEmail)
    @ApiOperation("Delete user with id or email (also deletes user's tweets")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = User.class),
            @ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found")})
    public ResponseEntity<User> deleteUser(@PathVariable(value = "var") String var) {
        User user;
        try {
            user = userService.findById(Integer.parseInt(var));
        } catch (NumberFormatException e) {
            user = userService.findByEmail(var);
        }

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            List<Tweet> tweets = tweetService.findByUser(user);
            for(Tweet t:tweets){
                tweetService.deleteTweet(t);
            }
            userService.deleteUser(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Method for getting all tweets for one user
     * @param var user id or email
     * @return list of all tweets by specified user or not found if user does not exist
     */
    @GetMapping(value = Constants.Api.User.getAllTweetsForUser)
    @ApiOperation("Fetch all tweets by user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = List.class), @ApiResponse(code = 404, message = "Not Found")})
    public ResponseEntity<List<Tweet>> getAllTweetsForUser(@PathVariable(value = "var") String var){
        User user;
        try {
            user = userService.findById(Integer.parseInt(var));
        } catch (NumberFormatException e) {
            user = userService.findByEmail(var);
        }

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(tweetService.findByUser(user), HttpStatus.OK);
    }

}
