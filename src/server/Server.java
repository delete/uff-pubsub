package server;

import client.IClient;
import model.Publication;
import model.Subscription;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by regmoraes on 18/04/15.
 */
public class Server extends UnicastRemoteObject implements IServer {

    List<Subscription> subscriptions = new ArrayList<>();
    List<Publication> publications = new ArrayList<>();

    protected Server() throws RemoteException {
        super();
    }

    @Override
    public void subscribe(IClient c, String keyword) throws RemoteException {

        try{

            for(Subscription s: subscriptions){

                if(s.getKeyword().equals(keyword)) {

                    s.addSubscriber(c);
                }
            }

            // If keyword doesn't exist in subscriptions category
            // create a new category and add the subscriber in the list
            subscriptions.add(new Subscription(keyword));
            subscriptions.get(subscriptions.size()-1).addSubscriber(c);

            //System.out.println("There's no category's with this keyword for subscription :(");

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    @Override
    public void publish(Publication p) {

        try{

            publications.add(p);
            notifySubscribers(p);

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    void notifySubscribers(Publication p){

        try{

            for(Subscription s: subscriptions){

                if(s.getKeyword().equals(p.getKeyword())){

                    for(IClient client: s.getClients()){

                        client.printPublication(p);
                    }
                }
            }

        }catch (Exception e){

            System.out.println("Cannot notify subscribers: "+ e.getMessage());
            e.printStackTrace();
        }

    }
}
