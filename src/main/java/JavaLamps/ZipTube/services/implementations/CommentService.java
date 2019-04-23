package JavaLamps.ZipTube.services.implementations;

import JavaLamps.ZipTube.models.Comment;
import JavaLamps.ZipTube.models.Video;
import JavaLamps.ZipTube.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public Iterable<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(Long id) {
        checkIfValidId(id);
        return commentRepository.findById(id).get();
    }

    public Comment save(Comment comment) {
        Video video = comment.getVideo();
        if (video != null) {
            video = videoService.findById(video.getId());
            comment.setVideo(video);
        }
        Date date = new Date();
        comment.setPostDate(date);
        return commentRepository.save(comment);
    }

    public Comment update(Long id, Comment newCommentData) {
        checkIfValidId(id);
        newCommentData.setId(id);
        return commentRepository.save(newCommentData);
    }

    public Comment updateWithoutUpdatingPostDate(Long id, Comment newCommentData) {
        checkIfValidId(id);
        newCommentData.setId(id);
        newCommentData.setPostDate(commentRepository.findById(id).get().getPostDate());
        return commentRepository.save(newCommentData);
    }

    public Boolean deleteById(Long id) {
        checkIfValidId(id);
        commentRepository.deleteById(id);
        return true;
    }

    public Iterable<Comment> findCommentsByVideoId(Long videoId) {
        videoService.checkIfValidId(videoId);
        return commentRepository.findCommentsByVideo_Id(videoId);
    }

    public void checkIfValidId(Long id) {
        if (!commentRepository.existsById(id))
            throw new NoSuchElementException();
    }
}
