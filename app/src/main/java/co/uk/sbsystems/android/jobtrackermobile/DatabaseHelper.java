// DatabaseHelper.java
// Author :Sam Sherwin: 2012
// 
// Provides helpers to manager database manipulation.
//


package co.uk.sbsystems.android.jobtrackermobile;

import android.content.Context; 
import android.database.Cursor; 
import android.database.sqlite.SQLiteDatabase; 
import android.database.sqlite.SQLiteOpenHelper; 
import android.database.sqlite.SQLiteStatement; 
import android.util.Log;
import android.webkit.WebChromeClient;
import android.widget.Spinner;

import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList; 
import java.util.Date;
import java.util.Random;

import java.util.List; 

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

	
public class DatabaseHelper {
	
private static final String TAG = "DatabaseHelper";


static final String DATABASE_NAME = "example.db"; 
static final int DATABASE_VERSION = 24;
static final String TABLE_NAME = "jobs"; 

private Context context; 
private SQLiteDatabase db; 

private SQLiteStatement insertStmt; 
private static final String INSERT = "insert into " + TABLE_NAME + "(JobNo) values (?)"; 

private Parser objParser;

private List<String> _labels = new ArrayList<String>();

public DatabaseHelper(Context context) { 
	this.context = context; 
	OpenHelper openHelper = new OpenHelper(this.context); 
	this.db = openHelper.getWritableDatabase(); 
	this.insertStmt = this.db.compileStatement(INSERT); 
	
	objParser = new Parser();
} 

	public String ExecuteSQL(String value)
	{
		String ret = "";
		
		try {
			this.db.execSQL(value);
		} 
		catch (Exception e)
		{
			ret = e.getMessage();	
		}
		
		return ret;
	}
	
	public SQLiteDatabase GetDB() {
		return db;
	}
	
	public long insert(String name) { 
		this.insertStmt.bindString(1, name); 
		return this.insertStmt.executeInsert(); 
	} 

	public void deleteAll() { 
		this.db.delete(TABLE_NAME, null, null); 
	}
	
	public void SaveAssignedEngineer()
	{
		try {
			String query;
			long RecordID = JTApplication.getInstance().GetApplicationManager().getRecordID();
			String deviceEngineerName = JTApplication.getInstance().GetApplicationManager().getDeviceAssignedEngineerName();
			
			if (RecordID > 0) {
				query = "UPDATE Settings SET Engineer='" + deviceEngineerName + "' WHERE RecordId=" + RecordID;
			} else {
				query = "INSERT INTO Settings (Engineer) VALUES ('" + deviceEngineerName + "')";
			}
			db.execSQL(query);
		} catch (Exception e)
		{
			Log.i("DatabaseHelper SaveAssignedEngineer Exception: ", e.getMessage());
		}
	}
	
	
	
	
	public void SaveSettings()
	{
		
		try {
			String query;
			long RecordID = JTApplication.getInstance().GetApplicationManager().getRecordID();
			int Port = JTApplication.getInstance().GetApplicationManager().getPort();
			String HostName = JTApplication.getInstance().GetApplicationManager().getHostName();
			Integer UseDueDate = JTApplication.getInstance().GetApplicationManager().getUseDueDate();
			Integer UseJobBrief = JTApplication.getInstance().GetApplicationManager().getUseJobBrief();
			Integer UseReverseLookup = JTApplication.getInstance().GetApplicationManager().getUseReverseLookup();
			Integer IgnoreLowSignal = JTApplication.getInstance().GetApplicationManager().getIgnoreLowSignal();

			String MobileUserName = JTApplication.getInstance().GetApplicationManager().getMobileUserName();
			String MobilePassword = JTApplication.getInstance().GetApplicationManager().getMobilePassword();
			
			if (RecordID > 0 ) {
				query = "UPDATE Settings SET Port=" + Port + ",RemoteIP='" + HostName + "'"
				+ ",DueDate=" + UseDueDate + ",UseJobBrief=" + UseJobBrief + ",IgnoreLowSignal=" + IgnoreLowSignal + ",UseReverseLookup=" + UseReverseLookup
				+ ",MobileUserName='" + MobileUserName + "',MobilePassword='" + MobilePassword + "'"
				+ " WHERE Recordid=" + RecordID;
			} else {
				query = "INSERT INTO Settings (Port,RemoteIP,DueDate,UseJobBrief,IgnoreLowSignal,UseReverseLookUp,MobileUserName,MobilePassword) VALUES " 
						+ "(" + Port + ",'" + HostName + "',"
						+ UseDueDate +"," + UseJobBrief +"," + IgnoreLowSignal +"," + UseReverseLookup + "," 
						+ "'" +  MobileUserName + "','" + MobilePassword + "'" 
						+ ")";
			}
			db.execSQL(query);
		}
		catch (Exception e)
		{
			String myString = e.getMessage();
			myString += "";
		}
		
	}
	
	// Read the next number, increment it and store it back in the database and return the incremented value.
	public Integer GetUniqueNumber() {
		Integer i=0;
		try {
			
			String query = "SELECT RecordID FROM Counters";
			Cursor cursor = this.db.rawQuery(query,null); 
			if (cursor.moveToNext()) {
				i = cursor.getInt(cursor.getColumnIndex("RecordID"));
			}
			cursor.close();
			i++;
			this.db.execSQL("UPDATE Counters SET RecordID=" + i);
		} catch (Exception e) {
			Log.i(TAG, e.getMessage());
			Random r = new Random();
			i = r.nextInt(65535-1) + 1;		// Best attempt to keep going.
		}
		
		return i;
	}
	
	public void LoadSettings()
	{
		try {
			
			String query = "SELECT * FROM Settings Order By RecordID";
			Cursor cursor = this.db.rawQuery(query,null); 
			if (cursor.moveToNext()) {
				JTApplication.getInstance().GetApplicationManager().setRecordID(cursor.getLong(cursor.getColumnIndex("RecordID")));
				JTApplication.getInstance().GetApplicationManager().setHostName(cursor.getString(cursor.getColumnIndex("RemoteIP")));
				JTApplication.getInstance().GetApplicationManager().setPort(cursor.getInt(cursor.getColumnIndex("Port")));
				JTApplication.getInstance().GetApplicationManager().setDeviceAssignedEngineerName(cursor.getString(cursor.getColumnIndex("Engineer")));
				JTApplication.getInstance().GetApplicationManager().setUseDueDate(cursor.getInt(cursor.getColumnIndex("DueDate")));
				JTApplication.getInstance().GetApplicationManager().setUseJobBrief(cursor.getInt(cursor.getColumnIndex("UseJobBrief")));
				JTApplication.getInstance().GetApplicationManager().setIgnoreLowSignal(cursor.getInt(cursor.getColumnIndex("IgnoreLowSignal")));
				JTApplication.getInstance().GetApplicationManager().setUseReverseLookup(cursor.getInt(cursor.getColumnIndex("UseReverseLookUp")));

				JTApplication.getInstance().GetApplicationManager().setMobileUserName(cursor.getString(cursor.getColumnIndex("MobileUserName")));
				JTApplication.getInstance().GetApplicationManager().setMobilePassword(cursor.getString(cursor.getColumnIndex("MobilePassword")));

				
			}
			cursor.close();
		}
		catch (Exception e)
		{
			String query = e.getMessage();
			query += "";
		} finally {
			LoadLabels();
		}
	}

	public String GetJobLabel(int iNdx)
	{
		String sValue = "";
		try {
			sValue = _labels.get(iNdx).toString();
		} catch (Exception e)
		{
			Log.i("GetJobLabel, iNdx-" + iNdx, e.getMessage());
			sValue = "";
		}
		
		return(sValue);
	}
	
