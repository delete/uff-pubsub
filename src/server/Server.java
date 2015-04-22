package server;

import client.IClient;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by regmoraes on 18/04/15.
 */
public class Server extends UnicastRemoteObject implements IServer {

    List<Subscription> subscriptions = new ArrayList<>();
    List<Publication> publications = new ArrayList<>();

    public Server() throws RemoteException {
        super();
    }

    @Override
    public void initializeServer() throws RemoteException{

        System.setProperty("java.security.policy","src/main/javaPolicy.policy");

        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }

        try{

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Server", this);

        }catch (Exception e){

            e.printStackTrace();
        }
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

                    client.showNewArticles(a);
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

            for(Subscription s: subscriptions) categorys.add(s.getKeyword());

            return categorys;

        }else {

            return null;
        }
    }

    private void updateSubscriptionsCategory(String keyword) {

        if(subscriptions.isEmpty() || !existentSubscription(keyword) ) {

            subscriptions.add(new Subscription(keyword));
        }
    }

    private boolean existentSubscription(String keyword){

        for (Subscription s : subscriptions) {

            if(s.getKeyword().equals(keyword)) {

                return true;
            }
        }

        return false;
    }

    private boolean existentPublication(String keyword){

        for (Publication p : publications) {

            if(p.getKeyword().equals(keyword)) {

                return true;
            }
        }

        return false;
    }
}
