import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;


public class App {

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        get("/",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.hbs");

        }, new HandlebarsTemplateEngine());

        get("/professional/login",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "professional-login.hbs");

        }, new HandlebarsTemplateEngine());

        get("/professional/register",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "professional-register.hbs");

        }, new HandlebarsTemplateEngine());

        get("/client/login",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "client-login.hbs");

        }, new HandlebarsTemplateEngine());

        get("/client/register",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "client-register.hbs");

        }, new HandlebarsTemplateEngine());


    }
}
