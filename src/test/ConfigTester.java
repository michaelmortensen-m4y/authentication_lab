package test;

import server.ConfigHandler;

public class ConfigTester {
    public static void main(String[] args) {

        String version = ConfigHandler.readConfig("app.cfg", "app.version");
        String timezone = ConfigHandler.writeConfig("app.cfg", "app.timezone", "GMT");

        System.out.println(version);
        System.out.println(timezone);
    }
}
