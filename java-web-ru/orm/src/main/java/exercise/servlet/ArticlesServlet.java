package exercise.servlet;

import exercise.TemplateEngineUtil;
import exercise.domain.Article;
import exercise.domain.Category;
import exercise.repository.ArticleRepository;
import exercise.repository.CategoryRepository;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ArticlesServlet extends HttpServlet {

    private ArticleRepository articleRepository;
    private CategoryRepository categoryRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        articleRepository = new ArticleRepository();
        categoryRepository = new CategoryRepository();
    }

    private String getId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return null;
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 1, null);
    }

    private String getAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return "list";
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 2, getId(request));
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        String action = getAction(request);

        switch (action) {
            case "list" -> showArticles(request, response);
            case "new" -> newArticle(request, response);
            default -> showArticle(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        String action = getAction(request);

        switch (action) {
            case "list" -> createArticle(request, response);
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showArticles(HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException, ServletException {

        int articlesPerPage = 10;
        String page = request.getParameter("page");
        int normalizedPage = page == null ? 1 : Integer.parseInt(page);
        int offset = (normalizedPage - 1) * articlesPerPage;
        List<Article> articles = articleRepository.getArticlesListWithOffset(offset, articlesPerPage);
        request.setAttribute("articles", articles);
        request.setAttribute("page", normalizedPage);
        TemplateEngineUtil.render("articles/index.html", request, response);
    }

    private void showArticle(HttpServletRequest request,
                             HttpServletResponse response)
            throws IOException, ServletException {

        String textID = getId(request);
        long id = 0;
        if (textID != null) {
            id = Long.parseLong(textID);
        }
        Article article = articleRepository.getArticleByID(id);
        if (article == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        request.setAttribute("article", article);
        TemplateEngineUtil.render("articles/show.html", request, response);
    }

    private void newArticle(HttpServletRequest request,
                            HttpServletResponse response)
            throws IOException, ServletException {
        List<Category> allCategories = categoryRepository.getAllCategories();
        request.setAttribute("categories", allCategories);
        TemplateEngineUtil.render("articles/new.html", request, response);
    }

    private void createArticle(HttpServletRequest request,
                               HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        String title = request.getParameter("title");
        String body = request.getParameter("body");
        String categoryId = request.getParameter("categoryId");
        Category category = categoryRepository.getCategoryById(Long.parseLong(categoryId));
        if (category == null || title == null || body == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Article article = new Article(title, body, category);
        articleRepository.createArticle(article);
        session.setAttribute("flash", "Статья успешно создана");
        response.sendRedirect("/articles");
    }
}
