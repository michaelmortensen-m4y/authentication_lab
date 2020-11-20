package client;

import shared.IPrinter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLOutput;

public class Client {

    private static final String name = "Printer";
    private static String token = null;

    public Client() {
        super();
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            IPrinter printer = (IPrinter) registry.lookup(name);

            System.out.println("Welcome to print server 2000");
            System.out.println("0) Exit");
            System.out.println("1) Log in");
            System.out.println("2) Print");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                String input = reader.readLine().strip();

                if (input.equals("0")) {
                    System.out.println("Goodbye!");
                    break;
                }

                if (input.equals("1")) {
                    System.out.println("Enter username:");
                    String username = reader.readLine().strip();
                    System.out.println("Enter password:");
                    String password = reader.readLine().strip();
                    token = printer.login(username, password).strip();
                    if (token == null) {
                        System.err.println("Wrong credentials");
                    }
                    System.out.println("token: " + token);
                    continue;
                }

                if (input.equals("2")) {
                    if (token == null) {
                        System.out.println("You need to be logged in.");
                        continue;
                    }

                    System.out.println("Specify file for printing:");
                    String fileName = reader.readLine().strip();
                    System.out.println("Specify printer:");
                    String device = reader.readLine().strip();
                    printer.print(fileName, device, token);
                }
            }
            String response = printer.echo("hello from client");
            System.out.println("Got response from the server: " + response);
        } catch (Exception e) {
            System.out.println("Client error");
            e.printStackTrace();
        }
    }
}
