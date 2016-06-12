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
public class EditPartsRequired extends Activity{

	TextView qty;
	TextView desc;
	TextView ourPartNo;
	TextView supplier;
	TextView supplierPartNo;
	Button butSave;
	Button butExit;
	
    
	 /** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editpartsrequired);
		
		
		qty= (TextView) findViewById(R.id.txtLocation);
		desc = (TextView) findViewById(R.id.txtNumber);
		ourPartNo = (TextView) findViewById(R.id.txtOurPtNo);
		supplier = (TextView) findViewById(R.id.txtSupplier);
		supplierPartNo = (TextView) findViewById(R.id.txtSupPtNo);
		
		
		butExit = (Button) findViewById(R.id.butExit);
		butSave = (Button) findViewById(R.id.butSave);
		
		// Save button
		butSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// User has just clicked Save.  
				PopulatePartsRequiredObjectFromScreen();
				
				// Check if ADD was pressed.  represented by AndroidJobRecordID = -2
				// If this is a new item being added, need to add it to the PartsRequired collection.
				if (ListPartsRequired.CurrentPartsRequired.getAndroidJobRecordID() == -2)
				{
					ListPartsRequired.CurrentPartsRequired.setJobNo(JTApplication.getInstance().GetApplicationManager().GetloadedJob().getJobNo());		// Set Job No.
					ListPartsRequired.CurrentPartsRequired.setEngineer(JTApplication.getInstance().GetApplicationManager().getDeviceAssignedEngineerName());
					ListPartsRequired.CurrentPartsRequired.setAndroidJobRecordID(-1);	
					ListPartsRequired.AddToPartsRequiredCollection(ListPartsRequired.CurrentPartsRequired);
				}
				
				ListPartsRequired.PopulatePartsRequiredList();
				EditPartsRequired.this.finish();
			}
		});
		
		// Exit Button
		butExit.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditPartsRequired.this.finish();
			}
		});
	
		
		
		
		PopulateScreen();
	}
	
	
	private void PopulateScreen() {
		try {
			double myQty =0.0;
			
			myQty = ListPartsRequired.CurrentPartsRequired.getQty();
			if (myQty == 0.0) {
				qty.setText("");
			} else {
				qty.setText(ListPartsRequired.CurrentPartsRequired.getQty().toString());
			}
			desc.setText(ListPartsRequired.CurrentPartsRequired.getDescription());
			ourPartNo.setText(ListPartsRequired.CurrentPartsRequired.getOurPartNo());
			supplier.setText(ListPartsRequired.CurrentPartsRequired.getSupplierName());
			supplierPartNo.setText(ListPartsRequired.CurrentPartsRequired.getSupplierPartNo());
			
			
		} catch (Exception e) {
			Log.i("EditPartsRequired.PopulateScreen: Exception", e.getMessage());
		}
	}
	
	
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}
	

	
	
	
	// Populate Object from the screen.
	private void PopulatePartsRequiredObjectFromScreen()
	{
		C_PartsRequired pr = ListPartsRequired.CurrentPartsRequired;		// Handy reference.
		
		pr.setQty(Double.parseDouble(qty.getText().toString()));
		pr.setDescription(desc.getText().toString());
		pr.setOurPartNo(ourPartNo.getText().toString());
		pr.setSupplierName(supplier.getText().toString());
		pr.setSupplierPartNo(supplierPartNo.getText().toString());
		
	}
	
	

}
