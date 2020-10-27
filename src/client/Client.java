package client;

import shared.IPrinter;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private static final String name = "Printer";

    public Client() {
        super();
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            IPrinter printer = (IPrinter) registry.lookup(name);

            String response = printer.echo("hello from client");
            System.out.println("Got response from the server: " + response);
        } catch (Exception e) {
            System.out.println("Client error");
            e.printStackTrace();
        }
    }
}
