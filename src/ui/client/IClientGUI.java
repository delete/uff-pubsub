package ui.client;

import server.Article;
import ui.IGUI;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by regmoraes on 21/04/15.
 */
public interface IClientGUI extends IGUI {

    @Override
    void initializeGUI();
    void notifyNewArticle(Article a);
}
