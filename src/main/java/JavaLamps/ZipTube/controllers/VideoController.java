package JavaLamps.ZipTube.controllers;

import JavaLamps.ZipTube.models.Video;
import JavaLamps.ZipTube.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VideoController {
    private VideoService service;

    @Autowired
    public VideoController(VideoService service) {
        this.service = service;
    }

    @GetMapping("/departments")
    public ResponseEntity<Iterable<Video>> index() {
        return new ResponseEntity<>(service.index(), HttpStatus.OK);
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<Video> show(@PathVariable Long id) {
        return new ResponseEntity<>(service.show(id), HttpStatus.OK);
    }

    @PostMapping("/departments")
    public ResponseEntity<Video> create(@RequestBody Video video) {
        return new ResponseEntity<>(service.create(video), HttpStatus.CREATED);
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<Video> update(@PathVariable Long id, @RequestBody Video video) {
        return new ResponseEntity<>(service.update(id, video), HttpStatus.OK);
    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Boolean> destroy(@PathVariable Long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.NO_CONTENT);
    }
}
