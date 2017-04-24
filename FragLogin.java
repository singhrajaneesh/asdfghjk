package com.ekant.justbiz;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragLogin extends Fragment  implements  ModAsyncResponce,View.OnClickListener,ModProfileResponce
{

    EditText password, email;
    TextView textView,headermsg;
    Button btn;
    String Email,Password,loginValue ;
    Moddb db;
    ProgressBar progressBar;
    ImageView imageView;
    Boolean isInternetPresent = false;
    ModConnectionDetector cd;
    String value;
    String MyPREFERENCES="MyPREFERENCES";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String uID = "";
    String username = "";
    int profilecomplete=0;
    LinearLayout linearLayoutprogress;
    RelativeLayout relativeLayoutlayoutprogress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.frag_login, container, false);

        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "normal_futura.ttf");

        email = (EditText) view.findViewById(R.id.etUserName);
        password = (EditText) view.findViewById(R.id.etPass);
        textView= (TextView) view.findViewById(R.id.tv);
        headermsg=(TextView) view.findViewById(R.id.textviewmsglogin);
        email.setTypeface(face);
       // password.setTypeface(face);
        textView.setTypeface(face);
        headermsg.setTypeface(face);
        db = new Moddb(getActivity());
        imageView=(ImageView) view.findViewById(R.id.alertimage);
        linearLayoutprogress=(LinearLayout) view.findViewById(R.id.llprogress);
        relativeLayoutlayoutprogress=(RelativeLayout) view.findViewById(R.id.rlprogressbar);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBarLogin);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        cd = new ModConnectionDetector(getActivity().getApplicationContext());


        int value = db.getCount("login");
        if(value == 1)
        {
            int profilecomplete =0;
            if(db.getValue("login").equals("1"))
            {
                getActivity().finish();
                sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                String strJson = sharedpreferences.getString("UserInfo", null);
                try
                {
                    JSONObject jsonObject = new JSONObject(strJson);
                    profilecomplete = Integer.parseInt(jsonObject.getString("CompleteLevel"));
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                if(profilecomplete ==5)
                {
                    Intent intent = new Intent(getActivity().getApplicationContext(), ActivityDashBoard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                else
                {
                    Intent intent = new Intent(getActivity().getApplicationContext(), ActivityMyProfile.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }


            }
        }
        btn = (Button) view.findViewById(R.id.btnlogin);
        btn.setOnClickListener((View.OnClickListener) this);

        btn.setTypeface(face);
        return view;

    }
    // "LOGIN" button click in first frag_login screen
    @Override
    public void onClick(View v)
    {
        Email=email.getText().toString();
        Password=password.getText().toString();
        if(email.getText().toString().equals("") && password.getText().toString().equals(""))
        {
            textView.setText("  Please fill required fields.");
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
        else if(email.getText().toString().equals(""))
        {
            textView.setText("  Please fill Email ID correct format.");
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }
        else if (!isValidEmail(Email))
        {
            textView.setText("  Please fill Email ID in correct format.");
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }
        else if (password.getText().toString().equals(""))
        {
            textView.setText("  Please fill the password.");
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }
        else
        {
            senddatatoweb(Email, Password);

            relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
            linearLayoutprogress.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    // Function to check user is exist or not
    private void senddatatoweb(final String Email, String Password)
    {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent)
        {
            JSONObject obj = new JSONObject();
            try
            {
                obj.put("Email",Email );
                obj.put("Password",Password );
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute(obj.toString(), "check-member.php");
        }
        else
        {
            cd.showAlertDialog(getActivity(), "No Internet Connection", "You don't have internet connection.", false);
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
                    linearLayoutprogress.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
         }, 0);
        }

    }
    public void processFinish(String output)
    {
        relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
        linearLayoutprogress.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        if (output != null)
        {
            loginValue = "";

            String loginResult = output;
            progressBar.setVisibility(View.INVISIBLE);

            try
            {
                JSONObject jsonObj = new JSONObject(loginResult);
                loginValue = jsonObj.getString("LoginCredential");
                uID = jsonObj.getString("UID");
                username = jsonObj.getString("MemberName");

            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            getUserdetail(uID);

        }


        else
        {
            Toast.makeText(getActivity(), "Error Occured can't fetch data", Toast.LENGTH_LONG).show();
        }
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
            task1.execute(obj.toString(), "member-profile.php");

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
        editor.putString("UID", uID);
        editor.commit();
        if (loginValue.equals("1"))
            {
                //getUserdetail(uID);
                getActivity().finish();
                Intent intent = new Intent(getActivity().getApplicationContext(), ActivityDashBoard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                int value = db.getCount("login");
                if( db.getCount("profilecomplete")== 0)
                {
                    db.insertLogin("profilecomplete", "4");
                }
                if (value == 0)
                {
                    db.insertLogin("login", loginValue);
                    db.insertLogin("UID", uID);
                    db.insertLogin("MemberName", username);
                }
                else
                {
                    db.updateValue("login", "1");
                    db.updateValue("UID", uID);
                    db.updateValue("MemberName", uID);
                }

            }
            else if (loginValue.equals("2"))
            {
                textView.setText("Your Email ID is not verified. Please check \nyour email verification link.");
                textView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                db = new Moddb(getActivity());
            }
            else if (loginValue.equals("0"))
            {
                textView.setText("This Email ID is not registered with us.\n sign up if not already done.");
                textView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                db = new Moddb(getActivity());
            }
            else if (loginValue.equals("3"))
            {
                textView.setText("This Email ID or Password is incorrect.");
                textView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                db = new Moddb(getActivity());
            }
            else if (loginValue.equals("4"))
            {
                int value = db.getCount("login");
                if (value == 0)
                {
                    db.insertLogin("login", loginValue);
                    db.insertLogin("UID", uID);
                    db.insertLogin("MemberName", username);

                }
                else
                {
                    db.updateValue("login", "1");
                    db.updateValue("UID", uID);
                    db.updateValue("MemberName", uID);

                }

                int profilecomplete = db.getCount("profilecomplete");
                if (profilecomplete == 0)
                {

                    db.insertLogin("profilecomplete", "0");

                }

                //getUserdetail(uID);
                Intent intent = new Intent(getActivity().getApplicationContext(), ActivityMyProfile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                email.setText("");
                password.setText("");
            }

            else
            {
                Toast.makeText(getActivity(), "There was a problem connecting to server.", Toast.LENGTH_LONG).show();
            }
    }

    // Email validations...
    private boolean isValidEmail(String Email)
    {

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(Email);
        return matcher.matches();

    }

}

