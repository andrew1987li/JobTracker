package co.uk.sbsystems.android.jobtrackermobile;

public class C_JobContact {

	private String mvarName;
	private String mvarAddress;
	private String mvarPostCode;
	private String mvarContacts;
	private String mvarTelephone;
	private String mvarMobile;
	private String mvarEmail;
	private String mvarFax;
	private String mvarWWW;
	private C_People mvarPeople;
	private Long mvarClientID;				// Client ID as provided by Job Tracker Pro, Used to link available riser locations with a client.
	
	public C_JobContact() {
		  mvarName = "";
		  mvarAddress = "";
		  mvarPostCode = "";
		  mvarContacts = "";
		  mvarTelephone = "";
		  mvarMobile = "";
		  mvarEmail = "";
		  mvarFax = "";
		  mvarWWW = "";
		  mvarPeople = new C_People();
		  mvarClientID = (long) -1;
		
	}
	
	public Long getClientID() {
		return mvarClientID;
	}
	public void setClientID(Long value) {
		mvarClientID = value;
	}
	
	public String getName() {
		return mvarName;
	}
	
	public void setName(String Value) {
		mvarName = Value;
	}
	

	public String getAddress() {
		return mvarAddress;
	}
	
	public void setAddress(String Value) {
		mvarAddress = Value;
	}

	public String getPostCode() {
		return mvarPostCode == null ? "" : mvarPostCode;
	}
	
	public void setPostCode(String Value) {
		mvarPostCode = Value;
	}

	public String getContacts() {
		return mvarContacts;
	}
	
	public void setContacts(String Value) {
		mvarContacts = Value;
	}
	
	public String getTelephone() {
		return mvarTelephone == null ? "" : mvarTelephone;
	}
	
	public void setTelephone(String Value) {
		mvarTelephone = Value;
	}
	
	public String getMobile() {
		return mvarMobile;
	}
	
	public void setMobile(String Value) {
		mvarMobile = Value;
	}
	
	public String getEmail() {
		return mvarEmail;
	}
	
	public void setEmail(String Value) {
		mvarEmail = Value;
	}
	
	
	public String getFax() {
		return mvarFax;
	}
	
	public void setFax(String Value) {
		mvarFax = Value;
	}
	
	public String getWWW() {
		return mvarWWW;
	}
	
	public void setWWW(String Value) {
		mvarWWW = Value;
	}
	
	public C_People getPeople() {
		return mvarPeople;
	}
	
	public void setPeople(C_People Value) {
		mvarPeople = Value;
	}
	
	
	public String FirstLineOfAddress() {
		try {
			String[] Lines;
			Lines = mvarAddress.split("\n");
			return Lines[0];
		} catch (Exception e) {
			return "";
		}
	}
	
	
	
}
