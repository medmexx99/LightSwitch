package at.ms;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import android.os.*;

public class MainActivity extends Activity implements AsyncTaskCallbacks {
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

	public static final String KEY_network_address_couch = "network_address_couch";
	public static final String KEY_network_port_couch = "network_port_couch";
	public static final String KEY_network_address_diningtable = "network_address_diningtable";
	public static final String KEY_network_port_diningtable = "network_port_diningtable";

	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		btCo1 = (Button) findViewById(R.id.btCoLampe1);
		btCo2 = (Button) findViewById(R.id.btCoLampe2);
		btEt1 = (Button) findViewById(R.id.btEtLampe1);
		btEt2 = (Button) findViewById(R.id.btEtLampe2);

		//init button state for couch
		try {
			InetAddress server = InetAddress.getByName(getSettingsFor(KEY_network_address_couch));
			int port = Integer.parseInt(getSettingsFor(KEY_network_port_couch));
			String message = "{\"getstatus\"}";
			String switchingLamp = "Couch";
			TcpClient client = new TcpClient(this);
			client.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, server,port,message,switchingLamp);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		//init button state for diningtable
		try {
			InetAddress server = InetAddress.getByName(getSettingsFor(KEY_network_address_diningtable));
			int port = Integer.parseInt(getSettingsFor(KEY_network_port_diningtable));
			String message = "{\"getstatus\"}";
			String switchingLamp = "diningtable";
			TcpClient client = new TcpClient(this);
			client.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, server,port,message,switchingLamp);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.mainMenuOptions:
                return true;
            case R.id.mainMenuConfiguration:
                Intent i = new Intent(this, MyPreferenceActivity.class);
                startActivity(i);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

 	public void switchLampe(View view) {

		if(view instanceof Button) {
			Button button = (Button)view;
			int id = button.getId();
			switch(id) {
				case btCo1Id:
					switchLamp("light1", bCoLampe1, KEY_network_address_couch, KEY_network_port_couch, "Couch");
					break;
				case btCo2Id:
					switchLamp("light2", bCoLampe1, KEY_network_address_couch, KEY_network_port_couch, "Couch");
					break;
				case btEt1Id:
					switchLamp("light1", bCoLampe1, KEY_network_address_diningtable, KEY_network_port_diningtable, "diningtable");
					break;
				case btEt2Id:
					switchLamp("light2", bCoLampe1, KEY_network_address_diningtable, KEY_network_port_diningtable, "diningtable");
					break;
				default:
					break;
			}
/*
			if (lightState) {
				//button.setTextColor(Color.parseColor("#8792F2"));
				//btCo1.setBackgroundColor(Color.BLACK);
				GradientDrawable shape = (GradientDrawable) button.getBackground();
				shape.setColor(Color.WHITE);
				lightState = false;
			} else {
				//button.setTextColor(Color.BLACK);
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
*/
		}
	}

	private void switchLamp(String lightId, boolean lightState, String key_ip, String key_port, String switchingLamp) {
		int lightPort;
		try {
            InetAddress server = InetAddress.getByName(getSettingsFor(key_ip));
            lightPort = Integer.parseInt(getSettingsFor(key_port));
            String message = "{\"switchLights\":{\""+lightId+"\":\""+(!lightState ? "ON" : "OFF")+"\"}}";

            TcpClient client = new TcpClient(this);
            client.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, server,lightPort,message,switchingLamp);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
	}

	private String getSettingsFor(String key) {
		String result = "";
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		result = sharedPreferences.getString(key, null);
		return result;
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

	@Override
	public void onTaskCompleted(String response, String switchingLamp) {
		JSONObject all = null;
		JSONObject status = null;
		String light1 = null;
		String light2 = null;

		if(response != null && response.length() > 0 && switchingLamp != null && switchingLamp.length() > 0) {
			try {
				all = new JSONObject(response);
				status = all.getJSONObject("status");
				light1 = status.getString("light1");
				light2 = status.getString("light2");

				boolean l1state = light1.equalsIgnoreCase("on") ? true : false;
				boolean l2state = light2.equalsIgnoreCase("on") ? true : false;

				if (switchingLamp.equalsIgnoreCase("couch")) {
					setButtonState(l1state, btCo1);
					setButtonState(l2state, btCo2);
				} else if (switchingLamp.equalsIgnoreCase("diningtable")) {
					setButtonState(l1state, btEt1);
					setButtonState(l2state, btEt2);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(switchingLamp != null && switchingLamp.length() > 0) {
			if (switchingLamp.equalsIgnoreCase("couch")) {
				setButtonStateUndefined(btCo1);
				setButtonStateUndefined(btCo2);
			} else if (switchingLamp.equalsIgnoreCase("diningtable")) {
				setButtonStateUndefined(btEt1);
				setButtonStateUndefined(btEt2);
			}
		}
	}

	private void setButtonState(boolean l1state, Button button) {
		if (l1state) {
            GradientDrawable shape = (GradientDrawable) button.getBackground();
            shape.setColor(Color.parseColor("#FFEF4F"));
			button.setText("ON");
            bCoLampe1 = true;
        } else {
            GradientDrawable shape = (GradientDrawable) button.getBackground();
            shape.setColor(Color.WHITE);
			button.setText("OFF");
            bCoLampe1 = false;
        }
	}

	private void setButtonStateUndefined(Button button) {
			GradientDrawable shape = (GradientDrawable) button.getBackground();
			shape.setColor(Color.parseColor("#FF0088"));
			button.setText("??");
			bCoLampe1 = true;
	}


	@Override
	public void onProgressUpdate(int status) {

	}
}
