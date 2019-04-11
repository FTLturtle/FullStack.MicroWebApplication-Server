package JavaLamps.ZipTube.controllers;

import JavaLamps.ZipTube.models.Video;
import JavaLamps.ZipTube.services.AmazonS3ClientService;
import JavaLamps.ZipTube.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Date;

@RestController
public class VideoController {
    private VideoService videoService;
    private AmazonS3ClientService amazonS3ClientService;


    @Autowired
    public VideoController(VideoService videoService, AmazonS3ClientService amazonS3ClientService) {
        this.videoService = videoService;
        this.amazonS3ClientService = amazonS3ClientService;
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
    public ResponseEntity<Video> create(@RequestPart MultipartFile file,@RequestPart String title, @RequestPart String description) {
        String fileName = file.getOriginalFilename();
        this.amazonS3ClientService.uploadFileToS3Bucket(file, fileName, true);
        Date date = new Date();
        String url = amazonS3ClientService.getFileUrl(fileName);
        Video video = new Video(url, fileName, title, description, date);
        return new ResponseEntity<>(videoService.create(video), HttpStatus.CREATED);
    }

//    @PutMapping("/videos/{id}")
//    public ResponseEntity<Video> update(@PathVariable Long id, @RequestBody Video video) {
//        return new ResponseEntity<>(videoService.update(id, video), HttpStatus.OK);
//    }

    @PutMapping("/videos/{id}/title")
    public ResponseEntity<Video> updateTitle(@PathVariable Long id, @RequestParam String title) {
        return new ResponseEntity<>(videoService.updateTitle(id, title), HttpStatus.OK);
    }

    @PutMapping("/videos/{id}/description")
    public ResponseEntity<Video> updateDescription(@PathVariable Long id, @RequestParam String description) {
        return new ResponseEntity<>(videoService.updateDescription(id, description), HttpStatus.OK);
    }

    @DeleteMapping("/videos/{id}")
    public ResponseEntity<Boolean> destroy(@PathVariable Long id) {
        String fileName = videoService.show(id).getFileName();
        this.amazonS3ClientService.deleteFileFromS3Bucket(fileName);
        return new ResponseEntity<>(videoService.delete(id), HttpStatus.NO_CONTENT);
    }
}
