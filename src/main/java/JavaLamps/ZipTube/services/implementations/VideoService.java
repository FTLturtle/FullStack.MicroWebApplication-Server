package JavaLamps.ZipTube.services.implementations;

import JavaLamps.ZipTube.models.Video;
import JavaLamps.ZipTube.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class VideoService {
    private VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Iterable<Video> findAll() {
        return videoRepository.findAll();
    }

    public Video save(Video video) {
        return videoRepository.save(video);
    }

    public Video findById(Long id) {
        checkIfValidId(id);
        return videoRepository.findById(id).get();
    }

    public Video update(Long id, Video newVideoData) {
        checkIfValidId(id);
        newVideoData.setId(id);
        return videoRepository.save(newVideoData);
    }

    public Boolean deleteById(Long id) {
        checkIfValidId(id);
        videoRepository.deleteById(id);
        return true;
    }

    public Video updateTitle(Long id, String newTitle) {
        checkIfValidId(id);
        Video video = findById(id);
        video.setTitle(newTitle);
        return videoRepository.save(video);
    }

    public Video updateDescription(Long id, String newDescription) {
        checkIfValidId(id);
        Video video = findById(id);
        video.setDescription(newDescription);
        return videoRepository.save(video);
    }

    public void checkIfValidId(Long id) {
        if (!videoRepository.existsById(id))
            throw new NoSuchElementException();
    }
}
