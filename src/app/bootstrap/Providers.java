package app.bootstrap;

import com.skillcorp.sejoframework.contracts.providers.IProvider;
import com.skillcorp.sejoframework.database.DB;

import java.util.HashMap;

public class Providers {

    private static HashMap<String, String> providers;

    private Providers()
    {

    }

    private static void register()
    {
        providers = new HashMap<String, String>();

        providers.put("DB", DB.class.getName());
    }

    public static HashMap getList()
    {
        register();
        return providers;
    }

}
