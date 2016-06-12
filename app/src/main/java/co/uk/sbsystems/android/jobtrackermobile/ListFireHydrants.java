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

public class ListFireHydrants extends ListActivity {

	
	static ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    static ArrayList<C_FireHydrant> FireHydrant = JTApplication.getInstance().GetApplicationManager().GetloadedJob().getFireHydrant();
	
	static SimpleAdapter adapter = null;							// Used to fill the list with FireHydrant objects
	static C_FireHydrant CurrentFireHydrant = new C_FireHydrant();	// Gives you access to selected FireHydrant.
    
	
	// Executes on entry
	public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);
	
	    setContentView(R.layout.firehydrantlist);
	    
	    // Instantiate the adapter for the list
	     adapter= new SimpleAdapter(this,list,R.layout.firehydrant_row,
		     		new String[] {"id","location","number","date"},
		     		new int[] {R.id.labId, R.id.txtLocation,R.id.txtNumber,R.id.txtDate}
		     		);
	     
	     // Traverse all members of the global FireHydrant collection. member of the JOB class and
	     // add them to a HashMap
	     // i is being used as a unique identified for each (C_FireHydrant) object in the list.
	     // i CANNOT BE used as a position reference as it will go out of sequence when things get deleted, added etc. 
	     setListAdapter(adapter);
	     
	     
	     TextView labJobNo = (TextView) findViewById(R.id.labJobNo);
	     labJobNo.setText("Job:" + JTApplication.getInstance().GetApplicationManager().GetloadedJob().getJobNo());

	     TextView labSiteAddr = (TextView) findViewById(R.id.labSiteAddress);
	     labSiteAddr.setText(JTApplication.getInstance().GetApplicationManager().GetloadedJob().getSiteAddress().getAddress());
	     
	     PopulateHydrantAvailableList();
	     
		 //Exit Button
		 Button btnExit = (Button) findViewById(R.id.butExit);
		 btnExit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ListFireHydrants.this.finish();
			}
		 });
	     
	     // Add Button
	     Button btnAdd = (Button) findViewById(R.id.butAdd);
	     btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Create a blank C_PartsRequired object and display the EditPartsRequired screen.
				
				CurrentFireHydrant = new C_FireHydrant();
				CurrentFireHydrant.setJTRecordID(-2);			// Flag to state ADD was pressed
				
        		Intent myIntent = new Intent(view.getContext(),EditFireHydrant.class);
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
        	    C_FireHydrant firehydrant =  (C_FireHydrant) JTApplication.getInstance().GetApplicationManager().GetloadedJob().getFireHydrant().get(objID);
        	    
        	    // This now points to the element we're interested in.
        	    // All we need to do is update this and redraw the list.
        		CurrentFireHydrant= firehydrant ;
        	}
			
			
			
			Intent intent = new Intent(this, EditFireHydrant.class);     
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
	 
	 public static void PopulateHydrantAvailableList () {
	     list.clear();
	     
	     try {
		     for (int i=0; i < FireHydrant.size(); i++) {
		    	 C_FireHydrant firehydrant =  (C_FireHydrant) FireHydrant.get(i);
		    	 AddToHashMap(i,firehydrant.getHydrantLocation(), firehydrant.getNumber(),firehydrant.getTestDate());
		     }
	     } catch (Exception e)
	     {
	    	 Log.i("PopulateHydrantAvailableList Exception", e.getMessage());
	     }
	     
	     adapter.notifyDataSetChanged();	     
	 }
	 
		 // Add the new item to the Fire Hydrant Collection.
		public static void AddToFireHydrantCollection(C_FireHydrant myFireHydrant)
		{
			JTApplication.getInstance().GetApplicationManager().GetloadedJob().getFireHydrant().add(myFireHydrant);
		}

}
