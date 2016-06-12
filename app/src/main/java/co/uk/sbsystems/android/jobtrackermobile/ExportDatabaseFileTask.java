package co.uk.sbsystems.android.jobtrackermobile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;




  public class ExportDatabaseFileTask extends AsyncTask<Void, Void, Void> {
	 private Context context;	
	  
	  public ExportDatabaseFileTask(Context mcontext) {
		  context = mcontext;
	  }
        // can use UI thread here
	  @Override
        protected void onPreExecute() {
        	Log.v("BACKGROUND","onPreExecute");
        }
	  @Override
      protected void onPostExecute(Void result) {
    	  Log.v("BACKGROUND","onPostExecute");
    	  Toast.makeText(context, "The database was successfully sent", 
    			   Toast.LENGTH_LONG).show();    	  
       }
	  
        // automatically done on worker thread (separate from UI thread)
	  @Override
        protected  Void doInBackground(Void... args) {
        	 Log.v("BACKGROUND", "doInBackground");
        	android.os.Debug.waitForDebugger();
        	
        	File dbFile;
			  dbFile = new File(Environment.getDataDirectory() + "/data/co.uk.sbsystems.android.jobtrackermobile/databases/example.db");

		           File exportDir = new File(Environment.getExternalStorageDirectory(), "");
		           if (!exportDir.exists()) {
		              exportDir.mkdirs();
		           }
		           File file = new File(exportDir, dbFile.getName());
		           
		           try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		           
		           try {
					this.copyFile(dbFile, file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	
			final GMailSender mySender = new GMailSender("jobtrackerpro@gmail.com", "**Arnold2706");
				String body = "";
				String subject = "";
				
				try {
					body = JTApplication.getInstance().GetApplicationManager().getHostName();
					body += "\n" + JTApplication.getInstance().GetApplicationManager().getMobileUserName();
					
					subject = "From JT Mobile: ";
					subject += JTApplication.getInstance().GetApplicationManager().getHostName();
				} catch (Exception e) {
					
				}
				
				try {
					mySender.sendMail(subject,body ,"jobtrackerpro@gmail.com", "sam@sbsystems.co.uk",dbFile) ;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;

				
        }

      
        void copyFile(File src, File dst) throws IOException {
           FileChannel inChannel = new FileInputStream(src).getChannel();
           FileChannel outChannel = new FileOutputStream(dst).getChannel();
           try {
              inChannel.transferTo(0, inChannel.size(), outChannel);
           } finally {
              if (inChannel != null)
                 inChannel.close();
              if (outChannel != null)
                 outChannel.close();
           }
        }



     }    