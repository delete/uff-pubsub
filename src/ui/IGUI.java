package ui;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by regmoraes on 21/04/15.
 */
public interface IGUI {

    void initializeGUI() throws NotBoundException, RemoteException;
}
