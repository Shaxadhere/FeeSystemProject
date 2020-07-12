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

public class ForgotPasswordActivity extends AppCompatActivity {

    ImageView Backwards;
    TextView Login;
    ImageButton Reset;
    EditText Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        setTitle("Forgot Password");

        Backwards = findViewById(R.id.btnBack);
        Login = findViewById(R.id.btnLogin);
        Reset = findViewById(R.id.btnReset);
        Username = findViewById(R.id.txtUsername);

        Backwards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchLogin();
            }
        });

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetPassword();
            }
        });
    }

    private void switchLogin(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        ForgotPasswordActivity.this.startActivity(intent);
    }

    private void ResetPassword(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://feesystemapi.000webhostapp.com/auth/forgotpassword.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("true")){
                    Intent intent = new Intent(ForgotPasswordActivity.this, LinkSentActivity.class);
                    ForgotPasswordActivity.this.startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), response.trim(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> map = new HashMap<String, String>();
                map.put("Username", "\'"+Username.getText().toString().trim()+"\'");
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
}
