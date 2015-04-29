package client;

import server.Article;
import server.ServerNotFoundException;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by regmoraes on 18/04/15.
 */
public interface IClient extends Remote{

    void initializeClient(String serverIP) throws RemoteException, ServerNotFoundException;
    void showNewArticles(Article a) throws RemoteException;
    String getConnectedServerIP() throws RemoteException;
}
