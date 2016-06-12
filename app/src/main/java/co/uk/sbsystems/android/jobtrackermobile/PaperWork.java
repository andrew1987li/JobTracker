package co.uk.sbsystems.android.jobtrackermobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PaperWork extends Activity {

	static TextView txtPaperwork;
	
	public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);
	
	    setContentView(R.layout.paperwork);

	
		try {
			txtPaperwork = (TextView)findViewById(R.id.txtPaperwork );
			txtPaperwork.setText(JTApplication.getInstance().GetApplicationManager().GetloadedJob().getPaperWork());
			
			 Button btnSave= (Button) findViewById(R.id.btnSave);
		        btnSave.setOnClickListener(new View.OnClickListener() {
		        	@Override
					public void onClick(View view) {
		        		// Take contents of text box and store in job.
						JTApplication.getInstance().GetApplicationManager().GetloadedJob().setPaperWork(txtPaperwork.getText().toString());
		        		finish();
		        	}
		        });
		        
				 Button btnClose= (Button) findViewById(R.id.btnClose);
			        btnClose.setOnClickListener(new View.OnClickListener() {
			        	@Override
						public void onClick(View view) {
			        		finish();
			        	}
			        });         			
		        
			
		} catch (Exception e)
		{
			
		}
	
	
	}
	
}
