package exercise.utils.mapper;

import exercise.dto.CommentDto;
import exercise.dto.GetCommentDto;
import exercise.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "content", target = "content")
    Comment map(CommentDto dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "content")
    GetCommentDto map(Comment comment);
}
