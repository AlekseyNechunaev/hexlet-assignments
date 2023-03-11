package exercise.service;

import exercise.model.Comment;

import java.util.List;

public interface CommentService {

    Comment findById(Long id);

    List<Comment> findAllByPostId(Long postId);

    Comment findByCommentIdAndPostId(Long postId, Long commentId);

    void create(Comment comment, Long postId);

    void update(Long commentId, Long postId, Comment updatedComment);

    void delete(Long commentId, Long postId);
}
