package nz.travel.townguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import nz.travel.townguide.constant.Constants;
import nz.travel.townguide.model.Choice;
import nz.travel.townguide.model.Quiz;
import nz.travel.townguide.utils.DataGenerator;

import java.util.Collections;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private TextView tvQuestion;
    private RadioGroup rdgChoice;
    private RadioButton rbAns0;
    private RadioButton rbAns1;
    private RadioButton rbAns2;
    private RadioButton rbAns3;

    private TextView tvInPageResult;
    private TextView tvInPageFeedback;
    private TextView tvInPageScore;

    private Button btnSubmitAnswer;
    private Button btnNextQuestion;

    private LinearLayout layoutQuestionMain;

    private List<Quiz> quizList = Collections.emptyList();
    private Quiz quiz;
    private Choice correctChoice;
    private int selectedId = 0;
    private int totalScore = 0;

    private SharedPreferences pref;
    private int questionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        tvQuestion = findViewById(R.id.tvQuestion);
        rdgChoice = findViewById(R.id.rdgChoice);

        rbAns0 = findViewById(R.id.rbAns0);
        rbAns1 = findViewById(R.id.rbAns1);
        rbAns2 = findViewById(R.id.rbAns2);
        rbAns3 = findViewById(R.id.rbAns3);

        tvInPageResult = findViewById(R.id.tvInPageResult);
        tvInPageFeedback = findViewById(R.id.tvInPageFeedback);
        tvInPageScore = findViewById(R.id.tvInPageScore);

        btnSubmitAnswer = findViewById(R.id.btnSubmitAnswer);
        btnNextQuestion = findViewById(R.id.btnNextQuestion);

        layoutQuestionMain = findViewById(R.id.layoutQuestionMain);

        try {
            loadSharePreferences();

            // load question from json
            quizList = DataGenerator.generateQuiz(getApplicationContext());
            setupQuestion();
        } catch (Exception e) {
            Log.e("onCreate", "Attempt to read questions from the json file:", e);
        }

        rdgChoice.setOnCheckedChangeListener(this);
        btnNextQuestion.setOnClickListener(this);
        btnSubmitAnswer.setOnClickListener(this);

        btnNextQuestion.setEnabled(false);
        btnSubmitAnswer.setEnabled(false);
    }

    /**
     * Action for submit and next page button
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Log.d("onclick", String.valueOf(v.getId()));
        if (v.getId() == R.id.btnSubmitAnswer) {
            Log.d("onclick", "Submit button clicked.");

            if (selectedId == 0) {
                String warningMsg = "Please select the answer!";
                Log.d("onclick", warningMsg);
                Toast toast = Toast.makeText(getApplicationContext(), warningMsg, Toast.LENGTH_SHORT);
                toast.show();
            } else {
                RadioButton rbSelectedAns = (RadioButton) findViewById(selectedId);
                if (rbSelectedAns.getText().equals(correctChoice.getChoiceDesc())) {
                    Log.d("onclick", "The answer is correct.");
                    totalScore++;
                    tvInPageResult.setText(Constants.ANS_CORRECT);
                } else {
                    Log.d("onclick", "The answer is wrong.");
                    tvInPageResult.setText(Constants.ANS_WRONG);
                }
                tvInPageFeedback.setText(quiz.getFeedback());
                tvInPageScore.setText(String.valueOf(totalScore) + "/" + String.valueOf(quizList.size()));
                btnNextQuestion.setEnabled(true);
                btnSubmitAnswer.setEnabled(false);
                rbAns0.setEnabled(false);
                rbAns1.setEnabled(false);
                rbAns2.setEnabled(false);
                rbAns3.setEnabled(false);
            }

        } else if (v.getId() == R.id.btnNextQuestion) {
            Log.d("onclick", "Next button clicked.");

            btnNextQuestion.setEnabled(false);
            questionIndex++;
            updateSharedPreference();

            if (questionIndex < quizList.size()) {
                Log.d("onclick", "Go to the next question.");
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            } else {
                Log.d("onclick", "Go to the result page.");
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        }
    }

    /**
     * Collect the selected answer
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        selectedId = group.getCheckedRadioButtonId();
        btnSubmitAnswer.setEnabled(true);
    }

    /**
     * Display question and answers on the screen
     */
    private void setupQuestion() {
        quiz = quizList.get(questionIndex);
        tvQuestion.setText(quiz.getQuestion());

        List<Choice> choices = quiz.getChoices();
        rbAns0.setText(choices.get(0).getChoiceDesc());
        rbAns1.setText(choices.get(1).getChoiceDesc());
        rbAns2.setText(choices.get(2).getChoiceDesc());
        rbAns3.setText(choices.get(3).getChoiceDesc());

        for (Choice item : choices) {
            if (item.isCorrect()) {
                correctChoice = item;
                break;
            }
        }

        layoutQuestionMain.setBackground(ContextCompat.getDrawable(getApplicationContext(), getBackgroundImage(quiz.getBackgroundImage())));
        layoutQuestionMain.getBackground().setAlpha(80);
    }

    /**
     * Get background image of the layout by imageName attribute
     * @param imageName
     * @return
     */
    private int getBackgroundImage(String imageName) {
        switch (imageName) {
            case "bg_0":
                return R.drawable.bg_0;
            case "bg_1":
                return R.drawable.bg_1;
            case "bg_2":
                return R.drawable.bg_2;
            case "bg_3":
                return R.drawable.bg_3;
            case "bg_4":
                return R.drawable.bg_4;
            case "bg_5":
                return R.drawable.bg_5;
            case "bg_6":
                return R.drawable.bg_6;
            case "bg_7":
                return R.drawable.bg_1;
            case "bg_8":
                return R.drawable.bg_2;
            case "bg_9":
                return R.drawable.bg_3;
            default:
                return R.drawable.bg_0;
        }
    }

    /**
     * Load shared variables to the activity
     */
    private void loadSharePreferences() {
        pref = getApplicationContext().getSharedPreferences("AnswerPref", Context.MODE_PRIVATE);
        questionIndex = pref.getInt("questionIndex", 0);
        totalScore = pref.getInt("totalScore", 0);
    }

    /**
     * Save shared variables for the next activity
     */
    private void updateSharedPreference() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("questionIndex", questionIndex);
        editor.putInt("totalScore", totalScore);
        editor.commit();
    }
}
