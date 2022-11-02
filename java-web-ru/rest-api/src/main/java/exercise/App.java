package exercise;

import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.crud;

import io.javalin.core.JavalinConfig;
import io.javalin.core.validation.ValidationException;

import exercise.controllers.UserController;
import io.javalin.http.NotFoundResponse;

public final class App {

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "5000");
        return Integer.parseInt(port);
    }

    private static void addRoutes(Javalin app) {

        app.get("/", ctx -> ctx.result("REST API"));
        app.routes(() -> {
            crud("/api/v1/users/{id}", new UserController());
        });
    }

    public static Javalin getApp() {

        Javalin app = Javalin.create(JavalinConfig::enableDevLogging);
        app.exception(ValidationException.class, (e, ctx) -> {
            ctx.json(e.getErrors()).status(422);
        });
        app.exception(NotFoundResponse.class, (e, ctx) -> {
            ctx.json(e.getMessage()).status(404);
        });

        addRoutes(app);

        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(getPort());
    }
}
