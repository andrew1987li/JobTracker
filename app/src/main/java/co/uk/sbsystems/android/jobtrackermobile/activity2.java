package co.uk.sbsystems.android.jobtrackermobile;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.LabeledIntent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

// This is the activity for the Job Screen.

public class activity2 extends Activity {

    final   int teh_packetsize =1024;



    static activity2 instance;

    public static activity2 getInstance()
    {
        if (instance == null)
        {
            instance = new activity2();
        }

        return instance;
    }

	 static long mStartTime = 0L;
	 static Handler mHandler = new Handler();
	
	static ProgressDialog dialog;
	static Context mycontext1;

	public static final int SIGNATURE_ACTIVITY = 1;
	public static final int SKETCH_ACTIVITY = 2;

	public String sNumberToDial;
	
    // NetworkTask networktask;
    static TextView txtJobNo;
    
    static TextView txtSLA;
    static TextView labTimeOnSite;
    
    static TextView txtClientName;
    static TextView txtClientContact;
    static TextView txtClientTel;
    static TextView txtClientEmail;
    static TextView txtJobDetails2;
    static TextView txtJobDetails0;
    static TextView txtJobDetails1;
    static TextView txtJobDate3;
    static TextView txtCustContact0;
    static TextView txtCustContact1;
    static TextView txtCustContact11;
    static TextView txtCustContact2;
    static TextView txtCustContact3;		// Site Telephone  
    static TextView txtCustContact4;		// Site Mobile
    static TextView txtCustContact6;		// Site Email 
    static TextView txtCompletionDate;
    static TextView txtJobDetails11;
    static TextView txtEngReport;
    static CheckBox cbJobCompleted;
    
    static CheckBox cbEngFinished;
    static TextView txtWorksCarriedOut;
    static TextView txtCauseOfProblem;
    Handler updateUploadDlg;

    static TextView labJobNo;
    static TextView labClientName;
    static TextView labClientContact;
    static TextView labClientTel;
    static TextView labClientEmail;
    static TextView labJobLabel11;
    static TextView labJobLabel17;
    static TextView labJobLabel18;
    static TextView labJobLabel5;
    
    static TextView labStatus;			// This is the server TCP/IP communications status.
    
    static C_JTJob myJob;			// Job Tracker job class.
    
    
    private TextView mDateDisplay;
    private Button btnCalendar;
    private int mYear;
    private int mMonth;
    private int mDay;
    private ImageButton btnConnect;

    static final int DATE_DIALOG_ID = 0;


    //Upload image progress
    ProgressDialog dlg_uploadImg;

    ProgressDialog dlg_uploadJob;

    private final Context mycontext = activity2.this;
	 /** Called when the activity is first created. */

