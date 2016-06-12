package co.uk.sbsystems.android.jobtrackermobile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.Object;
import java.util.Date;



// Generic's:
// 1. Void:			Type of reference passed to doInBackgound()
// 2. byte Array:	Type of reference passed to onProgressUpdate()
// 3. Boolean:		Type of reference returned on onPostExecute()
//
public class NetworkTask extends AsyncTask<Void, byte[], Boolean> {
	
	private  final String TAG = "NetworkTask";

	private  final int CMD_LOADJOB = 1;
	private  final int CMD_LISTJOBS = CMD_LOADJOB + 1 ;
	private  final int CMD_LISTSTAFF = CMD_LISTJOBS + 1;
	private  final int CMD_ERR = CMD_LISTSTAFF + 1;
	private  final int CMD_LABELS = CMD_ERR + 1;
	private  final int CMD_UPDATESTATUS = CMD_LABELS + 1;
	private  final int CMD_UPDATETIMESHEETIDS = CMD_UPDATESTATUS + 1;
	private  final int CMD_NAK = CMD_UPDATETIMESHEETIDS + 1;
	private  final int CMD_WHOAREYOU = CMD_NAK + 1;
	private  final int CMD_SETTINGS = CMD_WHOAREYOU + 1;
	
	
	private  Double dataSize =0.0;
	private  Integer currentDataSize = 0;
	private ProgressDialog mProgressDialog;
    Double iProgress = 0.0;
	private Context mContext;

    // We need a public context here that holds the context for the current UI.
	
	String _DataIn = "";			// This is the completed data received.
	
	public  Socket nsocket = null; 				// Network Socket
	public  InputStream nis; 						// Network Input Stream
	public  OutputStream nos; 					// Network Output Stream

	boolean _StillReceiving = false;
	boolean Terminate = false;			// Set to true to terminate the thread.
	boolean Running = false;


    String sUpdateCmd = "";             // Current command


    clsBuffer dataData = new clsBuffer();

