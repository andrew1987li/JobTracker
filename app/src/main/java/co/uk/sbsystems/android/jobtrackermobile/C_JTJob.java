package co.uk.sbsystems.android.jobtrackermobile;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;
import android.widget.Gallery;

public class C_JTJob {

	private static final String TAG = "C_JTJob";
	
	private String mvarJobNo;
	private String mvarJobStatus;
	private String mvarPickList1;
	private String mvarPickList2;
	private String mvarPickList3;
	private String mvarPickList4;
	private String mvarPickList5;
	private String mvarJobText1;
	private String mvarJobText2;
	private String mvarJobText3;
	private String mvarJobText4;
	private String mvarJobText5;
	private String mvarJobText6;
	private String mvarJobText7;
	private String mvarJobText8;
	private String mvarJobText9;
	private String mvarJobText10;
	private String mvarJobText11;
	private String mvarJobText12;
	private String mvarJobText13;
	private String mvarJobBrief;
	private String mvarJobDate1;
	private String mvarJobDate2;
	private String mvarJobDate3;
	private String mvarJobDate4;
	private String mvarJobDate5;
	private String mvarJobDate6;
	private String mvarJobDate7;
	private String mvarJobDate8;
	private String mvarJobDate9;
	private String mvarRecallReason;
	private C_JobContact mvarSiteAddress;
	private C_JobContact mvarClientAddress;
	private String mvarEngineers;
	private String mvarSkills;
	private String mvarClientNotes;
	private String mvarClientDate0;
	private String mvarClientDate1;
	private String mvarClientDate2;
	private String mvarClientDate3;
	private String mvarClientDate4;
	private String mvarClientDate5;
	private String mvarClientFields0;
	private String mvarClientFields1;
	private String mvarClientFields2;
	private String mvarClientFields3;
	private String mvarClientFields4;
	private String mvarClientFields5;
	private String mvarClientFields6;
	private String mvarClientFields7;
	private String mvarClientFields8;
	private String mvarClientFields9;
	private String mvarClientFields10;
	private String mvarClientFields11;
	private Boolean mvarJobFinished;		// Not sure what this is for at the moment.
	
	private Boolean mvarEngFinished;
	private String mvarWorksCarriedOut;
	private String mvarCauseOfProblem; 
	
	private String mvarEstimateNo;
	private String mvarEngineerText;
	private String mvarInvoiceNo;
	private String mvarInvFileName;
	private String mvarJobContacts;

    private String mvarSMSHistory;
    private Integer mvarAwaitingPayment;
    private Integer mvarNeedReport;
    private Integer mvarReportPrinted;
    private String mvarReportDate;
    private String mvarSearchData;
    private String mvarJobContactsCSV;
    private String mvarDNumber;
    private String mvarInvoiceNo2;
    private Integer mvarDiscount;
    private Integer mvarNewJob;
    
    private String mvarSLA;		// Added for UK Dry Riser to show SLA on main jobs screen.
    
    // private C_JobTimes mvarJobTimes;
    private ArrayList<C_JobTimes> mvarJobTimes = new ArrayList<C_JobTimes>();
    private C_Signature mvarSignature;
    private C_Signature mvarSketch;

    private Boolean mvarJobCompleted;					// This is what is used to mark the job as live or completed, other field mvarJobFinished not sure what that is for at the mo.
    private Boolean mvarPendingUpload;                  // True = Needs uploaded to server.
    private String mvarDateModified;                    //  The time / date this was last modified.

    private String mvarPaperWork;
    private ArrayList<C_PartsRequired> mvarPartsRequired = new ArrayList<C_PartsRequired>();
    private ArrayList<C_PaymentReceipt> mvarPaymentReceipt = new ArrayList<C_PaymentReceipt>();    
    private ArrayList<C_PartsUsed> mvarPartsUsed = new ArrayList<C_PartsUsed>();
    private ArrayList<C_FireHydrant> mvarFireHydrant= new ArrayList<C_FireHydrant>();
    private ArrayList<C_RiserService> mvarRiserService= new ArrayList<C_RiserService>();
    private ArrayList<C_RiserLocation> mvarRiserLocation= new ArrayList<C_RiserLocation>();

	public ArrayList<GalleryImgItem> image_paths_list = new ArrayList<GalleryImgItem>();

    // Constructor
    public C_JTJob() {
    	clear();
    }
	
    public void clear()
    {
    	mvarSiteAddress = new C_JobContact();
    	mvarClientAddress = new C_JobContact();
    	mvarJobTimes.clear();
    	mvarSignature = new C_Signature();
    	mvarSketch = new C_Signature();
    	mvarPartsRequired.clear();
    	mvarPaymentReceipt.clear();
    	mvarPartsUsed.clear();
    	mvarFireHydrant.clear();
    	mvarRiserService.clear();
    	mvarRiserLocation.clear();
		mvarJobNo = "";
		mvarSLA = "";
		mvarJobStatus = "";
		mvarPickList1="";
		mvarPickList2="";
		mvarPickList3="";
		mvarPickList4="";
		mvarPickList5="";
		mvarJobText1="";
		mvarJobText2="";
		mvarJobText3="";
		mvarJobText4="";
		mvarJobText5="";
		mvarJobText6="";
		mvarJobText7="";
		mvarJobText8="";
		mvarJobText9="";
		mvarJobText10="";
		mvarJobText11="";
		mvarJobText12="";
		mvarJobText13="";
		mvarJobBrief="";
		mvarJobDate1="";
		mvarJobDate2="";
		mvarJobDate3="";
		mvarJobDate4="";
		mvarJobDate5="";
		mvarJobDate6="";
		mvarJobDate7="";
		mvarJobDate8="";
		mvarJobDate9="";
		mvarRecallReason="";
		mvarEngineers="";
		mvarSkills="";
		mvarClientNotes="";
		mvarClientDate0="";
		mvarClientDate1="";
		mvarClientDate2="";
		mvarClientDate3="";
		mvarClientDate4="";
		mvarClientDate5="";
		mvarClientFields0="";
		mvarClientFields1="";
		mvarClientFields2="";
		mvarClientFields3="";
		mvarClientFields4="";
		mvarClientFields5="";
		mvarClientFields6="";
		mvarClientFields7="";
		mvarClientFields8="";
		mvarClientFields9="";
		mvarClientFields10="";
		mvarClientFields11="";
		mvarJobFinished= false;
		mvarEstimateNo="";
		mvarEngineerText="";
		mvarInvoiceNo="";
		mvarInvFileName="";
		mvarJobContacts="";
		mvarSMSHistory="";
		mvarAwaitingPayment=0;
		mvarNeedReport=0;
		mvarReportPrinted=0;
		mvarReportDate="";
		mvarSearchData="";
		mvarJobContactsCSV="";
		mvarDNumber="";
		mvarInvoiceNo2="";
		mvarDiscount=0;
		mvarNewJob=0;
		mvarJobCompleted = false;					
		mvarPendingUpload = false;                  
		mvarDateModified= "";                    
		mvarPaperWork = "";
		
		mvarEngFinished = false;
		mvarWorksCarriedOut = "";
		mvarCauseOfProblem = "";
		
		
    }
    
    public String getJobNo() {
    	return mvarJobNo;
    }
    public void setJobNo (String value) {
    	mvarJobNo = value;
    }
    
    public void setSLA(String value) {
    	mvarSLA = value;
    }
    
    public String getSLA() {
    	return mvarSLA;
    }
    
    public String getJobStatus() {
    	return mvarJobStatus;
    }
    public void setJobStatus (String value) {
    	mvarJobStatus = value;
    }
    
    public String getPickList1() {
    	return mvarPickList1;
    }
    public void setPickList1 (String value) {
    	mvarPickList1 = value;
    }
    
    public String getPickList2() {
    	return mvarPickList2;
    }
    public void setPickList2 (String value) {
    	mvarPickList2 = value;
    }
    public String getPickList3() {
    	return mvarPickList3;
    }
    public void setPickList3 (String value) {
    	mvarPickList3 = value;
    }
    public String getPickList4() {
    	return mvarPickList4;
    }
    public void setPickList4 (String value) {
    	mvarPickList4 = value;
    }

    
    public void setPickList5 (String value) {
    	mvarPickList5 = value;
    }
    public String getPickList5() {
    	return mvarPickList5;
    }


