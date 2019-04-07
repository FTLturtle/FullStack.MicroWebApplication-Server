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

    public Video show(Long id) {
        return videoRepository.findById(id).get();
    }

    public Video create(Video video) {
        return videoRepository.save(video);
    }

    public Video update(Long id, Video newVideoData) {
        if (!videoRepository.existsById(id))
            throw new NoSuchElementException();

        newVideoData.setId(id);
        return videoRepository.save(newVideoData);
    }

    public Boolean delete(Long id) {
        videoRepository.deleteById(id);
        return true;
    }
}
