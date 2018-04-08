package info.rohanmorris.quizmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ResultActivity extends AppCompatActivity {
/****/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        TextView vScore = findViewById(R.id.score);
        final String finalScore = getIntent().getStringExtra("FINAL_SCORE");
        vScore.setText(finalScore);

        String  h1 = null,
                h2 = "Well done";
        int per = getIntent().getIntExtra("PERCENTAGE",0);

        if(per==100){
            h1 = "Perfect!!";
        }
        if(per<100){
            h1 = "Great Work";
        }
        if(per<75){
            h1 = "Not Too Bad";
        }
        if(per<50){
            h1 = "Okay I Guess";
            h2 = "Try Again";
        }
        if(per<30){
            h1 = "Woo You Suck!!!";
            h2 = "Try Again";
        }

        TextView text1 = findViewById(R.id.text1);
        TextView text2 = findViewById(R.id.text2);
        ImageView img = findViewById(R.id.img);
        Button bShare = findViewById(R.id.share);
        Button bRestart = findViewById(R.id.restart);
        Button bSetting = findViewById(R.id.settings);
        
        text1.setText(h1);
        text2.setText(h2);
        img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gold));

        bShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String share = "I just got " + finalScore + " on " + getString(R.string.app_name) + " download now from the play store https://goo.gl/VLCCbR";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, share);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
            }
        });

        bRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        bSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
