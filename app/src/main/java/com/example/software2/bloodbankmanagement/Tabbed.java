package com.example.software2.bloodbankmanagement;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.software2.bloodbankmanagement.databinding.TabbedMainBinding;
import com.example.software2.bloodbankmanagement.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class Tabbed extends AppCompatActivity {

    private TabbedMainBinding binding;
    private DBHandler dbHandler;
    static String Uid;
     ViewPager viewPager;

    @SuppressLint({"Range", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = TabbedMainBinding.inflate(getLayoutInflater());
         setContentView(binding.getRoot());
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
         viewPager = binding.viewPager;
         viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @SuppressLint("ShowToast")
            public void onPageSelected(int position) {
                if (position == 0)
                    Toast.makeText(getApplicationContext(), Bloodbank.toast,
                            Toast.LENGTH_SHORT).show();
                if (position == 1)
                    Toast.makeText(getApplicationContext(), searchdonor.toast, Toast.LENGTH_SHORT).show();
                if (position == 2)
                    if (Profile.toast == null) {
                        return;
                    } else {

                        Toast.makeText(getApplicationContext(), Profile.toast, Toast.LENGTH_SHORT).show();
                    }
                if (position == 3)
                    if (request.toast == null) {
                        return;
                    } else {
                        Toast.makeText(getApplicationContext(), request.toast, Toast.LENGTH_SHORT).show();
                    }
            }
        });

         TabLayout tabs = binding.tabs;
         tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setTabTextColors(ColorStateList.valueOf(getColor(R.color.black)));
        tabs.setTabRippleColor(ColorStateList.valueOf(R.color.colorPrimary));
          tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabs.setupWithViewPager(viewPager);

        dbHandler = new DBHandler(this);

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String query = "select Uid from register where Username = '" + Login.getUsername() + "'";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            Uid = c.getString(c.getColumnIndex("Uid"));
        }
    }
     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        menu.getItem(1).setTitle("Your Uid is " + Uid);
        return true;

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.yourUid:
                item.setTitle("Your Uid is " + Uid);
                Toast.makeText(getApplicationContext(), item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.helpMenu:
                Intent i = new Intent(Tabbed.this,help.class);
                startActivity(i);
                return true;
            case R.id.signOutMenu:
                AlertDialog.Builder builder=new AlertDialog.Builder(Tabbed.this);
                builder.setTitle("Are you sure want to Exit?");
                builder.setMessage("Your account will be deleted permanently.");
                builder.setPositiveButton("yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Tabbed.this, "Your account has been permanently deleted.", Toast.LENGTH_LONG).show();
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    SQLiteDatabase db = dbHandler.getWritableDatabase();
                                    String sql2 = "delete from register where Username = '"+ Login.getUsername()+"'";
                                    db.execSQL(sql2);
                                    String sql3 = "delete from donor where Username = '"+ Login.getUsername()+"'";
                                    db.execSQL(sql3);
                                    String sql4 = "delete from request where Username = '"+ Login.getUsername()+"'";
                                    db.execSQL(sql4);
                                    Intent i = new Intent(Tabbed.this, Login.class);
                                    startActivity(i);
                                    finishAffinity();
                                    Intent intent = new Intent(Tabbed.this, Login.class);
                                    startActivity(intent);

                                } catch (Exception e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Tabbed.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        },1000);

                    }
                });
                builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertdialog=builder.create();
                //show the alertdialog
                alertdialog.show();


                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }


    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        //click double time
        Toast.makeText(Tabbed.this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



}