	public void LoadLabels()
	{
		String caption = "";
		Integer index =0;
		
		try {
			String query = "SELECT * FROM labels";
			Cursor cursor = this.db.rawQuery(query,null);
			cursor.moveToFirst();
			do {
				index = cursor.getInt(cursor.getColumnIndex("Indx"));
				caption= cursor.getString(cursor.getColumnIndex("Client"));
				
				_labels.add(index,caption);
			} while (cursor.moveToNext());
			cursor.close();
			
		} catch (Exception e)
		{
			Log.i("LoadLabels", e.getMessage());
		}
	}
	
	
public List<String> selectAll() { 
	List<String> list = new ArrayList<String>(); 
	Cursor cursor = this.db.query(TABLE_NAME, new String[] { "JobNo" },null, null, null, null, "JobNo asc"); 
	
	if (cursor.moveToFirst()) { 
		do { 
			list.add(cursor.getString(0)); 
		} while (cursor.moveToNext()); 
	} 
	if (cursor != null && !cursor.isClosed()) { 
		cursor.close(); 
	} 

	return list;
} 

private static class OpenHelper extends SQLiteOpenHelper { 

	OpenHelper(Context context) { 
		super(context, DATABASE_NAME, null, DATABASE_VERSION); 
	} 


	@Override
	public void onCreate(SQLiteDatabase db) { 
		// db.execSQL("CREATE TABLE " + TABLE_NAME + " (recordid INTEGER PRIMARY KEY, JobNo TEXT)" ); 
		db.execSQL("CREATE TABLE jobs (recordid INTEGER PRIMARY KEY, JobNo Text,Site_name Text,Site_Address Text,Site_PCode Text,Site_Contacts Text,Site_Tel Text,Site_Mobile Text,Site_Fax Text,Site_Email Text,Site_Website Text," + 
				"Comp_Name Text,Comp_Address Text,Comp_Tel Text,Comp_Mobile Text,Comp_Fax Text,Comp_Email Text,Comp_website Text,JobStatus Text," +
				"jobtext1 Text,jobtext2 Text,jobtext3 Text,jobtext4 Text,jobtext5 Text,jobtext6 Text,jobtext7 Text,jobtext8 Text,jobtext9 Text,jobtext10 Text," +
				"jobtext11 Text,jobtext12 Text,jobtext13 Text,JobBrief Text,jobdate1 Real,jobdate2 Real,jobdate3 Real,jobdate4 Real,jobdate5 Real,jobdate6 Real," +
				"jobdate7 Real,jobdate8 Real,jobdate9 Real,RecallReason Text,picklist1 Text,picklist2 Text,picklist3 Text,picklist4 Text,picklist5 Text," +
				"JobFinished Integer,DateModified Real,Comp_Contacts Text,Signature Text,Sketch Text,Sig_Surname Text,Sig_Date Real,ClientContact_Name Text," +
				"ClientContact_Tel Text,ClientContact_Email Text,PendingUpload Integer,PaperWork Text" +
				")");
		
		
		db.execSQL("CREATE TABLE JobSearch (JobNo Text PRIMARY KEY,Client Text,SiteAdd Text,JobDate Real" +
				")");
		
		db.execSQL("CREATE TABLE JobTimes (recordid INTEGER PRIMARY KEY,EngineerName Text,EventDate Real,StartTime Real,EndTime Real,TimeType Integer," +
				"JobNo Text,JobRecId Integer,EngineerID Integer,EntryValue Real" +
				")");
		
		
		db.execSQL("CREATE TABLE Labels (Indx Integer,Client TextValue" +
		")");
		
		db.execSQL("CREATE TABLE PartsRequired (JobNo Text,Qty Real,Desc Text,KLSPTNo Text,SuppName Text,SuppPtNo Text,RecordID Integer,Engineer Text" +
		")");
		
		db.execSQL("CREATE TABLE PartsUsed (RecordID Integer PRIMARY KEY,Qty Real,Description Text,JobNo Text,UnitPrice Real,PartNo Text" +
		")");
		
		db.execSQL("CREATE TABLE PaymentReceipt (RecordID Integer PRIMARY KEY,JobNo Text,Amount Real,PayType Text,ReceiptNo Text," +
				"DateTaken Real,EngineerNotes Text,DateInOffice Real,ReceivedBy Text,Description Text" +
				")");
		

		db.execSQL("CREATE TABLE Settings (RecordID Integer PRIMARY KEY,Port Integer,RemoteIP Text,SBSServer Text,Engineer Text,DueDate Integer," +
				"UseJobDetails Integer,UseJobBrief Integer,IgnoreLowSignal Integer,UseReverseLookUp Integer" +
				")");

		// This is not needed her.  The creation will be taken care off in the onUpgrade() routine.
		//db.execSQL("CREATE TABLE JobImg (RecordID integer primary key autoincrement, JobNo text ,ImageName text");

		onUpgrade(db,1,1);
		
	} 

	
	// To make this execute make sure you increase the database version number DATABASE_VERSION at the top of this file.
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 
		try {
			db.execSQL("CREATE TABLE jobs2 (recordid INTEGER PRIMARY KEY, JobNo2 TEXT, Surname Text)" );
		} catch (Exception e) {
			Log.i("onUpgrade", e.getMessage());
		}
		
		try {
			db.execSQL("ALTER TABLE jobs add column Site_PCode text");
		} catch (Exception e) {
			Log.i("onUpgrade", e.getMessage());
		}
		
		try {
			db.execSQL("ALTER TABLE jobs add column Comp_PCode text");
		} catch (Exception e) {
			Log.i("onUpgrade", e.getMessage());
		}

		try {
			db.execSQL("CREATE TABLE FireHydrant (RecordID Integer PRIMARY KEY,JTRecordID Integer,SiteAddress Text,SitePostCode Text,TestDate Real,Engineer text," +
					"HydrantLocation Text,RefNumber Integer," +
					"Details1 Text,Details2 Text,Details3 Text,Details4 Text,Details5 Text,Details6 Text,Details7 Text,Details8 Text," + 
					"Details9 Text,Details10 Text,Details11 Text,Details12 Text," +
					"JobNo Text" + 
					")");
		} catch (Exception e) {
			Log.i("onUpgrade: Create FireHydrant", e.getMessage());
		}
		
		try {
			//db.execSQL("DROP TABLE IF EXISTS RiserService");
			
			db.execSQL("CREATE TABLE RiserService (RecordID Integer PRIMARY KEY,JTRecordID Integer,SiteAddress Text,SitePostCode Text," +
					"RiserNumber Integer," +
					"RiserLocation Text," +
					"JobNo Text," +
					"ExtServiceCheck1 Text,ExtServiceCheck2 Text,ExtServiceCheck3 Text,ExtServiceCheck4 Text,ExtServiceCheck5 Text,ExtServiceCheck6 Text," +
					"ExtDetails1 Text,ExtDetails2 Text,ExtDetails3 Text,ExtDetails4 Text,ExtDetails5 Text,ExtDetails6 Text," +
					"ExtStatus1 Text,ExtStatus2 Text,ExtStatus3 Text,ExtStatus4 Text,ExtStatus5 Text,ExtStatus6 Text," +
					"IntServiceCheck1 Text,IntServiceCheck2 Text,IntServiceCheck3 Text,IntServiceCheck4 Text,IntServiceCheck5 Text," +
					"IntDetails1 Text,IntDetails2 Text,IntDetails3 Text,IntDetails4 Text,IntDetails5 Text," +
					"IntServiceOutletValve," +
					"Air1 Text,Air2 Text,Water1 Text,Water2 Text,CompletionChecked1 Text,CompletionChecked2 Text,CompletionChecked3 Text," +
					"Comments Text,RemedialWorks Text,OverAllStatus Text,DatePrinted Text" +
					")");
		} catch (Exception e) {
			Log.i("onUpgrade: Create FireHydrant", e.getMessage());
		}
		

