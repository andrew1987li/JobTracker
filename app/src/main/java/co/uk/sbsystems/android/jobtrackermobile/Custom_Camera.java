package co.uk.sbsystems.android.jobtrackermobile;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sam on 08/03/2016.
 */
    public class Custom_Camera extends Activity {
        private Camera mCamera;
        private CameraPreview mCameraPreview;
        private String filename;


        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.cam_custom);
            mCamera = getCameraInstance();
            if(mCamera == null){
                String result = "The picture has took.";
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",result);
                setResult(Activity.RESULT_CANCELED,returnIntent);
                finish();
            }

            Intent intent = getIntent();

            filename = intent.getStringExtra("filename");

            mCameraPreview = new CameraPreview(this, mCamera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);

            int wid = preview.getWidth();
            preview.setMinimumHeight(wid);
   /*         FrameLayout.LayoutParams mParams;
            mParams = (FrameLayout.LayoutParams) preview.getLayoutParams();
            mParams.height = preview.getWidth();
            preview.setLayoutParams(mParams);
            preview.postInvalidate();
*/

            preview.addView(mCameraPreview);

            Button captureButton = (Button) findViewById(R.id.button_capture);
            captureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCamera.takePicture(null, null, mPicture);
/*
                    String result = "The picture has took.";
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result",result);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                    */

                }
            });
        }

        /**
         * Helper method to access the camera returns null if it cannot get the
         * camera or does not exist
         *
         * @return
         */
        private Camera getCameraInstance() {
            Camera camera = null;
            int numCams = Camera.getNumberOfCameras();
            if(numCams > 0){
                try{
                    camera = Camera.open(0);

                } catch (RuntimeException ex){
                   // Toast.makeText(ctx, getString(R.string.camera_not_found), Toast.LENGTH_LONG).show();
                }
            }

            return camera;
        }

        Camera.PictureCallback mPicture = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                File pictureFile = new File(filename);
                if (pictureFile == null) {
                    return;
                }
                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                    String result = "The picture has took.";
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result",result);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();

                } catch (FileNotFoundException e) {

                } catch (IOException e) {

                }
            }
        };

        private static File getOutputMediaFile() {
            File mediaStorageDir = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "MyCameraApp");
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("MyCameraApp", "failed to create directory");
                    return null;
                }
            }
            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());
            File mediaFile;
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");

            return mediaFile;
        }
    }