     public void createJobUpProgress(){
         dlg_uploadJob =  new ProgressDialog(this);
         dlg_uploadJob.setTitle("Upload Jobs");
         dlg_uploadJob.setMessage("Uploading..........");
         dlg_uploadJob.setProgressStyle(dlg_uploadImg.STYLE_HORIZONTAL);
         dlg_uploadJob.setProgress(0);
        //dlg_uploadJob.show();
     }
    
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {                
    	
    	public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) { 
    		mYear = year;                    
    		mMonth = monthOfYear;                    
    		mDay = dayOfMonth;                    
    		updateDateDisplay();                
    	}            
	};	        
    
    
    @Override
    
    
    protected Dialog onCreateDialog(int id) {   
   	 switch (id) {    
   		case DATE_DIALOG_ID:        
		return new DatePickerDialog(this,mDateSetListener,mYear, mMonth, mDay);    
   	}    
   return null;
   }
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
//    	// Stops the scroll view setting focus to the next editText. 
//		ScrollView view = (ScrollView)findViewById(R.id.widget54);
//	    view.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
//	    view.setFocusable(true);
//	    view.setFocusableInTouchMode(true);
//	    view.setOnTouchListener(new View.OnTouchListener() {
//	        @Override
//	        public boolean onTouch(View v, MotionEvent event) {
//	            v.requestFocusFromTouch();
//	            return false;
//	        }
//
//			
//	    });		        
//        
        String jobNo = "";
        
        if (getIntent() != null) {
        	Bundle extras = getIntent().getExtras();
        	try {
        		jobNo = extras.getString("jobno");
        		
            	String params[] = jobNo.split("jobno=");
            	if (params[1] != "")
            	{
            		String myJobNo[] = params[1].split(",");
            		String sJobNo = myJobNo[0].replace("}", "");
            		
            		// To maintain backward compatibility we need to check the format of the data coming across.
            		// New 01/05/15 - Added the telephone number which means the jobno is no longer last
            		// So if new format, split in comma else use exsiting method.
            		// Extract the job number/ drop last characters as its a }  
            		//String sJobNo = params[1].substring(0,params[1].length()-1);
            		jobNo = sJobNo;
            	}
        		
        	} catch(Exception e) 
        	{
        		jobNo = "";
        	}
        	
        	
        }
        
        updateUploadDlg = new Handler();
        
        
        try {
	        // this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	        
	        setContentView(R.layout.jobscreen_vert);
		   // networktask = new NetworkTask(); 		//Create initial instance so SendDataToNetwork doesn't throw an error.	
	 	//	networktask.execute();

	        // Local pointer to Job object.


            // At this point the job is not actually loaded yet.
	        myJob = JTApplication.getInstance().GetApplicationManager().GetloadedJob();
	        //myJob = JTApplication.GetApplicationManager().GetloadedJob();
	 		
	        labStatus = (TextView)findViewById(R.id.labStatus);
		    txtJobNo = (TextView)findViewById(R.id.txtJobNo);    
		    
		    txtSLA = (TextView)findViewById(R.id.txtTimeOneSite);
		    labTimeOnSite = (TextView)findViewById(R.id.labTimeOnSite);
		    txtClientName= (TextView)findViewById(R.id.txtClientName);
		    txtClientContact= (TextView)findViewById(R.id.txtClientContact);
		    txtClientTel= (TextView)findViewById(R.id.txtClientTel);
		    txtClientEmail= (TextView)findViewById(R.id.txtClientEmail);
		    txtJobDetails2= (TextView)findViewById(R.id.txtJobDetails2);
		    txtJobDetails0= (TextView)findViewById(R.id.txtJobDetails0);
		    txtJobDetails1= (TextView)findViewById(R.id.txtJobDetails1);
		    txtJobDate3= (TextView)findViewById(R.id.txtJobDate3);
		    txtCustContact0= (TextView)findViewById(R.id.txtCustContact0);
		    txtCustContact1= (TextView)findViewById(R.id.txtCustContact1);
		    txtCustContact11= (TextView)findViewById(R.id.txtCustContact11);
		    txtCustContact2= (TextView)findViewById(R.id.txtCustContact2);
		    txtCustContact3= (TextView)findViewById(R.id.txtCustContact3);
		    txtCustContact4= (TextView)findViewById(R.id.txtCustContact4);
		    txtCustContact6= (TextView)findViewById(R.id.txtCustContact6);
		    txtCompletionDate= (TextView)findViewById(R.id.txtCompletionDate);
		    txtJobDetails11= (TextView)findViewById(R.id.txtJobDetails11);
		    txtEngReport= (TextView)findViewById(R.id.txtEngReport);
		    cbJobCompleted = (CheckBox) findViewById(R.id.cbJobCompleted);
		    
		    cbEngFinished = (CheckBox) findViewById(R.id.cbEngFinished);
		    txtWorksCarriedOut = (TextView) findViewById(R.id.txtWorksCarriedOut);
		    txtCauseOfProblem = (TextView) findViewById(R.id.txtCauseOfProblem);
		    
		    
		    
		    labJobNo = (TextView)findViewById(R.id.Label1);
		    labClientName = (TextView)findViewById(R.id.Label3);
		    labClientContact = (TextView)findViewById(R.id.Label8);
		    labClientTel = (TextView)findViewById(R.id.Label9);
		    labClientEmail = (TextView)findViewById(R.id.Label10);
		    labJobLabel11  = (TextView)findViewById(R.id.Label11);
		    labJobLabel17  = (TextView)findViewById(R.id.Label17);
		    labJobLabel18  = (TextView)findViewById(R.id.Label18);
		    labJobLabel5  = (TextView)findViewById(R.id.Label5);
	        
		    
		    labStatus.setVisibility(View.INVISIBLE);
		    
	        txtClientName.requestFocus();
	        
	        btnConnect = (ImageButton)findViewById(R.id.btnConnect);
	        
	        if (JTApplication.getInstance().getSettings().getJobNumbersAreNumeric() == true) {
	        	txtJobNo.setInputType(InputType.TYPE_CLASS_NUMBER);
	        } else {
	        	txtJobNo.setInputType(InputType.TYPE_CLASS_TEXT);
	        }

            txtJobNo.setText(jobNo);

	        if (JTApplication.getInstance().getTCPManager().AreWeOnline()== true )
	        	btnConnect.setImageResource(R.drawable.connected);
	        else
	        	btnConnect.setImageResource(R.drawable.disconnected);




            ClearJobsScreen(true);

            // This executes when a Job is selected from the Search Jobs form.
            if (jobNo != "") {

                mycontext1 = activity2.this;
                // isJobCachedPendingUpdate


                // Attempt to reconnect again just in case.  This takes care of already being connected as well.
                JTApplication.getInstance().GetApplicationManager().ConnectToNetwork();


                if ((JTApplication.getInstance().getTCPManager().AreWeOnline() == true) && (JTApplication.getInstance().GetDatabaseManager().isJobCachedPendingUpdate(jobNo) == false)) {
                    txtJobNo.setText(jobNo);
                    dialog = ProgressDialog.show(activity2.this,"Loading Job","Please Wait ...",false,true);
                    final String job_no = jobNo;
                    Thread th = new Thread(new Runnable() {
                        public void run() {
                            try {
                                LoadJob(job_no);
                            }
                            catch (Exception e) {
                                Log.i("LoadJob: ",e.getMessage());
                            }
                        }
                    });
                    th.start();
                } else {
                    // Offline or Online but Job is pending upload.

                    if (JTApplication.getInstance().GetDatabaseManager().isJobCached(jobNo) == true) {
                        JTApplication.getInstance().GetDatabaseManager().LoadJobFromCache(myJob,jobNo);
                        DoWork("");
                        if (JTApplication.getInstance().getTCPManager().AreWeOnline() == true) {
                            DoToast("Job " + jobNo + " is waiting to be uploaded.  Loaded from local database.");
                        } else {
                            DoToast("Working Offline. \nJob: " + jobNo + " loaded from local database.");
                        }
                    } else {
                        DoToast("You are working offline and job: " + jobNo + " is not in the local database.");
                    }
                }
            }



		    // The Exit buton
	        Button btnExit= (Button) findViewById(R.id.btnExit);
	        btnExit.setOnClickListener(new View.OnClickListener() {
	        	@Override
				public void onClick(View view) {
	        		ClearCache(mycontext1);
	        		finish();
	        	}
	        });          
	        
	        
	        // Click Listener for txtCustContact3  (Site telephone)
	        txtCustContact3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					CallNumber(txtCustContact3.getText().toString());
				}
			});
	        
	        
	        // Click Listener for txtCustContact4  (Site Mobile)
	        txtCustContact4.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					CallNumber(txtCustContact4.getText().toString());
				}
			});
	        
	        // Click Listener for txtCustContact6  (Site Email)
	        txtCustContact6.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					SendEmail(txtCustContact6.getText().toString(),"Re: Job " + txtJobNo.getText().toString());
				}
			});
	        
	        
	        // Click Listener for Client Telephone
	        txtClientTel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					CallNumber(txtClientTel.getText().toString());
				}
			});

	        // Click Listener for Client Email (Client Email)
	        txtClientEmail.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					SendEmail(txtClientEmail.getText().toString(),"Re: Job " + txtJobNo.getText().toString());
				}
			});

	        
	        
	        
			
	        // The Connection Button
	        btnConnect.setOnClickListener(new View.OnClickListener() {
	        	@Override
				public void onClick(View view) {
	        		try {
		        		if (JTApplication.getInstance().getTCPManager().AreWeOnline()) {
							JTApplication.getInstance().GetApplicationManager().SetForcedOffLine(true);
							JTApplication.getInstance().getTCPManager().CloseSocket();
		        			btnConnect.setImageResource(R.drawable.disconnected);
		        			//DoToast("Working Offline");
		        		} else {
							JTApplication.getInstance().GetApplicationManager().SetNoMessage(false);
							JTApplication.getInstance().GetApplicationManager().SetForcedOffLine(false);
							JTApplication.getInstance().GetApplicationManager().ConnectToNetwork();
		        			
		        			//JTApplication.noMessage = false;
		        			//JTApplication.forcedOffline = false;
		    				//JTApplication.GetApplicationManager().ConnectToNetwork();
		    				btnConnect.setImageResource(R.drawable.connected);
		        		}
	        		} catch (Exception ex) {
	        			
	        		}
	        	}
	        });          
	        
	        
	        
	        // The calendar button.
	        mDateDisplay = (TextView) findViewById(R.id.txtCompletionDate);
	        Button btnCalendar= (Button) findViewById(R.id.btnCalendar);
	        btnCalendar.setOnClickListener(new View.OnClickListener() {
				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View view) {
					showDialog(DATE_DIALOG_ID);
					
				}
			});
	        
	        // get the current date
	        try { 
		        final Calendar c = Calendar.getInstance();
		        mYear = c.get(Calendar.YEAR);
		        mMonth = c.get(Calendar.MONTH);
		        mDay = c.get(Calendar.DAY_OF_MONTH);
		        updateDateDisplay();
	        } catch (Exception e) {
	        	Log.i("activity2.onCreate", e.getMessage());
	        }
		       

	        // Photo Button
	        Button btnPhotos = (Button) findViewById(R.id.butTakePhoto);
	        btnPhotos.setOnClickListener(new View.OnClickListener() {
	        	@Override
	        	public void onClick(View v) {

                    Intent myIntent = new Intent(activity2.this,C_Photos.class);

	        		startActivityForResult(myIntent,0);
	        	}
	        });

	        // Update the job
	        Button btnUpdateJob = (Button) findViewById(R.id.btnUpdateJob);
	        btnUpdateJob.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UpdateJob();
					
				}
			});
	        
	     // Load Job Button
            // This expects the Job Number to be in the txtJobNo text box.
	        Button btnGetJob = (Button) findViewById(R.id.btnGetJob);
	        btnGetJob.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					
					mycontext1 = activity2.this;
					
					InputMethodManager imm = (InputMethodManager)getSystemService(
					Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(txtJobNo.getWindowToken(), 0);
					
					String JobNo = txtJobNo.getText().toString();
					if (JobNo.length() > 0 ) {
						
                        boolean bCacheAvailable = JTApplication.getInstance().getDBManager().isJobCached(JobNo);

						// Attempt to reconnect again just in case.  This takes care of already being connected as well.
						JTApplication.getInstance().getAppManager().ConnectToNetwork();


						if(JTApplication.getInstance().getTCPManager().AreWeOnline() == false) {
							if (bCacheAvailable == true) {
								JTApplication.getInstance().GetDatabaseManager().LoadJobFromCache(myJob,JobNo);
								DoWork("");
								DoToast("Working Offline. \nJob: " + JobNo + " loaded from local database.");
							} else {
								DoToast("You are working offline and job: " + JobNo + " is not in the local database.");
							}
						} else {

							if (JTApplication.getInstance().GetDatabaseManager().isJobCachedPendingUpdate(JobNo) == true) {
								// We're online but loading a job that is pending an update, so load from local database.
								JTApplication.getInstance().GetDatabaseManager().LoadJobFromCache(myJob,JobNo);
								DoWork("");
								DoToast("Job: " + JobNo + " is waiting to be uploaded. Loaded from local database.");
							} else {
								dialog = ProgressDialog.show(activity2.this,"Loading Job","Please Wait ...",false,true);

								if (mStartTime == 0L) {
						            mStartTime = System.currentTimeMillis();
						            mHandler.removeCallbacks(mUpdateTimeTask);
						            mHandler.postDelayed(mUpdateTimeTask, 10000);
						       }


                                final String job_no = JobNo;

								Thread th = new Thread(new Runnable() {
									public void run() {
										try {
											LoadJob(job_no);
										}
										catch (Exception e) {
											Log.i("LoadJob: ",e.getMessage());
										}
									}
								});
								th.start();
							}
						}
					}
				}
			});

	        // Signature Button
	        Button butGetSignature = (Button) findViewById(R.id.butSignature);
	        butGetSignature.setOnClickListener(new View.OnClickListener() {
	        	@Override
				public void onClick(View view) {
	        		Intent myIntent = new Intent(activity2.this, CaptureSignature.class);
	        		startActivityForResult(myIntent,SIGNATURE_ACTIVITY);
	        	}
	        });




	        // Sketch Button
	        Button butGetSketch = (Button) findViewById(R.id.butSketch);
	        butGetSketch.setOnClickListener(new View.OnClickListener() {
	        	@Override
				public void onClick(View view) {
	        		Intent myIntent = new Intent(activity2.this, CaptureSketch.class);
	        		startActivityForResult(myIntent,SKETCH_ACTIVITY);
	        	}
	        });




	        // Log Time Button
	        Button butTimes = (Button) findViewById(R.id.butTimes);
	        butTimes.setOnClickListener(new View.OnClickListener() {
	        	@Override
				public void onClick(View view) {
	        		Intent myIntent = new Intent(view.getContext(),LogTimes.class);
	        		startActivityForResult(myIntent,0);
	        	}
	        });

	        // Parts Required
	        Button butPartsRequired = (Button) findViewById(R.id.butPartsRequired);
	        butPartsRequired.setOnClickListener(new View.OnClickListener() {
	        	@Override
				public void onClick(View view) {
	        		Intent myIntent = new Intent(view.getContext(),ListPartsRequired.class);
	        		startActivityForResult(myIntent,0);
	        	}
	        });

	        // Parts Used
	        Button butPartsUsed = (Button) findViewById(R.id.butPartsUsed);
	        butPartsUsed.setOnClickListener(new View.OnClickListener() {
	        	@Override
				public void onClick(View view) {
	        		Intent myIntent = new Intent(view.getContext(),ListPartsUsed.class);
	        		startActivityForResult(myIntent,0);
	        	}
	        });


	        // Payment Receipts
	        Button butPayment = (Button) findViewById(R.id.butPaymentReceipt);
	        butPayment.setOnClickListener(new View.OnClickListener() {
	        	@Override
				public void onClick(View view) {
	        		Intent myIntent = new Intent(view.getContext(),ListPayments.class);
	        		startActivityForResult(myIntent,0);
	        	}
	        });



	        // Log Paperwork
	        Button butPaperwork= (Button) findViewById(R.id.butPaperwork);
	        butPaperwork.setOnClickListener(new View.OnClickListener() {
	        	@Override
				public void onClick(View view) {
	        		Intent myIntent = new Intent(view.getContext(),PaperWork.class);
	        		startActivityForResult(myIntent,0);
	        	}
	        });

	        // Fire Hydrant
	        Button butFireHydrant= (Button) findViewById(R.id.butFireHydrant);
	        butFireHydrant.setOnClickListener(new View.OnClickListener() {
	        	@Override
				public void onClick(View view) {
	        		Intent myIntent = new Intent(view.getContext(),ListFireHydrants.class);
	        		startActivityForResult(myIntent,0);
	        	}
	        });

	        // Riser Service
	        Button butRiserService= (Button) findViewById(R.id.butRiserService);
	        butRiserService.setOnClickListener(new View.OnClickListener() {
	        	@Override
				public void onClick(View view) {
	        		Intent myIntent = new Intent(view.getContext(),ListRiserServices.class);
	        		startActivityForResult(myIntent,0);
	        	}
	        });

			//Take the picutre
			Button btTakePicture = (Button) findViewById(R.id.bt_take_picutre_jobscreen);
			btTakePicture.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent myIntent = new Intent(activity2.this,C_Photos.class);
					startActivityForResult(myIntent,0);
				}
			});


	        // Assign visibility based on Job Tracker Pro settings
	        if (JTApplication.getInstance().getSettings().getbutLogTime() == true) {
	        	butTimes.setVisibility(View.INVISIBLE);
	        	butTimes.setHeight(1);
	        }

	        if (JTApplication.getInstance().getSettings().getbutDrawSketch() == true) {
	        	butGetSketch.setVisibility(View.INVISIBLE);
	        	butGetSketch.setHeight(1);
	        }

	        if (JTApplication.getInstance().getSettings().getbutPartsUsed() == true) {
	        	butPartsUsed.setVisibility(View.INVISIBLE);
	        	butPartsUsed.setHeight(1);
	        }

	        if (JTApplication.getInstance().getSettings().getbutPaperwork() == true) {
	        	butPaperwork.setVisibility(View.INVISIBLE);
	        	butPaperwork.setHeight(0);
	        }

	        if (JTApplication.getInstance().getSettings().getbutgetSignature() == true) {
	        	butGetSignature.setVisibility(View.INVISIBLE);
	        	butGetSignature.setHeight(0);
	        }

	        if (JTApplication.getInstance().getSettings().getbutPartsRequired() == true) {
	        	butPartsRequired.setVisibility(View.INVISIBLE);
	        	butPartsRequired.setHeight(0);
	        }

	        if (JTApplication.getInstance().getSettings().getbutPayments() == true) {
	        	butPayment.setVisibility(View.INVISIBLE);
	        	butPayment.setHeight(0);
	        }

	        if (JTApplication.getInstance().getSettings().getbutFireHydrants() == true) {
	        	butFireHydrant.setVisibility(View.INVISIBLE);
	        	butFireHydrant.setHeight(0);
	        }

	        if (JTApplication.getInstance().getSettings().getbutRiserService() == true) {
	        	butRiserService.setVisibility(View.INVISIBLE);
	        	butRiserService.setHeight(0);
	        	txtSLA.setVisibility(View.INVISIBLE);
	        	txtSLA.setHeight(0);
	        	labTimeOnSite.setVisibility(View.INVISIBLE);
	        	labTimeOnSite.setHeight(0);
	        }

	        if (JTApplication.getInstance().getSettings().getHideMainJobComplete() == true) {
	        	cbJobCompleted.setVisibility(View.INVISIBLE );
	        	cbJobCompleted.setHeight(0);
	        }


	        // Below have else clauses because their default values are visible and
	        // as they're new some people may not want them.
	        if (JTApplication.getInstance().getSettings().getShowWorksCarriedOut() == true) {
	        		txtWorksCarriedOut.setVisibility(View.VISIBLE );
	        }
	        else {
	        	txtWorksCarriedOut.setVisibility(View.INVISIBLE);
	        	txtWorksCarriedOut.setHeight(0);
	        }

	        if (JTApplication.getInstance().getSettings().getShowCauseOfProblem() == true) {
        		txtCauseOfProblem.setVisibility(View.VISIBLE );
	        }
	        else {
        		txtCauseOfProblem.setVisibility(View.INVISIBLE);
        		txtCauseOfProblem.setHeight(0);
	        }

	        if (JTApplication.getInstance().getSettings().getShowEngFinished() == true) {
	        	cbEngFinished.setVisibility(View.VISIBLE);
	        }
	        else {
	        	cbEngFinished.setVisibility(View.INVISIBLE);
	        	cbEngFinished.setHeight(0);
	        }




        } catch (Exception e)
        {
        	Log.i("activity2 SearchJobs",e.getMessage());
        }


    }


	public  void UpdateStatus(String DataIn) {
    	Parser objParser;
		objParser = new Parser();
		String ack;
		String JobNo;

		try {
			JobNo = objParser.ExtractTagValue(DataIn, "JOBNO");
	    	ack = objParser.ExtractTagValue(DataIn, "STATUS");

	    	// Make sure this ACK is for the currently loaded job.
	    	if (JobNo.equals(txtJobNo.getText().toString())) {
		    	if (ack.equals("ACK"))
		    	{
		    		labStatus.setText("Update Received.");
		    		DoToast("Job " + JobNo + " was successfully uploaded.");
		    		UpdateTimeSheetIDs(DataIn);

					JTApplication.getInstance().getDBManager().ClearSpecificJobCache(JobNo);
		    	} else {
		    		labStatus.setText("Update Failed.");
		    		DoToast("Job " + JobNo + " failed to upload. Please try again.");
		    	}
	    	}
		} catch (Exception e)
		{
			Log.i("UpdateStatus Exception:",e.getMessage());
		}



}


