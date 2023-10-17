package com.example.software2.bloodbankmanagement;

import static android.content.Context.VIBRATOR_SERVICE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Bloodbank extends Fragment implements AdapterView.OnItemSelectedListener {

     Button btn,btn2;
    static String item;//To pass the spinner value
    static String name, contactno;//created static string values
    private DBHandler dbHandler;
    static String toast;
    //To insert name and contact no in register table of particular user
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }



    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bloodbank, container, false);
        toast = "Scroll down for all blood groups";
        dbHandler = new DBHandler(view.getContext());
        btn2= (Button) view.findViewById(R.id.delete);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                final AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage(" Are you sure want to delete the request ?");
                alertDialog.setIcon(R.drawable.mx);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                 SQLiteDatabase db = dbHandler.getWritableDatabase();
                                String sql = "delete from request where Username = '"+Login.getUsername()+"'";
                                db.execSQL(sql);
                                Toast.makeText(view.getContext(), "Request deleted", Toast.LENGTH_LONG).show();

                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();
             }
        });
        btn = view.findViewById(R.id.request2);

         btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        if(item.equals("Blood type")){
                            Toast.makeText(view.getContext(), "Please select blood group first", Toast.LENGTH_LONG).show();
                        }
                        else {
                            alertDialog();
                        }
                        //calling the alertdialog when user will tap on send request button.


            }
        });

        try {
            SQLiteDatabase db = dbHandler.getWritableDatabase();
            String sql = "select * from register where Username = '"+ Login.getUsername()+"'";
            Cursor rs = db.rawQuery(sql,null);

            //We take the value of username from Login using static getvalue() function.
             if(rs.moveToFirst()){
                 name = rs.getString(0);
                 contactno = rs.getString(7);
//                 After registration the values of user were inserted in register table
//                so we have to again insert that values in request table.
            }
        }
         catch (Exception e) {
            e.printStackTrace();
        }
        final TextView txt1 = view.findViewById(R.id.textView9);
        final TextView   txt2 = view.findViewById(R.id.textView10);
        final TextView txt3 = view.findViewById(R.id.welcome);
        YoYo.with(Techniques.BounceInDown)
                .duration(1000)
                .repeat(3)
                .playOn(txt1);
        //set the animation
        YoYo.with(Techniques.BounceInDown)
                .duration(1000)
                .repeat(3)
                .playOn(txt2);

        YoYo.with(Techniques.Pulse)
                .duration(1000)
                .repeat(3)
                .delay(1000)
                .playOn(txt3);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.setText("W");
            }
        }, 300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append("e");
            }
        }, 500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append("l");
            }
        }, 700);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append("c");
            }
        }, 900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append("o");
            }
        }, 900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append("m");
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append("e");
            }
        }, 1200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append(" T");
            }
        }, 1400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append("o");
            }
        }, 1600);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append(" B");
            }
        }, 1800);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append("l");
            }
        }, 2000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append("o");
            }
        }, 2200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append("o");
            }
        }, 2400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append("d");
            }
        }, 2600);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append(" B");
            }
        }, 2800);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append("a");
            }
        }, 3000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append("n");
            }
        }, 3200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt3.append("k");
            }
        }, 3400);

        VideoView videoView = view.findViewById(R.id.video);
        String videoPath = "android.resource://" + requireActivity().getPackageName() + "/" + R.raw.blood6;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.setVolume(0, 0);

            }
        });
