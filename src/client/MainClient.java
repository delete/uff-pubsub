package client;

import UI.MainClientGUI;

import java.rmi.RemoteException;

/**
 * Created by regmoraes on 19/04/15.
 */
public class MainClient {

    public static MainClientGUI clientGUI;

    public static void main(String args[]){

        try {

            ClientControl clientControl = new ClientControl();

            clientGUI = new MainClientGUI(clientControl);
            clientGUI.showSubscribePanel();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
