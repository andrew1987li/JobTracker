package co.uk.sbsystems.android.jobtrackermobile;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Sam Sherwin (When I was learning Java)
 *
 */
public class EditPayments extends Activity{

	TextView amount;
	Spinner spinnerMethod;
	TextView receiptNo;
	Button butSave;
	Button butExit;
	
    
	 /** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editpayments);
		
		
		amount= (TextView) findViewById(R.id.txtAmount);
		spinnerMethod = (Spinner) findViewById(R.id.spinMethod);
		receiptNo = (TextView) findViewById(R.id.txtReceiptNo);
		butExit = (Button) findViewById(R.id.butExit);
		butSave = (Button) findViewById(R.id.butSave);
		
		// Save button
		butSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				
				// User has just clicked Save.  
				PopulatePaymentsObjectFromScreen();
				
				// Check if ADD was pressed.  represented by AndroidJobRecordID = -2
				// If this is a new item being added, need to add it to the PartsRequired collection.
				if (ListPayments.CurrentPaymentReceipt.getAndroidJobRecordID() == -2)
				{
					ListPayments.CurrentPaymentReceipt.setJobNo(JTApplication.getInstance().GetApplicationManager().GetloadedJob().getJobNo());		// Set Job No.
					ListPayments.CurrentPaymentReceipt.setEngineerName(JTApplication.getInstance().GetApplicationManager().getDeviceAssignedEngineerName());
					ListPayments.CurrentPaymentReceipt.setAndroidJobRecordID(-1);	
					ListPayments.CurrentPaymentReceipt.setDateTaken(dateFormat.format(cal.getTime()));
					ListPayments.AddToPaymentReceiptCollection(ListPayments.CurrentPaymentReceipt);
				}
				
				ListPayments.PopulatePaymentsList();
				EditPayments.this.finish();
			}
		});
		
		// Exit Button
		butExit.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditPayments.this.finish();
			}
		});
	
		try {
			ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this,R.array.paymentTypes,android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerMethod.setAdapter(adapter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("EditPayments.OnCreate: Exception", e.getMessage());
		}
		
		
		
		PopulateScreen();
	}
	
	
	private void PopulateScreen() {
		try {
			
			
			// Set the selected item in the Spinner to be that of the CurrentJobTime Type. 
			ArrayAdapter myAdap = (ArrayAdapter) spinnerMethod.getAdapter();
			try {
				int spinPos = myAdap.getPosition(ListPayments.CurrentPaymentReceipt.getPayType());
				spinnerMethod.setSelection(spinPos);
			} catch (Exception e)
			{
			}
			
			if (ListPayments.CurrentPaymentReceipt.getAmount().equals(new Double(0.0))) {
				amount.setText("");
			} else {
				amount.setText(ListPayments.CurrentPaymentReceipt.getAmount().toString());
			}
			receiptNo.setText(ListPayments.CurrentPaymentReceipt.getReceiptNo());
			
			
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
	private void PopulatePaymentsObjectFromScreen()
	{
		String selectedMethod = "";
		try {
			selectedMethod = spinnerMethod.getSelectedItem().toString();
		} catch (Exception e)
		{
			
		}

		
		C_PaymentReceipt pr = ListPayments.CurrentPaymentReceipt;		// Handy reference.
		
		pr.setAmount(Double.parseDouble(amount.getText().toString()));
		pr.setPayType(selectedMethod);
		pr.setReceiptNo(receiptNo.getText().toString());
		
	}
	
	

}
