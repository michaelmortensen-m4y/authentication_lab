package server;

import shared.IPrinter;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class Printer implements IPrinter {

    private static final String name = "Printer";

    public Printer() {
        super();
    }

    public static void main(String[] args) {
        try {
            Printer printer = new Printer();
            IPrinter stub = (IPrinter) UnicastRemoteObject.exportObject(printer, 0);

            Registry registry = LocateRegistry.createRegistry(1099);
            // Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);

            System.out.println("Printer Service ready");
        } catch (Exception e) {
            System.err.println("Printer exception");
            e.printStackTrace();
        }
    }

    @Override
    public void log(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public String echo(String message) throws RemoteException {
        log("Received message: " + message);
        return "Received: " + message;
    }

    @Override
    public String login(String username, String password) throws RemoteException {
        System.out.println(username);
        System.out.println(password);
        return AuthManager.login(username, password);
    }

    @Override
    public void print(String filename, String printer, String token) throws RemoteException {
        System.out.println("Client invoked method print for:");
        if (AuthManager.checkPermission("print", token)) {
            System.out.println("Filename: " + filename);
            System.out.println("Printer: " + printer);
            return;
        }

        System.out.println("Unauthorized attempt");
    }

    @Override
    public List<Map<String, String>> queue(String printer, String token) throws RemoteException {
        System.out.println("Client invoked method queue for printer: " + printer);
        if (AuthManager.checkPermission("queue", token)) {
            System.out.println("Queue for printer: " + printer);
        }

        System.out.println("Unauthorized attempt");
        return null;
    }

    @Override
    public void topQueue(String printer, int job, String token) throws RemoteException {
        System.out.println("Client invoked method topQueue for:");
        System.out.println("Printer: " + printer);
        System.out.println("Job: " + job);
    }

    @Override
    public void start(String token) throws RemoteException {
        System.out.println("Client invoked method start");
        if (AuthManager.checkPermission("start", token)) {
            System.out.println("Server started");
        }

        System.out.println("Unauthorized attempt");
    }

    @Override
    public void stop(String token) throws RemoteException {
        System.out.println("Client invoked method stop");
        if (AuthManager.checkPermission("stop", token)) {
            System.out.println("Server started");
        }

        System.out.println("Unauthorized attempt");
    }

    @Override
    public void restart(String token) throws RemoteException {
        System.out.println("Client invoked method restart");
        if (AuthManager.checkPermission("restart", token)) {
            System.out.println("Server started");
        }

        System.out.println("Unauthorized attempt");
    }

    @Override
    public String status(String printer, String token) throws RemoteException {
        System.out.println("Client invoked method status for printer " + printer);
        if (AuthManager.checkPermission("status", token)) {
            return "Status for printer: " + printer + " - OK";
        }

        System.out.println("Unauthorized attempt");
        return null;
    }

    @Override
    public String readConfig(String parameter, String token) throws RemoteException {
        if (AuthManager.checkPermission("readConfig", token)) {
            return ConfigHandler.readConfig("app.cfg", parameter);
        }

        System.out.println("Unauthorized attempt");
        return null;
    }

    @Override
    public String setConfig(String parameter, String value, String token) throws RemoteException {
        if (AuthManager.checkPermission("restart", token)) {
            return ConfigHandler.writeConfig("app.cfg", parameter, value);
        }

        System.out.println("Unauthorized attempt");
        return null;
    }
}