package app.http;

import app.http.handlers.HomeHandler;
import com.skillcorp.sejoframework.contracts.providers.Provider;
import com.skillcorp.sejoframework.web.RouterHandler;

import java.util.HashMap;

public class Routes {

    private RouterHandler router;

    public Routes(RouterHandler router)
    {
        this.router = router;

        registerRoutes();
    }

    private void registerRoutes()
    {
        router.get("/api", new String[]{"auth"}, new HomeHandler("API endpoint"));

        router.get("/web","auth" ,  new HomeHandler("ROOT URL"));

        router.get("/client","guest" ,  new HomeHandler("ROOT URL"));

        router.get("/test", new HomeHandler("Page for Testing"));
    }

    public RouterHandler getRouter()
    {
        return router;
    }

}
