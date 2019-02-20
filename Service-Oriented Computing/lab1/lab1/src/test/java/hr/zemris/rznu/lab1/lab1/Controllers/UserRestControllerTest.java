package hr.zemris.rznu.lab1.lab1.Controllers;

import hr.zemris.rznu.lab1.lab1.Models.Tweet;
import hr.zemris.rznu.lab1.lab1.Models.User;
import hr.zemris.rznu.lab1.lab1.Services.TweetService;
import hr.zemris.rznu.lab1.lab1.Services.UserService;
import hr.zemris.rznu.lab1.lab1.TestTemplate;
import hr.zemris.rznu.lab1.lab1.Util.Constants;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

public class UserRestControllerTest extends TestTemplate {

    @Mock
    UserService userService;

    @Mock
    TweetService tweetService;

    @InjectMocks
    UserRestController userRestController;

    private User testUser1;
    private User testUser2;
    private Tweet testTweet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
        testUser1 = new User();
        testUser1.setId(4);
        testUser1.setEmail("test@mail.com");
        testUser1.setFirstName("ASD");
        testUser1.setLastName("QWE");
        testUser2 = new User();
        testUser2.setId(5);
        testUser2.setEmail("test2@mail.com");
        testUser2.setFirstName("YXCV");
        testUser2.setLastName("MMMMMMMMMM");
        testTweet = new Tweet();
        testTweet.setId(4);
        testTweet.setText("Test tweet");
        testTweet.setDate(new Date());
        testTweet.setUser(testUser1);
    }

    @Test
    public void invalidURLTest() throws Exception {
        mockMvc.perform(get("/tweetUser")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllUsersEmptyList() throws Exception{
        List<User> users = new ArrayList<>();
        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get(Constants.Api.User.userRoot + Constants.Api.User.getAllUsers)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0))).andReturn();
    }

    @Test
    public void getAllUsers() throws Exception{
        List<User> users = new ArrayList<>();
        users.add(testUser1);
        users.add(testUser2);
        when(userService.findAll()).thenReturn(users);

        MvcResult result = mockMvc.perform(get(Constants.Api.User.userRoot + Constants.Api.User.getAllUsers)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andReturn();
        MockHttpServletResponse mockResponse = result.getResponse();
        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);

        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void getUserById() throws Exception{
        when(userService.findById(4)).thenReturn(testUser1);

        MvcResult result = mockMvc.perform(get(Constants.Api.User.userRoot + Constants.Api.User.userByIdOrEmail, 4))
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockResponse = result.getResponse();
        assertThat(mockResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");

        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);
        assertEquals(1, responseHeaders.size());
        assertEquals("Check for Content-Type header", "Content-Type", responseHeaders.iterator().next());
        String responseAsString = mockResponse.getContentAsString();
        assertTrue(responseAsString.equals("{\"id\":4,\"email\":\"test@mail.com\",\"firstName\":\"ASD\",\"lastName\":\"QWE\"}"));

        verify(userService, times(1)).findById(4);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void getUserByEmail() throws Exception{
        when(userService.findByEmail("test@mail")).thenReturn(testUser1);

        MvcResult result = mockMvc.perform(get(Constants.Api.User.userRoot + Constants.Api.User.userByIdOrEmail, "test@mail"))
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockResponse = result.getResponse();
        assertThat(mockResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");

        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);
        assertEquals(1, responseHeaders.size());
        assertEquals("Check for Content-Type header", "Content-Type", responseHeaders.iterator().next());
        String responseAsString = mockResponse.getContentAsString();
        assertTrue(responseAsString.equals("{\"id\":4,\"email\":\"test@mail.com\",\"firstName\":\"ASD\",\"lastName\":\"QWE\"}"));

        verify(userService, times(1)).findByEmail("test@mail");
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void getUserNoUserFound() throws Exception {
        MvcResult result = mockMvc.perform(get(Constants.Api.User.userRoot + Constants.Api.User.userByIdOrEmail, 4))
                .andExpect(status().isNotFound())
                .andReturn();
        MockHttpServletResponse mockResponse = result.getResponse();
        assertThat(mockResponse.getStatus()).isEqualTo(404);

        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);
    }

    @Test
    public void createNewUser() throws Exception {
        when(userService.saveUser(testUser1)).thenReturn(testUser1);

        MvcResult result = mockMvc.perform(post(Constants.Api.User.userRoot + Constants.Api.User.saveUser)
                .content("{\"id\":4,\"email\":\"test@mail.com\",\"firstName\":\"ASD\",\"lastName\":\"QWE\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        MockHttpServletResponse mockResponse = result.getResponse();
        assertThat(mockResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");

        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);
        assertEquals(2, responseHeaders.size());
        assertEquals("Check for Location header", "Location", responseHeaders.iterator().next());
        String responseAsString = mockResponse.getContentAsString();
        assertTrue(responseAsString.equals("{\"id\":4,\"email\":\"test@mail.com\",\"firstName\":\"ASD\",\"lastName\":\"QWE\"}"));
    }

    @Test
    public void createNewUserEmpty() throws Exception {
        mockMvc.perform(post(Constants.Api.User.userRoot + Constants.Api.User.saveUser)
                .content("{\"id\":,\"email\":\",\"firstName\":\",\"lastName\":\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    public void createNewUserAlreadyExists() throws Exception {
        when(userService.findByEmail("test@gmail.com")).thenReturn(testUser1);
        mockMvc.perform(post(Constants.Api.User.userRoot + Constants.Api.User.saveUser)
                .content("{\"id\":4,\"email\":\"test@mail.com\",\"firstName\":\"ASD\",\"lastName\":\"QWE\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void updateUser() throws Exception {
        when(userService.findById(4)).thenReturn(testUser1);

        MvcResult result = mockMvc.perform(put(Constants.Api.User.userRoot+Constants.Api.User.userByIdOrEmail, 4)
                .content("{\"firstName\":\"XXXXXXXXXXX\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse mockResponse = result.getResponse();
        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);
        verify(userService, times(1)).updateUser(testUser1);
    }

    @Test
    public void updateUserNoUserFound() throws Exception {
        when(userService.findById(4)).thenReturn(null);

        MvcResult result = mockMvc.perform(put(Constants.Api.User.userRoot+Constants.Api.User.userByIdOrEmail, 4)
                .content("{\"firstName\":\"XXXXXXXXXXX\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        MockHttpServletResponse mockResponse = result.getResponse();
        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);
    }

    @Test
    public void deleteUser() throws Exception {
        when(userService.findById(4)).thenReturn(testUser1);

        MvcResult result = mockMvc.perform(delete(Constants.Api.User.userRoot+Constants.Api.User.userByIdOrEmail, 4)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse mockResponse = result.getResponse();
        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);
        verify(userService, times(1)).deleteUser(testUser1);
    }

    @Test
    public void deleteUserNotFound() throws Exception {
        when(userService.findById(4)).thenReturn(null);

        MvcResult result = mockMvc.perform(delete(Constants.Api.User.userRoot+Constants.Api.User.userByIdOrEmail, 4)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        MockHttpServletResponse mockResponse = result.getResponse();
        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);
    }

    @Test
    public void getAllTweetsForUser() throws Exception{
        when(userService.findById(4)).thenReturn(testUser1);
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(testTweet);
        when(tweetService.findByUser(testUser1)).thenReturn(tweets);

        mockMvc.perform(get(Constants.Api.User.userRoot + Constants.Api.User.getAllTweetsForUser, 4)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))).andReturn();
    }

    @Test
    public void getAllTweetsForUserNotFound() throws Exception{
        when(userService.findById(4)).thenReturn(null);

        mockMvc.perform(get(Constants.Api.User.userRoot + Constants.Api.User.getAllTweetsForUser, 4)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
