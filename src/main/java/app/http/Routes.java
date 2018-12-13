package app.http;

import app.http.handlers.HomeHandler;
import app.http.handlers.validators.HomeValidator;
import com.skillcorp.sejoframework.web.RouterHandler;

public class Routes {

    private RouterHandler router;

    public Routes(RouterHandler router)
    {
        this.router = router;

        registerRoutes();
    }

    private void registerRoutes()
    {
        router.get("/api", HomeHandler.class).middleware("auth");
        router.get("/api/user/[user_id]/adress/[direction]", HomeHandler.class, "getUser").middleware("auth").validator(HomeValidator.class);

       // router.get("/web","auth" ,  new HomeHandler("ROOT URL"));

       // router.get("/client","guest" ,  new HomeHandler("ROOT URL"));
        router.get("/uploads", HomeHandler.class, "upload").validator(HomeValidator.class);

        router.get("/test", HomeHandler.class, "action")
                .middleware("auth");

        router.get("/cache", HomeHandler.class, "demo");

        router.get("/exit", HomeHandler.class, "exit");

        router.get("/json", HomeHandler.class, "api");
    }

    public RouterHandler getRouter()
    {
        return router;
    }

}
