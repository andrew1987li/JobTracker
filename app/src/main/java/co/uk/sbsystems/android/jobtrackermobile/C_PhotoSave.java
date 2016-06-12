package co.uk.sbsystems.android.jobtrackermobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sam on 02/03/2016.
 */
public class C_PhotoSave {
    private Context myContext;
    private String NameOfFolder = "/JTMobile/";
    private String NameOfFile = "";			// Job Number_uniquefilename

    public String SaveImage(Context context,Bitmap ImageToSave,String JobNo) {
        myContext = context;
        File file = null;
        String file_path = "";
        Integer filecount=0;

        try {
            file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder + JobNo;
            File dir = new File(file_path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            filecount = CountFiles(file_path);
            filecount ++;
            file = new File(dir,filecount.toString() + "_" + JobNo + ".jpg");
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            FileOutputStream fOut = new FileOutputStream(file);
            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedTheMakeAvailable(file);
            AbleToSave();

        } catch (FileNotFoundException e) {
            UnableToSave(e.getMessage());
        }
        catch (IOException e) {
            UnableToSave(e.getMessage());
        }

        return file_path;
    }

    public String CreateFileName(String JobNo, String photoType) {
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder + JobNo;
        File dir = new File(file_path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Integer filecount = CountFiles(file_path);
        filecount ++;

        DateFormat dateForamtter = new SimpleDateFormat("yyyyMMddhhmmss");

        Date today = new Date();
        String s = dateForamtter.format(today);
        File file = new File(dir,JobNo+ "_"+s + "_" +photoType  + ".jpg");

        return file.getAbsolutePath();
    }

    private void MakeSureFileWasCreatedTheMakeAvailable(File file) {
        MediaScannerConnection.scanFile(myContext, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        // TODO Auto-generated method stub

                    }
                });
    }

    private void UnableToSave(String msg) {
        try {
            Toast.makeText(myContext, "Unable to save the Photo: Error:" + msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void AbleToSave() {
        try {
            Toast.makeText(myContext,"The Photo was succssfully saved." ,Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private Integer CountFiles(String path) {
        File[] files;
        String JobNo = JTApplication.getInstance().GetApplicationManager().GetloadedJob().getJobNo();
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder + JobNo;
        File f = new File(file_path);
        files = f.listFiles();
        Integer filecount = files.length;

        return filecount;

    }

}
