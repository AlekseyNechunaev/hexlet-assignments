package exercise.service;

import exercise.model.Post;
import exercise.model.PostState;

public interface PostService {

    Iterable<Post> findAllPublished(PostState state);

    Post save(Post post);

    Post findById(Long id);

    boolean publish(Post post);

    boolean archive(Post post);
}
