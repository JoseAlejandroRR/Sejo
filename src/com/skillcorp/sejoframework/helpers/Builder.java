package com.skillcorp.sejoframework.helpers;


import java.net.URI;
import java.util.HashMap;

public class Builder {


    public static Object createInstance(String className) {
        Object object = null;
        try {
            Class classDefinition = Class.forName(className);
            object = classDefinition.newInstance();
        } catch (InstantiationException e) {
            System.out.println("Error 1: "+e);
        } catch (IllegalAccessException e) {
            System.out.println("Error 2: "+e);
        } catch (ClassNotFoundException e) {
            System.out.println("Error 3: "+e);
        }
        return object;
    }

    public static HashMap getParamsFromUrl(String query)
    {
        String[] params = query.split("&");
        HashMap<String, String> map = new HashMap<String, String>();
        for (String param : params)
        {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }

}
