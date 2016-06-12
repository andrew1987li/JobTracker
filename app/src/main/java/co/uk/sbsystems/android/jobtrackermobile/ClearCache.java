package co.uk.sbsystems.android.jobtrackermobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClearCache extends Activity{

    Button btnExit;
    Button btnClear;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    

	    setContentView(R.layout.clearcache );

	    btnClear= (Button) findViewById(R.id.btnClearCache );
	    btnExit = (Button) findViewById(R.id.btnExitCache);
	    
        btnExit.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View view) {
        		finish();
        	}
        });
        
        btnClear.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View view) {
				JTApplication.getInstance().GetDatabaseManager().ClearJobCache();
        		finish();
        	}
        });          
        
	    
	    
	}
	
}
