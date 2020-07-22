package com.muxad.app.feesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminLoginActivity extends AppCompatActivity {

    EditText Username;
    EditText Password;
    ImageButton LoginButton;
    TextView LoginUser;
    ImageView Backwards;
    TextView Error;
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
        Error = findViewById(R.id.txtError);

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

    public void loginRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://feesystemapi.000webhostapp.com/auth/admin.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response.trim(),Toast.LENGTH_SHORT).show();
                if(!response.trim().equals("false")){

                    //let's parse json data
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("users");

                        ArrayList<String> User = new ArrayList();

                        User.add(jsonArray.getJSONObject(0).getString(("PK_ID")));
                        User.add(jsonArray.getJSONObject(0).getString("Username"));

                        SharedPreferences.Editor editor = getSharedPreferences("Admin", MODE_PRIVATE).edit();
                        editor.putInt("ID", Integer.parseInt(User.get(0)));
                        editor.putString("Username", User.get(1));
                        editor.apply();
                        Intent Login = new Intent(AdminLoginActivity.this, AdminDrawerActivity.class);
                        AdminLoginActivity.this.startActivity(Login);

                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Error.setText(e.toString());
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Error.setText("Invalid Credentials");
                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
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


    private void switchUserLogin(){
        Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
        AdminLoginActivity.this.startActivity(myIntent);
    }

    private void switchBackwards(){
        finish();
    }
}