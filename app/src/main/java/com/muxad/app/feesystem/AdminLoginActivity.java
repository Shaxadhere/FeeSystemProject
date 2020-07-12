package com.muxad.app.feesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminLoginActivity extends AppCompatActivity {

    EditText Username;
    EditText Password;
    ImageButton LoginButton;
    TextView LoginUser;
    ImageView Backwards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        setTitle("Admin Login");

        Username = findViewById(R.id.txtUsername);
        Password = findViewById(R.id.txtPassword);
        LoginButton = findViewById(R.id.btnLogin);
        LoginUser = findViewById(R.id.btnLoginUser);
        Backwards = findViewById(R.id.btnBack);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest();
            }
        });

        LoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchUserLogin();
            }
        });

        Backwards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchBackwards();
            }
        });
    }

    private void loginRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://83b99e3f30c2.ngrok.io/feesystemapi/auth/login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response.trim(),Toast.LENGTH_SHORT).show();
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("users");

                    String data1 = jsonArray.getJSONObject(0).getString("Username");
                    Toast.makeText(getApplicationContext(), data1, Toast.LENGTH_SHORT).show();

                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> map = new HashMap<String, String>();
                map.put("Username", Username.getText().toString().trim());
                map.put("Password", Password.getText().toString().trim());

                return map;
            }
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void switchUserLogin(){
        Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
        AdminLoginActivity.this.startActivity(myIntent);
    }

    private void switchBackwards(){
        finish();
    }
}