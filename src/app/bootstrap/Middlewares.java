package app.bootstrap;


import app.http.middlewares.TokenAuthenticartion;

import java.util.HashMap;

public class Middlewares {

    private static HashMap<String, String> middlewares;

    private Middlewares()
    {

    }

    private static void register()
    {
        middlewares = new HashMap<String, String>();

        middlewares.put("auth", TokenAuthenticartion.class.getName());
    }

    public static HashMap getList()
    {
        register();
        return middlewares;
    }
}
