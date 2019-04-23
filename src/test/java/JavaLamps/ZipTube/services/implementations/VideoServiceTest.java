package JavaLamps.ZipTube.services.implementations;

import JavaLamps.ZipTube.models.Video;
import JavaLamps.ZipTube.repositories.VideoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VideoServiceTest {


    @InjectMocks
    VideoService videoService;
    @Mock
    VideoRepository videoRepository;


    @Test
    public void findAllTest() {
        //GIVEN
        Iterable<Video> expectedVideos = Arrays.asList(new Video(), new Video(), new Video());
        when(videoRepository.findAll()).thenReturn(expectedVideos);

        //WHEN
        Iterable<Video> actualVideoVideos = videoService.findAll();

        //THEN
        Assert.assertEquals(expectedVideos, actualVideoVideos);
    }

    @Test
    public void saveVideoTest() {
        //GIVEN
        Video inputVideo = new Video();
        Video expectedVideo = new Video();
        when(videoRepository.save(inputVideo)).thenReturn(expectedVideo);

        //WHEN
        Video actualVideoVideo = videoService.save(inputVideo);

        //THEN
        Assert.assertEquals(expectedVideo, actualVideoVideo);


    }

    @Test
    public void findByIdTest() {
        Video expectedVideo = new Video();
        when(videoRepository.findById(1L)).thenReturn(Optional.of(expectedVideo));
        when(videoRepository.existsById(1L)).thenReturn(true);

        //WHEN
        Video actualVideo = videoService.findById(1L);
        //THEN
        Assert.assertEquals(expectedVideo, actualVideo);

    }

    @Test (expected = Exception.class)
    public void findByIdExceptionTest() {
        Video expectedVideo = new Video();
        when(videoRepository.existsById(1L)).thenReturn(false);

        //WHEN
        Video actualVideo = videoService.findById(1L);
        //THEN
        Assert.assertEquals(expectedVideo, actualVideo);

   }


    @Test
    public void updateVideoTest() {
        Video inputVideo = new Video();
        Video expectedVideo = new Video();
        when(videoRepository.save(inputVideo)).thenReturn(expectedVideo);
        when(videoRepository.existsById(1L)).thenReturn(true);

        //WHEN
        Video actualVideo = videoService.update(1L, inputVideo);
        //THEN
        Assert.assertEquals(expectedVideo, actualVideo);
    }

    @Test (expected = Exception.class)
    public void updateVideoExceptionTest() {
        Video inputVideo = new Video();
        Video expectedVideo = new Video();
        when(videoRepository.existsById(1L)).thenReturn(false);

        //WHEN
        Video actualVideo = videoService.update(1L, inputVideo);
        //THEN
        Assert.assertEquals(expectedVideo, actualVideo);
    }


    @Test
    public void deleteByIdTest() {
        //GIVEN
        Boolean expected = true;
        when(videoRepository.existsById(1L)).thenReturn(true);

        //WHEN
        Boolean actualVideo = videoService.deleteById(1L);

        //THEN
        Assert.assertEquals(expected, actualVideo);
    }

    @Test (expected = Exception.class)
    public void deleteByIdExceptionTest() {
        //GIVEN
        Boolean expected = true;
        when(videoRepository.existsById(1L)).thenReturn(false);

        //WHEN
        Boolean actualVideo = videoService.deleteById(1L);

        //THEN
        Assert.assertEquals(expected, actualVideo);
    }

    @Test
    public void updateTitleTest() {
        Video video = new Video();
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));
        when(videoRepository.save(video)).thenReturn(video);
        when(videoRepository.existsById(1L)).thenReturn(true);

        //WHEN
        Video actualVideo = videoService.updateTitle(1L, "newTitle");
        //THEN
        Assert.assertEquals(video.getTitle(), actualVideo.getTitle());
    }

    @Test (expected = Exception.class)
    public void updateTitleExceptionTest() {
        Video video = new Video();
        when(videoRepository.existsById(1L)).thenReturn(false);

        //WHEN
        Video actualVideo = videoService.updateTitle(1L, "newTitle");
        //THEN
        Assert.assertEquals(video.getTitle(), actualVideo.getTitle());
    }

    @Test
    public void updateDescriptionTest() {
        Video video = new Video();
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));
        when(videoRepository.save(video)).thenReturn(video);
        when(videoRepository.existsById(1L)).thenReturn(true);

        //WHEN
        Video actualVideo = videoService.updateDescription(1L, "newDescription");
        //THEN
        Assert.assertEquals(video.getDescription(), actualVideo.getDescription());
    }

    @Test (expected = Exception.class)
    public void updateDescriptionExceptionTest() {
        Video video = new Video();
        when(videoRepository.existsById(1L)).thenReturn(false);

        //WHEN
        Video actualVideo = videoService.updateDescription(1L, "newDescription");
        //THEN
        Assert.assertEquals(video.getDescription(), actualVideo.getDescription());
    }
}