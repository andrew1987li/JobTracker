package co.uk.sbsystems.android.jobtrackermobile;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class SearchJobs extends ListActivity {
	
	
	 static long mStartTime = 0L;
	 static Handler mHandler = new Handler();
	
	static ProgressDialog progress;
	static Context myContext;
	
	 TextView txtSearch;
	 CheckBox cbNewOnly;
	 CheckBox cbClosedOnly;
	 CheckBox cbLocal;
	 TextView txtDays;
	 TextView labJobCount;

    int progressValue;
	
	 ImageView imgOnline;				
	
	boolean bOnline = JTApplication.getInstance().GetApplicationManager().getConnectedStatus();
	
	static ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
     
	static SimpleAdapter adapter = null;

    Handler update_download_job;

    AlertDialog.Builder srchDlg;
     
	public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);

        JTApplication.getInstance().setSearchJobsInstance(this);

	    setContentView(R.layout.searchjobs);
	    
	    myContext = SearchJobs.this;

        update_download_job = new Handler();
	    
	     adapter= new SimpleAdapter(this,list,R.layout.jobsearchrowlayout,
		     		new String[] {"jobno","client","site","pcode","duedate","tel"},
		     		new int[] {R.id.txtJobNo,R.id.txtClient,R.id.txtSiteAddress,R.id.txtPCode,R.id.txtDueDate,R.id.txtTelephone}
		     		);
	    
	    
	    imgOnline = (ImageView)findViewById(R.id.imgConnectStatus);
	    txtSearch = (TextView)findViewById(R.id.txtSearch);     
	    cbNewOnly = (CheckBox)findViewById(R.id.cbNew);     
	    cbClosedOnly = (CheckBox)findViewById(R.id.cbClosed);     
	    cbLocal = (CheckBox)findViewById(R.id.cbLocal ); 
	    txtDays= (TextView)findViewById(R.id.txtDays);
	    labJobCount = (TextView)findViewById(R.id.labJobCount);
	    
	    UpdateOnlineIcon();
	    
	    // Search Button
	    ImageButton btnGetJobs = (ImageButton) findViewById(R.id.butSearch);
        btnGetJobs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				// Tell background process which object to update.
				// NetworkTask.ProgressBarAsyncTask(myContext ,"Searching Jobs.");
				
				//int iDays = 0;
				Integer matchingCachedRecords =0;
				
				
				try {
					InputMethodManager imm = (InputMethodManager)getSystemService(
					Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(txtDays.getWindowToken(), 0);		
					
				} catch (Exception e) {
					Log.i("SearchJobs.java 111", e.getMessage());
				}

				// Clear existing list.
				try {
					list.clear();
				} catch (Exception e) {
					Log.i("SearchJobs.java 116", e.getMessage());
				} 

				try {
					boolean SearchLocalCache = cbLocal.isChecked();
					
					// Attempt to reconnect again just in case.  This takes care of already being connected as well.
					JTApplication.getInstance().GetApplicationManager().ConnectToNetwork();

                    if (JTApplication.getInstance().GetApplicationManager().getAttemptingToConnect())
                    {
                        DoToast("Currently trying to reconnect to server, please try again in a few moments.");
                        return;
                    }
					// The following is failing as its being executed before the connection thread has completed.
					UpdateOnlineIcon();


                    Boolean NewOnly = false;
                    Boolean ClosedOnly = false;
                    String SearchStr = "";
                    Boolean UseDueDate = false;
                   //String AssignedTo = "";
                    Integer iDays = 0;
                    //boolean NewOnly = cbNewOnly.isChecked();
                    //boolean ClosedOnly = cbClosedOnly.isChecked();
                    String AssignedTo = JTApplication.getInstance().GetApplicationManager().getDeviceAssignedEngineerName();

					if((JTApplication.getInstance().getTCPManager().AreWeOnline() == true) &&  (SearchLocalCache == false)) {

						/*progress=new ProgressDialog(myContext);
						progress.setMessage("Load List Of Jobs");
						progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
						progress.setIndeterminate(false);
                        progress.setMax(100);
						progress.setProgress(70);
						progress.show();
*/
						//JTApplication.getTCPManager().ProgressBarAsyncTask(SearchJobs.this, "Searching Jobs.");


                       // //new C_RequestJobs().execute(txtSearch.getText().toString(),txtDays.getText().toString(),Boolean.toString(cbNewOnly.isChecked()),Boolean.toString(cbClosedOnly.isChecked()));
						//new GetJobList().execute(txtSearch.getText().toString());

                        iDays = Integer.parseInt(txtDays.getText().toString());
                        NewOnly = Boolean.valueOf(Boolean.toString(cbNewOnly.isChecked()));
                        ClosedOnly = Boolean.valueOf(Boolean.toString(cbClosedOnly.isChecked()));
                        SearchStr = txtSearch.getText().toString();


                        NetworkTask nettask = JTApplication.getInstance().getTCPManager();


						//Create ProgressBar for downlaoding.
                        nettask.nowContext = SearchJobs.this;
                        nettask.nowState=true;
                        nettask.createProgressBar();


                        // This sends the TCP/IP XML request for the list of jobs.
                        // Tell the NetworkTask the context of this UI at this point.
                        // Set the public context property in the NetworkTask here.
                        JTApplication.getInstance().getTCPManager().RequestListJobs(iDays, NewOnly, SearchStr, ClosedOnly, AssignedTo, UseDueDate);

					} else {
						try {
							iDays =  Integer.parseInt(txtDays.getText().toString());		// This can throw an exception if NULL ("")
						} catch (Exception e) {
							iDays =0;
						}

						
						if (SearchLocalCache == true) {
							// Change this to show jobs pending uploading.'
							progress = ProgressDialog.show(SearchJobs.this, "Loading Jobs Awaiting Uploading From Local Database", "Please Wait",false,true);
							matchingCachedRecords = LoadJobListFromCache(iDays, NewOnly, txtSearch.getText().toString(), ClosedOnly, AssignedTo);
							if (matchingCachedRecords == 0) {
								DoToast("There are no jobs awaiting uploading.");
								
							} else {
								DoToast("Found " + matchingCachedRecords + " awaiting uploading.");
							}
							
						} else {
                            progress = ProgressDialog.show(SearchJobs.this, "Loading Jobs From Local Database", "Please Wait",false,true);
							matchingCachedRecords = LoadJobListFromCache(iDays, NewOnly, txtSearch.getText().toString(), ClosedOnly, AssignedTo);
							DoToast("Found " + matchingCachedRecords + " awaiting uploading.");
						}
						
						if (progress.isShowing())
                            progress.dismiss();
						
						setListAdapter(adapter);
					}
				} catch (Exception e) {
					Log.i("Searchjobs", e.getMessage());
				}
			}
		});
        
        // Can only update the adapter outside threads.
        try {
    		setListAdapter(adapter);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	protected void onListItemClick(ListView parent, View view, int position, long id) {
		String jobNo = "";
		boolean bJobAvailable = false;
		try
		{

			String item = (String) getListAdapter().getItem(position).toString();

			try {
				String params[] = item.split("jobno=");
				if (params[1] != "")
	        	{
					String myJobNo[] = params[1].split(",");
	        		String sJobNo = myJobNo[0].replace("}", "");
	        		jobNo = sJobNo;
	        	}
			} catch (Exception e) {
				// Something wrong, can't parse the job number.
				jobNo ="";
			}
			
			if(JTApplication.getInstance().getTCPManager().AreWeOnline() == false) {
				// Is this job available in the Cache?
				if (JTApplication.getInstance().GetDatabaseManager().isJobCached(jobNo) == false ) {
					DoToast("You are offline and job " + jobNo + " is not avaialble locally.");
					bJobAvailable = false;
				} else {
					// Call the jobs screen
					bJobAvailable = true;
					//DoToast("Loading Job " + jobNo + " from local database, Please Wait.");
					Intent intent = new Intent(this, activity2.class);
					intent.putExtra("jobno", item);
					startActivity(intent);
				}
			
			} else {
				try {
					// Call the jobs screen
					DoToast("Loading Job " + jobNo + ", Please Wait.");
					Intent intent = new Intent(this, activity2.class);
					intent.putExtra("jobno", item);
					startActivity(intent);
				} catch (Exception e) {
					Log.e("LoadJob", "Loading job From Search Screen Error:" + e.getMessage());
				}
			}
		} catch(Exception e)
		{
			Log.i("onListItemClick", "Exception:" + e.getMessage());
		}
	}
	

	private void UpdateOnlineIcon()
	{
		if (JTApplication.getInstance().GetApplicationManager().getConnectedStatus()) {
	    	imgOnline.setImageResource(R.drawable.connected);
	    }
	    else {
	    	imgOnline.setImageResource(R.drawable.disconnected);
	    }		
	}
	
	// Purpose:
	// In: The search string to look for. (txtSearch)
	// Out: Void.
	// Comments: This runs in a seperate thread. 
	// 			Not sure why we don't pass the txtDay here as well?
	/*private void RequestJobsList(String SearchStr)
	{
		Log.v("SearchJobs", "Starting search for jobs matching "+SearchStr);
		
		int iDays = 0;
		Integer matchingCachedRecords =0;
		try {
			try {
				iDays =  Integer.parseInt(txtDays.getText().toString());		// This can throw an exception if NULL ("")
			} 
			catch (Exception e)
			{
				iDays = 0;
			}
			
			boolean NewOnly = cbNewOnly.isChecked();
			boolean ClosedOnly = cbClosedOnly.isChecked();
			String AssignedTo = JTApplication.GetApplicationManager().getDeviceAssignedEngineerName();
			if (AssignedTo==null) {
				AssignedTo = "";
			}
			boolean UseDueDate = JTApplication.getSettings().getUseDueDate();
			// Integer UseDueDate = JTApplication.GetApplicationManager().getUseDueDate();
			
			
			JTApplication.GetApplicationManager().DoNotClearjobSummary(false);
			
			if(JTApplication.getTCPManager().AreWeOnline() == true) {
				// We're online, request data.
				JTApplication.getTCPManager().RequestListJobs(iDays, NewOnly, SearchStr, ClosedOnly, AssignedTo, UseDueDate);
				// Have to do this in the UI thread.
				// labJobCount.setText("");
			}
			
			//dialog.dismiss();
		} catch (Exception e)
		{
			Log.e("SearchJobs.RequestJobsList", "Exception", e);
			
		}
			
			
	}*/
	
	/*private void LoadJobListFromCache(String SearchStr)
	{
		
	}*/
	
	
	static void UpdateScreen(String DataIn) {
		Integer JobCount=0;

			try {
				JobCount = DoWork(DataIn);
				DoShortToast(JobCount + " matching jobs found.");
			} catch (Exception e) {
				Log.e("LoadJob", "Update Screen Failed: Expection:" + e.getMessage());
			}
	}
	
	private static void DoToast(String msg)
	{
		try {
			Toast.makeText(SearchJobs.myContext,msg,Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	
	private static void DoShortToast(String msg)
	{
		try {
			Toast.makeText(SearchJobs.myContext,msg,Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// Returns the number of Matching Jobs
	private static Integer DoWork(String DataIn)
	{
		Document doc = null;
		String JobNo,ClientName,SiteAddress,PostCode,DueDate,Telephone;
		Integer JobCount = 0;
		
		
		// String sXML = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\r\n <!--  Edited by XMLSpy  -->\r\n<note>\r\n<to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body>  </note>";
		
		try {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			// Create string read to parse XML from string.
			StringReader reader = new StringReader( DataIn );
			InputSource inputSource = new InputSource( reader );
			doc = db.parse(inputSource);
			doc.getDocumentElement().normalize();
			reader.close();
			
			} catch (IOException ioe) {
				System.out.println("I/O Exception: " + ioe.getMessage());
			} catch (ParserConfigurationException pce) {
				System.out.println("ParserConfigurationException: " + pce.getMessage());
			} catch (SAXException se) {
				System.out.println("SAXException: " + se.getMessage());
			}


		
		try 
		{
			// Remove previous entries.
			list.clear();
			
			
			NodeList nodeLst = doc.getElementsByTagName("JOB");
/*
			if (nodeLst.getLength() == 0) 
				DoToast("No Jobs Found");
*/
			
			for (int s = 0; s < nodeLst.getLength(); s++) {
				JobCount++;
			    Node fstNode = nodeLst.item(s);
			    
			    if (fstNode.getNodeType() == Node.ELEMENT_NODE) 
			    {
			  
			    	  Element fstElmnt = (Element) fstNode;
			    	  
			          NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("JOBNO");
			          Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
			          NodeList fstNm = fstNmElmnt.getChildNodes();
			          JobNo = fstNm.item(0).getNodeValue();
			          // System.out.println("JOBNO : "  + ((Node) fstNm.item(0)).getNodeValue());
			          
			          NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("CLIENT_NAME");
			          Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
			          NodeList lstNm = lstNmElmnt.getChildNodes();
			          try {
			          	ClientName = lstNm.item(0).getNodeValue();
			          } catch (Exception e)
			          {	
			        	  ClientName = "";
			          }
			          // System.out.println("CLIENT_NAME : " + ((Node) lstNm.item(0)).getNodeValue());
			          
			          NodeList siteAddressElmnLst = fstElmnt.getElementsByTagName("SITE_ADD");
			          Element siteAddressElmnt = (Element) siteAddressElmnLst.item(0);
			          NodeList siteAddress = siteAddressElmnt.getChildNodes();
			          try {
			        	  SiteAddress = siteAddress.item(0).getNodeValue();
			          } catch (Exception e)
			          {
			        	 SiteAddress = "";
			          }
			          
			          
			          NodeList pCodeElmnLst = fstElmnt.getElementsByTagName("PCODE");
			          Element pCodeElmnt = (Element) pCodeElmnLst.item(0);
			          if (pCodeElmnt == null) {
			        	  PostCode = "";
			          } else {
				          NodeList pcode = pCodeElmnt.getChildNodes();
				          try {
				        	  PostCode = pcode.item(0).getNodeValue();
				          } catch (Exception e)
				          {
				        	  PostCode = "";
				          }
			          }
			          
			          
			          
			          NodeList dueDateElmnLst = fstElmnt.getElementsByTagName("JOBDATE0");
			          Element dueDateElmnt = (Element) dueDateElmnLst.item(0);
			          NodeList dueDate= dueDateElmnt.getChildNodes();
			          try
			          {
			        	  DueDate= dueDate.item(0).getNodeValue();
			          } catch (Exception e)
			          {
			        	  DueDate = "";
			          }
			          
			          
			          NodeList telElmnLst = fstElmnt.getElementsByTagName("TEL");
			          Element telElmnt = (Element) telElmnLst.item(0);
			          if (telElmnt == null) {
			        	  Telephone = "";
			          } else {
				          NodeList tel = telElmnt.getChildNodes();
				          try {
				        	  Telephone = tel.item(0).getNodeValue();
				          } catch (Exception e)
				          {
				        	  Telephone = "";
				          }
			          }
			          
			          

			          //System.out.println("JOBNO : "  + ((Node) siteAddress.item(0)).getNodeValue());
			          
			          
			          AddToHashMap(JobNo,ClientName,SiteAddress,PostCode,DueDate,Telephone);
			    }
			}
			
			adapter.notifyDataSetChanged();

			
			// Log.i("Listing Jobs Finished", "--- FINISHED ---");
			//labJobCount.setText("Matching Jobs:" + JobCount.toString());

		}
		catch (Exception e)
		{
			System.out.println("Exception: " + e.getMessage() );
		}
		
		return JobCount;
		
	}
	
	 static void AddToHashMap(String JobNo,String Client,String SiteAddress, String PCode,String DueDate,String Telephone)
	{
		HashMap<String, String> temp = new HashMap<String, String>();
		
		try {
			temp.put("jobno",JobNo);
			temp.put("client",Client);
			temp.put("site",SiteAddress);
			temp.put("pcode", PCode);
			temp.put("duedate",DueDate);
			temp.put("tel",Telephone);
			list.add(temp);
			
			// Only write if we're online otherwise we'll be writing already cached data.
			if(JTApplication.getInstance().getTCPManager().AreWeOnline() == true) {
				JTApplication.getInstance().GetDatabaseManager().SaveJobSummary(JobNo,Client,SiteAddress,PCode,DueDate,Telephone);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	 
	 
	 int LoadJobListFromCache(int iDays,boolean bNewOnly,String sSearch,boolean bClosed,String AssignedTo)
	 {
		 int matchingRecords= 0;
		 String SQL;
		 SQLiteDatabase mydb;
		 String JobNo;
		 String Client;
		 String SiteAdd;
		 String PCode;
		 String JobDate;
		 String Telephone;
		 Long myDate;			// Temp storage for converting from dd/mm/yyyy to epoch
		 
		 try {
			 
			 // Showing jobs pending uploading.
			 SQL = "SELECT * FROM jobs WHERE PendingUpload<>0 Order By JobNo ";
			 mydb = JTApplication.getInstance().GetDatabaseManager().GetDB();
			 Cursor cursor = mydb.rawQuery(SQL, null);
			 if (cursor.moveToFirst()) {
				  do {
					 ++matchingRecords;
					 JobNo = cursor.getString(cursor.getColumnIndex("JobNo"));
					 Client = cursor.getString(cursor.getColumnIndex("Comp_Name"));
					 SiteAdd = cursor.getString(cursor.getColumnIndex("Site_Address"));
					 PCode = cursor.getString(cursor.getColumnIndex("Site_PCode"));
					 myDate = cursor.getLong(cursor.getColumnIndex("jobdate1"));
					 JobDate = JTApplication.getInstance().GetDatabaseManager().fromEpochStringToDate(myDate.toString());
					 Telephone = cursor.getString(cursor.getColumnIndex("Site_Tel")) + " ," + cursor.getString(cursor.getColumnIndex("Site_Mobile"));
					 AddToHashMap(JobNo,Client,SiteAdd,PCode,JobDate,Telephone);
				  } while (cursor.moveToNext());
			 }
			 cursor.close();
			 
		 } catch (Exception e) {
			 Log.i("LoadJobListFromCache", e.getMessage());
		 }
			 
			 
/*			 SQL = "SELECT * FROM JobSearch WHERE PendingUpload<>0 Order By JobNo ";
			 mydb = JTApplication.GetDatabaseManager().GetDB();
			 Cursor cursor = mydb.rawQuery(SQL, null);
			 if (cursor.moveToFirst()) {
				  do {
					 ++matchingRecords;
					 JobNo = cursor.getString(cursor.getColumnIndex("JobNo"));
					 Client = cursor.getString(cursor.getColumnIndex("Client"));
					 SiteAdd = cursor.getString(cursor.getColumnIndex("SiteAdd"));
					 PCode = cursor.getString(cursor.getColumnIndex("PCode"));
					 myDate = cursor.getLong(cursor.getColumnIndex("JobDate"));
					 JobDate = JTApplication.GetDatabaseManager().fromEpochStringToDate(myDate.toString());
				 
					 AddToHashMap(JobNo,Client,SiteAdd,PCode,JobDate);
				  } while (cursor.moveToNext());
			 }
			 cursor.close();
			 
		 } catch (Exception e) {
			 Log.i("LoadJobListFromCache", e.getMessage());
		 }
		 
*/		 
		 
		 
		 
		 
		 //adapter.notifyDataSetChanged();
		 
		 return matchingRecords;
	 }
	 
	 
	 int LoadJobsPendingUpload(int iDays, boolean bNewOnly,String sSearch,boolean bClosed,String AssignedTo)
	 {
		 int matchingRecords= 0;
		 String SQL;
		 SQLiteDatabase mydb;
		 String JobNo;
		 String Client;
		 String SiteAdd;
		 String PCode;
		 String JobDate;
		 String Telephone;
		 Long myDate;			// Temp storage for converting from dd/mm/yyyy to epoch
		 
		 try {
			 SQL = "SELECT * FROM jobs WHERE PendingUpload=1 Order By JobNo";
			 
			 mydb = JTApplication.getInstance().GetDatabaseManager().GetDB();
			 Cursor cursor = mydb.rawQuery(SQL, null);
			 if (cursor.moveToFirst()) {
				  do {
					 ++matchingRecords;
					 JobNo = cursor.getString(cursor.getColumnIndex("JobNo"));
					 Client = cursor.getString(cursor.getColumnIndex("Comp_Name"));
					 SiteAdd = cursor.getString(cursor.getColumnIndex("Site_Address"));
					 PCode = cursor.getString(cursor.getColumnIndex("PCode"));
					 myDate = cursor.getLong(cursor.getColumnIndex("JobDate"));
					 JobDate = JTApplication.getInstance().GetDatabaseManager().fromEpochStringToDate(myDate.toString());
					 Telephone = cursor.getString(cursor.getColumnIndex("Site_Tel")) + " , " + cursor.getString(cursor.getColumnIndex("Site_Mobile"));
					 AddToHashMap(JobNo,Client,SiteAdd,PCode,JobDate,Telephone);
				  } while (cursor.moveToNext());
			 }
			 cursor.close();
		 } catch (Exception e) {
			 Log.i("LoadJobPendingUpload", e.getMessage());
		 }
		 
		 return matchingRecords;
	 }

	static SearchJobs instance;
	public static SearchJobs getInstance()
	{

		return instance;
	}

	public void UpdateSearchStatus(Double proval)
	{
        progressValue = proval.intValue();
        progress.setProgress(progressValue);
        if(progressValue>95)       progress.dismiss();
		try {
            update_download_job.post(new Runnable() {
                @Override
                public void run() {

                    progress.setProgress(progressValue);
                    if (progressValue >= 95)
                        progress.dismiss();


                }
            });



		} catch (Exception ex)
        {

        }

	}


	 /*class GetJobList extends AsyncTask<String, Void,String> {

			ProgressDialog progress;

			@Override
			protected String doInBackground(String... params) {
				android.os.Debug.waitForDebugger();
				RequestJobsList(params[0]);
				return null;
			}

			@Override
			protected void onCancelled() {
				// TODO Auto-generated method stub
				super.onCancelled();
			}

			
			// PostExecute runs on the UI thread. i.e. we can update here. 
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				
				
				if (dialog .isShowing()) {
					dialog .dismiss();
				}
				
			}

			// onPreExecute runs on the UI thread. i.e. we can update here.
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}

			// onProgressUpdate runs on the UI thread. i.e. we can update here. 
			@Override
			protected void onProgressUpdate(Void... values) {
				super.onProgressUpdate(values);
				try {
					if (dialog.isShowing() == false) {
						ProgressDialog.show(myContext,"Loading Job,Please Wait", "Loading...",false,true).show();
					}				
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			
		}*/
	 
	 
	 
}


