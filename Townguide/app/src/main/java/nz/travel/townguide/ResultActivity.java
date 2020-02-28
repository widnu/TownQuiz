package nz.travel.townguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ResultActivity extends AppCompatActivity {

    private TextView tvScore;
    private Button btnPlayAgain;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        pref = getApplicationContext().getSharedPreferences("AnswerPref", Context.MODE_PRIVATE); // 0 - for private mode
        int questionIndex = pref.getInt("questionIndex", 0);
        int totalScore = pref.getInt("totalScore", 0);

        tvScore = findViewById(R.id.tvScore);
        tvScore.setText(String.valueOf(totalScore) + "/" + String.valueOf(questionIndex) + ".");

        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

    }
}
