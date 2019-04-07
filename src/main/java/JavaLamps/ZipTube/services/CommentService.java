package JavaLamps.ZipTube.services;

import JavaLamps.ZipTube.models.Comment;
import JavaLamps.ZipTube.models.Video;
import JavaLamps.ZipTube.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private VideoService videoService;

    @Autowired
    public CommentService(CommentRepository commentRepository, VideoService videoService) {
        this.commentRepository = commentRepository;
        this.videoService = videoService;
    }

    public Iterable<Comment> index() {
        return commentRepository.findAll();
    }

    public Comment show(Long id) {
        return commentRepository.findById(id).get();
    }

    public Comment create(Comment comment) {
        Video video = comment.getVideo();
        if (video != null) {
            video = videoService.show(video.getId());
            comment.setVideo(video);
        }
        return commentRepository.save(comment);
    }

    public Comment update(Long id, Comment newCommentData) {
        if (!commentRepository.existsById(id))
            throw new NoSuchElementException();

        newCommentData.setId(id);
        return commentRepository.save(newCommentData);
    }

    public Boolean delete(Long id) {
        commentRepository.deleteById(id);
        return true;
    }
}
