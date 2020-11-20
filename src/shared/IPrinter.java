package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IPrinter extends Remote {
    void log(String message) throws RemoteException;
    String echo(String message) throws RemoteException;

    String login(String username, String password) throws RemoteException;

    // prints file filename on the specified printer
    void print(String filename, String printer, String token) throws RemoteException;

    // lists the print queue for a given printer on the user's display in lines of the form <job number> <file name>
    List<Map<String, String>> queue(String printer, String token) throws RemoteException;

    // moves job to the top of the queue
    void topQueue(String printer, int job, String token) throws RemoteException;

    void start() throws RemoteException; // starts the print server
    void stop() throws RemoteException; // stops the print server
    void restart() throws RemoteException; // stops the print server, clears the print queue and starts the print server again

    // prints status of printer on the user's display
    String status(String printer, String token) throws RemoteException;

    // prints the value of the parameter on the user's display
    String readConfig(String parameter, String token) throws RemoteException;

    // sets the parameter to value
    String setConfig(String parameter, String value, String token) throws RemoteException;
}
