package co.uk.sbsystems.android.jobtrackermobile;

import android.provider.Settings.Secure;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Spinner;

// Create application global instance for networking.
// This is the entry point for the application.

public class JTApplication extends Application {

	static JTApplication instance;

	public static JTApplication getInstance() {

		return instance;
	}

	private final String TAG = "JTApplication";

	private SearchJobs objSearchJobs;    // Means of getting access to the activity that calls Search Jobs.

	private C_AppManager objAppManager; // Application helpers.
	private NetworkTask objTCPManager; // This is where all the TCP/IP stuff is
	// done.
	private DatabaseHelper objDBManager; // Database Helpers.
	private Parser objParser;
	private C_Settings AppSettings;
	private Context context;

	public SearchJobs getSearchJobsInstance() {
        return objSearchJobs;
    }

    public void setSearchJobsInstance(SearchJobs myInstance)
    {
        objSearchJobs = myInstance;
    }


	public NetworkTask getNetworkTask() {
		return objTCPManager;
	}

	public C_AppManager getAppManager() {
		return objAppManager;
	}

	public DatabaseHelper getDBManager() {
		return objDBManager;
	}

	public Parser getParser() {
		return objParser;
	}

	public C_Settings getAppSettings() {
		return AppSettings;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;

		context = getApplicationContext();
		objAppManager = new C_AppManager(this);
		objDBManager = new DatabaseHelper(this);
		objParser = new Parser();
		AppSettings = new C_Settings();

		StartTheApplication();

		// objTCPManager.execute();
	}

