package at.ms;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.net.*;

public class MainActivity extends Activity 
{
	Button btCo1;
	Button btCo2;
	Button btEt1;
	Button btEt2;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		btCo1 = (Button) findViewById(R.id.btCoLampe1);
		//btCo1.setBackgroundColor(Color.BLACK);
		btCo1.setTextColor(Color.parseColor("#8792F2"));
		btCo2 = (Button) findViewById(R.id.btCoLampe2);
		btCo2.setBackgroundColor(Color.BLACK);
		btCo2.setTextColor(Color.parseColor("#8792F2"));
		btEt1 = (Button) findViewById(R.id.btEtLampe1);
		btEt1.setBackgroundColor(Color.BLACK);
		btEt1.setTextColor(Color.parseColor("#8792F2"));
		btEt2 = (Button) findViewById(R.id.btEtLampe2);
    	btEt2.setBackgroundColor(Color.BLACK);
		btEt2.setTextColor(Color.parseColor("#8792F2"));
	}

	private boolean bCoLampe1 = false;
	public void switchCoLampe1(View view) {
		
		if(bCoLampe1) {
			btCo1.setTextColor(Color.parseColor("#8792F2"));
			btCo1.setBackgroundColor(Color.BLACK);
			bCoLampe1=false;}
		else {
			btCo1.setTextColor(Color.BLACK);
			btCo1.setBackgroundColor(Color.parseColor("#FFEF4F"));
			bCoLampe1=true;}
	}
	
	private boolean bCoLampe2 = false;
	public void switchCoLampe2(View view) {
		
		if(bCoLampe2) {
			btCo2.setTextColor(Color.parseColor("#8792F2"));
			btCo2.setBackgroundColor(Color.BLACK);
			bCoLampe2=false;}
		else {
			btCo2.setTextColor(Color.BLACK);
			btCo2.setBackgroundColor(Color.parseColor("#FFEF4F"));
			bCoLampe2=true;}
	}
	
	private boolean bEtLampe1 = false;
	public void switchEtLampe1(View view) {
		
		if(bEtLampe1) {
			btEt1.setTextColor(Color.parseColor("#8792F2"));
			btEt1.setBackgroundColor(Color.BLACK);
			bEtLampe1=false;}
		else {
			btEt1.setTextColor(Color.BLACK);
			btEt1.setBackgroundColor(Color.parseColor("#FFEF4F"));
			bEtLampe1=true;}
	}
	
	private boolean bEtLampe2 = false;
	public void switchEtLampe2(View view) {
		
		if(bEtLampe2) {
			btEt2.setTextColor(Color.parseColor("#8792F2"));
			btEt2.setBackgroundColor(Color.BLACK);
			bEtLampe2=false;}
		else {
			btEt2.setTextColor(Color.BLACK);
			btEt2.setBackgroundColor(Color.parseColor("#FFEF4F"));
			bEtLampe2=true;}
	}
	
	private void getStatus() {
		Socket client;
		

		try{
			InetAddress clientAddress = InetAddress.getByName("192.168.0.150");
			client = new Socket(clientAddress, 80);
			PrintWriter out = new PrintWriter(client.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

		} catch(UnknownHostException e) {
			System.out.println("Unknown host: www.example.com");

		} catch(IOException e) {
			System.out.println("No I/O");
		}
	}
	
}
