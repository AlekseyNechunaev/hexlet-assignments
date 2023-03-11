package exercise.service;

import exercise.handle.ResourceNotFoundException;
import exercise.model.Comment;
import exercise.model.Post;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostService postService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("comment with this id not found"));
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        if (!postService.existsById(postId)) {
            throw new ResourceNotFoundException("post with this id not found");
        }
        return commentRepository.findAllByPostId(postId);
    }

    @Override
    public Comment findByCommentIdAndPostId(Long postId, Long commentId) {
        if (!postService.existsById(postId)) {
            throw new ResourceNotFoundException("post with this id not found");
        }
        return commentRepository.findCommentByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("comment with this id not found"));
    }

    @Override
    public void create(Comment comment, Long postId) {
        Post post = postService.findById(postId);
        comment.setPost(post);
        commentRepository.save(comment);
    }

    @Override
    public void update(Long commentId, Long postId, Comment updatedComment) {
        if (!postService.existsById(postId)) {
            throw new ResourceNotFoundException("post with this id not found");
        }
        Comment comment = findById(commentId);
        comment.setContent(updatedComment.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void delete(Long commentId, Long postId) {
        Comment comment = findByCommentIdAndPostId(postId, commentId);
        commentRepository.delete(comment);
    }
}
