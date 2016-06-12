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

public class ListPartsUsed extends ListActivity {

	
	static ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    static ArrayList<C_PartsUsed> PartsUsed = JTApplication.getInstance().GetApplicationManager().GetloadedJob().getPartsUsed();
	
	static SimpleAdapter adapter = null;							// Used to fill the list with JobTime objects
	static C_PartsUsed CurrentPartsUsed = new C_PartsUsed();		// Gives you access to selected JobTime.
    
	private TextView labPrice;
	private TextView labUnitPrice;
	private Boolean showPartsUsed = false;
	
	// Executes on entry
	public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);
	
	    setContentView(R.layout.partsusedlist);
	    
/*	    labPrice = (TextView) findViewById(R.id.labUnitPrice);
	    labUnitPrice = (TextView) findViewById(R.id.txtUnitPrice);
	    
	    showPartsUsed = JTApplication.getSettings().getShowPartsUsedPrice();
	    if (showPartsUsed == false) {
	    	labPrice.setVisibility(View.INVISIBLE);
	    	labUnitPrice.setVisibility(View.INVISIBLE);
	    } else {
	    	labPrice.setVisibility(View.VISIBLE);
	    	labUnitPrice.setVisibility(View.VISIBLE);
	    }
*/	    
	    // Instantiate the adapter for the list
	     adapter= new SimpleAdapter(this,list,R.layout.partsused_row,
		     		new String[] {"id","qty","desc","ptno","uprice"},
		     		new int[] {R.id.labId,R.id.txtLocation,R.id.txtNumber,R.id.txtPtNo,R.id.txtUnitPrice}
		     		);
	     
	     // Traverse all members of the global PartsUsed collection. member of the JOB class and
	     // add them to a HashMap
	     // i is being used as a unique identified for each (C_PartsUsed) object in the list.
	     // i CANNOT BE used as a position reference as it will go out of sequence when things get deleted, added etc.
	     
	     TextView labJobNo = (TextView) findViewById(R.id.labJobNo);
	     labJobNo.setText("Job:" + JTApplication.getInstance().GetApplicationManager().GetloadedJob().getJobNo());

	     TextView labSiteAddr = (TextView) findViewById(R.id.labSiteAddress);
	     labSiteAddr.setText(JTApplication.getInstance().GetApplicationManager().GetloadedJob().getSiteAddress().getAddress());
	     
	     
	     setListAdapter(adapter);
	     
	     PopulatePartsUsedList();
	     
		 //Exit Button
		 Button btnExit = (Button) findViewById(R.id.butExit);
		 btnExit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ListPartsUsed.this.finish();
			}
		 });
	     
	     // Add Button
	     Button btnAdd = (Button) findViewById(R.id.butAdd);
	     btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Create a blank C_PartsRequired object and display the EditPartsRequired screen.
				
				CurrentPartsUsed = new C_PartsUsed();
				CurrentPartsUsed.setAndroidJobRecordID(-2);			// Flag to state ADD was pressed
				
        		Intent myIntent = new Intent(view.getContext(),EditPartsUsed.class);
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
        	    C_PartsUsed partsused =  (C_PartsUsed) JTApplication.getInstance().GetApplicationManager().GetloadedJob().getPartsUsed().get(objID);
        	    
        	    // This now points to the element we're interested in.
        	    // All we need to do is update this and redraw the list.
        		CurrentPartsUsed= partsused;
        	}
			
			
			
			Intent intent = new Intent(this, EditPartsUsed.class);     
			startActivity(intent);      		
		} catch(Exception e)
		{
			Log.i("onListItemClick", "Exception:" + e.getMessage());
		}
	}

	
	
	// Position is a unique id. Use it to traverse the list to extract an element.
	// On first entry it happens to be the position placed in the array. 
	// You can't use this as a position guide as it will change when other elements are added or removed.
	
	 static void AddToHashMap(Integer Position,Double Qty,String Desc,String PtNo,Double UnitPrice)
		{
		 	HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("id",Position.toString());
			temp.put("qty",Qty.toString());
			temp.put("desc",Desc);
			temp.put("ptno",PtNo);
			temp.put("uprice",UnitPrice.toString());
			
			list.add (temp);
		}
	 
	 public static void PopulatePartsUsedList () {
	     list.clear();
	     
	     try {
		     for (int i=0; i < PartsUsed.size(); i++) {
		    	 C_PartsUsed partsused =  (C_PartsUsed) PartsUsed.get(i);
		    	 if (JTApplication.getInstance().getSettings().getShowPartsUsedPrice() == true) {
		    		 AddToHashMap(i,partsused.getQty(), partsused.getDescription(), partsused.getPartNo(),partsused.getUnitPrice());
		    	 } else {
		    		 AddToHashMap(i,partsused.getQty(), partsused.getDescription(), partsused.getPartNo(),0.0);
		    	 }
		     }
	     } catch (Exception e)
	     {
	    	 Log.i("LogTimes.PopualteJobTimesList Exception", e.getMessage());
	     }
	     
	     adapter.notifyDataSetChanged();	     
	 }
	 
	 // Add the new item to the Job Times Collection.
		public static void AddToPartsUsedCollection(C_PartsUsed myPartsUsed)
		{
			JTApplication.getInstance().GetApplicationManager().GetloadedJob().getPartsUsed().add(myPartsUsed);
		}
	 

}
