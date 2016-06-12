package co.uk.sbsystems.android.jobtrackermobile;

public class C_RiserLocation {
	
	private long mvarRecordID;
	private long mvarJTRecordID;
	private Integer mvarNumber;
	private String mvarLocation;
	private Long mvarClientId;
	private String mvarOutletQty;
	private String mvarInletKey;
	private String mvarOutletKey;
	
	
	
	public long getRecordID() {
		return mvarRecordID;
	}
	public void setRecordID(long mvarRecordID) {
		this.mvarRecordID = mvarRecordID;
	}
	
	public long getJTRecordID() {
		return mvarJTRecordID;
	}
	public void setJTRecordID(long mvarRecordID) {
		this.mvarJTRecordID = mvarRecordID;
	}
	
	
	public Integer getNumber() {
		return mvarNumber;
	}
	public void setNumber(Integer mvarNumber) {
		this.mvarNumber = mvarNumber;
	}
	public String getLocation() {
		return mvarLocation;
	}
	public void setLocation(String mvarLocation) {
		this.mvarLocation = mvarLocation;
	}
	public Long getClientId() {
		return mvarClientId;
	}
	public void setClientId(Long mvarClientId) {
		this.mvarClientId = mvarClientId;
	}
	
	public void setOutletQty(String sValue) {
		this.mvarOutletQty = sValue;
	}
	
	public String getOutletQty() {
		return this.mvarOutletQty;
	}

	public void setInletKey(String sValue) {
		this.mvarInletKey = sValue;
	}
	
	public String getInletKey() {
		return this.mvarInletKey;
	}
	
	
	
	public void setOutletKey(String sValue) {
		this.mvarOutletKey = sValue;
	}
	
	public String getOutletKey() {
		return this.mvarOutletKey;
	}
	
	
	
	
}
