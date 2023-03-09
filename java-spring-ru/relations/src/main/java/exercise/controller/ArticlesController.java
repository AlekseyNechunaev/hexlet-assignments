package exercise.controller;

import exercise.model.Article;
import exercise.repository.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/articles")
public class ArticlesController {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticlesController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    @GetMapping(path = "")
    public Iterable<Article> getArticles() {
        return this.articleRepository.findAll();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteArticle(@PathVariable long id) {
        this.articleRepository.deleteById(id);
    }

    @PostMapping
    public void save(@RequestBody Article article) {
        articleRepository.save(article);
    }

    @PatchMapping(path = "/{id}")
    public void update(@PathVariable Long id, @RequestBody Article other) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article != null) {
            article.setBody(other.getBody());
            article.setCategory(other.getCategory());
            article.setName(other.getName());
        }
    }

    @GetMapping(path = "/{id}")
    public Article findById(@PathVariable Long id) {
        return articleRepository.findById(id).orElse(null);
    }
}
