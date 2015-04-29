package ui.main;

import client.Client;
import main.MainPubSub;
import server.Server;
import server.ServerNotFoundException;
import ui.IGUI;
import ui.client.ClientGUI;
import ui.server.ServerGUI;

import javax.swing.*;
import java.rmi.RemoteException;

/**
 * Created by regmoraes on 20/04/15.
 */
public class MainGUI extends JFrame implements IGUI {

    private JButton buttonConnectServer;
    private JButton buttonStartServer;
    private JTextField textFieldServerIP;
    private JPanel rootPanel;

    @Override
    public void initializeGUI() {

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeListeners();

        setVisible(true);
    }

    public void initializeListeners(){

        buttonStartServer.addActionListener(actionListener -> {

            try {

                MainPubSub.setServer(Server.getInstance());

                MainPubSub.setServerGUI(ServerGUI.getInstance());
                MainPubSub.getServerGUI().initializeGUI();

            } catch (RemoteException e) {

                JOptionPane.showMessageDialog(this.getContentPane(), "Cannot Start Server");
                e.printStackTrace();
            }
        });

        buttonConnectServer.addActionListener(actionListener -> {

            if (textFieldServerIP.getText().equals("")) {

                JOptionPane.showMessageDialog(this.getContentPane(), "Please, insert the server IP");

            } else {

                try {

                    MainPubSub.setClient(new Client());
                    MainPubSub.getClient().initializeClient(textFieldServerIP.getText());
                    MainPubSub.setClientGUI(ClientGUI.getInstance());
                    MainPubSub.getClientGUI().initializeGUI();

                } catch (ServerNotFoundException e) {

                    JOptionPane.showMessageDialog(this.getContentPane(), "Cannot start client, try a valid IP.");
                }
                catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
