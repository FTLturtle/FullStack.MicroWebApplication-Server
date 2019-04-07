package JavaLamps.ZipTube.controllers;

import JavaLamps.ZipTube.models.Video;
import JavaLamps.ZipTube.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VideoController {
    private VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/videos")
    public ResponseEntity<Iterable<Video>> index() {
        return new ResponseEntity<>(videoService.index(), HttpStatus.OK);
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<Video> show(@PathVariable Long id) {
        return new ResponseEntity<>(videoService.show(id), HttpStatus.OK);
    }

    @PostMapping("/videos")
    public ResponseEntity<Video> create(@RequestBody Video video) {
        return new ResponseEntity<>(videoService.create(video), HttpStatus.CREATED);
    }

    @PutMapping("/videos/{id}")
    public ResponseEntity<Video> update(@PathVariable Long id, @RequestBody Video video) {
        return new ResponseEntity<>(videoService.update(id, video), HttpStatus.OK);
    }

    @DeleteMapping("/videos/{id}")
    public ResponseEntity<Boolean> destroy(@PathVariable Long id) {
        return new ResponseEntity<>(videoService.delete(id), HttpStatus.NO_CONTENT);
    }
}
