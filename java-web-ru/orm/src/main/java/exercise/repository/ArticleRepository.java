package exercise.repository;

import exercise.domain.Article;
import exercise.domain.query.QArticle;
import io.ebean.PagedList;

import java.util.List;
import java.util.Optional;

public final class ArticleRepository {

    public List<Article> getArticlesListWithOffset(int offset, int articlesPerPage) {
        PagedList<Article> pagedArticles = new QArticle()
                .setFirstRow(offset)
                .setMaxRows(articlesPerPage)
                .orderBy().id.asc()
                .findPagedList();
        return pagedArticles.getList();
    }

    public Article getArticleByID(long id) {
        return new QArticle()
                .id.eq(id)
                .findOne();
    }

    public void createArticle(Article article) {
        article.save();
    }
}
