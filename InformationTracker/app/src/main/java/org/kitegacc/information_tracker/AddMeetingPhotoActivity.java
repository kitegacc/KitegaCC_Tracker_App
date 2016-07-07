package org.kitegacc.information_tracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by philip on 7/6/16.
 */
public class AddMeetingPhotoActivity extends Activity {
    private int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private Button photoButton;
    private Button galleryButton;
    private Button uploadButton;

    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath = "";
    public String meeting_id = "";
    public String image_str;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        meeting_id = getIntent().getExtras().getString("meeting_id");

        this.imageView = (ImageView) this.findViewById(R.id.imageView1);

        photoButton = (Button) this.findViewById(R.id.take_photo_button);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                // startActivityForResult(cameraIntent, CAMERA_REQUEST);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            }
        });

        // galleryButton = (Button) this.findViewById(R.id.choose_photo_button);
        // galleryButton.setEnabled(false);

        uploadButton = (Button) this.findViewById(R.id.upload_photo_button);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            // Bitmap photo = (Bitmap) data.getExtras().get("data");
            // imageView.setImageBitmap(photo);

            try {
                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                imageView.setImageBitmap(mImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            uploadButton.setEnabled(true);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public void uploadPhoto() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mImageBitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
        byte [] byte_arr = stream.toByteArray();
        image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
        new FormPoster().execute();
    }

    class FormPoster extends AsyncTask<Void, Void, Boolean> {

        private ProgressDialog pDialog;

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddMeetingPhotoActivity.this);
            pDialog.setMessage("Setting photo. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        @Override
        protected Boolean doInBackground(Void... params) {
            // Building Parameters
//            HashMap<String, String> params = new HashMap<>();
//            params.put("community_id", Integer.toString(COMMUNITY_ID));
//            // getting JSON string from URL
            String url_add_photo = "http://androidapp.kitegacc.org/add_photo.php";
            JSONParserHTTPRequest jphtr = new JSONParserHTTPRequest();
            HashMap<String, String> QUERY_ARGS = new HashMap<>();
            QUERY_ARGS.put("meeting_id", meeting_id);
            QUERY_ARGS.put("image", image_str);
            JSONObject json = jphtr.makeHttpRequest(url_add_photo, "GET", QUERY_ARGS);

            // Check your log cat for JSON response
            Log.d("All Products: ", json.toString());

            try {
                int success = json.getInt("success");
                if(success == 1) {
                    return true;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(final Boolean success) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            if(success) {
                Toast.makeText(AddMeetingPhotoActivity.this, "Successfully setting photo.", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(AddMeetingPhotoActivity.this, "Error setting photo.", Toast.LENGTH_LONG).show();
            }

        }

    }
}