	public void StartTheApplication() {
		MainActivity.appendLog("(About to execute LoadSettings");
		objDBManager.LoadSettings(); // Load network settings.

		MainActivity.appendLog("(About to execute LoadLabels");
		objDBManager.LoadLabels(); // The customisable labels.

		MainActivity.appendLog("About to register logon with Web Server");

		// Need to create a socket first.

		/*
		 * ConnectSocket();
		 * 
		 * try { Thread.sleep(1750); // Socket Connection fails without this
		 * delay?? } catch (Exception e) {
		 * 
		 * }
		 */

		// This does the actual socket connection
        objTCPManager = new NetworkTask(); // Fires off the TCP/IP on communications thread.
        objTCPManager.execute();

		String android_id = "";

		// Give TCP a change to connect.
		try {
			Thread.sleep(1750);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (objTCPManager.AreWeOnline() == true) {
			android_id = GetMACAddress();

			// THis is the 1st communications.  This needs wrapped.
			objTCPManager.SendMyId(android_id);
			MainActivity.appendLog("About to register logon with Web Server");

			new C_RegisterDevice().execute("", "", "");
			// RegisterDeviceWithServer();
		}
	}

	public String GetMACAddress() {

		String android_id = "";
		try {
			if (context != null) {
				android_id = Secure.getString(context.getContentResolver(),
						Secure.ANDROID_ID);
			}
		} catch (Exception e) {
			Log.i("JTApplication.GetMACAddress", e.getMessage() + "");
		}

		return android_id;

		/*
		 * StringBuffer fileData = new StringBuffer(1000); try { BufferedReader
		 * reader = new BufferedReader(new
		 * FileReader("/sys/class/net/wlan0/address")); char[] buf = new
		 * char[1024]; int numRead = 0; while ((numRead = reader.read(buf)) !=
		 * -1) { String readData = String.valueOf(buf, 0, numRead);
		 * fileData.append(readData); } reader.close(); return
		 * fileData.toString(); } catch (FileNotFoundException e) {
		 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
		 * return "";
		 */}

	/*
	 * private String GetVersionName() {
	 * 
	 * ApplicationInfo appIngo =
	 * context.getPackageManager().getApplicationInfo("your.app.package.name",
	 * GET_META_DATA);
	 * 
	 * String versionName = ""; PackageInfo packageInfo; try { packageInfo =
	 * getPackageManager().getPackageInfo(getPackageName(), 0); versionName =
	 * packageInfo.versionName; } catch (NameNotFoundException e) {
	 * e.printStackTrace(); } return versionName; }
	 */

	/*
	 * public void RegisterDeviceWithServer() { // going to reinstate this once
	 * I've had enough time to fine tune it. // got a probelm passing the right
	 * context as a parameter at the mo. //WebServerTask doBackStuff = new
	 * WebServerTask(); //doBackStuff.execute();
	 * 
	 * String HostName = ""; String EngineerName = ""; String Encoded = "";
	 * 
	 * try { if (objAppManager.getAlreadyRegisteredWithServer() == false) { try
	 * { HostName = JTApplication.GetApplicationManager().getHostName(); } catch
	 * (Exception e) { e.printStackTrace(); } try { EngineerName =
	 * JTApplication.GetApplicationManager().getDeviceAssignedEngineerName(); }
	 * catch (Exception e) { e.printStackTrace(); }
	 * 
	 * 
	 * try { Encoded = URLEncoder.encode(EngineerName); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * String url = "http://www.casenews.co.uk/jtregistry/parser.asp?CN=" +
	 * HostName + "&UN=" + Encoded +
	 * "&SK=MOBILEUSER&RECID=0&VER=1.0.51&COMPUTER=" +
	 * objAppManager.getMACAddress() + "&lon=" + getLongitude() +"&lat=" +
	 * getLatitude(); url = JTApplication.DropLastChar(url); // String url =
	 * "http://www.casenews.co.uk/jtregistry/parser.asp?CMD=GETSTATUS"; try {
	 * URI tURI = new URI(url);
	 * 
	 * HttpClient httpClient = new DefaultHttpClient(); HttpGet pageGet = new
	 * HttpGet(tURI); // HttpPost pagePost = new HttpPost(tURI); try {
	 * HttpResponse response = httpClient.execute(pageGet);
	 * 
	 * HttpEntity entity = response.getEntity(); String responseBody =
	 * EntityUtils.toString(entity);
	 * 
	 * objAppManager.setAlreadyRegisteredWithServer(true);
	 * 
	 * } catch (ClientProtocolException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); }
	 * 
	 * 
	 * } catch (URISyntaxException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } } catch (Exception e) {
	 * Log.i("JTApplication.RegisterDeviceWithServer", e.getMessage()); }
	 * 
	 * 
	 * }
	 */
	public void CloseWait(ProgressDialog myDialog) {
		myDialog.dismiss();
	}

	public Context getAppContext() {
		return context;
	}

	public C_AppManager GetApplicationManager() {
		return objAppManager;
	}

	public DatabaseHelper GetDatabaseManager() {
		return objDBManager;
	}

	public NetworkTask TCPManager() {
		return objTCPManager;
	}

	public Parser GetParser() {
		return objParser;
	}

	// Get access to settings class.
	public C_Settings getSettings() {
		return AppSettings;
	}

	public String GetNowString() {
		String myDate = "";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter;
		Date date = null;
		formatter = new SimpleDateFormat("dd/MM/yy H:m:s");
		date = cal.getTime();

		try {
			myDate = formatter.parse(cal.getTime().toString()).toString();
		} catch (Exception e) {
			try {
				myDate = DateFormat.getDateTimeInstance().format(new Date());
			} catch (Exception ex) {

				Log.i(TAG, "Exception: " + ex.getMessage());
			}
		}
		return myDate;
	}

	public void StartNetworkAgain() {
		try {
			if (objTCPManager == null) {
				objTCPManager = new NetworkTask();
			}
			objTCPManager.execute();

			if (objTCPManager.nsocket != null) {
				if (objTCPManager.nsocket.isConnected() == true) {
					try {
						objTCPManager.nsocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			objTCPManager.doInBackground();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public NetworkTask getTCPManager() {
		try {
			// if (objTCPManager.equals(null)) {
			// objTCPManager = new NetworkTask();
			// objTCPManager.execute();
			// }
		} catch (Exception e) {
			// objTCPManager = new NetworkTask();
			// objTCPManager.execute();
		}

		return objTCPManager;
	}

	public void setTCPManager(NetworkTask objTCP) {
		objTCPManager = objTCP;
	}

	public void RunTCPManagerAgain() {
		try {
			MainActivity
					.appendLog("((E) Creating new instance of objTCPManager");
			objTCPManager = new NetworkTask();
			MainActivity
					.appendLog("((F) Finished creating new instance of objTCPManager");

			StartNetworkAgain();
		} catch (Exception e) {
			MainActivity
					.appendLog("((D) RunTCPManagerAgain()" + e.getMessage());
		}
	}

	public void PrepareForDestroy() {
		try {
			if (objTCPManager != null) {
				objTCPManager.CloseSocket();
				objTCPManager = null;
			}
		} catch (Exception e) {
			Log.i(TAG + "PrepareForDestroy", e.getMessage());
		}
	}

	/*
	 * public void ConnectSocket() {
	 * 
	 * String serverIPAddress = ""; // "81.136.241.185"; Integer serverPort =
	 * 8010;
	 * 
	 * // Get the socket details from the database. try { serverPort =
	 * JTApplication.GetApplicationManager().getPort(); serverIPAddress =
	 * JTApplication.GetApplicationManager() .getHostName(); } catch (Exception
	 * e) { Log.e(TAG, "ConnectSocket", e); }
	 * 
	 * try { // JTApplication.nsocket = new Socket(serverIPAddress,serverPort);
	 * 
	 * JTApplication.nsocket = new Socket(); JTApplication.nsocket.connect(new
	 * InetSocketAddress( serverIPAddress, serverPort), 5000);
	 * 
	 * Log.v(TAG, "Socket connected " + serverIPAddress + ":" + serverPort); }
	 * catch (Exception e) { Log.e(TAG, "ConnectSocket", e); } }
	 */
	// When mobile starts it checks if it has a unique ID.
	// If not it asks for one from the server.
	// If it does have one it signs on to the server with it and also posts a
	// request to run to S B Systems server.
	// This is not ready just yet. Still under development
	// TODO
	public void WriteUniqueID(String id) {
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File(sdCard.getAbsolutePath());
		dir.mkdirs();
		File file = new File(dir, "uniqueid");

		try {
			FileOutputStream f1 = new FileOutputStream(file, false); // True =
																		// Append
																		// to
																		// file,
																		// false
																		// =
																		// Overwrite
			PrintStream p = new PrintStream(f1);
			p.print(id);
			p.close();
			f1.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

	}

	public String DropLastChar(String str) {
		try {
			str = str.substring(0, str.length() - 1);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return str;
	}

	public void setSpinnerTo(Spinner sp, String engName) {
		for (int i = 0; i < sp.getCount(); i++) {
			if (sp.getItemAtPosition(i).toString().equalsIgnoreCase(engName)) {
				sp.setSelection(i);
				break;
			}
		}
	}

	// Log the device onto the web server.
	class WebServerTask extends AsyncTask<Void, Void, Void> {

		public WebServerTask() {
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onPostExecute(Void result) {

		}

		@Override
		protected Void doInBackground(Void... params) {
			String HostName = "";
			String EngineerName = "";
			String Encoded = "";

			try {
				if (objAppManager.getAlreadyRegisteredWithServer() == false) {
					try {
						HostName = objAppManager.getHostName();
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						EngineerName = objAppManager
								.getDeviceAssignedEngineerName();
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						Encoded = URLEncoder.encode(EngineerName);
					} catch (Exception e) {
						e.printStackTrace();
					}

					String url = "http://www.casenews.co.uk/jtregistry/parser.asp?CN="
							+ HostName
							+ "&UN="
							+ Encoded
							+ "&SK=MOBILEUSER&RECID=0&VER=1.0.51&COMPUTER="
							+ objAppManager.getMACAddress()
							+ "&lon="
							+ objAppManager.getLongitude()
							+ "&lat="
							+ objAppManager.getLatitude();
					url = DropLastChar(url);
					// String url =
					// "http://www.casenews.co.uk/jtregistry/parser.asp?CMD=GETSTATUS";
					try {
						URI tURI = new URI(url);

						HttpClient httpClient = new DefaultHttpClient();
						HttpGet pageGet = new HttpGet(tURI);
						// HttpPost pagePost = new HttpPost(tURI);
						try {
							HttpResponse response = httpClient.execute(pageGet);

							HttpEntity entity = response.getEntity();
							String responseBody = EntityUtils.toString(entity);

							objAppManager.setAlreadyRegisteredWithServer(true);

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
			} catch (Exception e) {
				Log.i("JTApplication.RegisterDeviceWithServer", e.getMessage());
			}

			return null;
		}

	}
}