		try {
			db.execSQL("CREATE TABLE RiserLocations (RecordID Integer PRIMARY KEY,JTRecordID Integer,RiserNumber Integer,RiserLocation Text," +
					"ClietID Integer" +
					")");
		} catch (Exception e) {
			Log.i("onUpgrade: Create FireHydrant", e.getMessage());
		}
		
		try {
			db.execSQL("ALTER TABLE RiserService add column Cust_Signature text");
			db.execSQL("ALTER TABLE RiserService add column Cust_SignatureName Text");
			db.execSQL("ALTER TABLE RiserService add column Cust_SignatureDate Real");
			db.execSQL("ALTER TABLE RiserService add column Stack_Signature Text");
			db.execSQL("ALTER TABLE RiserService add column Stack_SignatureName Text");
			db.execSQL("ALTER TABLE RiserService add column Stack_SignatureDate Real");
			db.execSQL("ALTER TABLE RiserService add column Pump_Signature Text");
			db.execSQL("ALTER TABLE RiserService add column Pump_SignatureName Text");
			db.execSQL("ALTER TABLE RiserService add column Pump_SignatureDate Real");
		} catch (Exception e) {
			Log.i("onUpgrade", e.getMessage());
		}
		
		
		try {
			db.execSQL("CREATE TABLE Counters (RecordID Integer )");
			db.execSQL("INSERT INTO Counters VALUES (1)");
		} catch (Exception e) {
			Log.i("onUpgrade: Create Counters", e.getMessage());
		}
		
		
		try {
			db.execSQL("ALTER TABLE JobSearch add column PCode Text");
		} catch (Exception e) {
			Log.i("onUpgrade: Alter JobSearch", e.getMessage());
		}

		try {
			db.execSQL("ALTER TABLE JobSearch add column PendingUpload Integer");
			db.execSQL("ALTER TABLE jobs add column PendingUpload Integer");
		} catch (Exception e) {
			Log.i("onUpgrade: Alter JobSearch", e.getMessage());
		}
		
		try {
			db.execSQL("ALTER TABLE JobSearch add column EngFinished Integer");
			db.execSQL("ALTER TABLE JobSearch add column WorksCarriedOut Text");
			db.execSQL("ALTER TABLE JobSearch add column CauseOfProblem Text");
			db.execSQL("ALTER TABLE jobs add column EngFinished Integer");
			db.execSQL("ALTER TABLE jobs add column WorksCarriedOut Text");
			db.execSQL("ALTER TABLE jobs add column CauseOfProblem Text");
		} catch (Exception e) {
			Log.i("onUpgrade: Alter JobSearch, jobs ", e.getMessage());
		}

		// Add Job Tracker Mobile Security
		try {
			db.execSQL("ALTER TABLE Settings add column MobileUserName Text");
			db.execSQL("ALTER TABLE Settings add column MobilePassword Text");
		} catch (Exception e) {
			Log.i("onUpgrade: Alter Settings",e.getMessage());
		}
		
		
		// Add Inlet and Outlet to Risers
		try {
			db.execSQL("ALTER TABLE RiserService add column InletKey Text");
			db.execSQL("ALTER TABLE RiserService add column OutletKey Text");
		} catch (Exception e) {
			Log.i("onUpgrade: Alter Settings",e.getMessage());
		}
		
		try {
			db.execSQL("CREATE TABLE Engineers (EngName TextValue" +
			")");
		} catch (Exception ex) {
			Log.i("onUpgrade: Alter Settings",ex.getMessage());
		}

		// Add Telephone to job summary (The saved search results)
		try {
			db.execSQL("ALTER TABLE JobSearch add column Telephone Text");
		} catch (Exception e) {
			Log.i("onUpgrade: Alter Settings",e.getMessage());
		}


		// Add Image Table Here.
		try{
			db.execSQL("CREATE TABLE JobImg (RecordID integer primary key autoincrement, JobNo text ,ImageName text");
		}catch(Exception e){
			Log.i("onUpgrad:Alter Settings", e.getMessage());
		}

