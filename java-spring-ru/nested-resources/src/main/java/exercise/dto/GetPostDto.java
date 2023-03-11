package exercise.dto;

import java.util.List;

public class GetPostDto {
    private Long id;
    private String title;
    private String body;
    private List<GetCommentDto> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<GetCommentDto> getComments() {
        return comments;
    }

    public void setComments(List<GetCommentDto> comments) {
        this.comments = comments;
    }
}
