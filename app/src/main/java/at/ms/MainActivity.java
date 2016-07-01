package at.ms;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.graphics.*;

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
		btCo1.setBackgroundColor(Color.BLACK);
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
			btCo1.setTextColor(Color.RED);
			btCo1.setTextColor(Color.parseColor("#8792F2"));
			btCo1.setBackgroundColor(Color.BLACK);
			bCoLampe1=true;}
	}
	
	private boolean bCoLampe2 = false;
	public void switchCoLampe2(View view) {
		
		if(bCoLampe2) {
			btCo2.setTextColor(Color.parseColor("#8792F2"));
			btCo2.setBackgroundColor(Color.BLACK);
			bCoLampe2=false;}
		else {
			btCo2.setTextColor(Color.RED);
			bCoLampe2=true;}
	}
	
	private boolean bEtLampe1 = false;
	public void switchEtLampe1(View view) {
		
		if(bEtLampe1) {
			btEt1.setTextColor(Color.parseColor("#8792F2"));
			btEt1.setBackgroundColor(Color.BLACK);
			bEtLampe1=false;}
		else {
			btEt1.setTextColor(Color.RED);
			bEtLampe1=true;}
	}
	
	private boolean bEtLampe2 = false;
	public void switchEtLampe2(View view) {
		
		if(bEtLampe2) {
			btEt2.setTextColor(Color.parseColor("#8792F2"));
			btEt2.setBackgroundColor(Color.BLACK);
			bEtLampe2=false;}
		else {
			btEt2.setTextColor(Color.RED);
			bEtLampe2=true;}
	}
	
}
