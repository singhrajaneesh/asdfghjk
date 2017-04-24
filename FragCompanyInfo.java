package com.ekant.justbiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ekant on 02/03/16.
 */
public class FragCompanyInfo extends Fragment implements View.OnClickListener,ModAsyncResponce,ModProfileResponce
{

    EditText companyname, address, address2, city, pincode,state,country,websites,phone,fax,email,aboutus;
    Button button;
    String UID;
    Boolean isInternetPresent = false;
    ModConnectionDetector connectionDetector;
    Moddb db;
    ViewPager viewPager;
    ProgressBar progressBar;
    JSONObject obj;
    String companyType,companySize;
    Spinner spinner1, spinner;
    int position;
    ModConnectionDetector cd;
    String MyPREFERENCES="MyPREFERENCES";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String [] company_Type = {"Company Type","Individual","Proprietorship","Partnership","Pvt.Ltd.","Ltd.","Others"};
    String [] companysize = {"Company Size","1-9","10-25","26-50","50-100","100+"};
    LinearLayout linearLayoutprogress;
    RelativeLayout relativeLayoutlayoutprogress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {
        View view = inflater.inflate(R.layout.frag_company_info, container, false);

        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "normal_futura.ttf");
        companyname = (EditText) view.findViewById(R.id.companyname10);
        address = (EditText) view.findViewById(R.id.address33);
        address2 = (EditText) view.findViewById(R.id.address34);
        city = (EditText) view.findViewById(R.id.city);
        pincode = (EditText) view.findViewById(R.id.pincode12);
        state = (EditText) view.findViewById(R.id.state);
        country = (EditText) view.findViewById(R.id.country);
        websites = (EditText) view.findViewById(R.id.website12);
        phone = (EditText) view.findViewById(R.id.phone222);
        fax = (EditText) view.findViewById(R.id.fax);
        email = (EditText) view.findViewById(R.id.email);
        aboutus = (EditText) view.findViewById(R.id.edit_aboutcompany);
        connectionDetector = new ModConnectionDetector(getActivity().getApplicationContext());
        button=(Button) view.findViewById(R.id.next);
        db = new Moddb(getActivity());
        cd = new ModConnectionDetector(getActivity().getApplicationContext());
        UID = db.getValue("UID");


