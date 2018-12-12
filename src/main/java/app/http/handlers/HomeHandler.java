package app.http.handlers;

import app.models.User;

import com.skillcorp.sejoframework.contracts.http.IRequestHandler;
import com.skillcorp.sejoframework.helpers.Logger;
import com.skillcorp.sejoframework.web.*;

import java.util.ArrayList;
import java.util.Iterator;

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
        /*for(int i = 1; i <= 100; i++) {
            User user = new User();
            user.setName("User "+i);
            user.lastname = "Demo "+i;
            user.document = "V-"+i;
            user.save();
        }*/

        User user = new User();
        ArrayList users = user.where("id",">","1").get();
        Iterator it = users.iterator();
        while(it.hasNext())
        {
            User u = (User) it.next();
            Logger.getLogger().debug("User "+ u.name);
        }

        //user.find(1);

        res.send("Complete: ");

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
        res.send("Se ha creado un archivo para " + req.body.get("email") );
    }

    public void exit(Request req, Response res)
    {
        Cookies.destroy(Server.SESSION_SERVER_NAME);
        res.send("Works");
    }
}
