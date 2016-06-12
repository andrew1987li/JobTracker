package co.uk.sbsystems.android.jobtrackermobile;

public class C_FireHydrant {

	private long mvarRecordID = -1;							// RecordID as in the Android database
	// Members above this line are local to the android device.
	
	// Members below this line are received via the TCP/IP socket.
	// And exist as records in Job Tracker Pros table FireHydrant
	private Long mvarJTRecordID= (long) -1;						// The recordID as in the Job Tracker Pro table PartsRequired
	private String mvarSiteAddress = "";
	private String mvarSitePostCode = "";
	private String mvarTestDate = "";
	private String mvarEngineer = "";
	private String mvarHydrantLocation = "";
	private Integer mvarNumber =0;
	private String[] mvarDetails = new String[12];
	private String mvarJobNo = "";							// The Job Number associated with this data.

	public long getRecordID() {
		return mvarRecordID;
	}
	public void setRecordID(long mvarRecordID) {
		this.mvarRecordID = mvarRecordID;
	}
	public Long getJTRecordID() {
		return mvarJTRecordID;
	}
	public void setJTRecordID(long mvarJTRecordID) {
		this.mvarJTRecordID = mvarJTRecordID;
	}
	public String getSiteAddress() {
		return mvarSiteAddress;
	}
	public void setSiteAddress(String mvarSiteAddress) {
		this.mvarSiteAddress = mvarSiteAddress;
	}
	public String getSitePostCode() {
		return mvarSitePostCode;
	}
	public void setSitePostCode(String mvarSitePostCode) {
		this.mvarSitePostCode = mvarSitePostCode;
	}
	public String getTestDate() {
		return mvarTestDate;
	}
	public void setTestDate(String mvarTestDate) {
		this.mvarTestDate = mvarTestDate;
	}
	public String getEngineer() {
		try {
			if (mvarEngineer != null ) {
				return mvarEngineer;
			} else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
		
	}
	public void setEngineer(String mvarEngineer) {
		this.mvarEngineer = mvarEngineer;
	}
	public String getHydrantLocation() {
		return mvarHydrantLocation;
	}
	public void setHydrantLocation(String mvarHydrantLocation) {
		this.mvarHydrantLocation = mvarHydrantLocation;
	}
	public Integer getNumber() {
		return mvarNumber;
	}
	public void setNumber(Integer mvarNumber) {
		this.mvarNumber = mvarNumber;
	}
	
	public String getDetails(int iNdx) {
		return mvarDetails[iNdx];
	}
	public void setDetails(String mvarDetails,int iNdx) {
		this.mvarDetails[iNdx] = mvarDetails;
	}
	public String getJobNo() {
		return mvarJobNo;
	}
	public void setJobNo(String mvarJobNo) {
		this.mvarJobNo = mvarJobNo;
	}
	
	
	

	

}