//set the spinner values
        Spinner spinner = (Spinner) view.findViewById(R.id.blood3);
        spinner.setOnItemSelectedListener(Bloodbank.this);
        List<String> categories = new ArrayList<String>();
        categories.add("Blood type");
        categories.add("A+");
        categories.add("A-");
        categories.add("B+");
        categories.add("B-");
        categories.add("AB+");
        categories.add("AB-");
        categories.add("O-");
        categories.add("O+");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        //declared all textview i.e number of donors
        final TextView t1 = view.findViewById(R.id.textView18);
        final TextView t2 = view.findViewById(R.id.textView19);
        final TextView t3 = view.findViewById(R.id.textView20);
        final TextView t4 = view.findViewById(R.id.textView21);
        final TextView t5 = view.findViewById(R.id.textView22);
        final TextView t6 = view.findViewById(R.id.textView23);
        final TextView t7 = view.findViewById(R.id.textView24);
        final TextView t8 = view.findViewById(R.id.textView26);

        try {
            SQLiteDatabase db = dbHandler.getReadableDatabase();
            String sql = "select  * from donor where bloodgrp = 'AB+' AND donor  = 'yes'";
            Cursor c1 = db.rawQuery(sql,null);
            String sql1 = "select * from donor where bloodgrp = 'A+' AND donor  = 'yes'";
            Cursor c2 = db.rawQuery(sql1,null);
            String sql2 = "select * from donor where bloodgrp = 'A-' AND donor  = 'yes'";
            Cursor c3 = db.rawQuery(sql2,null);
            String sql3 = "select * from donor where bloodgrp = 'B+' AND donor = 'yes'";
            Cursor c4 = db.rawQuery(sql3,null);
            String sql4 = "select * from donor where bloodgrp = 'B-' AND donor = 'yes'";
            Cursor c5 = db.rawQuery(sql4,null);
            String sql5 = "select * from donor where bloodgrp = 'AB-' AND donor= 'yes'";
            Cursor c6 = db.rawQuery(sql5,null);
            String sql6 = "select * from donor where bloodgrp = 'O+' AND donor = 'yes'";
            Cursor c7 = db.rawQuery(sql6,null);
            String sql7 = "select * from donor where bloodgrp = 'O-' AND donor= 'yes'";
            Cursor c8 = db.rawQuery(sql7,null);

            if (c1.moveToFirst()) {
                do {
                    @SuppressLint("Range")
                    int count = Integer.parseInt(String.valueOf(c1.getCount()));
                         t3.setText(String.valueOf(count));


                }
                while (c1.moveToNext());
            }
            else {
                t3.setText("0");
            }
            if (c2.moveToFirst()) {
                do {
                    @SuppressLint("Range")
                    String count = String.valueOf(c2.getCount());
                    t1.setText(count);

                }
                while (c2.moveToNext());
            }
            else {
                t1.setText("0");
            }
            if (c3.moveToFirst()) {
                do {
                    @SuppressLint("Range")
                    String count = String.valueOf(c3.getCount());
                    t7.setText(count);
                    
                }
                while (c3.moveToNext());
            }
            else {
                t7.setText("0");
            }

            if (c4.moveToFirst()) {
                do {
                    @SuppressLint("Range")
                    int count = Integer.parseInt(String.valueOf(c4.getCount()));
                    if(count>0) {
                        t2.setText(String.valueOf(count));
                    }else {
                        t2.setText("0");
                    }
                }
                while (c4.moveToNext());
            }
            else {
                t2.setText("0");
            }
            if (c5.moveToFirst()) {
                do {
                    @SuppressLint("Range")
                    String count = String.valueOf(c5.getCount());
                    t8.setText(count);
                    
                }
                while (c5.moveToNext());
            }
            else {
                t8.setText("0");
            }
            if (c6.moveToFirst()) {
                do {
                    @SuppressLint("Range")
                    String count = String.valueOf(c6.getCount());
                    t4.setText(count);
                    
                }
                while (c6.moveToNext());
            }
            else {
                t4.setText("0");
            }
            if (c7.moveToFirst()) {
                do {
                    @SuppressLint("Range")
                    String count = String.valueOf(c7.getCount());
                    t5.setText(count);
                    
                }
                while (c7.moveToNext());
            }
            else {
                t5.setText("0");
            }
            if (c8.moveToFirst()) {
                do {
                    @SuppressLint("Range")
                    String count = String.valueOf(c8.getCount());
                    t6.setText(count);
                    
                }
                while (c8.moveToNext());
            }
            else {
                t6.setText("0");
            }


        } catch (Exception e) {
                     Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
         }
        return view;
     }


    private void alertDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
         View mView = getLayoutInflater().inflate(R.layout.custom_dialog2, null);
        Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
        Button btn_okay = (Button) mView.findViewById(R.id.btn_okay);
        TextView bld = (TextView) mView.findViewById(R.id.bld);
        bld.setText("selected blood group is "+item);
        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();

        alertDialog.setCanceledOnTouchOutside(false);
         btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), "Request dismissed", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    SQLiteDatabase db = dbHandler.getWritableDatabase();
                    String sql1 = "delete from request where time < date('now','-2 day') AND Username = '" + Login.getUsername() + "'";
                    db.execSQL(sql1);

                        try {
                            Date d = new Date();
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                            String currentTime = sdf.format(d);
                            final TextView txt_inputText = (EditText) mView.findViewById(R.id.txt_input);
                            String message = txt_inputText.getText().toString();
                            if (message.equals("")) {
                                Toast.makeText(requireContext(), "Please enter reason", Toast.LENGTH_LONG).show();
                            } else {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date();
                                ContentValues values = new ContentValues();
                                values.put("bloodgrp",item.trim());
                                values.put("Name", name);
                                values.put("contactNo", contactno);
                                values.put("Username", Login.getUsername());
                                values.put("time",currentTime);
                                values.put("message",message);
                                values.put("Datetime1",dateFormat.format(date));
                            db.insertOrThrow("request",null, values);

                              Toast.makeText(requireContext(), "sending request...", Toast.LENGTH_SHORT).show();
                              Handler handler = new Handler(Looper.getMainLooper());
                              handler.postDelayed(new Runnable() {
                                  @Override
                                  public void run() {
                                      Toast.makeText(requireContext(), "Request sent", Toast.LENGTH_SHORT).show();

                                  }
                              }, 2000);
                                         }

                        }
                        catch (Exception e) {
                             
                                    Vibrator vibrator = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);
                                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

                                    Toast.makeText(requireContext(), "Please select blood group first", Toast.LENGTH_SHORT).show();

                            
                        }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                alertDialog.dismiss();
            }

        });
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.60);
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setLayout(width, height);
        }
        alertDialog.show();





    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

