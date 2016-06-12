package co.uk.sbsystems.android.jobtrackermobile;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ListRiserServices extends ListActivity {

	
	static ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    static ArrayList<C_RiserService> RiserService = JTApplication.getInstance().GetApplicationManager().GetloadedJob().getRiserServices();
	
	static SimpleAdapter adapter = null;							// Used to fill the list with Riser objects
	static C_RiserService CurrentRiser = new C_RiserService();		// Gives you access to selected Riser Service
    
	
	// Executes on entry
	public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);
	
	    setContentView(R.layout.firehydrantlist);
	    
	    // Instantiate the adapter for the list
	     adapter= new SimpleAdapter(this,list,R.layout.riserservice_row,
		     		new String[] {"id","location","number","date"},
		     		new int[] {R.id.labId, R.id.txtLocation,R.id.txtNumber,R.id.txtDate}
		     		);
	     
	     // Traverse all members of the global RiserService collection. member of the JOB class and
	     // add them to a HashMap
	     // i is being used as a unique identified for each (C_RiserService) object in the list.
	     // i CANNOT BE used as a position reference as it will go out of sequence when things get deleted, added etc. 
	     setListAdapter(adapter);
	     
	     
	     TextView labJobNo = (TextView) findViewById(R.id.labJobNo);
	     labJobNo.setText("Job:" + JTApplication.getInstance().GetApplicationManager().GetloadedJob().getJobNo());

	     TextView labSiteAddr = (TextView) findViewById(R.id.labSiteAddress);
	     labSiteAddr.setText(JTApplication.getInstance().GetApplicationManager().GetloadedJob().getSiteAddress().getAddress());
	     
	     PopulateRiserAvailableList();
	     
		 //Exit Button
		 Button btnExit = (Button) findViewById(R.id.butExit);
		 btnExit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ListRiserServices.this.finish();
			}
		 });
	     
	     // Add Button
	     Button btnAdd = (Button) findViewById(R.id.butAdd);
	     btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Create a blank C_PartsRequired object and display the EditPartsRequired screen.
				
				CurrentRiser = new C_RiserService();
				CurrentRiser.setJTRecordID(-2);			// Flag to state ADD was pressed
				
        		Intent myIntent = new Intent(view.getContext(),EditRiserService.class);
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
                String riserID= params[1].replace("}", "");
        		//commapos = params[1].indexOf(",", 0);
				//if (commapos != -1) {
					objID = Integer.parseInt(riserID);
					C_RiserService riserservice = (C_RiserService) JTApplication.getInstance().GetApplicationManager().GetloadedJob().getRiserServices().get(objID);

					// This now points to the element we're interested in.
					// All we need to do is update this and redraw the list.
					CurrentRiser = riserservice;
				//}
        	}
			
			
			
			Intent intent = new Intent(this, EditRiserService.class);     
			startActivity(intent);      		
		} catch(Exception e)
		{
			Log.i("onListItemClick", "Exception:" + e.getMessage());
		}
	}

	// Position is a unique id. Use it to traverse the list to extract an element.
	// On first entry it happens to be the position placed in the array. 
	// You can't use this as a position guide as it will change when other elements are added or removed.
	
	 static void AddToHashMap(Integer Position,String Location,Integer Number,String TestDate)
		{
		 	HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("id",Position.toString());
			temp.put("location",Location.toString());
			temp.put("number",Number.toString());
			temp.put("date",TestDate);
			
			list.add (temp);
		}
	 
	 public static void PopulateRiserAvailableList () {
	     
	     try {
		     list.clear();
		     
		     for (int i=0; i < RiserService.size(); i++) {
		    	 C_RiserService riserservice =  (C_RiserService) RiserService.get(i);
		    	 AddToHashMap(i,riserservice.getRiserLocation(), riserservice.getRiserNumber(),"");
		     }
	     } catch (Exception e)
	     {
	    	 Log.i("PopulateRiserAvailableL", e.getMessage());
	     } finally {
	    	 adapter.notifyDataSetChanged();
	     }
	 }
	 
		 // Add the new item to the Riser Service Collection.
		public static void AddToRiserServiceCollection(C_RiserService myRiserService)
		{
			JTApplication.getInstance().GetApplicationManager().GetloadedJob().getRiserServices().add(myRiserService);
		}

}
