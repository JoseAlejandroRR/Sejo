package app;

import app.http.handlers.HomeHandler;
import com.skillcorp.sejoframework.web.RouterHandler;
import com.skillcorp.sejoframework.web.Server;

public class Bootstrap {

    public static int PORT = 9000;

    public static void run()
    {
        Server server = Server.getInstance(PORT);
        RouterHandler router = server.routerHandler;

        System.out.println("Server start at");

        router.get("/API", new HomeHandler("API"));

        router.get("/web", new HomeHandler("ROOT"));

        router.get("/404", new HomeHandler("ERROR 404"));

        Server.start();
    }
}
