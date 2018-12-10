package com.skillcorp.sejoframework.files;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class File {

    public static boolean save(String filePath, String content)
    {
        return File.save(filePath, content, false);
    }

    public static boolean save(String filePath, String content, boolean rewrite)
    {
        if(content.trim().length()==0) return false;

        if(rewrite) {
            return File.rewriteFile(filePath, content);
        } else {
            return File.writeFile(filePath, content);
        }
    }

    private static boolean writeFile(String path, String content)
    {
        byte data[] = content.getBytes();
        Path p = Paths.get(path);

        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(p, CREATE))) {
            out.write(data, 0, data.length);
            return true;
        } catch (IOException x) {
            System.err.println(x);
        }
        return false;
    }

    private static boolean rewriteFile(String path, String content)
    {
        byte data[] = content.getBytes();
        Path p = Paths.get(path);

        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(p, CREATE, APPEND))) {
            out.write(data, 0, data.length);
            return true;
        } catch (IOException x) {
            System.err.println(x);
        }
        return false;
    }
}
