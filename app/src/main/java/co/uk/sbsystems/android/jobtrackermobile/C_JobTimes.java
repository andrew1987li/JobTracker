package co.uk.sbsystems.android.jobtrackermobile;

/*
 This is an example of the XML received from Job Tracker Pro representating TIMESHEETS.
 Its always wrapped in a Job XML.
 
<TIMESHEETS>
<TIMECARD>
	<TC_DATE>30/03/2012 00:00:00</TC_DATE>
	<TC_STARTTIME>30/12/1899 10:00:00</TC_STARTTIME>
	<TC_ENDTIME>30/12/1899 12:00:00</TC_ENDTIME>
	<TC_RECORDID>19539</TC_RECORDID>
	<TC_ENGINEER>21</TC_ENGINEER>
	<TC_JOBNO>DF11262</TC_JOBNO>
	<TC_TYPE>1</TC_TYPE>
	<TC_ENGNAME>Jaime Leighmore</TC_ENGNAME>
	<TC_VALUE>-1</TC_VALUE>
</TIMECARD>

<TIMECARD>
	<TC_DATE>31/03/2012 00:00:00</TC_DATE>
	<TC_STARTTIME>30/12/1899 13:00:00</TC_STARTTIME>
	<TC_ENDTIME>30/12/1899 14:30:00</TC_ENDTIME>
	<TC_RECORDID>19540</TC_RECORDID>
	<TC_ENGINEER>21</TC_ENGINEER>
	<TC_JOBNO>DF11262</TC_JOBNO>
	<TC_TYPE>7</TC_TYPE>
	<TC_ENGNAME>Jaime Leighmore</TC_ENGNAME>
	<TC_VALUE>-1</TC_VALUE>
</TIMECARD>
</TIMESHEETS>
*/

public class C_JobTimes {
	
	public static final int TIMETYPE_TRAVEL_V = 1;
	public static final int TIMETYPE_ONSITE_V = 7;
	public static final int TIMETYPE_TRAVELTO_V = 2;
	public static final int TIMETYPE_TRAVELFROM_V = 3;
	public static final int TIMETYPE_MILEAGETO_V = 4;
	public static final int TIMETYPE_MILEAGEFROM_V = 5;
	public static final int TIMETYPE_MILEAGE_V = 6;
	public static final int TIMETYPE_CALLOUT_V = 8;
	public static final int TIMETYPE_FIXEDCOST_V = 9;
	
	public static final String TIMETYPE_TRAVEL = "Travel";
	public static final String TIMETYPE_ONSITE = "On Site";
	public static final String TIMETYPE_TRAVELTO = "Travel To";
	public static final String TIMETYPE_TRAVELFROM = "Travel From";
	public static final String TIMETYPE_MILEAGETO = "Mileage To";
	public static final String TIMETYPE_MILEAGEFROM = "Mileage From";
	public static final String TIMETYPE_MILEAGE = "Mileage";
	public static final String TIMETYPE_CALLOUT = "Call out £";
	public static final String TIMETYPE_FIXEDCOST = "Fixed Cost £";
	

	private long mvarRecordID = -1;							// RecordID as in the Android database
	private long mvarAndroidJobRecordID = -1;				// Points to Record Id in Android Jobs table.
	// Members above this line are local to the android device.
	
	// Members below this line are received via the TCP/IP socket.
	// And exist as records in Job Tracker Pros table JobTimes
	private long mvarJTRecordID= -1;						// The recordID as in the Job Tracker Pro table JobTimes
	private long mvarEngineerID =-1;						// The RecordID of the Engineer as in the Job Tracker Pro database.
	private String mvarEventDate = "";
	private String mvarStartTime = "";
	private String mvarEndTime = "";
	private Integer mvarTimeType = 0;						// Identifies the type of data.  See constants above.
	private String mvarJobNo = "";							// The Job Number associated with this data.
	private double mvarEntryValue =0;						// Holds the value of either the Cost £ or Miles if entered.

