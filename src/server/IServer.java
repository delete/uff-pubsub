package server;

import client.IClient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.List;

/**
 * Created by regmoraes on 18/04/15.
 */
public interface IServer extends Remote {

    void initializeServer() throws RemoteException;
    void publish(Article a) throws RemoteException;
    void subscribe(IClient c, String keyword) throws RemoteException;
    List<String> getSubscriptionsCategory() throws RemoteException;
    List<String> getConnectedClients() throws RemoteException;
    void setConnectedClient(String clientIP) throws RemoteException;
    void connectToServer(String bindName,IClient client,String serverIP) throws RemoteException;
}
