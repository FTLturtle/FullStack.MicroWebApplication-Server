package JavaLamps.ZipTube.repositories;

import JavaLamps.ZipTube.models.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    Iterable<Comment> findCommentsByVideo_Id(Long id);
}