    public String getJobText1() {
    	return mvarJobText1;
    }
    public void setJobText1 (String value) {
    	mvarJobText1 = value;
    }

    public String getJobText2() {
    	return mvarJobText2;
    }
    public void setJobText2 (String value) {
    	mvarJobText2 = value;
    }
    public String getJobText3() {
    	return mvarJobText3;
    }
    public void setJobText3 (String value) {
    	mvarJobText3 = value;
    }
    public String getJobText4() {
    	return mvarJobText4;
    }
    public void setJobText4 (String value) {
    	mvarJobText4 = value;
    }
    public String getJobText5() {
    	return mvarJobText5;
    }
    public void setJobText5 (String value) {
    	mvarJobText5 = value;
    }
    public String getJobText6() {
    	return mvarJobText6;
    }
    public void setJobText6 (String value) {
    	mvarJobText6 = value;
    }
    public String getJobText7() {
    	return mvarJobText7;
    }
    public void setJobText7 (String value) {
    	mvarJobText7 = value;
    }
    public String getJobText8() {
    	return mvarJobText8;
    }
    public void setJobText8 (String value) {
    	mvarJobText8 = value;
    }
    public String getJobText9() {
    	return mvarJobText9;
    }
    public void setJobText9 (String value) {
    	mvarJobText9 = value;
    }
    public String getJobText10() {
    	return mvarJobText10;
    }
    public void setJobText10 (String value) {
    	mvarJobText10 = value;
    }
    public String getJobText11() {
    	return mvarJobText11;
    }
    public void setJobText11 (String value) {
    	mvarJobText11 = value;
    }
    public String getJobText12() {
    	return mvarJobText12;
    }
    public void setJobText12 (String value) {
    	mvarJobText12 = value;
    }
    public String getJobText13() {
    	return mvarJobText13;
    }
    public void setJobText13 (String value) {
    	mvarJobText13 = value;
    }
    

    public String getJobBrief() {
    	return mvarJobBrief;
    }
    public void setJobBrief (String value) {
    	mvarJobBrief = value;
    }
    

    
    public String getJobDate1() {
    	return mvarJobDate1;
    }
    public void setJobDate1 (String value) {
    	mvarJobDate1 = value;
    }
    
    public String getJobDate2() {
    	return mvarJobDate2;
    }
    public void setJobDate2 (String value) {
    	mvarJobDate2 = value;
    }
    public String getJobDate3() {
    	return mvarJobDate3;
    }
    public void setJobDate3 (String value) {
    	mvarJobDate3 = value;
    }
    public String getJobDate4() {
    	
    	return mvarJobDate4;
    }
    public void setJobDate4 (String value) {
    	mvarJobDate4 = value;
    }
    public String getJobDate5() {
    	return mvarJobDate5;
    }
    public void setJobDate5 (String value) {
    	mvarJobDate5 = value;
    }
    public String getJobDate6() {
    	return mvarJobDate6;
    }
    public void setJobDate6 (String value) {
    	mvarJobDate6 = value;
    }
    public String getJobDate7() {
    	return mvarJobDate7;
    }
    public void setJobDate7 (String value) {
    	mvarJobDate7 = value;
    }
    public String getJobDate8() {
    	return mvarJobDate8;
    }
    public void setJobDate8 (String value) {
    	mvarJobDate8 = value;
    }
    public String getJobDate9() {
    	return mvarJobDate9;
    }
    public void setJobDate9 (String value) {
    	mvarJobDate9 = value;
    }
    

    public String getRecallReason() {
    	return mvarRecallReason;
    }
    public void setRecallReason (String value) {
    	mvarRecallReason = value;
    }

    public C_JobContact getSiteAddress() {
    	return mvarSiteAddress;
    }
    public void setSiteAddres (C_JobContact value) {
    	mvarSiteAddress = value;
    }

    public C_JobContact getClientAddress() {
    	return mvarClientAddress;
    }
    public void setClientAddres (C_JobContact value) {
    	mvarClientAddress = value;
    }
    
    
    public String getEngineers() {
    	return mvarEngineers;
    }
    public void setEngineers (String value) {
    	mvarEngineers = value;
    }
    
    public String getSkills() {
    	return mvarSkills;
    }
    public void setSkills (String value) {
    	mvarSkills = value;
    }
    
    public String getClientNotes() {
    	return mvarClientNotes;
    }
    public void setClientNotes (String value) {
    	mvarClientNotes = value;
    }
    
    public String getClientDate0() {
    	return mvarClientDate0;
    }
    public void setClientDate0 (String value) {
    	mvarClientDate0 = value;
    }
    
    public String getClientDate1() {
    	return mvarClientDate1;
    }
    public void setClientDate1 (String value) {
    	mvarClientDate1 = value;
    }
    public String getClientDate2() {
    	return mvarClientDate2;
    }
    public void setClientDate2 (String value) {
    	mvarClientDate2 = value;
    }
    public String getClientDate3() {
    	return mvarClientDate3;
    }
    public void setClientDate3 (String value) {
    	mvarClientDate3 = value;
    }
    public String getClientDate4() {
    	return mvarClientDate4;
    }
    public void setClientDate4 (String value) {
    	mvarClientDate4 = value;
    }
    public String getClientDate5() {
    	return mvarClientDate5;
    }
    public void setClientDate5 (String value) {
    	mvarClientDate5 = value;
    }


    
    public String getClientFields0() {
    	return mvarClientFields0;
    }
    public void setClientFields0 (String value) {
    	mvarClientFields0 = value;
    }
    public String getClientFields1() {
    	return mvarClientFields1;
    }
    public void setClientFields1 (String value) {
    	mvarClientFields1 = value;
    }
    public String getClientFields2() {
    	return mvarClientFields2;
    }
    public void setClientFields2 (String value) {
    	mvarClientFields2 = value;
    }
    public String getClientFields3() {
    	return mvarClientFields3;
    }
    public void setClientFields3 (String value) {
    	mvarClientFields3 = value;
    }
    public String getClientFields4() {
    	return mvarClientFields4;
    }
    public void setClientFields4 (String value) {
    	mvarClientFields4 = value;
    }
    public String getClientFields5() {
    	return mvarClientFields5;
    }
    public void setClientFields5 (String value) {
    	mvarClientFields5 = value;
    }
    public String getClientFields6() {
    	return mvarClientFields6;
    }
    public void setClientFields6 (String value) {
    	mvarClientFields6 = value;
    }
    public String getClientFields7() {
    	return mvarClientFields7;
    }
    public void setClientFields7 (String value) {
    	mvarClientFields7 = value;
    }
    public String getClientFields8() {
    	return mvarClientFields8;
    }
    public void setClientFields8 (String value) {
    	mvarClientFields8 = value;
    }
    public String getClientFields9() {
    	return mvarClientFields9;
    }
    public void setClientFields9 (String value) {
    	mvarClientFields9 = value;
    }
    public String getClientFields10() {
    	return mvarClientFields10;
    }
    public void setClientFields10 (String value) {
    	mvarClientFields10 = value;
    }
    
    public String getClientFields11() {
    	return mvarClientFields11;
    }
    public void setClientFields11 (String value) {
    	mvarClientFields11 = value;
    }

    public Boolean getJobFinished() {
    	return mvarJobFinished;
    }
    public void setJobFinished(Boolean value) {
    	mvarJobFinished= value;
    }
    
    public String getEstimateNo() {
    	return mvarEstimateNo;
    }
    public void setEstimateNo (String value) {
    	mvarEstimateNo = value;
    }
    
    public String getEngineerText() {
    	return mvarEngineerText;
    }
    public void setEngineerText (String value) {
    	mvarEngineerText = value;
    }
    public String getInvoiceNo() {
    	return mvarInvoiceNo;
    }
    public void setInvoiceNo (String value) {
    	mvarInvoiceNo = value;
    }

