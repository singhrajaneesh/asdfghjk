package com.ekant.justbiz;

import android.content.Intent;
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

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragForgotPassword extends Fragment  implements  ModAsyncResponce, View.OnClickListener
{
    EditText email;
    Button button;
    TextView textView,headermsg;
    String email1;
    ImageView imageView;
    ProgressBar progressBar;
    Boolean isInternetPresent = false;
    ModConnectionDetector cd;
    LinearLayout linearLayoutprogress;
    RelativeLayout relativeLayoutlayoutprogress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {
        View view = inflater.inflate(R.layout.frag_forgot_password, container, false);


        headermsg=(TextView) view.findViewById(R.id.textviewmsgforgotpassword);

        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "normal_futura.ttf");
        headermsg.setTypeface(face);

        textView = (TextView) view.findViewById(R.id.errortext);
        email=(EditText) view.findViewById(R.id.email);
        button=(Button) view.findViewById(R.id.btnsend);
        button.setOnClickListener((View.OnClickListener) this);

        textView.setTypeface(face);
        email.setTypeface(face);
        button.setTypeface(face);


        imageView=(ImageView) view.findViewById(R.id.alertimage);
        linearLayoutprogress=(LinearLayout) view.findViewById(R.id.llprogress);
        relativeLayoutlayoutprogress=(RelativeLayout) view.findViewById(R.id.rlprogressbar);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBarLogin);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        cd = new ModConnectionDetector(getActivity().getApplicationContext());
        return view;
    }

    // Button click
    @Override
    public void onClick(View v)
    {

        email1=email.getText().toString();

        if(email.getText().toString().equals(""))
        {
            textView.setText("  Please fill your Email ID.");
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
        else  if (!isValidEmail(email1))
        {
            textView.setText("  Please fill Email ID in correct format.");
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);

        }
        else
        {
            senddatatoweb(email1);
            relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
            linearLayoutprogress.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    // Send data to josn on Button click
    private void senddatatoweb(String email1)
    {

        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent)
        {

            JSONObject obj = new JSONObject();
            try
            {

                obj.put("Email", email1);


            } catch (Exception e)
            {
                e.printStackTrace();
            }

            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute(obj.toString(), "forgot-password.php");


        }
        else
        {
            cd.showAlertDialog(getActivity(), "No Internet Connection", "You don't have internet connection.", false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
                    linearLayoutprogress.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }, 0);
        }

    }

    @Override
    public void processFinish(String output)
    {


        relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
        linearLayoutprogress.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        if (output != null)
        {

            String loginValue = "";
            String loginvalue1="";
            String loginResult = output;


            try
            {
                JSONObject jsonObj = new JSONObject(loginResult);
                loginValue = jsonObj.getString("ForgotPasswordCredential");
                loginvalue1=jsonObj.getString("UID");


            if (loginValue.equals("1"))
                {
                    Intent intent = new Intent(getActivity(), ActivityForgotEmail.class);
                    startActivity(intent);
                    getActivity(). overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    email.setText("");
                }
                else if (loginValue.equals("2") )

                {
                    Intent intent=new Intent(getActivity(),ActivityResendEmail.class);
                    intent.putExtra("UID",loginvalue1);
                    startActivity(intent);
                    getActivity(). overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    email.setText("");
                }




            }
            catch (Exception e)
            {
                e.printStackTrace();
                textView.setText(" This Email ID is not registered with us.\n sign up if not already done.");
                imageView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);

            }


        }




    }


    // Email validations...
    private boolean isValidEmail(String email1)
    {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email1);
        return matcher.matches();
    }


}
