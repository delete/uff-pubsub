package client;

import client.Client;
import client.IClient;
import model.Publication;
import server.IServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by regmoraes on 18/04/15.
 */
public class MainClient{

    public static void main(String args[]) throws RemoteException {


        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {

            IClient client = new Client();
            Publication publication = new Publication("Programming", "RMI", "RMI code ....");
            Publication publication2 = new Publication("Vacation", "Cancun Resort", "Sunny ....");

            Registry registry = LocateRegistry.getRegistry("localhost");
            registry.rebind("client.Client", client);

            IServer server = (IServer) registry.lookup("server.Server");

            server.subscribe(client, "Programacao");
            server.subscribe(client, "Vacation");
            server.publish(publication);
            server.publish(publication2);

        } catch (Exception e) {

            System.out.println("Connection Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
