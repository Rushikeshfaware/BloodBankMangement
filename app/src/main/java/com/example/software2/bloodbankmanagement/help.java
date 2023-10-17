package com.example.software2.bloodbankmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.Locale;

public class help extends AppCompatActivity {
    TextToSpeech tts;
    private SlidrInterface slidr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        slidr = Slidr.attach(this);
        slidr.unlock();
        Button listen = findViewById(R.id.listen);
        Button stop = findViewById(R.id.stop);
        Toast.makeText(help.this, "Click on listen button to listen the information", Toast.LENGTH_LONG).show();

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                    tts.setSpeechRate(1f);
                   }
            }
        });
        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("Blood Bank. In this we have two fields, one is the blood  group and " +
                        "the other is the number of blood donors. After scrolling down, you will see  the send " +
                        " request button. In this, if you  want the blood of that particular blood group, " +
                        "you can select it and  click on the send request  button. " +
                        "After that you will see your request  in the Blood request field." +
                        "Search Donor :- In this field you can find information about blood donors." +
                        " If you need blood, you can select the blood type you want and see the number of donors for that blood group and contact them directly. " +"you can send them mail as well"+
                        "When we select any blood group, say B+, it automatically displays all blood donors with their B + blood group.The advantage is that if there is any emergency of blood it helps us a lot ." +
                        "My Profile :- This field helps the donor to see all the  details that is personal information  plus the blood group which together helps to select a donor." +
                        " You can update your details as well.Blood request.  In this field we can see request for blood of different people. We can also see the name and contact number of the sender.\n",TextToSpeech.QUEUE_FLUSH,null);


            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.stop();
            }
        });

    }

    public void onPause(){
        if(tts !=null){
            tts.stop();
        }
        super.onPause();
    }
}