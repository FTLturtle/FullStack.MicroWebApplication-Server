package JavaLamps.ZipTube.controllers;

import JavaLamps.ZipTube.models.Comment;
import JavaLamps.ZipTube.models.Video;
import JavaLamps.ZipTube.services.implementations.CommentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentControllerTest {

    @InjectMocks
    CommentController commentController;

    @Mock
    CommentService commentService;

    @Test
    public void getAllCommentsTest() {
        // Given
        Iterable<Comment> comments = Arrays.asList(new Comment("firstBody", "firstName", new Date(), new Video()),
                new Comment("secondBody", "secondName", new Date(), new Video()),
                new Comment("secondBody", "secondName", new Date(), new Video()));

        when(commentService.findAll()).thenReturn(comments);
        ResponseEntity<Iterable<Comment>> expected = new ResponseEntity<>(comments, HttpStatus.OK);

        // When
        ResponseEntity<Iterable<Comment>> actual = commentController.getAllComments();

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getCommentTest() {
        // Given
        Comment comment = new Comment("", "", new Date(), new Video());
        when(commentService.findById(1L)).thenReturn(comment);
        ResponseEntity<Comment> expected = new ResponseEntity<>(comment, HttpStatus.OK);

        // When
        ResponseEntity<Comment> actual = commentController.getComment(1L);

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllVideoCommentsTest() {
        // Given
        Iterable<Comment> comments = Arrays.asList(new Comment("firstBody", "firstName", new Date(), new Video()),
                new Comment("secondBody", "secondName", new Date(), new Video()),
                new Comment("thirdBody", "thirdName", new Date(), new Video()));

        when(commentService.findCommentsByVideoId(1L)).thenReturn(comments);
        ResponseEntity<Iterable<Comment>> expected = new ResponseEntity<>(comments, HttpStatus.OK);

        // When
        ResponseEntity<Iterable<Comment>> actual = commentController.getAllVideoComments(1L);

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createCommentTest() {
        // Given
        Comment inputComment = new Comment("inputBody", "inputName", new Date(), new Video());
        Comment returnComment = new Comment("returnBody", "returnName", new Date(), new Video());
        when(commentService.save(inputComment)).thenReturn(returnComment);
        ResponseEntity<Comment> expected = new ResponseEntity<>(returnComment, HttpStatus.CREATED);

        // When
        ResponseEntity<Comment> actual = commentController.createComment(inputComment);

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateCommentWithoutUpdatingPostDateTest() {
        // Given
        Comment inputComment = new Comment("inputBody", "inputName", new Date(), new Video());
        Comment returnComment = new Comment("returnBody", "returnName", new Date(), new Video());
        when(commentService.updateWithoutUpdatingPostDate(1L, inputComment)).thenReturn(returnComment);
        ResponseEntity<Comment> expected = new ResponseEntity<>(returnComment, HttpStatus.OK);

        // When
        ResponseEntity<Comment> actual = commentController.updateCommentWithoutUpdatingPostDate(1L, inputComment);

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteCommentTest() {
        // Given
        when(commentService.deleteById(1L)).thenReturn(true);
        ResponseEntity<Boolean> expected = new ResponseEntity<>(true, HttpStatus.NO_CONTENT);

        // When
        ResponseEntity<Boolean> actual = commentController.deleteComment(1L);

        // Then
        Assert.assertEquals(expected, actual);
    }
}