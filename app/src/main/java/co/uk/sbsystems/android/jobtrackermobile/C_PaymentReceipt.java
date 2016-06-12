package co.uk.sbsystems.android.jobtrackermobile;





public class C_PaymentReceipt {
	
	public static final int PAYTYPE_CASH_V = 1;
	public static final int PAYTYPE_CHEQUE_V = 2;
	public static final int PAYTYPE_CARD_V = 3;
	
	
	public static final String PAYTYPE_CASH = "Cash";
	public static final String PAYTYPE_CARD = "Card";
	public static final String PAYTYPE_CHEQUE = "Cheque";
	
	private long mvarAndroidJobRecordID = -1;

	private long mvarRecordID = -1;
	private String mvarJobNo = "";
	private Double mvarAmount = 0.0;
	private String mvarPayType ="";				// Method of payment, i.e. cash, card, cheque.
	private String mvarReceiptNo = "";
	private String mvarDateTaken = "";
	private String mvarEngineerName ="";;
	private String mvarDateInOffice = "";
	private String mvarReceivedBy = "";
	private String mvarDescription = "";
	private boolean mvarDelete = false;
		
	public long getRecordID() {
		return mvarRecordID;
	}
	public void setRecordID(long mvarRecordID) {
		this.mvarRecordID = mvarRecordID;
	}
	public String getJobNo() {
		return mvarJobNo;
	}
	public void setJobNo(String mvarJobNo) {
		this.mvarJobNo = mvarJobNo;
	}
	public Double getAmount() {
		return mvarAmount;
	}
	public void setAmount(Double mvarAmount) {
		this.mvarAmount = mvarAmount;
	}
	public String getPayType() {
		return mvarPayType;
	}
	public void setPayType(String mvarPayType) {
		this.mvarPayType = mvarPayType;
	}
	public String getReceiptNo() {
		return mvarReceiptNo;
	}
	public void setReceiptNo(String mvarReceiptNo) {
		this.mvarReceiptNo = mvarReceiptNo;
	}
	public String getDateTaken() {
		return mvarDateTaken;
	}
	public void setDateTaken(String mvarDateTaken) {
		this.mvarDateTaken = mvarDateTaken;
	}
	public String getEngineerName() {
		return mvarEngineerName;
	}
	public void setEngineerName(String mvarEngineerName) {
		this.mvarEngineerName = mvarEngineerName;
	}
	public String getDateInOffice() {
		return mvarDateInOffice;
	}
	public void setDateInOffice(String mvarDateInOffice) {
		this.mvarDateInOffice = mvarDateInOffice;
	}
	public String getReceivedBy() {
		return mvarReceivedBy;
	}
	public void setReceivedBy(String mvarReceivedBy) {
		this.mvarReceivedBy = mvarReceivedBy;
	}
	public String getDescription() {
		return mvarDescription;
	}
	public void setDescription(String mvarDescription) {
		this.mvarDescription = mvarDescription;
	}
	public boolean isMvarDelete() {
		return mvarDelete;
	}
	public void setDelete(boolean mvarDelete) {
		this.mvarDelete = mvarDelete;
	}
	
	public long getAndroidJobRecordID() {
		return mvarAndroidJobRecordID;
	}
	
	
	public void setAndroidJobRecordID(long mvarAndroidJobRecordID) {
		this.mvarAndroidJobRecordID = mvarAndroidJobRecordID;
	}
	
	
	
	
	public Integer PayTypeFromString(String value) {
		Integer ret =0;
		
		if (value.equals(PAYTYPE_CASH)) 
			ret = PAYTYPE_CASH_V;
		else if (value.equals(PAYTYPE_CHEQUE)) 
			ret = PAYTYPE_CHEQUE_V;
		else if (value.equals(PAYTYPE_CARD)) 
			ret = PAYTYPE_CARD_V;

		return ret;
	}
	
	
	
	public String PayTypeToString(int payType) {
		String ret = "";
		
		switch (payType) 
		{	
			case PAYTYPE_CASH_V:
				ret = PAYTYPE_CASH;
				break;
				
			case PAYTYPE_CHEQUE_V:
				ret = PAYTYPE_CHEQUE;
				break;
				
			case PAYTYPE_CARD_V:
				ret = PAYTYPE_CARD;
				break;
				
		}
		
		return ret;
	}
	
	
}
