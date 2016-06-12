package co.uk.sbsystems.android.jobtrackermobile;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Sam Sherwin (When I was learning Java)
 *
 */
public class EditPartsUsed extends Activity{
	
	private final static String TAG = "EditPartsUsed";

	TextView qty;
	TextView desc;
	TextView PartNo;
	TextView UnitPrice;
	Button butSave;
	Button butExit;
	
	private TextView labUnitPrice;
	private Boolean showPartsUsedPrice = false;
	
    
	 /** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editpartsused);
		
		
		
		qty= (TextView) findViewById(R.id.txtLocation);
		desc = (TextView) findViewById(R.id.txtNumber);
		PartNo = (TextView) findViewById(R.id.txtPtNo);
		UnitPrice = (TextView) findViewById(R.id.txtUnitPrice);
		labUnitPrice = (TextView) findViewById(R.id.labUnitPrice);
		
		butExit = (Button) findViewById(R.id.butExit);
		butSave = (Button) findViewById(R.id.butSave);
		
		// Determine if we can show prices or not.
		showPartsUsedPrice = JTApplication.getInstance().getSettings().getShowPartsUsedPrice();
		
		if (showPartsUsedPrice == false) {
			UnitPrice.setVisibility(View.INVISIBLE);
			labUnitPrice.setVisibility(View.INVISIBLE); 
		} else {
			UnitPrice.setVisibility(View.VISIBLE);
			labUnitPrice.setVisibility(View.VISIBLE);
		}
		
		
		// Save button
		butSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// User has just clicked Save.  
				PopulatePartsUsedObjectFromScreen();
				
				// Check if ADD was pressed.  represented by AndroidJobRecordID = -2
				// If this is a new item being added, need to add it to the PartsRequired collection.
				if (ListPartsUsed.CurrentPartsUsed.getAndroidJobRecordID() == -2)
				{
					ListPartsUsed.CurrentPartsUsed.setJobNo(JTApplication.getInstance().GetApplicationManager().GetloadedJob().getJobNo());		// Set Job No.
					ListPartsUsed.CurrentPartsUsed.setAndroidJobRecordID(-1);	
					ListPartsUsed.AddToPartsUsedCollection(ListPartsUsed.CurrentPartsUsed);
				}
				
				ListPartsUsed.PopulatePartsUsedList();
				EditPartsUsed.this.finish();
			}
		});
		
		// Exit Button
		butExit.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditPartsUsed.this.finish();
			}
		});
	
		
		
		
		PopulateScreen();
	}
	
	
	private void PopulateScreen() {
		try {
			Double myQty = 0.0;
			
			myQty = ListPartsUsed.CurrentPartsUsed.getQty();
			if (myQty == 0.0) {
				qty.setText("");
			} else {
				qty.setText(ListPartsUsed.CurrentPartsUsed.getQty().toString());
			}
			desc.setText(ListPartsUsed.CurrentPartsUsed.getDescription());
			PartNo.setText(ListPartsUsed.CurrentPartsUsed.getPartNo());
			
			myQty = ListPartsUsed.CurrentPartsUsed.getUnitPrice();
			if (myQty == 0.0) {
				UnitPrice.setText("");
			} else {
				UnitPrice.setText(ListPartsUsed.CurrentPartsUsed.getUnitPrice().toString());
			}
			
			
		} catch (Exception e) {
			Log.i("EditPartsUsed.PopulateScreen: Exception", e.getMessage());
		}
	}
	
	
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}
	

	
	
	
	// Populate Object from the screen.
	private void PopulatePartsUsedObjectFromScreen()
	{
		C_PartsUsed pr = ListPartsUsed.CurrentPartsUsed;		// Handy reference.
		
		try {
			pr.setQty(Double.parseDouble(qty.getText().toString()));
			pr.setDescription(desc.getText().toString());
			pr.setPartNo(PartNo.getText().toString());
			
			// If we're not showing prices, leave existing values alone.
			if (showPartsUsedPrice == true) {
				pr.setUnitPrice(Double.parseDouble(UnitPrice.getText().toString()));
			}
		} catch (Exception ex) {
			Log.i(TAG, ex.getMessage());
		}
		
	}
	
	

}
