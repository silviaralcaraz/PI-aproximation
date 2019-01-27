/**
 * Created by silvia on 5/03/18.
 */

import java.rmi.*;
import java.rmi.server.*;
import java.util.Date;
import java.util.Random;

public class ImplementationRMI extends UnicastRemoteObject implements InterfaceRMI {
    public ImplementationRMI() throws RemoteException {
        super();
    }

    /* Function that calculates the number of points, m, of the given n,
    that fall into the circumference applying method of Monte Carlo. */
    public long montecarloMethod(long n) throws RemoteException {
        long m = 0; // peers witch validate the condition
        double random1, random2;
        Random randomNum = new Random(new Date().getTime());
        for (int i = 0; i < n; i++) {
            random1 = randomNum.nextDouble();
            random2 = randomNum.nextDouble();
            if ((Math.pow(random1, 2) + Math.pow(random2, 2)) <= 1) {
                m++;
            }
        }
        return m;
    }
}