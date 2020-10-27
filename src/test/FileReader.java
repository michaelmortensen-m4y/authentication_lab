package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileReader {
    public static void main(String[] args) {
        Properties prop = new Properties();
        String fileName = "app.cfg";
        InputStream is = null;

        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {
            System.err.println("No config file found");
        }

        try {
            prop.load(is);
        } catch (IOException ex) {
            System.err.println("Could not load config");
        }

        System.out.println(prop.getProperty("app.version"));
    }
}
