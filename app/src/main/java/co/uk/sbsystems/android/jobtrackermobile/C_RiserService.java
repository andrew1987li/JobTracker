package co.uk.sbsystems.android.jobtrackermobile;

public class C_RiserService {

	private static final String TAG = "C_RiserService";
	
	public static final int PUMPSIGNATURE_ACTIVITY = 1;
	public static final int STACKSIGNATURE_ACTIVITY = 2;
	public static final int CUSTSIGNATURE_ACTIVITY = 3;
	
	private long mvarRecordID;
	private long mvarJTRecordID;
	private Integer mvarRiserNumber;
	private String mvarRiserLocation;
	private String mvarJobNo;
	private String[] mvarExtServiceCheck  = new String[6];
	private String[] mvarExtDetails = new String[6];
	private String[] mvarExtStatus = new String[6];
	private String[] mvarIntServiceCheck = new String[5];
	private String[] mvarIntDetails = new String[5];
	private String[] mvarAir = new String[2];
	private String[] mvarWater = new String[2];
	private String[] mvarCompletionChecked = new String[3];
	private String mvarComments;
	private String mvarRemedialWorks;
	private String mvarOverAllStatus;
	private C_Signature mvarEngSigPump;
	private C_Signature mvarEngSigStack;
	private C_Signature mvarCustSig;
	private String mvarPrintedDate;		
	private String mvarOutLetQty;
	private String mvarOutLetKey;
	private String mvarInLetKey;

	
	public String getOutLetQty() {
		return mvarOutLetQty;
	}
	
	public void setOutLetQty(String mvarValue) {
		this.mvarOutLetQty = mvarValue;
	}
	
	
	public String getOutLetKey() {
		return mvarOutLetKey;
	}
	public void setOutLetKey(String mvarValue) {
		this.mvarOutLetKey = mvarValue;
	}
	
	public String getInLetKey() {
		return mvarInLetKey;
	}
	public void setInLetKey(String mvarValue) {
		this.mvarInLetKey = mvarValue;
	}
	
	

	// Constructor
    public C_RiserService() {
    	clear();
    }
    
    public void clear()
    {
    	mvarEngSigPump = new C_Signature();
    	mvarEngSigStack = new C_Signature();
    	mvarCustSig = new C_Signature();
    }
    
	
	public long getRecordID() {
	return mvarRecordID;
	}
	public void setRecordID(long mvarRecordID) {
		this.mvarRecordID = mvarRecordID;
	}
	
	public Long getJTRecordID() {
		return mvarJTRecordID;
	}
	
	public void setJTRecordID(long mvarRecordID) {
		this.mvarJTRecordID = mvarRecordID;
	}
	
	
	public Integer getRiserNumber() {
		return mvarRiserNumber;
	}
	public void setRiserNumber(Integer mvarRiserNumber) {
		this.mvarRiserNumber = mvarRiserNumber;
	}
	public String getRiserLocation() {
		return mvarRiserLocation;
	}
	public void setRiserLocation(String mvarRiserLocation) {
		this.mvarRiserLocation = mvarRiserLocation;
	}
	public String getJobNo() {
		return mvarJobNo;
	}
	public void setJobNo(String mvarJobNo) {
		this.mvarJobNo = mvarJobNo;
	}
	public String getExtServiceCheck(Integer iNdx) {
		return mvarExtServiceCheck[iNdx];
	}
	public void setExtServiceCheck(int iNdx,String  sValue) {
		this.mvarExtServiceCheck[iNdx] = sValue;
	}
	public String getExtDetails(int iNdx) {
		return mvarExtDetails[iNdx];
	}
	public void setExtDetails(int iNdx, String sValue) {
		this.mvarExtDetails[iNdx] = sValue;
	}
	public String getExtStatus(int iNdx) {
		return mvarExtStatus[iNdx];
	}
	public void setExtStatus(int iNdx, String sValue) {
		this.mvarExtStatus[iNdx] = sValue;
	}
	public String getIntServiceCheck(int iNdx) {
		return mvarIntServiceCheck[iNdx];
	}
	public void setIntServiceCheck(int iNdx,String sValue) {
		this.mvarIntServiceCheck[iNdx] =sValue;
	}
	public String getIntDetails(int iNdx) {
		return mvarIntDetails[iNdx];
	}
	public void setIntDetails(int iNdx,String sValue) {
		this.mvarIntDetails[iNdx] = sValue;
	}
	public String getAir(int iNdx) {
		return mvarAir[iNdx];
	}
	public void setAir(int iNdx, String sValue) {
		this.mvarAir[iNdx] = sValue;
	}
	public String getWater(int iNdx) {
		return mvarWater[iNdx];
	}
	public void setWater(int iNdx,String sValue) {
		this.mvarWater[iNdx] = sValue;
	}
	public String getCompletionChecked(int iNdx) {
		return mvarCompletionChecked[iNdx];
	}
	public void setCompletionChecked(int iNdx,String sValue) {
		this.mvarCompletionChecked[iNdx] = sValue;
	}
	public String getComments() {
		return mvarComments;
	}
	public void setComments(String mvarComments) {
		this.mvarComments = mvarComments;
	}
	public String getRemedialWorks() {
		return mvarRemedialWorks;
	}
	public void setRemedialWorks(String mvarRemedialWorks) {
		this.mvarRemedialWorks = mvarRemedialWorks;
	}
	public String getOverAllStatus() {
		return mvarOverAllStatus;
	}
	public void setOverAllStatus(String mvarOverAllStatus) {
		this.mvarOverAllStatus = mvarOverAllStatus;
	}
	public C_Signature getEngSigPump() {
		return mvarEngSigPump;
	}
	
	public void setPumpSignature(String sValue) {
		this.mvarEngSigPump.mvarSignature = sValue;
	}
	public void setPumpSurname(String sValue) {
		this.mvarEngSigPump.mvarSurname  = sValue;
	}
	public void setPumpDate(String sValue) {
		this.mvarEngSigPump.mvarDateSigned = sValue;
	}
	
	
	
	
	public C_Signature getEngSigStack() {
		return mvarEngSigStack;
	}
	
	public void setStackSignature(String sValue) {
		this.mvarEngSigStack.mvarSignature = sValue;
	}
	public void setStackSurname(String sValue) {
		this.mvarEngSigStack.mvarSurname  = sValue;
	}
	public void setStackDate(String sValue) {
		this.mvarEngSigStack.mvarDateSigned = sValue;
	}
	
	public C_Signature getCustSig() {
		return mvarCustSig;
	}
	
	public void setCustSignature(String sValue) {
		this.mvarCustSig.mvarSignature = sValue;
	}
	public void setCustSurname(String sValue) {
		this.mvarCustSig.mvarSurname  = sValue;
	}
	public void setCustDate(String sValue) {
		this.mvarCustSig.mvarDateSigned = sValue;
	}
	
	public String getPrintedDate() {
		return mvarPrintedDate;
	}
	public void setPrintedDate(String mvarPrintedDate) {
		this.mvarPrintedDate = mvarPrintedDate;
	}
	

				
}
