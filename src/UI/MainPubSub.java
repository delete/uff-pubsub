package UI;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by regmoraes on 20/04/15.
 */
public class MainPubSub extends JFrame{

    private JButton buttonConnectServer;
    private JButton buttonStartServer;
    private JTextField textFieldServerIP;
    private JPanel rootPanel;
    private MainControl mainControl = MainControl.getInstance();

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

                mainControl.initializeServerGUI();

            }catch (RemoteException e){

                mainControl.showErrorMessage("Cannot start server");
                e.printStackTrace();
            }
        });

        buttonConnectServer.addActionListener(actionListener -> {

            if (textFieldServerIP.getText().equals("")){

                JOptionPane.showMessageDialog(null, "Please, insert the server IP");

            }else{

                try {

                    mainControl.initializeClientGUI(textFieldServerIP.getText());

                } catch (RemoteException e) {

                    mainControl.showErrorMessage("Cannot start client application");
                    e.printStackTrace();

                }catch (NotBoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
