package info.rohanmorris.quizmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private TextView vCat;
    private List<QuizDataModel.Result> data;
    private int score;
    private TextView vSteps, vQuiz;
    int mLvl, mCat, mNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        SharedPreferences prefSettings = getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);
        mLvl = prefSettings.getInt(getString(R.string.game_level), Integer.parseInt(getString(R.string.game_level_default)));
        mCat = prefSettings.getInt(getString(R.string.game_category), Integer.parseInt(getString(R.string.game_category_default)));
        mNum = prefSettings.getInt(getString(R.string.game_amonut), Integer.parseInt(getString(R.string.game_amonut_default)));

        vCat  = findViewById(R.id.category);
        vQuiz = findViewById(R.id.quiz);

        MobileAds.initialize(this, getString(R.string.add_id));
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ImageView settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        vSteps = findViewById(R.id.steps);
        new getJsonFile().execute();
    }

    @Override
    public void onBackPressed() {
    }

    private void loadData(final int step) {
        LinearLayout quizAnswerLayout = findViewById(R.id.quiz_answer);
        quizAnswerLayout.removeAllViews();

        vCat.setText(Html.fromHtml(data.get(step).getCategory()));
        vQuiz.setText(Html.fromHtml(data.get(step).getQuestion()));

        final ArrayList<String> list = new ArrayList<>(Arrays.asList(data.get(step).getIncorrectAnswers()));
        list.add(data.get(step).getCorrectAnswer());
        Collections.shuffle(list);

        vSteps.setText(getResources().getString(R.string.score, String.valueOf(step+1), String.valueOf(data.size())));
        int[] btn = {R.layout.answer_button1,R.layout.answer_button2,R.layout.answer_button3,R.layout.answer_button4};

        int i = 0;
        for(String s : list){
            LinearLayout v = (LinearLayout) View.inflate(this, btn[i++], null);
            final Button a = v.findViewById(R.id.answer);
            a.setTextAppearance(this, R.style.question2);
            a.setText(Html.fromHtml(s));
            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(a.getText().toString().equals(data.get(step).getCorrectAnswer())){
                        score+=1;
                    }

                    if(step+1 < data.size()){
                        loadData(step + 1);
                    }
                    else{
                        Intent intent = new Intent(getBaseContext(),ResultActivity.class);
                        intent.putExtra("FINAL_SCORE", score + " out of " + data.size());
                        intent.putExtra("PERCENTAGE", (int)((float)score/data.size()*100));
                        startActivity(intent);
                    }
                }
            });
            quizAnswerLayout.addView(v);
        }
    }

    private class getJsonFile extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... voids) {
            String json;
            String level = Common.getGameLevelList().get(mLvl).getId()==0
                    ? ""
                    : Common.getGameLevelList().get(mLvl).getTitle().toLowerCase();

            String url = String.format( "http://www.rohanmorris.info/api/quiz.php?amount=%s&category=%s&difficulty=%s",
                                        mNum,
                                        Common.getGameCategoryList().get(mCat).getId(),
                                        level) ;

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .writeTimeout(3, TimeUnit.SECONDS)
                    .readTimeout(3, TimeUnit.SECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                ResponseBody b = response.body();
                json = (b!=null)? b.string() : null;
            } catch (IOException e) {
                return null;
            }
            return json;
            //return "{\"response_code\":0,\"results\":[{\"category\":\"History\",\"type\":\"multiple\",\"difficulty\":\"medium\",\"question\":\" What Russian automatic gas-operated assault rifle was developed in the Soviet Union in 1947, and is still popularly used today?\",\"correct_answer\":\"AK-47\",\"incorrect_answers\":[\"RPK\",\"M16\",\"MG 42\"]},{\"category\":\"Entertainment: Video Games\",\"type\":\"multiple\",\"difficulty\":\"medium\",\"question\":\"What is the name of the currency in the &quot;Animal Crossing&quot; series?\",\"correct_answer\":\"Bells\",\"incorrect_answers\":[\"Sea Shells\",\"Leaves\",\"Bugs\"]},{\"category\":\"Geography\",\"type\":\"multiple\",\"difficulty\":\"easy\",\"question\":\"If soccer is called football in England, what is American football called in England?\",\"correct_answer\":\"American football\",\"incorrect_answers\":[\"Combball\",\"Handball\",\"Touchdown\"]},{\"category\":\"History\",\"type\":\"multiple\",\"difficulty\":\"medium\",\"question\":\"Which of the following ancient Near Eastern peoples still exists as a modern ethnic group?\",\"correct_answer\":\"Assyrians\",\"incorrect_answers\":[\"Babylonians\",\"Hittites\",\"Elamites\"]},{\"category\":\"History\",\"type\":\"multiple\",\"difficulty\":\"medium\",\"question\":\"On which day did construction start on &quot;The Pentagon&quot;, the headquarters for the United States Department of Defense?\",\"correct_answer\":\"September 11, 1941\",\"incorrect_answers\":[\"June 15, 1947\",\"January 15, 1943\",\"September 2, 1962\"]},{\"category\":\"Geography\",\"type\":\"multiple\",\"difficulty\":\"hard\",\"question\":\"Where is the fast food chain &quot;Panda Express&quot; headquartered?\",\"correct_answer\":\"Rosemead, California\",\"incorrect_answers\":[\"Sacremento, California\",\"Fresno, California\",\"San Diego, California\"]},{\"category\":\"Entertainment: Music\",\"type\":\"multiple\",\"difficulty\":\"easy\",\"question\":\"The 2016 song &quot;Starboy&quot; by Canadian singer The Weeknd features which prominent electronic artist?\",\"correct_answer\":\"Daft Punk\",\"incorrect_answers\":[\"deadmau5\",\"Disclosure\",\"DJ Shadow\"]},{\"category\":\"General Knowledge\",\"type\":\"multiple\",\"difficulty\":\"medium\",\"question\":\"Which of the following carbonated soft drinks were introduced first?\",\"correct_answer\":\"Dr. Pepper\",\"incorrect_answers\":[\"Coca-Cola\",\"Sprite\",\"Mountain Dew\"]},{\"category\":\"Entertainment: Video Games\",\"type\":\"multiple\",\"difficulty\":\"easy\",\"question\":\"In the game &quot;Hearthstone&quot;, what is the best rank possible?\",\"correct_answer\":\"Rank 1 Legend\",\"incorrect_answers\":[\"Rank 1 Elite\",\"Rank 1 Master\",\"Rank 1 Supreme\"]},{\"category\":\"Entertainment: Japanese Anime & Manga\",\"type\":\"multiple\",\"difficulty\":\"medium\",\"question\":\"In the &quot;Sailor Moon&quot; series, what is Sailor Jupiter&#039;s civilian name?\",\"correct_answer\":\"Makoto Kino\",\"incorrect_answers\":[\"Minako Aino\",\"Usagi Tsukino\",\"Rei Hino\"]}]}";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null) {
                Gson gson = new Gson();
                QuizDataModel QuizArray = gson.fromJson(s, QuizDataModel.class);

                switch(QuizArray.getResponseCode()) {
                    case 0:
                        data = QuizArray.getResults();
                        score = 0;
                        loadData(0);
                    break;
                    case 1:
                        vCat.setText(R.string.error);
                        vQuiz.setText(R.string.responce_code_1);
                    break;
                }
            }else{
                vCat.setText(R.string.error);
                vQuiz.setText(R.string.error_connecting);
            }
        }
    }
}
