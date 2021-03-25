package com.example.blooddonationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonationproject.util.Constant;

import java.util.HashMap;
import java.util.Map;

public class BbregForm extends AppCompatActivity {
    private Spinner spinner,spinner1;
    private String[] bloodgrp,state;
    private Button reg;
    private EditText e1,e2,e3,e4;
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String CONTACT = "contact";
    public static final String BLOODGROUP="bldgrp";
    public static final String CITY="city";
    public static final String STATE="states";
    private String bldgrp,states;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbreg_form);
        setTitle("BloodBank Registration Form");
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,bloodgrp){
            @Override
            public boolean isEnabled(int position) {
                if(position>0)
                    return true;
                else
                    return false;
            }
        };
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,state){
            @Override
            public boolean isEnabled(int position) {
                if(position>0)
                    return true;
                else
                    return false;
            }
        };
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    bldgrp= (String) parent.getItemAtPosition(position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    states = (String) parent.getItemAtPosition(position);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regUser();
            }
        });

    }
    private void regUser(){
        String pattern = "\"[a-z0-9._%+-]+@[a-z0-9.-]+\\\\.[a-z]\"";
        final String name = e1.getText().toString().trim();
        final String email = e2.getText().toString().trim();
        final String contact = e3.getText().toString().trim();
        final String city= e4.getText().toString().trim();
        final String bldgrp=bloodgrp.toString().trim();
        final String states=state.toString().trim();

        if(TextUtils.isEmpty(name)){
            e1.setError("Please Fill the Field");
        }else if(TextUtils.isEmpty(email)){
            e2.setError("Please Fill the Field");
        }else if(email.matches(pattern)) {
            e2.setError("Email Address is incorrect");
        }else if(contact.isEmpty()){
            e3.setError("Please Fill the Field");
        }else if(contact.length()<10){
            e3.setError("contact No. is incorrect");

            final RequestQueue requestQueue = Volley.newRequestQueue(BbregForm.this);
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.BLOODBANK_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("INFO", response);
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> hmap = new HashMap<>();
                    hmap.put(NAME, name);
                    hmap.put(EMAIL, email);
                    hmap.put(CONTACT, contact);
                    hmap.put(CITY,city);
                    hmap.put(BLOODGROUP,bldgrp);
                    hmap.put(STATE,states);
                    return hmap;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    private void init() {
        spinner = findViewById(R.id.spinner);
        spinner1=findViewById(R.id.spinner1);
        bloodgrp = getResources().getStringArray(R.array.bgroup);
        state=getResources().getStringArray(R.array.state);
        e1=findViewById(R.id.nameEdit);
        e2=findViewById(R.id.emailEdit);
        e3=findViewById(R.id.contactEdit);
        reg=findViewById(R.id.btn);
        e4=findViewById(R.id.cityedit);
        reg=findViewById(R.id.btn);
    }
}