package app;

import app.handlers.HomeHandler;
import com.skillcorp.sejoframework.web.Server;

public class Bootstrap {

    public static int PORT = 9000;

    public static void run()
    {
        Server server = Server.getInstance(PORT);

        System.out.println("Server start at");

        Server.get("/API", new HomeHandler("API"));

        Server.get("/web", new HomeHandler("ROOT"));

        Server.get("/404", new HomeHandler("ERROR 404"));

        Server.start();
    }
}
