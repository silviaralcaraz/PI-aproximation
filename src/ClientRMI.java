/**
 * Created by silvia on 5/03/18.
 */

import java.rmi.*;

public class ClientRMI extends Thread {
    private long m; // number of points into the circumference
    //private long n = 100000000; // number of random order peers
    private long n = 10000; // number of random order peers
    private static int k = 3; // number of servers/threads
    private int PORT = 2222; // default port
    public int count = 1;

    public long getN() {
        return n;
    }

    public void setN(long n) {
        this.n = n;
    }

    public static int getK() {
        return k;
    }

    public static void setK(int k) {
        ClientRMI.k = k;
    }

    public long getM() {
        return m;
    }

    public void setM(long m) {
        this.m = m;
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    /* Method to calculate number PI using m and n values */
    public double calculatePI(long m) {
        System.out.println("Calculating number PI...");
        System.out.println("m: " + m + " n: " + this.getN());
        System.out.print("PI: ");
        return 4d * ((double) m / (double) getN());
    }

    /* Method that the threads execute to make their own counting of m and add it to the total computation*/
    public void remoteInvocation() {
        long result = 0;
        try {
            String registryURL = "rmi://localhost:" + getPORT() + "/montecarlomethod";
            InterfaceRMI h = (InterfaceRMI) Naming.lookup(registryURL);
            float rest = this.getN() % this.getK();
            if (count == k) {
                result = h.montecarloMethod((long) ((this.getN() / this.getK()) + rest));
            } else {
                result = h.montecarloMethod(this.getN() / this.getK());
            }
            synchronized (this) {
                this.setM(this.getM() + result);
                setPORT(getPORT() + 1); // Increase the port number
                count++;
            }
        } catch (Exception e) {
            System.out.println("Remote thread invocation error: " + e.getMessage());
        }
    }
}