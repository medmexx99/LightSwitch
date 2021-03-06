package at.ms;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import android.os.*;
import android.widget.ScrollView;
import android.widget.TextView;

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

	TextView recTv;
	TextView sendTv;

	public static final String KEY_network_address_couch = "network_address_couch";
	public static final String KEY_network_port_couch = "network_port_couch";
	public static final String KEY_network_address_diningtable = "network_address_diningtable";
	public static final String KEY_network_port_diningtable = "network_port_diningtable";
	public static final String KEY_network_address_z5500 = "network_address_z5500";
	public static final String KEY_network_port_z5500 = "network_port_z5500";
	public static final String KEY_option_show_messages = "option_show_messages";

	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		btCo1 = (Button) findViewById(R.id.btCoLampe1);
		btCo2 = (Button) findViewById(R.id.btCoLampe2);
		btEt1 = (Button) findViewById(R.id.btEtLampe1);
		btEt2 = (Button) findViewById(R.id.btEtLampe2);
		recTv = ((TextView)findViewById(R.id.receiveTextView));
		sendTv = ((TextView)findViewById(R.id.sendTextView));
		init();

	}

	private void init() throws NumberFormatException
	{
		if (getBoolSettingsFor(KEY_option_show_messages))
		{
			sendTv.setMovementMethod(new ScrollingMovementMethod());
			recTv.setMovementMethod(new ScrollingMovementMethod());
			sendTv.setVisibility(View.VISIBLE);
			recTv.setVisibility(View.VISIBLE);
		}
		else
		{
			sendTv.setMovementMethod(new ScrollingMovementMethod());
			recTv.setMovementMethod(new ScrollingMovementMethod());
			sendTv.setVisibility(View.GONE);
			recTv.setVisibility(View.GONE);
		}



		//init button state for couch
		try
		{
			String inetAddr = getSettingsFor(KEY_network_address_couch);
			if(inetAddr == null) inetAddr = "192.168.0.50";
			String portStr = getSettingsFor(KEY_network_port_couch);
			if(portStr == null) portStr = "80";
			InetAddress server = InetAddress.getByName(inetAddr);
			int port = Integer.parseInt(portStr);
			String message = "{\"command\":\"requestStatus\"}";
			String switchingLamp = "Couch";
			TcpClient client = new TcpClient(this);
			client.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, server, port, message, switchingLamp);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}

		//init button state for diningtable
		try
		{
			String inetAddr = getSettingsFor(KEY_network_address_diningtable);
			if(inetAddr == null) inetAddr = "192.168.0.51";
			String portStr = getSettingsFor(KEY_network_port_diningtable);
			if(portStr == null) portStr = "80";
			InetAddress server = InetAddress.getByName(inetAddr);
			int port = Integer.parseInt(portStr);
			String message = "{\"command\":\"requestStatus\"}";
			String switchingLamp = "diningtable";
			TcpClient client = new TcpClient(this);
			client.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, server, port, message, switchingLamp);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		init();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		// TODO: Implement this method
		super.onWindowFocusChanged(hasFocus);
		init();
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
			boolean lightState = ((String)button.getText()).equalsIgnoreCase("ON") ? true : false;
			switch(id) {
				case btCo1Id:
					switchLamp("light1", !lightState, KEY_network_address_couch, KEY_network_port_couch, "Couch");
					break;
				case btCo2Id:
					switchLamp("light2", !lightState, KEY_network_address_couch, KEY_network_port_couch, "Couch");
					break;
				case btEt1Id:
					switchLamp("light1", !lightState, KEY_network_address_diningtable, KEY_network_port_diningtable, "diningtable");
					break;
				case btEt2Id:
					switchLamp("light2", !lightState, KEY_network_address_diningtable, KEY_network_port_diningtable, "diningtable");
					break;
				default:
					break;
			}
		}
	}

	public void irRemote(View view) {

		if(view instanceof Button) {
			Button button = (Button)view;
			int id = button.getId();
			switch(id) {
				case R.id.irPower:
					sendIrCommand(KEY_network_address_z5500, KEY_network_port_z5500, getResources().getString(R.string.irPower));
					break;
				case R.id.irVolumeUp:
					sendIrCommand(KEY_network_address_z5500, KEY_network_port_z5500, getResources().getString(R.string.irVolumeUp));
					break;
				case R.id.irVolumeDown:
					sendIrCommand(KEY_network_address_z5500, KEY_network_port_z5500, getResources().getString(R.string.irVolumeDown));
					break;
				case R.id.irDirect:
					sendIrCommand(KEY_network_address_z5500, KEY_network_port_z5500, getResources().getString(R.string.irDirect));
					break;
				case R.id.irOptical:
					sendIrCommand(KEY_network_address_z5500, KEY_network_port_z5500, getResources().getString(R.string.irOptical));
					break;
				case R.id.irCoax:
					sendIrCommand(KEY_network_address_z5500, KEY_network_port_z5500, getResources().getString(R.string.irCoax));
					break;
				case R.id.irEffect:
					sendIrCommand(KEY_network_address_z5500, KEY_network_port_z5500, getResources().getString(R.string.irEffect));
					break;
				case R.id.irSettings:
					sendIrCommand(KEY_network_address_z5500, KEY_network_port_z5500, getResources().getString(R.string.irSettings));
					break;
				case R.id.irSubUp:
					sendIrCommand(KEY_network_address_z5500, KEY_network_port_z5500, getResources().getString(R.string.irSubUp));
					break;
				case R.id.irSubDown:
					sendIrCommand(KEY_network_address_z5500, KEY_network_port_z5500, getResources().getString(R.string.irSubDown));
					break;
				case R.id.irCenterUp:
					sendIrCommand(KEY_network_address_z5500, KEY_network_port_z5500, getResources().getString(R.string.irCenterUp));
					break;
				case R.id.irCenterDown:
					sendIrCommand(KEY_network_address_z5500, KEY_network_port_z5500, getResources().getString(R.string.irCenterDown));
					break;
				case R.id.irSurroundUp:
					sendIrCommand(KEY_network_address_z5500, KEY_network_port_z5500, getResources().getString(R.string.irSurroundUp));
					break;
				case R.id.irSurroundDown:
					sendIrCommand(KEY_network_address_z5500, KEY_network_port_z5500, getResources().getString(R.string.irSurroundDown));
					break;
				case R.id.irMute:
					sendIrCommand(KEY_network_address_z5500, KEY_network_port_z5500, getResources().getString(R.string.irMuteDown));
					break;
				case R.id.irTest:
					sendIrCommand(KEY_network_address_z5500, KEY_network_port_z5500, getResources().getString(R.string.irTest));
					break;
				default:
					break;
			}
		}
	}

	private void sendIrCommand(String key_ip, String key_port, String command) {
		int lightPort;
		try {
			InetAddress server = InetAddress.getByName(getSettingsFor(key_ip));
			lightPort = Integer.parseInt(getSettingsFor(key_port));
			command = getResources().getString(R.string.irZ5500id) + command;
			String message = "{\"command\":\"irCommand\",\"parameters\":{\"irc\":\""+command+"\"}}";
			TextView sendTextView = (TextView) findViewById(R.id.sendTextView);
			sendTextView.setText("");
			sendTextView.append(" irRemote :" + message + "\r\n");
			TcpClient client = new TcpClient(this);
			client.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, server, lightPort, message, "test");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private void switchLamp(String lightId, boolean lightState, String key_ip, String key_port, String switchingLamp) {
		int lightPort;
		try {
            InetAddress server = InetAddress.getByName(getSettingsFor(key_ip));
            lightPort = Integer.parseInt(getSettingsFor(key_port));
            String message = "{\"command\":\"switchLights\",\"parameters\":{\""+lightId+"\":\""+(lightState ? "ON" : "OFF")+"\"}}";
			TextView sendTextView = (TextView) findViewById(R.id.sendTextView);
			sendTextView.setText("");
			sendTextView.append(switchingLamp + " switchingLamp :" + message + "\r\n");
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
	
	private Boolean getBoolSettingsFor(String key){
		boolean result = false;
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		result = sharedPreferences.getBoolean(key, false);
		return result;
	}

	@Override
	public void onTaskCompleted(String response, String switchingLamp) {

		JSONObject all = null;
		JSONObject status = null;
		String light1 = null;
		String light2 = null;

		TextView receiveTextView = (TextView) findViewById(R.id.receiveTextView);
		receiveTextView.setText("");
		if(response != null && response.length() > 0) {
			if(switchingLamp != null && switchingLamp.length() > 0) {
				receiveTextView.append("\r\nswitchingLamp: " + switchingLamp + " response:\r\n");
			}
			receiveTextView.append(response + "\r\n");
		}

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
