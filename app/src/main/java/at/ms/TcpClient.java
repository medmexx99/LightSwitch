package at.ms;

import android.os.*;
import java.io.*;
import java.net.*;

/**
 * Created by Mex on 04.07.2016.
 */
public class TcpClient extends AsyncTask<Object,Void,String>
{

	@Override
	protected String doInBackground(Object[] p1)
	{
		if(p1.length >= 3 &&
			p1[0] instanceof InetAddress &&
		   p1[1] instanceof Integer &&
		   p1[2] instanceof String) {
			   
		   }
		   
		return send((InetAddress)p1[0],
					(Integer)p1[1],
					(String)p1[2]);
	}


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
        } catch (Exception e) {
			System.out.println("Unknown exception:");
			e.printStackTrace();
		}

        return response;
    }
}
