package exercise.controllers;

import exercise.repository.ArticleRepository;
import exercise.repository.CategoryRepository;
import io.javalin.http.Handler;
import io.ebean.PagedList;

import java.util.List;
import java.util.Optional;

import exercise.domain.query.QArticle;
import exercise.domain.Article;
import exercise.domain.Category;
import exercise.domain.query.QCategory;
import io.javalin.http.HttpCode;
import io.javalin.http.NotFoundResponse;

public final class ArticleController {

    private static ArticleRepository articleRepository = new ArticleRepository();
    private static CategoryRepository categoryRepository = new CategoryRepository();

    public static Handler listArticles = ctx -> {
        int page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
        int rowsPerPage = 10;
        int offset = (page - 1) * rowsPerPage;
        List<Article> articles = articleRepository.getAllArticles(offset, rowsPerPage);
        ctx.attribute("articles", articles);
        ctx.attribute("page", page);
        ctx.render("articles/index.html");
    };

    public static Handler newArticle = ctx -> {
        List<Category> categories = categoryRepository.getAllCategories();
        ctx.attribute("categories", categories);
        ctx.render("articles/new.html");
    };

    public static Handler createArticle = ctx -> {
        String title = ctx.formParam("title");
        String body = ctx.formParam("body");
        Long categoryId = ctx.formParamAsClass("categoryId", Long.class).getOrDefault(null);
        Category category = categoryRepository.getCategoryByID(categoryId).orElseThrow(NotFoundResponse::new);
        articleRepository.createArticle(title, body, category);
        ctx.sessionAttribute("flash", "Статья успешно создана");
        ctx.redirect("/articles");
    };

    public static Handler showArticle = ctx -> {
        Long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);
        Article article = articleRepository.getArticleById(id).orElseThrow(NotFoundResponse::new);
        ctx.attribute("article", article);
        ctx.render("articles/show.html");
    };

    public static Handler editArticle = ctx -> {
        Long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);
        Article article = articleRepository.getArticleById(id).orElseThrow(NotFoundResponse::new);
        List<Category> categories = categoryRepository.getAllCategories();
        ctx.attribute("article", article);
        ctx.attribute("categories", categories);
        ctx.render("articles/edit.html");
    };

    public static Handler updateArticle = ctx -> {
        Long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);
        String title = ctx.formParamAsClass("title", String.class).getOrDefault(null);
        String body = ctx.formParamAsClass("body", String.class).getOrDefault(null);
        Long categoryId = ctx.formParamAsClass("categoryId", Long.class).getOrDefault(null);
        if (id == null || title.isEmpty() || body.isEmpty() || categoryId == null) {
            ctx.status(HttpCode.BAD_REQUEST);
            return;
        }
        articleRepository.updateArticle(id, title, body, categoryId);
        ctx.sessionAttribute("flash", "Статья успешно отредактирована");
        ctx.redirect("/articles");
    };

    public static Handler deleteArticle = ctx -> {
        Long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);
        Article article = articleRepository.getArticleById(id).orElseThrow(NotFoundResponse::new);
        ctx.attribute("article", article);
        ctx.render("articles/delete.html");
    };

    public static Handler destroyArticle = ctx -> {
        Long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);
        if (id == null) {
            ctx.status(HttpCode.BAD_REQUEST);
            return;
        }
        articleRepository.deleteArticleById(id);
        ctx.sessionAttribute("flash", "Статья успешно удалена");
        ctx.redirect("/articles");
    };
}
