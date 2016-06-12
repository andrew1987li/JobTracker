package co.uk.sbsystems.android.jobtrackermobile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class LogTimes extends ListActivity {

	
	static ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    static ArrayList<C_JobTimes> JobTimes = JTApplication.getInstance().GetApplicationManager().GetloadedJob().getJobTimes();
	
	static SimpleAdapter adapter = null;					// Used to fill the list with JobTime objects
	static C_JobTimes CurrentJobTime = new C_JobTimes();	// Gives you access to selected JobTime.
    
	
	// Executes on entry
	public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);
	
	    setContentView(R.layout.logtimes);
	    
	    // Instantiate the adapter for the list
	     adapter= new SimpleAdapter(this,list,R.layout.logtimes_row,
		     		new String[] {"id","datatype","jobdate","starttime","endtime","miles","minutes","cost"},
		     		new int[] {R.id.labId,R.id.txtType,R.id.txtDate,R.id.txtStartTime,R.id.txtEndTime,R.id.txtMiles,R.id.txtMins,R.id.txtCost}
		     		);
	     
	     // Traverse all members of the global JobTimes collection. member of the JOB class and
	     // add them to a HashMap
	     // i is being used as a unique identified for each (C_JobTimes) object in the list.
	     // i CANNOT BE used as a position reference as it will go out of sequence when things get deleted, added etc. 

	     TextView labJobNo = (TextView) findViewById(R.id.labJobNo);
	     labJobNo.setText("Job:" + JTApplication.getInstance().GetApplicationManager().GetloadedJob().getJobNo());

	     TextView labSiteAddr = (TextView) findViewById(R.id.labSiteAddress);
	     labSiteAddr.setText(JTApplication.getInstance().GetApplicationManager().GetloadedJob().getSiteAddress().getAddress());

	     PopulateJobTimesList();
	    
	     setListAdapter(adapter);
	     
	     
	     
		 //Exit Button
		 Button btnExit = (Button) findViewById(R.id.butExit);
		 btnExit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LogTimes.this.finish();
			}
		 });
	     
	     // Add Button
	     Button btnAdd = (Button) findViewById(R.id.butAdd);
	     btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Create a blank JobTime object and display the EditLogTimes screen.
				String myDate = "";
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				
				CurrentJobTime = new C_JobTimes();
				CurrentJobTime.setTimeType(C_JobTimes.TIMETYPE_ONSITE_V);
				CurrentJobTime.setEventDate(dateFormat.format(date).toString() );
				
				dateFormat = new SimpleDateFormat("h:mm aaa");
				CurrentJobTime.setStartTime(dateFormat.format(date));

				dateFormat = new SimpleDateFormat("h:mm aaa");
				CurrentJobTime.setEndTime(dateFormat.format(date));
				
				CurrentJobTime.setAndroidJobRecordID(-2);			// Flag to state ADD was pressed
				
        		Intent myIntent = new Intent(view.getContext(),EditLogTimes.class);
        		startActivityForResult(myIntent,0);
			}
	     });
     

		
	};    
	

	
	@Override
	protected void onListItemClick(ListView parent, View view, int position, long id) {
		try
		{
			Integer objID;
			int commapos;
			
			String item =  (String) getListAdapter().getItem(position).toString();
			// call the load jobs screen and load the selected job.
			//String engineersName = (String) getListAdapter().getItem(position).toString();
			
        	String params[] = item.split("id=");
        	if (params[1] != "")
        	{
        		// Extract the job number/ drop last characters as its a }
        		commapos = params[1].indexOf(",", 0);
        		objID = Integer.parseInt(params[1].substring(0,commapos));
        	    C_JobTimes jobtime =  (C_JobTimes) JTApplication.getInstance().GetApplicationManager().GetloadedJob().getJobTimes().get(objID);
        	    
        	    // This now points to the element we're interested in.
        	    // All we need to do is update this and redraw the list.
        		CurrentJobTime = jobtime;
        	}
			
			
			
			Intent intent = new Intent(this, EditLogTimes.class);     
			startActivity(intent);      		
		} catch(Exception e)
		{
			Log.i("onListItemClick", "Exception:" + e.getMessage());
		}
	}

	
	
	// Position is a unique id. Use it to traverse the list to extract an element.
	// On first entry it happens to be the position placed in the array. 
	// You can't use this as a position guide as it will change when other elements are added or removed.
	
	 static void AddToHashMap(Integer Position,Integer EntryType,String DataType,String JobDate,String StartTime, String EndTime,Double DataValue)
		{
		 	HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("id",Position.toString());
			temp.put("datatype",DataType);
			
			// Every element needs the date.
			temp.put("jobdate",EditLogTimes.displayDate(JobDate));
			
			switch (EntryType) {
				// ONSITE
					case C_JobTimes.TIMETYPE_ONSITE_V:
					case C_JobTimes.TIMETYPE_TRAVEL_V:
						temp.put("starttime",EditLogTimes.displayTime(StartTime));
						temp.put("endtime",EditLogTimes.displayTime(EndTime));
						break;
			
				//  TravelTo, TravelFrom
					case C_JobTimes.TIMETYPE_TRAVELTO_V:
					case C_JobTimes.TIMETYPE_TRAVELFROM_V:
						temp.put("minutes",EditLogTimes.DisplayWholeNumber(DataValue));
						break;
				
				// MILEAGE, MILEAGETO, MILEAGEFROM
					case C_JobTimes.TIMETYPE_MILEAGETO_V:
					case C_JobTimes.TIMETYPE_MILEAGEFROM_V:
					case C_JobTimes.TIMETYPE_MILEAGE_V:
						temp.put("miles",EditLogTimes.DisplayWholeNumber(DataValue));
						break;
					
				// CALLOUT, FIXEDCOST
					case C_JobTimes.TIMETYPE_CALLOUT_V:
					case C_JobTimes.TIMETYPE_FIXEDCOST_V:
						temp.put("cost",EditLogTimes.DisplayCurrency(DataValue));
						break;
					
					
			}
			
			list.add (temp);
			
			
			
			
		}
	 
	 public static void PopulateJobTimesList () {
	     list.clear();
	     
	     try {
		     for (int i=0; i < JobTimes.size(); i++) {
		    	 C_JobTimes jobtime =  (C_JobTimes) JobTimes.get(i);
		    	 AddToHashMap(i,jobtime.getTimeType(), jobtime.TimeTypeToString(),
						 JTApplication.getInstance().GetDatabaseManager().fromEpochStringToDate(jobtime.getEventDate()),
						 JTApplication.getInstance().GetDatabaseManager().fromEpochStringToLongTime(jobtime.getStartTime()),
						 JTApplication.getInstance().GetDatabaseManager().fromEpochStringToLongTime(jobtime.getEndTime()),
		    			 jobtime.getEntryValue());
		     }
	     } catch (Exception e)
	     {
	    	 Log.i("LogTimes.PopualteJobTimesList Exception", e.getMessage());
	     }
	     
	     adapter.notifyDataSetChanged();	     
	 }
	 
	 // Add the new item to the Job Times Collection.
		public static void AddToJobTimeCollection(C_JobTimes myTime)
		{
			JTApplication.getInstance().GetApplicationManager().GetloadedJob().getJobTimes().add(myTime);
		}
	 

}