/**
 * If new TimeSheets have been sent to Job Tracker Pro it returns
 * a list of the record IDs for each element.
 * This is required otherwise the time sheet elements are added again and again
 * as the system thinks they are new.
 *
 * In: Completed XML from Job Tracker Server.
 */
public  void UpdateTimeSheetIDs(String DataIn) {
	// TODO.  This is a temp fix.
	try {
		for (int i = 0; i < myJob.getJobTimes().size(); i++) {
			C_JobTimes timesheet = myJob.getJobTimes().get(i);
			if (timesheet.getRecordID() == -1) {
				timesheet.setRecordID(-99);
			}
		}
	} catch (Exception e)
	{
		e.getMessage();
	}

}

	private   void DoToast(String msg)
	{
/*		Toast myToast = new Toast(activity2.mycontext1);

		myToast.setGravity(Gravity.TOP|Gravity.LEFT,100,100);
		myToast.setDuration(Toast.LENGTH_LONG);
		myToast.setText(msg);
		myToast.show();
*/

		Toast.makeText(activity2.mycontext1,msg,Toast.LENGTH_LONG).show();

	}


	public  void IdentifyYourself(String DataIn)
	{
		String sData;
		TelephonyManager mTelephonyMgr;
		mTelephonyMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

		mTelephonyMgr.getDeviceId();
		sData = "<?xml version='1.0' encoding='utf-8' ?>\r\n<data>\r\n<CMD>IDENTIFYYOURSELF</CMD><ID><MAC>" + mTelephonyMgr + "</MAC><ENG>"
		+ JTApplication.getInstance().GetApplicationManager().getDeviceAssignedEngineerName() + "</ENG>\r\n</ID>\r\n</data>";


		JTApplication.getInstance().getTCPManager().SendDataToNetwork(sData);
	}




    public  void UpdateLoadJobStatus(String DataIn)
    {
    	try {
	    	dialog.dismiss();
	    	DoToast("Job Does Not Exist");

    	} catch (Exception e) {

    	}
    }

	public void UpdateScreen(final String DataIn) {
		DoWork(DataIn);
	}

	// This is passed the data received from the comms server
	private  static void DoWork(String DataIn)
	{
		try {
			if (DataIn != "")
			{
				// Extract the data from the XML and populate the Job object.
				// Passes the data received from the server and a boolean stating if its from the cache (think boolean is not used)
				myJob.PopulateJob(DataIn,false);
				try {
					if (dialog.isShowing() == true ) {
						dialog.dismiss();
					}
				} catch (Exception e) {

				}
			}

			txtJobNo.setText(myJob.getJobNo());
			txtSLA.setText(myJob.getJobText12());
			txtClientName.setText(myJob.getClientAddress().getName());
			txtClientContact.setText(myJob.getClientAddress().getContacts());
			txtClientTel.setText(myJob.getClientAddress().getTelephone());
			txtClientEmail.setText(myJob.getClientAddress().getEmail());
			txtJobDetails2.setText(myJob.getJobText1());
			txtJobDetails0.setText(myJob.getJobText2());
			txtJobDetails1.setText(myJob.getJobText3());

			String myDate = "";
			String localDate = "";
			String dateField;
			try {


                if (JTApplication.getInstance().getSettings().getUseDueDate()) {
					dateField = JTApplication.getInstance().getSettings().getDueDateField().toUpperCase();
					if (dateField.equals("JOBDATE1"))
						localDate = myJob.getJobDate1();

					if (dateField.equals("JOBDATE2"))
						localDate = myJob.getJobDate2();

					if (dateField.equals("JOBDATE3"))
						localDate = myJob.getJobDate3();

					if (dateField.equals("JOBDATE4"))
						localDate = myJob.getJobDate4();

					if (dateField.equals("JOBDATE5"))
						localDate = myJob.getJobDate5();

					if (dateField.equals("JOBDATE6"))
						localDate = myJob.getJobDate6();

					if (dateField.equals("JOBDATE7"))
						localDate = myJob.getJobDate7();

					if (dateField.equals("JOBDATE8"))
						localDate = myJob.getJobDate8();

					if (dateField.equals("JOBDATE9"))
						localDate = myJob.getJobDate9();

				} else {
					localDate = myJob.getJobDate1();
				}
				myDate = JTApplication.getInstance().GetDatabaseManager().fromEpochStringToDate(localDate);
				txtJobDate3.setText(myDate);

			} catch (Exception e) {
				Log.i("Error", e.getMessage());
				Log.i("Error", myDate);
			}


			txtCustContact0.setText(myJob.getSiteAddress().getName());
			txtCustContact1.setText(myJob.getSiteAddress().getAddress());
			txtCustContact11.setText(myJob.getSiteAddress().getPostCode());
			txtCustContact2.setText(myJob.getSiteAddress().getContacts());
			txtCustContact3.setText(myJob.getSiteAddress().getTelephone());
			txtCustContact4.setText(myJob.getSiteAddress().getMobile());
			txtCustContact6.setText(myJob.getSiteAddress().getEmail());




			if (JTApplication.getInstance().getSettings().getDisplayJobBrief())
                txtJobDetails11.setText(myJob.getJobBrief());
            else
				txtJobDetails11.setText(myJob.getJobText11());


			int height_in_pixels;
			height_in_pixels= txtJobDetails11.getLineCount() * txtJobDetails11.getLineHeight();
			txtJobDetails11.setHeight(height_in_pixels);

			txtEngReport.setText(myJob.getRecallReason());


			// Display Completion Date
			localDate = myJob.getJobDate6();
			myDate = JTApplication.getInstance().GetDatabaseManager().fromEpochStringToDate(localDate);
			txtCompletionDate.setText(myDate);

			cbJobCompleted.setChecked(myJob.getJobCompleted());

			cbEngFinished.setChecked(myJob.getEngFinished());

			txtWorksCarriedOut.setText(myJob.getWorksCarriedOut());
			txtCauseOfProblem.setText(myJob.getCauseOfProblem());



			try {
				if (dialog.isShowing())
					dialog.dismiss();
			} catch (Exception e) {
			}

			// Save cache, only when loaded from network otherwise we're just caching what was just read from the cache.
			if(JTApplication.getInstance().getTCPManager().AreWeOnline() == true)
                JTApplication.getInstance().GetDatabaseManager().SaveJobToCache();

		} catch (Exception e) {
			e.getMessage();
		}

	}

	private  void ClearJobsScreen(Boolean ClearJobNo)
	{
		try {
			if (ClearJobNo==true)
				txtJobNo.setText("");
			txtSLA.setText("");
			txtClientName.setText("");
			txtClientContact.setText("");
			txtClientTel.setText("");
			txtClientEmail.setText("");
			txtJobDetails2.setText("");
			txtJobDetails0.setText("");
			txtJobDetails1.setText("");
			txtJobDate3.setText("");

			txtCustContact0.setText("");
			txtCustContact1.setText("");
			txtCustContact11.setText("");
			txtCustContact2.setText("");
			txtCustContact3.setText("");
			txtCustContact4.setText("");
			txtCustContact6.setText("");


			txtJobDetails11.setText("");
			txtEngReport.setText("");

			cbJobCompleted.setChecked(false);
			txtCompletionDate.setText("");

			cbEngFinished.setChecked(false);
			txtWorksCarriedOut.setText("");
			txtCauseOfProblem.setText("");


			PopulateLables();

		} catch (Exception e) {

		}
	}

	// Populate the job screen with the custom labels.
	private void PopulateLables()
	{

		try {
			labJobLabel11.setText(JTApplication.getInstance().GetDatabaseManager().GetJobLabel(5));
			labJobLabel17.setText(JTApplication.getInstance().GetDatabaseManager().GetJobLabel(6));
			labJobLabel18.setText(JTApplication.getInstance().GetDatabaseManager().GetJobLabel(7));
			labTimeOnSite.setText(JTApplication.getInstance().GetDatabaseManager().GetJobLabel(115));

			if (JTApplication.getInstance().getSettings().getUseDueDate() == true) {
				labJobLabel5.setText(JTApplication.getInstance().getSettings().getDueDateDesc());
				//labJobLabel5.setText(JTApplication.GetDatabaseManager().GetJobLabel(32));

			} else {
				labJobLabel5.setText(JTApplication.getInstance().GetDatabaseManager().GetJobLabel(9));
			}

		} catch (Exception e) {
			// Do nothing, labels have not been initialised.
		}

	}


	private void LoadJob(String JobNo)
	{
		boolean bCacheAvailable = false;

		// no point doing this here as its in the UI thread and won't redraw until calling routine is complete.
//		dialog = ProgressDialog.show(activity2.this,"Downloading","Please Wait");
        String xmlStr = "<?xml version='1.0' encoding='utf-8' ?>\r\n<data>\r\n<GET><JOBNO>" + JobNo + "</JOBNO>\r\n</GET>\r\n</data>";

        byte[] sndData = xmlStr.getBytes();

        byte[] data= mUtils.MakePacket(sndData,(byte) CommandType.OTHERCOMMAND.getVal(), sndData.length);

		try {
			//String JobNo;
			//JobNo = txtJobNo.getText().toString();






			if (JobNo.length() > 0 ) {
				//JTApplication.getInstance().getTCPManager().SendDataToNetwork();
                JTApplication.getInstance().getTCPManager().SendPacket(data);
			}
		}
		catch (Exception e) {
			Log.i("LoadJob: ",e.getMessage());
		}
	}
