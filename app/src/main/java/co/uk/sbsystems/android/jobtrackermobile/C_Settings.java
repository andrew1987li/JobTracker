package co.uk.sbsystems.android.jobtrackermobile;

import android.util.Log;

// This class holds the various settings programmed and TCP'd from Job Tracker Professional.
//
//

public class C_Settings {
	private Boolean mvar_ShowPartsUsedPrice = false;
	private Boolean mvar_JobNumbersAreNumeric = false;
	private Boolean mvar_UseDueDate  = false;
	private Boolean mvar_UseDateCreated  = false;
	private Boolean mvar_DisplayJobBrief  = false;
	
	private Boolean mvar_HideMainJobComplete = false;
	private Boolean mvar_ShowWorksCarriedOut = false;
	private Boolean mvar_ShowCauseOfProblem = false;
	private Boolean mvar_ShowEngFinished = false;
	
	private Boolean mvar_butLogTime = false; 
	private Boolean mvar_butDrawSketch = false; 
	private Boolean mvar_butPartsUsed = false; 
	private Boolean mvar_butPaperwork = false; 
	private Boolean mvar_butgetSignature = false; 
	private Boolean mvar_butPartsRequired = false; 
	private Boolean mvar_butPayments = false; 
	private Boolean mvar_butFireHydrants = false; 
	private Boolean mvar_butRiserService = false; 
	
	private Boolean mvar_LoginFailed = false;
	private String mvar_DueDateDesc = "Due Date";
	private String mvar_DueDateField = "JOBDATE4";
	
	
	public String getDueDateDesc() {
		return mvar_DueDateDesc;
	}
	
	public void setDueDateDesc(String value) {
		mvar_DueDateDesc = value;
	}
	
	public String getDueDateField() {
		return mvar_DueDateField;
	}
	
	public void setDueDateField(String value) {
		mvar_DueDateDesc = value;
	}
	
	public Boolean getLoginFailed() {
		return mvar_LoginFailed;
	}
	
	public void setLoginFailed(Boolean value) {
		mvar_LoginFailed = value;
	}
	
	public Boolean getShowPartsUsedPrice() {
		return mvar_ShowPartsUsedPrice;
	}
	
	public Boolean getJobNumbersAreNumeric() {
		return mvar_JobNumbersAreNumeric;
	}
	

	public Boolean getUseDueDate() {
		return mvar_UseDueDate;
	}

	public Boolean getUseDateCreated() {
		return mvar_UseDateCreated;
	}

	public Boolean getDisplayJobBrief() {
		return mvar_DisplayJobBrief;
	}
	
	public Boolean getHideMainJobComplete() {
		return mvar_HideMainJobComplete;
	}
	
	public Boolean getShowWorksCarriedOut() {
		return mvar_ShowWorksCarriedOut ;
	}

	public Boolean getShowCauseOfProblem() {
		return mvar_ShowCauseOfProblem;
	}
	
	public Boolean getShowEngFinished() {
		return mvar_ShowEngFinished;
	}

	public Boolean getbutLogTime() {
		return mvar_butLogTime;
	}
	
	public Boolean getbutDrawSketch() {
		return mvar_butDrawSketch;
	}
	
	public Boolean getbutPartsUsed() {
		return mvar_butPartsUsed;
	}

	public Boolean getbutPaperwork() {
		return mvar_butPaperwork;
	}

	public Boolean getbutgetSignature() {
		return mvar_butgetSignature;
	}

	public Boolean getbutPartsRequired() {
		return mvar_butPartsRequired;
	}

	public Boolean getbutPayments() {
		return mvar_butPayments;
	}

	public Boolean getbutFireHydrants() {
		return mvar_butFireHydrants;
	}

