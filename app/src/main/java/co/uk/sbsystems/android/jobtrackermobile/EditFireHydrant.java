package co.uk.sbsystems.android.jobtrackermobile;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class EditFireHydrant extends Activity{

 	TextView hydrantNumber;
	EditText hydrantLocation;
	TextView passState1;
	TextView passState2;
	TextView passState3;
	TextView passState4;
	TextView passState5;
	TextView passState6;
	TextView passState7;
	TextView passState8;
	TextView passState9;
	EditText details10;
	EditText details11;
	EditText details12;
	Button butSave;
	Button butExit;
    
	C_JTJob myJob = JTApplication.getInstance().GetApplicationManager().GetloadedJob(); // <-- Handy Reference
	
	 /** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editfirehydrant);
		
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
	    
		hydrantNumber = (TextView) findViewById(R.id.textHydrantNumberValue);
	    hydrantLocation =(EditText) findViewById(R.id.editHydrantLocation);
		passState1 = (TextView) findViewById(R.id.textDetail1Value);
		passState2 = (TextView) findViewById(R.id.textDetail2Value);
		passState3 = (TextView) findViewById(R.id.textDetail3Value);
		passState4 = (TextView) findViewById(R.id.textDetail4Value);
		passState5 = (TextView) findViewById(R.id.textDetail5Value);
		passState6 = (TextView) findViewById(R.id.textDetail6Value);
		passState7 = (TextView) findViewById(R.id.textDetail7Value);
		passState8 = (TextView) findViewById(R.id.textDetail8Value);
		passState9 = (TextView) findViewById(R.id.textDetail9Value);
	    details10 =(EditText) findViewById(R.id.editDetail10Value);
	    details11 =(EditText) findViewById(R.id.editDetail11Value);
	    details12 =(EditText) findViewById(R.id.editDetail12Value);
		butExit = (Button) findViewById(R.id.butExit);
		butSave = (Button) findViewById(R.id.butSave);
	
		passState1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HideKeyboard();
				// Toggle state of the label
				String StateValue1 = passState1.getText().toString();
				if (StateValue1.equals(getResources().getString(R.string.yes))) {
					passState1.setText(R.string.no);
					passState1.setBackgroundColor(getResources().getColor(R.color.No));
				} else {
					passState1.setText(R.string.yes);
					passState1.setBackgroundColor(getResources().getColor(R.color.Yes));
				}
			}
		});
		
		passState2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HideKeyboard();
				// Toggle state of the label
				String StateValue2 = passState2.getText().toString();
				if (StateValue2.equals(getResources().getString(R.string.yes))) {
					passState2.setText(R.string.no);
					passState2.setBackgroundColor(getResources().getColor(R.color.No));
				} else {
					passState2.setText(R.string.yes);
					passState2.setBackgroundColor(getResources().getColor(R.color.Yes));
				}
			}
		});

		passState3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HideKeyboard();
				// Toggle state of the label
				String StateValue3 = passState3.getText().toString();
				if (StateValue3.equals(getResources().getString(R.string.yes))) {
					passState3.setText(R.string.no);
					passState3.setBackgroundColor(getResources().getColor(R.color.No));
				} else {
					passState3.setText(R.string.yes);
					passState3.setBackgroundColor(getResources().getColor(R.color.Yes));
				}
			}
		});

		passState4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HideKeyboard();
				// Toggle state of the label
				String StateValue4 = passState4.getText().toString();
				if (StateValue4.equals(getResources().getString(R.string.yes))) {
					passState4.setText(R.string.no);
					passState4.setBackgroundColor(getResources().getColor(R.color.No));
				} else {
					passState4.setText(R.string.yes);
					passState4.setBackgroundColor(getResources().getColor(R.color.Yes));
				}
			}
		});

		passState5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HideKeyboard();
				// Toggle state of the label
				String StateValue5 = passState5.getText().toString();
				if (StateValue5.equals(getResources().getString(R.string.yes))) {
					passState5.setText(R.string.no);
					passState5.setBackgroundColor(getResources().getColor(R.color.No));
				} else {
					passState5.setText(R.string.yes);
					passState5.setBackgroundColor(getResources().getColor(R.color.Yes));
				}
			}
		});


		passState6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HideKeyboard();
				// Toggle state of the label
				String StateValue6 = passState6.getText().toString();
				if (StateValue6.equals(getResources().getString(R.string.yes))) {
					passState6.setText(R.string.no);
					passState6.setBackgroundColor(getResources().getColor(R.color.No));
				} else {
					passState6.setText(R.string.yes);
					passState6.setBackgroundColor(getResources().getColor(R.color.Yes));
				}
			}
		});

		passState7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HideKeyboard();
				// Toggle state of the label
				String StateValue7 = passState7.getText().toString();
				if (StateValue7.equals(getResources().getString(R.string.yes))) {
					passState7.setText(R.string.no);
					passState7.setBackgroundColor(getResources().getColor(R.color.No));
				} else {
					passState7.setText(R.string.yes);
					passState7.setBackgroundColor(getResources().getColor(R.color.Yes));
				}
			}
		});

		passState8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HideKeyboard();
				// Toggle state of the label
				String StateValue8 = passState8.getText().toString();
				if (StateValue8.equals(getResources().getString(R.string.yes))) {
					passState8.setText(R.string.no);
					passState8.setBackgroundColor(getResources().getColor(R.color.No));
				} else {
					passState8.setText(R.string.yes);
					passState8.setBackgroundColor(getResources().getColor(R.color.Yes));
				}
			}
		});

		
		passState9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HideKeyboard();
				// Toggle state of the label
				String StateValue9 = passState9.getText().toString();
				if (StateValue9.equals(getResources().getString(R.string.yes))) {
					passState9.setText(R.string.no);
					passState9.setBackgroundColor(getResources().getColor(R.color.No));
				} else {
					passState9.setText(R.string.yes);
					passState9.setBackgroundColor(getResources().getColor(R.color.Yes));
				}
			}
		});
		
		
		
		
		// Save button
		butSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Integer NextHydrantNumber;
				
				// User has just clicked Save.  
				PopulateFireHydrantObjectFromScreen();
				
				// Check if ADD was pressed.  represented by AndroidJobRecordID = -2
				// If this is a new item being added, need to add it to the FireHydrant collection.
				if ((ListFireHydrants.CurrentFireHydrant.getJTRecordID() == -2) && (ListFireHydrants.CurrentFireHydrant.getNumber() == 0)) 					
				{
					ListFireHydrants.CurrentFireHydrant.setJobNo(JTApplication.getInstance().GetApplicationManager().GetloadedJob().getJobNo());		// Set Job No.
					if (ListFireHydrants.CurrentFireHydrant.getEngineer().equals("")) {
						ListFireHydrants.CurrentFireHydrant.setEngineer(JTApplication.getInstance().GetApplicationManager().getDeviceAssignedEngineerName());
					}
					ListFireHydrants.CurrentFireHydrant.setRecordID(-1);	
					NextHydrantNumber = ListFireHydrants.FireHydrant.size();
					++NextHydrantNumber;
					ListFireHydrants.CurrentFireHydrant.setNumber(NextHydrantNumber);
					ListFireHydrants.AddToFireHydrantCollection(ListFireHydrants.CurrentFireHydrant);
				}
				
				ListFireHydrants.PopulateHydrantAvailableList();
				
				myJob.setPendingUpload(true);
				JTApplication.getInstance().GetDatabaseManager().SaveJobToCache();
				
				EditFireHydrant.this.finish();
			}
		});
		
		
		// Exit Button
		butExit.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditFireHydrant.this.finish();
			}
		});
	
		
		
		// Start the coding here.
		PopulateScreen();
		
		
	}
	
//		PopulateScreen();
// 		ListFireHydrants.CurrentFireHydrant references a C_FireHydrant object created in ListFireHydrants. 	
	private void PopulateScreen() {
		Integer NextHydrantNumber;
		try {
			if ((ListFireHydrants.CurrentFireHydrant.getJTRecordID() == -2) && (ListFireHydrants.CurrentFireHydrant.getNumber() == 0)) { 
				NextHydrantNumber = ListFireHydrants.FireHydrant.size();
				NextHydrantNumber ++;
				hydrantNumber.setText(NextHydrantNumber.toString());
			}
			else {
				hydrantNumber.setText(ListFireHydrants.CurrentFireHydrant.getNumber().toString());
			}
			hydrantLocation.setText(ListFireHydrants.CurrentFireHydrant.getHydrantLocation());
			DisplayPassState(passState1,ListFireHydrants.CurrentFireHydrant.getDetails(0));
			DisplayPassState(passState2,ListFireHydrants.CurrentFireHydrant.getDetails(1));
			DisplayPassState(passState3,ListFireHydrants.CurrentFireHydrant.getDetails(2));
			DisplayPassState(passState4,ListFireHydrants.CurrentFireHydrant.getDetails(3));
			DisplayPassState(passState5,ListFireHydrants.CurrentFireHydrant.getDetails(4));
			DisplayPassState(passState6,ListFireHydrants.CurrentFireHydrant.getDetails(5));
			DisplayPassState(passState7,ListFireHydrants.CurrentFireHydrant.getDetails(6));
			DisplayPassState(passState8,ListFireHydrants.CurrentFireHydrant.getDetails(7));
			DisplayPassState(passState9,ListFireHydrants.CurrentFireHydrant.getDetails(8));
			details10.setText(ListFireHydrants.CurrentFireHydrant.getDetails(9));
			details11.setText(ListFireHydrants.CurrentFireHydrant.getDetails(10));
			details12.setText(ListFireHydrants.CurrentFireHydrant.getDetails(11));
			
			
		} catch (Exception e) {
			Log.i("EditPartsRequired.PopulateScreen: Exception", e.getMessage());
		}
	}
	
// This will set the pass State boxes to Yes (Green) or No (Red) or Blank (Gray) 
	private void DisplayPassState(TextView myView,String myStatus) {
		try {
				if (myStatus != null) {
					if (myStatus.equals(getResources().getString(R.string.yes))) {
						myView.setText(myStatus);
						myView.setBackgroundColor(getResources().getColor(R.color.Yes));
					} else if (myStatus.equals(getResources().getString(R.string.no))) {
						myView.setText(myStatus);
						myView.setBackgroundColor(getResources().getColor(R.color.No));
					} else if (myStatus.equals(getResources().getString(R.string.blank))) {
						myView.setText("");
						myView.setBackgroundColor(getResources().getColor(R.color.Netural));
					}
				}
		} catch (Exception ex) {
			Log.i("DisplayPassState",ex.getMessage());
		}
	}
	
 
	
	// Populate Object from the screen.
	private void PopulateFireHydrantObjectFromScreen()
	{
		try {
			C_FireHydrant fh = ListFireHydrants.CurrentFireHydrant;		// Handy reference (this is instantiated in ListFireHydrants).
			
			fh.setHydrantLocation(hydrantLocation.getText().toString());
			fh.setDetails(passState1.getText().toString(),0);
			fh.setDetails(passState2.getText().toString(),1);
			fh.setDetails(passState3.getText().toString(),2);
			fh.setDetails(passState4.getText().toString(),3);
			fh.setDetails(passState5.getText().toString(),4);
			fh.setDetails(passState6.getText().toString(),5);
			fh.setDetails(passState7.getText().toString(),6);
			fh.setDetails(passState8.getText().toString(),7);
			fh.setDetails(passState9.getText().toString(),8);
			fh.setDetails(details10.getText().toString(),9);
			fh.setDetails(details11.getText().toString(),10);
			fh.setDetails(details12.getText().toString(),11);
			
			// There are some other elements that need populating.
			// Address, PostCodde, Test Date, Engineer
			
			C_JTJob job = JTApplication.getInstance().GetApplicationManager().GetloadedJob();

			if (fh.getSiteAddress().equals("")) {
				//Calendar calendar = Calendar.getInstance();
				fh.setSiteAddress(job.getSiteAddress().getAddress());
				fh.setSitePostCode(job.getSiteAddress().getPostCode());
			}
			if (fh.getTestDate().equals("")) {
				SimpleDateFormat dfDate  = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");   
				Calendar c = Calendar.getInstance();
				String date = dfDate.format(c.getTime());				
				fh.setTestDate(date);
			}
			
			if (fh.getEngineer().equals("")) {
				fh.setEngineer(JTApplication.getInstance().GetApplicationManager().getDeviceAssignedEngineerName());
			}
					
			
		} catch(Exception ex) {
			Log.e("PopulateFireHydrantObjectFromScreen", ex.getMessage());
		}
	}
	
	private void HideKeyboard() 
	{
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(hydrantLocation.getWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY);
	}
		

}