        spinner = (Spinner) view.findViewById(R.id.static_spinner);
        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, company_Type);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(LTRadapter);


        spinner1 = (Spinner) view.findViewById(R.id.dynamic_spinner);
        ArrayAdapter<String> LTRadapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, companysize);
        LTRadapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(LTRadapter1);

        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String strJson = sharedpreferences.getString("UserInfo", null);//second parameter is necessary ie.,Value to return if this preference does not exist.

        try {
            JSONObject   jsonObj = new JSONObject(strJson);
            companyname.setText(jsonObj.getString("CompanyName"));
            address.setText(jsonObj.getString("Address1"));
            address2.setText(jsonObj.getString("Address2"));
            city.setText(jsonObj.getString("City"));
            pincode.setText(jsonObj.getString("Pincode"));
            state.setText(jsonObj.getString("State"));
            country.setText(jsonObj.getString("Country"));
            websites.setText(jsonObj.getString("Website"));
            phone.setText(jsonObj.getString("OfficeNo"));
            fax.setText(jsonObj.getString("Fax"));
            email.setText(jsonObj.getString("CompanyEmail"));
            aboutus.setText(jsonObj.getString("CompanyDescription"));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        linearLayoutprogress=(LinearLayout) view.findViewById(R.id.llprogress);
        relativeLayoutlayoutprogress=(RelativeLayout) view.findViewById(R.id.rlprogressbar);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarLogin);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (parent.getItemAtPosition(position).toString().equals("Company Type"))
                {

                    System.out.println("This is hint for Company Type.");

                }
                else
                {
                    companyType = parent.getItemAtPosition(position).toString();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

                Toast.makeText(getActivity(), "Nothing selected", Toast.LENGTH_SHORT).show();

            }
        }
        );
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(parent.getItemAtPosition(position).toString().equals("Company Size"))
                {

                    System.out.println("This is hint for Company Size.");
                }

                else
                {
                    companySize=parent.getItemAtPosition(position).toString();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        }
        );

        button.setOnClickListener((View.OnClickListener) this);
        //staticSpinner.setOnItemSelectedListener(this);


        companyname.setTypeface(face);
        address.setTypeface(face);
        address2.setTypeface(face);
        city.setTypeface(face);
        pincode.setTypeface(face);
        state.setTypeface(face);
        country.setTypeface(face);
        websites.setTypeface(face);
        phone.setTypeface(face);
        fax.setTypeface(face);
        email.setTypeface(face);
        aboutus.setTypeface(face);
        button.setTypeface(face);
        return view;

    }

    @Override
    public void onClick(View v)
    {

        if (companyname.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), "Please fill your company name", Toast.LENGTH_SHORT).show();

        }



        else if (address.getText().toString().equals(""))
        {

            Toast.makeText(getActivity(), "Please fill your company address", Toast.LENGTH_SHORT).show();
        }


        else if (city.getText().toString().equals(""))
        {

            Toast.makeText(getActivity(), "Please fill your city", Toast.LENGTH_SHORT).show();
        }

        else if (pincode.getText().toString().equals(""))
        {

            Toast.makeText(getActivity(), "Please fill your pincode", Toast.LENGTH_SHORT).show();
        }



        else if (pincode.getText().toString().length()!=6)
        {

            Toast.makeText(getActivity(), "Pincode must be 6 digits long", Toast.LENGTH_SHORT).show();
        }
        else if (state.getText().toString().equals(""))
        {

            Toast.makeText(getActivity(), "Please fill your state ", Toast.LENGTH_SHORT).show();
        }


        else if (country.getText().toString().equals(""))
        {

            Toast.makeText(getActivity(), "Please fill your country", Toast.LENGTH_SHORT).show();
        }




        else if (aboutus.getText().toString().equals(""))
        {

            Toast.makeText(getActivity(), "Please fill about your company", Toast.LENGTH_SHORT).show();
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


            relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
            linearLayoutprogress.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            obj = new JSONObject();

            try
            {

                obj.put("UID", UID);
                obj.put("CompanyName", companyname.getText().toString());
                obj.put("CompanyType", companyType);
                obj.put("SizeBusiness", companySize);
                obj.put("Address1", address.getText().toString());
                obj.put("Address2", address2.getText().toString());
                obj.put("City", city.getText().toString());
                obj.put("Pincode", pincode.getText().toString());
                obj.put("State", state.getText().toString());
                obj.put("Country", country.getText().toString());
                obj.put("Website", websites.getText().toString());
                obj.put("OfficeNo", phone.getText().toString());
                obj.put("Fax", fax.getText().toString());
                obj.put("CompanyEmail", email.getText().toString());
                obj.put("AboutCompany", aboutus.getText().toString());

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute(obj.toString(), "updateprofile.php");



        }
        else
        {
            connectionDetector.showAlertDialog(getActivity(), "No Internet Connection", "You don't have internet connection.", false);
            new Handler().postDelayed(new Runnable() {
                
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }, 0);
        }

    }


    @Override
    public void processFinish(String output)
    {
        System.out.println("processFinish" + output);


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
            ModgetProfile task = new ModgetProfile();
            task.delegate = this;
            task.execute(obj.toString(),"member-profile.php");
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
    public void getProfile(String output)
    {

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putString("UserInfo", output);
        editor.commit();
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(), "Information Saved", Toast.LENGTH_SHORT).show();
        viewPager =(ViewPager) getActivity().findViewById(R.id.tabspager);
        viewPager.setCurrentItem(1);
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
