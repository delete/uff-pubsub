package client;

import UI.MainControl;
import model.Article;
import server.IServer;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by regmoraes on 20/04/15.
 */
public class ClientControl {

    private static ClientControl instance;
    private Remote client;

    public static ClientControl getInstance(){

        if(instance == null){
            return new ClientControl();

        }else {
            return instance;
        }
    }

    public void initializeClient(String serverIP) throws RemoteException{

        System.setProperty("java.security.policy","src/javaPolicy.policy");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {

            client = new Client();

            Registry registry = LocateRegistry.getRegistry(serverIP);
            registry.rebind("Client", client);

        } catch (Exception e) {

           MainControl.getInstance().showErrorMessage("Cannot initiate client service");
            e.printStackTrace();
        }
    }

    public void notifyNewPublication(Article a){

        MainControl.getInstance().notifyNewPublication(a);
    }

    public IClient getClient() throws RemoteException, NotBoundException{

        Registry registry = LocateRegistry.getRegistry();
        return (IClient) registry.lookup("Client");
    }
}
