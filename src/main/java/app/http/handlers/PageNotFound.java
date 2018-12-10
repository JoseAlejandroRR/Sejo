package app.http.handlers;

import com.skillcorp.sejoframework.contracts.http.IRequestHandler;
import com.skillcorp.sejoframework.web.Request;
import com.skillcorp.sejoframework.web.Response;

public class PageNotFound implements IRequestHandler  {

    public void index(Request request, Response response) {
        response.send("URL not exists", 404);
    }
}