	// This is a disabled flag.  i.e. it returns True if its disabled.
	public Boolean getbutRiserService() {
		return mvar_butRiserService;
	}
	
	
	public void ParseSettings(String DataIn )
	{
    	Parser objParser;
		objParser = new Parser();
		
		String sData;
		
		try {
			sData= objParser.ExtractTagValue(DataIn, "SHOWPRICES");
			if (sData.equals("Yes")) {
				mvar_ShowPartsUsedPrice  = true;
			} else {
				mvar_ShowPartsUsedPrice  = false;
			}
			
			sData= objParser.ExtractTagValue(DataIn, "JOBNONUMERIC");
			if (sData.equals("Yes")) {
				mvar_JobNumbersAreNumeric  = true;
			} else {
				mvar_JobNumbersAreNumeric  = false;
			}
			
			sData= objParser.ExtractTagValue(DataIn, "USEDUEDATE");
			mvar_UseDueDate = sData.equals("Yes") ? true : false ;
			
			sData= objParser.ExtractTagValue(DataIn, "USEDATECREATED");
			mvar_UseDateCreated = sData.equals("Yes") ? true : false ;

			sData= objParser.ExtractTagValue(DataIn, "DISPLAYJOBBRIEF");
			mvar_DisplayJobBrief = sData.equals("Yes") ? true : false ;
			

			sData= objParser.ExtractTagValue(DataIn, "LOGTIME");
			mvar_butLogTime = sData.equals("Yes") ? true : false ;

			sData= objParser.ExtractTagValue(DataIn, "DRAWSKETCH");
			mvar_butDrawSketch = sData.equals("Yes") ? true : false ;

			sData= objParser.ExtractTagValue(DataIn, "PARTSUSED");
			mvar_butPartsUsed = sData.equals("Yes") ? true : false ;

			sData= objParser.ExtractTagValue(DataIn, "PAPERWORK");
			mvar_butPaperwork = sData.equals("Yes") ? true : false ;

			sData= objParser.ExtractTagValue(DataIn, "GETSIG");
			mvar_butgetSignature = sData.equals("Yes") ? true : false ;

			sData= objParser.ExtractTagValue(DataIn, "PARTSREQ");
			mvar_butPartsRequired = sData.equals("Yes") ? true : false ;

			sData= objParser.ExtractTagValue(DataIn, "PAYMENTS");
			mvar_butPayments = sData.equals("Yes") ? true : false ;

			sData= objParser.ExtractTagValue(DataIn, "FIREHYDRANTS");
			mvar_butFireHydrants = sData.equals("Yes") ? true : false ;

			sData= objParser.ExtractTagValue(DataIn, "RISERSERVICE");
			mvar_butRiserService = sData.equals("Yes") ? true : false ;
			
			sData = objParser.ExtractTagValue(DataIn,"HIDEMAINJOBCOMPLETE");
			mvar_HideMainJobComplete = sData.equals("Yes") ? true : false ;
			
			sData = objParser.ExtractTagValue(DataIn,"ENGFINISHED");
			mvar_ShowEngFinished= sData.equals("Yes") ? true : false ;
			
			sData = objParser.ExtractTagValue(DataIn,"WORKSCARRIEDOUT");
			mvar_ShowWorksCarriedOut= sData.equals("Yes") ? true : false ;
			
			sData = objParser.ExtractTagValue(DataIn,"CAUSEOFPROBLEM");
			mvar_ShowCauseOfProblem= sData.equals("Yes") ? true : false ;

			sData = objParser.ExtractTagValue(DataIn,"LOGINFAILED");
			mvar_LoginFailed= sData.equals("Yes") ? true : false ;
			
			try {
				sData = objParser.ExtractTagValue(DataIn,"DUEDATEDESC");
				mvar_DueDateDesc= sData;
				
			} catch (Exception e) {
				mvar_DueDateDesc= "Due Date:";
			} 
			
			try {
				sData = objParser.ExtractTagValue(DataIn,"DUEDATEFIELD");
				mvar_DueDateField= sData;
				
			} catch (Exception e) {
				mvar_DueDateField = "JOBDATE4";
			}
			
			
	;
			if (mvar_LoginFailed == true)
			{
				String sExit; 
				sExit = JTApplication.getInstance().getTCPManager().RequestTermination();
				JTApplication.getInstance().getTCPManager().SendData(sExit);
			}
			
		} catch (Exception e)
		{
			Log.i("ParseSettings Exception:",e.getMessage());
		}
			
	}
	
	
	
}
