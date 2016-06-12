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

public class ListPayments extends ListActivity {

	
	static ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    static ArrayList<C_PaymentReceipt> Payments = JTApplication.getInstance().GetApplicationManager().GetloadedJob().getPaymentReceipt();
	
	static SimpleAdapter adapter = null;										// Used to fill the list with JobTime objects
	static C_PaymentReceipt CurrentPaymentReceipt = new C_PaymentReceipt();		// Gives you access to selected JobTime.
    
	
	// Executes on entry
	public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);
	
	    setContentView(R.layout.paymentslist);
	    
	    // Instantiate the adapter for the list
	     adapter= new SimpleAdapter(this,list,R.layout.payments_row,
		     		new String[] {"id","amount","method","receipt","datetaken","officedate"},
		     		new int[] {R.id.labId,R.id.txtAmount,R.id.txtMethod,R.id.txtReceiptNo,R.id.txtDateTaken,R.id.txtOfficeDate}
		     		);
	     
	     // Traverse all members of the global PartsRequired collection. member of the JOB class and
	     // add them to a HashMap
	     // i is being used as a unique identified for each (C_PartsRequired) object in the list.
	     // i CANNOT BE used as a position reference as it will go out of sequence when things get deleted, added etc.
	     
	     TextView labJobNo = (TextView) findViewById(R.id.labJobNo);
	     labJobNo.setText("Job:" + JTApplication.getInstance().GetApplicationManager().GetloadedJob().getJobNo());

	     TextView labSiteAddr = (TextView) findViewById(R.id.labSiteAddress);
	     labSiteAddr.setText(JTApplication.getInstance().GetApplicationManager().GetloadedJob().getSiteAddress().getAddress());
	     
	     setListAdapter(adapter);
	     
	     PopulatePaymentsList();
	     
		 //Exit Button
		 Button btnExit = (Button) findViewById(R.id.butExit);
		 btnExit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ListPayments.this.finish();
			}
		 });
	     
	     // Add Button
	     Button btnAdd = (Button) findViewById(R.id.butAdd);
	     btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Create a blank C_PartsRequired object and display the EditPartsRequired screen.
				
				CurrentPaymentReceipt = new C_PaymentReceipt();
				CurrentPaymentReceipt.setAndroidJobRecordID(-2);			// Flag to state ADD was pressed
				
        		Intent myIntent = new Intent(view.getContext(),EditPayments.class);
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
        		C_PaymentReceipt paymentreceipt  =  (C_PaymentReceipt) JTApplication.getInstance().GetApplicationManager().GetloadedJob().getPaymentReceipt().get(objID);
        	    
        	    // This now points to the element we're interested in.
        	    // All we need to do is update this and redraw the list.
        		CurrentPaymentReceipt = paymentreceipt;
        	}
			
			
			
			Intent intent = new Intent(this, EditPayments.class);     
			startActivity(intent);      		
		} catch(Exception e)
		{
			Log.i("onListItemClick", "Exception:" + e.getMessage());
		}
	}

	
	
	// Position is a unique id. Use it to traverse the list to extract an element.
	// On first entry it happens to be the position placed in the array. 
	// You can't use this as a position guide as it will change when other elements are added or removed.
	
	 static void AddToHashMap(Integer Position,Double Amount,String Method,String ReceiptNo,String DateTaken, String OfficeDate)
		{
		 	HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("id",Position.toString());
			temp.put("amount",Amount.toString());
			temp.put("method",Method);
			temp.put("receipt",ReceiptNo);
			temp.put("datetaken",DateTaken);
			temp.put("officedate",OfficeDate);
			
			list.add (temp);
		}
	 
	 public static void PopulatePaymentsList () {
	     list.clear();
	     
	     try {
		     for (int i=0; i < Payments.size(); i++) {
		    	 C_PaymentReceipt paymentreceipts =  (C_PaymentReceipt) Payments.get(i);
		    	 AddToHashMap(i,paymentreceipts.getAmount(), paymentreceipts.getPayType(), paymentreceipts.getReceiptNo(),paymentreceipts.getDateTaken(),paymentreceipts.getDateInOffice());
		     }
	     } catch (Exception e)
	     {
	    	 Log.i("LogTimes.PopualtePaymentList Exception", e.getMessage());
	     }
	     
	     adapter.notifyDataSetChanged();	     
	 }
	 
	 // Add the new item to the Job Times Collection.
		public static void AddToPaymentReceiptCollection(C_PaymentReceipt myPaymentReceipt)
		{
			JTApplication.getInstance().GetApplicationManager().GetloadedJob().getPaymentReceipt().add(myPaymentReceipt);
		}
	 

}
