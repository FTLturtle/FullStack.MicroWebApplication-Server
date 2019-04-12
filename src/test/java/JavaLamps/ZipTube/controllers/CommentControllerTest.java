package JavaLamps.ZipTube.controllers;

import JavaLamps.ZipTube.models.Comment;
import JavaLamps.ZipTube.models.Video;
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

}