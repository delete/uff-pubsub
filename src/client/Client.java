package client;

import main.MainPubSub;
import server.Article;
import ui.client.ClientGUI;
import ui.client.IClientGUI;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by regmoraes on 18/04/15.
 */
public class Client extends UnicastRemoteObject implements IClient{

    public Client() throws RemoteException {
        super();
    }

    @Override
    public void initializeClient(String serverIP) throws RemoteException {

        System.setProperty("java.security.policy", "src/main/javaPolicy.policy");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {

            Registry registry = LocateRegistry.getRegistry(serverIP);
            registry.rebind("Client", this);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(ClientGUI.getInstance().getContentPane(),"Cannot start client");
            e.printStackTrace();
        }
    }

    @Override
    public void showNewArticles(Article a) throws RemoteException {

        IClientGUI clientGUI = MainPubSub.getInstance().getClientGUI();
        clientGUI.showNewArticles(a);
    }
}
