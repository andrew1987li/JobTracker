package co.uk.sbsystems.android.jobtrackermobile;

public class C_PartsUsed {
	private long mvarRecordID = -1;							// RecordID as in the Android database
	private long mvarAndroidJobRecordID = -1;				// Points to Record Id in Android Jobs table.
	// Members above this line are local to the android device.
	
	// Members below this line are received via the TCP/IP socket.
	// And exist as records in Job Tracker Pros table PartsUsed
	private long mvarJTRecordID= -1;						// The recordID as in the Job Tracker Pro table PartsRequired
	private String mvarJobNo = "";							// The Job Number associated with this data.
	private String mvarPartNo = "";
	private Double mvarQty =0.0;
	private String mvarDescription ="";
	private Double mvarUnitPrice =0.0;
	private boolean mvarDelete = false;

	public long getRecordID() {
		return mvarRecordID;
	}
	public void setRecordID(long mvarRecordID) {
		this.mvarRecordID = mvarRecordID;
	}
	public long getAndroidJobRecordID() {
		return mvarAndroidJobRecordID;
	}
	public void setAndroidJobRecordID(long mvarAndroidJobRecordID) {
		this.mvarAndroidJobRecordID = mvarAndroidJobRecordID;
	}
	public long getJTRecordID() {
		return mvarJTRecordID;
	}
	public void setJTRecordID(long mvarJTRecordID) {
		this.mvarJTRecordID = mvarJTRecordID;
	}
	public String getJobNo() {
		return mvarJobNo;
	}
	public void setJobNo(String mvarJobNo) {
		this.mvarJobNo = mvarJobNo;
	}
	public String getPartNo() {
		return mvarPartNo;
	}
	public void setPartNo(String mvarPartNo) {
		this.mvarPartNo = mvarPartNo;
	}
	public Double getQty() {
		return mvarQty;
	}
	public void setQty(Double mvarQty) {
		this.mvarQty = mvarQty;
	}
	public String getDescription() {
		return mvarDescription;
	}
	public void setDescription(String mvarDescription) {
		this.mvarDescription = mvarDescription;
	}
	public Double getUnitPrice() {
		return mvarUnitPrice;
	}
	public void setUnitPrice(Double mvarUnitPrice) {
		this.mvarUnitPrice = mvarUnitPrice;
	}
	public boolean isMvarDelete() {
		return mvarDelete;
	}
	public void setDelete(boolean mvarDelete) {
		this.mvarDelete = mvarDelete;
	}
	
	

}
