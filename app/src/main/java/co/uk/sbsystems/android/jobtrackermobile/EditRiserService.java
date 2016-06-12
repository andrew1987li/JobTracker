package co.uk.sbsystems.android.jobtrackermobile;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class EditRiserService extends Activity{
	
	private static final String TAG = "EditRiserService";
	
	

	private Spinner spinLocation;
	private EditText editRiserNo;
	private EditText editRiserLocation;
	
	private EditText editInletKey;
	private EditText editOutletKey;
	
	private EditText editExtDetails1;
	private TextView txtExtServiceCheck1;
	private TextView txtExtStatus1;
	
	private EditText editExtDetails2;
	private TextView txtExtServiceCheck2;
	private TextView txtExtStatus2;
	
	private EditText editExtDetails3;
	private TextView txtExtServiceCheck3;
	private TextView txtExtStatus3;
	
	private EditText editExtDetails4;
	private TextView txtExtServiceCheck4;
	private TextView txtExtStatus4;

	private EditText editExtDetails5;
	private TextView txtExtServiceCheck5;
	private TextView txtExtStatus5;

	private EditText editExtDetails6;
	private TextView txtExtServiceCheck6;
	private TextView txtExtStatus6;

	
	private EditText editIntDetails1;
	private EditText editIntDetailsValue1;
	
	private EditText editIntDetails2;
	private TextView txtIntServiceCheck2;
	private EditText editIntDetails3;
	private TextView txtIntServiceCheck3;
	private EditText editIntDetails4;
	private TextView txtIntServiceCheck4;
	private EditText editIntDetails5;
	private TextView txtIntServiceCheck5;

	
	private EditText editAir1;
	private EditText editWater1;
	private EditText editAir2;
	private EditText editWater2;

	private TextView txtCompCheck1;
	private TextView txtCompCheck2;
	private TextView txtCompCheck3;
	
	
	private EditText txtComments;
	private EditText txtRemedialWorks;
	
	private TextView txtOverAllStatus;
	
	private Button btnGetPumpSig;
	private Button btnGetStackSig;
	private Button btnGetCustSig;
	private Button btnSave;
	private Button btnExit;
	
	private Boolean bGetttingStarted = true;
	
	C_JTJob myJob = JTApplication.getInstance().GetApplicationManager().GetloadedJob(); // <-- Handy Reference
	C_RiserService myRiser = ListRiserServices.CurrentRiser;

	private void findViews() {
		spinLocation = (Spinner)findViewById( R.id.spinLocation );
		editRiserNo = (EditText)findViewById( R.id.editRiserNo );
		editRiserLocation = (EditText)findViewById( R.id.editRiserLocation );
		
		editInletKey = (EditText)findViewById( R.id.editExtInletKey);
		editOutletKey = (EditText)findViewById( R.id.editExtOutletKey);
		
		editExtDetails1 = (EditText)findViewById( R.id.editExtDetails1 );
		txtExtServiceCheck1 = (TextView)findViewById( R.id.txtExtServiceCheck1 );
		txtExtStatus1 = (TextView)findViewById( R.id.txtExtStatus1 );
		
		editExtDetails2 = (EditText)findViewById( R.id.editExtDetails2 );
		txtExtStatus2 = (TextView)findViewById( R.id.txtExtStatus2 );
		editExtDetails3 = (EditText)findViewById( R.id.editExtDetails3 );
		txtExtStatus3 = (TextView)findViewById( R.id.txtExtStatus3 );
		editExtDetails4 = (EditText)findViewById( R.id.editExtDetails4 );
		txtExtStatus4 = (TextView)findViewById( R.id.txtExtStatus4 );
		editExtDetails5 = (EditText)findViewById( R.id.editExtDetails5 );
		txtExtStatus5 = (TextView)findViewById( R.id.txtExtStatus5 );
		editExtDetails6 = (EditText)findViewById( R.id.editExtDetails6 );
		txtExtServiceCheck6 = (TextView)findViewById( R.id.txtExtServiceCheck6 );
		txtExtStatus6 = (TextView)findViewById( R.id.txtExtStatus6 );
		
		txtExtServiceCheck2 = (TextView)findViewById( R.id.txtExtServiceCheck2 );
		txtExtServiceCheck3 = (TextView)findViewById( R.id.txtExtServiceCheck3 );
		txtExtServiceCheck4 = (TextView)findViewById( R.id.txtExtServiceCheck4 );
		txtExtServiceCheck5 = (TextView)findViewById( R.id.txtExtServiceCheck5 );
		
		editIntDetails1 = (EditText)findViewById( R.id.editIntDetails1 );
		editIntDetailsValue1 = (EditText)findViewById( R.id.editIntDetailsValue1 );
		
		editIntDetails2 = (EditText)findViewById( R.id.editIntDetails2 );
		txtIntServiceCheck2 = (TextView)findViewById( R.id.txtIntServiceCheck2 );
		
		editIntDetails3 = (EditText)findViewById( R.id.editIntDetails3 );
		txtIntServiceCheck3 = (TextView)findViewById( R.id.txtIntServiceCheck3 );
		
		editIntDetails4 = (EditText)findViewById( R.id.editIntDetails4 );
		txtIntServiceCheck4 = (TextView)findViewById( R.id.txtIntServiceCheck4 );
		
		editIntDetails5 = (EditText)findViewById( R.id.editIntDetails5 );
		txtIntServiceCheck5 = (TextView)findViewById( R.id.txtIntServiceCheck5 );
		
		editAir1 = (EditText)findViewById( R.id.editAir1 );
		editWater1 = (EditText)findViewById( R.id.editWater1 );
		editAir2 = (EditText)findViewById( R.id.editAir2 );
		editWater2 = (EditText)findViewById( R.id.editWater2 );
		txtCompCheck1 = (TextView)findViewById( R.id.txtCompCheck1 );
		txtCompCheck2 = (TextView)findViewById( R.id.txtCompCheck2 );
		txtCompCheck3 = (TextView)findViewById( R.id.txtCompCheck3 );
		txtComments = (EditText)findViewById( R.id.txtComments );
		txtRemedialWorks = (EditText)findViewById( R.id.txtRemedialWorks );
		txtOverAllStatus = (TextView)findViewById( R.id.txtOverAllStatus );
		txtOverAllStatus = (TextView)findViewById( R.id.txtOverAllStatus );
		btnGetPumpSig = (Button)findViewById( R.id.btnGetPumpSig );
		btnGetStackSig = (Button)findViewById( R.id.btnGetStackSig );
		btnGetCustSig = (Button)findViewById(R.id.btnGetCustSig );
		btnSave = (Button)findViewById( R.id.btnSave );
		btnExit = (Button)findViewById( R.id.btnExit );
	}

    
	 /** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_riserservice);
		
		findViews();
		
		//RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativeLayout1);		
		
		// Stops the scroll view setting focus to the next editText. 
		ScrollView view = (ScrollView)findViewById(R.id.scrollview1);
	    view.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
	    view.setFocusable(true);
	    view.setFocusableInTouchMode(true);
	    view.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            v.requestFocusFromTouch();
	            return false;
	        }
	    });		
	    
	    
	    
	    
	    txtExtServiceCheck1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateYesNo_NA (txtExtServiceCheck1);
			}
		});
	    
	    txtExtServiceCheck2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateYesNo_NA (txtExtServiceCheck2);
			}
		});	    
	    
	    txtExtServiceCheck3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateYesNo_NA (txtExtServiceCheck3);
			}
		});	    
	    

	    txtExtServiceCheck4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateYesNo_NA (txtExtServiceCheck4);
			}
		});	    

	    txtExtServiceCheck5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateYesNo_NA (txtExtServiceCheck5);
			}
		});	    

	    txtExtServiceCheck6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateYesNo_NA (txtExtServiceCheck6);
			}
		});
	    
	    txtExtStatus1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateGOOD_FAILED_BLANK (txtExtStatus1);
			}
		});	    
	    
	    txtExtStatus2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateGOOD_FAILED_BLANK (txtExtStatus2);
			}
		});	    

	    txtExtStatus3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateGOOD_FAILED_BLANK (txtExtStatus3);
			}
		});	    

	    txtExtStatus4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateGOOD_FAILED_BLANK (txtExtStatus4);
			}
		});	    

	    txtExtStatus5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateGOOD_FAILED_BLANK (txtExtStatus5);
			}
		});	    

	    txtExtStatus6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateGOOD_FAILED_BLANK (txtExtStatus6);
			}
		});
	    

	    // Internal Equipment Service Check (Starts at 2 as 1 is a text box
	    txtIntServiceCheck2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateYesNo_NA(txtIntServiceCheck2);
			}
		});
	    
	    txtIntServiceCheck3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateYesNo_NA(txtIntServiceCheck3);
			}
		});
	    
	    txtIntServiceCheck4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateYesNo_NA(txtIntServiceCheck4);
			}
		});

	    txtIntServiceCheck5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateYesNo_NA(txtIntServiceCheck5);
			}
		});

	    
	    
	    
	    
	    
	    txtCompCheck1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateYes_No_Blank(txtCompCheck1);
			}
		});	    
	    

	    txtCompCheck2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateYes_No_Blank(txtCompCheck2);
			}
		});	    

	    txtCompCheck3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStateYes_No_Blank(txtCompCheck3);
			}
		});
	    
	    txtOverAllStatus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetStatePASS_FAIL_BLANK(txtOverAllStatus);
			}
		});	    

	    
	    // *******************************
	    // ********* SAVE BUTTON *********
	    // *******************************
	    btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					String sRet = "";
					sRet = PopulateRiserServiceObjectFromScreen();
					if (sRet.isEmpty()) {
						// Check if ADD was pressed.  represented by AndroidJobRecordID = -2
						// If this is a new item being added, need to add it to the RiserServier collection.
						if ((ListRiserServices.CurrentRiser.getJTRecordID() == -2) ) 					
						{
							ListRiserServices.CurrentRiser.setJobNo(JTApplication.getInstance().GetApplicationManager().GetloadedJob().getJobNo());		// Set Job No.
							ListRiserServices.CurrentRiser.setJTRecordID(-1);	
							ListRiserServices.AddToRiserServiceCollection(ListRiserServices.CurrentRiser);
						}
						
						ListRiserServices.PopulateRiserAvailableList();
						EditRiserService.this.finish();
						
						myJob.setPendingUpload(true);
						JTApplication.getInstance().GetDatabaseManager().SaveJobToCache();
					} else {
						try {
							Toast.makeText(activity2.mycontext1,"Something went wrong saving the Riser. The error is \n" + sRet + "\nPlease check the Riser Number and other data and try again",Toast.LENGTH_LONG).show();
						} catch (Exception e) {
						}
					}
					
				} catch (Exception e) {
					Log.i(TAG + "setOnClickListener",e.getMessage());
				}
			}
		});
	    
        // Pump Signature Button
        Button btnSignature = (Button) findViewById(R.id.btnGetPumpSig);
        btnSignature.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View view) {
        		Intent myIntent = new Intent(EditRiserService.this, CapturePumpSignature.class);
        		myIntent.putExtra("sigid", C_RiserService.PUMPSIGNATURE_ACTIVITY);
        		startActivityForResult(myIntent,C_RiserService.PUMPSIGNATURE_ACTIVITY);		
        	}
        });
        
        // Stack Signature Button
        Button btnStackSignature = (Button) findViewById(R.id.btnGetStackSig);
        btnStackSignature.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View view) {
        		Intent myIntent = new Intent(EditRiserService.this, CapturePumpSignature.class);
        		myIntent.putExtra("sigid", C_RiserService.STACKSIGNATURE_ACTIVITY);
        		startActivityForResult(myIntent,C_RiserService.STACKSIGNATURE_ACTIVITY);		
        	}
        });
        
        // CustomerSignature Button
        Button btnCustSignature = (Button) findViewById(R.id.btnGetCustSig);
        btnCustSignature.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View view) {
        		Intent myIntent = new Intent(EditRiserService.this, CapturePumpSignature.class);
        		myIntent.putExtra("sigid", C_RiserService.CUSTSIGNATURE_ACTIVITY);
        		startActivityForResult(myIntent,C_RiserService.CUSTSIGNATURE_ACTIVITY);		
        	}
        });
        
        
        
	    
	    
	    // ********* EXIT BUTTON *********
	    btnExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditRiserService.this.finish();
			}
		});
	    
 	    
		// ***********************************************************
		// ***************** POPULATE THE SPINNER ********************
		// ***********************************************************
	    // Format X - Description   (X is the riser number)
		try {
			//int riserCount = 0;
			// Need to take the data from mvarRiserLocation <-- ArrayList and populate the spinner with it.
			ArrayList<String> myList = new ArrayList<String>();
			if (myJob.getRiserCount() > 0 ) {
				myList.add("Select a riser - " + " ");

				for (C_RiserLocation s : myJob.getRiserLocations()) {
					//riserCount++;
					// myList.add(s.getNumber() + " - " + s.getLocation() + " - " + s.getOutletQty());
					myList.add(s.getNumber() + " - " + s.getLocation());
				}
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_dropdown_item, myList);
			spinLocation.setAdapter(adapter);

			// Do we have any risers?
			// If this is a NEW riser, allow the user to enter the riser number and other fields.
			if (myJob.getRiserCount() >0) {
				
				// Do not allow user to type in a riser as there is one to select.
		    	LockRiserFields();
			} else {
				editRiserNo.setEnabled(true);
		    	editRiserLocation.setEnabled(true);
			}
		} catch (Exception e) {
			Log.i(TAG, e.getMessage());
		}
	    
	    
		// Add onClick event for the spinner (Drop Down Box)
		// The spinner contains the riser number and location description separated by '-'
		// Once selected use the riser number to locate the class member to extract all data
		// required to populate the screen.
		// If data exists the populated fields will be disabled, preventing users from 
		// changing the contents.
		spinLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		    	// This catches the 1st unwanted event generated by populating the spinner.
		    	if (bGetttingStarted) {
		    		bGetttingStarted = false;
		    	} else {
			        // The spinner returns X - Description
			    	// Split the values and populate the riser number / description boxes.
		    		// Use the riser number to extract all data from the riserlocations class.
			    	String selectedItem = spinLocation.getItemAtPosition(i).toString();
			    	String []parameters =selectedItem.split("-");
			    	
			    	Integer selectedRiserNo = 0;
			    	
			    	try {
				    	selectedRiserNo  = Integer.parseInt(parameters[0].trim());
				    	
				    	for (C_RiserLocation s : myJob.getRiserLocations()) {
							if (s.getNumber() == selectedRiserNo) {
						    	editRiserNo.setText(selectedRiserNo.toString());
						    	editRiserLocation.setText(s.getLocation());
						    	editIntDetails1.setText(s.getOutletQty());
						    	editInletKey.setText(s.getInletKey());
						    	editOutletKey.setText(s.getOutletKey());
						    	
						    	if (!s.getOutletQty().isEmpty()) editIntDetails1.setEnabled(false);
						    	if (!s.getInletKey().isEmpty()) editInletKey.setEnabled(false);
						    	if (!s.getOutletKey().isEmpty()) editOutletKey.setEnabled(false);
						    	break;
							}
						}
				    	
			    	}
			    	catch (NumberFormatException ex) {
			    		// Happens when the "select a riser" is picked.
			    		ClearRiserFields();
				    	if (myJob.getRiserCount() > 0) {		
				    		// Disable fields as there is risers available to be picked.
				    		LockRiserFields();
				    	} else {
					    	EnableRiserFields();
				    	}
			    	}
		    	}
		    } 

		    public void onNothingSelected(AdapterView<?> adapterView) {
		        return;
		    } 
		});
		
		PopulateScreen();
		
	}
	
	

	
	private void LockRiserFields()
	{
		try {
			editRiserNo.setEnabled(false);
	    	editRiserLocation.setEnabled(false);
	    	editIntDetails1.setEnabled(false);
	    	editInletKey.setEnabled(false);
	    	editOutletKey.setEnabled(false);
			
		} catch (Exception ex){
			
		}
	}
	
	private void EnableRiserFields()
	{
		try {
			editRiserNo.setEnabled(true);
	    	editRiserLocation.setEnabled(true);
	    	editIntDetails1.setEnabled(true);
	    	editInletKey.setEnabled(true);
	    	editOutletKey.setEnabled(true);
			
		} catch (Exception ex){
			
		}
	}
	
	private void ClearRiserFields() 
	{
    	editRiserNo.setText("");
    	editRiserLocation.setText("");
    	editIntDetails1.setText("");
    	editInletKey.setText("");
    	editOutletKey.setText("");
	}
	
	
	
//	PopulateScreen();
	
private void PopulateScreen() {
	try {
		C_RiserService rs = ListRiserServices.CurrentRiser;
		
		// Don't attempt to display the riser if its a new one.  (All elements will be null)
		if ((rs.getJTRecordID() != -2) ) { 		
			editRiserNo.setText(rs.getRiserNumber().toString());
			editRiserLocation.setText(rs.getRiserLocation());
			
			editInletKey.setText(rs.getInLetKey());
			editOutletKey.setText(rs.getOutLetKey());
			
			DisplayStateColourYesNo_NA(txtExtServiceCheck1,rs.getExtServiceCheck(0));
			DisplayStateColourYesNo_NA(txtExtServiceCheck2,rs.getExtServiceCheck(1));
			DisplayStateColourYesNo_NA(txtExtServiceCheck3,rs.getExtServiceCheck(2));
			DisplayStateColourYesNo_NA(txtExtServiceCheck4,rs.getExtServiceCheck(3));
			DisplayStateColourYesNo_NA(txtExtServiceCheck5,rs.getExtServiceCheck(4));
			DisplayStateColourYesNo_NA(txtExtServiceCheck6,rs.getExtServiceCheck(5));
	
			editExtDetails1.setText(rs.getExtDetails(0));
			editExtDetails2.setText(rs.getExtDetails(1));
			editExtDetails3.setText(rs.getExtDetails(2));
			editExtDetails4.setText(rs.getExtDetails(3));
			editExtDetails5.setText(rs.getExtDetails(4));
			editExtDetails6.setText(rs.getExtDetails(5));
			
			DisplayStateColourGOOD_FAILED_BLANK(txtExtStatus1, rs.getExtStatus(0));
			DisplayStateColourGOOD_FAILED_BLANK(txtExtStatus2, rs.getExtStatus(1));
			DisplayStateColourGOOD_FAILED_BLANK(txtExtStatus3, rs.getExtStatus(2));
			DisplayStateColourGOOD_FAILED_BLANK(txtExtStatus4, rs.getExtStatus(3));
			DisplayStateColourGOOD_FAILED_BLANK(txtExtStatus5, rs.getExtStatus(4));
			DisplayStateColourGOOD_FAILED_BLANK(txtExtStatus6, rs.getExtStatus(5));
	
			// This is the large box on the right hand side.
			editIntDetailsValue1.setText(rs.getIntDetails(0));
			
			
			DisplayStateColourYesNo_NA(txtIntServiceCheck2, rs.getIntServiceCheck(1));
			DisplayStateColourYesNo_NA(txtIntServiceCheck3, rs.getIntServiceCheck(2));
			DisplayStateColourYesNo_NA(txtIntServiceCheck4, rs.getIntServiceCheck(3));
			DisplayStateColourYesNo_NA(txtIntServiceCheck5, rs.getIntServiceCheck(4));
			
			// This is the small left hand side box. "Outlet Valve (Quantity)"
			editIntDetails1.setText(rs.getIntServiceCheck(0));
			
			
			editIntDetails2.setText(rs.getIntDetails(1));
			editIntDetails3.setText(rs.getIntDetails(2));
			editIntDetails4.setText(rs.getIntDetails(3));
			editIntDetails5.setText(rs.getIntDetails(4));
			
			editAir1.setText(rs.getAir(0));
			editAir2.setText(rs.getAir(1));
			editWater1.setText(rs.getWater(0));
			editWater2.setText(rs.getWater(1));
	
			DisplayStateColourYes_No_Blank(txtCompCheck1, rs.getCompletionChecked(0));
			DisplayStateColourYes_No_Blank(txtCompCheck2, rs.getCompletionChecked(1));
			DisplayStateColourYes_No_Blank(txtCompCheck3, rs.getCompletionChecked(2));
			
			
			txtComments.setText(rs.getComments());
			txtRemedialWorks.setText(rs.getRemedialWorks());
	
			DisplayStateColourPASS_FAIL_BLANK(txtOverAllStatus, rs.getOverAllStatus());
		} else {
			// Arriving here when NEW riser is pressed.
			ClearForm();
			EnableRiserFields();
			// Suggest the next riser number.
			Integer SuggestedRiserNo =0 ;
			SuggestedRiserNo = myJob.getRiserCount() + 1;
			editRiserNo.setText(SuggestedRiserNo.toString());		
		}
		
		
	} catch (Exception e) {
		Log.i(TAG + "PopulateScreen", e.getMessage());
	}
}

	private void ClearForm()
	{
		editRiserNo.setText("");
		editRiserLocation.setText("");
		
		editInletKey.setText("");
		editOutletKey.setText("");
		
		DisplayStateColourYesNo_NA(txtExtServiceCheck1,"");
		DisplayStateColourYesNo_NA(txtExtServiceCheck2,"");
		DisplayStateColourYesNo_NA(txtExtServiceCheck3,"");
		DisplayStateColourYesNo_NA(txtExtServiceCheck4,"");
		DisplayStateColourYesNo_NA(txtExtServiceCheck5,"");
		DisplayStateColourYesNo_NA(txtExtServiceCheck6,"");

		editExtDetails1.setText("");
		editExtDetails2.setText("");
		editExtDetails3.setText("");
		editExtDetails4.setText("");
		editExtDetails5.setText("");
		editExtDetails6.setText("");
		
		DisplayStateColourGOOD_FAILED_BLANK(txtExtStatus1, "");
		DisplayStateColourGOOD_FAILED_BLANK(txtExtStatus2, "");
		DisplayStateColourGOOD_FAILED_BLANK(txtExtStatus3, "");
		DisplayStateColourGOOD_FAILED_BLANK(txtExtStatus4, "");
		DisplayStateColourGOOD_FAILED_BLANK(txtExtStatus5, "");
		DisplayStateColourGOOD_FAILED_BLANK(txtExtStatus6, "");

		
		editIntDetailsValue1.setText("");
		
		DisplayStateColourYesNo_NA(txtIntServiceCheck2, "");
		DisplayStateColourYesNo_NA(txtIntServiceCheck3, "");
		DisplayStateColourYesNo_NA(txtIntServiceCheck4, "");
		DisplayStateColourYesNo_NA(txtIntServiceCheck5, "");
		
		editIntDetails1.setText("");
		editIntDetails2.setText("");
		editIntDetails3.setText("");
		editIntDetails4.setText("");
		editIntDetails5.setText("");
		
		editAir1.setText("");
		editAir2.setText("");
		editWater1.setText("");
		editWater1.setText("");

		DisplayStateColourYes_No_Blank(txtCompCheck1, "");
		DisplayStateColourYes_No_Blank(txtCompCheck2, "");
		DisplayStateColourYes_No_Blank(txtCompCheck3, "");
		
		
		txtComments.setText("");
		txtRemedialWorks.setText("");

		DisplayStateColourPASS_FAIL_BLANK(txtOverAllStatus, "");
		
	}
	
	private void DisplayStateColourYesNo_NA(TextView myView,String sValue)
	{

		myView.setText(sValue);
		if (sValue.equals(getResources().getString(R.string.yes))) {
			myView.setBackgroundColor(getResources().getColor(R.color.Yes));
		} else if (sValue.equals(getResources().getString(R.string.no))) {
			myView.setBackgroundColor(getResources().getColor(R.color.No ));
		} else if (sValue.equals(getResources().getString(R.string.notapplicable))) {
			myView.setBackgroundColor(getResources().getColor(R.color.Netural ));
		} else if (sValue.equals("")) {
			myView.setBackgroundColor(getResources().getColor(R.color.Netural ));
		}
	}
	
	private void DisplayStateColourGOOD_FAILED_BLANK(TextView myView,String sValue)
	{

		myView.setText(sValue);
		if (sValue.equals(getResources().getString(R.string.good))) {
			myView.setBackgroundColor(getResources().getColor(R.color.Yes));
			
		} else if (sValue.equals(getResources().getString(R.string.failed ))) {
			myView.setBackgroundColor(getResources().getColor(R.color.No ));
			
		} else if (sValue.equals(getResources().getString(R.string.blank))) {
			myView.setBackgroundColor(getResources().getColor(R.color.Netural ));
		} else if (sValue.equals("")) {
			myView.setBackgroundColor(getResources().getColor(R.color.Netural ));
		}
	}
	
	private void DisplayStateColourYes_No_Blank(TextView myView,String sValue)
	{
		myView.setText(sValue);
		if (sValue.equals(getResources().getString(R.string.yes))) {
			myView.setBackgroundColor(getResources().getColor(R.color.Yes));
			
		} else if (sValue.equals(getResources().getString(R.string.no ))) {
			myView.setBackgroundColor(getResources().getColor(R.color.No ));
			
		} else if (sValue.equals(getResources().getString(R.string.blank))) {
			myView.setBackgroundColor(getResources().getColor(R.color.Netural ));
		} else if (sValue.equals("")) {
			myView.setBackgroundColor(getResources().getColor(R.color.Netural ));
		}
	}
	
	private void DisplayStateColourPASS_FAIL_BLANK(TextView myView,String sValue)
	{
		myView.setText(sValue);
		
		if (sValue.equals(getResources().getString(R.string.pass ))) {
			myView.setBackgroundColor(getResources().getColor(R.color.Yes));
			
		} else if (sValue.equals(getResources().getString(R.string.fail ))) {
			myView.setBackgroundColor(getResources().getColor(R.color.No ));
			
		} else if (sValue.equals(getResources().getString(R.string.blank))) {
			myView.setBackgroundColor(getResources().getColor(R.color.Netural ));
		} else if (sValue.equals("")) {
			myView.setBackgroundColor(getResources().getColor(R.color.Netural ));
		}
	}
	
	
	private void SetStateYesNo_NA(TextView myView)
	{
		String StateValue1 = myView.getText().toString();
		if (StateValue1.equals(getResources().getString(R.string.yes))) {
			myView.setText(R.string.no);
			myView.setBackgroundColor(getResources().getColor(R.color.No));
		} else if (StateValue1.equals(getResources().getString(R.string.no))) { 
			myView.setText(R.string.notapplicable);
			myView.setBackgroundColor(getResources().getColor(R.color.Netural));
		} else if (StateValue1.equals(getResources().getString(R.string.notapplicable)) || ((StateValue1.equals(getResources().getString(R.string.blank))))) {
			myView.setText(R.string.yes);
			myView.setBackgroundColor(getResources().getColor(R.color.Yes));
		}
	}
	
	private void SetStateGOOD_FAILED_BLANK(TextView myView)
	{
		String StateValue1 = myView.getText().toString();
		if (StateValue1.equals(getResources().getString(R.string.blank))) {
			myView.setText(R.string.good);
			myView.setBackgroundColor(getResources().getColor(R.color.Yes));
		} else if (StateValue1.equals(getResources().getString(R.string.good))) { 
			myView.setText(R.string.failed);
			myView.setBackgroundColor(getResources().getColor(R.color.No));
		} else if (StateValue1.equals(getResources().getString(R.string.failed))) {
			myView.setText(R.string.blank);
			myView.setBackgroundColor(getResources().getColor(R.color.Netural));
		}
	}

	private void SetStateYes_No_Blank(TextView myView)
	{
		String StateValue1 = myView.getText().toString();
		if (StateValue1.equals(getResources().getString(R.string.yes))) {
			myView.setText(R.string.no);
			myView.setBackgroundColor(getResources().getColor(R.color.No));
		} else if (StateValue1.equals(getResources().getString(R.string.no))) { 
			myView.setText(R.string.blank);
			myView.setBackgroundColor(getResources().getColor(R.color.Netural));
		} else if (((StateValue1.equals(getResources().getString(R.string.blank))))) {
			myView.setText(R.string.yes);
			myView.setBackgroundColor(getResources().getColor(R.color.Yes));
		}
	}
	
	private void SetStatePASS_FAIL_BLANK(TextView myView)
	{
		String StateValue1 = myView.getText().toString();
		if (StateValue1.equals(getResources().getString(R.string.blank))) {
			myView.setText(R.string.pass);
			myView.setBackgroundColor(getResources().getColor(R.color.Yes));
		} else if (StateValue1.equals(getResources().getString(R.string.pass))) { 
			myView.setText(R.string.fail);
			myView.setBackgroundColor(getResources().getColor(R.color.No));
		} else if (StateValue1.equals(getResources().getString(R.string.fail))) {
			myView.setText(R.string.blank);
			myView.setBackgroundColor(getResources().getColor(R.color.Netural));
		}
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
	    View view = getCurrentFocus();
	    boolean ret = super.dispatchTouchEvent(event);

	    if (view instanceof EditText) {
	        View w = getCurrentFocus();
	        int scrcoords[] = new int[2];
	        w.getLocationOnScreen(scrcoords);
	        float x = event.getRawX() + w.getLeft() - scrcoords[0];
	        float y = event.getRawY() + w.getTop() - scrcoords[1];
	        
	        if (event.getAction() == MotionEvent.ACTION_UP 
	 && (x < w.getLeft() || x >= w.getRight() 
	 || y < w.getTop() || y > w.getBottom()) ) { 
	            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
	        }
	    }
	 return ret;
	}
	
	
	// Return 1 = An error has occured.
	// 0 = OK to save
	public String PopulateRiserServiceObjectFromScreen() {
		try {
			C_RiserService rs = ListRiserServices.CurrentRiser;		// Handy reference (this is instantiated in ListRiserServices).
			try {
				rs.setRiserNumber(Integer.parseInt(editRiserNo.getText().toString()));	
			} catch (NumberFormatException e) {
				return e.getMessage();
			}
			
			rs.setRiserLocation(editRiserLocation.getText().toString());
			rs.setJobNo(myJob.getJobNo());
			
			rs.setInLetKey(editInletKey.getText().toString());
			rs.setOutLetKey(editOutletKey.getText().toString());
			
			rs.setExtServiceCheck(0,txtExtServiceCheck1.getText().toString());
			rs.setExtServiceCheck(1,txtExtServiceCheck2.getText().toString());
			rs.setExtServiceCheck(2,txtExtServiceCheck3.getText().toString());
			rs.setExtServiceCheck(3,txtExtServiceCheck4.getText().toString());
			rs.setExtServiceCheck(4,txtExtServiceCheck5.getText().toString());
			rs.setExtServiceCheck(5,txtExtServiceCheck6.getText().toString());
			
			rs.setExtDetails(0,editExtDetails1.getText().toString());
			rs.setExtDetails(1,editExtDetails2.getText().toString());
			rs.setExtDetails(2,editExtDetails3.getText().toString());
			rs.setExtDetails(3,editExtDetails4.getText().toString());
			rs.setExtDetails(4,editExtDetails5.getText().toString());
			rs.setExtDetails(5,editExtDetails6.getText().toString()); 
			
			rs.setExtStatus(0,txtExtStatus1.getText().toString());
			rs.setExtStatus(1,txtExtStatus2.getText().toString());
			rs.setExtStatus(2,txtExtStatus3.getText().toString());
			rs.setExtStatus(3,txtExtStatus4.getText().toString());
			rs.setExtStatus(4,txtExtStatus5.getText().toString());
			rs.setExtStatus(5,txtExtStatus6.getText().toString());
			
			rs.setIntServiceCheck(0, editIntDetails1.getText().toString());
			rs.setIntServiceCheck(1, txtIntServiceCheck2.getText().toString());
			rs.setIntServiceCheck(2, txtIntServiceCheck3.getText().toString());
			rs.setIntServiceCheck(3, txtIntServiceCheck4.getText().toString());
			rs.setIntServiceCheck(4, txtIntServiceCheck5.getText().toString());
			
			rs.setIntDetails(0,editIntDetailsValue1.getText().toString());
			rs.setIntDetails(1,editIntDetails2.getText().toString());
			rs.setIntDetails(2,editIntDetails3.getText().toString());
			rs.setIntDetails(3,editIntDetails4.getText().toString());
			rs.setIntDetails(4,editIntDetails5.getText().toString());
			
			rs.setAir(0,editAir1.getText().toString());
			rs.setAir(1,editAir2.getText().toString());
			
			rs.setWater(0,editWater1.getText().toString());
			rs.setWater(1,editWater2.getText().toString());
			
			rs.setCompletionChecked(0,txtCompCheck1.getText().toString());
			rs.setCompletionChecked(1,txtCompCheck2.getText().toString());
			rs.setCompletionChecked(2,txtCompCheck3.getText().toString());

			rs.setComments(txtComments.getText().toString());
			rs.setRemedialWorks(txtRemedialWorks.getText().toString());

			rs.setOverAllStatus(txtOverAllStatus.getText().toString());
			
		} catch (Exception e) {
			return e.getMessage();
		}

		return "";
	}
	
	
	
}

