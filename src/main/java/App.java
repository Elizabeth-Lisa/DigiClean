import dao.Sql2oCleanerDao;
import models.Cleaner;
import org.sql2o.Sql2o;
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

        String connectionString = "jdbc:postgresql://ec2-54-208-139-247.compute-1.amazonaws.com:5432/d30bk4tmsim0ug?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
        Sql2o sql2o = new Sql2o(connectionString,"hthenwgyqmbuvo","d136db0bd4b9991a3e18bcdeb1177aab7f710813542fa9e763493b230ddd1831");
        Sql2oCleanerDao cleanerDao = new Sql2oCleanerDao(sql2o);

        get("/",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.hbs");

        }, new HandlebarsTemplateEngine());

        get("/professional/login",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "professional-login.hbs");

        }, new HandlebarsTemplateEngine());

        //process cleaner/professional login form
        post("/professional/login", (request,response)->{
            Map<String,Object>model = new HashMap<>();
            int cleanerIdNo = Integer.parseInt(request.queryParams("cleanerIdNo"));
            int cleanerPassword = Integer.parseInt(request.queryParams("cleanerPassword"));
            Cleaner exist = cleanerDao.findCleanerByIdAndPassword(cleanerIdNo,cleanerPassword);
            if (exist.getCleanerIdNo() == cleanerIdNo && exist.getCleanerPassword() == cleanerPassword){
                response.redirect("/cleaner/dashboard");
            }else{
                response.redirect("/professional/login");
            }
            return null;
        }, new HandlebarsTemplateEngine());


        get("/cleaner/dashboard",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "cleaner-dashboard.hbs");

        }, new HandlebarsTemplateEngine());

        ///cleaner/profile/update
        //to do, add profile update logic


        get("/professional/register",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "professional-register.hbs");

        }, new HandlebarsTemplateEngine());

        //process new professional
        post("/professional/register",(request,response)->{
            Map<String,Object>model = new HashMap<>();
            String cleanerName = request.queryParams("cleanerName");
            int cleanerIdNo = Integer.parseInt(request.queryParams("cleanerIdNo"));
            int cleanerPhone = Integer.parseInt(request.queryParams("cleanerPhone"));
            int cleanerPassword = Integer.parseInt(request.queryParams("cleanerPassword"));
            int cleanerPasswordConf = Integer.parseInt(request.queryParams("password_confirmation"));
            if(cleanerPassword != cleanerPasswordConf){
                response.redirect("/professional/register");
            }else{
                Cleaner newCleaner = new Cleaner(cleanerName,cleanerPassword,cleanerIdNo,cleanerPhone);
                cleanerDao.addCleaner(newCleaner);

                System.out.println(newCleaner.getCleanerName()+newCleaner.getCleanerPassword()+newCleaner.getCleanerIdNo()+newCleaner.getCleanerPhone());
                response.redirect("/professional/login");
            }
            return null;
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
