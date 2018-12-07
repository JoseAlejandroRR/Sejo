package app.http.handlers;

import com.skillcorp.sejoframework.Application;
import com.skillcorp.sejoframework.contracts.http.IRequestHandler;
import com.skillcorp.sejoframework.database.DB;
import com.skillcorp.sejoframework.web.Request;
import com.skillcorp.sejoframework.web.Response;
import com.sun.net.httpserver.HttpExchange;

public class HomeHandler implements IRequestHandler {

    private String message;

    public HomeHandler(String message)
    {
        this.message = message;
    }


    @Override
    public Response execute(Request req, Response res)
    {
        DB db = (DB) Application.getProvider("DB");
       try {
            System.out.println(db.getMessage());
            return res.send("Hello, Sejo say: "+message + " with DI: " + db.getMessage());
        } catch(Exception ex) {
           System.out.println("ERRROR "+ex.toString());
           return res.send("INTERNAL SERVER", 500);
       }
    }
}
