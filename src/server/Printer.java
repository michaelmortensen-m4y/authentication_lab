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
    public void print(String filename, String printer) throws RemoteException {

    }

    @Override
    public List<Map<String, String>> queue(String printer) throws RemoteException {
        return null;
    }

    @Override
    public void topQueue(String printer, int job) throws RemoteException {

    }

    @Override
    public void start() throws RemoteException {

    }

    @Override
    public void stop() throws RemoteException {

    }

    @Override
    public void restart() throws RemoteException {

    }

    @Override
    public String status() throws RemoteException {
        return null;
    }

    @Override
    public String readConfig(String parameter) throws RemoteException {
        return null;
    }

    @Override
    public void setConfig(String parameter, String value) throws RemoteException {

    }
}