package com.muxad.app.feesystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText Username;
    EditText Password;
    ImageButton LoginButton;
    TextView ForgetPassword;
    TextView AdminLogin;
    ImageView Backwards;
    TextView Error;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Student Login");

        Username = findViewById(R.id.txtUsername);
        Password = findViewById(R.id.txtPassword);
        LoginButton = findViewById(R.id.btnLogin);
        ForgetPassword = findViewById(R.id.btnForgetPassword);
        AdminLogin = findViewById(R.id.btnAdminLogin);
        Backwards = findViewById(R.id.btnBack);
        Error = findViewById(R.id.txtError);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest();
            }
        });

        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchForgetPassword();
            }
        });

        AdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchAdminLogin();
            }
        });

        Backwards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchBackwards();
            }
        });
    }
    public void loginRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://feesystemapi.000webhostapp.com/auth/login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(),Toast.LENGTH_SHORT).show();
                if(!response.trim().equals("false")){

                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("users");

                    ArrayList<String> User = new ArrayList();

                    User.add(jsonArray.getJSONObject(0).getString(("PK_ID")));
                    User.add(jsonArray.getJSONObject(0).getString("Username"));

                    SharedPreferences.Editor editor = getSharedPreferences("USER", MODE_PRIVATE).edit();
                    editor.putInt("ID", Integer.parseInt(User.get(0)));
                    editor.putString("Username", User.get(1));
                    editor.apply();
                    Intent Login = new Intent(LoginActivity.this, HomeActivity.class);
                    LoginActivity.this.startActivity(Login);

                }
                catch (Exception e){
                    e.printStackTrace();
                    AdminLogin.setText(e.toString());
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
                }else{
                    Error.setText("Invalid Credentials");
                    //Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> map = new HashMap<String, String>();
                map.put("Username", "\'"+Username.getText().toString().trim()+"\'");
                map.put("Password", "\'"+Password.getText().toString().trim()+"\'");

                return map;
            }
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                Map<String,String> params=new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void switchForgetPassword(){
        Intent myIntent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
        LoginActivity.this.startActivity(myIntent);
    }

    public void switchAdminLogin(){
        Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
        LoginActivity.this.startActivity(intent);
    }

    public void switchBackwards(){
        finish();
    }


}
