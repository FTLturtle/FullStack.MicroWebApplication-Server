package JavaLamps.ZipTube.controllers;

import JavaLamps.ZipTube.models.Comment;
import JavaLamps.ZipTube.models.Video;
import JavaLamps.ZipTube.repositories.CommentRepository;
import JavaLamps.ZipTube.repositories.VideoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class EndpointCommentControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private VideoRepository videoRepository;

    @Test
    public void getAllCommentsTest() throws Exception {
        // Given
        Comment comment1 = new Comment("body1", "posterName1", new Date(), new Video(4L));
        comment1.setId(1L);
        Comment comment2 = new Comment("body2", "posterName2", new Date(), new Video(5L));
        comment2.setId(2L);
        Comment comment3 = new Comment("body3", "posterName3", new Date(), new Video(6L));
        comment3.setId(3L);
        Iterable<Comment> commentList = Arrays.asList(comment1, comment2, comment3);
        BDDMockito.given(commentRepository.findAll()).willReturn(commentList);
        String expectedContent = mapToJson(commentList);

        // Then
        mvc.perform(MockMvcRequestBuilders
                .get("/comments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void getCommentTest() throws Exception {
        // Given
        Long givenId = 1L;
        Comment returnComment = new Comment("text of body", "text of name", new Date(), new Video());
        BDDMockito.given(commentRepository.findById(givenId)).willReturn(Optional.of(returnComment));
        BDDMockito.given(commentRepository.existsById(givenId)).willReturn(true);
        String expectedContent = mapToJson(returnComment);

        // Then
        mvc.perform(MockMvcRequestBuilders
                .get("/comments/" + givenId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void getAllVideoCommentsTest() throws Exception {
        // Given
        Video video = new Video(1L);
        Long givenId = 1L;
        Comment comment1 = new Comment("body1", "posterName1", new Date(), video);
        comment1.setId(2L);
        Comment comment2 = new Comment("body2", "posterName2", new Date(), video);
        comment2.setId(3L);
        Comment comment3 = new Comment("body3", "posterName3", new Date(), video);
        comment3.setId(4L);
        Iterable<Comment> commentList = Arrays.asList(new Comment(), new Comment(), new Comment());
        BDDMockito.given(commentRepository.findCommentsByVideo_Id(givenId)).willReturn(commentList);
        BDDMockito.given(videoRepository.existsById(givenId)).willReturn(true);
        String expectedContent = mapToJson(commentList);

        // Then
        mvc.perform(MockMvcRequestBuilders
                .get("/videos/1/comments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void createCommentTest() throws Exception {
        // Given
        String jsonInput = mapToJson(new Comment("text of body", "text of name", null, null));

        Comment returnComment = new Comment("text of body", "text of name", new Date(), new Video());
        BDDMockito.given(commentRepository.save(any(Comment.class))).willReturn(returnComment);
        String expectedContent = mapToJson(returnComment);

        // Then
        mvc.perform(MockMvcRequestBuilders
                .post("/comments").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonInput))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void updateCommentWithoutUpdatingPostDateTest() throws Exception {
        // Given
        String jsonInput = mapToJson(new Comment("text of body", "text of name", null, null));

        Comment returnComment = new Comment("text of body", "text of name", new Date(), new Video());

        BDDMockito.given(commentRepository.save(any(Comment.class))).willReturn(returnComment);
        String expectedContent = mapToJson(returnComment);

        Long givenId = 1L;
        BDDMockito.given(commentRepository.findById(givenId)).willReturn(Optional.of(returnComment));
        BDDMockito.given(commentRepository.existsById(givenId)).willReturn(true);

        // Then
        mvc.perform(MockMvcRequestBuilders
                .put("/comments/" + givenId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonInput))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void deleteCommentTest() throws Exception {
        // Given
        Long givenId = 1L;
        BDDMockito.given(commentRepository.existsById(givenId)).willReturn(true);

        // Then
        mvc.perform(MockMvcRequestBuilders
                .delete("/comments/" + givenId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new StdDateFormat());
        return objectMapper.writeValueAsString(object);
    }
}