    public String getInvFileName() {
    	return mvarInvFileName;
    }
    public void setInvFileName (String value) {
    	mvarInvFileName = value;
    }

    public String getJobContacts() {
    	return mvarJobContacts;
    }
    public void setJobContacts (String value) {
    	mvarJobContacts = value;
    }

    public String getSMSHistory() {
    	return mvarSMSHistory;
    }
    public void setSMSHistory (String value) {
    	mvarSMSHistory = value;
    }

    public Integer getAwaitingPayment() {
    	return mvarAwaitingPayment;
    }
    public void setAwaitingPayment (Integer value) {
    	mvarAwaitingPayment = value;
    }
    
    public Integer getNeedReport() {
    	return mvarNeedReport;
    }
    public void setNeedReport (Integer value) {
    	mvarNeedReport = value;
    }


    public Integer getReportPrinted() {
    	return mvarReportPrinted;
    }
    public void setReportPrinted (Integer value) {
    	mvarReportPrinted= value;
    }
    
    public String getReportDate() {
    	return mvarReportDate;
    }
    public void setReportDate (String value) {
    	mvarReportDate= value;
    }
    
    public String getSearchData() {
    	return mvarSearchData;
    }
    public void setSearchData (String value) {
    	mvarSearchData = value;
    }
    
    public String getJobContactsCSV() {
    	return mvarJobContactsCSV;
    }
    public void setJobContactsCSV (String value) {
    	mvarJobContactsCSV = value;
    }
    
    public String getDNumber() {
    	return mvarDNumber;
    }
    public void setDNumber (String value) {
    	mvarDNumber  = value;
    }
    
    public String getInvoiceNo2() {
    	return mvarInvoiceNo2;
    }
    public void setInvoiceNo2 (String value) {
    	mvarInvoiceNo2 = value;
    }
    
    public Integer getDiscount() {
    	return mvarDiscount;
    }
    public void setDiscount (Integer value) {
    	mvarDiscount = value;
    }
    
    public Integer getNewJob() {
    	return mvarNewJob;
    }
    public void setNewJob (Integer value) {
    	mvarNewJob = value;
    }

    public ArrayList<C_JobTimes> getJobTimes() {
    	return mvarJobTimes;
    }
   

    
    public C_Signature getSignature() {
    	return mvarSignature;
    }
    public void setSignature (C_Signature value) {
    	mvarSignature = value;
    }
    
    public C_Signature getSketch() {
    	return mvarSketch;
    }
    public void setSketch(C_Signature value) {
    	mvarSketch = value;
    }

    public Boolean getJobCompleted() {
    	return mvarJobCompleted;
    }
    public void setJobCompleted (Boolean value) {
    	mvarJobCompleted = value;
    }
    
    public Boolean getEngFinished() {
    	return mvarEngFinished;
    }
    
    public void setEngFinished(Boolean value) {
    	mvarEngFinished = value;
    }
    
    public String getWorksCarriedOut() {
    	return mvarWorksCarriedOut;
    }
    
    public void setWorksCarriedOut(String value) {
    	mvarWorksCarriedOut = value;
    }
    
    
    public String getCauseOfProblem() {
    	return mvarCauseOfProblem;
    }
    
    public  void setCauseOfProblem(String value) {
    	mvarCauseOfProblem = value;
    }
    
    
    public boolean getPendingUpload() {
    	return mvarPendingUpload;
    }
    public void setPendingUpload (boolean value) {
    	mvarPendingUpload = value;
    }
    
    public String getDateModified() {
    	return mvarDateModified;
    }
    public void setDateModified (String value) {
    	mvarDateModified= value;
    }
    
    public String getPaperWork() {
    	return mvarPaperWork;
    }
    public void setPaperWork (String value) {
    	mvarPaperWork= value;
    }
    
    public ArrayList<C_PartsRequired> getPartsRequired() {
    	return mvarPartsRequired;
    }
    
    
    public ArrayList<C_PaymentReceipt> getPaymentReceipt() {
    	return mvarPaymentReceipt;
    }

    public ArrayList<C_PartsUsed> getPartsUsed() {
    	return mvarPartsUsed;
    }
    
    public ArrayList<C_FireHydrant> getFireHydrant() {
    	return mvarFireHydrant;
    }
    

    public ArrayList<C_RiserService> getRiserServices () {
    	return mvarRiserService;
    }
    
    public ArrayList<C_RiserLocation> getRiserLocations () {
    	return mvarRiserLocation;
    }
    
    public int getRiserCount() {
    	return mvarRiserLocation.size();
    }
    

    public String IdentifyJob() {
    	String Title;
    	Title = mvarSiteAddress.getName() + "," + mvarSiteAddress.FirstLineOfAddress();
    	return Title;
    	
    	
    }
    
