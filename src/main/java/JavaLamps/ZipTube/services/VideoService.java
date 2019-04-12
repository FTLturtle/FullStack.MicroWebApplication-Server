package JavaLamps.ZipTube.services;


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

    public Iterable<Video> index() {
        return videoRepository.findAll();
    }

    public Video create(Video video) {
        return videoRepository.save(video);
    }

    public Video show(Long id) {
        checkIfValidId(id);
        return videoRepository.findById(id).get();
    }

    public Video update(Long id, Video newVideoData) {
        checkIfValidId(id);
        newVideoData.setId(id);
        return videoRepository.save(newVideoData);
    }

    private void checkIfValidId(Long id) {
        if (!videoRepository.existsById(id))
            throw new NoSuchElementException();
    }

    public Boolean delete(Long id) {
        videoRepository.deleteById(id);
        return true;
    }

    public Video updateTitle(Long id, String newTitle) {
        Video video = show(id);
        video.setTitle(newTitle);
         return videoRepository.save(video);
    }

    public Video updateDescription(Long id, String newDescription) {
        Video video = show(id);
        video.setDescription(newDescription);
        return videoRepository.save(video);

    }

}
