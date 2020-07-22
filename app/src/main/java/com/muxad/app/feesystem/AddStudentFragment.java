package com.muxad.app.feesystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddStudentFragment extends Fragment {

    View v;
    EditText FullName;
    EditText Username;
    EditText Password;
    EditText StudentID;
    EditText Email;
    CheckBox IsAdvancePaid;
    Button JoiningDate;
    Spinner batchSpinner;
    Spinner programmeSpinner;
    EditText PaidFee;
    EditText JoiningDateET;
    Button AddStudent;
    TextView ErrorTxt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_student, container, false);

        FullName = v.findViewById(R.id.txtFullName);
        Username = v.findViewById(R.id.txtUsername);
        Password = v.findViewById(R.id.txtPassword);
        StudentID = v.findViewById(R.id.txtStudentID);
        Email = v.findViewById(R.id.txtEmail);
        JoiningDate = v.findViewById(R.id.JoiningDate);
        batchSpinner = v.findViewById(R.id.drpBatch);
        programmeSpinner = v.findViewById(R.id.drpProgramme);
        IsAdvancePaid = v.findViewById(R.id.chkAdvance);
        PaidFee = v.findViewById(R.id.txtPaidFee);
        AddStudent = v.findViewById(R.id.btnAddStudent);
        JoiningDateET = v.findViewById(R.id.txtJoiningDate);
        ErrorTxt = v.findViewById(R.id.error);

        selectProgramme();
        selectBatch();

        JoiningDate = v.findViewById(R.id.JoiningDate);
        JoiningDateET.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String date = i+"-"+i1+"-"+i2;
                        JoiningDateET.setText(date);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        JoiningDate.setOnClickListener(new View.OnClickListener() {
             @RequiresApi(api = Build.VERSION_CODES.N)
             @Override
             public void onClick(View view) {
                 DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                         String date = i+"-"+i1+"-"+i2;
                         JoiningDateET.setText(date);
                     }
                 }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                 datePickerDialog.show();
             }
         });
        AddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudent();
            }
        });
        return v;
    }
    public void selectProgramme(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://feesystemapi.000webhostapp.com/admin/selectProgramme.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity().getApplicationContext(),response.trim(),Toast.LENGTH_SHORT).show();
                if(!response.trim().equals("false")){
                    //let's parse json data
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("programmeList");

                        ArrayList<String> ProgrammeList = new ArrayList<String>();
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                           ProgrammeList.add(jsonArray.getJSONObject(i).getString("ProgrammeName"));
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, ProgrammeList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        programmeSpinner.setAdapter(adapter);

                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }else{

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    public void selectBatch(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://feesystemapi.000webhostapp.com/admin/selectBatch.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity().getApplicationContext(),response.trim(),Toast.LENGTH_SHORT).show();
                if(!response.trim().equals("false")){
                    //let's parse json data
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("batchList");

                        ArrayList<String> BatchList = new ArrayList<String>();
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            BatchList.add(jsonArray.getJSONObject(i).getString("BatchID"));
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, BatchList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        batchSpinner.setAdapter(adapter);

                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }else{

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    public void addStudent(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://feesystemapi.000webhostapp.com/admin/addStudent.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity().getApplicationContext(), JoiningDateET.getText().toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity().getApplicationContext(),response.trim(),Toast.LENGTH_SHORT).show();
                if(response.trim().equals("true")){
                    Toast.makeText(getActivity().getApplicationContext(), "Student Added Successfully", Toast.LENGTH_SHORT).show();
                    FullName.setText("");
                    Username.setText("");
                    Password.setText("");
                    StudentID.setText("");
                    Email.setText("");
                    JoiningDate.setText("");
                    IsAdvancePaid.setChecked(false);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
               // Toast.makeText(getActivity().getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> map = new HashMap<String, String>();
                map.put("fullName", FullName.getText().toString());
                map.put("username", Username.getText().toString());
                map.put("password", Password.getText().toString());
                map.put("studentId", StudentID.getText().toString());
                map.put("email", Email.getText().toString());
                map.put("paidFee", PaidFee.getText().toString());
                if(IsAdvancePaid.isChecked()){
                    map.put("isAdvancePaid", "1");
                }else{
                    map.put("isAdvancePaid", "0");
                }
                map.put("joiningDate", JoiningDateET.getText().toString());
//                map.put("FK_Programme", programmeSpinner.getSelectedItem().toString());
//                map.put("FK_Batch", batchSpinner.getSelectedItem().toString());

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
}