	// Members below this line are received via the TCP/IP socket.
	// They are virtual members passed to Android for easy reference.
	// They DO NOT exist in Job Tracker Pro's JobTimes table.
	private String EngName = "";							// A string representation of the engineers name.

	
	
	
	public String getEventDate() {
		return mvarEventDate;
	}
	public void setEventDate(String mvarEventDate) {
		this.mvarEventDate = mvarEventDate;
	}
	public String getStartTime() {
		return mvarStartTime;
	}
	public void setStartTime(String mvarStartTime) {
		this.mvarStartTime = mvarStartTime;
	}
	public String getEndTime() {
		return mvarEndTime;
	}
	public void setEndTime(String mvarEndTime) {
		this.mvarEndTime = mvarEndTime;
	}
	public Integer getTimeType() {
		return mvarTimeType;
	}
	public void setTimeType(Integer mvarTimeType) {
		this.mvarTimeType = mvarTimeType;
	}
	
	public long getEngineerID() {
		return mvarEngineerID;
	}
	public void setEngineerID(long mvarEngineerID) {
		this.mvarEngineerID = mvarEngineerID;
	}
	public String getJobNo() {
		return mvarJobNo;
	}
	public void setJobNo(String mvarJobNo) {
		this.mvarJobNo = mvarJobNo;
	}
	
	public long getAndroidJobRecordID() {
		return mvarAndroidJobRecordID;
	}
	
	public void setAndroidJobRecordID (long recordID) {
		mvarAndroidJobRecordID = recordID;
	}
	
	public void setRecordID (long recordID) {
		mvarRecordID = recordID;
	}
	
	public long getRecordID() {
		return mvarRecordID;
	}
	
	
	public String getEngName() {
		return EngName;
	}
	public void setEngName(String engName) {
		EngName = engName;
	}
	
	public double getEntryValue() {
		return mvarEntryValue;
	}
	public void setEntryValue(double mvarEntryValue) {
		this.mvarEntryValue = mvarEntryValue;
	}
	

	public String TimeTypeToString() {
		String ret="";
		
		switch (mvarTimeType) {
			case TIMETYPE_TRAVEL_V:
				ret = TIMETYPE_TRAVEL;
				break;
			case TIMETYPE_ONSITE_V:
				ret = TIMETYPE_ONSITE;
				break;
			case TIMETYPE_TRAVELTO_V:
				ret = TIMETYPE_TRAVELTO;
				break;
			case TIMETYPE_TRAVELFROM_V:
				ret = TIMETYPE_TRAVELFROM;
				break;
			case TIMETYPE_MILEAGETO_V:
				ret = TIMETYPE_MILEAGETO;
				break;
			case TIMETYPE_MILEAGEFROM_V:
				ret = TIMETYPE_MILEAGEFROM;
				break;
			case TIMETYPE_MILEAGE_V:
				ret = TIMETYPE_MILEAGE;
				break;
			case TIMETYPE_CALLOUT_V:
				ret = TIMETYPE_CALLOUT;
				break;
			case TIMETYPE_FIXEDCOST_V:
				ret = TIMETYPE_FIXEDCOST;
				break;

			default:
		}
		return ret;
	}
	
	
	public Integer TimeTypeFromString(String value) {
		Integer ret =0;
		
		if (value.equals(TIMETYPE_TRAVEL)) 
			ret = TIMETYPE_TRAVEL_V;
		else if (value.equals(TIMETYPE_ONSITE)) 
			ret = TIMETYPE_ONSITE_V;
		else if (value.equals(TIMETYPE_TRAVELTO)) 
			ret = TIMETYPE_TRAVELTO_V;
		else if (value.equals(TIMETYPE_TRAVELFROM)) 
			ret = TIMETYPE_TRAVELFROM_V;
		else if (value.equals(TIMETYPE_MILEAGETO)) 
			ret = TIMETYPE_MILEAGETO_V;
		else if (value.equals(TIMETYPE_MILEAGEFROM)) 
			ret = TIMETYPE_MILEAGEFROM_V;
		else if (value.equals(TIMETYPE_MILEAGE)) 
			ret = TIMETYPE_MILEAGE_V;
		else if (value.equals(TIMETYPE_CALLOUT)) 
			ret = TIMETYPE_CALLOUT_V;
		else if (value.equals(TIMETYPE_FIXEDCOST)) 
			ret = TIMETYPE_FIXEDCOST_V;

		return ret;
	}
	
	
	
}
