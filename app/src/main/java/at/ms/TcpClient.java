package at.ms;

import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Mex on 04.07.2016.
 */
public class TcpClient {

    private static TcpClient instance = null;

    private Socket socket;
    private InetAddress couchAddress;
    private InetAddress diningTableAddress;
    private int portCouch;
    private int portDiningTable;

    public InetAddress getDiningTableAddress() {
        return diningTableAddress;
    }

    public void setDiningTableAddress(InetAddress diningTableAddress) {
        this.diningTableAddress = diningTableAddress;
    }

    public InetAddress getCouchAddress() {
        return couchAddress;
    }

    public void setCouchAddress(InetAddress couchAddress) {
        this.couchAddress = couchAddress;
    }

    public int getPortCouch() {
        return portCouch;
    }

    public void setPortCouch(int portCouch) {
        this.portCouch = portCouch;
    }

    public int getPortDiningTable() {
        return portDiningTable;
    }

    public void setPortDiningTable(int portDiningTable) {
        this.portDiningTable = portDiningTable;
    }

    private TcpClient (){
    }

    public static TcpClient getInstance() {
        if(instance == null)
            instance = new TcpClient();

        //couchAddress;

        return instance;
    }

    public void getLightStatus() {}

    public void switchLight(String identifier, boolean lightOn) {}
}
