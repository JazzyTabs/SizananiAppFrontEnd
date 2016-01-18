package com.example.codetribe1.sizanani;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.codetribe1.unischool.R;

import com.example.codetribe1.sizanani.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity implements View.OnClickListener{

    Button b1,b2,b3,b4;
    EditText REG_name,REG_surname,REG_email, REG_password, REG_passwordTwo,LOG_email,LOG_password;
    private ProgressDialog pDialog;
    RelativeLayout registration, login;
    TextView error;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set views Layout Sections
        registration = (RelativeLayout) findViewById(R.id.REG_section);
        login = (RelativeLayout) findViewById(R.id.LOG_section);
        //initialize buttons
        b1 = (Button) findViewById(R.id.REG_btnLogin);
        b1.setOnClickListener(this);
        b2 = (Button) findViewById(R.id.LOG_btnRegister);
        b2.setOnClickListener(this);
        b3 = (Button) findViewById(R.id.REG_btnRegister);
        b3.setOnClickListener(this);
        b4 = (Button) findViewById(R.id.LOG_btnLogin);
        b4.setOnClickListener(this);

        //initialize editTexts
        REG_name = (EditText) findViewById(R.id.REG_name);
        REG_surname = (EditText) findViewById(R.id.REG_surname);
        REG_email = (EditText) findViewById(R.id.REG_email);
        REG_password = (EditText) findViewById(R.id.REG_password);
        REG_passwordTwo = (EditText) findViewById(R.id.REG_passwordTwo);
        LOG_email = (EditText) findViewById(R.id.LOG_email);
        LOG_password = (EditText) findViewById(R.id.LOG_password);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());


        //If the session is logged in move to mainScreen activity
        if (session.isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, mainScreen.class);
            startActivity(intent);
            finish();
        }
    }

    /*
        function to register user details in mysql database
    */
    public void registerUser(final String REG_name, final String REG_surname,final String REG_email, final String REG_password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        pDialog.setMessage("Registering ...");
        showDialog();

        //string request--------------------
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(getApplicationContext(),"user registered", Toast.LENGTH_LONG).show();
                    JSONObject data = new JSONObject(response).getJSONObject("user");

                    //hide REG section and show LOG section
                    registration.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                    hideDialog();
                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("method", "user_INS");
                params.put("User_Name", REG_name);
                params.put("User_Surname", REG_surname);
                params.put("User_Email", REG_email);
                params.put("User_Password", REG_password);
                params.put("Type_ID", "1");
                return params;
            }};

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
        }
    /*
           function to login user
    */
    public void loginUser(final String email, final String password){
        String tag_string_req = "req_login";
        pDialog.setMessage("Loging in...");
        showDialog();

        //string request--------------------
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                     JSONObject jsonResponse = new JSONObject(response);
                     String message = jsonResponse.getString("message");
                     error = (TextView)findViewById(R.id.LOG_error);

                    if( jsonResponse.getBoolean("success") ){
                        error.setText(message);
                        JSONObject data = new JSONObject(response);
                        JSONArray users = data.getJSONArray("user");
                        for (int i = 0; i < users.length(); i++) {
                            JSONObject user = users.getJSONObject(i);
                            //writing the value to sharedpreference which we will be showing in main screen
                            AppController.setString(MainActivity.this, "User_ID", user.getString("User_ID"));
                            AppController.setString(MainActivity.this, "User_Name", user.getString("User_Name"));
                            AppController.setString(MainActivity.this, "User_Surname", user.getString("User_Surname"));
                            AppController.setString(MainActivity.this, "User_Email", user.getString("User_Email"));
                        }
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Launching  main activity
                        Intent intent = new Intent(MainActivity.this,mainScreen.class);
                        startActivity(intent);
                        finish();
                    }else{
                        error.setText(message);
                    }


                    error.setVisibility(View.VISIBLE);


                    hideDialog();
                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(),"1 failed "+e.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"2 failed "+error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("method", "login_SEL");
                params.put("User_Email", email);
                params.put("Password", password);
                return params;
            }};

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.REG_btnLogin:
                registration.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
                break;
            case R.id.LOG_btnRegister:
                registration.setVisibility(View.VISIBLE);
                login.setVisibility(View.GONE);
                break;
            case R.id.REG_btnRegister:
                String name = REG_name.getText().toString();
                String surname = REG_surname.getText().toString();
                String email = REG_email.getText().toString();
                String password = REG_password.getText().toString();
                String password2 = REG_passwordTwo.getText().toString();
                String method ="user_INS";

                if (!name.isEmpty() && !surname.isEmpty() && !email.isEmpty() && !password.isEmpty() && !password2.isEmpty()) {

                    if(password.equals(password2)){
                        registerUser(name, surname, email, password);
                    }else{
                        Snackbar.make(v, "Passwords do not match!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(v, "Please fill in the entire form!", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.LOG_btnLogin:
                String txt_email = LOG_email.getText().toString();
                String txt_password = LOG_password.getText().toString();

                if(!txt_email.isEmpty() && !txt_password.isEmpty()){
                    loginUser(txt_email,txt_password);
                }else{
                    Snackbar.make(v, "Please enter the credentials!", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }
}
