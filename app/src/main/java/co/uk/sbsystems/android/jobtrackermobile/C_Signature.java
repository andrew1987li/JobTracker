package co.uk.sbsystems.android.jobtrackermobile;

	
public class C_Signature {
	
	Long mvarRecordID;
	String mvarJobNo;
	String mvarSignature;
	Long mvarHash;
	String mvarSurname;
	String mvarDateSigned;		// Stored as Epoch String
	Integer mvarScaleWidth;
	Integer mvarScaleHeight;
	

	public Long getRecordID() {
		return mvarRecordID;
	}

	public void setRecordID(Long mvarRecordID) {
		this.mvarRecordID = mvarRecordID;
	}

	public String getJobNo() {
		return mvarJobNo;
	}

	public void setJobNo(String mvarJobNo) {
		this.mvarJobNo = mvarJobNo;
	}

	public String getSignature() {
		return mvarSignature;
	}

	public void setSignature(String value) {
		mvarSignature = value;
	}

	public Long getHash() {
		return mvarHash;
	}

	public void setHash(Long mvarHash) {
		this.mvarHash = mvarHash;
	}

	public String getSurname() {
		return mvarSurname;
	}

	public void setSurname(String mvarSurname) {
		this.mvarSurname = mvarSurname;
	}

	public String getDateSigned() {
		return mvarDateSigned;
	}

	public void setDateSigned(String mvarDateSigned) {
		this.mvarDateSigned = mvarDateSigned;
	}

	public Integer getScaleWidth() {
		return mvarScaleWidth;
	}

	public void setScaleWidth(Integer mvarScaleWidth) {
		this.mvarScaleWidth = mvarScaleWidth;
	}

	public Integer getScaleHeight() {
		return mvarScaleHeight;
	}

	public void setScaleHeight(Integer mvarScaleHeight) {
		this.mvarScaleHeight = mvarScaleHeight;
	}


	// Constructor
	C_Signature()
	{
		clear();
	}
	
	public void clear()
	{
		mvarRecordID = (long)-1;
		mvarJobNo = "";
		mvarSignature = "";
		mvarHash= (long) 0;
		mvarSurname = "";
		mvarDateSigned = "";
		mvarScaleWidth = 0;
		mvarScaleHeight= 0;
		
	}
	
	
}
