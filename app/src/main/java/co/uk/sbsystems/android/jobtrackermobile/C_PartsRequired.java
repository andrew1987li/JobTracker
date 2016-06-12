package co.uk.sbsystems.android.jobtrackermobile;

public class C_PartsRequired {

	private long mvarRecordID = -1;							// RecordID as in the Android database
	private long mvarAndroidJobRecordID = -1;				// Points to Record Id in Android Jobs table.
	// Members above this line are local to the android device.
	
	// Members below this line are received via the TCP/IP socket.
	// And exist as records in Job Tracker Pros table JobTimes
	private long mvarJTRecordID= -1;						// The recordID as in the Job Tracker Pro table PartsRequired
	private String mvarJobNo = "";							// The Job Number associated with this data.
	private Double mvarQty = 0.0;
	private String mvarDescription = "";
	private String mvarKLSPartNo = "";						// This is the internal part no, Originally  bespoke for KLS, now main stream.
	private String mvarSupplierName = "";
	private String mvarSupplierPartNo = "";
	private String mvarEngineer = "";
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
	public Double getQty() {
		return mvarQty;
	}
	public void setQty(double mvarQty) {
		this.mvarQty = mvarQty;
	}
	public String getDescription() {
		return mvarDescription;
	}
	public void setDescription(String mvarDescription) {
		this.mvarDescription = mvarDescription;
	}
	public String getOurPartNo() {
		return mvarKLSPartNo;
	}
	public void setOurPartNo(String mvarKLSPartNo) {
		this.mvarKLSPartNo = mvarKLSPartNo;
	}
	public String getSupplierName() {
		return mvarSupplierName;
	}
	public void setSupplierName(String mvarSupplierName) {
		this.mvarSupplierName = mvarSupplierName;
	}
	public String getSupplierPartNo() {
		return mvarSupplierPartNo;
	}
	public void setSupplierPartNo(String mvarSupplierPartNo) {
		this.mvarSupplierPartNo = mvarSupplierPartNo;
	}
	public String getEngineer() {
		return mvarEngineer;
	}
	public void setEngineer(String mvarEngineer) {
		this.mvarEngineer = mvarEngineer;
	}
	public boolean isMvarDelete() {
		return mvarDelete;
	}
	public void setDelete(boolean mvarDelete) {
		this.mvarDelete = mvarDelete;
	}

}
