package co.uk.sbsystems.android.jobtrackermobile;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.channels.FileChannel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

// import java.util.List;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	static Context mycontext1;
	//protected JTApplication app;

	/** Called when the activity is first created. */
	private RefreshHandler mRedrawHandler = new RefreshHandler();

	// This is also called when coming back to the main screen from any other
	// screen
	@Override
	public void onStart() {
		super.onStart();
		
		
		if (!JTApplication.getInstance().getAppManager().GetStartingUp()) {
			NetworkTask myTask;
			myTask = JTApplication.getInstance().getTCPManager();

			if (myTask == null) {

				// JTApplication.ConnectSocket();

				try {
					Thread.sleep(1750); // Socket Connection fails without 
										// delay??
				} catch (Exception e) {

				}

				// This starts the network thread.
				JTApplication.getInstance().setTCPManager(new NetworkTask());
				JTApplication.getInstance().getTCPManager().execute();

				String android_id = "";
				
				

				// If we've got a socket connection, send the ID
				if (JTApplication.getInstance().getTCPManager().AreWeOnline() == true)
					JTApplication.getInstance().getTCPManager().SendMyId(android_id);

			}

		} else {
			JTApplication.getInstance().GetApplicationManager().SetStartingUp(false);
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//app = (JTApplication)getApplication();
		
		try {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.main);
			// JTApplication.StartTheApplication();

			LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			LocationListener ll = new myLocationListener();
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50,
					ll);

			ImageButton loadjob = (ImageButton) findViewById(R.id.butloadjob);
			loadjob.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent myIntent = new Intent(view.getContext(),
							activity2.class);
					startActivityForResult(myIntent, 0);
				}
			});

			ImageButton butExit = (ImageButton) findViewById(R.id.butlogoff);
			butExit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					CleanUp();
					finish();
				}
			});

			ImageButton butSearch = (ImageButton) findViewById(R.id.butsearchjobs);
			butSearch.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent myIntent = new Intent(view.getContext(),
							SearchJobs.class);
					startActivityForResult(myIntent, 0);
				}
			});

			updateUI();

		} catch (Exception e) {
			Log.e("onCreate", "Exception", e);

		}

	}

	class myLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			if (location != null) {
				double pLong = location.getLongitude();
				double pLat = location.getLatitude();

				try {
					JTApplication.getInstance().getAppManager().setLocation(pLong, pLat);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

	}

	// This is the pop up menu.
	@Override
	public boolean onCreateOptionsMenu(Menu mainmenu) {
		try {
			super.onCreateOptionsMenu(mainmenu);
			getMenuInflater().inflate(R.menu.mainmenu, mainmenu);
			return super.onCreateOptionsMenu(mainmenu);

			// mainmenu.findItem(R.id.mnuNetWorking).setIntent(
			// new Intent(this,NetworkSettings.class));
		} catch (Exception e) {
			Log.i("onCreateoptionsMenu", e.getMessage());
		}

		return true;

	}
	
	
	
	private void copyFile(File src, File dst) throws IOException {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
           inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (Exception ex){
        	Log.i("Error",ex.getMessage());
        }
        finally {
        }
           if (inChannel != null)
              inChannel.close();
           if (outChannel != null)
              outChannel.close();
        }
     
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		try {
			Log.i("Menu", Integer.toString(Menu.FIRST));

			Intent myIntent = null;
			switch (item.getItemId()) {
			case R.id.mnuNetWorking:
				myIntent = new Intent(this, NetworkSettings.class);
				this.startActivity(myIntent);
				break;

			case R.id.mnuConnect:

				JTApplication.getInstance().getAppManager().SetForcedOffLine(false);
				JTApplication.getInstance().GetApplicationManager().ConnectToNetwork();
				break;

			case R.id.mnuDisconnect:
				JTApplication.getInstance().getAppManager().SetForcedOffLine(true);
				JTApplication.getInstance().TCPManager().CloseSocket();
				break;

			case R.id.mnuAbout:
				myIntent = new Intent(this, About.class);
				this.startActivity(myIntent);
				break;

			case R.id.mnuAssignEng:
				// Attempt to reconnect again just in case. This takes care of
				// already being connected as well.
				JTApplication.getInstance().GetApplicationManager().ConnectToNetwork();

				myIntent = new Intent(this, AssignEngineer.class);
				this.startActivity(myIntent);
				break;

			case R.id.mnuRefreshLabs:
				// Attempt to reconnect again just in case. This takes care of
				// already being connected as well.
				JTApplication.getInstance().GetApplicationManager().ConnectToNetwork();
				if (JTApplication.getInstance().GetApplicationManager().getConnectedStatus()) {
					JTApplication.getInstance().getTCPManager().GetLabelsFromServer();
				}
				break;

			case R.id.mnuRefreshEngs:
				JTApplication.getInstance().GetApplicationManager().ConnectToNetwork();
				if (JTApplication.getInstance().GetApplicationManager().getConnectedStatus()) {
					JTApplication.getInstance().getTCPManager().GetEngsFromServer();
				}
				break;

			case R.id.mnuSettings:
				myIntent = new Intent(this, EditSettings.class);
				this.startActivity(myIntent);
				break;

			case R.id.mnuClearCache:
				myIntent = new Intent(this, ClearCache.class);
				this.startActivity(myIntent);
				break;

			case R.id.mnuSendDatabase:
				
					DoBackgroundOne();
					/*
					AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
					File dbFile;
					  dbFile = new File(Environment.getDataDirectory() + "/data/co.uk.sbsystems.android.jobtrackermobile/databases/example.db");

				           File exportDir = new File(Environment.getExternalStorageDirectory(), "");
				           if (!exportDir.exists()) {
				              exportDir.mkdirs();
				           }
				           File file = new File(exportDir, dbFile.getName());
					  

			           try {
			        	   file.createNewFile();
			        	   this.copyFile(dbFile, file);
					
							
							 
							 final GMailSender mySender = new GMailSender(
									"jobtrackerpro@gmail.com", "**Arnold2706");
								if (mySender.sendMail("From JT Mobile",JTApplication.GetApplicationManager().getMobileUserName(),"jobtrackerpro@gmail.com", "sam@sbsystems.co.uk",file, this) == 1 ) {
									//AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
									dlgAlert.setMessage("Database Sent Successfully");
									dlgAlert.setTitle("Database was sent");
									dlgAlert.setPositiveButton("OK", null);
									dlgAlert.setCancelable(true);
									dlgAlert.create().show();
								}
							
			           }
			           catch (Exception e) {
			        	   //AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
			        	   dlgAlert.setMessage(e.getMessage());
			        	   dlgAlert.setTitle("Sending Database Error");
			        	   dlgAlert.setPositiveButton("OK", null);
			        	   dlgAlert.setCancelable(true);
			        	   dlgAlert.create().show();
						}
						*/
						break;

			default:
				break;

			}

			Log.i("Menu Pressed", Integer.toString(item.getItemId()));
		} catch (Exception e) {
			Log.i("onOptionsItemsselected", e.getMessage());
		}
		return true;

	}

	private void CleanUp() {
		String TCPCmd = "";
		try {
			TCPCmd = JTApplication.getInstance().getTCPManager().RequestTermination();
			if (JTApplication.getInstance().getTCPManager().IsRunning() == true) {
				JTApplication.getInstance().getTCPManager().SendDataToNetwork(TCPCmd);
			} else {
				appendLog("(209) MainActivity CleanUp(), Not called as socket is closed.");
			}
		} catch (Exception e) {
			Log.i(TAG + "CleanUp", e.getMessage());
		}

		// JTApplication.getTCPManager().CloseSocket();
		// JTApplication.getTCPManager().cancel(true);
	}

	class RefreshHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			MainActivity.this.updateUI();
		}

		public void sleep(long delayMillis) {
			this.removeMessages(0);
			sendMessageDelayed(obtainMessage(0), delayMillis);
		}
	};

	private void updateUI() {
		// String android_id = "";

		ImageButton btnConnect = (ImageButton) findViewById(R.id.btnConnect);

		try {
			if (JTApplication.getInstance().GetApplicationManager().getConnectedStatus() == true) {
				btnConnect.setImageResource(R.drawable.connected);
				// if (JTApplication.getTCPManager().AreWeOnline() == true) {
				// android_id = JTApplication.GetMACAddress();
				// JTApplication.getTCPManager().SendMyId(android_id);
				// }
			} else {
				btnConnect.setImageResource(R.drawable.disconnected);
			}
		} catch (Exception e) {
			Log.i("UpdateUI", e.getMessage() + "");
		}

		try {
			btnConnect.setBackgroundDrawable(null);

			mRedrawHandler.sleep(5000);
		} catch (Exception e) {
			Log.i("UpdateUI", e.getMessage() + "");
		}

		if (JTApplication.getInstance().getSettings().getLoginFailed() == true) {

			try {
				JTApplication.getInstance().getSettings().setLoginFailed(false);
				DoToast("Username, Password was rejected");
				CleanUp();
			} catch (Exception e) {
				Log.i("Error MainActivity", e.getMessage());
			}
		}

	}

	private void DoToast(String msg) {
		Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

	}
	
	private void DoBigToast(String msg) {
		Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();

	}

	public static void appendLog(String text) {
		File logFile = new File(Environment.getExternalStorageDirectory(),
				"jtmob.log");
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			// BufferedWriter for performance, true to set append to file flag
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
					true));
			buf.append(text + "\r\n");
			buf.newLine();
			buf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void UpdateScreen(String DataIn) {

	}

	private void DoSocketTest() {
		Socket socket = null;
		DataOutputStream dataOutputStream = null;
		DataInputStream dataInputStream = null;
		try {
			appendLog("About to create socket (81.136.241.185,8010) ");

			String serverIPAddress = JTApplication.getInstance().GetApplicationManager()
					.getHostName(); // "81.136.241.185"
			// InetAddress serverAddr = InetAddress.getByName("81.136.241.185");
			// // This works.
			InetAddress serverAddr = InetAddress.getByName(serverIPAddress); // This
																				// works
																				// too.

			// SocketAddress sockaddr = new
			// InetSocketAddress("81.136.241.185",8010);
			try {
				socket = new Socket(serverAddr, 8010);
				appendLog("\nSocket was successfully created: "
						+ socket.getInetAddress().getHostAddress().toString());
			} catch (IOException ioE) {
				appendLog("IOException " + ioE.getMessage());
			}

			appendLog("\nBack from new Socket()\nAbout to do dataOutputSteam");
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			appendLog("\nBack from new dataOutputSteam");
			dataInputStream = new DataInputStream(socket.getInputStream());

			dataOutputStream.writeUTF("HI THERE");

			Thread.sleep(2500);

			socket.close();

		} catch (Exception e) {
			appendLog("\nSocket Failed");
			appendLog("\nException: " + e.getMessage());
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			JTApplication.getInstance().PrepareForDestroy();
		} catch (Exception e) {
			Log.i(TAG + "onDestroy", e.getMessage());
		}
	}

	public Context GetContext() {
		return this;
	}
	
	private void DoBackgroundOne()
	{
		//File dbFile = new File(Environment.getDataDirectory()
		//		+ "/data/co.uk.sbsystems.android.jobtrackermobile/databases/example.db");

		//dbFile = new File(Environment.getExternalStorageDirectory().getPath() + "/20150712_165611.jpg");
		 //ExportDatabaseFileTask export = new ExportDatabaseFileTask(getApplicationContext());
		
		try {
			DoBigToast("The database will be sent in the background.\nYou can carry on using Job Tracker Mobile");
			ExportDatabaseFileTask doBackStuff = new ExportDatabaseFileTask(this);
			doBackStuff.execute();
		} catch (Exception e) {
			Log.v("Start Task",e.getMessage());
		}
		
	}

	 
	
}
