package com.ekant.justbiz;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class ActivityTermCondition extends AppCompatActivity  {


    String data;
    WebView webView;
    Boolean isInternetPresent = false;
    ModConnectionDetector connectionDetector;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term__condition);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#4db7ab"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        connectionDetector = new ModConnectionDetector(getApplicationContext());
        webView=(WebView) findViewById(R.id.mWebView);


        data="<html><head><title>The Meaning of Life</title></head><body style='font-family:Helvetica Neue;font-size:13px;color:#7D6E68;font-weight:700;text-align:justify;'><div style='width:95%;margin:0  auto;'>JUSTBUSINESSES<div style='height:5px;width:100%;'></div>TERMS OF USE<div style='height:5px;width:100%;'></div> 1. &nbsp;ACCEPTANCE OF TERMS<div style='height:5px;width:100%;'></div>PLEASE READ THE TERMS OF USE THOROUGHLY AND CAREFULLY.<div style='height:5px;width:100%;'></div>The terms and conditions set forth below  constitute a legally-binding agreement between JustBusinesses (as defined below) and You. These Terms of Use contain provisions that define your limits, legal rights and obligations with respect to your use of the mobile application, JustBusinesses. The Terms of Use described below apply to all Users of the App (as defined below), including Users who are also contributors of video content, information, private and public messages, advertisements, and other materials or Services on the App.<div style='height:5px;width:100%;'></div>The App is currently owned and operated by [Mr. Kumar Savar Malhotra1] (&#34;Owner &#34;)  .<div style='height:5px;width:100%;'></div>You acknowledge that by &#34;accepting &#34; the Terms of Use, you hereby certify that: (1) you are a User, (2) you have the authority to enter into these Terms of Use, (3) you authorize the transfer of payment for Services requested through the use of the App, and (4) you agree to be bound by all terms and conditions of these Terms of Use and any other documents incorporated by reference herein. If you do not so agree to the foregoing, you should not click to affirm your acceptance thereof, in which case you are prohibited from accessing or using the App. If you do not agree to any of the provisions set forth in the Terms of Use, kindly discontinue viewing or using this App immediately.<div style='height:5px;width:100%;'></div>You specifically agree that you are at least 18 years of age and you are competent under law to enter into a legally binding and enforceable contract with Justbusinesses.If you use the App or open an Account (as defined below) on behalf of yourself or your business, you represent and warrant that you have the authority to bind that business and your acceptance of the Terms of Use will be deemed an acceptance by that business and &#34;you &#34; and  &#34;your &#34; herein shall refer to that business.<div style='height:5px;width:100%;'></div>JustBusinesses reserves the right, in its sole discretion, to change, modify, or otherwise amend the Terms of Use, and any other documents incorporated by reference herein for complying with legal and regulatory framework and for other legitimate business purposes, at any time, and JustBusinesses will post notice of the changes and the amended Terms of Use on the App and at [xxx@justbusinesses.xx]. It is your responsibility to review the Terms of Use for any changes and you are encouraged to check the Terms of Use frequently. Your use of the App following any amendment of the Terms of Use will signify your assent to and acceptance of any revised Terms of Use. If you do not agree to abide by these or any future Terms of Use, please do not use or access this App.<div style='height:5px;width:100%;'></div>For the purpose of this Terms of Use, the words will have the meaning accorded to them hereunder:<div style='height:5px;width:100%;'></div>*  &nbsp;The &#34;App &#34; or &#34;JuistBusinesses &#34; means this innovative B2B business networking mobile app offering Services to the User, designed strictly to help individuals and organizations to generate business on the internet using outbound online marketing leveraged through the power and potential of mobile social media.<div style='height:5px;width:100%;'></div>* &nbsp; The term &#34;you &#34;, &#34;your&#34;, &#34;User &#34; or  &#34;Service User,&#34; as applicable, mean the person that accesses, uses, and/or participates in the App in any manner, and the terms &#34;we &#34;, &#34;us &#34;, or &#34;our &#34; shall refer to JustBusinesses, its Owner and its affiliates, agents and contractors.<div style='height:5px;width:100%;'></div>* &nbsp;The &#34;Services&#34; shall mean all services, facilities and features that are made available to you, by or through this App, including any other additional software or service offered by the App in furtherance of achieving the objective of the App. It includes the access and usage of the App, including without limitation, creating an account, agreeing to allow the App to save contact information and other details on its server and downloading the App as and when required.<div style='height:5px;width:100%;'></div>2. SUBSCRIPTION AND ACCESIBILITY<div style='height:5px;width:100%;'></div>a) &nbsp;Access to APP<div style='height:5px;width:100%;'></div>To use the Services, you must open an account with us, which means you must register with the App and agree to the Terms of Use (thereby creating an &#34;Account &#34;). You must provide accurate and complete registration information any time you register.<div style='height:5px;width:100%;'></div>You may use the Services only in compliance with these Terms of Use and all applicable local, state, national, and international laws, rules and regulations.<div style='height:5px;width:100%;'></div>Your Account is available for your personal use. You may not use your Account to create an Internet link to any other web site without our express written permission, whether or not such link or affiliation is created for commercial use.<div style='height:5px;width:100%;'></div>JustBusinesses hereby grants you a non-exclusive, revocable access to use the App as set forth in the Terms of Use; provided, however, that (i) you will not copy, distribute, or make derivative works of the App in any medium without JustBusinesses's prior written consent; (ii) you will not alter or modify any part of the App; and (iii) you will otherwise act in accordance with the terms and conditions of the Terms of Use and in accordance with all applicable laws.<div style='height:5px;width:100%;'></div>b)&nbsp; Membership eligibility criteria<div style='height:5px;width:100%;'></div>Use of the App is available only to individuals who are at least 18 years old and can form legally binding contracts under applicable law. You represent, acknowledge and agree that you are at least 18 years of age, and that: (a) all sign up/ registration information and any other content posted on your Profile is truthful and accurate, (b) you will maintain the accuracy of such information, and (c) your use of the App and Services offered through this App do not violate any applicable law or regulation. Your Account may be terminated without warning if we, at our discretion, believe that you are under the age of 18 or that you are not complying with any applicable laws, rules or regulations.<div style='height:5px;width:100%;'></div>To access the features of the App, you will need to create a password-protected account (&#34Profile/Account &#34). To create an Account, you must submit your:<div style='height:5px;width:100%;'></div>* &nbsp;Full Name<div style='height:5px;width:100%;'></div>* &nbsp;Phone or cell number with country code<div style='height:5px;width:100%;'></div>* &nbsp;Email address<div style='height:5px;width:100%;'></div>* &nbsp;Company name<div style='height:5px;width:100%;'></div>* &nbsp; Size of business: (< 10 employees, 11 ? 50 employees, 51-100 employees, more than 100 employees)<div style='height:5px;width:100%;'></div>*  &nbsp;Do you own the company or are you an employee?<div style='height:5px;width:100%;'></div>* &nbsp; If employee?<div style='height:5px;width:100%;'></div>* &nbsp;Position if you are an employee<div style='height:5px;width:100%;'></div>* &nbsp;Company?s Location<div style='height:5px;width:100%;'></div>* &nbsp;Country<div style='height:5px;width:100%;'></div>* &nbsp;Vertical and type of business to which you belong<div style='height:5px;width:100%;'></div>* &nbsp;Along with any other information as may be required by the App for the provision of services under the App;<div style='height:5px;width:100%;'></div>through the sign up page on the App and create a password. You will also have the ability to provide additional optional information, which is not required to register for an account but may be helpful to JustBusinesses in providing you with a more customized experience when using the App.<div style='height:5px;width:100%;'></div>You are solely responsible for safeguarding your JustBusinesses password at all times and shall keep your passwords secure at all times. You shall be solely responsible for all activity that occurs on your Account and you shall notify JustBusinesses immediately of any breach of security or any unauthorized use of your Account. Similarly, you shall never use another's Account without JustBusinesses?s permission. You agree that you will not misrepresent yourself or represent yourself as another User of the App and/or the Services offered through the App.<div style='height:5px;width:100%;'></div>You hereby expressly acknowledge and agree that you, yourself and not JustBusinesses will be liable for your losses, damages etc. (whether direct or indirect, actual or consequential) caused by an unauthorized use of your Account. Notwithstanding the foregoing, you may be liable for the losses of JustBusinesses or others due to such unauthorized use. An Account holder is sometimes referred to herein as a &#34Registered User&#34 .<div style='height:5px;width:100%;'></div>You acknowledge and agree that you shall comply with the following policies (the &#34;Account Policies&#34;):<div style='height:5px;width:100%;'></div>* &nbsp;You will not copy any part of the App in any medium without JustBusinesses's prior written authorization.<div style='height:5px;width:100%;'></div>* You will not alter or modify any part of the App other than as may be reasonably necessary to use the App for its intended purpose.<div style='height:5px;width:100%;'></div>* You will provide true, accurate, current and complete information when creating your Account/Profile and you shall maintain and update such information during the term of this Agreement so that it will remain accurate, true, current and complete.<div style='height:5px;width:100%;'></div>*  &nbsp;You shall not use any automated system, including but not limited to, &#34;robots,&#34; &#34;spiders, &#34; &#34;offline readers,&#34; &#34;scrapers,&#34; etc., to access the App for any purpose without JustBusinesses's prior written approval.<div style='height:5px;width:100%;'></div>* &nbsp;You shall not in any manual or automated manner collect other Service Users information, including but not limited to, names, addresses, phone numbers, or email addresses, copying copyrighted text, or otherwise misuse or misappropriate App information or content, including but not limited to, use on a &#34;mirrored&#34;, competitive, or third party site/application.<div style='height:5px;width:100%;'></div>* &nbsp;You shall not in any way that transmits more request messages to the JustBusinesses servers, or any server of a JustBusinesses subsidiary or affiliate, in a given period of time than a human can reasonably produce in the same period by using a conventional online web browser.<div style='height:5px;width:100%;'></div>* &nbsp;You shall not take any action that (i) unreasonably encumbers or, in JustBusinesses's sole discretion, may unreasonably encumber the App's software or functionality; (ii) interferes or attempts to interfere with the proper working of the App or any third-party participation in the App; or (iii) bypasses JustBusinesses's measures that are used to prevent or restrict access to the App.<div style='height:5px;width:100%;'></div>* &nbsp;You agree not to collect or harvest any personally identifiable data, including without limitation, names or other Account information, from the App, nor to use the communication systems provided by the App for any commercial purposes.<div style='height:5px;width:100%;'></div>c) &nbsp;Additional policies<div style='height:5px;width:100%;'></div>Your access to, downloading of, use of, and participation in the App is subject to the Terms of Use and all applicable JustBusinesses regulations, guidelines and additional policies that JustBusinesses may set forth from time to time, including without limitation, a copyright policy and any other restrictions or limitations that JustBusinesses publishes on the App (the &#34;Additional Policies&#34;). You hereby agree to comply with the Additional Policies and your obligations thereunder at all times. You hereby acknowledge and agree that if you fail to adhere to any of the terms and conditions of this Agreement or documents referenced herein, including the Account Policies, membership eligibility criteria or Additional Policies, JustBusinesses, in its sole discretion, may terminate your Account at any time without prior notice to you as well as initiate appropriate legal proceedings, if necessary.<div style='height:5px;width:100%;'></div>3. &nbsp;USER CONDUCT<div style='height:5px;width:100%;'></div>a) &nbsp;Prohibitions on submitted content<div style='height:5px;width:100%;'></div>You shall not upload, post, transmit, transfer, disseminate, distribute, or facilitate distribution of any content, including text, images, video, sound, data, information, or software, to any part of the App, and any information uploaded or displayed by you on the App and/or your Profile shall not:<div style='height:5px;width:100%;'></div>*  &nbsp;misrepresents the source of anything you post, including impersonation of another individual or entity or any false or inaccurate biographical information for any service professionals; provides or create links to external sites that violate the Terms of Use; is intended to harm or exploit any individual under the age of 18 (&#34;Minor&#34;) in any way; is designed to solicit, or collect personally identifiable information of any Minor, including, but not limited to, name, email address, home address, phone number, or the name of his or her school<div style='height:5px;width:100%;'></div>*  &nbsp;invade anyone's privacy by attempting to harvest, collect, store, or publish private or personally identifiable information, such as names, email addresses, phone numbers, passwords, account information, credit card numbers, home addresses, or other contact information without their knowledge and willing consent;<div style='height:5px;width:100%;'></div>* &nbsp;contain falsehoods or misrepresentations that could damage JustBusinesses or any third party;<div style='height:5px;width:100%;'></div>* &nbsp;contain pornographic, harassing, hateful, illegal, obscene, defamatory, libelous, slanderous, threatening, discriminatory, racially, culturally or ethnically offensive; incites, advocates, or express pornography, obscenity, vulgarity, profanity, hatred, bigotry, racism, or gratuitous violence; encourages conduct that would be considered a criminal offense, give rise to civil liability or violate any law; promotes racism, hatred or physical harm of any kind against any group or individual; contain nudity, violence or inappropriate subject matter; or is otherwise inappropriate;<div style='height:5px;width:100%;'></div>* &nbsp;infringe any copyrighted, protected by trade secret or otherwise subject to third-party proprietary rights, including privacy and publicity rights, unless you are the owner of such rights or have permission from the rightful owner to post the material and to grant JustBusinesses all of the license rights granted herein;<div style='height:5px;width:100%;'></div>* &nbsp;contain or promote an illegal or unauthorized copy of another person's copyrighted work, such as pirated computer programs or links to them, information to circumvent manufacture installed copy-protection devices, pirated music or links to pirated music files, or lyrics, guitar tabs or sheet music, works of art, teaching tools, or any other item the copy, display, use, performance, or distribution of which infringes on another's copyright, intellectual property right, or any other proprietary right;<div style='height:5px;width:100%;'></div>* &nbsp;be intended to threaten, stalk, defame, defraud, degrade, victimize, or intimidate an individual or group of individuals for any reason on the basis of age, gender, disability, ethnicity, sexual orientation, race, or religion; or to incite or encourage anyone else to do so;<div style='height:5px;width:100%;'></div>* &nbsp;intend to harm or disrupt another user's mobile or would allow others to illegally access software or bypass security on Apps or servers, including but not limited, to spamming; impersonates, uses the identity of, or attempts to impersonate a JustBusinesses employee, agent, manager, host, another user, or any other person though any means;* advertise or solicit a business not related to or appropriate for the App (as determined by JustBusinesses in its sole discretion);<div style='height:5px;width:100%;'></div>* &nbsp;contain or could be considered &#34;junk mail&#34;, &#34;spam&#34;, &#34;chain letters&#34;, &#34;pyramid schemes&#34;, &#34;affiliate marketing&#34;, or unsolicited commercial advertisement;<div style='height:5px;width:100%;'></div>* &nbsp;contain advertising for ponzi schemes, discount cards, credit counseling, online surveys or online contests;<div style='height:5px;width:100%;'></div>* &nvsp;distribute or contain viruses or any other technologies that may harm JustBusinesses, or the interests or property of JustBusinesses users<div style='height:5px;width:100%;'></div>* &nbsp;contain links to commercial services or Apps, except as allowed pursuant to the Terms of Use;is non-local or irrelevant content;<div style='height:5px;width:100%;'></div>* &nbsp;contain identical content to other open postings you have already posted; or uses any form of automated device or computer program that enables the submission of Postings without the express written consent of JustBusinesses.<div style='height:5px;width:100%;'></div>b) &nbsp;No discrimination<div style='height:5px;width:100%;'></div>Indian laws prohibit any preference, limitation or discrimination based on race, color, religion, sex, national origin, age, handicap or other protected class. JustBusinesses will not knowingly accept any Profile/User which is in violation of the law. JustBusinesses has the right, in its sole discretion and without prior notice to you; to immediately terminate the Services of the App of any User that discriminates or is any way in violation of any law.<div style='height:5px;width:100%;'></div>c)\tProhibitions With Respect to the APP While using the App, you shall not:<div style='height:5px;width:100%;'></div>* &nbsp;post content or items in any inappropriate category or areas on the App;<div style='height:5px;width:100%;'></div>* &nbsp; violate any laws, third-party rights, Account Policies, or any provision of the Terms of Use, such as the prohibitions described above;<div style='height:5px;width:100%;'></div>* &nbsp;circumvent or manipulate our fee structure, the billing process, or fees owed to JustBusinesses; post false, inaccurate, misleading, defamatory, or libelous content (including personal information about any App user);<div style='height:5px;width:100%;'></div>* &nbsp;take any action that may undermine the feedback or ratings systems (such as displaying, importing or exporting feedback information off the App or using it for purposes unrelated to the App);<div style='height:5px;width:100%;'></div>In the event of any dispute between Users of the App concerning the use of the Services or the App, JustBusinesses has the right, in its sole and absolute discretion, to remove such feedback or take any action it deems reasonable without incurring any liability therefrom.<div style='height:5px;width:100%;'></div>If a Service User violates any of the above-referenced rules in connection with his or her posting, JustBusinesses, in its sole discretion, may take any of the following actions: (a) cancel the registration to the App; (b) limit the Service User's Account privileges; (c) suspend the Service User's Account; and/ or; (d) terminate this Agreement;  (e) decrease the Service User's status earned via the referrals and the feedback page.<div style='height:5px;width:100%;'></div>4. \tUSE OFCONTENTS TO WHICH ACCESS HAS BEEN ALLOWED<div style='height:5px;width:100%;'></div>a)\t&nbsp;No confidentiality<div style='height:5px;width:100%;'></div>The App may now or in the future permit the submission of videos or other communications submitted by you and other users, including without limitation, your offer, your wants of business, the people you can generate business for, any feedback, and all submitted content, and the hosting, sharing, and/or publishing of information. You understand that whether or not such information is published, JustBusinesses does not guarantee any confidentiality with respect to any such information.<div style='height:5px;width:100%;'></div>You agree that any information provided by you for which you authorize to be searchable by other Service Users who have access to the App is provided on a non-proprietary and non-confidential basis. You agree that JustBusinesses shall be free to use or disseminate such freely reachable information on an unrestricted basis for the purpose of providing the Services.<div style='height:5px;width:100%;'></div>You are aware that any information provided by you towards locating a professional who ordinarily is bound to maintain confidentiality under law with his/her client (i.e. a doctor or a lawyer) is not extended to Jut Businesses.<div style='height:5px;width:100%;'></div>JustBusiness shall have the right to access your remote device upto and to the extent allowed by you. By accepting the Terms of Use, you hereby authorise JustBusinesses to access the following:<div style='height:5px;width:100%;'></div>* &nbsp;Please enlist the drives/function of the User?s remote device that you would like to access ? camera, location, contacts, address book, etc]The right of JustBusinesses to access your remote device up to the limit permitted by you may be amended from time to time, to include additional accesses. At any time during the substance of the Agreement, you can revoke the rights of the App to access the remote device and JustBusniesses shall thereafter not access your remote device to the extent of revocation of such access by you. However, the information received by JustBusinesses pursuant to the access so granted by you, and  rights of the App to use such information under this Clause 4(a) of the Terms of Use shall survive termination of this Agreement.<div style='height:5px;width:100%;'></div>JustBusinesses may also disclose, retain or use, any and all information received from its Users, pursuant to the usage of the App, this Agreement or any Terms of Use, including but not limited to, personal information received from accessing the remote device of the User pursuant to the access so granted by the User. Any and all information collected pursuant to the Terms of Use shall survive the Termination of this Agreement.<div style='height:5px;width:100%;'></div>JustBusinesses may disclose, retain or use any information received by its Users, if  it reasonably believes that such a disclosure (i) is necessary in order to comply with a legal process (such as a court order, search warrant, etc.) or other legal requirement of any governmental authority, (ii) would potentially mitigate JustBusinesses?s liability in an actual or potential lawsuit, (iii) is otherwise necessary or appropriate to protect our rights or property, or the rights or property of any person or entity, (iv) to enforce this Agreement (including, but not limited to ensuring payment of fees by users), or (v) as may be required or necessary in our reasonable discretion.<div style='height:5px;width:100%;'></div>b) \t&nbsp;Your representations and warranties<div style='height:5px;width:100%;'></div>You shall be solely responsible for your Profile and the consequences of posting or publishing it. In connection with information submitted by you on the App, you affirm, represent, and/or warrant that: (a) you own or have the necessary licenses, rights, consents, and permissions to use and authorize JustBusinesses to use all patent, trademark, trade secret, copyright or other proprietary rights in and to any and all Submitted Content to enable inclusion and use of the Submitted Content in the manner contemplated by the App and these Terms of Use; and (b) you have the written consent, release, and/or permission of each and every identifiable individual person in the Submitted Content to use the name or likeness of each and every such identifiable individual person to enable inclusion and use of the Submitted Content in the manner contemplated by the App and these Terms of Use.<div style='height:5px;width:100%;'></div>c) \t&nbsp;JustBusinesses's disclaimers<div style='height:5px;width:10%;float:left;'></div>* &nbsp;JustBusinesses does not endorse any information, referrals or any opinion, recommendation, or advice expressed therein, and JustBusinesses expressly disclaims any and all liability in connection with any information, referrals or any opinion, recommendation, or advice, JustBusinesses does not permit copyright infringing activities and infringement of intellectual property rights on the App, and JustBusinesses will remove any content which infringes on another's intellectual property rights. JustBusinesses reserves the right to remove any content on the App without prior notice. JustBusinesses will also terminate a User's access to the App, if he or she is determined to be a repeat infringer. A repeat infringer is a App user who has been notified of infringing activity more than twice and/ or has had content removed from his Account;/Profile on  the App more than twice. JustBusinesses also reserves the right, in its sole and absolute discretion, to decide whether any content on the App is appropriate and complies with these Terms of Use for all violations, in addition to copyright infringement and violations of intellectual property law, including, but not limited to, pornography, obscene or defamatory material, or excessive length. JustBusinesses may remove such content and/or terminate a User's access for uploading such material in violation of these Terms of Use at any time, without prior notice and in its sole discretion.<div style='height:5px;width:100%;'></div>*  &nbsp;You acknowledge and understand that when using the App, you will be exposed to information and referrals from a variety of sources, and that JustBusinesses is not responsible for the accuracy, usefulness, competence, safety relating to, any such information or referrals, by and/or of, other Service User or any other third party. You further acknowledge and understand that you may be exposed to information that is inaccurate, offensive, indecent, or objectionable, and you agree to waive, and hereby do waive, any legal or equitable rights or remedies you have or may have against JustBusinesses with respect thereto, and agree to indemnify and hold JustBusinesses, its owners, members, managers, operators, directors, officers, agents, affiliates, and/or licensors, harmless to the fullest extent allowed by law regarding all matters related to your use of the App.<div style='height:5px;width:100%;'></div>* &nbsp;You are solely responsible for the photos, profiles and other content, including, without limitation, any content that you publish or display on or through the App, or transmit to other App Users. You understand and agree that JustBusinesses may, in its sole discretion and without incurring any liability, review and delete or remove any content that violates this Agreement or which might be offensive, illegal, or that might violate the rights, harm, or threaten the safety of App users or others.</div><div style='height:5px;width:100%;'></div>*  &nbsp;You acknowledge and understand that User identification on the internet/App is difficult, we cannot and do not confirm each User's purported identity and we are not responsible for the same. We may provide information about a User, such as information on various professionals/businesses, geographical location, or referrals, verification of identity or credentials. However, such information is based solely on data that the User submits and we provide such information solely for the convenience of Users and the provision of such information is not an introduction, endorsement or recommendation by us.<div style='height:5px;width:100%;'></div>*  &nbsp;You acknowledge and understand that the App is a dynamic time-sensitive mobile application and that we are not responsible and cannot be held liable for any such time-sensitive information. As such, contents and functionality of the App will change frequently. It is possible that some information could be considered offensive, harmful, inaccurate or misleading or mislabelled or deceptively labelled accidently by us or accidentally or purposefully by a third party. You accept our Services, the App and all content on it, are provided on an 'as is', 'with all faults' and 'as available' basis and without warranties of any kind either express or implied.<div style='height:5px;width:100%;'></div>*  &nbsp;You acknowledge and accept that to the extent permitted by law, we specifically disclaim any implied warranties of any kind.<div style='height:5px;width:100%;'></div> c)&nbsp;No Relationship<div style='height:5px;width:100%;'></div>You agree that nothing in this Agreement shall be construed as creating a joint venture, partnership, franchise, agency, employer/employee, or similar relationship between, the Users or any third party, and JustBusinesses.<div style='height:5px;width:100%;'></div></div></body></html>\"";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home==item.getItemId())
        {
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        finish();

        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }


}












































































