        // Add Description and type to job summary (The saved search results)
        try {
            db.execSQL("CREATE TABLE JobImg (RecordID integer primary key autoincrement, JobNo text ,ImageName text");
            db.execSQL("ALTER TABLE JobImg add column Description Text");
            db.execSQL("ALTER TABLE JobImg add column ImageType Text");
            db.execSQL("ALTER TABLE JobImg add column Upload Integer");
        } catch (Exception e) {
            Log.i("onUpgrade: Alter Settings",e.getMessage());
        }
		
	}

	}


	public void UpdateLabels(String xml)
	{
		
		int _index;
		String _caption;
		String SQLStr;
		
		try
		{
			
			// remove previous labels, need to do better here as if this fails there are no labels available.
			db.execSQL("DELETE FROM labels");
			
			Document doc = objParser.XMLfromString(xml);
			NodeList nodeLst = doc.getElementsByTagName("LABEL");
			for (int s = 0; s < nodeLst.getLength(); s++) {

			    Node fstNode = nodeLst.item(s);
			    
			    if (fstNode.getNodeType() == Node.ELEMENT_NODE) 
			    {
			    	
			    	  NodeList fstNmElmntLst;
			    	  Element fstNmElmnt;
			    	  NodeList fstNm;
			  
			    	  Element fstElmnt = (Element) fstNode;
			    	  
			          fstNmElmntLst = fstElmnt.getElementsByTagName("NDX");
			          fstNmElmnt = (Element) fstNmElmntLst.item(0);
			          fstNm = fstNmElmnt.getChildNodes();
			          try {
			        	  _index = Integer.valueOf(fstNm.item(0).getNodeValue());
			          } catch (Exception e)
			          {
			        	  _index  = -1;
			          }
			          
			          fstNmElmntLst = fstElmnt.getElementsByTagName("VALUE");
			          fstNmElmnt = (Element) fstNmElmntLst.item(0);
			          fstNm = fstNmElmnt.getChildNodes();
			          try {
			        	  _caption = fstNm.item(0).getNodeValue();
			          } catch (Exception e)
			          {
			        	  _caption ="";
			          }
			          
			          try {
			        	  SQLStr = "INSERT INTO labels VALUES (" + _index + ",'" + _caption + "')";
			        	  db.execSQL(SQLStr);
			          } catch (Exception e)
			          {
			        	  Log.i("UpdateLabels", e.getMessage());
			          }
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("ExtractTagValue: " + e.getMessage());
		}	
	}
	
	
	
	
	public String SaveJobToCache()
	{
		String sqlQuery;
		String ret = "";
		String jobNumber;
		C_JTJob currentJob = JTApplication.getInstance().GetApplicationManager().GetloadedJob();		// get local copy
		try
		{
			jobNumber = currentJob.getJobNo();
			// remove previous cached copy.
			try {

				this.db.execSQL("DELETE FROM jobs WHERE JobNo='" + jobNumber + "'");
				this.db.execSQL("DELETE FROM JobTimes WHERE JobNo='" + jobNumber + "'");
				this.db.execSQL("DELETE FROM PartsRequired WHERE JobNo='" + jobNumber + "'");
				this.db.execSQL("DELETE FROM PartsUsed WHERE JobNo='" + jobNumber + "'");
				this.db.execSQL("DELETE FROM PaymentReceipt WHERE JobNo='" + jobNumber + "'");
				this.db.execSQL("DELETE FROM FireHydrant WHERE JobNo='" + jobNumber + "'");
				this.db.execSQL("DELETE FROM RiserService WHERE JobNo='" + jobNumber + "'");
				this.db.execSQL("DELETE FROM JobImg WHERE JobNo='"+ jobNumber + "'");

			} catch (Exception e) {
				
			}


			String sql_command;

			try{
				int count= currentJob.image_paths_list.size();
                this.db.execSQL("DELETE FROM JobImg WHERE JobNo='"+ jobNumber + "'");
				for(int i=0 ;i<count; i++){
					sql_command = "insert into JobImg (JobNo, ImgPath) values (" + jobNumber + ","+ currentJob.image_paths_list.get(i)+")";
					this.db.execSQL(sql_command);
				}


			}catch(Exception e){
				Log.i(TAG+ " SaveImagePath", e.getMessage());
			}
			
			try {
				sqlQuery = currentJob.SQLcmdAddJob();
				this.db.execSQL(sqlQuery);
			} catch (Exception e) {
				Log.i(TAG + "SaveJobToCache.SQLcmdAddJob",e.getMessage());
			}
			
			
			try {
				// Save Job Times to Cache
				for (int i=0; i < currentJob.getJobTimes().size(); i++) {
					C_JobTimes timesheet = currentJob.getJobTimes().get(i);
					if (timesheet != null) {
						sqlQuery = currentJob.SQLcmdAddJobTimes(timesheet);
						this.db.execSQL(sqlQuery);
					}
				}
			} catch (Exception e) {
				Log.i(TAG + "SaveJobToCache.SQLcmdAddJobTime",e.getMessage());
			}

			try {
				// Save Parts Required to Cache
				for (int i=0; i < currentJob.getPartsRequired().size(); i++) {
					C_PartsRequired partsrequired = currentJob.getPartsRequired().get(i);
					if (partsrequired.isMvarDelete() == false) {
						sqlQuery = currentJob.SQLcmdAddPartsRequired(partsrequired);
						this.db.execSQL(sqlQuery);
					}
				}
			} catch (Exception e) {
				Log.i(TAG + "SaveJobToCache.SQLcmdAddPartsRequired",e.getMessage());
			}

			try {
				// TODO SavePartsUsedCache
				for (int i=0; i < currentJob.getPartsUsed().size(); i++) {
					C_PartsUsed partsused = currentJob.getPartsUsed().get(i);
					if (partsused.isMvarDelete() == false) {
						sqlQuery = currentJob.SQLcmdAddPartsUsed(partsused);
						this.db.execSQL(sqlQuery);
					}
				}
			} catch (Exception e) {
				Log.i(TAG + "SaveJobToCache.SQLcmdAddPartsUsed",e.getMessage());
			}

			try {
				// TODO SavePaymentReceiptsCache
				for (int i=0; i < currentJob.getPaymentReceipt().size(); i++) {
					C_PaymentReceipt receipts = currentJob.getPaymentReceipt().get(i);
					if (receipts.isMvarDelete() == false) {
						sqlQuery = currentJob.SQLcmdAddPaymentReceipts(receipts);
						this.db.execSQL(sqlQuery);
					}
				}
			} catch (Exception e) {
				Log.i(TAG + "SaveJobToCache.SQLcmdAddPaymentReceipts",e.getMessage());
			}
			
			try {
				// TODO SaveFireHydrantstoCache
				for (int i=0; i < currentJob.getFireHydrant().size(); i++) {
					C_FireHydrant firehydrant = currentJob.getFireHydrant().get(i);
					sqlQuery = currentJob.SQLcmdAddFireHydrants(firehydrant);
					this.db.execSQL(sqlQuery);
				}
			} catch (Exception e) {
				Log.i(TAG + "SaveJobToCache.SQLcmdAddFireHydrants",e.getMessage());
			}
			
			try {
				// TODO SaveRiserServicestoCache
				for (int i=0; i < currentJob.getRiserServices().size(); i++) {
					C_RiserService  riserservice = currentJob.getRiserServices().get(i);
					sqlQuery = currentJob.SQLcmdAddRiserService(riserservice);
					this.db.execSQL(sqlQuery);
				}
			} catch (Exception e) {
				Log.i(TAG + "SaveJobToCache.SQLcmdAddRiserService",e.getMessage());
			}
			
			
			
		} catch (Exception e)
		{
			Log.i("SaveJobToCache Exception:", e.getMessage());
		}
		
		return ret;
	}
	
	
	// Return true if the job number specified is stored in the local database.
	public boolean isJobCached(String jobNumber)
	{
		boolean ret = false;
		String sqlQuery;
		
		try {
			sqlQuery = "SELECT JobNo FROM jobs WHERE JobNo='" + jobNumber + "'";
			Cursor cursor = this.db.rawQuery(sqlQuery, null);
			
			if (cursor.moveToNext()) 
			{
				ret = true;
			}
			
			cursor.close();
		} catch (Exception e)
		{
			Log.i("isJobCached, Exception:",e.getMessage());
		}
		
		return ret;
		
	}
	
	
	// Return true if the job number specified is stored in the local database and its pending an update.
	public boolean isJobCachedPendingUpdate(String jobNumber)
	{
		boolean ret = false;
		String sqlQuery;
		
		try {
			sqlQuery = "SELECT JobNo,PendingUpload FROM jobs WHERE JobNo='" + jobNumber + "' AND PendingUpload <> 0";
			Cursor cursor = this.db.rawQuery(sqlQuery, null);
			
			if (cursor.moveToNext()) 
			{
				ret = true;
			}
			
			cursor.close();
		} catch (Exception e)
		{
			Log.i("isJobCached, Exception:",e.getMessage());
		}
		
		return ret;
		
	}
	
	
	
	
	
	
    public String MakeSafe(String sSQL)
    {
	    String sRet = "";
	    try {
		    sRet = sSQL.replaceAll("'", "''");
	    } catch (Exception e)
	    {
	    	Log.i("C_JTJob.MakeSafe: Exception",e.getMessage());
	    }
	    
	    return sRet;
    }
	
	
    public String SQLDate(Date dtDate)
    {
    	String ret="";
    	String tmp,tmp1;
    	
    	try 
    	{
        	//SimpleDateFormat df = new SimpleDateFormat("ddmmyy");
    		 
        	if (dtDate == null)
        	{
    			ret = "";
    		} else
    		{
    			tmp = "0" + dtDate.getMonth();
    			tmp1 = "0" + dtDate.getDay(); 
    			ret = (tmp.substring(tmp.length() -2)) + (tmp1.substring(tmp1.length() -2));
    		}
    		
    	} catch (Exception e)
    	{
    		Log.i("SQLDate: Exception:", e.getMessage());
    	}
    	
    	return ret;
    }
    
    public Date strToDate(String sDate)
    {
    	Date ret;
    	SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
    	
    	try {
    		sDate = sDate.trim();
    		
    		if (sDate.equals("01/01/0001"))
    		{
    			ret = null;
    		}
    		else
    		{
    			ret = format.parse(sDate);
    		}
    		
    	} catch (Exception e)
    	{
    	//	Log.i("StrToDate: Exception:", e.getMessage());
    		ret = null;
    	}
    	
    	return ret;
    }
    
    public long ToEpochFromString(String sDate)
    {
    	long timeInMillisSinceEpoch = 0;
    	SimpleDateFormat sdf  = new SimpleDateFormat("dd/MM/yyyy");
    	try {
    		Date date = sdf.parse(sDate);
        	timeInMillisSinceEpoch = date.getTime(); 
        	    	
    	} catch (Exception e) {
    		// Not a valid date.
    		timeInMillisSinceEpoch = 0;
    	}
    	
    	return timeInMillisSinceEpoch ;
    }
    

    public String ToEpochStringFromString(String sDate)
    {
    	long timeInMillisSinceEpoch = 0;
    	SimpleDateFormat sdf  = new SimpleDateFormat("dd/MM/yyyy");
    	try {
    		Date date = sdf.parse(sDate);
        	timeInMillisSinceEpoch = date.getTime(); 
        	    	
    	} catch (Exception e) {
    		// Not a valid date.
    		timeInMillisSinceEpoch = 0;
    	}
    	// 0 = 01/01/1970, we don't want that so return an empty string.
    	if (timeInMillisSinceEpoch == 0) {
    		return "";
    	} else {
    		return Long.toString(timeInMillisSinceEpoch) ;
    	}
    }

    
    
    
    public Date fromEpochToDate(Long dateValue)
    {
    	Date myDate = null;
    	
    	try {
    		myDate = new Date(Long.parseLong(dateValue.toString()));
    	} catch(Exception e) {
    		// Not sure what this would happen?
    	}
    	
    	return myDate;
    }
    

    public String fromEpochStringToDate(String dateValue)
    {
    	Date myDate = null;
    	String sDate = "";
    	
    	try {
    		if (dateValue.equals("0")) {
    			sDate = "";
    		} else {
	    		myDate = new Date(Long.parseLong(dateValue));
	    		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    		sDate = df.format(myDate);
    		}
    	} catch(Exception e) {
    		// Not sure what this would happen?
    		Log.i("Error",e.getMessage());
    	}
    	
    	if (sDate.equalsIgnoreCase("01/01/0001")) {
    		sDate ="";
    	}
    	return sDate;
    }
    
    public String ToEpochStringFromTimeString(String sDate)
    {
    	long timeInMillisSinceEpoch = 0;
    	SimpleDateFormat sdf  = new SimpleDateFormat("HH:mm");
    	try {
    		Date date = sdf.parse(sDate);
        	timeInMillisSinceEpoch = date.getTime(); 
        	    	
    	} catch (Exception e) {
    		// Not a valid date.
    		timeInMillisSinceEpoch = 0;
    	}
    	
    	return Long.toString(timeInMillisSinceEpoch) ;
    }
    
    public String ToEpochStringFromLongTimeString(String sDate)
    {
    	long timeInMillisSinceEpoch = 0;
    	StringBuilder myDate = new StringBuilder();
    	String tmpDate;
    	String timePart = "";
    	String AMPMPart = "";
    	
    	SimpleDateFormat sdf  = new SimpleDateFormat("dd/MMM/yyyy hh:mm a");
    	try {
    		
    		AMPMPart = sDate.substring(sDate.lastIndexOf(' ') + 1);
    		timePart = sDate.substring(0,sDate.length() - 3);
    		
    		myDate.append("01/JAN/1970 ").append(timePart).append(" ").append(AMPMPart);
    		
    		tmpDate = myDate.toString();
    		
    		Date date = sdf.parse(myDate.toString());
        	timeInMillisSinceEpoch = date.getTime(); 
        	    	
    	} catch (Exception e) {
    		// Not a valid date.
    		timeInMillisSinceEpoch = 0;
    	}
    	
    	return Long.toString(timeInMillisSinceEpoch) ;
    }
    
    public String ToEpochStringFrom24HrTimeString(String sDate)
    {
    	long timeInMillisSinceEpoch = 0;
    	StringBuilder myDate = new StringBuilder();
    	String tmpDate;
    	String timePart = "";
    	String AMPMPart = "";
    	
    	SimpleDateFormat sdf  = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
    	try {
    		
    		timePart = sDate.substring(sDate.lastIndexOf(' ') + 1);
    		
    		
    		myDate.append("01/JAN/1970 ").append(timePart);
    		
    		tmpDate = myDate.toString();
    		
    		Date date = sdf.parse(myDate.toString());
        	timeInMillisSinceEpoch = date.getTime(); 
        	    	
    	} catch (Exception e) {
    		// Not a valid date.
    		timeInMillisSinceEpoch = 0;
    	}
    	
    	return Long.toString(timeInMillisSinceEpoch) ;
    }
    
    
    
    
    public String fromEpochStringToTime(String dateValue)
    {
    	Date myDate = null;
    	String sDate = "";
    	
    	try {
    		myDate = new Date(Long.parseLong(dateValue));
    		DateFormat df = new SimpleDateFormat("HH:mm");
    		sDate = df.format(myDate);
    	} catch(Exception e) {
    		// Not sure what this would happen?
    		Log.i("Error",e.getMessage());
    	}
    	
    	return sDate;
    }
    
    public String fromEpochStringToLongTime(String dateValue)
    {
    	Date myDate = null;
    	String sDate = "";
    	
    	try {
    		myDate = new Date(Long.parseLong(dateValue));
    		DateFormat df = new SimpleDateFormat("hh:mm aaa");
    		sDate = df.format(myDate);
    	} catch(Exception e) {
    		// Not sure what this would happen?
    		Log.i("Error",e.getMessage());
    	}
    	
    	return sDate;
    }
    
    
    
    public String fromEpochStringToLongDate(String dateValue)
    {
    	Date myDate = null;
    	String sDate = "";
    	
    	try {
    		myDate = new Date(Long.parseLong(dateValue));
    		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
    		sDate = df.format(myDate);
    	} catch(Exception e) {
    		// Not sure what this would happen?
    		Log.i("Error",e.getMessage());
    	}
    	
    	return sDate;
    }


	
	
    // Save the Job Summary to the Cache.  This is called from  AddToHashMap() in SearchJobs.java
	void SaveJobSummary(String JobNo,String Client,String SiteAddress,String PCode,String myDate,String myTelephone)
	{
		String SQL;
		
		try {
			
			myDate = ToEpochStringFromString(myDate);
			
			if (DoesJobSummaryExist(JobNo)) {
                SQL = "UPDATE JobSearch SET JobNo='" + MakeSafe(JobNo) + "'," 
                 + "Client='" + MakeSafe(Client) + "'," 
                 + "SiteAdd='" + MakeSafe(SiteAddress) + "'," 
                 + "PCode='" + MakeSafe(PCode) + "'," 
                 + "JobDate='" + myDate + "'," 
                 + "Telephone='" + myTelephone + "'"
                 + " WHERE JobNo='" + MakeSafe(JobNo) + "'";
				
			} else {
				SQL = "INSERT INTO JobSearch (JobNo,Client,SiteAdd,PCode,JobDate) " +
				"VALUES ('" + MakeSafe(JobNo) + "'"
				+ ",'" + MakeSafe(Client) + "'"
				+ ",'" + MakeSafe(SiteAddress) + "'" 
				+ ",'" + MakeSafe(PCode) + "'" 
				+ ",'" + myDate + "'"
				+ ",'" + myTelephone + "'"
				+ ")";
			}
			
			this.db.execSQL(SQL);

		} catch (Exception e) {
			Log.i("SaveJobSummary: Exception:", e.getMessage());
		}
	}
	
	void ClearJobCache()
	{
		try {
			db.execSQL("DELETE FROM JobSearch");
			db.execSQL("DELETE FROM Jobs");
		} catch (Exception e) {
			Log.i("ClearJobCache: Exception:", e.getMessage());
		}
	}
	
	void ClearEngineers()
	{
		try {
			db.execSQL("DELETE FROM Engineers");
		} catch (Exception e) {
			Log.i("ClearJobCache: Exception:", e.getMessage());
		}
	}

	void AddEngineer(String engName) {
		try {
			String sSQL = "INSERT INTO Engineers (EngName) VALUES (?)";
			SQLiteStatement stmt = db.compileStatement(sSQL);
			stmt.bindString(1,engName);
			stmt.execute();
		} catch (Exception ex){
			Log.i("ClearJobCache: Exception:", ex.getMessage());
		}
	}
	
	void ClearSpecificJobCache(String sJobNo)
	{
		try {
			db.execSQL("Update Jobs Set PendingUpload=0 WHERE JobNo='" + sJobNo + "'");
		} catch (Exception e) {
			Log.i("ClearSpecificJobCache: Exception:", e.getMessage());
		}
	}

	
	Boolean DoesJobSummaryExist(String JobNo)
	{
		Boolean ret = false;
		
		try {
			String SQL = "SELECT JobNo FROM JobSearch WHERE JobNo='" + JobNo +"'";
			Cursor cursor =this.db.rawQuery(SQL,null);
			if (cursor.moveToNext()) {
				// Found the job
				ret = true ;
			} else {
				// Job does not exist in cache.
				ret = false;
			}
			cursor.close();
		} catch(Exception e) {
			
		}
		
		return ret;
	}
	
	public boolean LoadJobFromCache(C_JTJob myJob, String JobNo)
	{
		boolean JobFound = false;
		
		try {
			myJob.clear();
			String SQL = "SELECT * FROM jobs WHERE JobNo='" + JobNo + "'";
			Cursor cursor =this.db.rawQuery(SQL,null);


            //load image paths
            myJob.image_paths_list.clear();
            String imgSql = "select * from JobImg where JobNo='" + JobNo+"'";

            Cursor img_cur = this.db.rawQuery(imgSql, null);

            GalleryImgItem newImg;

            if(img_cur.moveToFirst()){
                while (img_cur.isAfterLast() == false) {
                    newImg = new GalleryImgItem();
                    newImg.filename = img_cur.getString(img_cur.getColumnIndex("ImageName"));

                    newImg.upload = (img_cur.getInt(img_cur.getColumnIndex("Upload")) == 0)?false :true;

                    newImg.description =img_cur.getString(img_cur.getColumnIndex("Description"));

                    myJob.image_paths_list.add(newImg);

                    cursor.moveToNext();
                }
            }
			
			if (cursor.moveToNext()) {
				// Found the job
				JobFound  = true ;
				
				myJob.setJobNo(cursor.getString(cursor.getColumnIndex("JobNo")));
				
				// Site address details.
				myJob.getSiteAddress().setName(cursor.getString(cursor.getColumnIndex("Site_name")));
				myJob.getSiteAddress().setAddress(cursor.getString(cursor.getColumnIndex("Site_Address")));
				myJob.getSiteAddress().setPostCode(cursor.getString(cursor.getColumnIndex("Site_PCode")));
				myJob.getSiteAddress().setTelephone(cursor.getString(cursor.getColumnIndex("Site_Tel")));
				myJob.getSiteAddress().setMobile(cursor.getString(cursor.getColumnIndex("Site_Mobile")));
				myJob.getSiteAddress().setFax(cursor.getString(cursor.getColumnIndex("Site_Fax")));
				myJob.getSiteAddress().setEmail(cursor.getString(cursor.getColumnIndex("Site_Email")));
				myJob.getSiteAddress().setWWW(cursor.getString(cursor.getColumnIndex("Site_Website")));
				myJob.getSiteAddress().setContacts(cursor.getString(cursor.getColumnIndex("Site_Contacts")));
				
				// Client address details.
				myJob.getClientAddress().setName(cursor.getString(cursor.getColumnIndex("Comp_Name")));
				myJob.getClientAddress().setAddress(cursor.getString(cursor.getColumnIndex("Comp_Address")));
				myJob.getClientAddress().setPostCode(cursor.getString(cursor.getColumnIndex("Comp_PCode")));
				myJob.getClientAddress().setTelephone(cursor.getString(cursor.getColumnIndex("Comp_Tel")));
				myJob.getClientAddress().setMobile(cursor.getString(cursor.getColumnIndex("Comp_Mobile")));
				myJob.getClientAddress().setFax(cursor.getString(cursor.getColumnIndex("Comp_Fax")));
				myJob.getClientAddress().setEmail(cursor.getString(cursor.getColumnIndex("Comp_Email")));
				myJob.getClientAddress().setWWW(cursor.getString(cursor.getColumnIndex("Comp_website")));
 				
				myJob.setJobStatus(cursor.getString(cursor.getColumnIndex("JobStatus")));
				myJob.setJobText1(cursor.getString(cursor.getColumnIndex("jobtext1")));
				myJob.setJobText2(cursor.getString(cursor.getColumnIndex("jobtext2")));
				myJob.setJobText3(cursor.getString(cursor.getColumnIndex("jobtext3")));
				myJob.setJobText4(cursor.getString(cursor.getColumnIndex("jobtext4")));
				myJob.setJobText5(cursor.getString(cursor.getColumnIndex("jobtext5")));
				myJob.setJobText6(cursor.getString(cursor.getColumnIndex("jobtext6")));
				myJob.setJobText7(cursor.getString(cursor.getColumnIndex("jobtext7")));
				myJob.setJobText8(cursor.getString(cursor.getColumnIndex("jobtext8")));
				myJob.setJobText9(cursor.getString(cursor.getColumnIndex("jobtext9")));
				myJob.setJobText10(cursor.getString(cursor.getColumnIndex("jobtext10")));
				myJob.setJobText11(cursor.getString(cursor.getColumnIndex("jobtext11")));
				myJob.setJobText12(cursor.getString(cursor.getColumnIndex("jobtext12")));
				myJob.setJobText13(cursor.getString(cursor.getColumnIndex("jobtext13")));
				
				myJob.setJobBrief(cursor.getString(cursor.getColumnIndex("JobBrief")));

				Long myDate ;
				myDate = cursor.getLong(cursor.getColumnIndex("jobdate1"));
				myJob.setJobDate1(myDate.toString());

				myDate = cursor.getLong(cursor.getColumnIndex("jobdate2"));
				myJob.setJobDate2(myDate.toString());
				
				myDate = cursor.getLong(cursor.getColumnIndex("jobdate3"));
				myJob.setJobDate3(myDate.toString());

				myDate = cursor.getLong(cursor.getColumnIndex("jobdate4"));
				myJob.setJobDate4(myDate.toString());

				myDate = cursor.getLong(cursor.getColumnIndex("jobdate5"));
				myJob.setJobDate5(myDate.toString());

				myDate = cursor.getLong(cursor.getColumnIndex("jobdate6"));
				myJob.setJobDate6(myDate.toString());

				myDate = cursor.getLong(cursor.getColumnIndex("jobdate7"));
				myJob.setJobDate7(myDate.toString());

				myDate = cursor.getLong(cursor.getColumnIndex("jobdate8"));
				myJob.setJobDate8(myDate.toString());

				myDate = cursor.getLong(cursor.getColumnIndex("jobdate9"));
				myJob.setJobDate9(myDate.toString());
				
				
				myJob.setRecallReason(cursor.getString(cursor.getColumnIndex("RecallReason")));
				myJob.setPickList1(cursor.getString(cursor.getColumnIndex("picklist1")));
				myJob.setPickList2(cursor.getString(cursor.getColumnIndex("picklist2")));
				myJob.setPickList3(cursor.getString(cursor.getColumnIndex("picklist3")));
				myJob.setPickList4(cursor.getString(cursor.getColumnIndex("picklist4")));
				myJob.setPickList5(cursor.getString(cursor.getColumnIndex("picklist5")));

				myJob.setJobCompleted(cursor.getInt(cursor.getColumnIndex("JobFinished")) != 0 ? true : false );
				
				myJob.getSignature().setSignature(cursor.getString(cursor.getColumnIndex("Signature")));
				myJob.getSignature().setDateSigned(cursor.getString(cursor.getColumnIndex("Sig_Date")));
				myJob.getSignature().setSurname(cursor.getString(cursor.getColumnIndex("Sig_Surname")));
				
				myJob.getSketch().setSignature(cursor.getString(cursor.getColumnIndex("Sketch")));
				
				myJob.setPendingUpload(cursor.getInt(cursor.getColumnIndex("PendingUpload")) != 0 ? true : false );
				
				myJob.setPaperWork(cursor.getString(cursor.getColumnIndex("PaperWork")));
				
				myJob.setEngFinished(cursor.getInt(cursor.getColumnIndex("EngFinished")) != 0 ? true : false );
				myJob.setWorksCarriedOut(cursor.getString(cursor.getColumnIndex("WorksCarriedOut")));
				myJob.setCauseOfProblem(cursor.getString(cursor.getColumnIndex("CauseOfProblem")));
				
				
				// Load Job Times.
				LoadJobTimesFromCache(myJob,JobNo);
				
				// Load Parts used
				LoadPartsUsedFromCache(myJob, JobNo);
				
				// Load Parts Required
				LoadPartsRequiredFromCache(myJob, JobNo);
				

				
				LoadFireHydrantsFromCache(myJob,JobNo);
				
				LoadRiserServicesFromCache(myJob,JobNo);
						
			} else {
				// Job does not exist in cache.
				JobFound = false;
			}
			cursor.close();
			
		} catch (Exception e) {
			Log.i("LoadJobFromCache: Exception:", e.getMessage());
		}
		
		return JobFound;
		
	}
	
	private void LoadJobTimesFromCache(C_JTJob myJob, String JobNo)
	{
		Long  myDate;
		Long lData;
		String sData;
		int iData;
		Double nData;
		
		try {
			String SQL = "SELECT * FROM JobTimes WHERE JobNo='" + JobNo + "'";
			Cursor cursor =this.db.rawQuery(SQL,null);
			
			while (cursor.moveToNext()) {
				C_JobTimes timecard = new C_JobTimes();
				
				myDate = cursor.getLong(cursor.getColumnIndex("EventDate"));
				timecard.setEventDate(myDate.toString());
				
				myDate = cursor.getLong(cursor.getColumnIndex("StartTime"));
				timecard.setStartTime(myDate.toString());

				myDate = cursor.getLong(cursor.getColumnIndex("EndTime"));
				timecard.setEndTime(myDate.toString());
				
				lData = cursor.getLong(cursor.getColumnIndex("recordid"));
				timecard.setRecordID(lData);
				
				lData= cursor.getLong(cursor.getColumnIndex("EngineerID"));
				timecard.setEngineerID(lData);
				
				sData = cursor.getString(cursor.getColumnIndex("JobNo"));
				timecard.setJobNo(sData);
				
				iData = cursor.getInt(cursor.getColumnIndex("TimeType"));
				timecard.setTimeType(iData);
				
				sData = cursor.getString(cursor.getColumnIndex("EngineerName"));
				timecard.setEngName(sData);
				
				nData = cursor.getDouble(cursor.getColumnIndex("EntryValue"));
				timecard.setEntryValue(nData);
				
				myJob.getJobTimes().add(timecard);
				
			}
		} catch (Exception e) {
			Log.i("Error Loading Jom Times From Cache",e.getMessage());
		}
	}
	
	private void LoadPartsRequiredFromCache(C_JTJob myJob, String JobNo)
	{
		String sData;
		Long lData;
		
		try {
			String SQL = "SELECT * FROM PartsRequired WHERE JobNo='" + JobNo + "'";
			Cursor cursor = this.db.rawQuery(SQL,null);
			
			while (cursor.moveToNext()) {
				C_PartsRequired  parts = new C_PartsRequired();
				
				sData = cursor.getString(cursor.getColumnIndex("JobNo"));
				parts.setJobNo(sData);
				
				lData = cursor.getLong(cursor.getColumnIndex("Qty"));
				parts.setQty(lData);
				
				sData = cursor.getString(cursor.getColumnIndex("Desc"));
				parts.setDescription(sData);
				
				sData = cursor.getString(cursor.getColumnIndex("KLSPTNo"));
				parts.setOurPartNo(sData);
				
				sData = cursor.getString(cursor.getColumnIndex("SuppName"));
				parts.setSupplierName(sData);
				
				sData = cursor.getString(cursor.getColumnIndex("SuppPtNo"));
				parts.setSupplierPartNo(sData);
				
				lData= cursor.getLong(cursor.getColumnIndex("RecordID"));
				parts.setRecordID(lData);
				
				sData= cursor.getString(cursor.getColumnIndex("Engineer"));
				parts.setEngineer(sData);
				
				myJob.getPartsRequired().add(parts);
				
			}
			
			
		} catch (Exception e) {
			Log.i("Error Loading Parts Required From Cache",e.getMessage());
		}
	}
	
	
	private void LoadPartsUsedFromCache(C_JTJob myJob, String JobNo)
	{
		String sData;
		Long lData;
		Double dData;
		
		try {
			String SQL = "SELECT * FROM PartsUsed WHERE JobNo='" + JobNo + "'";
			Cursor cursor = this.db.rawQuery(SQL,null);
			
			while (cursor.moveToNext()) {
				C_PartsUsed  parts = new C_PartsUsed();
				
				lData= cursor.getLong(cursor.getColumnIndex("RecordID"));
				parts.setRecordID(lData);
				
				dData= cursor.getDouble(cursor.getColumnIndex("Qty"));
				parts.setQty(dData);
				
				sData = cursor.getString(cursor.getColumnIndex("Description"));
				parts.setDescription(sData);
				
				sData = cursor.getString(cursor.getColumnIndex("JobNo"));
				parts.setJobNo(sData);
				
				
				dData = cursor.getDouble(cursor.getColumnIndex("UnitPrice"));
				parts.setUnitPrice(dData);
				
				sData= cursor.getString(cursor.getColumnIndex("PartNo"));
				parts.setPartNo(sData);
				
				myJob.getPartsUsed().add(parts);
			}
			
			
		} catch (Exception e) {
			Log.i("Error Loading Parts Used From Cache",e.getMessage());
		}
	}
	
	
	
	private void LoadFireHydrantsFromCache(C_JTJob myJob, String JobNo)
	{
		Long  myDate;
		Long lData;
		String sData;
		int iData;
		
		try {
			String SQL = "SELECT * FROM FireHydrant WHERE JobNo='" + JobNo + "'";
			Cursor cursor =this.db.rawQuery(SQL,null);
			
			while (cursor.moveToNext()) {
				C_FireHydrant firehydrant= new C_FireHydrant();
				
				myDate = cursor.getLong(cursor.getColumnIndex("TestDate"));
				firehydrant.setTestDate(myDate.toString());
				
				lData = cursor.getLong(cursor.getColumnIndex("RecordID"));
				firehydrant.setRecordID(lData);
				
				lData = cursor.getLong(cursor.getColumnIndex("JTRecordID"));
				firehydrant.setJTRecordID(lData);

				sData = cursor.getString(cursor.getColumnIndex("SiteAddress"));
				firehydrant.setSiteAddress(sData);
				
				sData = cursor.getString(cursor.getColumnIndex("SitePostCode"));
				firehydrant.setSitePostCode(sData);
				
				sData = cursor.getString(cursor.getColumnIndex("Engineer"));
				firehydrant.setEngineer(sData);
				
				sData = cursor.getString(cursor.getColumnIndex("HydrantLocation"));
				firehydrant.setHydrantLocation(sData);

				iData = cursor.getInt(cursor.getColumnIndex("RefNumber"));
				firehydrant.setNumber(iData);
				
				sData = cursor.getString(cursor.getColumnIndex("Details1"));
				firehydrant.setDetails(sData, 0);

				sData = cursor.getString(cursor.getColumnIndex("Details2"));
				firehydrant.setDetails(sData, 1);

				sData = cursor.getString(cursor.getColumnIndex("Details3"));
				firehydrant.setDetails(sData, 2);

				sData = cursor.getString(cursor.getColumnIndex("Details4"));
				firehydrant.setDetails(sData, 3);

				sData = cursor.getString(cursor.getColumnIndex("Details5"));
				firehydrant.setDetails(sData, 4);

				sData = cursor.getString(cursor.getColumnIndex("Details6"));
				firehydrant.setDetails(sData, 5);

				sData = cursor.getString(cursor.getColumnIndex("Details7"));
				firehydrant.setDetails(sData, 6);

				sData = cursor.getString(cursor.getColumnIndex("Details8"));
				firehydrant.setDetails(sData, 7);

				sData = cursor.getString(cursor.getColumnIndex("Details9"));
				firehydrant.setDetails(sData, 8);

				sData = cursor.getString(cursor.getColumnIndex("Details10"));
				firehydrant.setDetails(sData, 9);

				sData = cursor.getString(cursor.getColumnIndex("Details11"));
				firehydrant.setDetails(sData, 10);

				sData = cursor.getString(cursor.getColumnIndex("Details12"));
				firehydrant.setDetails(sData, 11);

				sData = cursor.getString(cursor.getColumnIndex("JobNo"));
				firehydrant.setJobNo(sData);
				
				myJob.getFireHydrant().add(firehydrant);
				
			}
		} catch (Exception e) {
			Log.i("Error Loading Fire hydrant From Cache",e.getMessage());
		}

		
	}
	
	private void LoadRiserServicesFromCache(C_JTJob myJob,String JobNo)
	{
		Long  myDate;
		Long lData;
		Integer iData;
		String sData;
		Integer iNdx;
		String sFieldName;
		
		try {
			String SQL = "SELECT * FROM RiserService WHERE JobNo='" + JobNo + "'";
			Cursor cursor =this.db.rawQuery(SQL,null);
			
			while (cursor.moveToNext()) {
				C_RiserService  riserservice= new C_RiserService ();
				
				lData = cursor.getLong(cursor.getColumnIndex("RecordID"));
				riserservice.setRecordID(lData);
				
				lData = (long) cursor.getLong(cursor.getColumnIndex("JTRecordID"));
				riserservice.setJTRecordID(lData);

				iData = cursor.getInt(cursor.getColumnIndex("RiserNumber"));
				riserservice.setRiserNumber(iData);
				
				sData = cursor.getString(cursor.getColumnIndex("RiserLocation"));
				riserservice.setRiserLocation(sData);

				sData = cursor.getString(cursor.getColumnIndex("JobNo"));
				riserservice.setJobNo(sData);
				
				sData = cursor.getString(cursor.getColumnIndex("InletKey"));
				riserservice.setInLetKey(sData);

				sData = cursor.getString(cursor.getColumnIndex("OutletKey"));
				riserservice.setOutLetKey(sData);
				
				for (iNdx = 1; iNdx <= 6; iNdx++) {
					riserservice.setExtServiceCheck(iNdx - 1, cursor.getString(cursor.getColumnIndex("ExtServiceCheck" + iNdx.toString())));
					riserservice.setExtDetails(iNdx - 1, cursor.getString(cursor.getColumnIndex("ExtDetails" + iNdx.toString())));
					riserservice.setExtStatus(iNdx - 1, cursor.getString(cursor.getColumnIndex("ExtStatus" + iNdx.toString())));
				}
				
				for (iNdx = 1; iNdx <= 5; iNdx++) {
					riserservice.setIntServiceCheck(iNdx - 1, cursor.getString(cursor.getColumnIndex("IntServiceCheck" + iNdx.toString())));
					riserservice.setIntDetails(iNdx - 1, cursor.getString(cursor.getColumnIndex("IntDetails" + iNdx.toString())));
				}
				
				for (iNdx = 1; iNdx <= 2; iNdx++) {
					riserservice.setAir(iNdx - 1, cursor.getString(cursor.getColumnIndex("Air" + iNdx.toString())));
					riserservice.setWater(iNdx - 1, cursor.getString(cursor.getColumnIndex("Water" + iNdx.toString())));
				}

				for (iNdx = 1; iNdx <= 3; iNdx++) {
					riserservice.setCompletionChecked(iNdx - 1, cursor.getString(cursor.getColumnIndex("CompletionChecked" + iNdx.toString())));
				}

				riserservice.setComments(cursor.getString(cursor.getColumnIndex("Comments")));
				riserservice.setRemedialWorks(cursor.getString(cursor.getColumnIndex("RemedialWorks")));
				riserservice.setOverAllStatus(cursor.getString(cursor.getColumnIndex("OverAllStatus")));
				myDate = cursor.getLong(cursor.getColumnIndex("DatePrinted"));
				riserservice.setPrintedDate(myDate.toString());
				
				riserservice.setCustSignature(cursor.getString(cursor.getColumnIndex("Cust_Signature")));
				riserservice.setCustSurname(cursor.getString(cursor.getColumnIndex("Cust_SignatureName")));
				myDate = cursor.getLong(cursor.getColumnIndex("Cust_SignatureDate"));
				riserservice.setCustDate(myDate.toString());
				
				riserservice.setStackSignature(cursor.getString(cursor.getColumnIndex("Stack_Signature")));
				riserservice.setStackSurname(cursor.getString(cursor.getColumnIndex("Stack_SignatureName")));
				myDate = cursor.getLong(cursor.getColumnIndex("Stack_SignatureDate"));
				riserservice.setStackDate(myDate.toString());
				
				riserservice.setPumpSignature(cursor.getString(cursor.getColumnIndex("Pump_Signature")));
				riserservice.setPumpSurname(cursor.getString(cursor.getColumnIndex("Pump_SignatureName")));
				myDate = cursor.getLong(cursor.getColumnIndex("Pump_SignatureDate"));
				riserservice.setPumpDate(myDate.toString());
				
				myJob.getRiserServices().add(riserservice);
			
			}
				
			
		} catch (Exception e) {
			Log.i(TAG + " LoadRiserSerivcesFromCache",e.getMessage());
		}
	}
	
	
	//
	public List<String> getEngineers()
	{
		List<String> labels = new ArrayList<String>();
		String SQL = "";
		
		try {
			labels.add("Select your name");
			SQL = "SELECT * FROM Engineers Order By EngName";
			Cursor cursor =this.db.rawQuery(SQL,null);
			if (cursor.moveToFirst()) {
				do {
					labels.add(cursor.getString(0));
				} while (cursor.moveToNext());
			}
			cursor.close();
		} catch (Exception ex) {
			Log.i(TAG + " getEngineers",ex.getMessage());
		}
		return labels;
	}
}


















