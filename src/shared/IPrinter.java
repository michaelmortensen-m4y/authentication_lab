package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IPrinter extends Remote {
    void log(String message) throws RemoteException;
    String echo(String message) throws RemoteException;

    void print(String filename, String printer) throws RemoteException;

    List<Map<String, String>> queue(String printer) throws RemoteException;
    void topQueue(String printer, int job) throws RemoteException;

    void start() throws RemoteException;
    void stop() throws RemoteException;
    void restart() throws RemoteException;

    String status() throws RemoteException;

    String readConfig(String parameter) throws RemoteException;
    void setConfig(String parameter, String value) throws RemoteException;
}
