package main;

import ui.client.IClientGUI;
import ui.main.MainGUI;
import client.IClient;
import server.IServer;
import ui.server.IServerGUI;

import javax.swing.*;

/**
 * Created by regmoraes on 20/04/15.
 */
public class MainPubSub {

    private static MainPubSub instance;
    private static IServer server;
    private static IClient client;
    private static IServerGUI serverGUI;
    private static IClientGUI clientGUI;

    public static MainPubSub getInstance(){

        if(instance==null){
            return new MainPubSub();

        }else{
            return instance;
        }
    }

    public static void main(String args[]){

        SwingUtilities.invokeLater(() -> new MainGUI().initializeGUI());
    }

    public static IServer getServer() {
        return server;
    }

    public static IClient getClient() {
        return client;
    }

    public static IServerGUI getServerGUI() {
        return serverGUI;
    }

    public static IClientGUI getClientGUI() {
        return clientGUI;
    }

    public static void setInstance(MainPubSub instance) {
        MainPubSub.instance = instance;
    }

    public static void setServer(IServer server) {
        MainPubSub.server = server;
    }

    public static void setClient(IClient client) {
        MainPubSub.client = client;
    }

    public static void setServerGUI(IServerGUI serverGUI) {
        MainPubSub.serverGUI = serverGUI;
    }

    public static void setClientGUI(IClientGUI clientGUI) {
        MainPubSub.clientGUI = clientGUI;
    }
}