//package com.ekant.justbiz_test;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.text.Html;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class ActivityTermCondition extends AppCompatActivity implements ModAsyncResponce {
//
//
//    String text;
//    TextView textView;
//    Boolean isInternetPresent = false;
//    ModConnectionDetector connectionDetector;
//    ProgressBar progressBar;
//    JSONObject jsonObj;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_term__condition);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
//        getSupportActionBar().setIcon(R.drawable.logo);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
//        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#4db7ab"), android.graphics.PorterDuff.Mode.SRC_ATOP);
//        connectionDetector = new ModConnectionDetector(getApplicationContext());
//        textView=(TextView) findViewById(R.id.termsandcondition);
//
//
//        sendDatatoWeb();
//
//
//    }
//
//    private void sendDatatoWeb()
//    {
//        progressBar.setVisibility(View.VISIBLE);
//
//        isInternetPresent = connectionDetector.isConnectingToInternet();
//
//        if (isInternetPresent) {
//            ModAsyncTask task = new ModAsyncTask();
//            task.delegate = this;
//            task.execute("Blank", "terms.php");
//        }
//
//        else
//        {
//
//            connectionDetector.showAlertDialog(this,"No Internet Connection", "You don't have internet connection.", false);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    progressBar.setVisibility(View.INVISIBLE);
//                }
//            }, 0);
//
//
//        }
//
//
//    }
//
//    public void processFinish(String output) {
//
//        progressBar.setVisibility(View.INVISIBLE);
//        if (output!=null)
//        {
//            try {
//                JSONObject  jsonObj = new JSONObject(output);
//                textView.setText(Html.fromHtml(jsonObj.toString()));
//
//            }
//            catch (JSONException e)
//            {
//                e.printStackTrace();
//                Toast.makeText(ActivityTermCondition.this, "Oops! something happend wrong.", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//
//        else
//        {
//            Toast.makeText(ActivityTermCondition.this, "Oops! something happend wrong.", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        if (android.R.id.home==item.getItemId())
//        {
//            finish();
//            overridePendingTransition(R.anim.left_in, R.anim.right_out);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onBackPressed()
//    {
//        finish();
//
//        overridePendingTransition(R.anim.left_in, R.anim.right_out);
//
//    }
//
//
//}
