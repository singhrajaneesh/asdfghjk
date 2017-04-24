package com.ekant.justbiz;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONObject;

public class ActivitySplashScreen extends AppCompatActivity implements ModAsyncResponce

{
    /**
     * Duration of wait
     **/
    private final int SPLASH_SCREEN_DELAY_TIME = 1000;//4000(ms)
    Moddb db;
    String value;
    String manufacturer, model, brand, screenresolution, dpilevel, osversion, versionCode, version, serialno, phoneid, hostid, deviceid;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 10000;
    static final String TAG = "Phone inforamtion send";
    PackageInfo pInfo = null;
    Boolean isInternetPresent = false;
    ModConnectionDetector cd;
    ProgressBar progressBar;
    String inter;
    String imei, countryCode, OperatorName, getSimSerialNumber, possibleEmail;
    int verCode;
    TextView appLogo,versiondispaly;
    Typeface face;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        db = new Moddb(this);
        value = db.getValue("Guide");
        cd = new ModConnectionDetector(getApplicationContext());
        face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");
        appLogo=(TextView) findViewById(R.id.appmsg);

        versiondispaly=(TextView) findViewById(R.id.version);




        appLogo.setTypeface(face);
        versiondispaly.setTypeface(face);


       // Typeface face= Typeface.createFromAsset(getAssets(),"raja.ttf");
       // appLogo.setTypeface(face);

        TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        getSimSerialNumber = telemamanger.getSimSerialNumber();
        String getSimNumber = telemamanger.getLine1Number();
        TelephonyManager tManager = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        countryCode = telemamanger.getSimCountryIso();
        // Get carrier name (Network Operator Name)
        OperatorName = tManager.getNetworkOperatorName();
        telemamanger.getDeviceId();
        imei = telemamanger.getDeviceId();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
     //   String myEmailid = accounts[0].toString();
        for (Account account : accounts)
        {
            possibleEmail = account.toString();
        }

