package ui.server;

import javax.swing.*;

/**
 * Created by regmoraes on 20/04/15.
 */
public class ServerGUI extends JFrame implements IServerGUI{

    private JPanel rootPanel;
    private JLabel labelServerStatus;
    private static ServerGUI instance;

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
}
