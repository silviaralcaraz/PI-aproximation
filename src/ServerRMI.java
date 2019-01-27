/**
 * Created by silvia on 5/03/18.
 */

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;

public class ServerRMI implements Serializable {
    public static int parallelConnections = 3; // number of parallel connections accepted by the server

    public static void main(String args[]) {
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        String portNum, registryURL;
        try {
            System.out.println("Enter the RMIregistry port number:");
            portNum = (br.readLine()).trim();
            Integer RMIPortNum = Integer.parseInt(portNum);
            for (int i = 0; i < parallelConnections; i++) {
                startRegistry(RMIPortNum);
                ImplementationRMI exportedObj = new ImplementationRMI();
                registryURL = "rmi://localhost:" + RMIPortNum.toString() + "/montecarlomethod";
                Naming.rebind(registryURL, (Remote) exportedObj);
                System.out.println("Server registered. Registry currently contains:");
                // list names currently in the registry
                listRegistry(registryURL);
                System.out.println("Server ready.");
                RMIPortNum++; // Increase the port number
            }
        } catch (Exception re) {
            System.out.println("Exception in ServerRMI: " + re);
        }
    }

    // This method starts a RMI registry on the local host, if it
    // does not already exists at the specified port number.
    private static void startRegistry(int RMIPortNum) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list();  // This call will throw an exception
            // if the registry does not already exist
        } catch (RemoteException e) {
            // No valid registry at that port.
            System.out.println("RMI registry cannot be located at port " + RMIPortNum);
            Registry registry = LocateRegistry.createRegistry(RMIPortNum);
            System.out.println("RMI registry created at port " + RMIPortNum);
        }
    }

    // This method lists the names registered with a Registry object
    private static void listRegistry(String registryURL) throws RemoteException, MalformedURLException {
        System.out.println("Registry " + registryURL + " contains: ");
        String[] names = Naming.list(registryURL);
        for (int i = 0; i < names.length; i++)
            System.out.println(names[i]);
    }
}