package ui.server;

import main.MainPubSub;

import javax.swing.*;
import java.util.List;

/**
 * Created by regmoraes on 20/04/15.
 */
public class ServerGUI extends JFrame implements IServerGUI{

    private JPanel rootPanel;
    private JLabel labelServerStatus;
    private JList listConnectedClients;
    private DefaultListModel<String> listModelClient;
    private JLabel labelClients;
    private static ServerGUI instance;

    @Override
    public void notifyNewClient(List<String> client) {

        listModelClient = new DefaultListModel<>();

        client.forEach(listModelClient::addElement);

        listConnectedClients.setModel(listModelClient);
    }

    private MainPubSub mainPubSub = MainPubSub.getInstance();

    public static ServerGUI getInstance(){

        if(instance == null){
            return new ServerGUI();

        }else {
            return instance;
        }
    }

    @Override
    public void initializeGUI(){

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        labelServerStatus.setText("Server running");
        setVisible(true);
    }

    @Override
    public void showClientsIP() {


    }
}
