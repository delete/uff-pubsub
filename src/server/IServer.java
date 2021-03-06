package server;

import client.IClient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by regmoraes on 18/04/15.
 */
public interface IServer extends Remote {

    void initializeServer() throws RemoteException;
    void publish(Article a) throws RemoteException;
    void subscribe(IClient c, String keyword) throws RemoteException;
    List<String> getSubscriptionsCategory() throws RemoteException;
    List<Article> getArticles(String keyword) throws RemoteException;
    void connectToServer(String bindName,IClient client,String serverIP) throws RemoteException;
}
