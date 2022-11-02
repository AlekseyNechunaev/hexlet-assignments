package exercise;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;

import java.io.File;

import exercise.servlet.WelcomeServlet;
import exercise.servlet.UsersServlet;
import exercise.servlet.SessionServlet;

public class App {

    private static Users users;

    public static Users getUsers() {
        return users;
    }

    private static int getPort() {
        String port = System.getenv("PORT");
        if (port != null) {
            return Integer.parseInt(port);
        }
        return 5000;
    }

    public static Tomcat getApp(int port) {
        users = new Users();

        Tomcat tomcat = new Tomcat();

        tomcat.setBaseDir(System.getProperty("java.io.tmpdir"));
        tomcat.setPort(port);

        Context ctx = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());

        Tomcat.addServlet(ctx, WelcomeServlet.class.getSimpleName(), new WelcomeServlet());
        ctx.addServletMappingDecoded("", WelcomeServlet.class.getSimpleName());

        Tomcat.addServlet(ctx, UsersServlet.class.getSimpleName(), new UsersServlet());
        ctx.addServletMappingDecoded("/users/*", UsersServlet.class.getSimpleName());

        Tomcat.addServlet(ctx, SessionServlet.class.getSimpleName(), new SessionServlet());
        ctx.addServletMappingDecoded("/login", SessionServlet.class.getSimpleName());
        ctx.addServletMappingDecoded("/logout", SessionServlet.class.getSimpleName());

        return tomcat;
    }

    public static void main(String[] args) throws LifecycleException {
        Tomcat app = getApp(getPort());
        app.start();
        app.getServer().await();
    }

}