        try
        {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        }   catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBarsplashscreen);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"), android.graphics.PorterDuff.Mode.SRC_ATOP);

        if (checkPlayServices())
        {
            manufacturer = android.os.Build.MANUFACTURER;
            model = android.os.Build.MODEL;
            brand = android.os.Build.BRAND;
            osversion = Build.VERSION.RELEASE;
            screenresolution = String.valueOf(width) + "*" + String.valueOf(height);
            version = pInfo.versionName;
            serialno = Build.DEVICE;
            phoneid = Build.ID;
            hostid = Build.HOST;
            deviceid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            verCode = pInfo.versionCode;
            versionCode = String.valueOf(verCode);

            switch (metrics.densityDpi)
            {
                case DisplayMetrics.DENSITY_LOW:
                    dpilevel = "LDPI";
                    break;
                case DisplayMetrics.DENSITY_MEDIUM:
                    dpilevel = "MDPI";
                    break;
                case DisplayMetrics.DENSITY_HIGH:
                    dpilevel = "HDPI";
                    break;
                case DisplayMetrics.DENSITY_XHIGH:
                    dpilevel = "XHDPI";
                    break;
                case DisplayMetrics.DENSITY_XXHIGH:
                    dpilevel = "XXHDPI";
                    break;
                case DisplayMetrics.DENSITY_XXXHIGH:
                    dpilevel = "XXXHDPI";
                    break;
                default:
                    dpilevel = "XXXXHDPI";

            }

            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent)
            {
                sendDatatoweb(version, versionCode, manufacturer, model, brand, osversion, screenresolution,
                        dpilevel, hostid, deviceid, getSimSerialNumber, OperatorName, countryCode,
                        imei, possibleEmail);

                progressBar.setVisibility(View.VISIBLE);


            }
            else
            {
                Toast.makeText(ActivitySplashScreen.this, "Oops! You don't have internet connections.", Toast.LENGTH_LONG).show();

            }


        }
        else
        {
            Toast.makeText(ActivitySplashScreen.this, "Don't have google play services.", Toast.LENGTH_SHORT).show();

        }


    }


    private void sendDatatoweb(String version,String versionCode,String manufacturer,String model,
                               String brand,String osversion,String screenresolution,String dpilevel,
                               String hostid,String deviceid,String getSimSerialNumber,String OperatorName,
                               String countryCode,String imei,String EmailID)
    {

        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent)
        {
            progressBar.setVisibility(View.VISIBLE);
            JSONObject json = new JSONObject();
            try
            {
                json.put("VERSION", version);
                json.put("VERSIONCODE",versionCode );
                json.put("MANUFACTURER", manufacturer);
                json.put("MODEL", model);
                json.put("BRAND", brand);
                json.put("OSVERSION", osversion);
                json.put("SCREENRESOLUTION", screenresolution);
                json.put("DPILEVEL", dpilevel);
                json.put("HOSTID", hostid);
                json.put("DEVICEID", deviceid);
                json.put("PHONESERIALNUMBER", getSimSerialNumber);
                json.put("OPERATORNAME", OperatorName);
                json.put("COUNTRYCODE", countryCode);
                json.put("IMEI", imei);
                json.put("EMAILID", EmailID);

            }

            catch (Exception e)
            {

                e.printStackTrace();
            }
            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute(json.toString(), "deviceregister.php");



        }
        else
        {
          //  cd.showAlertDialog(getApplicationContext(), "No Internet Connection", "You don't have internet connection.", false);
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }, 0);
        }
    }

    public void processFinish(String output)
    {

        isInternetPresent = cd.isConnectingToInternet();
        progressBar.setVisibility(View.INVISIBLE);

        if (output==null)
        {
            Toast.makeText(ActivitySplashScreen.this, "App under maintenance.", Toast.LENGTH_LONG).show();
        }

        else {
            if (isInternetPresent) {


                try {
                    JSONObject jsonObject = new JSONObject(output);
                    String receivedvalue = jsonObject.getString("Action");

                    String versionreceived=jsonObject.getString("Version");
                    versiondispaly.setText("Version  "+versionreceived);

                    System.out.println("versionreceived"+versionreceived+"receivedvalue"+receivedvalue);


                    switch (receivedvalue)
                    {
                        case "AppApproved":
                         //   Toast.makeText(ActivitySplashScreen.this, "App Approved", Toast.LENGTH_SHORT).show();

//
//                    AlertDialog.Builder builder12 = new AlertDialog.Builder(this);
//                    builder12.setMessage("Do you want to update this app ?")
//                            .setCancelable(false)
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                            {
//                                public void onClick(DialogInterface dialog, int id) {
//
//                                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
//                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    i.setData(Uri.parse("https://play.google.com/store/apps"));
//                                    startActivity(i);
//                                }
//                            })
//                            .setNegativeButton("No", new DialogInterface.OnClickListener()
//                            {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    //  Action for 'NO' Button
//                                    dialog.cancel();
//                                    finish();
//
//                                }
//                            });
//
//                    //Creating dialog box
//                   AlertDialog alert12 = builder12.create();
//                    //Setting the title manually
//                    alert12.setTitle("Update App");
//                    alert12.show();

//
                            if (value.equals("1"))
                            {
                                Intent mainIntent = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                finish();
                            }
                            else

                            {
                                Intent mainIntent = new Intent(ActivitySplashScreen.this, ActivityGuide.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                finish();
                            }






                            // uncomment here


//
//                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivitySplashScreen.this);
//
//                            alertDialogBuilder.setTitle(this.getTitle());
//
//                            alertDialogBuilder.setMessage("Do you want to update this app ?");
//                            // set positive button: Yes message
//
//                            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                            {
//
//                                public void onClick(DialogInterface dialog, int id)
//                                {
//
//                                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
//                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    i.setData(Uri.parse("https://play.google.com/store/apps"));
//                                    startActivity(i);
//
//
//
//                                }
//
//                            });
//
//                            // set negative button: No message
//
//                            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//
//                                public void onClick(DialogInterface dialog, int id) {
//
//                                    // cancel the alert box and put a Toast to the user
//
//                                    dialog.cancel();
//                                   finish();
//
//                                }
//
//                            });
//
//                            // set neutral button: Exit the app message
//
//                            alertDialogBuilder.setNeutralButton("Later",new DialogInterface.OnClickListener() {
//
//                                public void onClick(DialogInterface dialog,int id) {
//
//
//                            if (value.equals("1"))
//                            {
//                                Intent mainIntent = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
//                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(mainIntent);
//                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                                finish();
//                            }
//                            else
//
//                            {
//                                Intent mainIntent = new Intent(ActivitySplashScreen.this, ActivityGuide.class);
//                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(mainIntent);
//                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                                finish();
//                            }
//
//                                }
//
//                            });
//                            AlertDialog alertDialog = alertDialogBuilder.create();
//                            alertDialog.show();

                    break;
                        case "Update":


                            if (value.equals("1"))
                            {
                                Intent mainIntent = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                finish();
                            }
                            else
                            {
                                Intent mainIntent = new Intent(ActivitySplashScreen.this, ActivityGuide.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                finish();
                            }

                            break;

                        case "Available":

                            //  Toast.makeText(ActivitySplashScreen.this, "Please update your App for better performance!", Toast.LENGTH_LONG).show();

                            if (value.equals("1")) {
                                Intent mainIntent = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                finish();
                            }
                            else
                            {
                                Intent mainIntent = new Intent(ActivitySplashScreen.this, ActivityGuide.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                finish();
                            }

                            break;

                        case "Updatemandatory":


                            if (value.equals("1"))
                            {
                                Intent mainIntent = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                finish();
                            }
                            else
                            {
                                Intent mainIntent = new Intent(ActivitySplashScreen.this, ActivityGuide.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                finish();
                            }



//                    AlertDialog.Builder builder12 = new AlertDialog.Builder(this);
//                    builder12.setMessage("Do you want to update this app ?")
//                            .setCancelable(false)
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                            {
//                                public void onClick(DialogInterface dialog, int id) {
//
//                                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
//                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    i.setData(Uri.parse("https://play.google.com/store/apps/details?id=my packagename "));
//                                    startActivity(i);
//                                }
//                            })
//                            .setNegativeButton("No", new DialogInterface.OnClickListener()
//                            {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    //  Action for 'NO' Button
//                                    dialog.cancel();
//                                    finish();
//
//                                }
//                            });
//
//                    //Creating dialog box
//                   AlertDialog alert12 = builder12.create();
//                    //Setting the title manually
//                    alert12.setTitle("Update App");
//                    alert12.show();
                            break;


                        case "Countdowntimer":

                            if (value.equals("1")) {
                                Intent mainIntent = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                finish();
                            } else {
                                Intent mainIntent = new Intent(ActivitySplashScreen.this, ActivityGuide.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                finish();
                            }

                            break;

                        case "Dashboard":

                            if (value.equals("1")) {
                                Intent mainIntent = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                finish();
                            } else {
                                Intent mainIntent = new Intent(ActivitySplashScreen.this, ActivityGuide.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                finish();
                            }
                            break;
                        default:

                            if (value.equals("1")) {
                                Intent mainIntent = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                finish();
                            } else {
                                Intent mainIntent = new Intent(ActivitySplashScreen.this, ActivityGuide.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                finish();
                            }
                    }


                } catch (Exception e) {

                    e.printStackTrace();


                }

            }

        else {
            System.out.println("inside Else   "+output);
                Toast.makeText(ActivitySplashScreen.this, "Oops! something happened wrong to server side.", Toast.LENGTH_SHORT).show();

        }}

    }

    private boolean checkPlayServices()
    {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else
            {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        finish();
        super.onBackPressed();
    }
}
