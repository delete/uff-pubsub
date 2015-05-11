package client;

import main.MainPubSub;
import server.Article;
import server.ServerNotFoundException;
import ui.client.IClientGUI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

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

    private void setConnectedServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    @Override
    public void notifyNewArticle(Article a) throws RemoteException {

        IClientGUI clientGUI = MainPubSub.getClientGUI();
        clientGUI.notifyNewArticle(a);
    }

    @Override
    public List<Article> getArticles(String keyword) throws RemoteException, NotBoundException {

        return MainPubSub.getServer(serverIP).getArticles(keyword);
    }

    @Override
    public String getConnectedServerIP() {
        return serverIP;
    }

    @Override
    public void notifyNewCategory() throws RemoteException, NotBoundException {

        MainPubSub.getClientGUI().notifyNewCategory();
    }

}
