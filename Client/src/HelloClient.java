import java.rmi.RemoteException;

public class HelloClient implements Hello {
    public static void main(String[] args) {
        System.out.println("Hello from Client");
    }

    @Override
    public void printMsg() throws RemoteException {

    }
}
