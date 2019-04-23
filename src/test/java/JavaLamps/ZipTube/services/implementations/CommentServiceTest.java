package JavaLamps.ZipTube.services.implementations;

import JavaLamps.ZipTube.models.Comment;
import JavaLamps.ZipTube.models.Video;
import JavaLamps.ZipTube.repositories.CommentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    CommentRepository commentRepository;

    @Mock
    VideoService videoService;


    @Test
    public void findAllTest() {
        //GIVEN
        Iterable<Comment> comments = Arrays.asList(new Comment(), new Comment(), new Comment());
        when(commentRepository.findAll()).thenReturn(comments);

        //WHEN
        Iterable<Comment> actual = commentService.findAll();

        //THEN
        Assert.assertEquals(comments, actual);
    }

    @Test
    public void findByIdTest() {
        //GIVEN
        Comment comment = new Comment();
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentRepository.existsById(1L)).thenReturn(true);

        //WHEN
        Comment actual = commentService.findById(1L);

        //THEN
        Assert.assertEquals(comment, actual);
    }

    @Test(expected = Exception.class)
    public void findByIdExceptionTest() {
        //GIVEN
        Comment comment = new Comment();
        when(commentRepository.existsById(1L)).thenReturn(false);

        //WHEN
        Comment actual = commentService.findById(1L);

        //THEN
        Assert.assertEquals(comment, actual);
    }

    @Test
    public void saveCommentTest() {
        Video idVideo = new Video();
        Video videoWithData = new Video("", "", "", "", new Date());
        idVideo.setId(1L);
        videoWithData.setId(1L);
        Comment comment = new Comment();
        comment.setVideo(idVideo);
        when(videoService.findById(1L)).thenReturn(videoWithData);
        when(commentRepository.save(comment)).thenReturn(comment);

        Comment actual = commentService.save(comment);

        Assert.assertEquals(videoWithData, actual.getVideo());
        Assert.assertNotNull(actual.getPostDate());
    }

    @Test
    public void updateCommentTest() {
        //GIVEN
        Comment comments = new Comment();
        when(commentRepository.save(comments)).thenReturn(comments);
        when(commentRepository.existsById(1L)).thenReturn(true);

        //WHEN
        Comment actual = commentService.update(1L, comments);

        //THEN
        Assert.assertEquals(comments, actual);
    }

    @Test(expected = Exception.class)
    public void updateCommentExceptionTest() {
        //GIVEN
        Comment comments = new Comment();
        when(commentRepository.existsById(1L)).thenReturn(false);

        //WHEN
        Comment actual = commentService.update(1L, comments);

        //THEN
        Assert.assertEquals(comments, actual);
    }

    @Test
    public void updateWithoutUpdatingPostDateTest() {
        //GIVEN
        Date oldDate = new Date();
        Date updateDate = new Date();
        Comment updateComment = new Comment("body", "name", updateDate, new Video());
        Comment oldComment = new Comment("body", "name", oldDate, new Video());
        when(commentRepository.existsById(1L)).thenReturn(true);
        when(commentRepository.save(updateComment)).thenReturn(updateComment);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(oldComment));

        Comment actualComment = commentService.updateWithoutUpdatingPostDate(1L, updateComment);

        Assert.assertEquals(oldComment.getPostDate(), actualComment.getPostDate());
    }

    @Test(expected = Exception.class)
    public void updateWithoutUpdatingPostDateExceptionTest() {
        //GIVEN
        Date oldDate = new Date();
        Date updateDate = new Date();
        Comment updateComment = new Comment("body", "name", updateDate, new Video());
        Comment oldComment = new Comment("body", "name", oldDate, new Video());
        when(commentRepository.existsById(1L)).thenReturn(false);


        Comment actualComment = commentService.updateWithoutUpdatingPostDate(1L, updateComment);

        Assert.assertEquals(oldComment.getPostDate(), actualComment.getPostDate());
    }

    @Test
    public void deleteByIdTest() {
        //GIVEN
        when(commentRepository.existsById(1L)).thenReturn(true);

        //WHEN
        Boolean actual = commentService.deleteById(1L);

        //THEN
        Assert.assertTrue(actual);
    }

    @Test(expected = Exception.class)
    public void deleteByIdExceptionTest() {
        //WHEN
        when(commentRepository.existsById(1L)).thenReturn(false);

        //WHEN
        Boolean actual = commentService.deleteById(1L);

        //THEN
        Assert.assertTrue(actual);
    }

    @Test
    public void findCommentsByVideoIdTest() {
        //GIVEN
        Iterable<Comment> comments = Arrays.asList(new Comment(), new Comment(), new Comment());
        when(commentRepository.findCommentsByVideo_Id(1L)).thenReturn(comments);

        //WHEN
        Iterable<Comment> actual = commentService.findCommentsByVideoId(1L);

        //THEN
        Assert.assertEquals(comments, actual);

    }
}