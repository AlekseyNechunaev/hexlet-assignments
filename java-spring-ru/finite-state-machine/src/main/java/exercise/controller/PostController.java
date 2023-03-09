package exercise.controller;

import exercise.UnprocessableEntityException;
import exercise.model.Post;
import exercise.model.PostState;
import exercise.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(path = "")
    public Iterable<Post> getPosts() {
        return postService.findAllPublished(PostState.PUBLISHED);
    }

    @PostMapping(path = "")
    public Post createPost(@RequestBody Post post) {
        return postService.save(post);
    }

    @GetMapping(path = "/{id}")
    public Post getPost(@PathVariable long id) {
        return postService.findById(id);
    }

    @PatchMapping(path = "/{id}/publish")
    public Post publish(@PathVariable long id) {
        Post post = postService.findById(id);
        if (postService.publish(post)) {
            return post;
        }
        throw new UnprocessableEntityException("Publishing is not possible");
    }

    @PatchMapping(path = "/{id}/archive")
    public Post archive(@PathVariable long id) {
        Post post = postService.findById(id);
        if (postService.archive(post)) {
            return post;
        }
        throw new UnprocessableEntityException("Archived is not possible");
    }
}
