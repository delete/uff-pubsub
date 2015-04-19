package client;

import model.Publication;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by regmoraes on 18/04/15.
 */
public class Client extends UnicastRemoteObject implements IClient {

    public Client() throws RemoteException{
    }

    @Override
    public void printPublication(Publication p) throws RemoteException {

        if(p!=null){
            System.out.println("model.Publication '"+p.getTitle()+"' Received \n");
            System.out.println("Category: "+p.getTitle()+" \n");
            System.out.println("Content: '"+p.getContent()+"' \n");
        }

        else{

            System.out.println("Error: subscription doesn't exist \n");
        }
    }
}
