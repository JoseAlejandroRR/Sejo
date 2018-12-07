package app.http.middlewares;

import com.skillcorp.sejoframework.contracts.middlewares.IMiddleware;
import com.skillcorp.sejoframework.web.Request;
import com.skillcorp.sejoframework.web.Response;

public class TokenAuthenticartion implements IMiddleware {


    @Override
    public Response handle(Request request, Response response) {
        System.out.println("CHECHEANDO URL " + request.url);
        if (request.url.equals("/api"))
        {
            return response.send(404);
        }
        return null;
    }
}
