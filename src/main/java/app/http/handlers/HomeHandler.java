package app.http.handlers;

import app.models.User;

import com.skillcorp.sejoframework.cache.Cache;
import com.skillcorp.sejoframework.contracts.http.IRequestHandler;
import com.skillcorp.sejoframework.helpers.Logger;
import com.skillcorp.sejoframework.web.*;

import java.util.ArrayList;
import java.util.HashMap;
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
           //String value = (String) Cache.getString("prueba");
           Sessions.destoy("id_usuario");
           //Cache.add("prueba","cualquier contenido que se me pueda ocurrir");
            res.send("Probando el cache: " + 1);
        } catch(Exception ex) {
           System.out.println("ERRROR "+ex.toString());
           res.send("INTERNAL SERVER", 500);
       }
    }

    public void demo(Request req, Response res)
    {
        //DB db = (DB) this.container.getProvider("DB");
        try {
            //  System.out.println(db.getMessage());
            //String value = (String) Cache.getString("prueba");
            //Cache.add("prueba","cualquier contenido que se me pueda ocurrir");
            Logger.getLogger().debug("RECIBO ",req.query.get("user") );
            Sessions.add("id_usuario",req.query.get("user"));
            res.send("Probando el cache: guardado" );
        } catch(Exception ex) {
            System.out.println("ERRROR "+ex.toString());
            res.send("INTERNAL SERVER", 500);
        }
    }

    public void getUser(Request req, Response res)
    {
        //DB db = (DB) this.getContainer().getProvider("DB2");

        //ArrayList<Map<String, String>> datos = Cache.get("info");
        /*for(int i = 1; i <= 10000; i++) {
            User user = new User();
            user.setName("User "+i);
            user.lastname = "Demo "+i;
            user.document = "V-"+i;
            user.bio = "Soy00 el registro de prueba #"+i;
            user.state = "Buenos Aires";
            user.country = "Argentina";
            user.save();
        }*/

        String content = "<table width=\"800px\">";
        User user = new User();
        ArrayList users = user.where("id",">","1").get();
        Iterator it = users.iterator();
        while(it.hasNext())
        {
            User u = (User) it.next();
           /*content += String.format("<tr>"+
                    "<tr>"+
                    "<td>%s</td>"+
                            "<td>%s</td>"+
                    "<td>%s</td>"+
                    "<td>%s</td>"+
                    "<td>%s</td>"+
                    "<td>%s</td>"+
                            "</tr>",
                    u.id, u.name, u.lastname, u.document, u.bio, u.state, u.state
            );*/
            content += "<tr>"+
                            "<tr>"+
                            "<td>"+u.id+"</td>"+
                            "<td>"+u.name+"</td>"+
                            "<td>"+u.lastname+"</td>"+
                            "<td>"+u.document+"</td>"+
                            "<td>"+u.bio+"</td>"+
                            "<td>"+u.state+"</td>"+
                            "</tr>";

           // Logger.getLogger().debug("User "+ u.id);
        }
        content += "</table>";

        //user.find(1);

        res.send(content);

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

    public void api(Request req, Response res)
    {
        HashMap<String, String> obj = new HashMap<String, String>();
        obj.put("id","1");
        obj.put("email","josealejandror28@gmail.com");

        res.json(obj);
    }
}
