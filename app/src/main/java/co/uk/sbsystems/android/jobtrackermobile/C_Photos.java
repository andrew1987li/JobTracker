package co.uk.sbsystems.android.jobtrackermobile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

// This is the activity for the photo screen

@SuppressWarnings("deprecation")
public class C_Photos extends Activity {

	public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;

	private C_PhotoSave objSave = new C_PhotoSave();

	private String NameOfFolder = "/JTMobile/";
	private String NameOfFile = "";			// Job Number_uniquefilename
	private String file_path = "";
	private String JobNo = "";

   // Added by Sam.  I want the Description and Image Type sent across in the XML
    private String Description  = "";       // User can type this in.
    private String ImageType = "";          // Literal strings "BEFORE", "AFTER", "OTHER"

    private Button bt_delete;


	private File[] files;
	private String imageFileName ="",str_filename="";
	int index = -1;

	ImageView imageView;

	Gallery gallery;
	ArrayList<GalleryImgItem> bmplist;
	ImageAdapter img_adapter;

	C_JTJob myjob;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);


        TextView txt_filename = (TextView) findViewById(R.id.txt_filename);
        TextView txt_desc = (TextView) findViewById(R.id.txt_description);
        CheckBox chk_up = (CheckBox) findViewById(R.id.chkUpload);



        savedInstanceState.putString("imgName",txt_filename.getText().toString());
        savedInstanceState.putString("description", txt_desc.getText().toString());
        savedInstanceState.putBoolean("checked", chk_up.isChecked());


    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){

        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState.containsKey("imgName")){
            str_filename = savedInstanceState.getString("imgName");
        }
    }

    @Override
    public void onDestroy(){

        DatabaseHelper dbhelper  = JTApplication.getInstance().getDBManager();

        String sql_command;

        try{
            int count= myjob.image_paths_list.size();
            dbhelper.ExecuteSQL("DELETE FROM JobImg WHERE JobNo='" + JobNo + "'");
            for(int i=0 ;i<count; i++){
                int upstate = myjob.image_paths_list.get(i).upload?0:1;
                sql_command = "insert into JobImg (JobNo, ImageName, Description ,Upload) values (" + JobNo + ","+ myjob.image_paths_list.get(i).filename+","+ myjob.image_paths_list.get(i).description+","+ upstate+")";
                dbhelper.ExecuteSQL(sql_command);
            }


        }catch(Exception e){
            Log.i( "CPhoto SaveImagePath", e.getMessage());
        }






        super.onDestroy();
    }


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photos);

      /*  myReceiver = new CameraReceiver();
        IntentFilter intfil = new IntentFilter("android.intent.action.CAMERA_BUTTON");
        registerReceiver(myReceiver, intfil);
*/

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


		try {

			// Get list of images from

			myjob =  JTApplication.getInstance().GetApplicationManager().GetloadedJob();

            JobNo = myjob.getJobNo();
            file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder + JobNo;
            bmplist = myjob.image_paths_list;
            try{
                int count = bmplist.size();

                for(int i= 0 ;i < count; i++){
                    GalleryImgItem gal =bmplist.get(i);
                    String filename = file_path+"/"+gal.filename;
                    Bitmap bmp = decodeSampledBitmapFromFile(filename, 1000, 700);

                    gal.bmp = bmp;

                }


            }catch(Exception e){
                e.printStackTrace();
            }


			/*myjob.image_paths_list.clear();
                bmplist = new ArrayList<GalleryImgItem>();
			JobNo = myjob.getJobNo();
			file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder + JobNo;
			File f = new File(file_path);
			if(!f.exists())
				f.mkdir();
			files = f.listFiles();
			Bitmap bmr = null;
			String filename="";
			try {
				if (files != null) {
					for(int i=0;i<files.length;i++) {


						filename = files[i].getAbsolutePath();
						Bitmap bmp = decodeSampledBitmapFromFile(filename, 1000, 700);
                        GalleryImgItem gal = new GalleryImgItem();
						gal.bmp = bmp;
						gal.filename = files[i].getName();
						myjob.image_paths_list.add(files[i].getName());
						bmplist.add(gal);
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		imageView = (ImageView) findViewById(R.id.img_preview);

        TextView txt_filename = (TextView) findViewById(R.id.txt_filename);
        TextView txt_desc = (TextView) findViewById(R.id.txt_description);
        CheckBox chk_up = (CheckBox) findViewById(R.id.chkUpload);

        if(savedInstanceState!= null) {
            if (savedInstanceState.containsKey("imgName")) {
                String file_name = savedInstanceState.getString("imgName");
                txt_filename.setText(file_name);

                String imgfile = file_path + File.separator + file_name;

                File file = new File(imgfile);

                if(file.exists()) {
                    Bitmap bm = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000, 700);
                    imageView.setImageBitmap(bm);
                }


            }

            if (savedInstanceState.containsKey("description")) {
                String description = savedInstanceState.getString("description");
                txt_desc.setText(description);
            }

            if (savedInstanceState.containsKey("checked")) {
                Boolean chk = savedInstanceState.getBoolean("checked");
                chk_up.setChecked(chk);
            }

        }
        gallery = (Gallery) findViewById(R.id.galary_images);

		img_adapter =new ImageAdapter(this,bmplist);

		gallery.setAdapter(img_adapter);

		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try {
					//Toast.makeText(getApplicationContext(), "pic: " + position, Toast.LENGTH_SHORT).show();
					String filepath =  bmplist.get(position).filename;
					TextView txt_filename = (TextView)findViewById(R.id.txt_filename);
                    EditText txt_desc = (EditText)findViewById(R.id.txt_description);

                    txt_filename.setText(filepath);
                    txt_desc.setText(bmplist.get(position).description);



					Bitmap bmp = bmplist.get(position).bmp;
					if (bmp != null)
						imageView.setImageBitmap(bmp);

    				index = position;

                    bt_delete.setVisibility(View.VISIBLE);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});


		Button btnExit = (Button) findViewById(R.id.butExit);
		btnExit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

		Button btnTakeAfter=(Button) findViewById(R.id.butTakePhotoAfter);
		btnTakeAfter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    imageFileName = objSave.CreateFileName(JobNo, "After");
                    File file = new File(imageFileName);

                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

                    //  myReceiver = new CameraReceiver();
                    //   sendBroadcast(intent);
                    // startActivity(intent);
                    startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });

		Button btnTakeBefore = (Button) findViewById(R.id.butTakePhotoBefore);
		btnTakeBefore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					imageFileName = objSave.CreateFileName(JobNo,"Before");
					File file = new File(imageFileName);

                    /*Intent intent  = new Intent(C_Photos.this,Custom_Camera.class);


                    intent.putExtra("filename",imageFileName);

                    startActivityForResult(intent, 1);
*/
					Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

                  //  myReceiver = new CameraReceiver();
                  //   sendBroadcast(intent);
                   // startActivity(intent);
					startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});


		Button btnTakeOther = (Button) findViewById(R.id.butTakePhotoOther);
		btnTakeOther.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
                    imageFileName = objSave.CreateFileName(JobNo,"Other");
                    File file = new File(imageFileName);
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

                    //  myReceiver = new CameraReceiver();
                    //   sendBroadcast(intent);
                    // startActivity(intent);
                    startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);


				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		//Save the picutres with different name.
		Button btnSavephoto = (Button) findViewById(R.id.butSavePhote);
		btnSavephoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				imageView.buildDrawingCache();
				Bitmap bm = imageView.getDrawingCache();

				TextView txt_filename =(TextView)findViewById(R.id.txt_filename);
                EditText txt_desc = (EditText) findViewById(R.id.txt_description);
                CheckBox chk_up = (CheckBox)findViewById(R.id.chkUpload);
				String new_filename = txt_filename.getText().toString();
                String new_desc = txt_desc.getText().toString();



                if(new_filename.equals("")){
                    Toast.makeText(getApplicationContext(), "There is no photo selected", Toast.LENGTH_SHORT).show();

                    return;
                }
                if(index != -1){


                    bmplist.get(index).description = new_desc;

                    index = -1;
                    Toast.makeText(getApplicationContext(), "The picture has saved", Toast.LENGTH_SHORT).show();
                    txt_filename.setText("");
                    txt_desc.setText("");
                    imageView.setImageResource(0);

                    return;
                }

				OutputStream fout = null;
				try{

                    GalleryImgItem newItem = new GalleryImgItem();
                    newItem.filename = new_filename;
                    newItem.description = new_desc;
                    newItem.upload = chk_up.isChecked();

                    String filename = file_path+"/"+ new_filename;
                    Bitmap bmp = decodeSampledBitmapFromFile(filename, 1000, 700);
                    newItem.bmp = bmp;
                    bmplist.add(newItem);

                    index = -1;

                    Toast.makeText(getApplicationContext(), "The picture has saved", Toast.LENGTH_SHORT).show();
                    txt_filename.setText("");
                    txt_desc.setText("");
                    imageView.setImageResource(0);

                    img_adapter.notifyDataSetChanged();
                    /*
                    index =bmplist.size()-1;

                    img_adapter.notifyDataSetChanged();
                    gallery.setSelection(index, true);
                    */


				}catch(Exception e){
					Toast.makeText(getApplicationContext(), "File create error.", Toast.LENGTH_SHORT).show();

				}

			}
		});


		//Delete image
		//Button btnDeletPhoto = (Button)findViewById(R.id.butDeletePhoto);
        bt_delete= (Button) findViewById(R.id.butDeletePhoto);
        bt_delete.setVisibility(View.GONE);
        bt_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

                final TextView txtfile_name = (TextView)findViewById(R.id.txt_filename);
                final TextView txtDesc = (EditText)findViewById(R.id.txt_description);
                final String fn = txtfile_name.getText().toString();

                AlertDialog.Builder alertDlg = new AlertDialog.Builder(v.getContext());

                alertDlg.setTitle("Warning");

                alertDlg
                        .setMessage("Do you want to delete the image   " +fn)
                        .setCancelable(true)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                File oldfile = new File(file_path,fn);
                                if(index != -1) {
                                    bmplist.remove(index);
                                    index = -1;
                                    img_adapter.notifyDataSetChanged();

                                    imageView.setImageResource(0);
                                }
                                if(oldfile.exists())
                                    oldfile.delete();
                                txtfile_name.setText("");
                                txtDesc.setText("");
                                bt_delete.setVisibility(View.GONE);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }
                        ).show();


				img_adapter.notifyDataSetChanged();

			}
		});

		// Load images from folder.

	}
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
            try {
                    if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE) {


                        File file = new File(imageFileName);

                        if(!file.exists()) return;


                        Bitmap bm = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000, 700);


                        bt_delete.setVisibility(View.GONE);
                        //previous_filename = imageFileName;
                        //Bitmap bm = (Bitmap) data.getExtras().get("data");
                        //String file_name;
                        TextView txt_filename = (TextView) findViewById(R.id.txt_filename);
                        TextView txt_desc = (TextView) findViewById(R.id.txt_description);
                        CheckBox chk_up = (CheckBox) findViewById(R.id.chkUpload);


                        chk_up.setChecked(true);
                        txt_filename.setText(file.getName());
                        txt_desc.setText("");

                        imageView.setImageBitmap(bm);


                        index = -1;
                        //file_name = objSave.SaveImage(this, bm, JobNo);



                    }

                    // Store the filename in a database against the job number.
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

		}





		public class ImageAdapter extends BaseAdapter {

			private Context context;
			private ArrayList<GalleryImgItem> bitmaplist;
			int imageBackground;

			public ImageAdapter(Context context, ArrayList<GalleryImgItem>bmlist ) {
				this.context = context; bitmaplist =bmlist;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return bitmaplist.size();
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				String filepath;
				ImageView imageView = new ImageView(context);
				imageView.setImageBitmap(bitmaplist.get(position).bmp);
				return imageView;
			}

		}



		public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
		{ // BEST QUALITY MATCH

		    //First decode with inJustDecodeBounds=true to check dimensions
		    final BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;

			    BitmapFactory.decodeFile(path, options);

		    // Calculate inSampleSize, Raw height and width of image
		    final int height = options.outHeight;
		    final int width = options.outWidth;
		    options.inPreferredConfig = Bitmap.Config.RGB_565;
		    int inSampleSize = 1;

		    try {
				if (height > reqHeight)
				{
				    inSampleSize = Math.round((float)height / (float)reqHeight);
				}
				int expectedWidth = width / inSampleSize;

				if (expectedWidth > reqWidth)
				{
				    //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
				    inSampleSize = Math.round((float)width / (float)reqWidth);
				}

				options.inSampleSize = inSampleSize;

				// Decode bitmap with inSampleSize set
				options.inJustDecodeBounds = false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    Bitmap bmp =  BitmapFactory.decodeFile(path, options);
/*		    Bitmap bmr = null;

			if (bmp !=null) {
			    try {
			    	// String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};
					ExifInterface exif = new ExifInterface(path);
					int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
					if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
						bmr = rotateBitmap(bmp, ExifInterface.ORIENTATION_ROTATE_90);
					else
						bmr = bmp;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
*/
		    return bmp;
		}


		public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

		        try {
					Matrix matrix = new Matrix();
					switch (orientation) {
					    case ExifInterface.ORIENTATION_NORMAL:
					        return bitmap;
					    case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
					        matrix.setScale(-1, 1);
					        break;
					    case ExifInterface.ORIENTATION_ROTATE_180:
					        matrix.setRotate(180);
					        break;
					    case ExifInterface.ORIENTATION_FLIP_VERTICAL:
					        matrix.setRotate(180);
					        matrix.postScale(-1, 1);
					        break;
					    case ExifInterface.ORIENTATION_TRANSPOSE:
					        matrix.setRotate(90);
					        matrix.postScale(-1, 1);
					        break;
					   case ExifInterface.ORIENTATION_ROTATE_90:
					       matrix.setRotate(90);
					       break;
					   case ExifInterface.ORIENTATION_TRANSVERSE:
					       matrix.setRotate(-90);
					       matrix.postScale(-1, 1);
					       break;
					   case ExifInterface.ORIENTATION_ROTATE_270:
					       matrix.setRotate(-90);
					       break;
					   default:
					       return bitmap;

					}
					try {
					    Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
					    bitmap.recycle();
					    return bmRotated;
					}
					catch (OutOfMemoryError e) {
					    e.printStackTrace();
					    return null;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return bitmap;

		}

/*

    CameraReceiver myReceiver;

    public class CameraReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            abortBroadcast();
            Log.d("New Photo Clicked", ">");
            Cursor cursor = context.getContentResolver().query(
                    intent.getData(), null, null, null, null);
            cursor.moveToFirst();
            String image_path = cursor
                    .getString(cursor.getColumnIndex("_data"));
            Toast.makeText(context, "New Photo is Saved as : " + image_path,
                    Toast.LENGTH_SHORT).show();
        }

    }
    */

}	
	
