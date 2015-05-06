package ui.client;

import server.Article;
import ui.IGUI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by regmoraes on 21/04/15.
 */
public interface IClientGUI extends IGUI {

    @Override
    void initializeGUI() throws NotBoundException, RemoteException;
    void notifyNewArticle(Article a);
    void notifyNewCategory() throws NotBoundException, RemoteException;
}
