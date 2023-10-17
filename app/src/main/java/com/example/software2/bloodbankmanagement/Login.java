package com.example.software2.bloodbankmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Login extends AppCompatActivity {


    Button loginbtn, regbtn;
     TextView t;
    private DBHandler dbHandler;
     private static String username,password;
    /**  Here we have created static string value for getting the username
    value after user login complete. and below is the getvalue which is public static class and
    in that we will return this value.  */

    public static String getUsername() {
        return username;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);
        t = findViewById(R.id.textView27);
        YoYo.with(Techniques.FadeInUp)
                .repeat(15)
                .duration(1500)
                .playOn(t);
         dbHandler = new DBHandler(this);
         loginbtn = findViewById(R.id.loginbtn);
        regbtn = findViewById(R.id.regbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });


        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
    }

    private void checkLogin() {
        final EditText editText = findViewById(R.id.emaillogin);
        final EditText editText1 = findViewById(R.id.passwordlogin);

        //editText.setText("");
        password = editText1.getText().toString();
        editText1.setText("");
        username = editText.getText().toString().trim();

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String query = "SELECT * FROM register WHERE Username = '"+ username +"' and password = '"+password+"';";
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()) {
            do {
                Toast.makeText(Login.this, "Login Success", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Login.this, Tabbed.class);
                startActivity(intent);
            }
            while (c.moveToNext());
        }
        else{
            Toast.makeText(getApplicationContext(), "Check your email or password.", Toast.LENGTH_SHORT).show();

        }


    }

}