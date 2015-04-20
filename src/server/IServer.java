package server;

import client.IClient;
import model.Article;
import model.Publication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by regmoraes on 18/04/15.
 */
public interface IServer extends Remote {

    void publish(Article p) throws RemoteException;
    void subscribe(IClient c, String keyword) throws RemoteException;
    List<String> getSubscriptionsCategory() throws RemoteException;
}
