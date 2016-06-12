package co.uk.sbsystems.android.jobtrackermobile;

import android.os.AsyncTask;
import android.util.Log;

//
// Params: Search String, Number of Days, New Only, Closed 
//
//
public class C_RequestJobs extends AsyncTask<String, Integer, String> {
	
	Boolean NewOnly = false;
	Boolean ClosedOnly = false;
	String SearchStr = "";
	Boolean UseDueDate = false;
	String AssignedTo = "";
	Integer iDays = 0;

	@Override
	protected String doInBackground(String... params) {
		android.os.Debug.waitForDebugger();
		
		
		try {
			SearchStr = params[0];
			iDays =  Integer.parseInt(params[1]);		// This can throw an exception if NULL ("")
			NewOnly = Boolean.valueOf(params[2]);
			ClosedOnly = Boolean.valueOf(params[3]);
		} 
		catch (Exception e)
		{
		}

		try {
			if(JTApplication.getInstance().getTCPManager().AreWeOnline() == true) {
				// We're online, request data.
				JTApplication.getInstance().getTCPManager().RequestListJobs(iDays, NewOnly, SearchStr, ClosedOnly, AssignedTo, UseDueDate);
			}
		}
		catch (Exception e)
		{
			Log.i("C_RequestJobs", e.getMessage());
		}
		
		return "";
	} 
	
	 @Override
     protected void onPreExecute() 
	 {
		try {
			String AssignedTo = JTApplication.getInstance().GetApplicationManager().getDeviceAssignedEngineerName();
			if (AssignedTo==null) {
				AssignedTo = "";
			}
			UseDueDate = JTApplication.getInstance().getSettings().getUseDueDate();

			JTApplication.getInstance().GetApplicationManager().DoNotClearjobSummary(false);
			
		}
		catch (Exception e)
		{
			Log.i("C_RequestJobs", e.getMessage());
		}
	 }

}
