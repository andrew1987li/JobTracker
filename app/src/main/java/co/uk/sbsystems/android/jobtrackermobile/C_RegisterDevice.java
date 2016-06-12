package co.uk.sbsystems.android.jobtrackermobile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class C_RegisterDevice extends AsyncTask<String, String, String> {

	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		String HostName = "";
		   String EngineerName = "";
		   String Encoded = "";
			       	
		        if (JTApplication.getInstance().GetApplicationManager().getAlreadyRegisteredWithServer()== false) {
			        try {
				        HostName = JTApplication.getInstance().GetApplicationManager().getHostName();
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
				        EngineerName = JTApplication.getInstance().GetApplicationManager().getDeviceAssignedEngineerName();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
		 			
					try {
						Encoded = URLEncoder.encode(EngineerName);
					} catch (Exception e) {
						e.printStackTrace();
					}
						
					String url = "http://www.casenews.co.uk/jtregistry/parser.asp?CN=" + HostName + "&UN=" + Encoded + "&SK=MOBILEUSER&RECID=0&VER=1.0.51&COMPUTER=" + JTApplication.getInstance().GetApplicationManager().getMACAddress()
					+ "&lon=" + JTApplication.getInstance().getAppManager().getLongitude() +"&lat="  + JTApplication.getInstance().getAppManager().getLatitude();



			        url = JTApplication.getInstance().DropLastChar(url);
			        
			        //JTApplication
			        
		//	       	String url = "http://www.casenews.co.uk/jtregistry/parser.asp?CMD=GETSTATUS";
			    	try {
						URI tURI = new URI(url);
		
						HttpClient httpClient = new DefaultHttpClient();
						HttpGet pageGet = new HttpGet(tURI);
						// HttpPost pagePost = new HttpPost(tURI);
						try {
							HttpResponse response = httpClient.execute(pageGet);
							
							HttpEntity entity = response.getEntity();
							String responseBody = EntityUtils.toString(entity);


                            JTApplication.getInstance().GetApplicationManager().setAlreadyRegisteredWithServer(true);
							 
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}		
			    	
			    	
			    	} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		return null;
	}

	
}



