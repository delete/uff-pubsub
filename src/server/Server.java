package server;

import client.IClient;
import model.Article;
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
                    break;
                }
            }

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    private void notifySubscribers(Article a) throws RemoteException {

        for(Subscription s: subscriptions){

            if(s.getKeyword().equals(a.getKeyword())){

                for(IClient client : s.getClients()){

                    client.showPublication(a);
                }
            }
        }
    }

    @Override
    public void publish(Article a) {

        try{

            if(publications.isEmpty() || !existentPublication(a.getKeyword()) ){

                publications.add(new Publication(a.getKeyword()));
                publications.get(publications.size()-1).addArticle(a);

            }else {

                for (Publication p : publications) {

                    if (p.getKeyword().equals(a.getKeyword())) {

                        p.addArticle(a);

                        break;
                    }
                }
            }

            updateSubscriptionsCategory(a.getKeyword());
            notifySubscribers(a);

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    @Override
    public List<String> getSubscriptionsCategory() throws RemoteException {

        if(!subscriptions.isEmpty()){

            List<String> categorys = new ArrayList<>();

            for(Subscription s: subscriptions){

                categorys.add(s.getKeyword());
            }

            return categorys;

        }else {

            return null;
        }
    }

    private void updateSubscriptionsCategory(String keyword) {

        if(subscriptions.isEmpty()) {

            subscriptions.add(new Subscription(keyword));

        } else {

            if(!existentSubscription(keyword)) {
                subscriptions.add(new Subscription(keyword));
            }
        }
    }

    public boolean existentSubscription(String keyword){

        for (Subscription s : subscriptions) {

            if(s.getKeyword().equals(keyword)) {

                return true;
            }
        }

        return false;
    }

    public boolean existentPublication(String keyword){

        for (Publication p : publications) {

            if(p.getKeyword().equals(keyword)) {

                return true;
            }
        }

        return false;
    }
}
