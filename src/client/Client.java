package client;

import model.Article;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by regmoraes on 18/04/15.
 */
public class Client extends UnicastRemoteObject implements IClient{

    public Client() throws RemoteException {
        super();
    }

    @Override
    public void showPublication(Article a) throws RemoteException {

       ClientControl.getInstance().notifyNewPublication(a);
    }
}
