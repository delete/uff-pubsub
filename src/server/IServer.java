package server;

import client.IClient;
import model.Publication;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by regmoraes on 18/04/15.
 */
public interface IServer extends Remote {

    void subscribe(IClient c, String keyword) throws RemoteException;
    void publish(Publication p) throws RemoteException;
}
