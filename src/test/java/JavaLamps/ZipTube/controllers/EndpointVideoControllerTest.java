package JavaLamps.ZipTube.controllers;

import JavaLamps.ZipTube.models.Video;
import JavaLamps.ZipTube.repositories.VideoRepository;
import JavaLamps.ZipTube.services.AmazonS3ClientService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class EndpointVideoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VideoRepository videoRepository;

    @MockBean
    private AmazonS3ClientService amazonS3ClientService;

    @Test
    public void getAllVideoTest() throws Exception {

        Iterable<Video> videos = Arrays.asList(new Video(), new Video(), new Video());

        BDDMockito
                .given(videoRepository.findAll())
                .willReturn(videos);

        String expectedContent = mapToJson(videos);
        this.mvc.perform(MockMvcRequestBuilders
                .get("/videos"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

    @Test
    public void getVideoTest() throws Exception {
        Long givenId = 1L;
        Video video = new Video("url of the video", "video file name", "video title", "video description", new Date());
        BDDMockito
                .given(videoRepository.findById(givenId))
                .willReturn(Optional.of(video));
        BDDMockito
                .given(videoRepository.existsById(givenId))
                .willReturn(true);


        String expectedContent = mapToJson(video);
        this.mvc.perform(MockMvcRequestBuilders
                .get("/videos/" + givenId))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

    @Test
    public void createVideoTest() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "some original file name", "text/plain", "some file data".getBytes());

        Video video = new Video("some url", "some file name", "some title", "some description", new Date());
        BDDMockito
                .given(videoRepository.save(any(Video.class)))
                .willReturn(video);

        String expectedContent = mapToJson(video);
        this.mvc.perform(MockMvcRequestBuilders
                .multipart("/videos")
                .file(mockMultipartFile)
                .param("title", "some title")
                .param("description", "some description")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void updateTitleEndpointVideoTest() throws Exception {
        Long givenId = 1L;
        Video video = new Video("url of the video", "video file name", "video title", "video description", new Date());
        Video returnVideo = new Video();
        BDDMockito
                .given(videoRepository.findById(givenId))
                .willReturn(Optional.of(video));
        BDDMockito
                .given(videoRepository.existsById(givenId))
                .willReturn(true);
        BDDMockito
                .given(videoRepository.save(video))
                .willReturn(returnVideo);


        String expectedContent = mapToJson(returnVideo);
        this.mvc.perform(MockMvcRequestBuilders
                .put("/videos/" + givenId + "/title").param("title", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));

    }

    @Test
    public void updateDescriptionEndpointVideoTest() throws Exception {
        Long givenId = 1L;
        Video video = new Video("url of the video", "video file name", "video title", "video description", new Date());
        Video returnVideo = new Video();
        BDDMockito
                .given(videoRepository.findById(givenId))
                .willReturn(Optional.of(video));
        BDDMockito
                .given(videoRepository.existsById(givenId))
                .willReturn(true);
        BDDMockito
                .given(videoRepository.save(video))
                .willReturn(returnVideo);

        String expectedContent = mapToJson(returnVideo);
        this.mvc.perform(MockMvcRequestBuilders
                .put("/videos/" + givenId + "/description").param("description", "4"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));

    }

    @Test
    public void deleteEndpointVideoTest() throws Exception {
        Long givenId = 1L;
        Video video = new Video("url of the video", "video file name", "video title", "video description", new Date());

        BDDMockito
                .given(videoRepository.findById(givenId))
                .willReturn(Optional.of(video));
        BDDMockito
                .given(videoRepository.existsById(1L))
                .willReturn(true);

        this.mvc.perform(MockMvcRequestBuilders
                .delete("/videos/" + givenId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new StdDateFormat());
        return objectMapper.writeValueAsString(object);
    }
}