package main;

import ui.main.MainGUI;
import client.IClient;
import server.IServer;

import javax.swing.*;

/**
 * Created by regmoraes on 20/04/15.
 */
public class MainPubSub {

    private static MainPubSub instance;
    public static IServer server;
    public static IClient client;

    public static MainPubSub getInstance(){

        if(instance==null){
            return new MainPubSub();

        }else{
            return instance;
        }
    }

    public static void main(String args[]){

        //System.setProperties("codebase","out/publish-subscriber.jar");
        SwingUtilities.invokeLater(() -> new MainGUI().initializeGUI());
    }
}