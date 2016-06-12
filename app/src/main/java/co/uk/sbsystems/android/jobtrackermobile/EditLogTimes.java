package co.uk.sbsystems.android.jobtrackermobile;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * @author Sam Sherwin (When I was learning Java)
 *
 */
public class EditLogTimes extends Activity{

	TextView jobDate;
	TextView startTime;
	TextView endTime;
	TextView dataValue;
	TextView valueLabel;
	
	TextView labStartTime;
	TextView labEndTime;
	TextView txtValue;
	
	Spinner spinner1;
	Button butSave;
	Button butExit;
	Button butCalendar;
	Button butStartTime;
	Button butEndTime;
	
    private int mYear = Calendar.getInstance().YEAR;
    private int mMonth = Calendar.getInstance().MONTH;
    private int mDay  = Calendar.getInstance().DAY_OF_MONTH;
    private int mHour = Calendar.getInstance().HOUR;
    private int mMinute  = Calendar.getInstance().MINUTE;

    static final int DATE_DIALOG_ID = 0;
    static final int START_TIME_DIALOG_ID = 1;
    static final int END_TIME_DIALOG_ID = 2;


    
	 /** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editlogtimes);
		
		spinner1 = (Spinner) findViewById(R.id.spinnerType);
		jobDate = (TextView) findViewById(R.id.txtDate);
		startTime = (TextView) findViewById(R.id.txtStartTime);
		endTime = (TextView) findViewById(R.id.txtEndTime);
		dataValue = (TextView) findViewById(R.id.txtValue);
		valueLabel = (TextView) findViewById(R.id.labValue);
		
		labStartTime = (TextView) findViewById(R.id.labStartTime);
		labEndTime = (TextView) findViewById(R.id.labEndTime);
		txtValue = (TextView) findViewById(R.id.txtValue);
		butSave =(Button) findViewById(R.id.butSave);
		butExit = (Button) findViewById(R.id.butExit);
		butCalendar = (Button) findViewById(R.id.butCalendarx);
		butStartTime = (Button) findViewById(R.id.butStartTime);
		butEndTime = (Button) findViewById(R.id.butEndTime);
		
		// Save button
		butSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// User has just clicked Save.  
				PopulateTimeObjectFromScreen();
				
				// Check if ADD was pressed.  represented by AndroidJoBrecordID = -2
				// If this is a new item being added, need to add it to the JobTimes collection.
				if (LogTimes.CurrentJobTime.getAndroidJobRecordID() == -2)
				{
					LogTimes.CurrentJobTime.setJobNo(JTApplication.getInstance().GetApplicationManager().GetloadedJob().getJobNo());		// Set Job No.
					LogTimes.CurrentJobTime.setEngName(JTApplication.getInstance().GetApplicationManager().getDeviceAssignedEngineerName());
					LogTimes.CurrentJobTime.setAndroidJobRecordID(-1);	
					LogTimes.AddToJobTimeCollection(LogTimes.CurrentJobTime);
				}
				
				LogTimes.PopulateJobTimesList();
				EditLogTimes.this.finish();
			}
		});
		
		// Exit Button
		butExit.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditLogTimes.this.finish();
			}
		});
	
		// Set Start Time Button
		butStartTime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
 				showDialog(START_TIME_DIALOG_ID);
 			}
		});
		
		// Set End Time Button
		butEndTime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(END_TIME_DIALOG_ID);
				
			}
		});
		
		
		// Calendar Button
		butCalendar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Calendar cal = Calendar.getInstance();
	   			SimpleDateFormat formatter;
	   			Date date = null;
	   			formatter = new SimpleDateFormat("dd/MM/yy");
	   			
			try {
				// Read the date from the txtDate label. 
				date = (Date)formatter.parse(jobDate.getText().toString());
			} catch (ParseException e) {
				// No valid date available, use todays date.
				date = cal.getTime();
			}
	   			cal.setTime(date);
	   			
	   			mYear = cal.get(Calendar.YEAR);
	   			mMonth =cal.get(Calendar.MONTH);
	   			mDay =cal.get(Calendar.DAY_OF_MONTH);
	   			
				showDialog(DATE_DIALOG_ID);				
				
			}
		});
		
		// Add onClick event for the spinner (Drop Down Box)
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { 
		        // Your code here
		    	String selectedItem = spinner1.getItemAtPosition(i).toString();
		    	ConstructScreen(LogTimes.CurrentJobTime.TimeTypeFromString(selectedItem));
		    } 

		    public void onNothingSelected(AdapterView<?> adapterView) {
		        return;
		    } 
		});		
		
		try {
			ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this,R.array.jobTimeTypes,android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner1.setAdapter(adapter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("EditLogTimes.OnCreate: Exception", e.getMessage());
		}
		
		PopulateScreen();
		ConstructScreen(LogTimes.CurrentJobTime.getTimeType());
	}
	
	// Need the date inserted here otherwise parse fails.
	private TimePickerDialog.OnTimeSetListener mStartTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			Calendar calendar = Calendar.getInstance();
			Long myStartTime;
			calendar.set(1970,1,1,view.getCurrentHour(),view.getCurrentMinute(),0);
			myStartTime = calendar.getTimeInMillis();
			String myFinalTime; 
			
			try {
				SimpleDateFormat dateFormat;
				dateFormat = new SimpleDateFormat("hh:mm aaa");
				myFinalTime = dateFormat.format(myStartTime).toString();
				startTime.setText(myFinalTime);
				
				
			} catch (Exception e) {
				Log.i("Error EditLogTimes.OnTimeSetListener",e.getMessage());
			}
		}
	};
	
	private TimePickerDialog.OnTimeSetListener mEndTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			Calendar calendar = Calendar.getInstance();
			Long myEndTime;
			calendar.set(1970,1,1,view.getCurrentHour(),view.getCurrentMinute(),0);
			myEndTime = calendar.getTimeInMillis();
			String myFinalTime; 
			
			try {
				SimpleDateFormat dateFormat;
				dateFormat = new SimpleDateFormat("hh:mm aaa");
				myFinalTime = dateFormat.format(myEndTime).toString();
				endTime.setText(myFinalTime);
				
			} catch (Exception e) {
				Log.i("Error EditLogTimes.OnTimeSetListener",e.getMessage());
			}
		}
	};
	
	
	
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {                
		@Override
    	public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) { 
    		mYear = year;                    
    		mMonth = monthOfYear;                    
    		mDay = dayOfMonth;
    		updateDateDisplay(jobDate);
    	}            
	};	        
    
    
	
	// Populate the dialog boxes prior to being shown.
	@Override
	protected void onPrepareDialog(final int id, final Dialog dialog) {
		final Calendar calendar = Calendar.getInstance();
		String myTime;
		SimpleDateFormat formatter;
		Date date = null;
		Long myStartTime;
		
		switch (id) {
			case DATE_DIALOG_ID:
			    //update to current Date
			    ((DatePickerDialog) dialog).updateDate(mYear,mMonth,mDay);
			    break;
		   
			case START_TIME_DIALOG_ID:
				StringBuilder myBoxTime = new StringBuilder();
				myTime = startTime.getText().toString(); 
				
				// Create vaid date format.
				myBoxTime.append("01/01/1970 ");
				
				// Coming here with a time already entered by the user has the format HH:mm aaa (seconds are missing)
				// If no time specified (When adding a new time log) then the we will have "HH:mm:ss aaa")
				if (myTime.length() == 0) {
					myBoxTime.append(myTime).append(":00");
					formatter = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss aaa");
				} else {
					myBoxTime.append(myTime);
					formatter = new SimpleDateFormat("dd/mm/yyyy hh:mm aaa");
				}

				try { 
					date = formatter.parse(myBoxTime.toString());
				} catch (Exception e) {
					date = calendar.getTime();
				}

				// Set time picker opening time.
				((TimePickerDialog) dialog).updateTime(date.getHours(),date.getMinutes());
				
			    break;
			    
			case END_TIME_DIALOG_ID:
				StringBuilder myEndBoxTime = new StringBuilder();
				myTime = endTime.getText().toString(); 
				
				// Create valid date format.
				myEndBoxTime.append("01/01/1970 ");
				
				// Coming here with a time already entered by the user has the format HH:mm aaa (seconds are missing)
				// If no time specified (When adding a new time log) then the we will have "HH:mm:ss aaa")
				if (myTime.length() == 0) {
					myEndBoxTime.append(myTime).append(":00");
					formatter = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss aaa");
				} else {
					myEndBoxTime.append(myTime);
					formatter = new SimpleDateFormat("dd/mm/yyyy hh:mm aaa");
				}

				try { 
					date = formatter.parse(myEndBoxTime.toString());
				} catch (Exception e) {
					date = calendar.getTime();
				}

				((TimePickerDialog) dialog).updateTime(date.getHours(),date.getMinutes());
			    break;
	  }
	}	
	
    @Override
    protected Dialog onCreateDialog(int id) {
    	

    	try {
		   	 switch (id) {    
		   		case DATE_DIALOG_ID:       
		   			return new DatePickerDialog(this,mDateSetListener,mYear, mMonth, mDay);
		   			
		   		case START_TIME_DIALOG_ID:
		   			return new TimePickerDialog(this,mStartTimeSetListener,mHour,mMinute,false);
		   			
		   		case END_TIME_DIALOG_ID:
		   			return new TimePickerDialog(this,mEndTimeSetListener,mHour,mMinute,false);
		   	}
    	} catch(Exception e) {
    		Log.i("EditLogTimes.onCreateDialog Exception",e.getMessage());
    	}
   	return null;
   }	
	
	
	private void PopulateScreen() {
		try {
			int TimeType;
			String formattedValue="";
			String myDate = "";
			Calendar c = Calendar.getInstance(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			ArrayAdapter myAdap = (ArrayAdapter) spinner1.getAdapter();
			int spinPos = myAdap.getPosition(LogTimes.CurrentJobTime.TimeTypeToString());
			spinner1.setSelection(spinPos);
			
			// If no date available use todays date.
			myDate = JTApplication.getInstance().GetDatabaseManager().fromEpochStringToDate(LogTimes.CurrentJobTime.getEventDate());
			if (myDate.length() ==0) {
				myDate = dateFormat.format(c.getTime());
			} 
			
			jobDate.setText(myDate);
			
			startTime.setText(JTApplication.getInstance().GetDatabaseManager().fromEpochStringToLongTime(LogTimes.CurrentJobTime.getStartTime()));
			endTime.setText(JTApplication.getInstance().GetDatabaseManager().fromEpochStringToLongTime(LogTimes.CurrentJobTime.getEndTime()));
			
			TimeType  = LogTimes.CurrentJobTime.getTimeType();
			valueLabel.setText(GetLabelDescription(TimeType));

			switch (TimeType) {
				case C_JobTimes.TIMETYPE_MILEAGETO_V:
				case C_JobTimes.TIMETYPE_MILEAGEFROM_V:
				case C_JobTimes.TIMETYPE_MILEAGE_V:
				case C_JobTimes.TIMETYPE_TRAVELTO_V:
				case C_JobTimes.TIMETYPE_TRAVELFROM_V:
					
					formattedValue  = DisplayWholeNumber(LogTimes.CurrentJobTime.getEntryValue());
					break;
				
				case C_JobTimes.TIMETYPE_CALLOUT_V:
				case C_JobTimes.TIMETYPE_FIXEDCOST_V:
					formattedValue  = DisplayCurrency(LogTimes.CurrentJobTime.getEntryValue());
					break;
				
			}
			dataValue.setText(formattedValue);
			
			
			ConstructScreen(LogTimes.CurrentJobTime.getTimeType());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("EditLogTimes.PopulateScreen: Exception", e.getMessage());
		}
	}
	
	private String GetLabelDescription(Integer DataType) {
		String LabelDesc= "";
		
		switch (DataType) {
			case C_JobTimes.TIMETYPE_TRAVEL_V:						// Travel
					LabelDesc = "";
				break;
				
			case C_JobTimes.TIMETYPE_TRAVELTO_V:
			case C_JobTimes.TIMETYPE_TRAVELFROM_V:
					LabelDesc= "Mins:";
				break;
				
			case C_JobTimes.TIMETYPE_MILEAGETO_V:
			case C_JobTimes.TIMETYPE_MILEAGEFROM_V:
			case C_JobTimes.TIMETYPE_MILEAGE_V:
					LabelDesc= "Miles:";
				break;
				
			case C_JobTimes.TIMETYPE_CALLOUT_V:
			case C_JobTimes.TIMETYPE_FIXEDCOST_V:
					LabelDesc= "Cost £";
				break;
				
			default:
					LabelDesc = "";
		}
		
		return LabelDesc;
	}
	
	private void ConstructScreen(Integer DataType) {
		
		switch (DataType) {
			// TRAVEL
			case C_JobTimes.TIMETYPE_TRAVEL_V:			// Travel Time
			case C_JobTimes.TIMETYPE_ONSITE_V:			// On site Time
				dataValue.setVisibility(View.INVISIBLE);
				valueLabel.setVisibility(View.INVISIBLE);
				startTime.setVisibility(View.VISIBLE);
				endTime.setVisibility(View.VISIBLE);
				labStartTime.setVisibility(View.VISIBLE);
				labEndTime.setVisibility(View.VISIBLE);
				txtValue.setVisibility(View.INVISIBLE);
				butStartTime.setVisibility(View.VISIBLE);
				butEndTime.setVisibility(View.VISIBLE);
				
				break;
				
			// Travel to,Travel From Minutes.
			case C_JobTimes.TIMETYPE_TRAVELTO_V:
			case C_JobTimes.TIMETYPE_TRAVELFROM_V:
				
				dataValue.setVisibility(View.VISIBLE);
				valueLabel.setVisibility(View.VISIBLE);
				startTime.setVisibility(View.INVISIBLE);
				endTime.setVisibility(View.INVISIBLE);
				labStartTime.setVisibility(View.INVISIBLE);
				labEndTime.setVisibility(View.INVISIBLE);
				txtValue.setVisibility(View.VISIBLE);
				butStartTime.setVisibility(View.INVISIBLE);
				butEndTime.setVisibility(View.INVISIBLE);
				valueLabel.setText("Minutes: ");
				
				break;
				
			// TRAVELTO, TRAVELFROM
			case C_JobTimes.TIMETYPE_MILEAGETO_V:
			case C_JobTimes.TIMETYPE_MILEAGEFROM_V:
			case C_JobTimes.TIMETYPE_MILEAGE_V:
				
				dataValue.setVisibility(View.VISIBLE);
				valueLabel.setVisibility(View.VISIBLE);
				startTime.setVisibility(View.INVISIBLE);
				endTime.setVisibility(View.INVISIBLE);
				labStartTime.setVisibility(View.INVISIBLE);
				labEndTime.setVisibility(View.INVISIBLE);
				txtValue.setVisibility(View.VISIBLE);
				butStartTime.setVisibility(View.INVISIBLE);
				butEndTime.setVisibility(View.INVISIBLE);
				valueLabel.setText("Miles: ");
				
				break;

			case C_JobTimes.TIMETYPE_CALLOUT_V:
			case C_JobTimes.TIMETYPE_FIXEDCOST_V:
				
				dataValue.setVisibility(View.VISIBLE);
				valueLabel.setVisibility(View.VISIBLE);
				startTime.setVisibility(View.INVISIBLE);
				endTime.setVisibility(View.INVISIBLE);
				labStartTime.setVisibility(View.INVISIBLE);
				labEndTime.setVisibility(View.INVISIBLE);
				txtValue.setVisibility(View.VISIBLE);
				butStartTime.setVisibility(View.INVISIBLE);
				butEndTime.setVisibility(View.INVISIBLE);
				valueLabel.setText("Cost £");
				break;
				
		}
	}
	
	
	private String GetTimeString() {
		StringBuilder myTime = new StringBuilder();
		
		try {
			
			myTime.append(pad(mHour)).append(":");
			myTime.append(pad(mMinute));
		} catch(Exception e) {
			Log.i("GetTimeString: Exception", e.getMessage());
		}
		
			
		return myTime.toString();
	}

	private void updateDateDisplay(TextView myDate) {
		myDate.setText(
		new StringBuilder()
			// Month is 0 based so add 1
		.append(mDay).append("/")
		.append(mMonth + 1).append("/")
		.append(mYear).append(" "));
	}
	
	
	/*private void updateTimeDisplay(TextView myTime) {
		myTime.setText(
		new StringBuilder()
			.append(pad(mHour)).append(":")
			.append(pad(mMinute)));
	}
*/	
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}
	

	public static String displayDate(String myDate) {
		String ret;
		
		try {
			SimpleDateFormat dateFormatDDMMYY = new SimpleDateFormat("dd/MM/yy");
			Date dateObj = dateFormatDDMMYY.parse(myDate);
			StringBuilder nowDDMMYY = new StringBuilder(dateFormatDDMMYY.format(dateObj));
			ret = nowDDMMYY.toString();
		} catch (Exception e) {
			ret = myDate;
		}
		
		return ret;
	}
	
	public static String displayTime(String myTime) {
		String ret;
		
		try {
			if (myTime.equals("")) {
				ret = "";
			} else {
				Long inDate = Date.parse(myTime);
				String delegate = "hh:mm";
				ret = (String) DateFormat.format(delegate,inDate);
			}
		} catch (Exception e) {
			ret = myTime;
		}
		
		return ret;
	}
	
	public static String displayTime24hrs(String myTime) {
		String goodDateFormat = "27/06/1969 ";
		SimpleDateFormat df =null;
		Calendar c = Calendar.getInstance();
		String formattedTime = "";
		
		try {
			if (myTime.equals("")) {
			} else {
				if (myTime.length() < 9) {
					df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
					goodDateFormat = goodDateFormat.concat(myTime);
				}
				else {
					df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
					goodDateFormat = myTime;
				}
				
				Date dte = df.parse(goodDateFormat);
				df = new SimpleDateFormat("hh:mm:ss");
				formattedTime = df.format(dte);
			}
		} catch (Exception e) {
			Log.i("EditLogTimes.displayTime24hrs Exception:", e.getMessage());
		}
		
		return formattedTime;
	}
	
	
	
	public static String DisplayCurrency(double value) {
		String formattedValue = "";
		
		try {
			formattedValue = new DecimalFormat("####0.00").format(value);
		} catch (Exception e) {
			Log.i("EditLogTimes.DisplayCurrency Exception", e.getMessage());
		}
		return formattedValue;
	}
	
	public static String DisplayWholeNumber(double value) {
		String formattedValue = "";
		
		try {
			formattedValue = new DecimalFormat("####0").format(value);
		} catch (Exception e) {
			Log.i("EditLogTimes.DisplayWholeNumber Exception", e.getMessage());
		}
		return formattedValue;
	}
	
	
	
	
	
	// Populate LogTimes.CurrentJobTime Object from the screen.
	private void PopulateTimeObjectFromScreen()
	{
		C_JobTimes jt = LogTimes.CurrentJobTime;		// Handy reference.
		String selectedTimeType ="";
		
		try {
			selectedTimeType = spinner1.getSelectedItem().toString();
		} catch (Exception e) {
			Log.i("EditLogTimes.PopulateTimeObjectFromScreen Exception", e.getMessage());
		}
		
		// get the time type from the spinner box as a long and update object.
		jt.setTimeType(jt.TimeTypeFromString(selectedTimeType));							
		
		String sEpoch;
		
		switch (jt.getTimeType())
		{
			// TRAVEL
			case C_JobTimes.TIMETYPE_TRAVEL_V:			// Travel Time
			case C_JobTimes.TIMETYPE_ONSITE_V:			// On site Time
				sEpoch = JTApplication.getInstance().GetDatabaseManager().ToEpochStringFromString(jobDate.getText().toString());
				jt.setEventDate(sEpoch);
				
				sEpoch = JTApplication.getInstance().GetDatabaseManager().ToEpochStringFromLongTimeString(startTime.getText().toString());
				jt.setStartTime(sEpoch);
				
				sEpoch = JTApplication.getInstance().GetDatabaseManager().ToEpochStringFromLongTimeString(endTime.getText().toString());
				jt.setEndTime(sEpoch);
				
				break;
				
			// TRAVELTO, TRAVELFROM
				
			case C_JobTimes.TIMETYPE_TRAVELTO_V:			// Travel To (minutes)
			case C_JobTimes.TIMETYPE_TRAVELFROM_V:			// Travel From (minutes)
			case C_JobTimes.TIMETYPE_MILEAGETO_V:			// Mileage To
			case C_JobTimes.TIMETYPE_MILEAGEFROM_V:			// Mileage From
			case C_JobTimes.TIMETYPE_MILEAGE_V:				// Mileage
			case C_JobTimes.TIMETYPE_CALLOUT_V:				// Call out £
			case C_JobTimes.TIMETYPE_FIXEDCOST_V:			// Fixed Cost £

				sEpoch = JTApplication.getInstance().GetDatabaseManager().ToEpochStringFromString(jobDate.getText().toString());
				jt.setEventDate(sEpoch);
				jt.setEntryValue(Double.parseDouble(txtValue.getText().toString()));
				
				break;
				
		}
		
	}
	
	

}
