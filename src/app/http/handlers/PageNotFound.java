package app.http.handlers;

import com.skillcorp.sejoframework.contracts.http.IRequestHandler;
import com.skillcorp.sejoframework.web.Request;
import com.skillcorp.sejoframework.web.Response;

public class PageNotFound implements IRequestHandler {

    @Override
    public Response execute(Request request, Response response) {
        return response.send("URL not exists", 404);
    }
}
