package com.muxad.app.feesystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class AttendanceFragment extends Fragment {

    View v;
    TextView Title;
    Integer PK_ID;
    TextView FullName, BatchCode, StudentID, Programme, Semester, RemainingSessions, AttendedSessions, TotalSessions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_attendance, container, false);
        Title = v.findViewById(R.id.title);
        FullName = v.findViewById(R.id.txtFullName);
        BatchCode = v.findViewById(R.id.txtBatchCode);
        StudentID = v.findViewById(R.id.txtStudentID);
        Programme = v.findViewById(R.id.txtProgrammeName);
        Semester = v.findViewById(R.id.txtSemesterName);
        RemainingSessions = v.findViewById(R.id.txtRemainingSession);
        AttendedSessions = v.findViewById(R.id.txtAttendedSession);

        SharedPreferences prefs = getContext().getSharedPreferences("USER", MODE_PRIVATE);
        PK_ID = (prefs.getInt("ID", 0));
        FullName.setText(PK_ID.toString());

        getAttendanceRequest();

        return v;
    }

    private void getAttendanceRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://feesystemapi.000webhostapp.com/student/check_attendance.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity().getApplicationContext(),response.trim(),Toast.LENGTH_SHORT).show();
                if(!response.trim().equals("false")){

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("attendance");

                        FullName.setText(jsonArray.getJSONObject(0).getString(("FullName")));
                        BatchCode.setText(jsonArray.getJSONObject(0).getString(("BatchCode")));
                        StudentID.setText(jsonArray.getJSONObject(0).getString(("StudentID")));
                        Programme.setText(jsonArray.getJSONObject(0).getString(("Programme")));
                        Semester.setText(jsonArray.getJSONObject(0).getString(("SemesterName")));
                        RemainingSessions.setText(jsonArray.getJSONObject(0).getString(("TotalSession")));
                        AttendedSessions.setText(jsonArray.getJSONObject(0).getString(("AttendedSessions")));

                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Title.setText(e.toString());
                        Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Title.setText("500 - Internal Server Error");
                    //Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity().getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> map = new HashMap<String, String>();
                map.put("PK_ID", String.valueOf(PK_ID));

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