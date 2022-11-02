package exercise.repository;

import exercise.domain.Article;
import exercise.domain.Category;
import exercise.domain.query.QArticle;
import io.ebean.PagedList;

import java.util.List;
import java.util.Optional;

public final class ArticleRepository {

    public List<Article> getAllArticles(int offset, int rowsPerPage) {
        PagedList<Article> pagedArticles = new QArticle()
                .setFirstRow(offset)
                .setMaxRows(rowsPerPage)
                .orderBy()
                .id.asc()
                .findPagedList();
        return pagedArticles.getList();
    }

    public Optional<Article> getArticleById(long id) {
        return new QArticle()
                .id.eq(id)
                .findOneOrEmpty();
    }

    public void createArticle(String title, String body, Category category) {
        Article article = new Article(title, body, category);
        article.save();
    }

    public void updateArticle(Long id, String title, String body, long categoryId) {
        new QArticle()
                .id.eq(id)
                .asUpdate()
                .set("title", title)
                .set("body", body)
                .set("category_id", categoryId)
                .update();
    }

    public void deleteArticleById(Long id) {
        new QArticle()
                .id.eq(id)
                .delete();
    }
}