    public void PopulateJob(String XML,Boolean FromCache)
    {
    	//Parser objParser;
		//objParser = new Parser();
		
		try {

			clear();
	    	mvarJobNo = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBNO");
	        mvarJobBrief = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBBRIEF");
	
	        mvarPickList1 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBPICK1");
	        mvarPickList2 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBPICK2");
	        mvarPickList3 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBPICK3");
	        mvarPickList4 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBPICK4");
	        mvarPickList5 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBPICK5");
	
	        mvarJobText1 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDETAILS1");
	        mvarJobText2 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDETAILS2");
	        mvarJobText3 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDETAILS3");
	        mvarJobText4 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDETAILS4");
	        mvarJobText5 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDETAILS5");
	        mvarJobText6 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDETAILS6");
	        mvarJobText7 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDETAILS7");
	        mvarJobText8 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDETAILS8");
	        mvarJobText9 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDETAILS9");
	        mvarJobText10 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDETAILS10");
	        mvarJobText11 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDETAILS11");
	        mvarJobText12 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDETAILS12");
	        mvarJobText13 = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDETAILS13");
	        
	        
	        mvarJobDate1 = Long.toString(JTApplication.getInstance().GetDatabaseManager().ToEpochFromString(JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDATE1")));
	        mvarJobDate2 = Long.toString(JTApplication.getInstance().GetDatabaseManager().ToEpochFromString(JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDATE2")));
	        mvarJobDate3 = Long.toString(JTApplication.getInstance().GetDatabaseManager().ToEpochFromString(JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDATE3")));
	        mvarJobDate4 = Long.toString(JTApplication.getInstance().GetDatabaseManager().ToEpochFromString(JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDATE4")));
	        mvarJobDate5 = Long.toString(JTApplication.getInstance().GetDatabaseManager().ToEpochFromString(JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDATE5")));
	        mvarJobDate6 = Long.toString(JTApplication.getInstance().GetDatabaseManager().ToEpochFromString(JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDATE6")));
	        mvarJobDate7 = Long.toString(JTApplication.getInstance().GetDatabaseManager().ToEpochFromString(JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDATE7")));
	        mvarJobDate8 = Long.toString(JTApplication.getInstance().GetDatabaseManager().ToEpochFromString(JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDATE8")));
	        mvarJobDate9 = Long.toString(JTApplication.getInstance().GetDatabaseManager().ToEpochFromString(JTApplication.getInstance().GetParser().ExtractTagValue(XML, "JOBDATE9")));
	        
	        mvarPaperWork = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "PAPERWORK");
	        
	        mvarSignature.setSurname(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"SIGPRINT"));
	        mvarSignature.setDateSigned(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"SIGDATE"));
	        mvarSignature.setSignature(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"SIG"));
	        
	        mvarSketch.setSignature(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"SKETCH"));

	        // These are in try .. Catch to make it backward compatible with earlier Server versions.
	        
	        try {
	        		String sCompleted = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "COMPLETED");
	        		if (sCompleted.equals("True"))
	        				mvarJobCompleted = true;
	        		else
	        			mvarJobCompleted = false;
	        		
	        } catch (NumberFormatException nfe) {
	        	mvarJobCompleted = false;
	        }
	        
	        
	        try {
        		String sCompleted = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "ENGFINISHED");
        		if (sCompleted.equals("True"))
        				mvarEngFinished  = true;
        		else
        			mvarEngFinished = false;
        		
	        } catch (NumberFormatException nfe) {
	        	mvarEngFinished = false;
	        }
	        
	        try {
	        	mvarWorksCarriedOut = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "WORKSCARRIEDOUT");
	        } catch (Exception ex ) {
	        	mvarWorksCarriedOut = "";
	        }
	        
	        try {
	        	mvarCauseOfProblem  = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "CAUSEOFPROBLEM");
	        } catch (Exception ex ) {
	        	mvarCauseOfProblem = "";
	        }

	        
	        
	
	        mvarRecallReason = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "RECALLREASON");
	        mvarEngineers = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "ENGINEERS");
	        mvarSkills = JTApplication.getInstance().GetParser().ExtractTagValue(XML, "SKILLS");
	    	
	        try {
	        	mvarSiteAddress.setClientID(Long.parseLong(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CUSTCONTACTID")));
	        } catch(Exception ex) {
	        	
	        }
	        mvarSiteAddress.setName(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CUSTCONTACT0"));
	        mvarSiteAddress.setAddress(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CUSTCONTACT1"));
	        mvarSiteAddress.setPostCode(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CUSTCONTACT11"));
	        mvarSiteAddress.setContacts(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CUSTCONTACT2"));
	        mvarSiteAddress.setTelephone(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CUSTCONTACT3"));
	        mvarSiteAddress.setMobile(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CUSTCONTACT4"));
	        mvarSiteAddress.setFax(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CUSTCONTACT5"));
	        mvarSiteAddress.setEmail(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CUSTCONTACT6"));
	        mvarSiteAddress.setWWW(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CUSTCONTACT7"));
	        
	        try {
	        	mvarClientAddress.setClientID(Long.parseLong(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CLCONTACTID")));
	        } catch(Exception ex) {
	        	
	        }
	        mvarClientAddress.setName(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CLCONTACT0"));
	        mvarClientAddress.setAddress(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CLCONTACT1"));
	        mvarClientAddress.setPostCode(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CLCONTACT11"));
	        mvarClientAddress.setContacts(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CLCONTACT2"));
	        mvarClientAddress.setTelephone(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CLCONTACT3"));
	        mvarClientAddress.setMobile(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CLCONTACT4"));
	        mvarClientAddress.setFax(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CLCONTACT5"));
	        mvarClientAddress.setEmail(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CLCONTACT6"));
	        
	        mvarClientAddress.getPeople().setName(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CLIENTCONTACT_NAME"));
	        mvarClientAddress.getPeople().setTel(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CLIENTCONTACT_TEL"));
	        mvarClientAddress.getPeople().setEmail(JTApplication.getInstance().GetParser().ExtractTagValue(XML,"CLIENTCONTACT_EMAIL"));
	        
	        Document doc = null;
	        Element rootElement = null;
	        NodeList myNodeList = null;
	        Integer noOfChildren = 0;
	        Element tmpE1;
	        
	        
	        try {
		        doc = JTApplication.getInstance().GetParser().XMLfromString(XML);
		        rootElement = doc.getDocumentElement();
		        myNodeList = doc.getElementsByTagName("TIMECARD");
		        
		        for (int i = 0; i < myNodeList.getLength(); i++) {
			        C_JobTimes timecard = new C_JobTimes();
		        	
		        	tmpE1 = (Element) myNodeList.item(i);
		        	NodeList tmpList;
		        	Element myNode;
		        	
		        	tmpList = tmpE1.getElementsByTagName("TC_DATE");
		        	myNode= (Element) tmpList.item(0);
		        	timecard.setEventDate(JTApplication.getInstance().GetDatabaseManager().ToEpochStringFromString(parseValue(myNode)));
		        	
		        	tmpList = tmpE1.getElementsByTagName("TC_STARTTIME");
		        	myNode= (Element) tmpList.item(0);
		        	timecard.setStartTime(JTApplication.getInstance().GetDatabaseManager().ToEpochStringFrom24HrTimeString(parseValue(myNode)));
 		        	
		        	tmpList = tmpE1.getElementsByTagName("TC_ENDTIME");
		        	myNode= (Element) tmpList.item(0);
		        	timecard.setEndTime(JTApplication.getInstance().GetDatabaseManager().ToEpochStringFrom24HrTimeString(parseValue(myNode)));
		        	
		        	tmpList = tmpE1.getElementsByTagName("TC_RECORDID");
		        	myNode= (Element) tmpList.item(0);
		        	timecard.setRecordID(Long.parseLong(parseValue(myNode)));
		        	
		        	tmpList = tmpE1.getElementsByTagName("TC_ENGINEER");
		        	myNode= (Element) tmpList.item(0);
		        	timecard.setEngineerID(Long.parseLong(parseValue(myNode)));
		        	
		        	tmpList = tmpE1.getElementsByTagName("TC_JOBNO");
		        	myNode= (Element) tmpList.item(0);
		        	timecard.setJobNo(parseValue(myNode));
		        	
		        	tmpList = tmpE1.getElementsByTagName("TC_TYPE");
		        	myNode= (Element) tmpList.item(0);
		        	timecard.setTimeType(Integer.parseInt(parseValue(myNode)));
		        	
		        	tmpList = tmpE1.getElementsByTagName("TC_ENGNAME");
		        	myNode= (Element) tmpList.item(0);
		        	timecard.setEngName(parseValue(myNode));
		        	
		        	tmpList = tmpE1.getElementsByTagName("TC_VALUE");
		        	myNode= (Element) tmpList.item(0);
		        	timecard.setEntryValue(Double.parseDouble(parseValue(myNode)));

		        	mvarJobTimes.add(timecard);
		        }
	        
		} catch (Exception e) {
			String ErrStr = e.getMessage();
			ErrStr += "";
		}
		
		
		// <A_PARTS_REQ> <A_PARTS>          <-- Android specific XML  (Windows is <PARTS_REQ> and <PARTS>
        try {
	        doc = JTApplication.getInstance().GetParser().XMLfromString(XML);
	        rootElement = doc.getDocumentElement();
	        myNodeList = doc.getElementsByTagName("A_PARTS");
	        
	        for (int i = 0; i < myNodeList.getLength(); i++) {
		        C_PartsRequired partrequired = new C_PartsRequired();
	        	
	        	tmpE1 = (Element) myNodeList.item(i);
	        	NodeList tmpList;
	        	Element myNode;
	        	
	        	tmpList = tmpE1.getElementsByTagName("PR_QTY");
	        	myNode= (Element) tmpList.item(0);
	        	partrequired.setQty(Double.parseDouble(parseValue(myNode)));
	        	
	        	tmpList = tmpE1.getElementsByTagName("PR_DESC");
	        	myNode= (Element) tmpList.item(0);
	        	partrequired.setDescription(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("PR_INTPTNO");
	        	myNode= (Element) tmpList.item(0);
	        	partrequired.setOurPartNo(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("PR_SUPPLIER");
	        	myNode= (Element) tmpList.item(0);
	        	partrequired.setSupplierName(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("PR_SUPPPTNO");
	        	myNode= (Element) tmpList.item(0);
	        	partrequired.setSupplierPartNo(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("PR_ENG");
	        	myNode= (Element) tmpList.item(0);
	        	partrequired.setEngineer(parseValue(myNode));
	        	

	        	mvarPartsRequired.add(partrequired);
	        }
        
	    } catch(Exception e) {
	    	Log.i("XML","Exception: " + e.getMessage());	        	
	    }
	    
	    
	    // <PARTS_USED>
        try {
	        doc = JTApplication.getInstance().GetParser().XMLfromString(XML);
	        rootElement = doc.getDocumentElement();
	        myNodeList = doc.getElementsByTagName("A_PARTSU");
	        
	        for (int i = 0; i < myNodeList.getLength(); i++) {
		        C_PartsUsed partused = new C_PartsUsed();
	        	
	        	tmpE1 = (Element) myNodeList.item(i);
	        	NodeList tmpList;
	        	Element myNode;
	        	
	        	tmpList = tmpE1.getElementsByTagName("PU_PARTNO");
	        	myNode= (Element) tmpList.item(0);
	        	partused.setPartNo(parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("PU_QTY");
	        	myNode= (Element) tmpList.item(0);
	        	partused.setQty(Double.parseDouble(parseValue(myNode)));
	        	
	        	tmpList = tmpE1.getElementsByTagName("PU_DESC");
	        	myNode= (Element) tmpList.item(0);
	        	partused.setDescription(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("PU_UPRICE");
	        	myNode= (Element) tmpList.item(0);
	        	partused.setUnitPrice(Double.parseDouble(parseValue(myNode)));

	        	mvarPartsUsed.add(partused);
	        }
        
	    } catch(Exception e) {
	    	Log.i("XML","Exception: " + e.getMessage());	        	
	    }
	    
	    
	    // Payments Received
        try {
	        doc = JTApplication.getInstance().GetParser().XMLfromString(XML);
	        rootElement = doc.getDocumentElement();
	        myNodeList = doc.getElementsByTagName("RECEIPT");
	        
	        for (int i = 0; i < myNodeList.getLength(); i++) {
		        C_PaymentReceipt paymentreceipt = new C_PaymentReceipt();
	        	
	        	tmpE1 = (Element) myNodeList.item(i);
	        	NodeList tmpList;
	        	Element myNode;
	        	
	        	tmpList = tmpE1.getElementsByTagName("RE_AMOUNT");
	        	myNode= (Element) tmpList.item(0);
	        	paymentreceipt.setAmount(Double.parseDouble(parseValue(myNode)));

	        	tmpList = tmpE1.getElementsByTagName("RE_PAYTYPE");
	        	myNode= (Element) tmpList.item(0);
	        	paymentreceipt.setPayType(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RE_RECNO");
	        	myNode= (Element) tmpList.item(0);
	        	paymentreceipt.setReceiptNo(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RE_DATETAKEN");
	        	myNode= (Element) tmpList.item(0);
	        	paymentreceipt.setDateTaken(JTApplication.getInstance().GetDatabaseManager().ToEpochStringFromString(parseValue(myNode)));

	        	tmpList = tmpE1.getElementsByTagName("RE_ENGNAME");
	        	myNode= (Element) tmpList.item(0);
	        	paymentreceipt.setEngineerName(parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RE_DATEINOFF");
	        	myNode= (Element) tmpList.item(0);
	        	paymentreceipt.setDateInOffice(JTApplication.getInstance().GetDatabaseManager().ToEpochStringFromString(parseValue(myNode)));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RE_RECBY");
	        	myNode= (Element) tmpList.item(0);
	        	paymentreceipt.setReceivedBy(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RE_DESC");
	        	myNode= (Element) tmpList.item(0);
	        	paymentreceipt.setDescription(parseValue(myNode));

	        	mvarPaymentReceipt.add(paymentreceipt);
	        }
	        
	        
	        
        
	    } catch(Exception e) {
	    	Log.i("XML","Exception: " + e.getMessage());	        	
	    }
	    
	    
	    // <FIREHYDRANTS>
        try {
	        doc = JTApplication.getInstance().GetParser().XMLfromString(XML);
	        rootElement = doc.getDocumentElement();
	        myNodeList = doc.getElementsByTagName("HYDRANT");
	        
	        for (int i = 0; i < myNodeList.getLength(); i++) {
		        C_FireHydrant firehydrant = new C_FireHydrant();
	        	
	        	tmpE1 = (Element) myNodeList.item(i);
	        	NodeList tmpList;
	        	Element myNode;
	        	
	        	tmpList = tmpE1.getElementsByTagName("FH_RECORDID");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setJTRecordID(Long.parseLong(parseValue(myNode)));

	        	tmpList = tmpE1.getElementsByTagName("FH_ADD");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setSiteAddress(parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("FH_PC");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setSitePostCode(parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("FH_TD");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setTestDate(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("FH_ENG");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setEngineer(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("FH_HL");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setHydrantLocation(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("FH_REF");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setNumber(Integer.parseInt(parseValue(myNode)));
	        	
	        	tmpList = tmpE1.getElementsByTagName("FH_D1");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setDetails(parseValue(myNode),0);
	        	
	        	tmpList = tmpE1.getElementsByTagName("FH_D2");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setDetails(parseValue(myNode),1);

	        	tmpList = tmpE1.getElementsByTagName("FH_D3");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setDetails(parseValue(myNode),2);

	        	tmpList = tmpE1.getElementsByTagName("FH_D4");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setDetails(parseValue(myNode),3);

	        	tmpList = tmpE1.getElementsByTagName("FH_D5");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setDetails(parseValue(myNode),4);

	        	tmpList = tmpE1.getElementsByTagName("FH_D6");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setDetails(parseValue(myNode),5);

	        	tmpList = tmpE1.getElementsByTagName("FH_D7");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setDetails(parseValue(myNode),6);

	        	tmpList = tmpE1.getElementsByTagName("FH_D8");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setDetails(parseValue(myNode),7);

	        	tmpList = tmpE1.getElementsByTagName("FH_D9");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setDetails(parseValue(myNode),8);

	        	tmpList = tmpE1.getElementsByTagName("FH_D10");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setDetails(parseValue(myNode),9);

	        	tmpList = tmpE1.getElementsByTagName("FH_D11");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setDetails(parseValue(myNode),10);

	        	tmpList = tmpE1.getElementsByTagName("FH_D12");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setDetails(parseValue(myNode),11);

	        	tmpList = tmpE1.getElementsByTagName("FH_JN");
	        	myNode= (Element) tmpList.item(0);
	        	firehydrant.setJobNo(parseValue(myNode));


	        	mvarFireHydrant.add(firehydrant);
	        }
        
	    } catch(Exception e) {
	    	Log.i("XML C_JTJob FIREHYDRANTS","Exception: " + e.getMessage());	        	
	    }
	    
	    
	    
	    // <RISERLOCATIONS>
        try {
	        doc = JTApplication.getInstance().GetParser().XMLfromString(XML);
	        rootElement = doc.getDocumentElement();
	        myNodeList = doc.getElementsByTagName("RISERLOCATION");
	        
	        for (int i = 0; i < myNodeList.getLength(); i++) {
		        C_RiserLocation riserlocation = new C_RiserLocation();
	        	
	        	tmpE1 = (Element) myNodeList.item(i);
	        	NodeList tmpList;
	        	Element myNode;
	        	
	        	tmpList = tmpE1.getElementsByTagName("RL_JTRECORDID");
	        	myNode= (Element) tmpList.item(0);
	        	riserlocation.setJTRecordID(Long.parseLong(parseValue(myNode)));

	        	tmpList = tmpE1.getElementsByTagName("RL_NUMBER");
	        	myNode= (Element) tmpList.item(0);
	        	riserlocation.setNumber(Integer.parseInt(parseValue(myNode)));

	        	tmpList = tmpE1.getElementsByTagName("RL_LOCATION");
	        	myNode= (Element) tmpList.item(0);
	        	riserlocation.setLocation(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RL_OUTLETQTY");
	        	myNode= (Element) tmpList.item(0);
	        	riserlocation.setOutletQty(parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RL_OUTLETKEY");
	        	myNode= (Element) tmpList.item(0);
	        	riserlocation.setOutletKey(parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RL_INLETKEY");
	        	myNode= (Element) tmpList.item(0);
	        	riserlocation.setInletKey(parseValue(myNode));
	        	
	        	
	        	tmpList = tmpE1.getElementsByTagName("RL_CLIENTID");
	        	myNode= (Element) tmpList.item(0);
	        	riserlocation.setClientId(Long.parseLong(parseValue(myNode)));
	        	
	        	mvarRiserLocation.add(riserlocation);
	        }
        
	    } catch(Exception e) {
	    	Log.i("XML C_JTJob RiserLocation","Exception: " + e.getMessage());	        	
	    }
	    
	    
	    // *********************************
	    // ******** <RISERSERVICES> ********
	    // *********************************
        try {
	        doc = JTApplication.getInstance().GetParser().XMLfromString(XML);
	        rootElement = doc.getDocumentElement();
	        myNodeList = doc.getElementsByTagName("RSERVICE");
	        
	        for (int i = 0; i < myNodeList.getLength(); i++) {
		        C_RiserService riserservice= new C_RiserService();
	        	
	        	tmpE1 = (Element) myNodeList.item(i);
	        	NodeList tmpList;
	        	Element myNode;
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_JTRECORDID");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setJTRecordID(Long.parseLong(parseValue(myNode)));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_NO");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setRiserNumber(Integer.parseInt(parseValue(myNode)));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_LOC");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setRiserLocation(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_JOBNO");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setJobNo(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_INLETKEY");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setInLetKey(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_OUTLETKEY");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setOutLetKey(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_EESC1");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtServiceCheck(0, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_EESC2");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtServiceCheck(1, parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RS_EESC3");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtServiceCheck(2, parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RS_EESC4");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtServiceCheck(3, parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RS_EESC5");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtServiceCheck(4, parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RS_EESC6");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtServiceCheck(5, parseValue(myNode));

	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_EED1");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtDetails(0, parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RS_EED2");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtDetails(1, parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RS_EED3");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtDetails(2, parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RS_EED4");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtDetails(3, parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RS_EED5");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtDetails(4, parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RS_EED6");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtDetails(5, parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RS_EES1");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtStatus(0, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_EES2");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtStatus(1, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_EES3");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtStatus(2, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_EES4");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtStatus(3, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_EES5");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtStatus(4, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_EES6");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setExtStatus(5, parseValue(myNode));
	        	

	        	// This is the Riser Outlet Valve (quantity)
	        	tmpList = tmpE1.getElementsByTagName("RS_IESC1");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setIntServiceCheck(0, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_IESC2");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setIntServiceCheck(1, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_IESC3");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setIntServiceCheck(2, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_IESC4");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setIntServiceCheck(3, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_IESC5");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setIntServiceCheck(4, parseValue(myNode));
	        	
	        	// This is the Outlet Valve Details
	        	tmpList = tmpE1.getElementsByTagName("RS_IED1");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setIntDetails(0, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_IED2");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setIntDetails(1, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_IED3");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setIntDetails(2, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_IED4");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setIntDetails(3, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_IED5");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setIntDetails(4, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_AIR1");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setAir(0, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_AIR2");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setAir(1, parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RS_WATER1");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setWater(0, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_WATER2");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setWater(1, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_CC1");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setCompletionChecked(0, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_CC2");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setCompletionChecked(1, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_CC3");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setCompletionChecked(2, parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_COM");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setComments(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_REM");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setRemedialWorks(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_STATUS");
	        	myNode= (Element) tmpList.item(0);
	        	riserservice.setOverAllStatus(parseValue(myNode));
	        	
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_PUMPSIG");
	        	myNode= (Element) tmpList.item(0);
	        	if (myNode != null) riserservice.setPumpSignature(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_PUMPNAME");
	        	myNode= (Element) tmpList.item(0);
	        	if (myNode != null) riserservice.setPumpSurname(parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RS_PUMPDATE");
	        	myNode= (Element) tmpList.item(0);
	        	if (myNode != null) riserservice.setPumpDate(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_STACKSIG");
	        	myNode= (Element) tmpList.item(0);
	        	if (myNode != null) riserservice.setStackSignature(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_STACKNAME");
	        	myNode= (Element) tmpList.item(0);
	        	if (myNode != null) riserservice.setStackSurname(parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RS_STACKDATE");
	        	myNode= (Element) tmpList.item(0);
	        	if (myNode != null) riserservice.setStackDate(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_CUSTSIG");
	        	myNode= (Element) tmpList.item(0);
	        	if (myNode != null) riserservice.setCustSignature(parseValue(myNode));
	        	
	        	tmpList = tmpE1.getElementsByTagName("RS_CUSTNAME");
	        	myNode= (Element) tmpList.item(0);
	        	if (myNode != null) riserservice.setCustSurname(parseValue(myNode));

	        	tmpList = tmpE1.getElementsByTagName("RS_CUSTDATE");
	        	myNode= (Element) tmpList.item(0);
	        	if (myNode != null) riserservice.setCustDate(parseValue(myNode));
	        	
	        	
	        	mvarRiserService.add(riserservice);
	        }
        
	    } catch(Exception e) {
	    	Log.i("XML C_JTJob RiserLocation","Exception: " + e.getMessage());	        	
	    }
	    
	    
		
	    } catch(Exception e) {
	    	Log.i("XML","Exception: " + e.getMessage());	        	
	    }

	    // Save just loaded job to cache if not already in cache pending uploading.
	    // This ensures latest version of job is always used.
	    if (JTApplication.getInstance().GetDatabaseManager().isJobCachedPendingUpdate(mvarJobNo) == false) {
            JTApplication.getInstance().GetDatabaseManager().SaveJobToCache();
	    }
	}
		
		
		
    public static String parseValue (Node node) { 
	   StringBuffer text = new StringBuffer(); 
	   NodeList nodeChildren = node.getChildNodes(); 
	   String value; 
	   Node tmpNode; 
	   for (int i = 0; i < nodeChildren.getLength(); i++) { 
	      tmpNode = nodeChildren.item (i); 
	      value = tmpNode.getNodeValue(); 
	      if (value != null) { 
	         text.append (value); 
	      } 

	   } 

	   return text.toString(); 
    } 
    
 // Construct SQL required to add job time to cache database.
    public String SQLcmdAddJobTimes(C_JobTimes timesheet)
    {
    	String sqlQuery = "";
    	DatabaseHelper dbh = JTApplication.getInstance().GetDatabaseManager();
    	
    	try {
    		sqlQuery = "INSERT INTO JobTimes " +
    		"(EngineerID,EventDate,StartTime,EndTime,TimeType,EntryValue,JobNo) VALUES (" + 
    		timesheet.getEngineerID() + "," +
    		"'" + timesheet.getEventDate() + "'," +
            "'" + timesheet.getStartTime()  + "'," +
            "'" + timesheet.getEndTime() + "'," +
            timesheet.getTimeType() + "," +
            timesheet.getEntryValue() + "," +
            "'" + dbh.MakeSafe(mvarJobNo) + "')" ;   		
    		
    	} catch (Exception e) {
    		Log.i("SQLcmdAddJob, ExceptionL", e.getMessage());
    	}
    	
    	return sqlQuery;
    }
    
    
    public String SQLcmdAddPartsRequired(C_PartsRequired partsrequired)
    {
    	String sqlQuery = "";
    	DatabaseHelper dbh = JTApplication.getInstance().GetDatabaseManager();
    	
    	try {
	        // Create an SQL query to insert the parts required record.
    		sqlQuery = "INSERT INTO PartsRequired " +
	         "(JobNo,Qty,[Desc],KLSPtNo,SuppName,SuppPtNo,Engineer) VALUES (" +
	         "'" + mvarJobNo + "'," +
	         partsrequired.getQty() + "," +
	         "'" + dbh.MakeSafe(partsrequired.getDescription()) + "'," +
	         "'" + dbh.MakeSafe(partsrequired.getOurPartNo()) + "'," +
	         "'" + dbh.MakeSafe(partsrequired.getSupplierName()) + "'," +
	         "'" + dbh.MakeSafe(partsrequired.getSupplierPartNo()) + "'," +
	         "'" + dbh.MakeSafe(partsrequired.getEngineer()) + "')";
    	} catch (Exception e) {
    		Log.i("SQLcmdAddJob, ExceptionL", e.getMessage());
    	}
    	
    	return sqlQuery;
    	
    }
    
    // Construct SQL for parts used.
    public String SQLcmdAddPartsUsed(C_PartsUsed partsused)
    {
    	String sqlQuery = "";
    	DatabaseHelper dbh = JTApplication.getInstance().GetDatabaseManager();

    	try {
	        // Create an SQL query to insert the parts used record.
            sqlQuery = "INSERT INTO PartsUsed " +
             "(JobNo,PartNo,Qty,[Description],UnitPrice) VALUES (" +
             "'" + dbh.MakeSafe(partsused.getJobNo()) + "'," +
             "'" + dbh.MakeSafe(partsused.getPartNo()) + "'," +
             partsused.getQty() + "," +
             "'" + dbh.MakeSafe(partsused.getDescription()) + "'," +
             partsused.getUnitPrice() + ")";
    	} catch (Exception e) {
    		Log.i("SQLcmdAddJob, ExceptionL", e.getMessage());
    	}
    	
    	return sqlQuery;
    	
    }
    
    
    // Construct SQL for payment receipts used.
    public String SQLcmdAddPaymentReceipts(C_PaymentReceipt receipt)
    {
    	String sqlQuery = "";
    	DatabaseHelper dbh = JTApplication.getInstance().GetDatabaseManager();
    	
    	try {
	        // Create an SQL query to insert the payment receipt record.
            sqlQuery= "INSERT INTO PaymentReceipt " +
             "(JobNo,Amount,PayType,ReceiptNo,DateTaken,EngineerNotes,DateInOffice,ReceivedBy,Description) VALUES (" +
             "'" + receipt.getJobNo() + "'," +
             receipt.getAmount() + "," +
             "'" + dbh.MakeSafe(receipt.getPayType()) + "'," +
             "'" + dbh.MakeSafe(receipt.getReceiptNo()) + "'," +
             "'" + dbh.SQLDate(dbh.strToDate(receipt.getDateTaken())) + "'," +
             "'" + dbh.MakeSafe(receipt.getEngineerName()) + "'," +
             "'" + dbh.SQLDate(dbh.strToDate(receipt.getDateInOffice())) + "'," +
             "'" + receipt.getReceivedBy() + "'," +
             "'" + dbh.MakeSafe(receipt.getDescription()) + "')";

    	} catch (Exception e) {
    		Log.i("SQLcmdAddJob, ExceptionL", e.getMessage());
    	}
    	
    	return sqlQuery;
    	
    }
    
    public String SQLcmdAddFireHydrants(C_FireHydrant firehydrant)
    {
    	String sqlQuery = "";
    	DatabaseHelper dbh = JTApplication.getInstance().GetDatabaseManager();
    	
    	try {
	        // Create an SQL query to insert the firehydrant record.
            sqlQuery= "INSERT INTO FireHydrant " +
             "(JTRecordID,SiteAddress,SitePostCode,TestDate,Engineer,HydrantLocation,RefNumber," +
             "Details1,Details2,Details3,Details4,Details5,Details6,Details7,Details8,Details9,Details10,Details11,Details12,JobNo" +
             ") VALUES (" +
             + firehydrant.getJTRecordID() + "," +
             "'" + dbh.MakeSafe(firehydrant.getSiteAddress()) + "'," +
             "'" + dbh.MakeSafe(firehydrant.getSitePostCode()) + "'," +
             "'" + dbh.SQLDate(dbh.strToDate(firehydrant.getTestDate())) + "'," +
             "'" + dbh.MakeSafe(firehydrant.getEngineer()) + "'," +
             "'" + dbh.MakeSafe(firehydrant.getHydrantLocation()) + "'," +
             + firehydrant.getNumber() + "," +
             "'" + dbh.MakeSafe(firehydrant.getDetails(0)) + "'," +
             "'" + dbh.MakeSafe(firehydrant.getDetails(1)) +"'," +
             "'" + dbh.MakeSafe(firehydrant.getDetails(2)) +"'," +
             "'" + dbh.MakeSafe(firehydrant.getDetails(3)) +"'," +
             "'" + dbh.MakeSafe(firehydrant.getDetails(4)) +"'," +
             "'" + dbh.MakeSafe(firehydrant.getDetails(5)) +"'," +
             "'" + dbh.MakeSafe(firehydrant.getDetails(6)) +"'," +
             "'" + dbh.MakeSafe(firehydrant.getDetails(7)) +"'," +
             "'" + dbh.MakeSafe(firehydrant.getDetails(8)) +"'," +
             "'" + dbh.MakeSafe(firehydrant.getDetails(9)) +"'," +
             "'" + dbh.MakeSafe(firehydrant.getDetails(10)) +"'," +
             "'" + dbh.MakeSafe(firehydrant.getDetails(11)) +"'," +
             "'" + dbh.MakeSafe(firehydrant.getJobNo()) +"')"; 
    	} catch (Exception e) {
    		Log.i("SQLcmdAddJob, SQLcmdAddFireHydrants", e.getMessage());
    	}
    	return sqlQuery;
    }
    
    public String SQLcmdAddRiserService(C_RiserService riserservice)
    {
    	String sqlQuery = "";
    	DatabaseHelper dbh = JTApplication.getInstance().GetDatabaseManager();
    	
    	try {
	        // Create an SQL query to insert the RiserService record.
            sqlQuery= "INSERT INTO RiserService " +
             "(JTRecordID,SiteAddress,SitePostCode,RiserNumber,RiserLocation,JobNo," +
             "ExtServiceCheck1,ExtServiceCheck2,ExtServiceCheck3,ExtServiceCheck4,ExtServiceCheck5,ExtServiceCheck6," +
             "ExtDetails1,ExtDetails2,ExtDetails3,ExtDetails4,ExtDetails5,ExtDetails6," +
             "ExtStatus1,ExtStatus2,ExtStatus3,ExtStatus4,ExtStatus5,ExtStatus6," +
             "IntServiceCheck1,IntServiceCheck2,IntServiceCheck3,IntServiceCheck4,IntServiceCheck5," +
             "IntDetails1,IntDetails2,IntDetails3,IntDetails4,IntDetails5," +
             "Air1,Air2,Water1,Water2," +
             "CompletionChecked1,CompletionChecked2,CompletionChecked3," +
             "Comments,RemedialWorks,OverAllStatus,DatePrinted," +
             "Cust_Signature,Cust_SignatureName,Cust_SignatureDate," +
             "Pump_Signature,Pump_SignatureName,Pump_SignatureDate," +
             "Stack_Signature,Stack_SignatureName,Stack_SignatureDate," +
             "InletKey,OutletKey" +
             ") VALUES (" +
             riserservice.getJTRecordID() + "," +
             "''," +
             "''," +
             riserservice.getRiserNumber()+ "," +
             "'" + dbh.MakeSafe(riserservice.getRiserLocation()) + "'," +
             "'" + dbh.MakeSafe(riserservice.getJobNo()) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtServiceCheck(0)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtServiceCheck(1)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtServiceCheck(2)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtServiceCheck(3)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtServiceCheck(4)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtServiceCheck(5)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtDetails(0)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtDetails(1)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtDetails(2)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtDetails(3)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtDetails(4)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtDetails(5)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtStatus(0)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtStatus(1)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtStatus(2)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtStatus(3)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtStatus(4)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getExtStatus(5)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getIntServiceCheck(0)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getIntServiceCheck(1)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getIntServiceCheck(2)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getIntServiceCheck(3)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getIntServiceCheck(4)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getIntDetails(0)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getIntDetails(1)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getIntDetails(2)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getIntDetails(3)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getIntDetails(4)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getAir(0)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getAir(1)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getWater(0)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getWater(1)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getCompletionChecked(0)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getCompletionChecked(1)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getCompletionChecked(2)) + "'," +
             "'" + dbh.MakeSafe(riserservice.getComments()) + "'," +
             "'" + dbh.MakeSafe(riserservice.getRemedialWorks()) + "'," +
             "'" + dbh.MakeSafe(riserservice.getOverAllStatus()) + "'," +
             "'" + dbh.SQLDate(dbh.strToDate(riserservice.getPrintedDate())) + "'," +
             "'" + dbh.MakeSafe(riserservice.getCustSig().getSignature()) + "'," +
             "'" + dbh.MakeSafe(riserservice.getCustSig().getSurname()) + "'," +
             "'" + riserservice.getCustSig().getDateSigned().toString() + "'," +
             "'" + dbh.MakeSafe(riserservice.getEngSigPump().getSignature()) + "'," +
             "'" + dbh.MakeSafe(riserservice.getEngSigPump().getSurname()) + "'," +
             "'" + riserservice.getEngSigPump().getDateSigned().toString() + "'," +
             "'" + dbh.MakeSafe(riserservice.getEngSigStack().getSignature()) + "'," +
             "'" + dbh.MakeSafe(riserservice.getEngSigStack().getSurname()) + "'," +
             "'" + riserservice.getEngSigStack().getDateSigned().toString() + "'," +
             "'" + dbh.MakeSafe(riserservice.getInLetKey()) + "'," +
             "'" + dbh.MakeSafe(riserservice.getOutLetKey()) + "'" +
             
             ")" ;
            
    	} catch (Exception e) {
    		Log.i(TAG + "SQLcmdAddRiserService",  e.getMessage());
    	}
    	return sqlQuery;
    }
    
    
    
    
    
    
    // Construct SQL required to add job to database.
    public String SQLcmdAddJob()
    {
    	String sqlQuery = "";
    	DatabaseHelper dbh = JTApplication.getInstance().GetDatabaseManager();
    	
    	try 
    	{
    		Integer completed =  mvarJobCompleted.equals(true) ? 1 : 0;
    		Integer pendingUpload=  mvarPendingUpload.equals(true) ? 1 : 0;
    		Integer engFinished = mvarEngFinished.equals(true) ? 1 : 0;
    		
    		sqlQuery = "INSERT INTO jobs (Jobno,Site_name,Site_Address,Site_PCode,Site_Contacts,Site_Tel,Site_Mobile,Site_Fax,Site_Email,Site_Website," +
             "Comp_Name,Comp_Address,Comp_Contacts,Comp_Tel,Comp_Mobile,Comp_Fax,Comp_Email,Comp_Website," +
             "JobStatus,jobtext1,jobtext2,jobtext3,jobtext4,jobtext5,jobtext6,jobtext7,jobtext8,jobtext9,jobtext10,jobtext11,jobtext12,jobtext13," +
             "JobBrief,jobdate1,jobdate2,jobdate3,jobdate4,jobdate5,jobdate6,jobdate7,jobdate8,jobdate9,RecallReason,picklist1,picklist2,picklist3,picklist4,picklist5," +
             "Signature,Sig_Surname,Sig_Date,Sketch," +
             "JobFinished,PendingUpload,DateModified," +
             "ClientContact_Name,ClientContact_Tel,ClientContact_Email," +
             "PaperWork,EngFinished,WorksCarriedOut,CauseOfProblem" +
             ") VALUES (" +
             "'" + mvarJobNo + "'," +
             "'" +  dbh.MakeSafe(mvarSiteAddress.getName()) + "'," +
             "'" + dbh.MakeSafe(mvarSiteAddress.getAddress()) + "'," +
             "'" + dbh.MakeSafe(mvarSiteAddress.getPostCode()) + "'," +
             "'" + dbh.MakeSafe(mvarSiteAddress.getContacts()) + "'," +
             "'" + dbh.MakeSafe(mvarSiteAddress.getTelephone()) + "'," +
             "'" + dbh.MakeSafe(mvarSiteAddress.getMobile()) + "'," +
             "'" + dbh.MakeSafe(mvarSiteAddress.getFax()) + "'," +
             "'" + dbh.MakeSafe(mvarSiteAddress.getEmail()) + "'," +
             "'" + dbh.MakeSafe(mvarSiteAddress.getWWW()) + "'," +
             "'" + dbh.MakeSafe(mvarClientAddress.getName()) + "'," +
             "'" + dbh.MakeSafe(mvarClientAddress.getAddress()) + "'," +
             "'" + dbh.MakeSafe(mvarClientAddress.getContacts()) + "'," +
             "'" + dbh.MakeSafe(mvarClientAddress.getTelephone()) + "'," +
             "'" + dbh.MakeSafe(mvarClientAddress.getMobile()) + "'," +
             "'" + dbh.MakeSafe(mvarClientAddress.getFax()) + "'," +
             "'" + dbh.MakeSafe(mvarClientAddress.getEmail()) + "'," +
             "'" + dbh.MakeSafe(mvarClientAddress.getWWW()) + "'," +
             "'" + dbh.MakeSafe(mvarJobStatus) + "'," +
             "'" + dbh.MakeSafe(mvarJobText1) + "'," +
             "'" + dbh.MakeSafe(mvarJobText2) + "'," +
             "'" + dbh.MakeSafe(mvarJobText3) + "'," +
             "'" + dbh.MakeSafe(mvarJobText4) + "'," +
             "'" + dbh.MakeSafe(mvarJobText5) + "'," +
             "'" + dbh.MakeSafe(mvarJobText6) + "'," +
             "'" + dbh.MakeSafe(mvarJobText7) + "'," +
             "'" + dbh.MakeSafe(mvarJobText8) + "'," +
             "'" + dbh.MakeSafe(mvarJobText9) + "'," +
             "'" + dbh.MakeSafe(mvarJobText10) + "'," +
             "'" + dbh.MakeSafe(mvarJobText11) + "'," +
             "'" + dbh.MakeSafe(mvarJobText12) + "'," +
             "'" + dbh.MakeSafe(mvarJobText13) + "'," +
             "'" + dbh.MakeSafe(mvarJobBrief) + "'," +
             "'" + mvarJobDate1 + "'," +
             "'" + mvarJobDate2 + "'," +
             "'" + mvarJobDate3 + "'," +
             "'" + mvarJobDate4 + "'," +
             "'" + mvarJobDate5 + "'," +
             "'" + mvarJobDate6 + "'," +
             "'" + mvarJobDate7 + "'," +
             "'" + mvarJobDate8 + "'," +
             "'" + mvarJobDate9 + "'," +
             "'" + dbh.MakeSafe(mvarRecallReason) + "'," +
             "'" + dbh.MakeSafe(mvarPickList1) + "'," +
             "'" + dbh.MakeSafe(mvarPickList2) + "'," +
             "'" + dbh.MakeSafe(mvarPickList3) + "'," +
             "'" + dbh.MakeSafe(mvarPickList4) + "'," +
             "'" + dbh.MakeSafe(mvarPickList5) + "'," +
             "'" + mvarSignature.getSignature() + "'," +
             "'" + dbh.MakeSafe(mvarSignature.getSurname()) + "'," +
             "'" + dbh.SQLDate(dbh.strToDate(mvarSignature.getDateSigned())) + "'," +
             "'" + mvarSketch.getSignature() + "'," +
             completed + "," +
             pendingUpload + "," +
             "'" + dbh.SQLDate(dbh.strToDate(mvarDateModified)) + "'," +
             "'" + dbh.MakeSafe(mvarClientAddress.getPeople().getName()) + "'," +
             "'" + dbh.MakeSafe(mvarClientAddress.getPeople().getTel()) + "'," +
             "'" + dbh.MakeSafe(mvarClientAddress.getPeople().getEmail()) + "'," +
             "'" + dbh.MakeSafe(mvarPaperWork) + "'," +
             engFinished + "," +
             "'" + dbh.MakeSafe(mvarWorksCarriedOut) + "'," +
             "'" + dbh.MakeSafe(mvarCauseOfProblem) + "'" +
             ")";
    		
    		
    		// Save JobTimes
    		
    	} catch (Exception e)
    	{
    		Log.i("SQLcmdAddJob, ExceptionL", e.getMessage());
    	}
    	
    	return sqlQuery;
    	
    }
    
    
    public void UpdatePendingStatus(boolean bValue)
    {
    	DatabaseHelper dbh = JTApplication.getInstance().GetDatabaseManager();
    	String sqlQuery;
    	Integer iValue;
    	String ret = "";
    	
    	try
    	{
    		iValue = (bValue=true) ? 1 : 0;
    		sqlQuery = "UPDATE jobs SET PendingUpload=" + iValue + " WHERE JobNo='" + dbh.MakeSafe(mvarJobNo) + "'";
            JTApplication.getInstance().GetDatabaseManager().ExecuteSQL(sqlQuery);
    		if (ret != "")
    		{
        		Log.i("C_JTJob.UpdatePendingStatus: Exception", ret);
    			// Something have done wrong with the sqlQuery.
    		}
    	} catch (Exception e)
    	{
    		Log.i("C_JTJob.UpdatePendingStatus: Exception", e.getMessage());
    	}
    }
    
    

    
}


















