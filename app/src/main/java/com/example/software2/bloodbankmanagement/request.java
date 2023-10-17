package com.example.software2.bloodbankmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class request extends Fragment {

    SimpleAdapter ad;
    static String datetime;
    TextView list3;
    static String toast;
     static List<Map<String, String>> data = null;
    private DBHandler dbHandler;

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("Range")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_request, container, false);


        dbHandler = new DBHandler(view.getContext());
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        list3 = view.findViewById(R.id.list3);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list3.setText("B");
            }
        }, 300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list3.append("l");
            }
        }, 400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list3.append("o");
            }
        }, 500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list3.append("o");
            }
        }, 600);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list3.append("d");
            }
        }, 700);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list3.append(" R");
            }
        }, 800);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list3.append("e");
            }
        }, 900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list3.append("q");
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list3.append("u");
            }
        }, 1100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list3.append("e");
            }
        }, 1200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list3.append("s");
            }
        }, 1300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list3.append("t");
            }
        }, 1400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list3.append("s");
            }
        }, 1500);
        TextView txt = view.findViewById(R.id.list4);


        data = new ArrayList<Map<String, String>>();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            //Get current date or today's date records because we have to display only today's requests.
            String sql2 = "SELECT * from request WHERE Datetime1 = '" + dateFormat.format(date) + "'";
            Cursor rs = db.rawQuery(sql2, null);
            if (rs.moveToFirst()) {
                do {
                    SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");

                    try {
                        datetime = myFormat.format(fromUser.parse(rs.getString(rs.getColumnIndex("Datetime1"))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Map<String, String> dtname = new HashMap<String, String>();
                    dtname.put("bloodgrp", rs.getString(rs.getColumnIndex("bloodgrp")));
                    dtname.put("date", "Date:- " + datetime);
                    dtname.put("time", "Time:- " + rs.getString(rs.getColumnIndex("time")));
                    dtname.put("name", "Name:- " + rs.getString(rs.getColumnIndex("Name")));
                    dtname.put("Username", rs.getString(rs.getColumnIndex("Username")));
                    data.add(dtname);

                }
                while (rs.moveToNext());

            }
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        ListView lst1 = (ListView) view.findViewById(R.id.list2);
        List<Map<String, String>> MyDataList = null;
        MyDataList = data;
         String[] from = {"bloodgrp", "date", "time", "name", "Username"};
        int[] to = {R.id.listt, R.id.date, R.id.time1, R.id.name3, R.id.user};
        ad = new SimpleAdapter(view.getContext(), MyDataList, R.layout.listrequest, from, to);

        lst1.setAdapter(ad);

        lst1.setBackgroundColor(view.getContext().getColor(R.color.colorPrimary));
        if (ad.getCount() == 0) {
            toast = "No requests are currently visible";
        }

        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                List<Map<String, String>> MyDataList1 = null;
                MyDataList1 = data;
                String[] from = {"Username"};
                int[] to = {R.id.user};
                ad = new SimpleAdapter(view.getContext(), MyDataList1, R.layout.listrequest, from, to);

                HashMap<String, String> obj = (HashMap<String, String>) ad.getItem(i);
                String name = (String) obj.get("Username");


                try {
                    SQLiteDatabase db = dbHandler.getReadableDatabase();
                    String sql = "select * from request where Username = '" + name + "'";
                    Cursor rs = db.rawQuery(sql, null);
                    if (rs.moveToFirst()) {
                        do {
                            try {

                                final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                                View mView = getLayoutInflater().inflate(R.layout.custom_dialog3, null);
                                final TextView txt_inputText = (TextView) mView.findViewById(R.id.name1);
                                txt_inputText.setText(rs.getString(rs.getColumnIndex("Name")));
                                final TextView txt_inputText2 = (TextView) mView.findViewById(R.id.contactno1);
                                txt_inputText2.setText(rs.getString(rs.getColumnIndex("contactNo")));
                                final TextView txt_inputText3 = (TextView) mView.findViewById(R.id.message1);
                                txt_inputText3.setText(rs.getString(rs.getColumnIndex("message")));
                                txt_inputText3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(view.getContext(), "This is the message from the person", Toast.LENGTH_LONG).show();
                                    }
                                });

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
                                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }


                        } while (rs.moveToNext());
                    }


                } catch (Exception e) {
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.setText("O");
            }
        }, 300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("n");
            }
        }, 500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("l");
            }
        }, 700);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("y");
            }
        }, 900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append(" t");
            }
        }, 1100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("o");
            }
        }, 1300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("d");
            }
        }, 1500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("a");
            }
        }, 1700);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("y'");
            }
        }, 1900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("s");
            }
        }, 2100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append(" r");
            }
        }, 2300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("e");
            }
        }, 2500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("q");
            }
        }, 2700);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("u");
            }
        }, 2900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("e");
            }
        }, 3100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("s");
            }
        }, 3300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("t");
            }
        }, 3500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("s");
            }
        }, 3700);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append(" a");
            }
        }, 3900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("r");
            }
        }, 4100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("e");
            }
        }, 4100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append(" v");
            }
        }, 4300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("i");
            }
        }, 4500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("s");
            }
        }, 4700);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("i");
            }
        }, 4900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("b");
            }
        }, 5100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("l");
            }
        }, 5300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("e");
            }
        }, 5500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append(" t");
            }
        }, 5700);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("o");
            }
        }, 5800);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append(" a");
            }
        }, 5900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("l");
            }
        }, 6000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("l");
            }
        }, 6100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append(" p");
            }
        }, 6200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("e");
            }
        }, 6300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("o");
            }
        }, 6400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("p");
            }
        }, 6500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("l");
            }
        }, 6600);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.append("e");
            }
        }, 6700);


        return view;
    }



}





