package com.example.blooddonationproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterAct extends AppCompatActivity {
    private EditText ed1, ed2, ed3, ed4, ed5;
    private Button regBtn;
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String CONTACT = "contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regUser();
            }
        });
    }

    private void regUser() {
        String pattern = "\"[a-z0-9._%+-]+@[a-z0-9.-]+\\\\.[a-z]\"";
        final String name = ed1.getText().toString().trim();
        final String email = ed2.getText().toString().trim();
        final String password = ed3.getText().toString().trim();
        final String contact = ed4.getText().toString().trim();
        if(name.isEmpty()){
            ed1.setError("Please Fill the Field");
        }else if(email.isEmpty()){
            ed2.setError("Please Fill the Field");
        }else if(email.matches(pattern)){
            ed2.setError("Email Address is incorrect");
        }else if(password.isEmpty()){
            ed3.setError("Please Fill the Field");
        }else if(password.length()<8){
            ed3.setError("password is too short");
        }else if(contact.isEmpty()){
            ed4.setError("Please Fill the Field");
        }else if(contact.length()<10){
            ed4.setError("contact No. is incorrect");
        }else{
            final RequestQueue requestQueue = Volley.newRequestQueue(RegisterAct.this);
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.REG_URL,
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
                    hmap.put(PASSWORD, password);
                    hmap.put(CONTACT, contact);
                    return hmap;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
        private void init() {
            ed1 = findViewById(R.id.nameEdit);
            ed2 = findViewById(R.id.emailEdit);
            ed3 = findViewById(R.id.passwordEdit);
            ed4 = findViewById(R.id.contactEdit);
            regBtn = findViewById(R.id.btn);
        }

}