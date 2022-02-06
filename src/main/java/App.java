import dao.Sql2oCleanerDao;
import dao.Sql2oClientDao;
import dao.Sql2oReviewDao;
import models.Cleaner;
import models.Client;
import models.Review;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.jknack.handlebars.helper.StringHelpers.center;
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
//        String connectionString = "jdbc:postgresql://:5432/digiclean";
//        Sql2o sql2o = new Sql2o(connectionString,"ftm","sparkpass");

        Sql2oCleanerDao cleanerDao = new Sql2oCleanerDao(sql2o);
        Sql2oClientDao clientDao = new Sql2oClientDao(sql2o);
        Sql2oReviewDao reviewDao = new Sql2oReviewDao(sql2o);

        //display the homepage
        get("/",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //doisplay the cleaner login page
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
            if (exist == null){
                //response.type("text/html");
                //response.body("User Does Not Exist");
                //response.redirect("/professional/login");
                String error = "User Does Not Exist";
                model.put("error",error);
            }else{
                if (exist.getCleanerIdNo() == cleanerIdNo && exist.getCleanerPassword() == cleanerPassword){
                    int id = exist.getId();
                    //cid = exist.getId();
                    response.redirect("/cleaner/dashboard/"+id+"");
                    halt();
                }else{
                    response.body("Wrong Id or Password");
                    String error = "Wrong Id or Password";
                    //response.redirect("/professional/login");
                    model.put("error",error);
                }
            }
            return new ModelAndView(model,"professional-login.hbs");
        }, new HandlebarsTemplateEngine());


        //diaplay the cleaner dashboard (profile update)
        get("/cleaner/dashboard/:id",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(request.params("id"));
            Cleaner thisCleaner = cleanerDao.findCleanerById(id);
            //System.out.println(id);
            model.put("cleaner",thisCleaner);
            return new ModelAndView(model, "cleaner-dashboard.hbs");
        }, new HandlebarsTemplateEngine());

        ///process cleaner profile update
        post("/cleaner/profile/update/:id",(request,response)->{
            Map<String,Object>model = new HashMap<>();
            int cleanerId = Integer.parseInt(request.params("id"));
            int id = Integer.parseInt(request.params("id"));
            String success = "Profile updated successfully";
            Cleaner thisCleaner = cleanerDao.findCleanerById(id);
            //System.out.println(id);
            model.put("cleaner",thisCleaner);
            //System.out.println(cleanerId);
            String cleanerName = request.queryParams("cleanerName");
            String cleanerBio = request.queryParams("cleanerBio");
            int cleanerPhone = Integer.parseInt(request.queryParams("cleanerPhone"));
            Cleaner cleanerToUpdate = cleanerDao.findCleanerById(cleanerId);

            if(!(cleanerName.equals(""))){
                cleanerToUpdate.setCleanerName(cleanerName);
            }
            if(!(cleanerBio.equals(""))){
                cleanerToUpdate.setCleanerBio(cleanerBio);
            }
            if(!(cleanerBio.equals(""))){
                cleanerToUpdate.setCleanerPhone(cleanerPhone);
            }

            cleanerDao.updateCleaner(cleanerToUpdate, cleanerId);
            //System.out.println(cleanerId);
            //response.redirect("/cleaner/dashboard/"+cleanerId+"");
            model.put("success",success);
            return new ModelAndView(model,"cleaner-dashboard.hbs");
        }, new HandlebarsTemplateEngine());


        //show cleaner registration form
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

                //System.out.println(newCleaner.getCleanerName()+newCleaner.getCleanerPassword()+newCleaner.getCleanerIdNo()+newCleaner.getCleanerPhone());
                response.redirect("/professional/login");
            }
            return null;
        }, new HandlebarsTemplateEngine());


        //show client login form
        get("/client/login",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "client-login.hbs");
        }, new HandlebarsTemplateEngine());

        //process client login form
        post("/client/login", (request,response)->{
            Map<String,Object>model = new HashMap<>();
            String error = "Incorrect Password or Id";

            int clientIdNo = Integer.parseInt(request.queryParams("clientIdNo"));
            int clientPassword = Integer.parseInt(request.queryParams("clientPassword"));
            Client exist = clientDao.findClientByIdAndPassword(clientIdNo,clientPassword);
            if (exist.getClientIdNo() == clientIdNo && exist.getClientPassword() == clientPassword){
                response.redirect("/client/dashboard");
            }else{
                model.put("error",error);
                response.redirect("/client/login");
            }
            return null;
        }, new HandlebarsTemplateEngine());

        //show client registration form
        get("/client/register",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "client-register.hbs");
        }, new HandlebarsTemplateEngine());

        //process new client
        post("/client/register",(request,response)->{
            Map<String,Object>model = new HashMap<>();
            String clientName = request.queryParams("clientName");
            int clientIdNo = Integer.parseInt(request.queryParams("clientIdNo"));
            int clientPhone = Integer.parseInt(request.queryParams("clientPhone"));
            String clientLocation = request.queryParams("clientLocation");
            int clientPassword = Integer.parseInt(request.queryParams("clientPassword"));
            int clientPasswordConf = Integer.parseInt(request.queryParams("password_confirmation"));
            if(clientPassword != clientPasswordConf){
                response.redirect("/cleaner/register");
            }else{
                Client newClient = new Client(clientName, clientPassword, clientIdNo, clientPhone, clientLocation);
                clientDao.addClient(newClient);

                //System.out.println(newClient.getClientName()+newClient.getClientPassword()+newClient.getClientIdNo()+newClient.getClientPhone()+newClient.getClientLocation());
                response.redirect("/client/login");
            }
            return null;
        }, new HandlebarsTemplateEngine());


        //client dashboard
        get("/client/dashboard",(request,response)->{
            Map<String,Object>model = new HashMap<>();
            List<Cleaner> allCleaners = cleanerDao.getAllCleaners();
            model.put("cleaners",allCleaners);
            System.out.println(allCleaners.get(1).getStatus());
            return new ModelAndView(model,"client-dashboard.hbs");
        }, new HandlebarsTemplateEngine());

        //process hire
        get("/cleaner/hire/:id",(request,response)->{
            Map<String, Object> model = new HashMap<>();
            boolean msg = true;
            List<Cleaner> allCleaners = cleanerDao.getAllCleaners();
            model.put("cleaners",allCleaners);
            model.put("message",msg);
            int cleanerId = Integer.parseInt(request.params("id"));
            Cleaner foundCleaner = cleanerDao.findCleanerById(cleanerId);
            foundCleaner.setStatus(true);
            cleanerDao.updateCleaner(foundCleaner, foundCleaner.getId());
            return new ModelAndView(model,"client-dashboard.hbs");
        }, new HandlebarsTemplateEngine());

        // Custom error page
        internalServerError((req, res) -> {
            res.type("text/html");
            return "<h1 style=\"text-align:center;\">Oopsie, something went wrong :(</h1>";
        });

        //show cleaner profile and their reviews
        get("/cleaner/profile/:id",(request,response)->{
            Map<String, Object> model = new HashMap<>();
            int cleanerId = Integer.parseInt(request.params("id"));
            Cleaner foundCleaner = cleanerDao.findCleanerById(cleanerId);
            model.put("cleaner",foundCleaner);
            model.put("reviews",reviewDao.getReviewsByCleanerId(cleanerId));
            return new ModelAndView(model,"cleaner-profile.hbs");
        }, new HandlebarsTemplateEngine());

        //show review form
        get("/review/:id/new",(request,response)->{
            Map<String, Object> model = new HashMap<>();
            int cleanerId = Integer.parseInt(request.params("id"));
            Cleaner foundCleaner = cleanerDao.findCleanerById(cleanerId);
            model.put("cleaner",foundCleaner);
            return new ModelAndView(model,"review-form.hbs");
        }, new HandlebarsTemplateEngine());

        //process review form
        post("/review/process/:cleanerId",(request, response)->{
            int rating = Integer.parseInt(request.queryParams("rating"));
            int cleanerId = Integer.parseInt(request.queryParams("cleanerId"));
            String reviewer = request.queryParams("reviewer");
            String reviewerLocation = request.queryParams("reviewerLocation");
            String reviewMessage = request.queryParams("reviewerMessage");
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String  timeAtm = formatter.format(new Timestamp(new Date().getTime()));
            Review newReview = new Review(rating,cleanerId,reviewer,reviewerLocation,reviewMessage,timeAtm);
            reviewDao.addReview(newReview);
            response.redirect("/success");
            return null;
        },new HandlebarsTemplateEngine());

        //get success page and redirect to home
        get("/success",(request, response) -> {
            Map<String,Object> model = new HashMap<>();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine() );

        //get cleaners and reviews
        get("/reviews",(request,response)->{
            Map<String,Object>model = new HashMap<>();
            List<Cleaner> allCleaners = cleanerDao.getAllCleaners();
            model.put("cleaners",allCleaners);
            return new ModelAndView(model,"client-dashboard.hbs");
        }, new HandlebarsTemplateEngine());

        //get services
        get("/services",(request,response)->{
            Map<String,Object>model = new HashMap<>();
//            List<Cleaner> allCleaners = cleanerDao.getAllCleaners();
//            model.put("cleaners",allCleaners);
            return new ModelAndView(model,"services.hbs");
        }, new HandlebarsTemplateEngine());

        //get customer reviews
        get("/customer/reviews",(request,response)->{
            Map<String,Object>model = new HashMap<>();
            return new ModelAndView(model,"customer-reviews.hbs");
        }, new HandlebarsTemplateEngine());

    }
}
