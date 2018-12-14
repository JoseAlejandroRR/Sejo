package com.skillcorp.sejoframework.views;

import com.skillcorp.sejoframework.Application;
import com.skillcorp.sejoframework.files.File;
import com.skillcorp.sejoframework.helpers.Builder;
import com.skillcorp.sejoframework.helpers.Logger;
import sun.rmi.runtime.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class View {

    public static final String PATH_TEMPLATES = "/views/";

    public static String render(String file, HashMap<String, Object> data)
    {
        //String content = File.get(file);

        String content = View.loadFile(file);

        content = View.checkIncludes(content);

        content = View.parseData(content, data);

        return content;
    }

    private static String checkIncludes(String content)
    {
        Pattern pInclude = Pattern.compile("\\@include(\\(.*?)\\)");
        Matcher matcher = pInclude.matcher(content);
        String file = "";
        while (matcher.find())
        {
            String in = matcher.group();
            file = in.substring(9, in.length() - 1);
            content = content.replace(in, View.loadFile(file));
            Logger.getLogger().debug("include ",file, View.loadFile(file));
        }
        return content;
    }

    private static String parseData(String content, HashMap<String, Object> data)
    {
        Pattern pForeach = Pattern.compile("\\@foreach(.*?)@endforeach");
        Pattern pForeachCondition = Pattern.compile("\\@foreach(\\(.*?)\\)");
        Matcher mForeach = null;
        Matcher mForeachCondition = null;

        for (Map.Entry<String, Object> var : data.entrySet())
        {
            //String key = "$[" + var.getKey() + "]";
            content = View.parseVars(content, var.getKey(), var.getValue());

            mForeachCondition = pForeachCondition.matcher(content);

            String condition = "";

            String[] conditionVars = new String[3];

            while(mForeachCondition.find())
            {
                condition = mForeachCondition.group().replace("  ", " ").replace("  ", " ");
                condition = condition.substring(9, condition.length() - 1).replace("  "," ");
                Logger.getLogger().debug("confition ", condition);
                conditionVars = condition.split(" as ");
            }

            if (conditionVars[0] != null && conditionVars[1] != null) {
                if (!conditionVars[0].toLowerCase().equals(var.getKey().toLowerCase())) {

                    //Logger.getLogger().debug("Var undefined: " + conditionVars[0], var.getKey().toLowerCase());
                } else {
                    mForeach = pForeach.matcher(content);
                    String node = "";
                    while(mForeach.find())
                    {
                        node = mForeach.group();
                        //Logger.getLogger().debug("FOREACH ", node);
                        String htmlSearch = node.substring(node.indexOf(")") + 1, node.length() - 11);
                        Logger.getLogger().debug("KD",htmlSearch);

                        Iterator it = ((ArrayList) var.getValue()).iterator();
                        String[] pointer = conditionVars[1].split(" => ");
                        String kk = null;
                        String row = pointer[0];

                        int index = 0;
                        if (pointer.length == 2 && pointer[0] != null && pointer[1] != null) {
                            kk = pointer[0];
                            row = pointer[1];
                        }


                        String htmlReplacer = "";

                        String kch = "";
                        while (it.hasNext()) {
                            HashMap<String, String> obj = (HashMap<String, String>) it.next();
                            Logger.getLogger().debug("CHECK ",kk, row, obj.get("name"));
                            if (kk != null) {
                                kch = View.parseVars(htmlSearch, "k", index);
                                if (!kch.equals(htmlSearch)) {

                                    Logger.getLogger().debug("KK", kch);
                                    //htmlReplacer += kch;
                                }
                            }

                            String pp = htmlSearch;

                            for (Map.Entry<String, String> item : obj.entrySet())
                            {
                                pp = View.parseVars(pp, row + "." + item.getKey(), item.getValue());
                                Logger.getLogger().debug("VL", pp);
                            }
                            htmlReplacer += pp;



                            //Logger.getLogger().debug("POX ",htmlReplacer);
                            index++;

                        }
                        content = content.replace(node, htmlReplacer);
                        //Logger.getLogger().debug("HTML ",content);
                        //Logger.getLogger().debug("htmlReplacer ",htmlReplacer);

                    }
                }
            }





        }

        return content;
    }

    private  static String parseVars(String content, String key, Object obj)
    {
        String keyReplace = "$[" + key + "]";

        //Logger.getLogger().debug("CHANGE ",keyReplace, String.valueOf(obj), String.valueOf(content.indexOf(keyReplace)));
        switch (Builder.getClassName(obj))
        {
            case "java.lang.String":
                content = content.replace(keyReplace, obj.toString());
                break;

            case "java.lang.Integer":
            case "int":
            case "float":
            case "double":
                content = content.replace(keyReplace, String.valueOf(obj));
            case "java.util.HashMap":
        }
        return content;
    }

    private static String loadFile(String file)
    {
        StringBuilder contentBuilder = new StringBuilder();
        file = Application.PATH_RESOURCES + View.PATH_TEMPLATES + file.replace(".sj.html","") + ".sj.html";
        String content = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            Logger.getLogger().error("Template not found: " + file);
        }
        content = contentBuilder.toString();
        return content;
    }
}
