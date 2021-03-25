package com.example.blooddonationproject;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class LoginAct extends AppCompatActivity {
    int RC_SIGN_IN=0;
    private EditText ed1,ed2;
    private Button loginbtn;
    private TextView regtxt;
    public static final String EMAIL="email";
    public static final String PASSWORD="password";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private SignInButton signInButton;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Login");
        setContentView(R.layout.activity_login);
        preferences= getSharedPreferences("mypref",MODE_PRIVATE);
        init();
    /*try {

            PackageInfo info = getPackageManager().getPackageInfo("com.example.volleysignin", PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }*/
        callbackManager= CallbackManager.Factory.create();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            startActivity(new Intent(LoginAct.this,NavAct.class));
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginAct.this.userLogin();
            }
        });
        regtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAct.this.getApplicationContext(), RegisterAct.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                LoginAct.this.startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginGoogle();
            }
        });
    }
    private void loginGoogle(){
        GoogleSignInOptions gso=new  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(this,gso);
        Intent intent=googleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);
    }
    private void loginFB(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent=new Intent(LoginAct.this,NavAct.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginAct.this, "Login Cancelled by user", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginAct.this, exception.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void userLogin() {
        String pattern = "\"[a-z0-9._%+-]+@[a-z0-9.-]+\\\\.[a-z]\"";
        final String email = ed1.getText().toString().trim();
        final String password = ed2.getText().toString().trim();
        if(email.isEmpty()){
            ed1.setError("Please Fill the Field");
        }else if(email.matches(pattern)){
            ed1.setError("Email Address is incorrect");
        }else if(password.isEmpty()) {
            ed2.setError("Please Fill the Field");
        }else if(password.length()<8){
            ed2.setError("password is too short");
        } else {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("INFO", response);
                            if (response.equalsIgnoreCase("Success")) {
                                Intent intent=new Intent(LoginAct.this,NavAct.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> hmap = new HashMap<>();
                    hmap.put(EMAIL, email);
                    hmap.put(PASSWORD, password);
                    return hmap;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==RC_SIGN_IN){
                Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }
        private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
            try{
                GoogleSignInAccount account=completedTask.getResult(ApiException.class);
                startActivity(new Intent(LoginAct.this,NavAct.class));
            }catch(ApiException e){
                Log.w("Google Sign Error","signResult:Failed code"+e.getStatusCode());
                Toast.makeText(LoginAct.this,"Failed",Toast.LENGTH_LONG).show();
            }
    }
    private void init(){

        ed1=findViewById(R.id.emailEdit);
        ed2=findViewById(R.id.passwordEdit);
        loginbtn=findViewById(R.id.loginbtn);
        regtxt=findViewById(R.id.regtxt);
        loginButton=findViewById(R.id.login_button);
        signInButton=findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
    }
}