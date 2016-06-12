package co.uk.sbsystems.android.jobtrackermobile;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

public class EditSettings extends Activity{

	TextView txtUserName; 
	TextView txtPassword;
    Button btnExit;
    Button btnSave;
    
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    

	    setContentView(R.layout.settings);

	    txtUserName = (TextView) findViewById(R.id.txtUserName);
	    txtPassword = (TextView) findViewById(R.id.txtPassword );
	    btnExit= (Button) findViewById(R.id.btnExit);
	    btnSave = (Button) findViewById(R.id.btnSave);
	    
	    
	    
	    DisplaySettings();
	    
        btnExit.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View view) {
        		finish();
        	}
        });
        
        btnSave.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View view) {
        		PopulateSettings();
        		//JTApplication.GetDatabaseManager().SaveSettings();
        		finish();
        	}
        });          
        
	    
	    
	}
	
 /*   @Override
    public void onWindowFocusChanged(boolean hasFocus) {
     super.onWindowFocusChanged(hasFocus);
     DisplaySettings();
    }*/	
	
	
	// Populate the  settings from the screen
	private void PopulateSettings()
	{

		Integer UseDueDate;
		
		try {
//			UseDueDate = rbUseDueDate.isChecked() ? 1:0;
			
//			JTApplication.GetApplicationManager().setUseDueDate(UseDueDate);
//			JTApplication.GetApplicationManager().setUseJobBrief(cbUseJobBrief.isChecked()? 1:0);
//			JTApplication.GetApplicationManager().setIgnoreLowSignal(cbIgnoreLowSignal.isChecked()? 1:0);
//			JTApplication.GetApplicationManager().setUseReverseLookup(cbUseReverseLookup.isChecked()? 1:0);

			JTApplication.getInstance().GetApplicationManager().setMobileUserName(txtUserName.getText().toString());
			JTApplication.getInstance().GetApplicationManager().setMobilePassword(txtPassword.getText().toString());
			JTApplication.getInstance().GetDatabaseManager().SaveSettings();
		} catch(Exception e)
		{
			Log.i("PopulateSettings Exception:",e.getMessage());
		}
	}
	
	
	private void DisplaySettings() 
	{
		try {
			
//			if ((JTApplication.GetApplicationManager().getUseDueDate() >= 1) == true) {
//				rbUseDueDate.setChecked(true);
//				rbUseDateCreated.setChecked(false);
//			} else {
//				rbUseDateCreated.setChecked(true);
//				rbUseDueDate.setChecked(false);
//			}
			
//			cbUseJobBrief.setChecked(JTApplication.GetApplicationManager().getUseJobBrief() >= 1);
//			cbIgnoreLowSignal.setChecked(JTApplication.GetApplicationManager().getIgnoreLowSignal() >= 1);
//			cbUseReverseLookup.setChecked(JTApplication.GetApplicationManager().getUseReverseLookup() >= 1);
			
			txtUserName.setText(JTApplication.getInstance().GetApplicationManager().getMobileUserName());
			txtPassword.setText(JTApplication.getInstance().GetApplicationManager().getMobilePassword());
			
		} catch (Exception e) {
			Log.i("DisplaySettings Exception:",e.getMessage());
		}
		
		
	}
}
