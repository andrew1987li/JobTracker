package co.uk.sbsystems.android.jobtrackermobile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

// This has been mis-named.  It should really be CaptureRiserSignatures as its used for all signatures
// NOT just the pump signature.
// The Signature ID is passed in the Intent

public class CapturePumpSignature extends Activity {
	
	private static final String TAG = "CapturePumpSignature";
    private String bmpSignatureB64;
    private byte[] bmpBArray;
    private Bitmap myBitmap;
	
    LinearLayout mContent;
    signature mSignature;
    Button mClear, mGetSign, mCancel;
    public static String tempDir;
    public int count = 1;
    public String current = null;
    private Bitmap mBitmap;
    View mView;
    File mypath;

    private String uniqueId;
    private EditText yourName;
    private Spinner spEngineers;
    
    private C_RiserService myRiser;
    
    private Integer sigID = -1;				// Used to identity the type of signature being collected, passed via Intent "sigid2
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	
    	
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.signature);
       
        // Find out who called this view.
        Intent intent = getIntent();
		sigID = intent.getIntExtra("sigid",C_RiserService.PUMPSIGNATURE_ACTIVITY);
		
		tempDir = Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.external_dir) + "/";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(getResources().getString(R.string.external_dir), Context.MODE_PRIVATE);

        prepareDirectory();
        uniqueId = getTodaysDate() + "_" + getCurrentTime() + "_" + Math.random();
        current = uniqueId + ".png";
        mypath= new File(directory,current);


        mContent = (LinearLayout) findViewById(R.id.linearLayout);
        mSignature = new signature(this, null);
        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        mClear = (Button)findViewById(R.id.clear);
        mGetSign = (Button)findViewById(R.id.getsign);
        mGetSign.setEnabled(false);
        mCancel = (Button)findViewById(R.id.cancel);
        mView = mContent;

        yourName = (EditText) findViewById(R.id.yourName);
        spEngineers = (Spinner) findViewById(R.id.spEngineers);
        
        // Only show the Spinner for 
        if ((JTApplication.getInstance().getSettings().getbutRiserService() == true) || sigID == C_RiserService.CUSTSIGNATURE_ACTIVITY)  {
        	spEngineers.setVisibility(View.GONE);
        	yourName.setVisibility(View.VISIBLE);
        }    else {
        	yourName.setVisibility(View.GONE);
        	spEngineers.setVisibility(View.VISIBLE);
        	List<String> labels = JTApplication.getInstance().GetDatabaseManager().getEngineers();
        	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, labels);
     
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     
            // attaching data adapter to spinner
            spEngineers.setAdapter(dataAdapter);        	
        }
        
     
        
        
        
     // Pointer to Currently Loaded Riser
        C_RiserService rs = ListRiserServices.CurrentRiser;
        
        
        // Draw the image.
        // Load the signature
        if (sigID == C_RiserService.PUMPSIGNATURE_ACTIVITY) {
        	bmpSignatureB64 = rs.getEngSigPump().getSignature();
        	yourName.setText(rs.getEngSigPump().getSurname());
        }
        else if (sigID == C_RiserService.STACKSIGNATURE_ACTIVITY) { 
        	bmpSignatureB64 = rs.getEngSigStack().getSignature();
        	yourName.setText(rs.getEngSigStack().getSurname());
        }
        else if (sigID == C_RiserService.CUSTSIGNATURE_ACTIVITY) { 
        	bmpSignatureB64 = rs.getCustSig().getSignature();
        	yourName.setText(rs.getCustSig().getSurname());
        }

        // Set spinner to the previously saved engineers name.
        JTApplication.getInstance().setSpinnerTo(spEngineers, yourName.getText().toString());
        
        bmpBArray = Base64.decode(bmpSignatureB64,0);
        try {
        	myBitmap = BitmapFactory.decodeByteArray(bmpBArray, 0, bmpBArray.length);
        } catch (Exception e)
        {
        }        
        mView.invalidate();
        

        mClear.setOnClickListener(new OnClickListener() 
        {        
            public void onClick(View v) 
            {
                Log.v("log_tag", "Panel Cleared");
                myBitmap = null;
                mSignature.clear();
                mGetSign.setEnabled(false);
            }
        });

        mGetSign.setOnClickListener(new OnClickListener() 
        {        
            public void onClick(View v) 
            {
                Log.v("log_tag", "Panel Saved");
                boolean error = captureSignature();
                if(!error){
                    mView.setDrawingCacheEnabled(true);
                    mSignature.save(mView);
                    Bundle b = new Bundle();
                    b.putString("status", "done");
                    Intent intent = new Intent();
                    intent.putExtras(b);
                    setResult(RESULT_OK,intent);   
                    finish();
                }
            }
        });

        mCancel.setOnClickListener(new OnClickListener() 
        {        
            public void onClick(View v) 
            {
                Log.v("log_tag", "Panel Canceled");
                Bundle b = new Bundle();
                b.putString("status", "cancel");
                Intent intent = new Intent();
                intent.putExtras(b);
                setResult(RESULT_OK,intent);  
                finish();
            }
        });

    }
    
    
    

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
     // TODO Auto-generated method stub
     super.onWindowFocusChanged(hasFocus);
     
     	if (myRiser != null) {
	     	//bmpSignatureB64 = myRiser.getEngSigPump().getSignature();
	        //bmpBArray = Base64.decode(bmpSignatureB64,0);
	        try {
	        	myBitmap = BitmapFactory.decodeByteArray(bmpBArray, 0, bmpBArray.length);
	        } catch (Exception e)
	        {
	        	Log.i(TAG,e.getMessage());
	        }
     	}
    }
   
    
    @Override
    protected void onDestroy() {
        Log.w("GetSignature", "onDestory");
        super.onDestroy();
    }

    private boolean captureSignature() {

        boolean error = false;
        String errorMessage = "";
        String selectedName = "";

        // If we're not getting the customers signature then take value from the spinner
        // otherwise take it from the text box.
        try {
            if (sigID != C_RiserService.CUSTSIGNATURE_ACTIVITY) { 
            	selectedName = spEngineers.getSelectedItem().toString();
    	        if (selectedName.equalsIgnoreCase("Select your name")) {
    	            errorMessage = errorMessage + "Please enter your Name\n";
    	            error = true;
    	        } else {
    	        	yourName.setText(selectedName);
    	        }
            }
        } catch (Exception ex)
        { 
        	System.out.println(ex.getMessage().toString());        	
        }
        
        

//        if(yourName.getText().toString().equalsIgnoreCase("")){
//            errorMessage = errorMessage + "Please enter your Name\n";
//           error = true;
//        }   

        if(error){
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 105, 50);
            toast.show();
        }

        return error;
    }

    private String getTodaysDate() { 

        final Calendar c = Calendar.getInstance();
        int todaysDate =     (c.get(Calendar.YEAR) * 10000) + 
        ((c.get(Calendar.MONTH) + 1) * 100) + 
        (c.get(Calendar.DAY_OF_MONTH));
        Log.w("DATE:",String.valueOf(todaysDate));
        return(String.valueOf(todaysDate));

    }

    private String getCurrentTime() {

        final Calendar c = Calendar.getInstance();
        int currentTime =     (c.get(Calendar.HOUR_OF_DAY) * 10000) + 
        (c.get(Calendar.MINUTE) * 100) + 
        (c.get(Calendar.SECOND));
        Log.w("TIME:",String.valueOf(currentTime));
        return(String.valueOf(currentTime));

    }


    private boolean prepareDirectory() 
    {
        try 
        {
            if (makedirs()) 
            {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) 
        {
            e.printStackTrace();
            Toast.makeText(this, "Could not initiate File System.. Is Sdcard mounted properly?", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean makedirs() 
    {
        File tempdir = new File(tempDir);
        if (!tempdir.exists())
            tempdir.mkdirs();

        if (tempdir.isDirectory()) 
        {
            File[] files = tempdir.listFiles();
            for (File file : files) 
            {
                if (!file.delete()) 
                {
                    System.out.println("Failed to delete " + file);
                }
            }
        }
        return (tempdir.isDirectory());
    }

    public class signature extends View 
    {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) 
        {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v) 
        {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if(mBitmap == null)
            {
                mBitmap =  Bitmap.createBitmap (mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);;
            }
            Canvas canvas = new Canvas(mBitmap);
            try 
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream (); 
                v.draw(canvas); 
                mBitmap.compress(Bitmap.CompressFormat.JPEG , 100, baos); 
                
                //String url = Images.Media.insertImage(getContentResolver(), mBitmap, "title", null);
                String encoded = null ;
                
                byte[] image =  baos.toByteArray();
                try {
                	//System.out.println(Base64.class.getProtectionDomain().getCodeSource().getLocation());
                	encoded = Base64.encodeToString(image,0);		// 0 = adhere to RFC 2045
                }
                catch (Exception e) {
                    System.out.println("Error " + e.getMessage());
                }
                

                C_Signature mySignature = null;
                if (sigID == C_RiserService.PUMPSIGNATURE_ACTIVITY) 
                	mySignature = ListRiserServices.CurrentRiser.getEngSigPump();
                else if (sigID == C_RiserService.STACKSIGNATURE_ACTIVITY) 
                	mySignature = ListRiserServices.CurrentRiser.getEngSigStack();
                else if (sigID == C_RiserService.CUSTSIGNATURE_ACTIVITY) 
                	mySignature = ListRiserServices.CurrentRiser.getCustSig();
                
                if (mySignature != null) {
	                mySignature.setSignature(encoded);
	                mySignature.setSurname(yourName.getText().toString());
	                mySignature.setDateSigned(Long.toString(System.currentTimeMillis()));
                }
                
                // Log.v("log_tag","url: " + url);
                //In case you want to delete the file
                //boolean deleted = mypath.delete();
                //Log.v("log_tag","deleted: " + mypath.toString() + deleted);
                //If you want to convert the image to string use base64 converter

            }
            catch(Exception e) 
            { 
                Log.v("log_tag", e.toString()); 
            } 
        }

        public void clear() 
        {
            path.reset();
            invalidate();
        }
        


        @Override
        protected void onDraw(Canvas canvas) 
        {
            canvas.drawPath(path, paint);
            if (myBitmap != null) {
            	canvas.drawBitmap(myBitmap, 0, 0,null);
            }
                   
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) 
        {
            float eventX = event.getX();
            float eventY = event.getY();
            mGetSign.setEnabled(true);

            switch (event.getAction()) 
            {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                lastTouchX = eventX;
                lastTouchY = eventY;
                return true;

            case MotionEvent.ACTION_MOVE:

            case MotionEvent.ACTION_UP:

                resetDirtyRect(eventX, eventY);
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) 
                {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);
                    expandDirtyRect(historicalX, historicalY);
                    path.lineTo(historicalX, historicalY);
                }
                path.lineTo(eventX, eventY);
                break;

            default:
                debug("Ignored touch event: " + event.toString());
                return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string){
        }

        private void expandDirtyRect(float historicalX, float historicalY) 
        {
            if (historicalX < dirtyRect.left) 
            {
                dirtyRect.left = historicalX;
            } 
            else if (historicalX > dirtyRect.right) 
            {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) 
            {
                dirtyRect.top = historicalY;
            } 
            else if (historicalY > dirtyRect.bottom) 
            {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) 
        {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }

        
    }
    
}
