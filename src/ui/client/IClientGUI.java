package ui.client;

import ui.IGUI;
import server.Article;

import java.rmi.RemoteException;

/**
 * Created by regmoraes on 21/04/15.
 */
public interface IClientGUI extends IGUI {

    @Override
    void initializeGUI();
    void showNewArticles(Article a) throws RemoteException;
}
