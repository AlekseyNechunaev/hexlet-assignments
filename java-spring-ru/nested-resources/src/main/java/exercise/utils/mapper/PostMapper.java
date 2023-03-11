package exercise.utils.mapper;

import exercise.dto.GetCommentDto;
import exercise.dto.GetPostDto;
import exercise.dto.PostDto;
import exercise.model.Comment;
import exercise.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentMapper commentMapper;

    @Mapping(source = "body", target = "body")
    @Mapping(source = "title", target = "title")
    public abstract Post map(PostDto dto);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "body", target = "body")
    @Mapping(target = "comments", expression = "java(takeComments(post))")
    public abstract GetPostDto map(Post post);

    protected List<GetCommentDto> takeComments(Post post) {
        List<Comment> comments = post.getComments();
        if (!CollectionUtils.isEmpty(comments)) {
            return comments.stream()
                    .map(commentMapper::map)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
