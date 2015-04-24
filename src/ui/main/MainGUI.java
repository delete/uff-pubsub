package ui.main;

import client.Client;
import main.MainPubSub;
import server.IServer;
import server.Server;
import ui.IGUI;
import ui.client.ClientGUI;
import ui.server.ServerGUI;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
/**
 * Created by regmoraes on 20/04/15.
 */
public class MainGUI extends JFrame implements IGUI {

    private JButton buttonConnectServer;
    private JButton buttonStartServer;
    private JTextField textFieldServerIP;
    private JPanel rootPanel;
    private MainPubSub mainPubSub = MainPubSub.getInstance();
    @Override
    public void initializeGUI(){

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeListeners();

        setVisible(true);
    }

    public void initializeListeners(){

        buttonStartServer.addActionListener(actionListener ->{

            try {

                mainPubSub.setServer(Server.getInstance());
                mainPubSub.getServer().initializeServer();

                mainPubSub.setServerGUI(ServerGUI.getInstance());
                mainPubSub.getServerGUI().initializeGUI();

            }catch (RemoteException e){

                JOptionPane.showMessageDialog(this.getContentPane(),
                        "Cannot Start Server");
                e.printStackTrace();
            }
        });

        buttonConnectServer.addActionListener(actionListener -> {

            if (textFieldServerIP.getText().equals("")){

                JOptionPane.showMessageDialog(this.getContentPane(), "Please, insert the server IP");

            }else{

                try {

                    mainPubSub.setClient(new Client());
                    mainPubSub.getClient().initializeClient(textFieldServerIP.getText());

                    mainPubSub.setClientGUI(ClientGUI.getInstance());
                    mainPubSub.getClientGUI().initializeGUI();

                } catch (RemoteException e) {

                    JOptionPane.showMessageDialog(this.getContentPane(), "Cannot start client");
                    e.printStackTrace();
                }
            }
        });
    }
}
