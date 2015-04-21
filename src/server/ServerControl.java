package server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by regmoraes on 20/04/15.
 */
public class ServerControl {

private static ServerControl instance;

    Registry registry;
    IServer server;

    public static ServerControl getInstance(){

        if(instance == null){
            return new ServerControl();

        }else {
            return instance;
        }
    }

    public void initializeServer() throws RemoteException{

        System.setProperty("java.security.policy","src/javaPolicy.policy");

        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }

        try{

            server = new Server();
            registry = LocateRegistry.createRegistry(1099);
            registry.bind("Server", server);

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    public IServer getServer() throws RemoteException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry();
        return (IServer) registry.lookup("Server");
    }

}
