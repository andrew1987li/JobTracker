package co.uk.sbsystems.android.jobtrackermobile;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.Toast;

// import java.util.ArrayList;

public class C_AppManager {

	protected Context context;

	private long mvarRecordID = -1;
	private String mvarHostName = "";
	private int mvarPort = 0;
	private boolean mvarConnected = false;
	private boolean mvarDoNotClear = false;
    private boolean mvarAttemptingToConnect = false;


	private String mvarassignedEngineersName = "";
	private C_JTJob loadedJob = null;
	// private ArrayList _labels = new ArrayList();

	private Integer UseDueDate = 0;
	private Integer UseJobBrief = 1;
	private Integer IgnoreLowSignal = 1;
	private Integer UseReverseLookup = 0;

	private String MACAddress = "";

	private boolean AlreadyRegisteredWithServer = false;

	// Mobile login credentials
	private String mvarUserName = "";
	private String mvarPassword = "";
	
	private boolean mvarForcedOffline = false; 		// User clicked disconnect
	boolean mvarNoMessage = false; 						// Do not display connection messages
	
	private  boolean mvarStartingUp = true;
	private  double mvarLongitude = 0;
	private  double mvarLatitude = 0;
	
	public  boolean GetStartingUp() {
		return mvarStartingUp;
	}

	public  void SetStartingUp(boolean value) {
		mvarStartingUp = value;
	}


	public  double getLongitude() {
		return mvarLongitude;
	}

	public  double getLatitude() {
		return mvarLatitude;
	}

	public  void setLocation(double lat, double lon) {
		mvarLongitude = lon;
		mvarLatitude = lat;
	}	

	public boolean GetForcedOffLine() {
		return mvarForcedOffline;
	}
	public void SetForcedOffLine(boolean value) {
		mvarForcedOffline= value;
	}
	
	public boolean GetNoMessage() {
		return mvarNoMessage;
	}
	
	public void SetNoMessage(boolean value) {
		mvarNoMessage = value;
	}
	
	
	public C_AppManager(Context mycontext) {
		context = mycontext;
		loadedJob = new C_JTJob();
		setMACAddress();
	}

	public String getMobileUserName() {
		return mvarUserName;
	}

	public void setMobileUserName(String value) {
		mvarUserName = value;
	}

	public String getMobilePassword() {
		return mvarPassword;
	}

	public void setMobilePassword(String value) {
		mvarPassword = value;
	}

	public Integer getUseReverseLookup() {
		return UseReverseLookup;
	}

	public void setUseReverseLookup(Integer value) {
		UseReverseLookup = value;
	}

	public Integer getIgnoreLowSignal() {
		return IgnoreLowSignal;
	}

	public void setIgnoreLowSignal(Integer value) {
		IgnoreLowSignal = value;
	}

	public Integer getUseJobBrief() {
		return UseJobBrief;
	}

	public void setUseJobBrief(Integer value) {
		UseJobBrief = value;
	}

	public Integer getUseDueDate() {
		return UseDueDate;
	}

	public void setUseDueDate(Integer value) {
		UseDueDate = value;
	}

	public C_JTJob GetloadedJob() {
		return loadedJob;
	}

	public String getDeviceAssignedEngineerName() {
		return mvarassignedEngineersName;
	}

	public void setDeviceAssignedEngineerName(String value) {
		mvarassignedEngineersName = value;
	}

	public boolean getConnectedStatus() {
		return mvarConnected;
	}

	public void setConnectedStatus(boolean value) {
		mvarConnected = value;
	}

    public void setAttemptingToConnect(boolean value) {
        mvarAttemptingToConnect = value;
    }

    public boolean getAttemptingToConnect() {
        return mvarAttemptingToConnect;
    }

    public Long getRecordID() {
		return mvarRecordID;
	}

	public void setRecordID(Long value) {
		mvarRecordID = value;
	}

	public String getHostName() {
		return mvarHostName;
	}

	public void setHostName(String value) {
		mvarHostName = value;
	}

	public int getPort() {
		return mvarPort;
	}

	public void setPort(int value) {
		mvarPort = value;
	}

	public void DoNotClearjobSummary(boolean value) {
		mvarDoNotClear = value;
	}

	public boolean DoNotClearjobSummary() {
		return (mvarDoNotClear);
	}

	public String getMACAddress() {
		return MACAddress;
	}

	public void setMACAddress() {

		String android_id = "";
		try {
			if (context != null) {
				android_id = Secure.getString(context.getContentResolver(),
						Secure.ANDROID_ID);
				MACAddress = android_id;
			}
		} catch (Exception e) {
			Log.i("JTApplication.GetMACAddress", e.getMessage() + "");
		}

	}

	public boolean getAlreadyRegisteredWithServer() {
		return AlreadyRegisteredWithServer;
	}

	public void setAlreadyRegisteredWithServer(boolean value) {
		AlreadyRegisteredWithServer = value;
	}

	public Boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public Boolean ConnectToNetwork() {
		Boolean ret = false;

		// Quick  way to exit if we're already connected.
		if (JTApplication.getInstance().GetApplicationManager().getConnectedStatus() == true) {
			return true;
		}

		if (mvarForcedOffline == true) {
			if (GetNoMessage() == false) {
				Toast.makeText(
						context,
						"Connection has been manually closed, automatic reconnection is not allowed.  To reconnect you need to click Connect",
						Toast.LENGTH_LONG).show();
			}
			return false;
		}

		if (JTApplication.getInstance().GetApplicationManager().isNetworkAvailable() == false) {
			Toast.makeText(context,
					"There is no Internet connection available to connect to!",
					Toast.LENGTH_LONG).show();
			ret = false;
		} else {
			// Double check, make sure Job Tracker thinks we've disconnected
			if (JTApplication.getInstance().GetApplicationManager().getConnectedStatus() == false) {
				// Connection thread should have terminated, try restarting.


				JTApplication.getInstance().setTCPManager(new NetworkTask());
				JTApplication.getInstance().getTCPManager().execute();


				/*String android_id = "";

				//JTApplication.getInstance().GetApplicationManager().getConnectedStatus();
				if (JTApplication.getInstance().GetApplicationManager().getConnectedStatus()) {
					Toast.makeText(context, "Successfully Reconnected.",
					Toast.LENGTH_LONG).show();
					ret = true;
				} else {
					Toast.makeText(context, "Connection Failed.",
							Toast.LENGTH_LONG).show();
					ret = false;
				}*/

			}
		}

		return ret;
	}

}