    private  void ConnectSocket()
	{
		String TAG = "NetworkTask.ConnectSocket()";
		String serverIPAddress = "";		
		Integer serverPort = 8010;
		
		try {
			serverPort = JTApplication.getInstance().GetApplicationManager().getPort();
			serverIPAddress = JTApplication.getInstance().GetApplicationManager().getHostName();
			JTApplication.getInstance().getTCPManager().nsocket = new Socket();
			JTApplication.getInstance().getTCPManager().nsocket.connect(new InetSocketAddress(serverIPAddress.trim(), serverPort), 5000);

			//new C_ConnectSocket().execute();

		} catch (Exception e) {
			Log.e(TAG, "DoInBackground", e);
		}		
	}    


	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
        searchUpdate = new Handler();
       /* if(mContext != null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setTitle("Download Jobs");
            mProgressDialog.setMessage("Downloading");
            mProgressDialog.setProgressStyle(mProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setMax(100);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();

        }


*/
    // This needs to be done in the background thread.
	//	ConnectSocket();
	}

	
	// This is the data receiver, it keeps running until the socket is closed by termination or error. 
	@Override
	protected Boolean doInBackground(Void... params) { //This runs on a different thread
		// android.os.Debug.waitForDebugger();
		boolean result = false;
		Integer progress =10;
		
		try {

            if (nsocket == null) {
				JTApplication.getInstance().GetApplicationManager().setAttemptingToConnect(true);
                // This makes the physical connection.
                ConnectSocket();   // This blocks until connected or times out
				JTApplication.getInstance().GetApplicationManager().setAttemptingToConnect(false);

			}
			
			if (nsocket.isConnected()) {
				JTApplication.getInstance().GetApplicationManager().setConnectedStatus(true);
				Running = true;


                nis = nsocket.getInputStream();
				nos = nsocket.getOutputStream();
				byte[] buffer = new byte[1024];
				int read = nis.read(buffer, 0, 1024); //This is blocking
				while(read != -1) {		// This keeps going until -1 is returned, -1 = socket closed or some other socket error.
					byte[] tempdata = new byte[read];
					System.arraycopy(buffer, 0, tempdata, 0, read);
				//Process Receiving Message.
					publishProgress(tempdata);		// This fires onProgressUpdate
                    Thread.sleep(100);

					read = nis.read(buffer, 0, 1024); //This is blocking
					
					if (isCancelled()) break;
				}
                sUpdateCmd = "";
			}

			JTApplication.getInstance().GetApplicationManager().setConnectedStatus(false);
			Running = false;
			
		} catch (IOException e) {
			Log.e("TCP", "Exception receiving data", e);
			result = true;
		} catch (Exception e) {
			Log.e("TCP", "Exception receiving data", e);
			result = true;
		} finally {
			try {
				JTApplication.getInstance().GetApplicationManager().setConnectedStatus(false);
				nis.close();
				nos.close();
				nsocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Running = false;
		return result;
	}
	

//For download interface
    public Context nowContext;
    boolean nowState = false;
    ProgressDialog searchDlg;
    ProgressDialog ringDlg;
    Handler searchUpdate;

    AlertDialog srchDlg;
    View prostate;


    public void createProgressBar(){

      searchUpdate = new Handler();
        searchDlg = new ProgressDialog(nowContext);

        searchDlg.setTitle("Downloading Jobs");
        searchDlg.setMessage("Downloading......");
        searchDlg.setProgressStyle(mProgressDialog.STYLE_HORIZONTAL);
        searchDlg.setMax(100);
        searchDlg.setIndeterminate(false);
        //searchDlg.setProgress(70);

        searchDlg.show();
     /*


        LayoutInflater li = LayoutInflater.from(nowContext);
        prostate = li.inflate(R.layout.progressview, null);

        AlertDialog.Builder bld_srchDlg = new AlertDialog.Builder(nowContext);

        bld_srchDlg.setView(prostate);

        bld_srchDlg.setCancelable(true);

        srchDlg = bld_srchDlg.create();

        srchDlg.show();

*/
      //  ringDlg = ProgressDialog.show(nowContext, "Donwloading Jobs", "Waiting for downlaoding",true);
      //  ringDlg.setCancelable(true);



    }





	// As data is received this is called with the data to date.
	@Override
	protected void onProgressUpdate(byte[]... values) {
        super.onProgressUpdate(values);
        Integer iDataInSize;


        try {
            if (values.length > 0) {
                String DataIn = new String(values[0]);


                // If the start of a sequence is detected clear down any previous data.
                dataData.addString(DataIn);

                if (DataIn.contains("<?xml")) {
                    // The command is always sent at the start
                    sUpdateCmd = JTApplication.getInstance().GetParser().ExtractTagValue(DataIn, "CMD");


                    _DataIn = "";
                    // Get the data size
                    dataSize = GetDataSize(DataIn);
                    currentDataSize = 0;

                }

                iDataInSize = DataIn.length();
                currentDataSize += iDataInSize;

                if (dataSize > 0) {

                    iProgress = (currentDataSize / dataSize) * 100;


                    if (nowState) {
                        iProgress = (currentDataSize / dataSize) * 100;
                        searchDlg.setProgress(iProgress.intValue());

                        searchDlg.setMessage("Downloading:" + iProgress.intValue());


                        /* searchUpdate.post(new Runnable() {
                             @Override
                             public void run() {
                                 searchDlg.setMessage("jobs  percent:"+ iProgress.intValue() );
                                 searchDlg.setProgress(iProgress.intValue());
                             }
                         });


                        }
                        */
                        if (iProgress.intValue() >= 90) {
                            try {
                                Thread.sleep(300);
                            } catch (Exception e) {

                            }

                            try {
                                searchDlg.dismiss();
                            } catch (Exception e) {

                            }


                            nowState = false;
                        }

/*                     if(nowState){



                         final ProgressBar pro =(ProgressBar)prostate.findViewById(R.id.downloadBar);
                         final TextView state = (TextView)prostate.findViewById(R.id.downlaodState);
                         pro.setProgress(iProgress.intValue());


                         state.setText("jobs  percent:" + iProgress.intValue());
                         searchUpdate.post(new Runnable() {
                             @Override
                             public void run() {
                                 pro.setProgress(iProgress.intValue());

                                 state.setText("jobs  percent:"+ iProgress.intValue() );
                             }
                         });

                            */

                     }

                        if (iProgress.intValue()>=100) {
                            try {
                                srchDlg.cancel();
                                srchDlg.dismiss();
                                //searchDlg.dismiss();
                                // Thread.sleep(400);
                                ringDlg.dismiss();
                                nowState = false;
                            } catch (Exception e) {

                            }

                        }
                        try {
                            switch (GetCMDId(sUpdateCmd)) {
                                case CMD_LISTJOBS:

                                    // Reaching here we have recevied 1024 bytes of data.
                                    // Need to update the progress using the public context set previously.
                                    // if the progress bar does not exist, create it here.
                                    // mProgressDialog.setProgress(iProgress.intValue());

                                    // searchDlg.setProgress(iProgress.intValue());
                                    // if(iProgress.intValue()>95) searchDlg.dismiss();
                                    //JTApplication.getInstance().getSearchJobsInstance().UpdateSearchStatus(iProgress);
                                    // Thread.sleep(300);
                                default:
                            }


                        } catch (Exception ex) {
                            // Do Nothing, simply means progress dialog was not visible.
                        }
                    }

                    // GetCommand returns a CMD only when the entire data has been received.
                    // e.g. When CMD_LISTJOBS is returned it means DataIn contains the entire list of jobs.
                    String sCMD = GetCommand(DataIn);

                    switch (GetCMDId(sCMD)) {
                        case CMD_LOADJOB:
                            // activity2  ->  This is the Jobs Screen
                            activity2.getInstance().UpdateScreen(_DataIn);

                            break;

                        case CMD_LISTJOBS:
                            // When it reaches here it has fully formatted XML in _DataIn
                            SearchJobs.UpdateScreen(_DataIn);
					/*	if (mProgressDialog != null) {
							if (mProgressDialog.isShowing())
								mProgressDialog.dismiss();
						}*/
                            break;

                        case CMD_LISTSTAFF:
                            AssignEngineer.UpdateScreen(_DataIn);
                            break;

                        case CMD_ERR:
                            break;

                        case CMD_LABELS:
                            JTApplication.getInstance().GetDatabaseManager().UpdateLabels(_DataIn);
                            JTApplication.getInstance().GetDatabaseManager().LoadLabels();
                            break;

                        case CMD_UPDATESTATUS:

                            activity2.getInstance().UpdateStatus(_DataIn);
                            break;

                        case CMD_UPDATETIMESHEETIDS:
                            activity2.getInstance().UpdateTimeSheetIDs(_DataIn);
                            break;

                        case CMD_NAK:
                            activity2.getInstance().UpdateLoadJobStatus(_DataIn);
                            break;

                        case CMD_WHOAREYOU:
                            //activity2 myactivity = new activity2();
                            activity2.getInstance().IdentifyYourself(_DataIn);
                            break;

                        case CMD_SETTINGS:
                            JTApplication.getInstance().getSettings().ParseSettings(_DataIn);


                        default:
                            if (dataSize > 0) {

                            }

                    }

                }
            else{
                    try {
                        srchDlg.cancel();
                        srchDlg.dismiss();
                        //searchDlg.dismiss();
                        // Thread.sleep(400);
                        ringDlg.dismiss();
                        nowState = false;
                    } catch (Exception e) {

                    }

            }

        }catch(Exception e)
        {
                Log.i("AsyncTask", "onProgressUpdate: " + e.getMessage());

        }


    }
	
	@Override
	protected void onCancelled() {
		try {
			Log.i("AsyncTask", "Cancelled.");
		} catch (Exception e)
		{
			Log.i("OnCancelled Exception", e.getMessage());

		}
	}
	
	@Override
	protected void onPostExecute(Boolean result) {

		try {
            mProgressDialog.dismiss();
            searchDlg.dismiss();
			if (result) {
				Log.i("AsyncTask", "onPostExecute: Completed with an Error." + result.toString());
				
				//textStatus.setText("There was a connection error.");
			} else {
				Log.i("AsyncTask", "onPostExecute: Completed.");
			}
		} catch (Exception e)
		{
			Log.e("AsyncTask", "onPostExecute", e);
		}
		//btnStart.setVisibility(View.VISIBLE);
	}
	


	public boolean IsRunning()
	{
		return Running;
	}
	
	private int GetCMDId(String cmd)
	{
		int id=-1;
		if (cmd.equals("LOADJOB")) 
			id = CMD_LOADJOB;
		
		if (cmd.equals("LISTJOBS"))
			id = CMD_LISTJOBS;

		if (cmd.equals("LISTSTAFF"))
			id = CMD_LISTSTAFF;

		if (cmd.equals("ERR"))
			id = CMD_ERR;

		if (cmd.equals("LABELS"))
			id = CMD_LABELS;

		if (cmd.equals("UPDATESTATUS"))
			id = CMD_UPDATESTATUS;
		
		if (cmd.equals("NAK"))
			id = CMD_NAK;
		
		if (cmd.equals("IDENTIFYYOURSELF"))
			id = CMD_WHOAREYOU;
			
		if (cmd.equals("GETSETTINGS"))
			id = CMD_SETTINGS;
		return id;
	}

    public  void SendPacket(byte[]data, int pos, int len){
        try {
            if (nsocket != null) {
                if (AreWeOnline()) {
                    nos.write(data, pos, len );
                } else {
                    // Lost connection, job will have been saved to cache.
                    Log.v("NetworkTask", "No longer connected");
                }
            }else{
                Log.v("NetworkTask", "Socket is null");
            }
        } catch (Exception e) {
            Log.e("AsyncTask", "SendDataToNetwork: Message send failed. Caught an exception", e);
        }
    }

	public  void SendPacket(byte[]data){
		try {
			if (nsocket != null) {
				if (AreWeOnline()) {
					nos.write(data);
				} else {
					// Lost connection, job will have been saved to cache.
					Log.v("NetworkTask", "No longer connected");
				}
			}else{
				Log.v("NetworkTask", "Socket is null");
			}
		} catch (Exception e) {
			Log.e("AsyncTask", "SendDataToNetwork: Message send failed. Caught an exception", e);
		}
	}
	
	public void SendDataToNetwork(String cmd) { //You run this from the main thread.
		try {
			if (nsocket != null) {
				if (AreWeOnline()) {
					nos.write(cmd.getBytes());
				} else {
					// Lost connection, job will have been saved to cache.
					Log.v("NetworkTask", "No longer connected");
				}
			}else{
				Log.v("NetworkTask", "Socket is null");
			}
		} catch (Exception e) {
			Log.e("AsyncTask", "SendDataToNetwork: Message send failed. Caught an exception", e);
		}
	}
	
	
	// This is here for compatibility reasons only.
	public void SendData(String cmd) { //You run this from the main thread.
		
		try {
			if (AreWeOnline()) {
				SendDataToNetwork(cmd);
			}
		} catch (Exception e) {
			Log.e("AsyncTask", "SendDataToNetwork: Message send failed. Caught an exception", e);
		}
	}
	
	
	public boolean AreWeOnline() {
		boolean Online = false;
		boolean notClosed = false;
		boolean connected = false;
		
		try {
			if (nsocket != null) {
				notClosed = nsocket.isClosed() == false;
				connected = nsocket.isConnected();
			}
		} catch (Exception e) {
			// Most likely socket does not exist, in short we're off line.
		}
		
		Online = (notClosed & connected);
		
		 
		return Online;
	}
	
	
	public void CloseSocket()
	{
		try {
			nsocket.close();
			
		} catch(Exception e)
		{
		}
	}
	
	public Double GetDataSize(String sDataIn) {
		Double size = -1.0;			// -1 = Unknown size
		String sSize="";
		Integer startPos;
		Integer endPos;
		
		try {
			if (sDataIn.contains("<SIZE>")) {
				startPos = sDataIn.indexOf("<SIZE>");
				endPos = sDataIn.indexOf("</SIZE>");
				sSize = sDataIn.substring(startPos + 6, endPos);
				if (sSize.equals("XXX")) {
					size  = -1.0;
				} else {
					size = Double.parseDouble(sSize);
				}
			}
		} catch (Exception ex) {
			Log.i(TAG, ex.getMessage());
		}
		
		return size;
	}
	
	private String GetCommand(String sDataIn )
	{
		String sCmd = "";
		
		try 
		{
			if (sDataIn.contains("</data>") || (sDataIn.contains("</ERROR>")) || sDataIn.contains("<NAK"))
			{
				if (sDataIn.contains("</ERROR>"))
				{
					sCmd = "ERR";
					return (sCmd);
				}
				else 
				{
					// Set cursor to normal.
					_StillReceiving = false;
					_DataIn += sDataIn;
					
					try
					{
						if (sDataIn.contains("NAK")) {
							JTApplication.getInstance().GetParser().ResetDocLoaded();
							sCmd = "NAK";
						} else {
							JTApplication.getInstance().GetParser().ResetDocLoaded();
							sCmd = JTApplication.getInstance().GetParser().ExtractTagValue(_DataIn ,"CMD");
						}
					}
					catch (Exception e)
					{
						Log.i("GetCommand", "Exception: " + e.getMessage());
						sCmd = "";
					}
					
				}
			}
			else
			{
				_StillReceiving = true;
				_DataIn += sDataIn;
				sCmd = "";
			}
		}
		catch (Exception e)
		{
			Log.i("GetCommand", "Exception: " + e.getMessage());
			sCmd = "";
		}
		
		
		return (sCmd);
	}
	
    public void SendMyId(String myID)
	{
		//MainActivity myact = new MainActivity();
    	
    	new C_RegisterDevice().execute("","","");
     	//JTApplication.RegisterDeviceWithServer();
    	
		String TCPString = "";
		TCPString = HeresMyID(myID);		// TCPString needs put into a packet.

		byte[]data = TCPString.getBytes();
		byte[]sndData = mUtils.MakePacket (data,(byte) CommandType.OTHERCOMMAND.getVal(),data.length);			//  <- Anatoly's new wrapper function.

		//SendDataToNetwork(TCPString);
		
	}
	
	public void RequestListJobs(int iDays, boolean NewOnly,String SearchStr,boolean ClosedOnly,String AssignedTo,boolean UseDueDate)
	{
		String TCPString = "";
		TCPString = ConstructRequestListJobs(iDays,NewOnly,SearchStr,ClosedOnly,AssignedTo,UseDueDate);
		Log.v("NetworkTask", "Sending data: "+TCPString);

		byte[]sndData = TCPString.getBytes();

		byte[] data = mUtils.MakePacket(sndData, (byte)CommandType.OTHERCOMMAND.getVal(), sndData.length);
		SendPacket(data);

		//SendDataToNetwork(TCPString);
	}
	
	private String ConstructRequestListJobs(int iDaysOld,boolean NewOnly,String sSearch,boolean bClosed,String sAssignedTo,boolean bDueDate)
	{
		String ret = "";
		ResetDataBuffer();
		
		ret = XMLHeader();
		ret += "<LISTJOBS>\r\n";

        ret += AddTag("UN", JTApplication.getInstance().GetApplicationManager().getMobileUserName());
        ret += AddTag("PW", JTApplication.getInstance().GetApplicationManager().getMobilePassword());
		
        ret += AddTag("AGE", Integer.toString(iDaysOld));
        ret += AddTag("NEWONLY", Boolean.toString(NewOnly));
        ret += AddTag("CLOSED", Boolean.toString(bClosed));
        ret += AddTag("ASSIGNED", sAssignedTo);
        ret += AddTag("DUEDATE", Boolean.toString(bDueDate));
		if (sSearch.trim() != "") {
			ret += AddTag("SEARCH", sSearch);
		}
        ret += AddGeo();	// Add Geo Location Coords
        
        ret += "</LISTJOBS>\r\n";
        ret += XMLFooter();		
		
		return ret;
	}
	
	public String AddGeo() 
	{
		String ret = "";
		
		try {
			ret += "<LONG>";
			ret += JTApplication.getInstance().getAppManager().getLongitude();
			ret += "</LONG>\r\n";
			ret += "<LAT>";
			ret += JTApplication.getInstance().getAppManager().getLatitude();
			ret += "</LAT>\r\n";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public String RequestTermination()
	{
		String ret = "";
		
		try {
			ret = XMLHeader();
			ret += "<TERMINATE>\r\n";
			ret += "</TERMINATE>\r\n";
			ret += XMLFooter();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return ret;
	
	}
	
	
	public String GetMACAddress(Context myContext)
	{
		
		TelephonyManager mTelephonyMgr = null;
		
		try {
			mTelephonyMgr = (TelephonyManager)myContext.getSystemService(Context.TELEPHONY_SERVICE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mTelephonyMgr.getDeviceId().toString();
		
				
	}
	
	public String HeresMyID(String myID)
	{
		String ret = "";
		
		try {
			ret = XMLHeader();
			ret += "<IDENTITY>\r\n";
			ret += "<MAC>\r\n";
			ret += myID +  "\r\n";
			ret += "</MAC>\r\n";
			ret += "<ENG>\r\n";
			ret += JTApplication.getInstance().GetApplicationManager().getDeviceAssignedEngineerName() +  "\r\n";
			ret += "</ENG>\r\n";
			ret += "<UN>\r\n";
			ret += JTApplication.getInstance().GetApplicationManager().getMobileUserName() + "\r\n";
			ret += "</UN>\r\n";
			ret += "<PW>\r\n";
			ret += JTApplication.getInstance().GetApplicationManager().getMobilePassword() + "\r\n";
			ret += "</PW>\r\n";
			ret += AddGeo();
			ret += "</IDENTITY>\r\n";
			ret += XMLFooter();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
        
        
		return ret;
		
	}
	
	
	private String RequestLabels() 
	{
		String sRet = "";
		
		try { 
			ResetDataBuffer();
			sRet = XMLHeader();
			sRet += "<LABELS>\r\n";
			sRet += "</LABELS>\r\n";
			sRet += XMLFooter();
		} catch (Exception e)
		{
			Log.i("RequestLabels", e.getMessage());
		}
		return sRet;
		
	}
	

	
	
	
	
	
	// Clear the data buffer and receive state
    private void ResetDataBuffer()
    {
    	_StillReceiving = false;
    	_DataIn = "";
    }
    		
    private String XMLHeader() 
    {
    	String header="";
    	header= "<?xml version='1.0' encoding='utf-8' ?>\r\n<data>\r\n";
    	return (header);
    }
    
    private String XMLFooter() 
    {
    	String footer ="";
    	footer= "</data>";
    	return (footer);
    }
    
    private String AddTag(String sTag,String sValue)
    {
    	String sRet = "";
    	sRet = "<" + sTag.toUpperCase() + ">" + sValue + "</" + sTag.toUpperCase() + ">\r\n";
    	return sRet;
    }
    
    public void GetLabelsFromServer()
    {
		//SendData(RequestLabels());

        String TCPString = "";
        TCPString = RequestLabels();
        Log.v("NetworkTask", "Sending data: "+TCPString);

        byte[]sndData = TCPString.getBytes();

        byte[] data = mUtils.MakePacket(sndData, (byte)CommandType.OTHERCOMMAND.getVal(), sndData.length);
        SendPacket(data);
    }
    
    
    public void GetEngsFromServer() {
        RequestEngineerList();
    }
    
	  static void appendLogAsync(String text) {     
		    File logFile = new File( Environment.getExternalStorageDirectory(),"jtmobasync.log");   
		    if (!logFile.exists()) { 
		    	try {
		    		logFile.createNewFile();
		    	}        
		    	catch (IOException e)
		    	{          
		    		// TODO Auto-generated catch block          
		    		e.printStackTrace();
		    	} 
		    }  
			try {    
				//BufferedWriter for performance, true to set append to file flag    
			    BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));     
			    buf.append(text + "\r\n");   
			    buf.newLine();   
			    buf.close(); 
			} catch (IOException e)   
			{      
				// TODO Auto-generated catch block       
				e.printStackTrace();  
			}
		}


    public String ConstructImageTransferHeader(String jobNumber,String fileName,Long imgDateValue)
    {
        String sRet = "";
        Date imgDate = new Date(imgDateValue);
        try {

            sRet = XMLHeader();
            sRet += "<UPLOADIMG>";
            sRet += AddTag("JOBNO",jobNumber);
            sRet += AddGeo();
            sRet += AddTag("ENGNAME", JTApplication.getInstance().GetApplicationManager().getMobileUserName());
            sRet += AddTag("IMGDATE", imgDate.toString());
            sRet += AddTag("UN", JTApplication.getInstance().GetApplicationManager().getMobileUserName());
            sRet += AddTag("PW", JTApplication.getInstance().GetApplicationManager().getMobilePassword());
            sRet += AddTag("FN", fileName);
            sRet += "<IMG>";
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return sRet;

    }

    public String ConstructImageTransferFooter()
    {
        String sRet = "";
        sRet = "</IMG>";
        sRet += "</UPLOADIMG>";
        sRet += XMLFooter();

        return sRet;
    }

    public String ConstructImageTransfer(String jobNumber,String fileName,String imgData,Long imgDateValue)
	{
		String sRet = "";
        Date imgDate = new Date(imgDateValue);
		try {

			sRet = XMLHeader();
			sRet += "<UPLOADIMG>";
            sRet += AddTag("JOBNO",jobNumber);
			sRet += AddGeo();
            sRet += AddTag("ENGNAME", JTApplication.getInstance().GetApplicationManager().getMobileUserName());
            sRet += AddTag("IMGDATE", imgDate.toString());
            sRet += AddTag("UN", JTApplication.getInstance().GetApplicationManager().getMobileUserName());
            sRet += AddTag("PW", JTApplication.getInstance().GetApplicationManager().getMobilePassword());
            sRet += AddTag("FN",fileName);
            sRet += AddTag("IMG",imgData);
            sRet += "</UPLOADIMG>";
            sRet += XMLFooter();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return sRet;
	}

	  public void RequestEngineerList()
	  {
		  try {
			  String TCPString = "";
			  TCPString = ConstructRequestEngineerList();

              byte[]sndData = TCPString.getBytes();

              byte[] data = mUtils.MakePacket(sndData, (byte)CommandType.OTHERCOMMAND.getVal(), sndData.length);
              SendPacket(data);

			  //SendDataToNetwork(TCPString);
		  } catch (Exception e)
		  {
			  e.printStackTrace();
		  }
	  }
	  
	  
	  private String ConstructRequestEngineerList()
		{
			String ret = "";
			ResetDataBuffer();
			
			ret = XMLHeader();
			ret += "<LISTSTAFF>\r\n";
	        ret += "</LISTSTAFF>\r\n";
	        ret += XMLFooter();		
			
			return ret;
		}


    public String PrepareJobXMLForUploading()
    {
    	String ret = "";
    	try {
	    	C_JTJob job = JTApplication.getInstance().GetApplicationManager().GetloadedJob();
	    	
	    	
	    	ret = XMLHeader();
	    	ret += "<UPDATE>\r\n";
	    	
	    	ret += AddGeo();
	        ret += AddTag("UN", JTApplication.getInstance().GetApplicationManager().getMobileUserName());
	        ret += AddTag("PW", JTApplication.getInstance().GetApplicationManager().getMobilePassword());
	    	
	    	ret += AddTag("JOBNO", job.getJobNo());
	    	ret += AddTag("JOBDATE3",JTApplication.getInstance().GetDatabaseManager().fromEpochStringToLongDate(job.getJobDate3()));
	    	ret += AddTag("JOBDATE6",JTApplication.getInstance().GetDatabaseManager().fromEpochStringToLongDate(job.getJobDate6()));
	    	ret += AddTag("RECALLREASON",job.getRecallReason());
	    	ret += AddTag("COMPLETED",job.getJobCompleted().toString());
	    	
	    	ret += AddTag("ENGFINISHED",job.getEngFinished().toString());
	    	ret += AddTag("WORKSCARRIEDOUT",job.getWorksCarriedOut());
	    	ret += AddTag("CAUSEOFPROBLEM",job.getCauseOfProblem());
	    	
	    	
	    	ret += AddTag("MOBENG",JTApplication.getInstance().GetApplicationManager().getDeviceAssignedEngineerName());
	    	
	    	ret += AddTag("SIG",job.getSignature().getSignature());
	    	ret += AddTag("SIGPRINT",job.getSignature().getSurname());
	    	// ret += AddTag("SIGDATE",job.getSignature().getDateSigned());
	    	
	    	ret += AddTag("SKETCH",job.getSketch().getSignature());

	    	
	    	String myTime;
	    	
	    	ret += "<TIMESHEET>\r\n";
	    	for (int i = 0; i < job.getJobTimes().size(); i++) {
	    		C_JobTimes timesheet = job.getJobTimes().get(i);
	    		
		    	ret += "<TIMECARD>\r\n";
		    	
		    	ret += AddTag("TC_DATE",JTApplication.getInstance().GetDatabaseManager().fromEpochStringToLongDate(timesheet.getEventDate()));
		    	ret += AddTag("TC_STARTTIME",JTApplication.getInstance().GetDatabaseManager().fromEpochStringToTime(timesheet.getStartTime()));
		    	ret += AddTag("TC_ENDTIME",JTApplication.getInstance().GetDatabaseManager().fromEpochStringToTime(timesheet.getEndTime()));
	    		ret += AddTag("TC_RECID", Long.toString(timesheet.getRecordID()));
	    		ret += AddTag("TC_ENGINEER", Long.toString(timesheet.getEngineerID()));
	    		ret += AddTag("TC_JOBNO",timesheet.getJobNo());
	    		ret += AddTag("TC_TYPE",timesheet.getTimeType().toString());
	    		ret += AddTag("TC_ENGNAME",timesheet.getEngName());
	    		ret += AddTag("TC_VALUE", Double.toString(timesheet.getEntryValue()));
		    	ret += "</TIMECARD>\r\n";
	    	}
	    	ret += "</TIMESHEET>\r\n";
	    	
	    	ret += "<PARTS_REQ>\r\n";
	    	for (int i = 0; i < job.getPartsRequired().size(); i++) {
	    		C_PartsRequired partsrequired = job.getPartsRequired().get(i);
	    		
		    	ret += "<PARTS>\r\n";
	    		ret += AddTag("PR_QTY",partsrequired.getQty().toString());
	    		ret += AddTag("PR_DESC",partsrequired.getDescription());
	    		ret += AddTag("PR_INTPTNO",partsrequired.getOurPartNo());
	    		ret += AddTag("PR_SUPPLIER",partsrequired.getSupplierName());
	    		ret += AddTag("PR_SUPPPTNO",partsrequired.getSupplierPartNo());
	    		ret += AddTag("PR_ENG",partsrequired.getEngineer());
		    	ret += "</PARTS>\r\n";
	    	}
	    	ret += "</PARTS_REQ>\r\n";
	    	
	    	ret += "<PARTS_USED>\r\n";
	    	for (int i = 0; i < job.getPartsUsed().size(); i++) {
	    		C_PartsUsed partsused= job.getPartsUsed().get(i);
	    		
		    	ret += "<PARTS>\r\n";
	    		ret += AddTag("PU_QTY",partsused.getQty().toString());
	    		ret += AddTag("PU_DESC",partsused.getDescription());
	    		
	    		// If left blank the materials will not be displayed within Job Tracker.
	    		if (partsused.getPartNo() == "") {
	    			ret += AddTag("PU_PARTNO","MANUAL");
	    		} else {
	    			ret += AddTag("PU_PARTNO",partsused.getPartNo());
	    		}
	    		ret += AddTag("PU_UPRICE",partsused.getUnitPrice().toString());
		    	ret += "</PARTS>\r\n";
	    	}
	    	ret += "</PARTS_USED>\r\n";
	    	
	    	
	    	ret += "<PAY_RECEIPT>\r\n";
	    	for (int i = 0; i < job.getPaymentReceipt().size(); i++) {
	    		C_PaymentReceipt paymentreceipt= job.getPaymentReceipt().get(i);
	    		
		    	ret += "<RECEIPT>\r\n";
	    		ret += AddTag("RE_AMOUNT",paymentreceipt.getAmount().toString());
	    		ret += AddTag("RE_PAYTYPE",paymentreceipt.getPayType());
	    		ret += AddTag("RE_RECNO",paymentreceipt.getReceiptNo());
	    		ret += AddTag("RE_DATETAKEN",paymentreceipt.getDateTaken());
	    		ret += AddTag("RE_ENGNAME",paymentreceipt.getEngineerName());
	    		ret += AddTag("RE_DATEINOFF",paymentreceipt.getDateInOffice());
	    		ret += AddTag("RE_RECBY",paymentreceipt.getReceivedBy());
	    		ret += AddTag("RE_DESC",paymentreceipt.getDescription());
		    	ret += "</RECEIPT>\r\n";
	    	}
	    	ret += "</PAY_RECEIPT>\r\n";
	    	
	    	
	    	ret += "<FIREHYDRANTS>\r\n";
	    	for (int i = 0; i < job.getFireHydrant().size(); i++) {
	    		C_FireHydrant firehydrant = job.getFireHydrant().get(i);
	    		
		    	ret += "<HYDRANT>\r\n";
	    		ret += AddTag("FH_RECORDID",firehydrant.getJTRecordID().toString());
	    		ret += AddTag("FH_ADD",firehydrant.getSiteAddress());
	    		ret += AddTag("FH_PC",firehydrant.getSitePostCode());
	    		ret += AddTag("FH_TD",firehydrant.getTestDate().toString());
	    		ret += AddTag("FH_ENG",firehydrant.getEngineer());
	    		ret += AddTag("FH_HL",firehydrant.getHydrantLocation());
	    		ret += AddTag("FH_REF",firehydrant.getNumber().toString());
	    		ret += AddTag("FH_D1",firehydrant.getDetails(0));
	    		ret += AddTag("FH_D2",firehydrant.getDetails(1));
	    		ret += AddTag("FH_D3",firehydrant.getDetails(2));
	    		ret += AddTag("FH_D4",firehydrant.getDetails(3));
	    		ret += AddTag("FH_D5",firehydrant.getDetails(4));
	    		ret += AddTag("FH_D6",firehydrant.getDetails(5));
	    		ret += AddTag("FH_D7",firehydrant.getDetails(6));
	    		ret += AddTag("FH_D8",firehydrant.getDetails(7));
	    		ret += AddTag("FH_D9",firehydrant.getDetails(8));
	    		ret += AddTag("FH_D10",firehydrant.getDetails(9));
	    		ret += AddTag("FH_D11",firehydrant.getDetails(10));
	    		ret += AddTag("FH_D12",firehydrant.getDetails(11));
	    		ret += AddTag("FH_JN",firehydrant.getJobNo());
		    	ret += "</HYDRANT>\r\n";
	    	}
	    	ret += "</FIREHYDRANTS>\r\n";
	    	

	    	ret += "<RISERSERVICES>\r\n";
	    	for (int i = 0; i < job.getRiserServices().size(); i++) {
	    		C_RiserService  riserservice = job.getRiserServices().get(i);
	    		
		    	ret += "<RSERVICE>\r\n";
	    		ret += AddTag("RS_RECORDID",riserservice.getJTRecordID().toString());
	    		ret += AddTag("RS_NO",riserservice.getRiserNumber().toString());
	    		ret += AddTag("RS_LOC",riserservice.getRiserLocation());
	    		ret += AddTag("RS_JOBNO",riserservice.getJobNo());

	    		ret += AddTag("RS_INLETKEY",riserservice.getInLetKey());
	    		ret += AddTag("RS_OUTLETKEY",riserservice.getOutLetKey());

	    		
	    		ret += AddTag("RS_EESC1",riserservice.getExtServiceCheck(0));		// External Equipment Service Check (1 - 6)
	    		ret += AddTag("RS_EESC2",riserservice.getExtServiceCheck(1));
	    		ret += AddTag("RS_EESC3",riserservice.getExtServiceCheck(2));
	    		ret += AddTag("RS_EESC4",riserservice.getExtServiceCheck(3));
	    		ret += AddTag("RS_EESC5",riserservice.getExtServiceCheck(4));
	    		ret += AddTag("RS_EESC6",riserservice.getExtServiceCheck(5));
	    		
	    		ret += AddTag("RS_EED1",riserservice.getExtDetails(0));				// External Equipment Details (1 - 6)
	    		ret += AddTag("RS_EED2",riserservice.getExtDetails(1));				
	    		ret += AddTag("RS_EED3",riserservice.getExtDetails(2));				
	    		ret += AddTag("RS_EED4",riserservice.getExtDetails(3));				
	    		ret += AddTag("RS_EED5",riserservice.getExtDetails(4));				
	    		ret += AddTag("RS_EED6",riserservice.getExtDetails(5));				
	    		
	    		ret += AddTag("RS_EES1",riserservice.getExtStatus(0));				// External Equipment Status (1 - 6)
	    		ret += AddTag("RS_EES2",riserservice.getExtStatus(1));				
	    		ret += AddTag("RS_EES3",riserservice.getExtStatus(2));				
	    		ret += AddTag("RS_EES4",riserservice.getExtStatus(3));				
	    		ret += AddTag("RS_EES5",riserservice.getExtStatus(4));				
	    		ret += AddTag("RS_EES6",riserservice.getExtStatus(5));				
	    		
	    		ret += AddTag("RS_IESC1",riserservice.getIntServiceCheck(0));		// Internet Equipment Service Check (1 - 5)
	    		ret += AddTag("RS_IESC2",riserservice.getIntServiceCheck(1));		
	    		ret += AddTag("RS_IESC3",riserservice.getIntServiceCheck(2));		
	    		ret += AddTag("RS_IESC4",riserservice.getIntServiceCheck(3));		
	    		ret += AddTag("RS_IESC5",riserservice.getIntServiceCheck(4));		
	    		
	    		ret += AddTag("RS_IED1",riserservice.getIntDetails(0));				// Internet Equipment Details Check (1 - 5)
	    		ret += AddTag("RS_IED2",riserservice.getIntDetails(1));				
	    		ret += AddTag("RS_IED3",riserservice.getIntDetails(2));				
	    		ret += AddTag("RS_IED4",riserservice.getIntDetails(3));				
	    		ret += AddTag("RS_IED5",riserservice.getIntDetails(4));
	    		
	    		ret += AddTag("RS_AIR1",riserservice.getAir(0));				
	    		ret += AddTag("RS_AIR2",riserservice.getAir(1));				
	    		ret += AddTag("RS_WATER1",riserservice.getWater(0));				
	    		ret += AddTag("RS_WATER2",riserservice.getWater(1));
	    		
	    		ret += AddTag("RS_CC1",riserservice.getCompletionChecked(0));		// Completion Checked (1 - 3)
	    		ret += AddTag("RS_CC2",riserservice.getCompletionChecked(1));		
	    		ret += AddTag("RS_CC3",riserservice.getCompletionChecked(2));		
	    		
	    		ret += AddTag("RS_COM",riserservice.getComments());		
	    		ret += AddTag("RS_REM",riserservice.getRemedialWorks());	
	    		ret += AddTag("RS_STATUS",riserservice.getOverAllStatus());
	    		
	    		ret += AddTag("RS_PUMPSIG",riserservice.getEngSigPump().getSignature());		
	    		ret += AddTag("RS_PUMPNAME",riserservice.getEngSigPump().getSurname());		
	    		ret += AddTag("RS_PUMPDATE",JTApplication.getInstance().GetDatabaseManager().fromEpochStringToLongDate(riserservice.getEngSigPump().getDateSigned()));
	    		
	    		ret += AddTag("RS_STACKSIG",riserservice.getEngSigStack().getSignature());		
	    		ret += AddTag("RS_STACKNAME",riserservice.getEngSigStack().getSurname());		
	    		ret += AddTag("RS_STACKDATE",JTApplication.getInstance().GetDatabaseManager().fromEpochStringToLongDate(riserservice.getEngSigStack().getDateSigned()));
	    		
	    		ret += AddTag("RS_CUSTSIG",riserservice.getCustSig().getSignature());		
	    		ret += AddTag("RS_CUSTNAME",riserservice.getCustSig().getSurname());		
	    		ret += AddTag("RS_CUSTDATE",JTApplication.getInstance().GetDatabaseManager().fromEpochStringToLongDate(riserservice.getCustSig().getDateSigned()));
	    		
	    		
		    	ret += "</RSERVICE>\r\n";
	    	}
	    	ret += "</RISERSERVICES>\r\n";


			ret += AddTag("PAPERWORK",job.getPaperWork());
	    	
	    	
	    	
	    	ret += "</UPDATE>\r\n";
			ret += XMLFooter();
	    	
	    	// translate £ to XML char.
	    	ret = ret.replaceAll("£", "&#163");
    	} catch (Exception e)
    	{
    		Log.i("NetworkTask.PrepareJobXMLForUploading Exception:",e.getMessage());
    	}
    	
    	return ret;
    }
    


}

