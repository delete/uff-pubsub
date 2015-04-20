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

            if(publications.isEmpty()){

                publications.add(new Publication(a.getKeyword()));
                publications.get(publications.size()-1).addArticle(a);

            }else {

                for (Publication p : publications) {

                    if (p.getKeyword().equals(a.getKeyword())) {

                        p.addArticle(a);
                        notifySubscribers(a);
                        break;
                    }
                }
            }

            updateSubscriptionsCategory(a.getKeyword());

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

            if(!existentCategory(keyword)) {
                subscriptions.add(new Subscription(keyword));
            }
        }
    }

    public boolean existentCategory(String keyword){

        for (Subscription s : subscriptions) {

            if(s.getKeyword().equals(keyword)) {

                return true;
            }
        }

        return false;
    }
}
