package hr.zemris.rznu.lab1.lab1.Controllers;

import hr.zemris.rznu.lab1.lab1.Models.Tweet;
import hr.zemris.rznu.lab1.lab1.Models.User;
import hr.zemris.rznu.lab1.lab1.Services.TweetService;
import hr.zemris.rznu.lab1.lab1.Services.UserService;
import hr.zemris.rznu.lab1.lab1.TestTemplate;
import hr.zemris.rznu.lab1.lab1.Util.Constants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TweetRestControllerTest extends TestTemplate {

    @Mock
    UserService userService;

    @Mock
    TweetService tweetService;

    @InjectMocks
    TweetRestController tweetRestController;

    private User testUser1;
    private User testUser2;
    private Tweet testTweet1;
    private Tweet testTweet2;
    private Date date= new Date(5000000000L);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tweetRestController).build();
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
        testTweet1 = new Tweet();
        testTweet1.setId(4);
        testTweet1.setText("Test tweet");
        testTweet1.setDate(date);
        testTweet1.setUser(testUser1);
        testTweet2 = new Tweet();
        testTweet2.setId(5);
        testTweet2.setText("Hakuna matata");
        testTweet2.setDate(date);
        testTweet2.setUser(testUser2);
    }

    @Test
    public void invalidURLTest() throws Exception {
        mockMvc.perform(get("/tweet/user")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllTweetsEmptyList() throws Exception{
        List<Tweet> tweets = new ArrayList<>();
        when(tweetService.getAllTweet()).thenReturn(tweets);

        mockMvc.perform(get(Constants.Api.Tweet.tweetRoot + Constants.Api.Tweet.getAllTweets)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0))).andReturn();
    }

    @Test
    public void getAllTweets() throws Exception{
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(testTweet1);
        tweets.add(testTweet2);
        when(tweetService.getAllTweet()).thenReturn(tweets);

        MvcResult result = mockMvc.perform(get(Constants.Api.Tweet.tweetRoot + Constants.Api.Tweet.getAllTweets)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andReturn();
        MockHttpServletResponse mockResponse = result.getResponse();
        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);

        verify(tweetService, times(1)).getAllTweet();
        verifyNoMoreInteractions(tweetService);
    }

    @Test
    public void getTweetById() throws Exception{
        when(tweetService.findById(4)).thenReturn(testTweet1);

        MvcResult result = mockMvc.perform(get(Constants.Api.Tweet.tweetRoot + Constants.Api.Tweet.tweetBy, 4))
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockResponse = result.getResponse();
        assertThat(mockResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");

        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);
        assertEquals(1, responseHeaders.size());
        assertEquals("Check for Content-Type header", "Content-Type", responseHeaders.iterator().next());
        String responseAsString = mockResponse.getContentAsString();
        assertTrue(responseAsString.equals("{\"id\":4,\"text\":\"Test tweet\",\"user\":{\"id\":4,\"email\":\"test@mail.com\",\"firstName\":\"ASD\",\"lastName\":\"QWE\"},\"date\":5000000000}"));

        verify(tweetService, times(1)).findById(4);
        verifyNoMoreInteractions(tweetService);
    }

    @Test
    public void getTweetByIdNotFound() throws Exception{
        when(tweetService.findById(4)).thenReturn(null);
        mockMvc.perform(get(Constants.Api.Tweet.tweetRoot + Constants.Api.Tweet.tweetBy, 4))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void createNewTweet() throws Exception {
        when(userService.findByEmail(Matchers.anyString())).thenReturn(testUser1);
        when(tweetService.saveTweet(Matchers.any(Tweet.class))).thenReturn(testTweet1);

        MvcResult result = mockMvc.perform(post(Constants.Api.Tweet.tweetRoot + Constants.Api.Tweet.saveTweet)
                .content("{\"id\":4,\"text\":\"Test tweet\",\"user\":{\"id\":4,\"email\":\"test@mail.com\",\"firstName\":\"ASD\",\"lastName\":\"QWE\"},\"date\":5000000000}")
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
        assertTrue(responseAsString.equals("{\"id\":4,\"text\":\"Test tweet\",\"user\":{\"id\":4,\"email\":\"test@mail.com\",\"firstName\":\"ASD\",\"lastName\":\"QWE\"},\"date\":5000000000}"));
    }

    @Test
    public void createNewTweetBadRequest() throws Exception {
        when(userService.findByEmail(Matchers.anyString())).thenReturn(null);
        when(tweetService.saveTweet(Matchers.any(Tweet.class))).thenReturn(testTweet1);
        mockMvc.perform(post(Constants.Api.Tweet.tweetRoot + Constants.Api.Tweet.saveTweet)
                .content("{\"id\":4,\"user\":{\"id\":4,\"lastName\":\"QWE\"},\"date\":5000000000}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    public void updateTweet() throws Exception {
        when(tweetService.findById(4)).thenReturn(testTweet1);

        MvcResult result = mockMvc.perform(put(Constants.Api.Tweet.tweetRoot+Constants.Api.Tweet.tweetBy, 4)
                .content("{\"id\":4,\"text\":\"XXXXXXXXXXX\",\"user\":{\"id\":4,\"email\":\"test@mail.com\",\"firstName\":\"ASD\",\"lastName\":\"QWE\"},\"date\":5000000000}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse mockResponse = result.getResponse();
        verify(tweetService, times(1)).updateTweet(testTweet1);
    }

    @Test
    public void updateTweetNotFound() throws Exception {
        when(tweetService.findById(4)).thenReturn(null);

        mockMvc.perform(put(Constants.Api.Tweet.tweetRoot+Constants.Api.Tweet.tweetBy, 4)
                .content("{\"id\":4,\"text\":\"XXXXXXXXXXX\",\"user\":{\"id\":4,\"email\":\"test@mail.com\",\"firstName\":\"ASD\",\"lastName\":\"QWE\"},\"date\":5000000000}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void updateTweetBadRequest() throws Exception {
        when(tweetService.findById(4)).thenReturn(testTweet1);

        mockMvc.perform(put(Constants.Api.Tweet.tweetRoot+Constants.Api.Tweet.tweetBy, 4)
                .content("{\"id\":4,\"text\":\"XXXXXXXXXXX\",\"user\":{\"id\":4,\"email\":\"test@mail.com\",\"firstName\":\"ASD\",\"lastName\":\"QWE\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void deleteTweet() throws Exception {
        when(tweetService.findById(4)).thenReturn(testTweet1);

        MvcResult result = mockMvc.perform(delete(Constants.Api.Tweet.tweetRoot+Constants.Api.Tweet.tweetBy, 4)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse mockResponse = result.getResponse();
        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);
        verify(tweetService, times(1)).deleteTweet(testTweet1);
    }

    @Test
    public void deleteTweetNotFound() throws Exception {
        when(tweetService.findById(4)).thenReturn(null);

        mockMvc.perform(delete(Constants.Api.Tweet.tweetRoot+Constants.Api.Tweet.tweetBy, 4)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deleteTweetBadRequest() throws Exception {
        when(tweetService.findById(4)).thenReturn(testTweet1);
        doAnswer((Answer) invocation -> {
            throw new RuntimeException();
        }).when(tweetService).deleteTweet(any(Tweet.class));

        mockMvc.perform(delete(Constants.Api.Tweet.tweetRoot+Constants.Api.Tweet.tweetBy, 4)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}
