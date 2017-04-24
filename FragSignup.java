package com.ekant.justbiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FragSignup extends Fragment implements  ModAsyncResponce,OnClickListener
{
    EditText fname, lname, email, password;
    Button btn;
    TextView textView,errortext,headermsg;
    CheckBox checkBox;
    ProgressBar progressBar;
    String Firstname,Lastname,Email,Password;
    ImageView imageView;
    Boolean isInternetPresent = false;
    ModConnectionDetector cd;
    LinearLayout linearLayoutprogress;
    RelativeLayout relativeLayoutlayoutprogress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.frag_signup, container, false);
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "normal_futura.ttf");

        headermsg=(TextView) view.findViewById(R.id.textviewmsgsignup);
        errortext = (TextView) view.findViewById(R.id.errortext);
        fname = (EditText) view.findViewById(R.id.firstname);
        lname = (EditText) view.findViewById(R.id.lastname);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        btn = (Button) view.findViewById(R.id.btnregister);
        imageView=(ImageView) view.findViewById(R.id.alertimage);
        btn.setOnClickListener((View.OnClickListener) this);
        textView=(TextView)view.findViewById(R.id.textview);

        headermsg.setTypeface(face);
        errortext.setTypeface(face);
        fname.setTypeface(face);
        lname.setTypeface(face);
        email.setTypeface(face);
       // password.setTypeface(face);
        checkBox.setTypeface(face);
        btn.setTypeface(face);
        textView.setTypeface(face);


        linearLayoutprogress=(LinearLayout) view.findViewById(R.id.llprogress);
        relativeLayoutlayoutprogress=(RelativeLayout) view.findViewById(R.id.rlprogressbar);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBarLogin);
        cd = new ModConnectionDetector(getActivity().getApplicationContext());


        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
           public void onCheckedChanged(CompoundButton arg0, boolean arg1)
            {
                // You don't need to use onCheckChangeListener.


            }
        });
        return view;
    }


    // button click
    @Override
    public void onClick(View v)
    {
        Firstname= fname.getText().toString().trim();
        Lastname= lname.getText().toString().trim();
        Email=email.getText().toString().trim();
        Password=password.getText().toString().trim();

        if(fname.getText().toString().equals("") && lname.getText().toString().equals("") && email.getText().toString().equals("") && password.getText().toString().equals("")) {
            errortext.setText("  Please fill all required fields.");
            imageView.setVisibility(View.VISIBLE);
            errortext.setVisibility(View.VISIBLE);
        }
        else   if (fname.getText().toString().equals("") || fname.length()==0 || fname==null )
        {
            errortext.setText(" Please fill your first name.");
            imageView.setVisibility(View.VISIBLE);
            errortext.setVisibility(View.VISIBLE);
        }
        else   if (lname.getText().toString().equals(""))
        {
            errortext.setText("  Please fill your last name.");
            imageView.setVisibility(View.VISIBLE);
            errortext.setVisibility(View.VISIBLE);
        }
        else  if (!isValidEmail(Email))
        {
                errortext.setText("  Please fill the Email ID in correct format.");
            imageView.setVisibility(View.VISIBLE);
            errortext.setVisibility(View.VISIBLE);
        }
        else if (password.getText().toString().equals(""))
        {
            errortext.setText("  Please fill the password.");
            imageView.setVisibility(View.VISIBLE);
            errortext.setVisibility(View.VISIBLE);
        }
        else
        {
            if (checkBox.isChecked() == false) {
                errortext.setText("  You must accept and agree \n to our Terms and Conditions.");
                imageView.setVisibility(View.VISIBLE);
                errortext.setVisibility(View.VISIBLE);
            }
            else
            {
                senddatatoweb(Firstname, Lastname, Email, Password);

                relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
                linearLayoutprogress.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    private void senddatatoweb(String Firstname, String Lastname, String Email, String Password)
    {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent)
        {
            relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
            linearLayoutprogress.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            JSONObject obj = new JSONObject();
            try
            {

                obj.put("FirstName", Firstname);
                obj.put("LastName", Lastname);
                obj.put("Email", Email);
                obj.put("Password", Password);

            } catch (Exception e)
            {
                e.printStackTrace();
            }

            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute(obj.toString(), "member-signup.php");

        } else {
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
            String loginValue = "";
            String loginResult = output;

            try
            {
                JSONObject jsonObj = new JSONObject(loginResult);
                loginValue = jsonObj.getString("SignupCredential");

                if (loginValue.equals("1"))
                {

                    Intent intent = new Intent(getActivity(), ActivitySignUpClick.class);
                    startActivity(intent);
                    getActivity(). overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    fname.setText("");
                    lname.setText("");
                    email.setText("");
                    password.setText("");

                }
                else
                {

                    errortext.setText("This Email ID is already registered with us.\n Please log in or retrieve your password \n if you have forgotten it.");
                    imageView.setVisibility(View.VISIBLE);
                    errortext.setVisibility(View.VISIBLE);
                    password.setText("");

                }
            }

            catch (JSONException e)

            {
                e.printStackTrace();
            }

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


















































