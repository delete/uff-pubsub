package client;

import main.MainPubSub;
import server.Article;
import server.ServerNotFoundException;
import ui.client.ClientGUI;
import ui.client.IClientGUI;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by regmoraes on 18/04/15.
 */
public class Client extends UnicastRemoteObject implements IClient{

    String serverIP;

    public Client() throws RemoteException {
        super();
    }

    @Override
    public void initializeClient(String serverIP) throws RemoteException, ServerNotFoundException {

        System.setProperty("java.security.policy", "src/main/javaPolicy.policy");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {

            MainPubSub.getServer(serverIP).connectToServer("Client", this, serverIP);
            setConnectedServerIP(serverIP);

        }catch (Exception e) {
            throw new ServerNotFoundException();
        }
    }

    @Override
    public void showNewArticles(Article a) throws RemoteException {

        IClientGUI clientGUI = MainPubSub.getInstance().getClientGUI();
        clientGUI.showNewArticles(a);
    }

    @Override
    public String getConnectedServerIP() {
        return serverIP;
    }

    private void setConnectedServerIP(String serverIP) {
        this.serverIP = serverIP;
    }
}
