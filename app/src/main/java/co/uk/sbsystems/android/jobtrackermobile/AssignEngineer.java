package co.uk.sbsystems.android.jobtrackermobile;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class AssignEngineer extends ListActivity {

	private String selectedEngineersName = "";

	static TextView labAssignedEngineer;
	
	
	boolean bOnline = JTApplication.getInstance().GetApplicationManager().getConnectedStatus();

	static ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	
	static SimpleAdapter adapter = null; 
	
	TextView labAssignedTo = null;

	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	
	    try {
		    setContentView(R.layout.assignengineer);
	    } catch (Exception e)
	    {
			Log.i("onCreate Exception", e.getMessage());
	    }
	    

		 labAssignedTo= (TextView)findViewById(R.id.labAssignedEngineersName);		// Shows the currently assigned engineer.

	    
	     adapter= new SimpleAdapter(this,list,R.layout.assigneng_listlayout,
		     		new String[] {"engineer"},
		     		new int[] {R.id.labEngineer}
		     		);
	    
	    // labAssignedEngineer = (TextView)findViewById(R.id.labEngineer);

	     
		    Button btnAssign = (Button) findViewById(R.id.butAssign);
		    btnAssign.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					assignEngineer(selectedEngineersName);
				}
			});
	     

		    Button btnClear = (Button) findViewById(R.id.butClear);
		    btnClear.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					clearEngineer();
				}
			});

			Button btnExit = (Button) findViewById(R.id.butExit);
			btnExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
				}
			});


		labAssignedTo.setText(JTApplication.getInstance().GetApplicationManager().getDeviceAssignedEngineerName());
		 
	     if (bOnline == true)
	    	 RequestEngineerList();
	    
	    
	     
		setListAdapter(adapter);

	}
	

		@Override
		protected void onListItemClick(ListView parent, View view, int position, long id) {
			try
			{
	
				String engineersName = (String) getListAdapter().getItem(position).toString();
				
				//Toast.makeText(this, engineersName + " selected", Toast.LENGTH_LONG).show();
				
				view.setSelected(true);
				
				selectedEngineersName = engineersName;
				
			} catch(Exception e)
			{
				Log.i("onListItemClick", "Exception:" + e.getMessage());
			}
		}
		
	private void RequestEngineerList()
	{
		JTApplication.getInstance().getTCPManager().RequestEngineerList();

	}
	
	
	static void UpdateScreen(String DataIn) {
		try {
			DoWork(DataIn);
		}
		catch (Exception e)
		{
			
		}
	}
	
	private static void DoWork(String DataIn)
	{
		Document doc = null;
		String engineerName;
		String usesJTM;			// Uses Job Tracker Mobile.
		
		try {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			// Create string read to parse XML from string.
			StringReader reader = new StringReader( DataIn );
			InputSource inputSource = new InputSource( reader );
			doc = db.parse(inputSource);
			doc.getDocumentElement().normalize();
			reader.close();
			
			} catch (IOException ioe) {
				System.out.println("I/O Exception: " + ioe.getMessage());
			} catch (ParserConfigurationException pce) {
				System.out.println("ParserConfigurationException: " + pce.getMessage());
			} catch (SAXException se) {
				System.out.println("SAXException: " + se.getMessage());
			}


		
		try 
		{
			// Remove previous entries.
			list.clear();

			JTApplication.getInstance().GetDatabaseManager().ClearEngineers();
			
			NodeList nodeLst = doc.getElementsByTagName("PERSON");
			for (int s = 0; s < nodeLst.getLength(); s++) {

			    Node fstNode = nodeLst.item(s);
			    
			    if (fstNode.getNodeType() == Node.ELEMENT_NODE) 
			    {
			  
			    	  Element fstElmnt = (Element) fstNode;
			    	  
			          NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("NAME");
			          Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
			          NodeList fstNm = fstNmElmnt.getChildNodes();
			          engineerName = fstNm.item(0).getNodeValue();
			          

			          NodeList fstUsElmntLst = fstElmnt.getElementsByTagName("USESJTM");
			          Element fstUsElmnt = (Element) fstUsElmntLst.item(0);
			          NodeList fstUs = fstUsElmnt.getChildNodes();
			          usesJTM = fstUs.item(0).getNodeValue();

			          
			          if (usesJTM.equalsIgnoreCase("Yes")) {
						  JTApplication.getInstance().GetDatabaseManager().AddEngineer(engineerName);
			          }
			          
			          AddToHashMap(engineerName);
			    }
			}
			
			adapter.notifyDataSetChanged();

			
			Log.i("Listing Jobs Finished", "--- FINISHED ---");

		}
		catch (Exception e)
		{
			System.out.println("Exception: " + e.getMessage() );
		}
		
	}

	 static void AddToHashMap(String engineerName)
		{
			HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("engineer",engineerName);
			list.add (temp);
		}
	 
	 // Using the selectedEngineersName assign this device to the engineer.
	 void assignEngineer(String engName)
	 {
		 int eqPos = -1;
		 
		 try {
			 if (engName != "") 
			 {
				 eqPos = engName.indexOf("=");
				 
				 if (eqPos > 0) {
					 // Only use after the =
					 String tmpName = engName.substring(eqPos + 1);
					 
					 // Now strip of the last character.
					 
					 engName = tmpName.substring(0,tmpName.length()-1);
				 }
				 
				 labAssignedTo.setText(engName);
				 JTApplication.getInstance().GetApplicationManager().setDeviceAssignedEngineerName(engName);
				 JTApplication.getInstance().GetDatabaseManager().SaveAssignedEngineer();
			 }
		 }
		catch (Exception e)
		{
			Log.i("assignEngineer. Exception", e.getMessage());
		}
	 }
	
	void clearEngineer()
	{
		String engName = "";
		labAssignedTo.setText(engName);
		JTApplication.getInstance().GetApplicationManager().setDeviceAssignedEngineerName("");
		JTApplication.getInstance().GetDatabaseManager().SaveAssignedEngineer();
	}
}
