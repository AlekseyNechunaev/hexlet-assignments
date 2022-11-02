package exercise.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;

import static exercise.Data.getUsers;
import static exercise.Data.getNextId;

@WebServlet(name = "UsersServlet", urlPatterns = "/users/*")
public class UsersServlet extends HttpServlet {

    private List<Map<String, String>> users = getUsers();

    private String getId(HttpServletRequest request) {
        return request.getParameter("id");
    }

    private String getAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return "list";
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 1, "");
    }

    private Map<String, String> getUserById(String id) {
        Map<String, String> user = users
                .stream()
                .filter(u -> u.get("id").equals(id))
                .findAny()
                .orElse(null);

        return user;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        String action = getAction(request);

        switch (action) {
            case "list":
                showUsers(request, response);
                break;
            case "new":
                newUser(request, response);
                break;
            case "edit":
                editUser(request, response);
                break;
            case "show":
                showUser(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        String action = getAction(request);

        switch (action) {
            case "new":
                createUser(request, response);
                break;
            case "edit":
                updateUser(request, response);
                break;
            case "delete":
                destroyUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showUsers(HttpServletRequest request,
                           HttpServletResponse response)
            throws IOException, ServletException {

        request.setAttribute("users", users);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/users.jsp");
        requestDispatcher.forward(request, response);
    }


    private void showUser(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.setAttribute("user", user);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/show.jsp");
        requestDispatcher.forward(request, response);
    }

    private void newUser(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
        Map<String, String> emptyUser = new HashMap<>();
        request.setAttribute("user", emptyUser);
        request.setAttribute("error", "");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/new.jsp");
        requestDispatcher.forward(request, response);
    }

    private void createUser(HttpServletRequest request,
                            HttpServletResponse response)
            throws IOException, ServletException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        if (firstName.isEmpty() || lastName.isEmpty()) {
            Map<String, String> invalidUser = Map.of("firstName", firstName, "lastName", lastName, "email", email);
            response.setStatus(422);
            request.setAttribute("user", invalidUser);
            request.setAttribute("error", "the first and last name fields should not be empty");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/new.jsp");
            requestDispatcher.forward(request, response);
            return;
        }
        String id = getNextId();
        Map<String, String> validUser = Map.of("id", id, "firstName", firstName, "lastName", lastName, "email", email);
        users.add(validUser);
        response.sendRedirect("/users");
    }

    private void editUser(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        request.setAttribute("user", user);
        request.setAttribute("error", "");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/edit.jsp");
        requestDispatcher.forward(request, response);
    }

    private void updateUser(HttpServletRequest request,
                            HttpServletResponse response)
            throws IOException, ServletException {

        String id = getId(request);
        Map<String, String> user = getUserById(id);
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (firstName.isEmpty() || lastName.isEmpty()) {
            Map<String, String> invalidUser = Map.of("id", id, "firstName", firstName, "lastName", lastName, "email", email);
            response.setStatus(422);
            request.setAttribute("user", invalidUser);
            request.setAttribute("error", "the first and last name fields should not be empty");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/edit.jsp");
            requestDispatcher.forward(request, response);
            return;
        }
        users.remove(user);
        users.add(Map.of("id", id, "firstName", firstName, "lastName", lastName, "email", email));
        response.sendRedirect("/users/show?id=" + id);
    }

    private void deleteUser(HttpServletRequest request,
                            HttpServletResponse response)
            throws IOException, ServletException {

        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.setAttribute("user", user);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/delete.jsp");
        requestDispatcher.forward(request, response);

    }

    private void destroyUser(HttpServletRequest request,
                             HttpServletResponse response)
            throws IOException, ServletException {

        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        users.remove(user);
        response.sendRedirect("/users");
    }
}
