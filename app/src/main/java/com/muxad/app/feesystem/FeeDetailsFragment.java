package com.muxad.app.feesystem;

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

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class FeeDetailsFragment extends Fragment {
    View v;
    TextView Title;
    Integer PK_ID;
    TextView FullName, BatchCode, StudentID, Programme, Semester, CourseStatus, RemainingFee, TotalFee, PaidFee, UnPaidFee;
    Boolean IsFeePaid;
    Boolean SendNotif;
    String Course;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_fee_details, container, false);

        FullName = v.findViewById(R.id.txtFullName);
        BatchCode = v.findViewById(R.id.txtBatchCode);
        StudentID = v.findViewById(R.id.txtStudentID);
        Programme = v.findViewById(R.id.txtProgramme);
        Semester = v.findViewById(R.id.txtSemesterName);
        CourseStatus = v.findViewById(R.id.txtCourseStatus);
        RemainingFee = v.findViewById(R.id.txtRemainingFee);
        TotalFee = v.findViewById(R.id.txtTotalFee);
        PaidFee = v.findViewById(R.id.txtPaidFee);

        SharedPreferences prefs = getContext().getSharedPreferences("USER", MODE_PRIVATE);
        PK_ID = (prefs.getInt("ID", 0));
        FullName.setText(PK_ID.toString());

        getFeeDetailsRequest();

        return v;
    }

    private void getFeeDetailsRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://feesystemapi.000webhostapp.com/student/check_fee.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity().getApplicationContext(),response.trim(),Toast.LENGTH_SHORT).show();
                if(!response.trim().equals("false")){

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("fee");

                        FullName.setText(jsonArray.getJSONObject(0).getString(("FullName")));
                        BatchCode.setText(jsonArray.getJSONObject(0).getString(("BatchCode")));
                        StudentID.setText(jsonArray.getJSONObject(0).getString(("StudentID")));
                        Programme.setText(jsonArray.getJSONObject(0).getString(("Programme")));
                        Semester.setText(jsonArray.getJSONObject(0).getString(("SemesterName")));
                        Course = jsonArray.getJSONObject(0).getString("CourseStatus");
                        if(Course.equals("1")){
                            CourseStatus.setText("Currently running");
                        }else if(Course.equals("0")){
                            CourseStatus.setText("Course is not running");
                        }else{
                            CourseStatus.setText("Course is not running");
                        }

                        RemainingFee.setText(jsonArray.getJSONObject(0).getString(("RemainingFee")));
                        TotalFee.setText(jsonArray.getJSONObject(0).getString(("TotalFee")));
                        PaidFee.setText(jsonArray.getJSONObject(0).getString(("PaidFee")));
                        IsFeePaid = jsonArray.getJSONObject(0).getBoolean("IsFeePaid");
                        SendNotif = jsonArray.getJSONObject(0).getBoolean("SendNotif");
                        SharedPreferences.Editor editor = getContext().getSharedPreferences("FEE", MODE_PRIVATE).edit();
                        editor.putBoolean("IsFeePaid", SendNotif);
                        editor.putBoolean("SendNotif", SendNotif);
                        editor.apply();


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