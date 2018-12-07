package app;

import app.bootstrap.Middlewares;
import app.bootstrap.Providers;
import app.http.Routes;
import app.http.handlers.HomeHandler;
import app.http.handlers.PageNotFound;
import com.skillcorp.sejoframework.Application;
import com.skillcorp.sejoframework.web.RouterHandler;
import com.skillcorp.sejoframework.web.Server;

public class Bootstrap {


    public static void run()
    {
        Application app = Application.getInstance();

        Routes routes = new Routes(app.server.getRouterHandler());

        routes.getRouter().setHandler404(new PageNotFound());

        app.server.setRouterHandler(routes.getRouter());

        app.registerProviders(Providers.getList());

        app.registerMiddlewares(Middlewares.getList());

        app.run();
    }
}
