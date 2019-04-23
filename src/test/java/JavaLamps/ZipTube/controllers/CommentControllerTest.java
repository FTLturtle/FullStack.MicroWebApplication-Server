package JavaLamps.ZipTube.controllers;

import JavaLamps.ZipTube.models.Comment;
import JavaLamps.ZipTube.models.Video;
<<<<<<< HEAD
import JavaLamps.ZipTube.services.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;


@RunWith(SpringRunner.class)
@WebMvcTest(value=CommentController.class, secure = false)
public class CommentControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTest() throws Exception {

        Comment mockComment = new Comment();
        //GregorianCalendar cal=new GregorianCalendar();
        mockComment.setId(1L);
        mockComment.setBody("This is first comment");
        mockComment.setPosterName("Swapna");
        mockComment.setPostDate(new Date());
        mockComment.setVideo(new Video());

        String inputInJson = this.mapToJason(mockComment);

        String URI = "https://javalamps-ziptube.herokuapp.com/comments";

        when(commentService.create(Mockito.any(Comment.class))).thenReturn(mockComment);

        RequestBuilder requestBuilder= MockMvcRequestBuilders
                                    .post(URI)
                                    .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                                    .contentType(MediaType.APPLICATION_JSON);

            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();
        assertThat(outputInJson).isEqualTo(inputInJson);

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    private String mapToJason (Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new StdDateFormat());

        return objectMapper.writeValueAsString(object);
    }

    @Test
    public void showTest() throws Exception {

        Comment mockComment = new Comment();

        mockComment.setId(1L);
        mockComment.setBody("This is first comment");
        mockComment.setPosterName("Swapna");
        mockComment.setPostDate(new Date());
        mockComment.setVideo(new Video());

        String URI = "/comments/1";

        when(commentService.show( Mockito.anyLong())).thenReturn(mockComment);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expectedJson = this.mapToJason(mockComment);
        String outputJson = result.getResponse().getContentAsString();
        System.out.println("output is"+outputJson);
        assertThat(outputJson).isEqualTo(expectedJson);


    }

=======
import JavaLamps.ZipTube.services.implementations.CommentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentControllerTest {

    @InjectMocks
    CommentController commentController;

    @Mock
    CommentService commentService;

    @Test
    public void getAllCommentsTest() {
        // Given
        Iterable<Comment> comments = Arrays.asList(new Comment("firstBody", "firstName", new Date(), new Video()),
                new Comment("secondBody", "secondName", new Date(), new Video()),
                new Comment("secondBody", "secondName", new Date(), new Video()));

        when(commentService.findAll()).thenReturn(comments);
        ResponseEntity<Iterable<Comment>> expected = new ResponseEntity<>(comments, HttpStatus.OK);

        // When
        ResponseEntity<Iterable<Comment>> actual = commentController.getAllComments();

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getCommentTest() {
        // Given
        Comment comment = new Comment("", "", new Date(), new Video());
        when(commentService.findById(1L)).thenReturn(comment);
        ResponseEntity<Comment> expected = new ResponseEntity<>(comment, HttpStatus.OK);

        // When
        ResponseEntity<Comment> actual = commentController.getComment(1L);

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllVideoCommentsTest() {
        // Given
        Iterable<Comment> comments = Arrays.asList(new Comment("firstBody", "firstName", new Date(), new Video()),
                new Comment("secondBody", "secondName", new Date(), new Video()),
                new Comment("thirdBody", "thirdName", new Date(), new Video()));

        when(commentService.findCommentsByVideoId(1L)).thenReturn(comments);
        ResponseEntity<Iterable<Comment>> expected = new ResponseEntity<>(comments, HttpStatus.OK);

        // When
        ResponseEntity<Iterable<Comment>> actual = commentController.getAllVideoComments(1L);

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createCommentTest() {
        // Given
        Comment inputComment = new Comment("inputBody", "inputName", new Date(), new Video());
        Comment returnComment = new Comment("returnBody", "returnName", new Date(), new Video());
        when(commentService.save(inputComment)).thenReturn(returnComment);
        ResponseEntity<Comment> expected = new ResponseEntity<>(returnComment, HttpStatus.CREATED);

        // When
        ResponseEntity<Comment> actual = commentController.createComment(inputComment);

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateCommentWithoutUpdatingPostDateTest() {
        // Given
        Comment inputComment = new Comment("inputBody", "inputName", new Date(), new Video());
        Comment returnComment = new Comment("returnBody", "returnName", new Date(), new Video());
        when(commentService.updateWithoutUpdatingPostDate(1L, inputComment)).thenReturn(returnComment);
        ResponseEntity<Comment> expected = new ResponseEntity<>(returnComment, HttpStatus.OK);

        // When
        ResponseEntity<Comment> actual = commentController.updateCommentWithoutUpdatingPostDate(1L, inputComment);

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteCommentTest() {
        // Given
        when(commentService.deleteById(1L)).thenReturn(true);
        ResponseEntity<Boolean> expected = new ResponseEntity<>(true, HttpStatus.NO_CONTENT);

        // When
        ResponseEntity<Boolean> actual = commentController.deleteComment(1L);

        // Then
        Assert.assertEquals(expected, actual);
    }
>>>>>>> master
}