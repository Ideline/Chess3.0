import java.util.ArrayList;

import static spark.Spark.*;

public class Main {



    public static void main(String[] args) {

        //Game g = new Game();
        //ex: https://github.com/mscharhag/blog-examples/tree/master/sparkdemo/src/main/java/com/mscharhag/sparkdemo

        // Skriv ut alla fel
        exception(Exception.class, (e, req, res) -> e.printStackTrace());

        //Ställ in vilken port servern ska köra på
        port(9999);

        //https://sparktutorials.github.io/2016/05/01/cors.html = CORS (Cross-Origin Resource Sharing)
        enableCORS("null", "GET,PUT,POST", "Access-Control-Allow-Origin, Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With" );

        //http://localhost:9999/hello
        get("/start", (req, res) -> Game.start(), JsonUtil.json());
        get("/move", (req, res) -> Game.getNextMove(), JsonUtil.json());

        //Skicka tillbaka datan som json datatypen
        after((req, res) -> {
            res.type("application/json");
        });
    }


    // Enables CORS on requests. This method is an initialization method and should be called once.
    private static void enableCORS(final String origin, final String methods, final String headers) {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }
}