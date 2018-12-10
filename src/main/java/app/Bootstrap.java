package app;

import app.bootstrap.Middlewares;
import app.bootstrap.Providers;
import app.http.Routes;
import app.http.handlers.HomeHandler;
import app.http.handlers.PageNotFound;

import com.skillcorp.sejoframework.Application;
import com.skillcorp.sejoframework.database.Conexion;
import com.skillcorp.sejoframework.helpers.Logger;
import com.skillcorp.sejoframework.providers.ServiceProvider;
import com.skillcorp.sejoframework.web.RouterHandler;
import com.skillcorp.sejoframework.web.Server;

public class Bootstrap {


    public static void run()
    {
        Conexion conex = new Conexion();
        conex.modeDebug = true;

        Logger logger = new Logger(Logger.DEBUG, "./application.log");

        Server server = new Server(logger, new PageNotFound());

        ServiceProvider container = new ServiceProvider();

        server.routerHandler.setContainer(container);

        container.setLogger(logger);

        Application app = new Application(server, container, logger);

        Routes routes = new Routes(server.getRouterHandler());

        //routes.getRouter().setHandler404(new PageNotFound());

        //server.setRouterHandler(routes.getRouter());

        server.registerMiddlewares(Middlewares.getList());

        container.registerProviders(Providers.getList());

        server.start(9000);
    }
}
