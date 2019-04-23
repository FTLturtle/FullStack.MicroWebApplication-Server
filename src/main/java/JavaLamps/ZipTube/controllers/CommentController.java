package JavaLamps.ZipTube.controllers;

import JavaLamps.ZipTube.models.Comment;
import JavaLamps.ZipTube.services.implementations.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public ResponseEntity<Iterable<Comment>> getAllComments() {
        return new ResponseEntity<>(commentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/videos/{videoId}/comments")
    public ResponseEntity<Iterable<Comment>> getAllVideoComments(@PathVariable Long videoId) {
        return new ResponseEntity<>(commentService.findCommentsByVideoId(videoId), HttpStatus.OK);
    }

    @PostMapping("/comments")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        return new ResponseEntity<>(commentService.save(comment), HttpStatus.CREATED);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateCommentWithoutUpdatingPostDate(@PathVariable Long id, @RequestBody Comment comment) {
        return new ResponseEntity<>(commentService.updateWithoutUpdatingPostDate(id, comment), HttpStatus.OK);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}
