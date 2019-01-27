import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.util.ArrayList;

/**
 * Created by silvia on 20/03/18.
 */
public class MainClient {
    public static void main(String args[]) {
        try {
            ClientRMI client = new ClientRMI();
            int RMIPort;
            String hostName;
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);
            System.out.println("Enter the RMIRegistry host name:");
            hostName = br.readLine();
            System.out.println("Enter the RMIregistry port number:");
            String portNum = br.readLine();
            RMIPort = Integer.parseInt(portNum);
            String registryURL = "rmi://" + hostName + ":" + portNum + "/montecarlomethod";
            client.setPORT(RMIPort);

            long time_start, time_end;
            // Part a):
            System.out.println("--Exercise a)--");
            // find the remote object and cast it to an interface object
            InterfaceRMI h = (InterfaceRMI) Naming.lookup(registryURL);
            System.out.println("Lookup completed ");
            // invoke the remote method
            time_start = System.currentTimeMillis();
            client.setM(client.getM() + h.montecarloMethod(client.getN()));
            time_end = System.currentTimeMillis();
            System.out.println(client.calculatePI(client.getM()));
            System.out.println("Time: " + (time_end-time_start) +" ms.");
            client.setM(0); // reset m value

            // Part b: using k servers
            System.out.println("\n--Exercise b)--");
            float rest = client.getN()%client.getK();
            time_start = System.currentTimeMillis();
            for (int i = 0; i < client.getK(); i++) {
                InterfaceRMI remoteObject = (InterfaceRMI) Naming.lookup(registryURL);
                System.out.println("Lookup " + (i + 1) + " completed ");
                if(i==client.getK()-1) { // if is the last server I include the rest
                    client.setM(client.getM() + h.montecarloMethod((long) ((client.getN() / client.getK())+rest)));
                }else{
                    client.setM(client.getM() + h.montecarloMethod(client.getN() / client.getK()));
                }
            }
            time_end = System.currentTimeMillis();
            System.out.println(client.calculatePI(client.getM()));
            System.out.println("Time: " + (time_end-time_start) +" ms.");
            client.setM(0); // reset m value

            // Part c: using threads
            System.out.println("\n--Exercise c)--");
            ArrayList<ClientCommunication> threads = new ArrayList();
            time_start = System.currentTimeMillis();
            for (int i = 0; i < client.getK(); i++) {
                ClientCommunication thread = new ClientCommunication(client);
                threads.add(thread);
                thread.start();
            }
            for (ClientCommunication thread : threads) {
                thread.join();
            }
            time_end = System.currentTimeMillis();
            System.out.println(client.calculatePI(client.getM()));
            System.out.println("Time: " + (time_end-time_start) +" ms.");
        } catch (Exception e) {
            System.out.println("Exception in MainClient: " + e);
        }
    }
}