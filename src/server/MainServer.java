package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by regmoraes on 18/04/15.
 */
public class MainServer {

    public static void main(String args[]){

        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }

        try{

            IServer server = new Server();

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("server.Server",server);

            System.out.println("server.Server is bound in registry");

        }catch (Exception e){

            System.out.println("server.Server Error: "+ e.getMessage());
            e.printStackTrace();
        }
    }
}
