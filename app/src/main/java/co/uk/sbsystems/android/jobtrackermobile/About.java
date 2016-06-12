package co.uk.sbsystems.android.jobtrackermobile;


import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

// This is the activity for the About Screen

public class About extends Activity {
	
	SignalStrengthListener signalStrengthListener;
	
    TextView labVersionNo;
    Button butClose;
	TextView labSignalStrength;
    
		
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        // Set up content stuff.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.about);
        try {
	        butClose = (Button)findViewById(R.id.btnClose);
	        labVersionNo = (TextView)findViewById(R.id.labVersionNo);
	        labSignalStrength = (TextView)findViewById(R.id.txtSignalStrength);
	    } catch (Exception e)
	    {
			Log.i("onCreate Exception", e.getMessage());
	    }        
        
	    // Start the listener
	    signalStrengthListener = new SignalStrengthListener();
	    
        // Add the Close button listener
	    butClose.setOnClickListener(new View.OnClickListener() {
	    	@Override
			public void onClick(View view) {
	    		finish();
	    	}
	    });
	    
	    
	     ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).listen(signalStrengthListener,SignalStrengthListener.LISTEN_SIGNAL_STRENGTHS);	    

        // Display the version number
        labVersionNo.setText(GetVersionName());
	}
        	
	    
	
	public String GetVersionName() {
	    String versionName = "";
	    PackageInfo packageInfo;
	    try {
	        packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
	        versionName = packageInfo.versionName;
	    } catch (NameNotFoundException e) {
	        e.printStackTrace();
	    }
	    return versionName;
	}	
	
    private class SignalStrengthListener extends PhoneStateListener
    {
     @Override
     public void onSignalStrengthsChanged(android.telephony.SignalStrength signalStrength) {

        // get the signal strength (a value between 0 and 31)
        int strengthAmplitude = signalStrength.getGsmSignalStrength();

       //do something with it (in this case we update a text view)
        labSignalStrength.setText(String.valueOf(strengthAmplitude));
       super.onSignalStrengthsChanged(signalStrength);
     }
    
}
	}