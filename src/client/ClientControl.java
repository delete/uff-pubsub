package client;

import model.Article;
import server.IServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by regmoraes on 18/04/15.
 */
public class ClientControl extends UnicastRemoteObject implements IClient{

    private IServer server;
    Registry registry;

    public ClientControl() throws RemoteException {
        super();
        initialize();
    }

    public void initialize() {

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {

            registry = LocateRegistry.getRegistry("localhost");
            registry.rebind("Client", this);

            server = (IServer) registry.lookup("server.Server");

        } catch (Exception e) {

            System.out.println("Connection Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public IServer contactServer() {
        return server;
    }

    public IClient getClient() {
        return this;
    }

    @Override
    public void showPublication(Article p) throws RemoteException {

       MainClient.clientGUI.notifyNewPublication(p);
    }
}
