package com.paul_nikki.cse5236.appointmentpal.Activities;
 
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
 
import org.json.JSONException;
import org.json.JSONObject;
 
import java.util.HashMap;
import java.util.Map;
 
import com.paul_nikki.cse5236.appointmentpal.R;
import com.paul_nikki.cse5236.appointmentpal.AppConfig;
import com.paul_nikki.cse5236.appointmentpal.Controllers.AppController;
import com.paul_nikki.cse5236.appointmentpal.Helper.SessionManager;
import com.paul_nikki.cse5236.appointmentpal.Helper.PrefManager;
import com.paul_nikki.cse5236.appointmentpal.SmsStuff.HttpService;


public class CreateLoginActivity extends Activity {
    private static final String TAG = CreateLoginActivity.class.getSimpleName();

    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputPhoneNo;
    private ProgressDialog pDialog;
    private SessionManager session;

//    // new
//    private ViewPager viewPager;
//    private ViewPagerAdapter adapter;
//    private PrefManager pref;

 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_login);
 
        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPhoneNo = (EditText) findViewById(R.id.phoneno);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
 
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
 
        // Session manager
        session = new SessionManager(getApplicationContext());
 
        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(CreateLoginActivity.this,
                    MainScreenActivity.class);
            startActivity(intent);
            finish();
        }
 
        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String phoneno = inputPhoneNo.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
 
                if (!name.isEmpty() && !email.isEmpty() && !phoneno.isEmpty() && !password.isEmpty()) {
                    registerUser(name, email, phoneno, password);
                } else {
                    Log.e("tag", "plz enter details!");
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
 
        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
 
    }
 
    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email, final String phoneno,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
 
        pDialog.setMessage("Registering ...");
        showDialog();
 
        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {
 
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
 
                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("error");
                    if (error.equals("0")) {
                        // User successfully stored in mysql database
                        String uid = jObj.getString("uuid");
                        String name = jObj.getString("name");
                        String email = jObj.getString("email");

                        Log.e("tag", "user successfully registered");
                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
 
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Log.e("tag", "there was an error in registration");
                        Toast.makeText(getApplicationContext(),
                                errorMsg+"error in registration.", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
 
            }
        }, new Response.ErrorListener() {
 
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
 
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("phoneno", phoneno);
                params.put("password", password);
 
                return params;
            }
 
        };
 
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }
 
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
 
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

//    ///////
//    /**
//     * Method initiates the SMS request on the server
//     *
//     * @param name   user name
//     * @param email  user email address
//     * @param mobile user valid mobile number
//     */
//    private void requestForSMS(final String name, final String email, final String mobile) {
//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                AppConfig.URL_REQUEST_SMS, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, response.toString());
//
//                try {
//                    JSONObject responseObj = new JSONObject(response);
//
//                    // Parsing json object response
//                    // response will be a json object
//                    boolean error = responseObj.getBoolean("error");
//                    String message = responseObj.getString("message");
//
//                    // checking for error, if not error SMS is initiated
//                    // device should receive it shortly
//                    if (!error) {
//                        // boolean flag saying device is waiting for sms
//                        pref.setIsWaitingForSms(true);
//
//                        // moving the screen to next pager item i.e otp screen
//                        viewPager.setCurrentItem(1);
//                        txtEditMobile.setText(pref.getMobileNumber());
//                        layoutEditMobile.setVisibility(View.VISIBLE);
//
//                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        Toast.makeText(getApplicationContext(),
//                                "Error: " + message,
//                                Toast.LENGTH_LONG).show();
//                    }
//
//                    // hiding the progress bar
//                    progressBar.setVisibility(View.GONE);
//
//                } catch (JSONException e) {
//                    Toast.makeText(getApplicationContext(),
//                            "Error: " + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
//
//                    progressBar.setVisibility(View.GONE);
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
//                progressBar.setVisibility(View.GONE);
//            }
//        }) {
//
//            /**
//             * Passing user parameters to our server
//             * @return
//             */
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("name", name);
//                params.put("email", email);
//                params.put("mobile", mobile);
//
//                Log.e(TAG, "Posting params: " + params.toString());
//
//                return params;
//            }
//
//        };
//
//        // Adding request to request queue
//        MyApplication.getInstance().addToRequestQueue(strReq);
//    }
//
//    /**
//     * sending the OTP to server and activating the user
//     */
//    private void verifyOtp() {
//        String otp = inputOtp.getText().toString().trim();
//
//        if (!otp.isEmpty()) {
//            Intent grapprIntent = new Intent(getApplicationContext(), HttpService.class);
//            grapprIntent.putExtra("otp", otp);
//            startService(grapprIntent);
//        } else {
//            Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    /**
//     * Regex to validate the mobile number
//     * mobile number should be of 10 digits length
//     *
//     * @param mobile
//     * @return
//     */
//    private static boolean isValidPhoneNumber(String mobile) {
//        String regEx = "^[0-9]{10}$";
//        return mobile.matches(regEx);
//    }
//
//
//    class ViewPagerAdapter extends PagerAdapter {
//
//        @Override
//        public int getCount() {
//            return 2;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == ((View) object);
//        }
//
//        public Object instantiateItem(View collection, int position) {
//
//            int resId = 0;
//            switch (position) {
//                case 0:
//                    resId = R.id.layout_sms;
//                    break;
//                case 1:
//                    resId = R.id.layout_otp;
//                    break;
//            }
//            return findViewById(resId);
//        }
//    }
}