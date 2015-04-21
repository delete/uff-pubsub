package UI;

import client.ClientControl;
import client.IClient;
import model.Article;
import server.ServerControl;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by regmoraes on 20/04/15.
 */
public class MainControl {

    private static MainControl instance;
    private ClientControl clientControl = ClientControl.getInstance();
    private ServerControl serverControl = ServerControl.getInstance();
    public ClientGUI clientGUI;

    public static MainControl getInstance(){

        if(instance==null){
            return new MainControl();

        }else{
            return instance;
        }
    }

    public static void main(String args[]){

        SwingUtilities.invokeLater(() -> new MainPubSub().initializeGUI());
    }

    public void initializeClientGUI(String serverIP) throws RemoteException,NotBoundException {

        try {

            clientControl.initializeClient(serverIP);
            ClientGUI.getInstance().initializeGUI();


        }catch (RemoteException e){

            showErrorMessage("Cannot start client service");
            e.printStackTrace();
        }
    }

    public void initializeServerGUI() throws RemoteException {

        try{
            serverControl.initializeServer();
            ServerGUI.getInstance().initializeGUI();

        }catch (RemoteException e){

            showErrorMessage("Cannot start server");
            e.printStackTrace();
        }
    }

    public void showErrorMessage(String message){

        JOptionPane.showMessageDialog(null, message);
    }

    public void publish(Article a) throws NotBoundException,RemoteException{
        serverControl.getServer().publish(a);
    }

    public void subscribe(String keyword) throws NotBoundException,RemoteException{

        IClient client = clientControl.getClient();
        serverControl.getServer().subscribe(client, keyword);
    }

    public List<String> getSubscriptionsCategory() throws NotBoundException,RemoteException{
        return serverControl.getServer().getSubscriptionsCategory();
    }

    public void notifyNewPublication(Article a){

        ClientGUI.getInstance().notifyNewPublication(a);
    }
}
