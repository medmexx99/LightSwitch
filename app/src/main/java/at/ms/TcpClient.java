package at.ms;

import android.os.*;
import java.io.*;
import java.net.*;

/**
 * Created by Mex on 04.07.2016.
 */
public class TcpClient extends AsyncTask<Object,Void,String>
{

    private String response = "";
    private String switchingLamp = "";
    private AsyncTaskCallbacks callbacks;

	@Override
	protected String doInBackground(Object[] p1)
	{
		if(p1.length >= 4 &&
		   p1[0] instanceof InetAddress &&
		   p1[1] instanceof Integer &&
		   p1[2] instanceof String &&
           p1[3] instanceof  String) {

           switchingLamp = (String)p1[3];
	       response =  send((InetAddress)p1[0],
					(Integer)p1[1],
					(String)p1[2]);

        }

        return response;
	}

    @Override
    protected void onPostExecute(String response){
        // your stuff
        callbacks.onTaskCompleted(response,switchingLamp);
    }

    private static TcpClient instance = null;

    public TcpClient (AsyncTaskCallbacks callbacks){
        this.callbacks = callbacks;
    }

    public String send(InetAddress server, int port, String message) {
        Socket client = null;
        String response = "";
        try{

            client = new Socket();
            client.connect(new InetSocketAddress(server,port),5000);
            PrintWriter out = new PrintWriter(client.getOutputStream(),true);
            out.println(message);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            boolean receiving = true;
            String line = "";
            response = in.readLine();

        } catch(UnknownHostException e) {
            System.out.println("Unknown host: www.example.com");

        } catch(IOException e) {
            System.out.println("No I/O");
        } catch (Exception e) {
			System.out.println("Unknown exception:");
			e.printStackTrace();
		} finally {
            if (client != null && client.isConnected())
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }


        return response;
    }
}
