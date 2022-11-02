package exercise.servlet;

import exercise.TemplateEngineUtil;
import exercise.servlet.model.Article;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.sql.ResultSet.*;


public class ArticlesServlet extends HttpServlet {

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
            case "list":
                showArticles(request, response);
                break;
            default:
                showArticle(request, response);
                break;
        }
    }

    private void showArticles(HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException, ServletException {

        ServletContext context = request.getServletContext();
        Connection connection = (Connection) context.getAttribute("dbConnection");
        List<Article> articles = new ArrayList<>();
        int currentPage = this.getCurrentPage(request);
        int previousPage;
        int nextPage;
        if (currentPage < 1) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        previousPage = currentPage == 1 ? 1 : currentPage - 1;
        nextPage = currentPage + 1;
        int offset = currentPage == 1 ? 0 : previousPage * 10;
        String query = "select id, title from articles order by id asc limit 10 offset ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, offset);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String title = rs.getString("title");
                Article article = new Article();
                article.setId(id);
                article.setTitle(title);
                articles.add(article);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        request.setAttribute("previousPage", previousPage);
        request.setAttribute("nextPage", nextPage);
        request.setAttribute("articles", articles);
        TemplateEngineUtil.render("articles/index.html", request, response);
    }

    private int getCurrentPage(HttpServletRequest request) {
        String pageNumber = request.getParameter("page");
        if (pageNumber == null) {
            return 1;
        }
        return Integer.parseInt(pageNumber);
    }

    private void showArticle(HttpServletRequest request,
                             HttpServletResponse response)
            throws IOException, ServletException {

        ServletContext context = request.getServletContext();
        Connection connection = (Connection) context.getAttribute("dbConnection");
        Article article = null;
        long id = Long.parseLong(Objects.requireNonNull(getId(request)));
        String query = "select title, body from articles where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query, TYPE_SCROLL_INSENSITIVE, CONCUR_UPDATABLE);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                rs.close();
                statement.close();
                return;
            }
            rs.previous();
            while (rs.next()) {
                String title = rs.getString("title");
                String body = rs.getString("body");
                article = new Article();
                article.setId(id);
                article.setTitle(title);
                article.setBody(body);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        request.setAttribute("article", article);
        TemplateEngineUtil.render("articles/show.html", request, response);
    }
}