//		});
//		th.start();

//	}


	private void UpdateJob()
	{
//		Handler mHandler;
//		String sendText = "";
		boolean includeTimeSheet;
//		String saveResult ;
//		Integer sigStrength;
		String JobNo;

		try {

			includeTimeSheet = false;
			// Set screen cursor to wait TODO
			JobNo = txtJobNo.getText().toString();
			if (JobNo != "") {

				PopulateJobObjectFromScreen();


				// Save copy to the local database.
				JTApplication.getInstance().GetDatabaseManager().SaveJobToCache();



				//mHandler = new Handler();
				//mHandler.post(mUpdate);

				// Attempt to reconnect again just in case.  This takes care of already being connected as well.
				JTApplication.getInstance().GetApplicationManager().ConnectToNetwork();

				if (JTApplication.getInstance().getTCPManager().AreWeOnline()) {
					// Create XML data and upload it.
                 /*   String xmlJob =JTApplication.getInstance().getTCPManager().PrepareJobXMLForUploading();
                    byte[] data = xmlJob.getBytes();
                    byte[]sndData = mUtils.MakePacket(data, (byte)CommandType.OTHERCOMMAND.getVal(), data.length);

                    JTApplication.getInstance().getTCPManager().SendPacket(sndData);
                */
					//JTApplication.getInstance().getTCPManager().SendDataToNetwork();
					//upload images.


                    // So what does this do.  It look slike it creates a progress bar?
                    // This is for Upload Images.
                    dlg_uploadImg =  new ProgressDialog(this);
                    dlg_uploadImg.setTitle("Upload Images");
                    dlg_uploadImg.setMessage("Uploading images");
                    dlg_uploadImg.setProgressStyle(dlg_uploadImg.STYLE_HORIZONTAL);
                    dlg_uploadImg.setProgress(0);


                    // Can you write some comments here about this sub please.
                    // What does createJobUpProgress do?
                    //This function creates the Progressbar for Uploading the Job.
                    createJobUpProgress();

                    int count = myJob.image_paths_list.size();
                        dlg_uploadImg.setMax(100);

                        new UploadImagesTask().execute();

                        dlg_uploadImg.show();


                    // This uploads every image ever taken for the job. It only needs to upload
                    // images that have never been successfully uploaded before.


					labStatus.setVisibility(View.VISIBLE);
					labStatus.setText("Waiting on server..");
				} else {
					labStatus.setVisibility(View.VISIBLE);
					labStatus.setText("Working Offline, Saved locally.");
					DoToast("Working Offline, Job " + JobNo + " saved on device.");
				}
			}


		} catch (Exception e)
		{

		}
	}

	//private Runnable mUpdate = new Runnable() {
