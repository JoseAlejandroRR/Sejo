package app.http.handlers;

import app.models.User;
import com.skillcorp.sejoframework.Application;
import com.skillcorp.sejoframework.cache.Cache;
import com.skillcorp.sejoframework.contracts.http.IRequestHandler;
import com.skillcorp.sejoframework.database.DB;
import com.skillcorp.sejoframework.files.File;
import com.skillcorp.sejoframework.helpers.Logger;
import com.skillcorp.sejoframework.web.Request;
import com.skillcorp.sejoframework.web.RequestHandler;
import com.skillcorp.sejoframework.web.Response;
import com.sun.net.httpserver.HttpExchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        //DB db = (DB) this.getContainer().getProvider("DB2");

        //ArrayList<Map<String, String>> datos = Cache.get("info");
        User user = new User();
        //user.find(Integer.parseInt(req.query.get("user_id")));

        res.send("User: "+user.getName());

    }

    public void upload(Request req, Response res)
    {
        /*Logger.getLogger().debug("TIENE "+req.body.size());
        for (Map.Entry<String, String> in : req.body.entrySet())
        {
            Logger.getLogger().debug("FIELD ",in.getKey(), in.getValue());
        }*/
        //String filePath = File.get("foto",null);

        /*ArrayList<Map<String, String>> datos = new ArrayList<Map<String, String>>();
        HashMap<String, String> obj = new HashMap<String, String>();
        obj.put("email",String.valueOf(req.body.get("email")));
        datos.add(obj);
        Cache.add("info", datos);*/
        res.send("Se ha creado un archivo en " + 1 );
    }
}
