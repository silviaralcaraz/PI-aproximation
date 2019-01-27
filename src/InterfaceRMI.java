/**
 * Created by silvia on 5/03/18.
 */

import java.rmi.*;

public interface InterfaceRMI extends Remote {
    public long montecarloMethod(long n) throws java.rmi.RemoteException;
}
