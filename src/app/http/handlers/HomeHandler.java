package app.http.handlers;

import com.skillcorp.sejoframework.Application;
import com.skillcorp.sejoframework.contracts.http.IRequestHandler;
import com.skillcorp.sejoframework.database.DB;
import com.skillcorp.sejoframework.web.Request;
import com.skillcorp.sejoframework.web.RequestHandler;
import com.skillcorp.sejoframework.web.Response;
import com.sun.net.httpserver.HttpExchange;

public class HomeHandler extends RequestHandler implements IRequestHandler {

    private String message;

    public HomeHandler()
    {
        //this.message = message;
    }


    public void action(Request req, Response res)
    {
        //DB db = (DB) this.container.getProvider("DB");
       try {
          //  System.out.println(db.getMessage());
            res.send("Hello, Sejo say: "+message + " with DI: " + "db.getMessage()" + " > ");
        } catch(Exception ex) {
           System.out.println("ERRROR "+ex.toString());
           res.send("INTERNAL SERVER", 500);
       }
    }

    public void getUser(Request req, Response res)
    {
        DB db = (DB) this.getContainer().getProvider("DB2");
        res.send("User: "+req.query.get("user_id") + " con "+req.query.get("a"));
    }
}
