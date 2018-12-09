package com.skillcorp.sejoframework.helpers;

import com.skillcorp.sejoframework.contracts.providers.ILogger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger implements ILogger {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

    public static final String MODE_DEBUG = "Debug";

    public static final String MODE_INFO = "Info";

    public static final int DEBUG = 1;

    public static final int INFO = 2;

    private int mode = DEBUG;

    private String modeLevel = MODE_DEBUG;

    public Logger(int mode)
    {
        this.mode = mode;

        if(mode == INFO) {
            modeLevel = MODE_INFO;
        }
    }

    @Override
    public void debug(String... strings) {

        if(mode != DEBUG) return;

        showConsole(strings, MODE_DEBUG);
    }

    @Override
    public void info(String... strings) {

        showConsole(strings, MODE_INFO);
    }

    private void showConsole(String[] strings, String mode)
    {
        String timeStamp = format.format(Calendar.getInstance().getTime());

        print(mode + " \t ");
        print(timeStamp+ " \t ");

        for(String str:  strings)
        {
            print(str + " \t");
        }
        print("\n");
    }

    private void print(String str) {
        System.out.printf(str);
    }
}
