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

public class ListPartsRequired extends ListActivity {

	
	static ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    static ArrayList<C_PartsRequired> PartsRequired = JTApplication.getInstance().GetApplicationManager().GetloadedJob().getPartsRequired();
	
	static SimpleAdapter adapter = null;							// Used to fill the list with JobTime objects
	static C_PartsRequired CurrentPartsRequired = new C_PartsRequired();	// Gives you access to selected JobTime.
    
	
	// Executes on entry
	public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);
	
	    setContentView(R.layout.partsrequiredlist);
	    
	    // Instantiate the adapter for the list
	     adapter= new SimpleAdapter(this,list,R.layout.partsrequired_row,
		     		new String[] {"id","qty","desc","ourptno","supplier","supptno"},
		     		new int[] {R.id.labId,R.id.txtLocation,R.id.txtNumber,R.id.txtOurPtNo,R.id.txtSupplier,R.id.txtSupPtNo}
		     		);
	     
	     // Traverse all members of the global PartsRequired collection. member of the JOB class and
	     // add them to a HashMap
	     // i is being used as a unique identified for each (C_PartsRequired) object in the list.
	     // i CANNOT BE used as a position reference as it will go out of sequence when things get deleted, added etc. 
	     setListAdapter(adapter);
	     
	     
	     TextView labJobNo = (TextView) findViewById(R.id.labJobNo);
	     labJobNo.setText("Job:" + JTApplication.getInstance().GetApplicationManager().GetloadedJob().getJobNo());

	     TextView labSiteAddr = (TextView) findViewById(R.id.labSiteAddress);
	     labSiteAddr.setText(JTApplication.getInstance().GetApplicationManager().GetloadedJob().getSiteAddress().getAddress());
	     
	     PopulatePartsRequiredList();
	     
		 //Exit Button
		 Button btnExit = (Button) findViewById(R.id.butExit);
		 btnExit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ListPartsRequired.this.finish();
			}
		 });
	     
	     // Add Button
	     Button btnAdd = (Button) findViewById(R.id.butAdd);
	     btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Create a blank C_PartsRequired object and display the EditPartsRequired screen.
				
				CurrentPartsRequired = new C_PartsRequired();
				CurrentPartsRequired.setAndroidJobRecordID(-2);			// Flag to state ADD was pressed
				
        		Intent myIntent = new Intent(view.getContext(),EditPartsRequired.class);
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
        	    C_PartsRequired partsrequired =  (C_PartsRequired) JTApplication.getInstance().GetApplicationManager().GetloadedJob().getPartsRequired().get(objID);
        	    
        	    // This now points to the element we're interested in.
        	    // All we need to do is update this and redraw the list.
        		CurrentPartsRequired = partsrequired;
        	}
			
			
			
			Intent intent = new Intent(this, EditPartsRequired.class);     
			startActivity(intent);      		
		} catch(Exception e)
		{
			Log.i("onListItemClick", "Exception:" + e.getMessage());
		}
	}

	
	
	// Position is a unique id. Use it to traverse the list to extract an element.
	// On first entry it happens to be the position placed in the array. 
	// You can't use this as a position guide as it will change when other elements are added or removed.
	
	 static void AddToHashMap(Integer Position,Double Qty,String Desc,String OurPtNo,String Supplier, String SupPtNo)
		{
		 	HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("id",Position.toString());
			temp.put("qty",Qty.toString());
			temp.put("desc",Desc);
			temp.put("ourptno",OurPtNo);
			temp.put("supplier",Supplier);
			temp.put("supptno",SupPtNo);
			
			list.add (temp);
		}
	 
	 public static void PopulatePartsRequiredList () {
	     list.clear();
	     
	     try {
		     for (int i=0; i < PartsRequired.size(); i++) {
		    	 C_PartsRequired partsrequired =  (C_PartsRequired) PartsRequired.get(i);
		    	 AddToHashMap(i,partsrequired.getQty(), partsrequired.getDescription(), partsrequired.getOurPartNo(),partsrequired.getSupplierName(),partsrequired.getSupplierPartNo());
		     }
	     } catch (Exception e)
	     {
	    	 Log.i("LogTimes.PopualteJobTimesList Exception", e.getMessage());
	     }
	     
	     adapter.notifyDataSetChanged();	     
	 }
	 
	 // Add the new item to the Job Times Collection.
		public static void AddToPartsRequiredCollection(C_PartsRequired myPartsRequired)
		{
			JTApplication.getInstance().GetApplicationManager().GetloadedJob().getPartsRequired().add(myPartsRequired);
		}
	 

}
