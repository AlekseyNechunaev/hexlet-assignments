package exercise.controller;

import exercise.dto.GetPostDto;
import exercise.dto.PostDto;
import exercise.service.PostService;
import exercise.utils.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @Autowired
    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping(path = "")
    public List<GetPostDto> getPosts() {
        return postService.findAll().stream()
                .map(postMapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public GetPostDto getPost(@PathVariable long id) {
        return postMapper.map(postService.findById(id));
    }

    @PostMapping(path = "")
    public void createPost(@RequestBody PostDto dto) {
        postService.create(postMapper.map(dto));
    }

    @PatchMapping(path = "/{id}")
    public void updatePost(@PathVariable long id, @RequestBody PostDto dto) {
        postService.update(id, postMapper.map(dto));
    }

    @DeleteMapping(path = "/{id}")
    public void deletePost(@PathVariable long id) {
        postService.delete(id);
    }
}
