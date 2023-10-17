package com.example.software2.bloodbankmanagement;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

public class Profile extends Fragment {

    public static String toast;
    Button help, upload;
    static String username;
    String encodedImage;
    private DBHandler dbHandler;
    ImageView imageView;
    public static final int REQUEST_LOAD_IMAGE = 1;

    /**
     * This is our profile activity. When registration of user is successfully
     * we insert all the data of user in registration table. So when user open profile acivity
     * we have to shown all the information that stored in database .
     **/
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
        View view = inflater.inflate(R.layout.my_profile, container, false);

        final TextView ani = view.findViewById(R.id.textView7);
        upload = view.findViewById(R.id.upload);
        imageView = view.findViewById(R.id.img2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ani.setText("W");
            }
        }, 700);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ani.append("e");
            }
        }, 900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ani.append("l");
            }
        }, 1100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ani.append("c");
            }
        }, 1300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ani.append("o");
            }
        }, 1500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ani.append("m");
            }
        }, 1700);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ani.append("e");
            }
        }, 1900);

        dbHandler = new DBHandler(view.getContext());
        username = Login.getUsername();

        new Handler().postDelayed(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                ani.append("  " + username);
            }
        }, 2100);
        //set the username on the textview at top. welcome ...... <-- Username

        final TextView tt2 = view.findViewById(R.id.uid2);
        final CheckBox checkBox = view.findViewById(R.id.checkBox2);
        checkBox.setEnabled(false);
        final ImageView imageView = view.findViewById(R.id.img2);
        final EditText edt = view.findViewById(R.id.blood);
        final EditText email = view.findViewById(R.id.email);
        email.setEnabled(false);
        final EditText age = view.findViewById(R.id.age);
        age.setEnabled(false);
        final EditText gender = view.findViewById(R.id.gender);
        gender.setEnabled(false);
        final EditText name = view.findViewById(R.id.name);
        name.setEnabled(false);
        final EditText contactNo = view.findViewById(R.id.contactno);
        contactNo.setEnabled(false);
        final EditText city = view.findViewById(R.id.city);
        city.setEnabled(false);
        final EditText address = view.findViewById(R.id.Address);
        address.setEnabled(false);
        final Button button = view.findViewById(R.id.edit);
        final Button upload = view.findViewById(R.id.upload);
        upload.setVisibility(View.VISIBLE);
        upload.setClickable(false);
        final Button cancel = view.findViewById(R.id.cancel);
        cancel.setVisibility(View.GONE);
        final Button update = view.findViewById(R.id.update);
        update.setVisibility(View.GONE);
        try {
            SQLiteDatabase db = dbHandler.getReadableDatabase();
            String sql1 = "select * from donor where Username = '" + username + "'";
            Cursor c = db.rawQuery(sql1, null);
            if (c.moveToFirst()) {
                do {
                    @SuppressLint("CutPasteId") final CheckBox checkBox1 = view.findViewById(R.id.checkBox2);
                    if (c.getString(5).contains("yes")) {
                        checkBox1.setChecked(true);
                    }
                    if (c.getString(5).contains("no")) {
                        checkBox1.setChecked(false);
                    }

                }
                while (c.moveToNext());
            } else {
                @SuppressLint("CutPasteId") final CheckBox checkBox1 = view.findViewById(R.id.checkBox2);
                checkBox1.setChecked(false);

            }
        } catch (Exception e) {

            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        try {
            SQLiteDatabase db = dbHandler.getReadableDatabase();
            String query = "SELECT * FROM register WHERE Username = '" + username + "';";
            Cursor c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {

                    try {
                        name.setText(c.getString(0));
                        name.setEnabled(false);
                        age.setText(c.getString(1));
                        age.setEnabled(false);
                        gender.setText(c.getString(2));
                        gender.setEnabled(false);
                        email.setText(c.getString(3));
                        email.setEnabled(false);
                        contactNo.setText(c.getString(7));
                        contactNo.setEnabled(false);
                        address.setText(c.getString(8));
                        address.setEnabled(false);
                        city.setText(c.getString(9));
                        city.setEnabled(false);
                        edt.setText(c.getString(11));
                        edt.setEnabled(false);
                        tt2.setText(c.getString(12));
                        byte[] image = c.getBlob(10);
                        if (!(image.length > 0)) {
                            imageView.setBackgroundColor(getActivity().getColor(R.color.black));
                        } else {
                            byte[] bytes = Base64.decode(image, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            imageView.setImageBitmap(bitmap);
                        }

                    } catch (Exception e) {
                        toast = "You have not uploaded profile picture";

                    }

                }
                while (c.moveToNext());
            }
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();


        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CheckBox checkBox1 = view.findViewById(R.id.checkBox2);
                email.setEnabled(false);
                name.setEnabled(false);
                contactNo.setEnabled(false);
                city.setEnabled(false);
                address.setEnabled(false);
                age.setEnabled(false);
                edt.setEnabled(false);
                gender.setEnabled(false);
                checkBox1.setEnabled(false);
                update.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
            }

        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CheckBox checkBox1 = view.findViewById(R.id.checkBox2);
                cancel.setVisibility(View.VISIBLE);
                update.setVisibility(View.VISIBLE);
                upload.setVisibility(View.VISIBLE);
                email.setEnabled(true);
                name.setEnabled(true);
                contactNo.setEnabled(true);
                upload.setClickable(true);
                city.setEnabled(true);
                address.setEnabled(true);
                age.setEnabled(true);
                edt.setEnabled(true);
                gender.setEnabled(true);
                checkBox1.setEnabled(true);
                edt.setEnabled(true);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_LOAD_IMAGE);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CheckBox checkBox1 = view.findViewById(R.id.checkBox2);
                cancel.setVisibility(View.GONE);
                update.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
                email.setEnabled(false);
                name.setEnabled(false);
                contactNo.setEnabled(false);
                city.setEnabled(false);
                address.setEnabled(false);
                age.setEnabled(false);
                edt.setEnabled(false);
                gender.setEnabled(false);
                checkBox1.setEnabled(false);
                updateUser();
            }
        });

        final Button logout = view.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Are you sure want to Exit?");
                builder.setMessage("Your account will be deleted permanently.");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(view.getContext(), "Your account has been permanently deleted.", Toast.LENGTH_LONG).show();
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    SQLiteDatabase db = dbHandler.getWritableDatabase();
                                    String sql2 = "delete from register where Username = '" + Login.getUsername() + "'";
                                    db.execSQL(sql2);
                                    String sql3 = "delete from donor where Username = '" + Login.getUsername() + "'";
                                    db.execSQL(sql3);
                                    Intent i = new Intent(view.getContext(), Login.class);
                                    startActivity(i);
                                    requireActivity().finishAffinity();
                                    Intent intent = new Intent(view.getContext(), Login.class);
                                    startActivity(intent);

                                } catch (Exception e) {

                                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        }, 1000);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertdialog = builder.create();
                //show the alertdialog
                alertdialog.show();


            }
        });


        help = view.findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), help.class);
                startActivity(i);

            }
        });
        return view;
    }

    public void updateUser() {

        final EditText edt = requireActivity().findViewById(R.id.blood);
        final EditText email = requireActivity().findViewById(R.id.email);
        final EditText age = requireActivity().findViewById(R.id.age);
        final EditText gender = requireActivity().findViewById(R.id.gender);
        final EditText city = requireActivity().findViewById(R.id.city);
        final EditText name = requireActivity().findViewById(R.id.name);
        final EditText contactNo = requireActivity().findViewById(R.id.contactno);
        final EditText address = requireActivity().findViewById(R.id.Address);

        try {
            SQLiteDatabase db = dbHandler.getWritableDatabase();
            String sql = "update register set Name='" + name.getText() + "',Age=" + age.getText() + ",gender='" + gender.getText() + "',Email='" + email.getText() + "',contactNo='" + contactNo.getText() + "',Address='" + address.getText() + "',city='" + city.getText() + "',bloodgrp='" + edt.getText() + "',img = '" + encodedImage + "' where Username ='" + Login.getUsername() + "'";
            db.execSQL(sql);

            Toast.makeText(requireActivity(), "Updated successfully", Toast.LENGTH_LONG).show();


        } catch (Exception e) {
            Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }


        final CheckBox checkBox1 = requireActivity().findViewById(R.id.checkBox2);
        if (checkBox1.isChecked()) {
            try {
                SQLiteDatabase db = dbHandler.getWritableDatabase();
                String sql1 = "update donor set Name='" + name.getText() + "',contactNo='" + contactNo.getText() + "',Address='" + address.getText() + "',city='" + city.getText() + "',bloodgrp='" + edt.getText() + "',Email='" + email.getText() + "',donor='yes',img='" + encodedImage + "' where Username = '" + Login.getUsername() + "'";
                db.execSQL(sql1);

            } catch (Exception e) {
                Toast.makeText(requireActivity(), "Please enter all fields and correct information", Toast.LENGTH_LONG).show();

            }
        } else {
            try {
                SQLiteDatabase db = dbHandler.getWritableDatabase();
                String sql1 = "update donor set Name='" + name.getText() + "',contactNo='" + contactNo.getText() + "',Address='" + address.getText() + "',city='" + city.getText() + "',bloodgrp='" + edt.getText() + "',Email='" + email.getText() + "',donor='no',img = '" + encodedImage + "' where Username ='" + Login.getUsername() + "'";
                db.execSQL(sql1);

            } catch (Exception e) {
                Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }


    }


         @Override
         public void onActivityResult(int requestCode, int resultCode, Intent data) {
           super.onActivityResult(requestCode, resultCode, data);
             if (requestCode == REQUEST_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
               Bitmap originBitmap = null;
               Uri selectedImage = data.getData();
               InputStream imageStream;
               Toast.makeText(requireActivity(), "Image successfully uploaded", Toast.LENGTH_SHORT).show();
               final Handler h = new Handler(Looper.getMainLooper());
               h.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       Toast.makeText(requireActivity(), "Please click on edit button and update your profile picture.", Toast.LENGTH_LONG).show();
                   }
               },4000);
               try {

                   imageStream = requireActivity().getContentResolver().openInputStream(selectedImage);
                   originBitmap = BitmapFactory.decodeStream(imageStream);
                   originBitmap = originBitmap.copy(Bitmap.Config.ARGB_8888, true);

               } catch (FileNotFoundException e) {
                            Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
               }
               if (originBitmap != null) {
                   originBitmap.setPixel(24, 24, requireActivity().getColor(R.color.white));
                   this.imageView.setImageBitmap(originBitmap);
                   Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                   ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                   image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                   encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

               }
           }

      }


}







