package co.uk.sbsystems.android.jobtrackermobile;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.os.AsyncTask;
import android.util.Log;
import co.uk.sbsystems.android.jobtrackermobile.JTApplication;

public class C_ConnectSocket  extends AsyncTask<String, String, String> {
	
	
	@Override
	protected String doInBackground(String... params) {
		android.os.Debug.waitForDebugger();
		
		String TAG = "C_ConnectSocket";
		String serverIPAddress = "";		//"81.136.241.185";
		Integer serverPort = 8010;
		
		try {
			serverPort = JTApplication.getInstance().GetApplicationManager().getPort();
			serverIPAddress = JTApplication.getInstance().GetApplicationManager().getHostName();
		} catch (Exception e) {
			Log.e(TAG, "DoInBackground",e);
		}
		
		try {
			JTApplication.getInstance().getTCPManager().nsocket = new Socket();
			JTApplication.getInstance().getTCPManager().nsocket.connect(new InetSocketAddress(serverIPAddress.trim(),serverPort), 5000);
		} catch (Exception e) {
			Log.e(TAG, "DoInBackground",e);
		}		
		return null;
		
	}


	@Override
	protected void onPostExecute(String result) {
		if (JTApplication.getInstance().getTCPManager().nsocket.isConnected())
			JTApplication.getInstance().GetApplicationManager().setConnectedStatus(true);
	}


}
