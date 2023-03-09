package exercise.service;

import exercise.PostNotFoundException;
import exercise.model.Post;
import exercise.model.PostState;
import exercise.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Iterable<Post> findAllPublished(PostState state) {
        return postRepository.findAllByState(PostState.PUBLISHED);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    public boolean publish(Post post) {
        if (post.getState() == PostState.CREATED) {
            post.setState(PostState.PUBLISHED);
            save(post);
            return true;
        }
        return false;
    }

    @Override
    public boolean archive(Post post) {
        if (post.getState() != PostState.ARCHIVED) {
            post.setState(PostState.ARCHIVED);
            save(post);
            return true;
        }
        return false;
    }
}
