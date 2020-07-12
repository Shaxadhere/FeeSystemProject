package com.muxad.app.feesystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.muxad.app.feesystem.ui.main.SectionsPagerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    int ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences prefs = this.getSharedPreferences("USER", Context.MODE_PRIVATE);
        ID = prefs.getInt("ID", 0);



        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void getFee(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://feesystemapi.000webhostapp.com/student/check_fee.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.trim().equals("false")){


                    Toast.makeText(getApplicationContext(),response.trim(),Toast.LENGTH_SHORT).show();
                    //let's parse json data
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("fee");

                        ArrayList<String> FeeInfo = new ArrayList();
                        for (int i=0;i<jsonArray.length();i++){
                            FeeInfo.add(jsonArray.getString(i));
                        }

                        SharedPreferences.Editor editor = getSharedPreferences("FEE", MODE_PRIVATE).edit();
                        editor.putInt("PaidFee", Integer.parseInt(FeeInfo.get(0)));
                        editor.putInt("TotalFee", Integer.parseInt(FeeInfo.get(1)));
                        editor.putInt("RemainingFee", Integer.parseInt(FeeInfo.get(2)));
                        editor.putString("JoiningDate", (FeeInfo.get(3)));
                        editor.putBoolean("IsFeePaid", Boolean.parseBoolean(FeeInfo.get(4)));
                        editor.putBoolean("SendNotif", Boolean.parseBoolean(FeeInfo.get(5)));
                        editor.putBoolean("CourseStatus", Boolean.parseBoolean(FeeInfo.get(6)));
                        editor.putInt("UnpaidFee", Integer.parseInt(FeeInfo.get(7)));
                        editor.apply();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "500 - Server Error", Toast.LENGTH_LONG);
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
                map.put("PK_ID", String.valueOf(ID));
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

    private void getAttendance(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://feesystemapi.000webhostapp.com/student/check_attendance.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.trim().equals("false")){


                    Toast.makeText(getApplicationContext(),response.trim(),Toast.LENGTH_SHORT).show();
                    //let's parse json data
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("attendance");

                        ArrayList<String> AttendanceInfo = new ArrayList();
                        for (int i=0;i<jsonArray.length();i++){
                            AttendanceInfo.add(jsonArray.getString(i));
                        }

                        SharedPreferences.Editor editor = getSharedPreferences("FEE", MODE_PRIVATE).edit();
                        editor.putString("SemesterName", AttendanceInfo.get(0));
                        editor.putInt("TotalSession", Integer.parseInt(AttendanceInfo.get(1)));
                        editor.putInt("AttendedSessions", Integer.parseInt(AttendanceInfo.get(2)));
                        editor.putString("RemainingSessions", (AttendanceInfo.get(3)));
                        editor.apply();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "500 - Server Error", Toast.LENGTH_LONG);
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
                map.put("PK_ID", String.valueOf(ID));
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