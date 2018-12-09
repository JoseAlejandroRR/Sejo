import app.Bootstrap;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args)
    {
        Bootstrap.run();

        /*String[] urls = new String[5];
        urls[0] = "/apis/user/[user_id]";
        urls[1] = "/apis/user/[user_id]/address/[adress_id]";
        urls[2] = "/apis/post/[user_id]/address/[adress_id]";
        urls[3] = "/apis/user";
        urls[4] = "/apis/user/";
        for (int i = 5; i < 10; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                //System.out.println(j);
                urls[j+i] = urls[j];
            }
        }
        long startTime   =  System.currentTimeMillis();
        long startMemory = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        String urlPatter = "";
        String route = "/apis/user/user1/address/user?user=user1";
        String urlX = "";
        String urlY = route;
        String[] vars;
        int nR = 1;
        for(String url : urls)
        {
            urlX = url.replaceAll("/","__");
            urlY = route.replaceAll("/","__");
            //System.out.println("Search in: " + url);
            //System.out.println("With : " + route);

            Pattern p = Pattern.compile("\\[(.*?)\\]");
            String urlP = "";
            urlPatter = url;
            Matcher m = p.matcher(url);

            ArrayList varsA = new ArrayList();

            while(m.find()) {
                //System.out.println("COUNT " + m.group());
                for(int i = 0; i < m.groupCount(); i++)
                {

                }
                varsA.add(m.group());
                urlX = urlX.replace(m.group(),"(.*?)");

                urlPatter = urlPatter.replace(m.group(),"([0-9]+)").replaceAll("/", Matcher.quoteReplacement("\\/"));
                //urlP = urlP+"//i";
                System.out.println("GET " + m.group());
            }

            p = Pattern.compile(urlX);
            m = p.matcher(urlY);
            boolean b = false;
            while(m.find()) {
                b = true;
                //System.out.println("WORKS " + m.group());
            }
            // System.out.println("PATTER FINAL " +urlPatter );
           // System.out.println("ROUTER FINAL " +urlX );
            String[] paths  = urlX.replaceAll("//","/").split("/");
            ArrayList pathsA = new ArrayList();

            for(String node : paths)
            {
                System.out.println("Node " +node);
                pathsA.add(node);
                urlY = urlY.replace(node,"").replaceAll("//","/");
            }
            vars = urlY.split("/");

            for(String value : route.split("/"))
            {
                System.out.println("var " +value );
                if(value.trim().length() > 0){
                    if(pathsA.contains(value)){

                    } else {
                        if(varsA.contains(value)){

                        }
                    }
                }
            }
            System.out.println("Pattern " +urlX );
            System.out.println("Route   " +urlY );
            if(b && urlX.split("__").length==urlY.split("__").length) {
                System.out.printf("ROUTE %d EXIST \n", nR);
            } else {
                System.out.printf("ROUTE %d NOT EXIST\n", nR);
            }
            //System.out.println("_____________________" );
            nR++;
        }

        long endTime   =  System.currentTimeMillis();
        long endMemory = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long totalTime = (endTime - startTime);
        long totalMemory = (endMemory - startMemory);
        System.out.printf("Duration %d miliseconds using %s kb of memory \n", totalTime, totalMemory / 1000);
        */
    }
}
