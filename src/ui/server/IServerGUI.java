package ui.server;

import ui.IGUI;

import java.util.List;

/**
 * Created by regmoraes on 21/04/15.
 */
public interface IServerGUI extends IGUI {

    @Override
    void initializeGUI();
    void showClientsIP();
    void notifyNewClient(List<String> client);
}
