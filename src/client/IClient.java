package client;

import server.Article;
import server.ServerNotFoundException;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by regmoraes on 18/04/15.
 */
public interface IClient extends Remote{

    void initializeClient(String serverIP) throws RemoteException, ServerNotFoundException;
    void notifyNewArticle(Article a) throws RemoteException;
    List<Article> getArticles(String keyword) throws RemoteException, NotBoundException;
    String getConnectedServerIP() throws RemoteException;
}
