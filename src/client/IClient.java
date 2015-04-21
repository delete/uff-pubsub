package client;

import model.Article;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by regmoraes on 18/04/15.
 */
public interface IClient extends Remote{

    void showPublication(Article a) throws RemoteException;
}
