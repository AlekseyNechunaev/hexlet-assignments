package exercise.service;

import exercise.handle.ResourceNotFoundException;
import exercise.model.Post;
import exercise.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post not found"));
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public void create(Post post) {
        postRepository.save(post);
    }

    @Override
    public void update(Long id, Post updatedPost) {
        Post post = findById(id);
        post.setTitle(updatedPost.getTitle());
        post.setBody(updatedPost.getBody());
        postRepository.save(post);
    }

    @Override
    public void delete(Long id) {
        Post post = findById(id);
        postRepository.delete(post);
    }

    @Override
    public boolean existsById(Long id) {
        return postRepository.existsById(id);
    }
}
