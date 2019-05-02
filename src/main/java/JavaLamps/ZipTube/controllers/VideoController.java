package JavaLamps.ZipTube.controllers;

import JavaLamps.ZipTube.models.Video;
import JavaLamps.ZipTube.services.AmazonS3ClientService;
import JavaLamps.ZipTube.services.implementations.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class VideoController {
    private VideoService videoService;
    private AmazonS3ClientService amazonS3ClientService;


    @Autowired
    public VideoController(VideoService videoService, AmazonS3ClientService amazonS3ClientService) {
        this.videoService = videoService;
        this.amazonS3ClientService = amazonS3ClientService;
    }

    @GetMapping("/videos")
    public ResponseEntity<Iterable<Video>> getAllVideos() {
        return new ResponseEntity<>(videoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<Video> getVideo(@PathVariable Long id) {
        return new ResponseEntity<>(videoService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/videos")
    public ResponseEntity<Video> createVideo(@RequestPart MultipartFile file, @RequestParam String title, @RequestParam String description) {
        String fileName = file.getOriginalFilename();
        this.amazonS3ClientService.uploadFileToS3Bucket(file, fileName, true);
        Date date = new Date();
        String url = amazonS3ClientService.getFileUrl(fileName);

        Video video = new Video(url, fileName, title, description, date);
        return new ResponseEntity<>(videoService.save(video), HttpStatus.CREATED);
    }

    @PutMapping("/videos/{id}/title")
    public ResponseEntity<Video> updateVideoTitle(@PathVariable Long id, @RequestParam String title) {
        return new ResponseEntity<>(videoService.updateTitle(id, title), HttpStatus.OK);
    }

    @PutMapping("/videos/{id}/description")
    public ResponseEntity<Video> updateVideoDescription(@PathVariable Long id, @RequestParam String description) {
        return new ResponseEntity<>(videoService.updateDescription(id, description), HttpStatus.OK);
    }

    @DeleteMapping("/videos/{id}")
    public ResponseEntity<Boolean> deleteVideo(@PathVariable Long id) {
        String fileName = videoService.findById(id).getFileName();
        amazonS3ClientService.deleteFileFromS3Bucket(fileName);
        return new ResponseEntity<>(videoService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}
