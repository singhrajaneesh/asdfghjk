package com.ekant.justbiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class FragPersonalInfo extends Fragment implements ModAsyncResponce,View.OnClickListener,ModProfileResponce {

    Button button;
    EditText fname, lname, email, mobile;
    String UID;
    Boolean isInternetPresent = false;
    ModConnectionDetector connectionDetector;
    ProgressBar progressBar;
    Moddb db;
    String MyPREFERENCES="MyPREFERENCES";
    ModConnectionDetector cd;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    LinearLayout linearLayoutprogress;
    RelativeLayout relativeLayoutlayoutprogress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {
        View view = inflater.inflate(R.layout.frag_personal_information, container, false);
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "normal_futura.ttf");

        connectionDetector = new ModConnectionDetector(getActivity().getApplicationContext());
        fname = (EditText) view.findViewById(R.id.fname12);
        lname = (EditText) view.findViewById(R.id.lname12);
        email = (EditText) view.findViewById(R.id.email12);
        mobile = (EditText) view.findViewById(R.id.mobile12);


        db = new Moddb(getActivity());
        cd = new ModConnectionDetector(getActivity().getApplicationContext());
        UID = db.getValue("UID");
        linearLayoutprogress=(LinearLayout) view.findViewById(R.id.llprogress);
        relativeLayoutlayoutprogress=(RelativeLayout) view.findViewById(R.id.rlprogressbar);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBarLogin);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        button=(Button) view.findViewById(R.id.save);
        button.setOnClickListener((View.OnClickListener) this);

        fname.setTypeface(face);
        lname.setTypeface(face);
        email.setTypeface(face);
        mobile.setTypeface(face);


        button.setTypeface(face);
       email.setOnClickListener(this);
        email.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                // Setting Dialog Title
                alertDialog.setTitle("Read-only Field");
                // Setting Dialog Message
                alertDialog.setMessage("This is your primary Email ID and used for login.\n This field is not editable.");
                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.cancel);
                // Setting OK Button
                alertDialog.setButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Write your code here to execute after dialog closed
                        dialog.cancel();

                    }
                });
                // Showing Alert Message
                alertDialog.show();
            }

        });

        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String strJson = sharedpreferences.getString("UserInfo", null);//second parameter is necessary ie.,Value to return if this preference does not exist.

        try {
            JSONObject   jsonObj = new JSONObject(strJson);
            fname.setText(jsonObj.getString("FName"));
            lname.setText(jsonObj.getString("LName"));
            email.setText(jsonObj.getString("LoginEmail"));
            mobile.setText(jsonObj.getString("Mobile"));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return view;

    }
    @Override
    public void onClick(View v)
    {

         if (mobile.getText().toString().equals(""))
        {

        Toast.makeText(getActivity(), "Please fill your mobile no.", Toast.LENGTH_SHORT).show();
         }



    else if (mobile.getText().toString().length()!=10)
        {

        Toast.makeText(getActivity(), "Mobile no. must be 10 digits long.", Toast.LENGTH_SHORT).show();
             }

        else
         {
            sendDataToWeb();

        }


    }

    public void sendDataToWeb()
    {

        isInternetPresent = connectionDetector.isConnectingToInternet();
        if (isInternetPresent)
        {
            JSONObject obj = new JSONObject();
            relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
            linearLayoutprogress.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);

            try
            {
                obj.put("UID", UID);
                obj.put("FName", fname.getText().toString());
                obj.put("LName", lname.getText().toString());
               // obj.put("Email", email.getText().toString());
                obj.put("Mobile", mobile.getText().toString());
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute(obj.toString(), "updatepersonalprofile.php");
        }
        else
        {
            connectionDetector.showAlertDialog(getActivity(), "No Internet Connection", "You don't have internet connection.", false);
            new Handler().postDelayed(new Runnable()
                                      {
                @Override
                public void run()
                {
                    relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
                    linearLayoutprogress.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }

            },
                    0);
        }

    }
    @Override
    public void processFinish(String output)
    {
        relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
        linearLayoutprogress.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        getUserdetail(UID);

    }
    public void getUserdetail(String uid)
    {
        if (isInternetPresent)
        {
            JSONObject obj = new JSONObject();
            try
            {
                obj.put("UID",uid );

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
            cd.showAlertDialog(getActivity(), "No Internet Connection", "You don't have internet connection.", false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }, 0);
        }
    }
    public void getProfile(String output)
    {

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putString("UserInfo", output);
        editor.commit();
        getActivity().finish();
        Intent intent = new Intent(getActivity(), ActivityMyProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

}
