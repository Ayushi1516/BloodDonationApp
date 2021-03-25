package com.example.blooddonationproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonationproject.adapter.FragmentAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class NavAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener

{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentAdapter adapter;
    private Toolbar toolbar;
    private GoogleSignInClient googleSignInClient;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    ArrayList<Uri> arrayListapkFilepath;
    private ImageButton donor,profile,hospital,bloodbank;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        setTitle("Volley");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        GoogleSignInOptions gso=new  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(NavAct.this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        TextView nametxt=navigationView.getHeaderView(0).findViewById(R.id.nametext);
        TextView emailtxt=navigationView.getHeaderView(0).findViewById(R.id.emailtext);
        if(account!=null){
            String name=account.getDisplayName();
            //String email =account.getEmail();
            //url personPhoto=account.getPhotoUrl();
            nametxt.setText(name);
            String email=account.getEmail();
            emailtxt.setText(email);
            setTitle(name);
            //Picasso.with(JsonData.this).load(personPhoto).into();
        }


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(NavAct.this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(NavAct.this);
        donor=findViewById(R.id.donor);
        hospital=findViewById(R.id.hospital);
        profile=findViewById(R.id.profile);
        bloodbank=findViewById(R.id.bloodbank);

    }

    @Override
    protected void onResume() {

        super.onResume();
        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavAct.this.getApplicationContext(), Donor.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavAct.this.getApplicationContext(), Profile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavAct.this.getApplicationContext(), Hospital.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        bloodbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavAct.this.getApplicationContext(), Bloodbank.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer=findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout){

            googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(NavAct.this, "Successfully Signed Out", Toast.LENGTH_LONG).show();
                   startActivity(new Intent(NavAct.this,LoginAct.class));
                   finish();
                }
            });

        }
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.nav_home){
            Intent intent=new Intent(getApplicationContext(),NavAct.class);
            startActivity(intent);
        }else if(id==R.id.nav_profile){
            Intent intent=new Intent(getApplicationContext(),Profile.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_services){
            Intent intent=new Intent(getApplicationContext(),Services.class);
            startActivity(intent);
        }else if(id==R.id.nav_donor){
            Intent intent=new Intent(getApplicationContext(),Donorser.class);
            startActivity(intent);
        }else if(id==R.id.nav_share){
           shareit();
        }else if(id==R.id.logout){
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(NavAct.this, "Successfully Signed Out", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(NavAct.this,LoginAct.class));
                        finish();
                    }
                });
            }
            return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    public void shareit()
    {
        arrayListapkFilepath = new ArrayList<Uri>();

        shareAPK(getPackageName());
        // you can pass bundle id of installed app in your device instead of getPackageName()
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("application/vnd.android.package-archive");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,
                arrayListapkFilepath);
        startActivity(Intent.createChooser(intent, "Share " +
                arrayListapkFilepath.size() + " Files Via"));
    }

    public void shareAPK(String bundle_id){
        File f1;
        File f2 = null;

        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List pkgAppsList = getPackageManager().queryIntentActivities(mainIntent, 0);
        int z = 0;
        for (Object object : pkgAppsList) {

            ResolveInfo info = (ResolveInfo) object;
            if (info.activityInfo.packageName.equals(bundle_id)) {

                f1 = new File(info.activityInfo.applicationInfo.publicSourceDir);

                Log.v("file--",
                        " " + f1.getName().toString() + "----" + info.loadLabel(getPackageManager()));
                try {

                    String file_name = info.loadLabel(getPackageManager()).toString();
                    Log.d("file_name--", " " + file_name);

                    f2 = new File(Environment.getExternalStorageDirectory().toString() + "/Folder");
                    f2.mkdirs();
                    f2 = new File(f2.getPath() + "/" + file_name + ".apk");
                    f2.createNewFile();

                    InputStream in = new FileInputStream(f1);

                    OutputStream out = new FileOutputStream(f2);

                    // byte[] buf = new byte[1024];
                    byte[] buf = new byte[4096];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                    System.out.println("File copied.");
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage() + " in the specified directory.");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
       arrayListapkFilepath.add(Uri.fromFile(new File(f2.getAbsolutePath())));
    }
}