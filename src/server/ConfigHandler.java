package server;

import java.io.*;
import java.util.Properties;

public class ConfigHandler {

    private static String fileName = "app.cfg";

    public static String readConfig(String parameter) {
        Properties prop = new Properties();
        InputStream is = null;

        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {
            return "No config file found";
        }

        try {
            prop.load(is);
            is.close();
        } catch (IOException ex) {
            return "Could not load configuration file";
        }

        return prop.getProperty(parameter);
    }

    public static String writeConfig(String parameter, String value) {
        Properties prop = new Properties();
        InputStream is = null;
        OutputStream os = null;

        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {
            return "No config file found";
        }

        try {
            prop.load(is);
            is.close();
        } catch (IOException ex) {
            return "Could not load configuration file";
        }

        prop.setProperty(parameter, value);

        try {
            os = new FileOutputStream(fileName);
            prop.store(os, null);
            os.close();
        } catch (IOException e) {
            System.err.println("Couldn't write to config");
        }

        return value;
    }

}
