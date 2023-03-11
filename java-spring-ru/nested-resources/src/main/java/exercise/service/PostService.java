package exercise.service;

import exercise.model.Post;

import java.util.List;

public interface PostService {

    Post findById(Long id);

    List<Post> findAll();

    void create(Post post);

    void update(Long id, Post updatedPost);

    void delete(Long id);

    boolean existsById(Long id);
}
