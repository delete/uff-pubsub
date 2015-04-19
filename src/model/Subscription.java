package model;

import client.IClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by regmoraes on 18/04/15.
 */
public class Subscription {

    String keyword;
    List<IClient> clients;

    public Subscription(String keyword) {
        this.keyword = keyword;
        this.clients = new ArrayList<>();
    }

    public String getKeyword() {
        return keyword;
    }

    public void addSubscriber(IClient c){

        clients.add(c);
    }

    public List<IClient> getClients() {

        return this.clients;
    }
}
