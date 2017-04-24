package com.ekant.justbiz;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ActivityUpLoadImage extends AppCompatActivity implements ModProfileResponce {
    int flag = 0;
    ImageView banner, logo;
    Button uploadbutton;
    Dialog dialog;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    final Context context = this;
    String bannerstr, iconstr;
    final String imageurl = "http://www.justbusinesses.net/bridge/uploads/company-logos/";
    final String bannerurl = "http://www.justbusinesses.net/bridge/uploads/company-banners/";
    String SERVER = "http://www.justbusinesses.net/bridge/v0/companyprofile.php";
    String uID;
    Moddb db;
    ProgressBar p;
    LinearLayout linearLayoutprogress;
    RelativeLayout relativeLayoutlayoutprogress;
    String x, y, x1, y2;
    Boolean isInternetPresent = false;
    ModConnectionDetector cd;
    SharedPreferences sharedpreferences;
    int width,height;
    String shraredUID;
    String MyPREFERENCES="MyPREFERENCES";
    SharedPreferences.Editor editor;
    TextView heading,editbanner,editlogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");
        heading=(TextView) findViewById(R.id.uploadheadingheading);
        editbanner=(TextView) findViewById(R.id.editbanner);
        editlogo=(TextView) findViewById(R.id.editlogo);




        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String strJson = sharedpreferences.getString("UserInfo", null);//second parameter is necessary ie.,Value to return if this preference does not exist.
        banner = (ImageView) findViewById(R.id.banner_image);
        logo = (ImageView) findViewById(R.id.logo_image);
        uploadbutton = (Button) findViewById(R.id.upload);

        heading.setTypeface(face);
        editbanner.setTypeface(face);
        editlogo.setTypeface(face);
        uploadbutton.setTypeface(face);

        shraredUID = sharedpreferences.getString("UID", null);
        try
        {
            JSONObject jsonObject=new JSONObject(strJson);
            ModImageLoader imageLoader = new ModImageLoader(this);
            imageLoader.logo(imageurl + jsonObject.getString("CompanyLogo"), logo);
            imageLoader.banner(bannerurl + jsonObject.getString("CompanyBanner"), banner);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        db = new Moddb(this);

        linearLayoutprogress=(LinearLayout) findViewById(R.id.llprogress);
        relativeLayoutlayoutprogress=(RelativeLayout) findViewById(R.id.rlprogressbar);
        p = (ProgressBar) findViewById(R.id.progressBarLogin);
        p.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        cd = new ModConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        Display display = getWindowManager().getDefaultDisplay();
         width = display.getWidth();  // deprecated
         height = display.getHeight();  // deprecated
      //  Toast.makeText(ActivityUpLoadImage.this, ""+width+height, Toast.LENGTH_SHORT).show();
    }

    public void updatephotos(View view)
    {


        x = Integer.toString(banner.getWidth());
        y = Integer.toString(banner.getHeight());
        x1 = Integer.toString(logo.getWidth());
        y2 = Integer.toString(logo.getHeight());
        int newwidth=width;
        float f = Float.parseFloat(x);
        float g = Integer.parseInt(y);

        float scaleFactor = (float)newwidth/(float)f;
        int newHeight = (int)(g * scaleFactor);
        Bitmap bannerimage = ((BitmapDrawable) banner.getDrawable()).getBitmap();
        bannerimage = Bitmap.createScaledBitmap(bannerimage, newwidth, newHeight, false);

        Bitmap iconimage = ((BitmapDrawable) logo.getDrawable()).getBitmap();
        iconimage = Bitmap.createScaledBitmap(iconimage, Integer.parseInt(x1), Integer.parseInt(y2), false);

        new Upload(shraredUID, bannerstr, iconstr, bannerimage, iconimage).execute();

    }

    public void selectBanner(View view)
    {
        // flag = 0;
        dialog = new Dialog(context);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Upload Picture");
        dialog.show();
        ImageView galary = (ImageView) dialog.findViewById(R.id.galary);
        galary.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

            }

        });

        ImageView camera = (ImageView) dialog.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();

            }

        });

    }

    public void selectIcon(View view) {
        flag = 1;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);

        //dialog.getWindow().setBackgroundDrawableResource(R.color.lightgreencolor);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("Upload Picture");
        dialog.show();
        ImageView galary = (ImageView) dialog.findViewById(R.id.galary);
        galary.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

            }

        });


        ImageView camera = (ImageView) dialog.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);

            }

        });


        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();

            }

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                Bitmap value = onSelectFromGalleryResult(data);
                if (flag == 0)
                {
                    Uri image = data.getData();
                    bannerstr = getRealPathFromURI(image);
                    banner.setImageBitmap(value);
                    dialog.dismiss();
                }
                else
                {
                    Uri image = data.getData();
                    iconstr = getRealPathFromURI(image);
                    logo.setImageBitmap(value);
                    dialog.dismiss();
                }
            } else if (requestCode == REQUEST_CAMERA) {

                Bitmap value = onCaptureImageResult(data);
                if (flag == 0) {
                    banner.setImageBitmap(value);
                    dialog.dismiss();
                } else {
                    logo.setImageBitmap(value);
                    dialog.dismiss();
                }
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {

        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        s = s.substring(s.lastIndexOf("/") + 1);
        return s;

    }

    public String hashMapToUrl(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }


// Function to Capture image

    private Bitmap onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return thumbnail;
    }


    // Function to set image from Galary
    @SuppressWarnings("deprecation")
    private Bitmap onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String selectedImagePath = cursor.getString(column_index);
        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);
        return bm;

    }

    @Override
    public void onBackPressed() {
        finish();

        Intent intent = new Intent(getApplicationContext(), ActivityMyProfile.class);
        intent.putExtra("Edit", "1");
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
            Intent intent = new Intent(getApplicationContext(), ActivityMyProfile.class);
            intent.putExtra("Edit", "1");
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private class Upload extends AsyncTask<Void, Void, String> {

        private Bitmap bannerimage1, iconimage1;
        private String UID1, bannerstr1, iconstr1;

        public Upload(String UID, String bannerstr, String iconstr, Bitmap bannerimage, Bitmap iconimage) {
            this.UID1 = UID;
            this.bannerstr1 = bannerstr;
            this.iconstr1 = iconstr;
            this.bannerimage1 = bannerimage;
            this.iconimage1 = iconimage;

        }

        @Override
        protected void onPreExecute()

        {
            super.onPreExecute();
            relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
            linearLayoutprogress.setVisibility(View.VISIBLE);
            p.setVisibility(View.VISIBLE);


        }

        @Override
        protected String doInBackground(Void... params)
        {

            ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            //compress the image to jpg format
            bannerimage1.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream1);
            iconimage1.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream2);

            String enbannerimage = Base64.encodeToString(byteArrayOutputStream1.toByteArray(), Base64.DEFAULT);
            String eniconimage = Base64.encodeToString(byteArrayOutputStream2.toByteArray(), Base64.DEFAULT);
            HashMap<String, String> detail = new HashMap<String, String>();
            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss");
            String dateString = sdf.format(date);
            detail.put("UID", UID1);
            detail.put("BannerName", UID1+dateString+"banner.jpg");
            detail.put("LogoName", UID1+dateString+"logo.jpg");
            detail.put("Banner", enbannerimage);
            detail.put("Logo", eniconimage);

            try {
                //convert this HashMap to encodedUrl to send to php file
                String dataToSend = hashMapToUrl(detail);
                String response = Request.post(SERVER, dataToSend);
                return response;


            } catch (Exception e) {
                e.printStackTrace();
                //Log.e(TAG, "ERROR  " + e);
                System.out.println("Error in uploads:  " + e.toString());
                return null;
            }

        }


        @Override
        protected void onPostExecute(String s)
        {
            relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
            linearLayoutprogress.setVisibility(View.INVISIBLE);
            p.setVisibility(View.INVISIBLE);
            finish();
            Toast.makeText(getApplicationContext(), "Thank You. The image has been uploaded.", Toast.LENGTH_SHORT).show();
            getUserdetail("");

        }

    }
    public void getUserdetail(String uid)
    {

        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        shraredUID=sharedpreferences.getString("UID", null);
        System.out.println("shraredUID uploads"+shraredUID);
        if (isInternetPresent)
        {
            JSONObject obj = new JSONObject();
            try
            {
                obj.put("UID",shraredUID );

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            ModgetProfile task1 = new ModgetProfile();
            task1.delegate = this;
            task1.execute(obj.toString(),"member-profile.php");

        }
        else
        {
            cd.showAlertDialog(this, "No Internet Connection", "You don't have internet connection.", false);
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run() {
                    relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
                    linearLayoutprogress.setVisibility(View.INVISIBLE);
                    p.setVisibility(View.INVISIBLE);
                }
            }, 0);
        }
    }
    @Override
    public void getProfile(String output)
    {
        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putString("UserInfo", output);
        editor.commit();

        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String strJson = sharedpreferences.getString("UserInfo", null);
        Intent editprofile = new Intent(ActivityUpLoadImage.this, ActivityMyProfile.class);
        startActivity(editprofile);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

}



























































