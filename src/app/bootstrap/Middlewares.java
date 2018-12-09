package app.bootstrap;


import app.http.middlewares.TokenAuthenticartion;

import java.util.HashMap;

public class Middlewares {

    private static HashMap<String, Object> middlewares;

    private Middlewares()
    {

    }

    private static void register()
    {
        middlewares = new HashMap<String, Object>();

        middlewares.put("auth", TokenAuthenticartion.class);
    }

    public static HashMap getList()
    {
        register();
        return middlewares;
    }
}
