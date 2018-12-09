package app.http.middlewares;

import com.skillcorp.sejoframework.contracts.middlewares.IMiddleware;
import com.skillcorp.sejoframework.web.Request;
import com.skillcorp.sejoframework.web.RequestHandler;
import com.skillcorp.sejoframework.web.Response;

public class TokenAuthenticartion extends RequestHandler implements IMiddleware {


    @Override
    public void handle(Request request, Response response) {
        System.out.println("CHEQUEANDO URL " + request.url);
        if (request.url.equals("/api"))
        {
            response.send("Not enabled");
        }
    }
}
