package co.uk.sbsystems.android.jobtrackermobile;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NetworkSettings extends Activity{

//	private TextView txtIPAddress;
	
	private static final String TAG = "NetworkSettings";
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
      
	    setContentView(R.layout.network);
	    
	    DisplayNetworkSettings();
	    
//	    txtIPAddress= (TextView)findViewById(R.id.txtIPAddress); 
	    
        Button btnExit= (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View view) {
        		finish();
        	}
        });
        
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View view) {
        		PopulateNWorkSettings();
				JTApplication.getInstance().GetDatabaseManager().SaveSettings();
        		finish();
        	}
        });          
        
	    
	    
	}
	
	// Populate the network settings from the screen
	private void PopulateNWorkSettings()
	{
		TextView txtIPAddress;
		TextView txtPort;
		String sPort = "8010";
		int Port;
		
		try {
			txtIPAddress =  (TextView) findViewById(R.id.txtIPAddress);
			txtPort =  (TextView) findViewById(R.id.txtPort);
	
			sPort = txtPort.getText().toString();
			try {
				Port = Integer.parseInt(sPort);
			} catch (Exception e)
			{
				Port = 8010;
			}

			JTApplication.getInstance().GetApplicationManager().setHostName(txtIPAddress.getText().toString());
			JTApplication.getInstance().getInstance().GetApplicationManager().setPort(Port);
			JTApplication.getInstance().GetDatabaseManager().SaveSettings();
		} catch (Exception e) {
			Log.i(TAG + "PopulateNWorkSettings",e.getMessage());
		}
	}
	
	
	private void DisplayNetworkSettings() 
	{
		TextView txtIPAddress;
		TextView txtPort;
		
		txtIPAddress =  (TextView) findViewById(R.id.txtIPAddress);
		txtPort =  (TextView) findViewById(R.id.txtPort);
		
		
		txtIPAddress.setText(JTApplication.getInstance().GetApplicationManager().getHostName());
		txtPort.setText(Integer.toString(JTApplication.getInstance().GetApplicationManager().getPort()));
	}
	    
	
}
