package com.example.software2.bloodbankmanagement;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public  class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
   static EditText name, email, password,
            contactno, Address, city, Age, username;
    Button registerbtn;
    private static DBHandler dbHandler;
    static Spinner spinner;
     public static final int REQUEST_LOAD_IMAGE = 1;
    //to start gallery intent or load the image from external storage (OnActivityResult) we need request code.
    ImageView img;
    Button btnupload;
    static String gender1 ="";
    //to pass the RadioButton checked values, check line no.479
    static String item;
    //pass string value of spinner i.e blood group
    TextView status1, uid,registration;
    ProgressBar progressBar;
    //to show the progress bar when uploading image
    String encodedImage;
    //Decode the uploaded image in byte code to stored in database
      CheckBox blood;
     //for selecting if user want to be a donor or not
    static RadioButton Male,Female;
    private TextToSpeech textToSpeech;
     static boolean isAllFieldsChecked = true;
    //created method at line 539


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        SlidrInterface slidr = Slidr.attach(this);
        slidr.unlock();

         dbHandler = new DBHandler(this);
        status1 = findViewById(R.id.status1);
        Male = findViewById(R.id.Male);
        Female = findViewById(R.id.Female);
        registration = findViewById(R.id.registration);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registration.setText("R");
            }
        }, 300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registration.append("e");
            }
        }, 500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registration.append("g");
            }
        }, 700);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registration.append("i");
            }
        }, 900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registration.append("s");
            }
        }, 1100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registration.append("t");
            }
        }, 1300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registration.append("r");
            }
        }, 1500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registration.append("a");
            }
        }, 1700);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registration.append("t");
            }
        }, 1900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registration.append("i");
            }
        }, 2100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registration.append("o");
            }
        }, 2300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registration.append("n");
            }
        }, 2500);

        uid = findViewById(R.id.uid);
        Random random = new Random();
        //we require random uid for donor
        int val = random.nextInt(900) + 100;
        uid.setText(Integer.toString(val));
      /**sometimes uid is same for two or more donors so we check whether given uid
      is already present in database or not. below is the code where we will check the Uid of each donor
      if it same then will again start same actvity to get new uid. */
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql = "select  * from register where Uid = '"+val+"'";
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                Toast.makeText(getApplicationContext(), "sorry for inconvenience...", Toast.LENGTH_SHORT).show();
                Intent i = getIntent();
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
            while (cursor.moveToNext());
        }



        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.setSpeechRate(0.8f);
                }
            }
        });

        blood = findViewById(R.id.checkBox);
        blood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if (blood.isChecked()){
                    textToSpeech.speak("selected I want to be a donor", TextToSpeech.QUEUE_FLUSH, null);
            }
                else {
                    textToSpeech.speak("selected I don't want to be a donor", TextToSpeech.QUEUE_FLUSH,null);

                }
        }
        });

        name = (EditText) findViewById(R.id.name);
         username = (EditText) findViewById(R.id.username);
         img = (ImageView) findViewById(R.id.img);
        Age = (EditText) findViewById(R.id.age);
     final TextView  gender2 = findViewById(R.id.gend);
         btnupload = (Button) findViewById(R.id.btnupload);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        contactno = (EditText) findViewById(R.id.contactno);
        Address = (EditText) findViewById(R.id.Address);
         progressBar.setVisibility(View.GONE);
         //If you don't need to see progressbar then set it as GONE.
        city = findViewById(R.id.city);
         registerbtn = (Button) findViewById(R.id.regbtn);


         spinner = (Spinner) findViewById(R.id.blood);
         spinner.setOnItemSelectedListener(RegisterActivity.this);
        List<String> categories = new ArrayList<String>();
        categories.add("select blood");
        categories.add("A+");
        categories.add("A-");
        categories.add("B+");
        categories.add("B-");
        categories.add("AB+");
        categories.add("AB-");
        categories.add("O-");
        categories.add("O+");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        Age.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // When focus is lost check that the text field has valid values.

                if (!hasFocus) {

                    if (view.getId() == R.id.age) {
                        String x = Age.getText().toString();
                        if (x.equals("")) {
                            Age.setError("Age is required");
                            Toast.makeText(getApplicationContext(), "Age is required", Toast.LENGTH_LONG).show();

                        } else {
                            int x1 = Integer.parseInt(x);
                            if (x1 < 18) {
                                Age.setText("");
                                Toast.makeText(getApplicationContext(), "Age must be greater than 18", Toast.LENGTH_LONG).show();
                                Age.setError("Age must be greater than 18");
                                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

                            } else if (x1 > 65) {
                                Age.setText("");
                                Age.setError("Age must be less than 65");
                                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                                Toast.makeText(getApplicationContext(), "Age must be less than 65", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                }

            }
        });
        contactno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    switch (view.getId()) {
                        case R.id.contactno:
                            if (contactno.length() > 10) {
                                contactno.setError("Contact number must be 10 digit");
                                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                                Toast.makeText(getApplicationContext(), "Contact number must be 10 digit", Toast.LENGTH_LONG).show();
                             } else if (contactno.length() < 10) {
                                contactno.setError("Contact number must be 10 digit");
                               Toast.makeText(getApplicationContext(), "Contact number must be 10 digit", Toast.LENGTH_LONG).show();
                             }
                            break;
                    }
                }
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {

                    if (view.getId() == R.id.password) {
                        if (password.length() == 0) {
                            YoYo.with(Techniques.Shake)
                                    .playOn(password);
                            Toast.makeText(getApplicationContext(), "Password is required", Toast.LENGTH_LONG).show();
                            password.setError("Password is required.");
                        } else if (password.length() > 4) {
                            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            YoYo.with(Techniques.Shake)
                                    .playOn(password);
                            Toast.makeText(getApplicationContext(), "Password must be 4 digit.", Toast.LENGTH_LONG).show();
                            password.setError("Password must be of 4 digit.");
                            password.setText("");

                        } else if (password.length() < 4) {
                            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            YoYo.with(Techniques.Shake)
                                    .playOn(password);
                            Toast.makeText(getApplicationContext(), "Password must be 4 digit.", Toast.LENGTH_LONG).show();
                            password.setError("Password must be of 4 digit.");
                            password.setText("");

                        }
                    }
                }
            }

        });

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if (view.getId() == R.id.username) {
                        SQLiteDatabase db = dbHandler.getReadableDatabase();
                        String sql = "select * from register where Username = '"+username.getText()+"';";
                         Cursor cursor = db.rawQuery(sql,null);

                         int count = cursor.getCount();
                          if(count>0) {
                                runOnUiThread(new Runnable() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void run() {
                                        YoYo.with(Techniques.Shake)
                                                .playOn(username);
                                        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                                        username.setError("Username already exist");
                                        username.setText("");
                                        Toast.makeText(RegisterActivity.this, "Username already exist", Toast.LENGTH_SHORT).show();

                                    }
                                });

                        }

                    }
                }
            }
        });



        registerbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v)   {

                 if (isAllFieldsChecked == CheckAllFields()) {

                      SQLiteDatabase db = dbHandler.getWritableDatabase();
                     ContentValues values = new ContentValues();
                     values.put("Name", name.getText().toString());
                     values.put("age",Age.getText().toString());
                     values.put("gender", gender1.trim());
                     values.put("Email", email.getText().toString());
                     values.put("Username", username.getText().toString());
                     values.put("password", password.getText().toString());
                     values.put("contactNo", contactno.getText().toString());
                     values.put("Address", Address.getText().toString());
                     values.put("city", city.getText().toString());
                     values.put("img", encodedImage);
                     values.put("bloodgrp",item);
                     values.put("Uid",uid.getText().toString());
                     try {
                         db.insertOrThrow("register", null, values);
                         Toast.makeText(getApplicationContext(), "Registration successful.", Toast.LENGTH_LONG).show();
                         textToSpeech.speak("Registration successful.",TextToSpeech.QUEUE_FLUSH,null);
                         final Handler h = new Handler();
                         h.postDelayed(new Runnable() {
                             @Override
                             public void run() {
                                 Intent i = new Intent(RegisterActivity.this, Login.class);
                                 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                 startActivity(i);
                             }
                         },3000);

                     }catch (SQLiteException e){
                      Toast.makeText(getApplicationContext(), "Please select blood group", Toast.LENGTH_SHORT).show();
                     }
                         if (blood.isChecked()) {
                              ContentValues values1 = new ContentValues();
                             values1.put("Name", name.getText().toString());
                             values1.put("Email", email.getText().toString());
                             values1.put("Username", username.getText().toString());
                             values1.put("contactNo", contactno.getText().toString());
                             values1.put("Address",  Address.getText().toString());
                             values1.put("city", city.getText().toString());
                             values1.put("img", encodedImage);
                             values1.put("donor","yes");
                             values1.put("bloodgrp",item);
                             values1.put("Uid",uid.getText().toString());

                             try {
                           db.insertOrThrow("donor", null, values1);

                            }
                            catch (SQLiteException e){
                                Toast.makeText(getApplicationContext(), "Please select blood group", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                             ContentValues values1 = new ContentValues();
                             values1.put("Name", name.getText().toString());
                             values1.put("Email", email.getText().toString());
                             values1.put("Username", username.getText().toString());
                             values1.put("contactNo", contactno.getText().toString());
                             values1.put("Address",  Address.getText().toString());
                             values1.put("city", city.getText().toString());
                             values1.put("img", encodedImage);
                             values1.put("donor","no");
                             values1.put("bloodgrp",item);
                             values1.put("Uid",uid.getText().toString());

                             try {
                                 db.insertOrThrow("donor", null, values1);

                             }
                            catch (SQLiteConstraintException e){
                                Toast.makeText(getApplicationContext(), "Please select blood group", Toast.LENGTH_SHORT).show();
                            }
                        }

                      }
        }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_LOAD_IMAGE);
            }

        });
    }


    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()) {
            case R.id.Male:
                if(checked)
                    gender1 = "male";
                    textToSpeech.speak("Male is selected", TextToSpeech.QUEUE_FLUSH, null);
                    break;


            case R.id.Female:
                if(checked) {
                    gender1 = "female";
                    textToSpeech.speak("Female is selected", TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Bitmap originBitmap = null;
            Uri selectedImage = data.getData();
            InputStream imageStream;
            Toast.makeText(getApplicationContext(), "Image successfully uploaded", Toast.LENGTH_SHORT).show();
             try {

                imageStream = getContentResolver().openInputStream(selectedImage);
                originBitmap = BitmapFactory.decodeStream(imageStream);

                originBitmap= originBitmap.copy(Bitmap.Config.ARGB_8888, true);

            } catch (FileNotFoundException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
            }
            if (originBitmap != null) {
                try {
                    originBitmap.setPixel(0, 0, getColor(R.color.colorPrimary));
                    this.img.setImageBitmap(originBitmap);
                    Bitmap image = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                    encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean CheckAllFields() {

        if (name.length() == 0) {
            name.setError("This field is required");
            name.requestFocus();
            YoYo.with(Techniques.Shake)
                    .playOn(name);
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            return false;

        }


        if (email.length() == 0) {
            email.setError("Email is required");
            YoYo.with(Techniques.Shake)
                    .playOn(email);
            return false;
        }
        if (Age.length() == 0) {
            Age.setError("Age is required");
            YoYo.with(Techniques.Shake)
                    .playOn(Age);
            return false;
        }


        if (password.length() == 0) {
            password.setError("Password is required");
            YoYo.with(Techniques.Shake)
                    .playOn(password);
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            return false;
        } else if (password.length() > 4) {
            password.setError("Password must be of 4 characters");
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            return false;
        }
        else if(password.length()<4){
            password.setError("Password must be of 4 characters");
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            return false;
        }
        if (contactno.length() == 0) {
            contactno.setError("contactno is required");
            contactno.requestFocus();
            YoYo.with(Techniques.Shake)
                    .playOn(contactno);
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            return false;
        }
        if (Address.length() == 0) {
            Address.setError("Address is required");
            YoYo.with(Techniques.Shake)
                    .playOn(Address);
            Address.requestFocus();
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            return false;
        }

        if (city.length() == 0) {
            city.setError("city is required");
            city.requestFocus();
            YoYo.with(Techniques.Shake)
                    .playOn(city);
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            return false;
        }
        if(gender1.equals("")){
            final TextView  gender2 = findViewById(R.id.gend);
            Age.requestFocus();
            YoYo.with(Techniques.Shake)
                    .repeat(2)
                    .playOn(gender2);
            Toast.makeText(RegisterActivity.this, "Please select gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(item.contains("select blood")){
            final TextView textView=findViewById(R.id.bloodgroup6);
            final ScrollView sc = findViewById(R.id.scroll);
            sc.post(new Runnable() {
                @Override
                public void run() {
                    sc.scrollTo(0,city.getTop());
                }
            });

            textToSpeech.speak("please select blood group",TextToSpeech.QUEUE_FLUSH,null);
            Toast.makeText(getApplicationContext(), "please select blood group", Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Flash)
                    .repeat(2)
                    .playOn(textView);

        }

        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
         item = parent.getItemAtPosition(position).toString();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(getApplicationContext(), "Please select the blood group", Toast.LENGTH_SHORT).show();
    }







    public void onPause(){
        if(textToSpeech !=null){
            textToSpeech.stop();
        }
        super.onPause();
    }
}
