package at.ms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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

    public String send(InetAddress server, int port, String message) {
        Socket client;
        String response = "";
        try{

            client = new Socket(server, port);
            PrintWriter out = new PrintWriter(client.getOutputStream(),true);
            out.write(message);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            boolean receiving = true;
            String line = "";
            while((line = in.readLine()) != null) {
                response += line + "\r\n";
            }

        } catch(UnknownHostException e) {
            System.out.println("Unknown host: www.example.com");

        } catch(IOException e) {
            System.out.println("No I/O");
        }

        return response;
    }
}
