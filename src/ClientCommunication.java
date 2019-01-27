/**
 * Created by silvia on 20/03/18.
 */
public class ClientCommunication extends Thread{
    private ClientRMI clientRMI;

    public ClientCommunication(ClientRMI clientRMI){
        this.clientRMI = clientRMI;
    }

    public ClientRMI getClientRMI() {
        return clientRMI;
    }

    public void setClientRMI(ClientRMI client) {
        this.clientRMI = client;
    }

    @Override
    public void run(){
        this.getClientRMI().remoteInvocation();
    }
}
