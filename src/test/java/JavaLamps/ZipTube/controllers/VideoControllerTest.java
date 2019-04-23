package JavaLamps.ZipTube.controllers;

import JavaLamps.ZipTube.models.Video;
import JavaLamps.ZipTube.services.AmazonS3ClientService;
import JavaLamps.ZipTube.services.implementations.VideoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VideoControllerTest {

    @InjectMocks
    VideoController videoController;

    @Mock
    AmazonS3ClientService amazonS3ClientService;

    @Mock
    VideoService videoService;

    @Test
    public void getAllVideoTest() {
        // Given
        Iterable<Video> videos = Arrays.asList(new Video(), new Video(), new Video());
        when(videoService.findAll()).thenReturn(videos);
        ResponseEntity<Iterable<Video>> expected = new ResponseEntity<>(videos, HttpStatus.OK);

        // When
        ResponseEntity<Iterable<Video>> actual = videoController.getAllVideos();

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getVideoTest() {
        // Given
        Video video = new Video("", "", "", "", new Date());
        when(videoService.findById(1L)).thenReturn(video);
        ResponseEntity<Video> expected = new ResponseEntity<>(video, HttpStatus.OK);

        // When
        ResponseEntity<Video> actual = videoController.getVideo(1L);

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createVideoTest() {
        // Given
        Video returnVideo = new Video();
        when(videoService.save(any(Video.class))).thenReturn(returnVideo);
        ResponseEntity<Video> expected = new ResponseEntity<>(returnVideo, HttpStatus.CREATED);

        // When
        ResponseEntity<Video> actual = videoController.createVideo(new MockMultipartFile("fileName", new byte[0]), new ArrayList<>(Arrays.asList("some title", "some description")));

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateTitleTest() {
        // Given
        Video video = new Video("", "", "", "", new Date());
        when(videoService.updateTitle(1L, "")).thenReturn(video);
        ResponseEntity<Video> expected = new ResponseEntity<>(video, HttpStatus.OK);

        // When
        ResponseEntity<Video> actual = videoController.updateVideoTitle(1L, "");

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateDescriptionTest() {
        // Given
        Video video = new Video("", "", "", "", new Date());
        when(videoService.updateDescription(1L, "")).thenReturn(video);
        ResponseEntity<Video> expected = new ResponseEntity<>(video, HttpStatus.OK);

        // When
        ResponseEntity<Video> actual = videoController.updateVideoDescription(1L, "");

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteVideoTest() {
        // Given
        Video video = new Video("", "", "", "", new Date());
        when(videoService.deleteById(1L)).thenReturn(true);
        when(videoService.findById(1L)).thenReturn(video);
        ResponseEntity<Boolean> expected = new ResponseEntity<>(true, HttpStatus.NO_CONTENT);

        // When
        ResponseEntity<Boolean> actual = videoController.deleteVideo(1L);

        // Then
        Assert.assertEquals(expected, actual);
    }
}