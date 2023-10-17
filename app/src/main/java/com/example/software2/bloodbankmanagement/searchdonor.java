package com.example.software2.bloodbankmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class searchdonor extends Fragment implements AdapterView.OnItemSelectedListener {
     TextView textView;
     static List<Map<String,String>> data = null;
     private DBHandler dbHandler;
     static Spinner spinner;
   static SimpleAdapter ad;
     static String toast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void setMenuVisibility(boolean isvisible) {
        super.setMenuVisibility(isvisible);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_searchdonor, container, false);
        spinner = (Spinner) view.findViewById(R.id.blood2);

        toast = "select blood group to see the donors";
        dbHandler = new DBHandler(requireActivity());
         textView = view.findViewById(R.id.text2);
         new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("S");
            }
        }, 300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("e");
            }
        }, 500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("a");
            }
        }, 700);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("r");
            }
        }, 900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("c");
            }
        }, 1100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("h");
            }
        }, 1300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append(" D");
            }
        }, 1500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("o");
            }
        }, 1700);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("n");
            }
        }, 1900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("o");
            }
        }, 2100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("r");
            }
        }, 2300);


        List<String> categories = new ArrayList<>();
        categories.add("select blood");
        categories.add("A+");
        categories.add("A-");
        categories.add("B+");
        categories.add("B-");
        categories.add("AB+");
        categories.add("AB-");
        categories.add("O-");
        categories.add("O+");
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
               if(position == 1) {
                   data = new ArrayList<Map<String, String>>();
                   try {
                       SQLiteDatabase db = dbHandler.getReadableDatabase();
                       String sql = "select * from donor where bloodgrp='A+' and donor =  'yes'";
                       Cursor rs = db.rawQuery(sql,null);
                       if (rs.moveToFirst()){
                           do {
                               Map<String,String> dtname = new HashMap<String,String>();
                               dtname.put("name", "Name  :  " + rs.getString(rs.getColumnIndex("Name")));
                               dtname.put("Username", rs.getString(rs.getColumnIndex("Username")));
                               dtname.put("city", "City  :  " + rs.getString(rs.getColumnIndex("city")));
                               data.add(dtname);
                           }
                           while (rs.moveToNext());
                       }
                   } catch (Exception e) {
                       e.getMessage();
                   }
                   final ListView lst = (ListView) requireActivity().findViewById(R.id.list1);
                   List<Map<String, String>> MyDataList = null;
                   MyDataList = data;
                   String[] from = {"name","Username","city"};
                   int[] to = {R.id.name,R.id.Username,R.id.city};
                   ad = new SimpleAdapter(requireActivity(), MyDataList, R.layout.listitems, from, to);
                   lst.setAdapter(ad);


                   lst.setBackgroundColor(requireActivity().getColor(R.color.colorPrimary));
                   if(ad.getCount()==0){
                       Toast.makeText(requireActivity(), "There are no donors for the selected blood group", Toast.LENGTH_LONG).show();
                   }
                   lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                           HashMap<String, String> obj = (HashMap<String, String>) ad.getItem(i);
                           String name = (String) obj.get("Username");

                           try {
                               SQLiteDatabase db = dbHandler.getReadableDatabase();
                               String sql = "select * from donor where Username = '" + name + "'";
                               Cursor rs = db.rawQuery(sql,null);
                               if (rs.moveToFirst()) {
                                   do {
                                       try {

                                           final AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
                                           View mView = getLayoutInflater().inflate(R.layout.custom_dialog1, null);
                                           final TextView txt_inputText = (TextView) mView.findViewById(R.id.name1);
                                           txt_inputText.setText(rs.getString(rs.getColumnIndex("Name")));
                                           final TextView txt_inputText1 = (TextView) mView. findViewById(R.id.address);
                                           txt_inputText1.setText(rs.getString(rs.getColumnIndex("Address")));
                                           final TextView txt_inputText2 = (TextView) mView.findViewById(R.id.contactno1);
                                           txt_inputText2.setText(rs.getString(rs.getColumnIndex("contactNo")));
                                           final TextView email = (TextView) mView.findViewById(R.id.emaill);
                                           email.setText(rs.getString(rs.getColumnIndex("Email")));
                                           email.setPaintFlags(email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                           email.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Intent intent = new Intent(Intent.ACTION_SEND);
                                                   intent.setType("plain/text");
                                                   intent.putExtra(Intent.EXTRA_EMAIL, new String[]{String.valueOf(email.getText())});
                                                   intent.putExtra(Intent.EXTRA_SUBJECT, "Blood Bank Management");
                                                   intent.putExtra(Intent.EXTRA_TEXT, "There is an emergency for blood group ____");
                                                   startActivity(Intent.createChooser(intent, ""));

                                               }
                                           });
                                           final TextView txt_inputText3 = (TextView) mView.findViewById(R.id.bloodgroup);
                                           txt_inputText3.setText(rs.getString(rs.getColumnIndex("bloodgrp")));
                                           final ImageView img = (ImageView) mView.findViewById(R.id.img);
                                           if(Arrays.toString(rs.getBlob(rs.getColumnIndex("img"))).contains("null")){
                                               img.setBackgroundResource(R.drawable.profiler);
                                           }
                                           else {
                                               byte[] image = rs.getBlob(rs.getColumnIndex("img"));
                                               byte[] bytes = Base64.decode(image, Base64.DEFAULT);
                                               Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                               img.setImageBitmap(bitmap);

                                           }
                                           Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
                                           Button btn_okay = (Button) mView.findViewById(R.id.btn_okay);
                                           alert.setView(mView);
                                           final AlertDialog alertDialog = alert.create();
                                           alertDialog.setCanceledOnTouchOutside(false);
                                           btn_cancel.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   alertDialog.dismiss();
                                               }
                                           });
                                           btn_okay.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   String number = txt_inputText2.getText().toString();
                                                   Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));// Initiates the Intent
                                                   startActivity(intent);
                                                   alertDialog.dismiss();
                                               }

                                           });
                                           int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                                           int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.60);
                                           if (alertDialog.getWindow() != null) {
                                               alertDialog.getWindow().setLayout(width, height);
                                           }

                                           alertDialog.show();

                                       } catch (Exception e) {
                                           Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                       }


                                   }while (rs.moveToNext());
                               }

                           } catch (Exception e) {
                               Toast.makeText(requireActivity(),e.getMessage(),Toast.LENGTH_LONG).show();

                           }

                       }
                   });


               }
               if(position == 2) {
                   try {
                       data = new ArrayList<Map<String, String>>();
                       SQLiteDatabase db = dbHandler.getReadableDatabase();
                       String sql = "select * from donor where bloodgrp='A-' and  donor  =  'yes'";
                       Cursor rs = db.rawQuery(sql,null);
                       if (rs.moveToFirst()){
                           do {
                               Map<String, String> dtname = new HashMap<String, String>();
                               dtname.put("name", "Name  :  " + rs.getString(rs.getColumnIndex("Name")));
                               dtname.put("Username", rs.getString(rs.getColumnIndex("Username")));
                               dtname.put("city", "City  :  " + rs.getString(rs.getColumnIndex("city")));
                               data.add(dtname);

                           }while (rs.moveToNext());
                       }
                   } catch ( Exception e) {
                       e.getMessage();
                   }
                   final ListView lst = (ListView) requireActivity().findViewById(R.id.list1);
                   List<Map<String, String>> MyDataList = null;
                   MyDataList = data;
                   String[] from = {"name","Username","city"};
                   int[] to = {R.id.name,R.id.Username,R.id.city};
                   ad = new SimpleAdapter(requireActivity(), MyDataList, R.layout.listitems, from, to);
                   lst.setAdapter(ad);
                   lst.setBackgroundColor(requireActivity().getColor(R.color.colorPrimary));
                   if(ad.getCount()==0){
                       Toast.makeText(requireActivity(), "There are no donors for the selected blood group", Toast.LENGTH_LONG).show();
                   }
                   lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                           HashMap<String, String> obj = (HashMap<String, String>) ad.getItem(i);
                           String name = (String) obj.get("Username");

                           try {
                               SQLiteDatabase db = dbHandler.getReadableDatabase();
                               String sql = "select Name, contactNo,Address,bloodgrp,img,Email from donor where Username= '" + name + "'";
                               Cursor rs = db.rawQuery(sql,null);
                               if (rs.moveToFirst()) {
                                   do {
                                       try {
                                           final AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
                                           View mView = getLayoutInflater().inflate(R.layout.custom_dialog1, null);
                                           final TextView txt_inputText = (TextView) mView.findViewById(R.id.name1);
                                           txt_inputText.setText(rs.getString(rs.getColumnIndex("Name")));
                                           final TextView txt_inputText1 = (TextView) mView.findViewById(R.id.address);
                                           txt_inputText1.setText(rs.getString(rs.getColumnIndex("Address")));
                                           final TextView txt_inputText2 = (TextView) mView.findViewById(R.id.contactno1);
                                           txt_inputText2.setText(rs.getString(rs.getColumnIndex("contactNo")));
                                           final TextView email = (TextView) mView.findViewById(R.id.emaill);
                                           email.setText(rs.getString(rs.getColumnIndex("Email")));
                                           email.setPaintFlags(email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                                           email.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Intent intent = new Intent(Intent.ACTION_SEND);
                                                   intent.setType("plain/text");
                                                   intent.putExtra(Intent.EXTRA_EMAIL, new String[]{String.valueOf(email.getText())});
                                                   intent.putExtra(Intent.EXTRA_SUBJECT, "Blood Bank Management");
                                                   intent.putExtra(Intent.EXTRA_TEXT, "There is an emergency for blood group ____");
                                                   startActivity(Intent.createChooser(intent, ""));

                                               }
                                           });
                                           final TextView txt_inputText3 = (TextView) mView.findViewById(R.id.bloodgroup);
                                           txt_inputText3.setText(rs.getString(rs.getColumnIndex("bloodgrp")));
                                           final ImageView img = (ImageView) mView.findViewById(R.id.img);
                                           if(Arrays.toString(rs.getBlob(rs.getColumnIndex("img"))).contains("null")){
                                               img.setBackgroundResource(R.drawable.profiler);
                                           }
                                           else {
                                               byte[] image = rs.getBlob(rs.getColumnIndex("img"));
                                               byte[] bytes = Base64.decode(image, Base64.DEFAULT);
                                               Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                               img.setImageBitmap(bitmap);

                                           }
                                           Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
                                           Button btn_okay = (Button) mView.findViewById(R.id.btn_okay);
                                           alert.setView(mView);
                                           final AlertDialog alertDialog = alert.create();
                                           alertDialog.setCanceledOnTouchOutside(false);
                                           btn_cancel.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   alertDialog.dismiss();
                                               }
                                           });
                                           btn_okay.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   String number = txt_inputText2.getText().toString();
                                                   Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));// Initiates the Intent
                                                   startActivity(intent);
                                                   alertDialog.dismiss();
                                               }

                                           });
                                           int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                                           int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.60);
                                           if (alertDialog.getWindow() != null) {
                                               alertDialog.getWindow().setLayout(width, height);
                                           }
                                           alertDialog.show();

                                       } catch (Exception e) {
                                           Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

                                       }


                                   } while (rs.moveToNext());
                               }

                           } catch (Exception e) {
                               Toast.makeText(requireActivity(),e.getMessage(),Toast.LENGTH_LONG).show();

                           }

                       }
                   });


               }
               if(position == 3) {

                   data = new ArrayList<Map<String, String>>();
                   try {
                       SQLiteDatabase db = dbHandler.getReadableDatabase();
                       String sql = "select * from donor where bloodgrp='B+' and donor =  'yes'";
                       Cursor rs = db.rawQuery(sql,null);
                       if (rs.moveToFirst()){
                           do {
                               Map<String, String> dtname = new HashMap<String, String>();
                               dtname.put("name", "Name :  " + rs.getString(rs.getColumnIndex("Name")));
                               dtname.put("Username", rs.getString(rs.getColumnIndex("Username")));
                               dtname.put("city", "City :  " + rs.getString(rs.getColumnIndex("city")));
                               data.add(dtname);

                           }while (rs.moveToNext());
                       }
                   } catch ( Exception e) {
                       e.getMessage();
                   }

                   final ListView lst = (ListView) requireActivity().findViewById(R.id.list1);
                   List<Map<String, String>> MyDataList = null;
                   MyDataList = data;
                   String[] from = {"name","Username","city"};
                   int[] to = {R.id.name,R.id.Username,R.id.city};
                   ad = new SimpleAdapter(requireActivity(), MyDataList, R.layout.listitems, from, to);
                   lst.setAdapter(ad);
                   lst.setBackgroundColor(requireActivity().getColor(R.color.colorPrimary));
                   if(ad.getCount()==0){
                       Toast.makeText(requireActivity(), "There are no donors for the selected blood group", Toast.LENGTH_LONG).show();
                   }
                   lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                           HashMap<String, String> obj = (HashMap<String, String>) ad.getItem(i);
                           String name = (String) obj.get("Username");

                           try {
                               SQLiteDatabase db = dbHandler.getReadableDatabase();
                               String sql = "select Name, contactNo,Address,bloodgrp,img,Email from donor where Username = '" + name + "' and donor = 'yes'";
                               Cursor rs = db.rawQuery(sql,null);
                               if (rs.moveToFirst()) {
                                   do {
                                       try {

                                           final AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
                                           View mView = getLayoutInflater().inflate(R.layout.custom_dialog1, null);
                                           final TextView txt_inputText = (TextView) mView.findViewById(R.id.name1);
                                           txt_inputText.setText(rs.getString(rs.getColumnIndex("Name")));
                                           final TextView txt_inputText1 = (TextView) mView.findViewById(R.id.address);
                                           txt_inputText1.setText(rs.getString(rs.getColumnIndex("Address")));
                                           final TextView txt_inputText2 = (TextView) mView.findViewById(R.id.contactno1);
                                           txt_inputText2.setText(rs.getString(rs.getColumnIndex("contactNo")));
                                           final TextView email = (TextView) mView.findViewById(R.id.emaill);
                                           email.setText(rs.getString(rs.getColumnIndex("Email")));
                                           email.setPaintFlags(email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                                           email.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Intent intent = new Intent(Intent.ACTION_SEND);
                                                   intent.setType("plain/text");
                                                   intent.putExtra(Intent.EXTRA_EMAIL, new String[]{String.valueOf(email.getText())});
                                                   intent.putExtra(Intent.EXTRA_SUBJECT, "Blood Bank Management");
                                                   intent.putExtra(Intent.EXTRA_TEXT, "There is an emergency for blood group ____");
                                                   startActivity(Intent.createChooser(intent, ""));

                                               }
                                           });
                                           final TextView txt_inputText3 = (TextView) mView.findViewById(R.id.bloodgroup);
                                           txt_inputText3.setText(rs.getString(rs.getColumnIndex("bloodgrp")));
                                           final ImageView img = (ImageView) mView.findViewById(R.id.img);
                                           if(Arrays.toString(rs.getBlob(rs.getColumnIndex("img"))).contains("null")){
                                               img.setBackgroundResource(R.drawable.profiler);
                                           }
                                           else {
                                               byte[] image = rs.getBlob(rs.getColumnIndex("img"));
                                               byte[] bytes = Base64.decode(image, Base64.DEFAULT);
                                               Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                               img.setImageBitmap(bitmap);

                                           }                                            Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
                                           Button btn_okay = (Button) mView.findViewById(R.id.btn_okay);
                                           alert.setView(mView);
                                           final AlertDialog alertDialog = alert.create();
                                           alertDialog.setCanceledOnTouchOutside(false);
                                           btn_cancel.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   alertDialog.dismiss();
                                               }
                                           });
                                           btn_okay.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   String number = txt_inputText2.getText().toString();
                                                   Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));// Initiates the Intent
                                                   startActivity(intent);
                                                   alertDialog.dismiss();
                                               }

                                           });


                                           int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                                           int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.60);
                                           if (alertDialog.getWindow() != null) {
                                               alertDialog.getWindow().setLayout(width, height);
                                           }
                                           alertDialog.show();

                                       } catch (Exception e) {
                                           Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                       }

                                   }while (rs.moveToNext());
                               }

                           } catch (Exception e) {
                               Toast.makeText(requireActivity(),e.getMessage(),Toast.LENGTH_LONG).show();

                           }

                       }
                   });


               }
               if(position == 4) {
                   data = new ArrayList<Map<String, String>>();
                   try {
                       SQLiteDatabase db = dbHandler.getReadableDatabase();
                       String sql = "select * from donor where bloodgrp='B-' and donor =  'yes'";
                       Cursor rs = db.rawQuery(sql,null);
                       if (rs.moveToFirst()){
                           do {
                               Map<String, String> dtname = new HashMap<String, String>();
                               dtname.put("name", "Name  :  " + rs.getString(rs.getColumnIndex("Name")));
                               dtname.put("Username", rs.getString(rs.getColumnIndex("Username")));
                               dtname.put("city", "City  :  " + rs.getString(rs.getColumnIndex("city")));
                               data.add(dtname);

                           }while (rs.moveToNext());
                       }
                   } catch ( Exception e) {
                       e.getMessage();
                   }

                   final ListView lst = (ListView) requireActivity().findViewById(R.id.list1);
                   List<Map<String, String>> MyDataList = null;
                   MyDataList = data;
                   String[] from = {"name","Username","city"};
                   int[] to = {R.id.name,R.id.Username,R.id.city};
                   ad = new SimpleAdapter(requireActivity(), MyDataList, R.layout.listitems, from, to);
                   lst.setAdapter(ad);
                   lst.setBackgroundColor(requireActivity().getColor(R.color.colorPrimary));
                   if(ad.getCount()==0){
                       Toast.makeText(requireActivity(), "There are no donors for the selected blood group", Toast.LENGTH_LONG).show();
                   }
                   lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                           HashMap<String, String> obj = (HashMap<String, String>) ad.getItem(i);
                           String name = (String) obj.get("Username");

                           try {
                               SQLiteDatabase db = dbHandler.getReadableDatabase();
                               String sql = "select Name, contactNo,Address,bloodgrp,img,Email from donor where Username = '" + name + "' AND donor = 'yes'";
                               Cursor rs = db.rawQuery(sql,null);
                               if (rs.moveToFirst()) {
                                   do {

                                       try {

                                           final AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
                                           View mView = getLayoutInflater().inflate(R.layout.custom_dialog1, null);
                                           final TextView txt_inputText = (TextView) mView.findViewById(R.id.name1);
                                           txt_inputText.setText(rs.getString(rs.getColumnIndex("Name")));
                                           final TextView txt_inputText1 = (TextView) mView.findViewById(R.id.address);
                                           txt_inputText1.setText(rs.getString(rs.getColumnIndex("Address")));
                                           final TextView txt_inputText2 = (TextView) mView.findViewById(R.id.contactno1);
                                           txt_inputText2.setText(rs.getString(rs.getColumnIndex("contactNo")));
                                           final TextView email = (TextView) mView.findViewById(R.id.emaill);
                                           email.setText(rs.getString(rs.getColumnIndex("Email")));
                                           email.setPaintFlags(email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                                           email.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Intent intent = new Intent(Intent.ACTION_SEND);
                                                   intent.setType("plain/text");
                                                   intent.putExtra(Intent.EXTRA_EMAIL, new String[]{String.valueOf(email.getText())});
                                                   intent.putExtra(Intent.EXTRA_SUBJECT, "Blood Bank Management");
                                                   intent.putExtra(Intent.EXTRA_TEXT, "There is an emergency for blood group ____");
                                                   startActivity(Intent.createChooser(intent, ""));

                                               }
                                           });
                                           final TextView txt_inputText3 = (TextView) mView.findViewById(R.id.bloodgroup);
                                           txt_inputText3.setText(rs.getString(rs.getColumnIndex("bloodgrp")));
                                           final ImageView img = (ImageView) mView.findViewById(R.id.img);
                                           if(Arrays.toString(rs.getBlob(rs.getColumnIndex("img"))).contains("null")){
                                               img.setBackgroundResource(R.drawable.profiler);
                                           }
                                           else {
                                               byte[] image = rs.getBlob(rs.getColumnIndex("img"));
                                               byte[] bytes = Base64.decode(image, Base64.DEFAULT);
                                               Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                               img.setImageBitmap(bitmap);
                                           }    Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
                                           Button btn_okay = (Button) mView.findViewById(R.id.btn_okay);
                                           alert.setView(mView);
                                           final AlertDialog alertDialog = alert.create();
                                           alertDialog.setCanceledOnTouchOutside(false);
                                           btn_cancel.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   alertDialog.dismiss();
                                               }
                                           });
                                           btn_okay.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   String number = txt_inputText2.getText().toString();
                                                   Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));// Initiates the Intent
                                                   startActivity(intent);
                                                   alertDialog.dismiss();
                                               }

                                           });
                                           int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                                           int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.60);
                                           if (alertDialog.getWindow() != null) {
                                               alertDialog.getWindow().setLayout(width, height);
                                           }
                                           alertDialog.show();

                                       } catch (Exception e) {
                                           Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                       }



                                   }while (rs.moveToNext());
                               }

                           } catch (Exception e) {
                               Toast.makeText(requireActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                           }


                       }
                   });


               }
               if(position == 5) {
                   data = new ArrayList<Map<String, String>>();
                   try {
                       SQLiteDatabase db = dbHandler.getReadableDatabase();
                       String sql = "select * from donor where bloodgrp='AB+' and donor =  'yes'";
                       Cursor rs = db.rawQuery(sql,null);
                       if (rs.moveToFirst()){
                           do {
                               Map<String, String> dtname = new HashMap<String, String>();
                               dtname.put("name", "Name  :  " + rs.getString(rs.getColumnIndex("Name")));
                               dtname.put("Username", rs.getString(rs.getColumnIndex("Username")));
                               dtname.put("city", "City  :  " + rs.getString(rs.getColumnIndex("city")));
                               data.add(dtname);

                           }while (rs.moveToNext());
                       }
                   } catch ( Exception e) {
                       e.getMessage();
                   }

                   final ListView lst = (ListView) requireActivity().findViewById(R.id.list1);
                   List<Map<String, String>> MyDataList = null;
                   MyDataList = data;
                   String[] from = {"name","Username","city"};
                   int[] to = {R.id.name,R.id.Username,R.id.city};
                   ad = new SimpleAdapter(requireActivity(), MyDataList, R.layout.listitems, from, to);
                   lst.setAdapter(ad);
                   lst.setBackgroundColor(requireActivity().getColor(R.color.colorPrimary));
                   if(ad.getCount()==0){
                       Toast.makeText(requireActivity(), "There are no donors for the selected blood group", Toast.LENGTH_LONG).show();
                   }
                   lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                           HashMap<String, String> obj = (HashMap<String, String>) ad.getItem(i);
                           String name = (String) obj.get("Username");

                           try {
                               SQLiteDatabase db = dbHandler.getReadableDatabase();
                               String sql = "select Name, contactNo,Address,bloodgrp,img,Email from donor where Username = '" + name + "'";
                               Cursor rs = db.rawQuery(sql,null);
                               if (rs.moveToFirst()) {
                                   do {
                                       try {

                                           final AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
                                           View mView = getLayoutInflater().inflate(R.layout.custom_dialog1, null);
                                           final TextView txt_inputText = (TextView) mView.findViewById(R.id.name1);
                                           txt_inputText.setText(rs.getString(rs.getColumnIndex("Name")));
                                           final TextView txt_inputText1 = (TextView) mView.findViewById(R.id.address);
                                           txt_inputText1.setText(rs.getString(rs.getColumnIndex("Address")));
                                           final TextView txt_inputText2 = (TextView) mView.findViewById(R.id.contactno1);
                                           txt_inputText2.setText(rs.getString(rs.getColumnIndex("contactNo")));
                                           final TextView email = (TextView) mView.findViewById(R.id.emaill);
                                           email.setText(rs.getString(rs.getColumnIndex("Email")));
                                           email.setPaintFlags(email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                                           email.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Intent intent = new Intent(Intent.ACTION_SEND);
                                                   intent.setType("plain/text");
                                                   intent.putExtra(Intent.EXTRA_EMAIL, new String[]{String.valueOf(email.getText())});
                                                   intent.putExtra(Intent.EXTRA_SUBJECT, "Blood Bank Management");
                                                   intent.putExtra(Intent.EXTRA_TEXT, "There is an emergency for blood group ____");
                                                   startActivity(Intent.createChooser(intent, ""));

                                               }
                                           });
                                           final TextView txt_inputText3 = (TextView) mView.findViewById(R.id.bloodgroup);
                                           txt_inputText3.setText(rs.getString(rs.getColumnIndex("bloodgrp")));
                                           final ImageView img = (ImageView) mView.findViewById(R.id.img);
                                           if(Arrays.toString(rs.getBlob(rs.getColumnIndex("img"))).contains("null")){
                                               img.setBackgroundResource(R.drawable.profiler);
                                           }
                                           else {
                                               byte[] image = rs.getBlob(rs.getColumnIndex("img"));
                                               byte[] bytes = Base64.decode(image, Base64.DEFAULT);
                                               Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                               img.setImageBitmap(bitmap);

                                           }                                            Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
                                           Button btn_okay = (Button) mView.findViewById(R.id.btn_okay);
                                           alert.setView(mView);
                                           final AlertDialog alertDialog = alert.create();
                                           alertDialog.setCanceledOnTouchOutside(false);
                                           btn_cancel.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   alertDialog.dismiss();
                                               }
                                           });
                                           btn_okay.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   String number = txt_inputText2.getText().toString();
                                                   Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));// Initiates the Intent
                                                   startActivity(intent);
                                                   alertDialog.dismiss();
                                               }

                                           });
                                           int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                                           int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.60);
                                           if (alertDialog.getWindow() != null) {
                                               alertDialog.getWindow().setLayout(width, height);
                                           }
                                           alertDialog.show();

                                       } catch (Exception e) {
                                           Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

                                       }


                                   }while (rs.moveToNext());
                               }

                           } catch (Exception e) {
                               Toast.makeText(requireActivity(),e.getMessage(),Toast.LENGTH_LONG).show();

                           }

                       }
                   });


               }
               if(position == 6) {
                   data = new ArrayList<Map<String, String>>();
                   try {
                       SQLiteDatabase db = dbHandler.getReadableDatabase();
                       String sql = "select * from donor where bloodgrp='AB-' and donor =  'yes'";
                       Cursor rs = db.rawQuery(sql,null);
                       if (rs.moveToFirst()){
                           do {
                               Map<String, String> dtname = new HashMap<String, String>();
                               dtname.put("name", "Name  :  " + rs.getString(rs.getColumnIndex("Name")));
                               dtname.put("Username", rs.getString(rs.getColumnIndex("Username")));
                               dtname.put("city", "City  :  " + rs.getString(rs.getColumnIndex("city")));
                               data.add(dtname);

                           }while (rs.moveToNext());
                       }
                   } catch ( Exception e) {
                       e.getMessage();
                   }

                   final ListView lst = (ListView) requireActivity().findViewById(R.id.list1);
                   List<Map<String, String>> MyDataList = null;
                   MyDataList = data;
                   String[] from = {"name","Username","city"};
                   int[] to = {R.id.name,R.id.Username,R.id.city};
                   ad = new SimpleAdapter(requireActivity(), MyDataList, R.layout.listitems, from, to);
                   lst.setAdapter(ad);
                   lst.setBackgroundColor(requireActivity().getColor(R.color.colorPrimary));
                   if(ad.getCount()==0){
                       Toast.makeText(requireActivity(), "There are no donors for the selected blood group", Toast.LENGTH_LONG).show();
                   }
                   lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                           HashMap<String, String> obj = (HashMap<String, String>) ad.getItem(i);
                           String name = (String) obj.get("Username");

                           try {
                               SQLiteDatabase db = dbHandler.getReadableDatabase();
                               String sql = "select Name, contactNo,Address,bloodgrp,img,Email from donor where Username = '" + name + "'";
                               Cursor rs = db.rawQuery(sql,null);
                               if (rs.moveToFirst()) {
                                   do {

                                       try {

                                           final AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
                                           View mView = getLayoutInflater().inflate(R.layout.custom_dialog1, null);
                                           final TextView txt_inputText = (TextView) mView.findViewById(R.id.name1);
                                           txt_inputText.setText(rs.getString(rs.getColumnIndex("Name")));
                                           final TextView txt_inputText1 = (TextView) mView.findViewById(R.id.address);
                                           txt_inputText1.setText(rs.getString(rs.getColumnIndex("Address")));
                                           final TextView txt_inputText2 = (TextView) mView.findViewById(R.id.contactno1);
                                           txt_inputText2.setText(rs.getString(rs.getColumnIndex("contactNo")));
                                           final TextView email = (TextView) mView.findViewById(R.id.emaill);
                                           email.setText(rs.getString(rs.getColumnIndex("Email")));
                                           email.setPaintFlags(email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                                           email.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Intent intent = new Intent(Intent.ACTION_SEND);
                                                   intent.setType("plain/text");
                                                   intent.putExtra(Intent.EXTRA_EMAIL, new String[]{String.valueOf(email.getText())});
                                                   intent.putExtra(Intent.EXTRA_SUBJECT, "Blood Bank Management");
                                                   intent.putExtra(Intent.EXTRA_TEXT, "There is an emergency for blood group ____");
                                                   startActivity(Intent.createChooser(intent, ""));

                                               }
                                           });
                                           final TextView txt_inputText3 = (TextView) mView.findViewById(R.id.bloodgroup);
                                           txt_inputText3.setText(rs.getString(rs.getColumnIndex("bloodgrp")));
                                           final ImageView img = (ImageView) mView.findViewById(R.id.img);
                                           if(Arrays.toString(rs.getBlob(rs.getColumnIndex("img"))).contains("null")){
                                               img.setBackgroundResource(R.drawable.profiler);
                                           }
                                           else {
                                               byte[] image = rs.getBlob(rs.getColumnIndex("img"));
                                               byte[] bytes = Base64.decode(image, Base64.DEFAULT);
                                               Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                               img.setImageBitmap(bitmap);

                                           }                                            Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
                                           Button btn_okay = (Button) mView.findViewById(R.id.btn_okay);
                                           alert.setView(mView);
                                           final AlertDialog alertDialog = alert.create();
                                           alertDialog.setCanceledOnTouchOutside(false);
                                           btn_cancel.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   alertDialog.dismiss();
                                               }
                                           });
                                           btn_okay.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   String number = txt_inputText2.getText().toString();
                                                   Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));// Initiates the Intent
                                                   startActivity(intent);
                                                   alertDialog.dismiss();
                                               }

                                           });
                                           int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                                           int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.60);
                                           if (alertDialog.getWindow() != null) {
                                               alertDialog.getWindow().setLayout(width, height);
                                           }
                                           alertDialog.show();

                                       } catch (Exception e) {
                                           Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

                                       }


                                   }while (rs.moveToNext());
                               }

                           } catch (Exception e) {
                               Toast.makeText(requireActivity(),e.getMessage(),Toast.LENGTH_LONG).show();

                           }

                       }
                   });


               }
               if(position == 7) {
                   data = new ArrayList<Map<String, String>>();
                   try {
                       SQLiteDatabase db = dbHandler.getReadableDatabase();
                       String sql = "select * from donor where bloodgrp='O-' and donor =  'yes'";
                       Cursor rs = db.rawQuery(sql,null);
                       if (rs.moveToFirst()){
                           do {
                               Map<String, String> dtname = new HashMap<String, String>();
                               dtname.put("name", "Name  :  " + rs.getString(rs.getColumnIndex("Name")));
                               dtname.put("Username", rs.getString(rs.getColumnIndex("Username")));
                               dtname.put("city", "City  :  " + rs.getString(rs.getColumnIndex("city")));
                               data.add(dtname);

                           }while (rs.moveToNext());
                       }
                   } catch ( Exception e) {
                       e.getMessage();
                   }

                   final ListView lst = (ListView) requireActivity().findViewById(R.id.list1);
                   List<Map<String, String>> MyDataList = null;
                   MyDataList = data;
                   String[] from = {"name","Username","city"};
                   int[] to = {R.id.name,R.id.Username,R.id.city};
                   ad = new SimpleAdapter(requireActivity(), MyDataList, R.layout.listitems, from, to);
                   lst.setAdapter(ad);
                   lst.setBackgroundColor(requireActivity().getColor(R.color.colorPrimary));
                   if(ad.getCount()==0){
                       Toast.makeText(requireActivity(), "There are no donors for the selected blood group", Toast.LENGTH_LONG).show();
                   }
                   lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                           HashMap<String, String> obj = (HashMap<String, String>) ad.getItem(i);
                           String name = (String) obj.get("Username");

                           try {
                               SQLiteDatabase db = dbHandler.getReadableDatabase();
                               String sql = "select Name, contactNo,Address,bloodgrp,img,Email from donor where Username = '" + name + "'";
                               Cursor rs = db.rawQuery(sql,null);
                               if (rs.moveToFirst()) {
                                   do {
                                       try {

                                           final AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
                                           View mView = getLayoutInflater().inflate(R.layout.custom_dialog1, null);
                                           final TextView txt_inputText = (TextView) mView.findViewById(R.id.name1);
                                           txt_inputText.setText(rs.getString(rs.getColumnIndex("Name")));
                                           final TextView txt_inputText1 = (TextView) mView.findViewById(R.id.address);
                                           txt_inputText1.setText(rs.getString(rs.getColumnIndex("Address")));
                                           final TextView txt_inputText2 = (TextView) mView.findViewById(R.id.contactno1);
                                           txt_inputText2.setText(rs.getString(rs.getColumnIndex("contactNo")));
                                           final TextView email = (TextView) mView.findViewById(R.id.emaill);
                                           email.setText(rs.getString(rs.getColumnIndex("Email")));
                                           email.setPaintFlags(email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                                           email.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Intent intent = new Intent(Intent.ACTION_SEND);
                                                   intent.setType("plain/text");
                                                   intent.putExtra(Intent.EXTRA_EMAIL, new String[]{String.valueOf(email.getText())});
                                                   intent.putExtra(Intent.EXTRA_SUBJECT, "Blood Bank Management");
                                                   intent.putExtra(Intent.EXTRA_TEXT, "There is an emergency for blood group ____");
                                                   startActivity(Intent.createChooser(intent, ""));

                                               }
                                           });
                                           final TextView txt_inputText3 = (TextView) mView.findViewById(R.id.bloodgroup);
                                           txt_inputText3.setText(rs.getString(rs.getColumnIndex("bloodgrp")));
                                           final ImageView img = (ImageView) mView.findViewById(R.id.img);
                                           if(Arrays.toString(rs.getBlob(rs.getColumnIndex("img"))).contains("null")){
                                               img.setBackgroundResource(R.drawable.profiler);
                                           }
                                           else {
                                               byte[] image = rs.getBlob(rs.getColumnIndex("img"));
                                               byte[] bytes = Base64.decode(image, Base64.DEFAULT);
                                               Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                               img.setImageBitmap(bitmap);

                                           }                                            Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
                                           Button btn_okay = (Button) mView.findViewById(R.id.btn_okay);
                                           alert.setView(mView);
                                           final AlertDialog alertDialog = alert.create();
                                           alertDialog.setCanceledOnTouchOutside(false);
                                           btn_cancel.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   alertDialog.dismiss();
                                               }
                                           });
                                           btn_okay.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   String number = txt_inputText2.getText().toString();
                                                   Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));// Initiates the Intent
                                                   startActivity(intent);
                                                   alertDialog.dismiss();
                                               }

                                           });
                                           int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                                           int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.60);
                                           if (alertDialog.getWindow() != null) {
                                               alertDialog.getWindow().setLayout(width, height);
                                           }
                                           alertDialog.show();

                                       } catch (Exception e) {
                                           Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                       }



                                   }while (rs.moveToNext());
                               }

                           } catch (Exception e) {
                               Toast.makeText(requireActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                           }


                       }
                   });


               }
               if(position == 8) {
                   data = new ArrayList<Map<String, String>>();
                   try {
                       SQLiteDatabase db = dbHandler.getReadableDatabase();
                       String sql = "select * from donor where bloodgrp='O+' and donor =  'yes'";
                       Cursor rs = db.rawQuery(sql,null);
                       if (rs.moveToFirst()){
                           do {
                               Map<String, String> dtname = new HashMap<String, String>();
                               dtname.put("name", "Name  :  " + rs.getString(rs.getColumnIndex("Name")));
                               dtname.put("Username", rs.getString(rs.getColumnIndex("Username")));
                               dtname.put("city", "City  :  " + rs.getString(rs.getColumnIndex("city")));
                               data.add(dtname);

                           }while (rs.moveToNext());
                       }
                   } catch ( Exception e) {
                       e.getMessage();
                   }

                   final ListView lst = (ListView) requireActivity().findViewById(R.id.list1);
                   List<Map<String, String>> MyDataList = null;
                   MyDataList = data;
                   String[] from = {"name","Username","city"};
                   int[] to = {R.id.name,R.id.Username,R.id.city};
                   ad = new SimpleAdapter(requireActivity(), MyDataList, R.layout.listitems, from, to);
                   lst.setAdapter(ad);
                   lst.setBackgroundColor(requireActivity().getColor(R.color.colorPrimary));
                   if(ad.getCount()==0){
                       Toast.makeText(requireActivity(), "There are no donors for the selected blood group", Toast.LENGTH_LONG).show();
                   }
                   lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                           HashMap<String, String> obj = (HashMap<String, String>) ad.getItem(i);
                           String name = (String) obj.get("Username");

                           try {
                               SQLiteDatabase db = dbHandler.getReadableDatabase();
                               String sql = "select Name, contactNo,Address,bloodgrp,img,Email from donor where Username = '" + name + "'";
                               Cursor rs = db.rawQuery(sql,null);
                               if (rs.moveToFirst()) {
                                   do {
                                       try {

                                           final AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
                                           View mView = getLayoutInflater().inflate(R.layout.custom_dialog1, null);
                                           final TextView txt_inputText = (TextView) mView.findViewById(R.id.name1);
                                           txt_inputText.setText(rs.getString(rs.getColumnIndex("Name")));
                                           final TextView txt_inputText1 = (TextView) mView.findViewById(R.id.address);
                                           txt_inputText1.setText(rs.getString(rs.getColumnIndex("Address")));
                                           final TextView txt_inputText2 = (TextView) mView.findViewById(R.id.contactno1);
                                           txt_inputText2.setText(rs.getString(rs.getColumnIndex("contactNo")));
                                           final TextView email = (TextView) mView.findViewById(R.id.emaill);
                                           email.setText(rs.getString(rs.getColumnIndex("Email")));
                                           email.setPaintFlags(email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                                           email.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Intent intent = new Intent(Intent.ACTION_SEND);
                                                   intent.setType("plain/text");
                                                   intent.putExtra(Intent.EXTRA_EMAIL, new String[]{String.valueOf(email.getText())});
                                                   intent.putExtra(Intent.EXTRA_SUBJECT, "Blood Bank Management");
                                                   intent.putExtra(Intent.EXTRA_TEXT, "There is an emergency for blood group ____");
                                                   startActivity(Intent.createChooser(intent, ""));

                                               }
                                           });
                                           final TextView txt_inputText3 = (TextView) mView.findViewById(R.id.bloodgroup);
                                           txt_inputText3.setText(rs.getString(rs.getColumnIndex("bloodgrp")));
                                           final ImageView img = (ImageView) mView.findViewById(R.id.img);
                                           if(Arrays.toString(rs.getBlob(rs.getColumnIndex("img"))).contains("null")){
                                               img.setBackgroundResource(R.drawable.profiler);
                                           }
                                           else {
                                               byte[] image = rs.getBlob(rs.getColumnIndex("img"));
                                               byte[] bytes = Base64.decode(image, Base64.DEFAULT);
                                               Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                               img.setImageBitmap(bitmap);
                                           }
                                           Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
                                           Button btn_okay = (Button) mView.findViewById(R.id.btn_okay);
                                           alert.setView(mView);
                                           final AlertDialog alertDialog = alert.create();
                                           alertDialog.setCanceledOnTouchOutside(false);
                                           btn_cancel.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   alertDialog.dismiss();
                                               }
                                           });
                                           btn_okay.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   String number = txt_inputText2.getText().toString();
                                                   Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));// Initiates the Intent
                                                   startActivity(intent);
                                                   alertDialog.dismiss();
                                               }

                                           });
                                           int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                                           int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.60);
                                           if (alertDialog.getWindow() != null) {
                                               alertDialog.getWindow().setLayout(width, height);
                                           }
                                           alertDialog.show();

                                       } catch (Exception e) {
                                           Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

                                       }


                                   }while (rs.moveToNext());
                               }

                           } catch (Exception e) {
                               Toast.makeText(requireActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                           }


                       }
                   });


               }

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });



            // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(isVisible());

    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.helpMenu).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }



    @SuppressLint("Range")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

  }