//		   public void run() {
//				// TODO Check phone signal strength before transmitting.
//			   JTApplication.getTCPManager().SendDataToNetwork(JTApplication.getTCPManager().PrepareJobXMLForUploading());
//		    }
//		};	

	
	// TODO this is a task list
	private void PopulateJobObjectFromScreen()
	{
		String myDate = "";
		String myEpoch = "";
		
		try {
			myDate = txtCompletionDate.getText().toString();
			myEpoch = JTApplication.getInstance().GetDatabaseManager().ToEpochStringFromString(myDate);
			myJob.setJobDate6(myEpoch);
 			
			myJob.setJobCompleted(cbJobCompleted.isChecked());
			myJob.setRecallReason(txtEngReport.getText().toString());
			
			myJob.setEngFinished(cbEngFinished.isChecked());
			myJob.setWorksCarriedOut(txtWorksCarriedOut.getText().toString());
			myJob.setCauseOfProblem(txtCauseOfProblem.getText().toString());
			
			
			
			myJob.setPendingUpload(true);
			
		} catch (Exception e) {
			Log.i("activity2:PopulateJobObjectFromScreen",e.getMessage());
		}
	}
	
	
	private void updateDateDisplay() {
		mDateDisplay.setText(
				new StringBuilder()
					// Month is 0 based so add 1
				.append(mDay).append("/")
				.append(mMonth + 1).append("/")
				.append(mYear).append(" "));
	}
	
	
	static Runnable mUpdateTimeTask = new Runnable() {
		   public void run() {
		     
			   mStartTime =0L;
		       mHandler.removeCallbacks(mUpdateTimeTask);
		       dialog.dismiss();
		       
		       // Removed this as its being displayed even when message was received. 
		       // DoToast("No Reply From Server");
		   }
		};	
	

		public static void ClearCache(Context context) {
		      try {
		         File dir = context.getCacheDir();
		         if (dir != null && dir.isDirectory()) {
		            deleteDir(dir);
		         }
		      } catch (Exception e) {
		         // TODO: handle exception
		      }
		   }

		public static boolean deleteDir(File dir) {
		      if (dir != null && dir.isDirectory()) {
		         String[] children = dir.list();
		         for (int i = 0; i < children.length; i++) {
		            boolean success = deleteDir(new File(dir, children[i]));
		            if (!success) {
		               return false;
		            }
		         }
		      }

		      // The directory is now empty so delete it
		      return dir.delete();
		   }
		
		public void CallNumber(String sNumber) {
			try {

				sNumberToDial = sNumber;
				
				new AlertDialog.Builder(this)
			    .setTitle("Make Phone Call")
			    .setMessage("Please confirn you want to call " + sNumber)
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 

			            Intent callIntent = new Intent(Intent.ACTION_CALL);
			            callIntent.setData(Uri.parse("tel:"+ sNumberToDial));
			            startActivity(callIntent);
			        }
			     })
			    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			        }
			     })
			     
			    .setNeutralButton("Send SMS", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			        	SendSMS(sNumberToDial,"");
			        }
			     })
			     
			     
			    .setIcon(android.R.drawable.ic_dialog_alert)
			     .show();				
				
	        } catch (ActivityNotFoundException activityException) {
	            Log.e("Calling Phone Failed", "Call failed", activityException);
	        }		
		}
		
		public void SendSMS(String sNumber, String sText) {
			try {
				
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                        + sNumber)));
				
				//SmsManager smsManager = SmsManager.getDefault();
				//smsManager.sendTextMessage(sNumber, null, sText, null, null);
				//Toast.makeText(getApplicationContext(), "SMS Sent!",
				//			Toast.LENGTH_LONG).show();				
			} catch (Exception e) {
				// TODO: handle exception
			} 
			
		}
		
		public void SendEmail(String sEmailAddress,String sSubject) {
			try {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/html");
				intent.putExtra(Intent.EXTRA_EMAIL, sEmailAddress);
				intent.putExtra(Intent.EXTRA_SUBJECT, "");
				intent.putExtra(Intent.EXTRA_TEXT, "");

				startActivity(Intent.createChooser(intent, "Send Email"));			
			} catch (Exception e) {
				// TODO: handle exception
			}
		}



	private class UploadImagesTask extends AsyncTask<String,Integer,Long>{
		private String NameOfFolder = "/JTMobile/";

		final byte Permission =1;
		final byte ImageUpload =2;
		final byte JobUpload =3;
		final byte UpFinish =4;

		final byte newImage =5;
		final byte newJob =6;




		@Override
		protected Long doInBackground(String... params) {

            //Upload Image

			String JobNo = myJob.getJobNo();
			int count  = myJob.image_paths_list.size();
			String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder + JobNo;
			String file_name ;

            long filesize;
            long sendProgress;
            double pcProgress;
            int _progress;



            byte[] img_buffer =new byte[teh_packetsize];     // Lets try 3k  (10k Caused corruption)
            byte[] data;

			for(int i =0 ;i<count; i++){



				file_name = file_path + "/"+ myJob.image_paths_list.get(i).filename;


                File file = new File(file_name);

				if(file.exists()){
                    filesize = file.length();
                    sendProgress = 0;

                    // Send the XML Header
                   // String sndData = JTApplication.getInstance().getTCPManager().ConstructImageTransferHeader(JobNo, file.getName(), file.lastModified());
                    //JTApplication.getInstance().getTCPManager().SendDataToNetwork(sndData);


                    //byte[] filedata = new byte[(int)file.length()];

					int cmd_type  = newImage;
					byte[] tmpData = file.getName().getBytes();

                    data = mUtils.MakePacket(tmpData, newImage, tmpData.length);
					JTApplication.getInstance().getTCPManager().SendPacket(data);


      				try{
						DataInputStream dis = new DataInputStream(new FileInputStream(file));

						//dis.readFully(filedata);
						//dis.close();

                        int readbyte,total_readsize = 0;
                        while((readbyte = dis.read(img_buffer))>0) {



                           // System.arraycopy(img_buffer,0,tmp_buffer, 0, readbyte);
							byte[] tmp_buffer = mUtils.MakePacket(img_buffer, ImageUpload, readbyte);

							JTApplication.getInstance().getTCPManager().SendPacket(tmp_buffer);


                            // Anatoly.  This is exactly right.  Its how we already do it.
                          //  String base64 = Base64.encodeToString(filedata, Base64.DEFAULT);
                          //  String base64 = Base64.encodeToString(tmp_buffer, Base64.DEFAULT);

                           // imgData += base64;

                            // Send block of encoded file.
                           // JTApplication.getInstance().getTCPManager().SendDataToNetwork(base64);

							total_readsize += readbyte;

                            pcProgress= ((float) total_readsize / filesize) * 100;
                            _progress =  (int) pcProgress;
                            publishProgress(_progress, i+1, count, 0, 0);
                            Thread.sleep(10);
                        }

						//publishProgress(100, i+1, count, 0);
                        dis.close();

                        Thread.sleep(300);
                        // Create XML header ready for sending image file and SEND IT
                        // Start looping
                        //     Encode part of jpg file
                        //     Send That
                        // End loop when eof
                        // Send XML footer




                        // Construct XML Header for transfering to server.
                       // String sndData = JTApplication.getInstance().getTCPManager().ConstructImageTransfer(JobNo, file.getName(), imgData, file.lastModified());

                        // String sndData = "<IMG>"+ "<FILENAME>"+file.getName()+ "</FILENAME>"+"<DATA>"+base64+"</DATA></IMG>";
                        //JTApplication.getInstance().getTCPManager().SendDataToNetwork(sndData);

					}
					catch (Exception e){

					}

				}
                // Send the XML Footer
                //String sndData = JTApplication.getInstance().getTCPManager().ConstructImageTransferFooter();
                //JTApplication.getInstance().getTCPManager().SendDataToNetwork(sndData);


                //publishProgress(i);


               	if(isCancelled()) break;
            }


            publishProgress(100, count, count, 1, 0);


            float total_datasize =0.0f;

            //Upload Job
            String xmlJob =JTApplication.getInstance().getTCPManager().PrepareJobXMLForUploading();
            data = xmlJob.getBytes();
            byte[]sndData = mUtils.MakePacket(data, (byte)CommandType.OTHERCOMMAND.getVal(), data.length);

            int allsize = sndData.length;
            int uploadjob_packetsize = teh_packetsize * 3;
            total_datasize = (float)allsize;
            int ptr_data = 0;
            int prog =0;
            while(allsize!= 0){
                byte[]upData;
                int packet_len =0;
                if( allsize >= uploadjob_packetsize){
                    allsize -= uploadjob_packetsize;
                    packet_len = uploadjob_packetsize;
                }else{
                    packet_len = allsize;
                    allsize = 0;
                }

               /*  upData = new byte[packet_len];

                System.arraycopy(sndData, ptr_data, upData, 0, packet_len);


                JTApplication.getInstance().getTCPManager().SendPacket(upData);
                */

                JTApplication.getInstance().getTCPManager().SendPacket(sndData, ptr_data, packet_len);
                ptr_data += packet_len;
                float res = (((float)ptr_data)/ total_datasize)*100;
                prog = Math.round(res);

                publishProgress(prog, 0, 0, 0,1);
/*
                try{
                    Thread.sleep(40);
                }catch(Exception e){

                }
*/
            }






            publishProgress(100, 0, 0, 1, 1);

			return null;
		}

		@Override
		protected  void onProgressUpdate(Integer... progress){

            updateUploadImgProgress(progress[0],progress[1], progress[2], progress[3], progress[4]);
		}

		@Override
		protected  void onPostExecute(Long result){
            dlg_uploadImg.dismiss();
		}
	}
    public  void updateUploadImgProgress(final int progress, final int ImgNo, final int Count,int last, int JobUpload){

        if(JobUpload == 0) {
            dlg_uploadImg.setTitle("Image Uploading");
            dlg_uploadImg.setMessage("Image No" + Integer.toString(ImgNo) + "/" + Integer.toString(Count));
            dlg_uploadImg.setProgress(progress);
		/*updateUploadDlg.post(new Runnable() {
			@Override
			public void run() {
				// int cur = dlg_uploadImg.getProgress();
				dlg_uploadImg.setTitle("Image Uploading");
				dlg_uploadImg.setMessage("Image No" + Integer.toString(ImgNo) +"/" + Integer.toString(Count));
				dlg_uploadImg.setProgress(progress);
			}
		});*/

            if (progress >= 100 && last == 1) {
                dlg_uploadImg.dismiss();
            }
        }
        else {
            //dlg_uploadImg.setTitle("Image Uploading");
           // dlg_uploadImg.setMessage("Image No" + Integer.toString(ImgNo) + "/" + Integer.toString(Count));
            dlg_uploadJob.show();
            dlg_uploadJob.setProgress(progress);
		/*updateUploadDlg.post(new Runnable() {
			@Override
			public void run() {
				// int cur = dlg_uploadImg.getProgress();
				dlg_uploadImg.setTitle("Image Uploading");
				dlg_uploadImg.setMessage("Image No" + Integer.toString(ImgNo) +"/" + Integer.toString(Count));
				dlg_uploadImg.setProgress(progress);
			}
		});*/

            if (progress >= 100 && last == 1) {
                dlg_uploadJob.dismiss();
            }

        }
      /*  if(dlg_uploadImg.getProgress()< dlg_uploadImg.getMax()){
            updateUploadDlg.post(new Runnable() {
                @Override
                public void run() {
                   // int cur = dlg_uploadImg.getProgress();
                    dlg_uploadImg.setProgress(progress);
                }
            });

        }
        else{
            dlg_uploadImg.dismiss();
        }
        */
    }


}


