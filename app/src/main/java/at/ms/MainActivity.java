package at.ms;

import android.app.*;
import android.graphics.*;
import android.graphics.drawable.*;
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

	private boolean bCoLampe1 = false;
	private boolean bCoLampe2 = false;
	private boolean bEtLampe1 = false;
	private boolean bEtLampe2 = false;

	final int btCo1Id = R.id.btCoLampe1;
	final int btCo2Id = R.id.btCoLampe2;
	final int btEt1Id = R.id.btEtLampe1;
	final int btEt2Id = R.id.btEtLampe2;

	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//		btCo1 = (Button) findViewById(R.id.btCoLampe1);
//		//ShapeDrawable shape = (ShapeDrawable) btCo1.getBackground();
//		//shape.getPaint().setColor(Color.BLACK);
//		//btCo1.setBackgroundColor(Color.BLACK);
//		btCo1.setTextColor(Color.parseColor("#8792F2"));
//		btCo2 = (Button) findViewById(R.id.btCoLampe2);
//		btCo2.setBackgroundColor(Color.BLACK);
//		btCo2.setTextColor(Color.parseColor("#8792F2"));
//		btEt1 = (Button) findViewById(R.id.btEtLampe1);
//		btEt1.setBackgroundColor(Color.BLACK);
//		btEt1.setTextColor(Color.parseColor("#8792F2"));
//		btEt2 = (Button) findViewById(R.id.btEtLampe2);
//    	btEt2.setBackgroundColor(Color.BLACK);
//		btEt2.setTextColor(Color.parseColor("#8792F2"));
	}

	public void switchLampe(View view) {

		if(view instanceof Button) {
			Button button = (Button)view;
			int id = button.getId();
			boolean lightState = false;
			switch(id) {
				case btCo1Id:
					lightState = bCoLampe1;
					break;
				case btCo2Id:
					lightState = bCoLampe2;
					break;
				case btEt1Id:
					lightState = bEtLampe1;
					break;
				case btEt2Id:
					lightState = bEtLampe2;
					break;
				default:
					break;
			}
			if (lightState) {
				button.setTextColor(Color.parseColor("#8792F2"));
				//btCo1.setBackgroundColor(Color.BLACK);
				GradientDrawable shape = (GradientDrawable) button.getBackground();
				shape.setColor(Color.BLACK);
				lightState = false;
			} else {
				button.setTextColor(Color.BLACK);
				//btCo1.setBackgroundColor(Color.parseColor("#FFEF4F"));
				GradientDrawable shape = (GradientDrawable) button.getBackground();
				shape.setColor(Color.parseColor("#FFEF4F"));
				lightState = true;
			}
			switch(id) {
				case btCo1Id:
					bCoLampe1 = lightState;
					break;
				case btCo2Id:
					bCoLampe2 = lightState;
					break;
				case btEt1Id:
					bEtLampe1 = lightState;
					break;
				case btEt2Id:
					bEtLampe2 = lightState;
					break;
				default:
					break;
			}
		}
	}
	
/*
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
*/

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
