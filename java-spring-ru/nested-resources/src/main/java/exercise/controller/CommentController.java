package exercise.controller;

import exercise.dto.CommentDto;
import exercise.dto.GetCommentDto;
import exercise.service.CommentService;
import exercise.utils.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/posts")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @GetMapping(path = "/{postId}/comments")
    public List<GetCommentDto> findAllCommentsByPostId(@PathVariable Long postId) {
        return commentService.findAllByPostId(postId).stream()
                .map(commentMapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{postId}/comments/{commentId}")
    public GetCommentDto findCommentByPostIdAndCommentId(@PathVariable Long postId, @PathVariable Long commentId) {
        return commentMapper.map(commentService.findByCommentIdAndPostId(postId, commentId));
    }

    @PostMapping(path = "/{postId}/comments")
    public void create(@PathVariable Long postId, @RequestBody CommentDto dto) {
        commentService.create(commentMapper.map(dto), postId);
    }

    @PatchMapping(path = "/{postId}/comments/{commentId}")
    public void update(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentDto dto) {
        commentService.update(
                commentId,
                postId,
                commentMapper.map(dto)
        );
    }

    @DeleteMapping(path = "/{postId}/comments/{commentId}")
    public void delete(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.delete(commentId, postId);
    }
}
