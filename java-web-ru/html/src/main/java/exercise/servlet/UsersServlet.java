package exercise.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.model.User;
import exercise.serivce.Utils;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "UsersServlet", urlPatterns = "/users/*")
public class UsersServlet extends HttpServlet {

    public static final String BEGIN_HTML = """
            <!DOCTYPE HTML PUBLIC>
            <html>
            <head>
                <meta charset=\\"UTF-8\\">
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
                <title>
                    Show users
                </title>
            </title>
            </head>
            <body>
                <table>""";

    public static final String END_HTML = """
                </table>
            </body>
            </html>
            """;


    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        String pathInfo = request.getPathInfo();

        response.setContentType("text/html;charset=UTF-8");

        if (pathInfo == null) {
            showUsers(request, response);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String id = ArrayUtils.get(pathParts, 1, "");

        showUser(request, response, id);
    }

    private List<User> getUsers() throws IOException {
        Path filePath = Paths.get("src/main/resources/users.json");
        String fileContent = Utils.readFile(filePath);
        return new ObjectMapper().readValue(fileContent, new TypeReference<>() {
        });
    }

    private void showUsers(HttpServletRequest request,
                           HttpServletResponse response)
            throws IOException {
        PrintWriter pw = response.getWriter();
        StringBuilder html = new StringBuilder(BEGIN_HTML);
        List<User> users = getUsers();
        users.forEach(user -> {
            html.append("<tr><td>").append(user.getId()).append("</td>")
                    .append("<td>").append("<a href=\"/users/").append(user.getId()).append("\">")
                    .append(user.getFirstName()).append(" ").append(user.getLastName()).append("</a>")
                    .append("</td></tr>");
        });
        html.append(END_HTML);
        pw.write(html.toString());
        pw.close();

    }

    private void showUser(HttpServletRequest request,
                          HttpServletResponse response,
                          String id)
            throws IOException {
        StringBuilder html = new StringBuilder(BEGIN_HTML);
        PrintWriter pw = response.getWriter();
        List<User> users = getUsers();
        Optional<User> user = getUserByID(users, id);
        if (user.isPresent()) {
            User searchUser = user.get();
            html.append("<tr><th>firstName</th>")
                    .append("<th>lastName</th>")
                    .append("<th>id</th")
                    .append("<th>email</th>")
                    .append("</tr>")
                    .append("<tr>")
                    .append("<td>").append(searchUser.getFirstName()).append("</td>")
                    .append("<td>").append(searchUser.getLastName()).append("</td>")
                    .append("<td>").append(searchUser.getId()).append("</td>")
                    .append("<td>").append(searchUser.getEmail()).append("</td>")
                    .append(END_HTML);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        pw.write(html.toString());
        pw.close();
    }

    private Optional<User> getUserByID(List<User> users, String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }
}
