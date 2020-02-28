package nz.travel.townguide.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import nz.travel.townguide.R;
import nz.travel.townguide.model.Choice;
import nz.travel.townguide.model.Quiz;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static List<Quiz> generateQuiz(Context context) throws Exception {
        Log.i("Read json for ", Quiz.class.getSimpleName());

        JSONArray jsonQuizs = readJsonFile(context, R.raw.choice, "quiz");

        List<Quiz> quizList = new ArrayList<>();
        for (int i = 0; i < jsonQuizs.length(); i++) {
            JSONObject obj = jsonQuizs.getJSONObject(i);
            Quiz quiz = new Quiz();

            quiz.setId(Integer.parseInt(obj.getString("id")));
            quiz.setQuestion(obj.getString("question"));
            quiz.setFeedback(obj.getString("feedback"));
            quiz.setBackgroundImage(obj.getString("backgroundImage"));

            String correctChoice = obj.getString("correctChoice");
            JSONArray jsonChoices = new JSONArray(obj.getString("choices"));
            for (int j = 0; j < jsonChoices.length(); j++) {
                JSONObject choicesObj = jsonChoices.getJSONObject(j);
                quiz.addChoices(new Choice(choicesObj.getString("a"), correctChoice.equals("a")));
                quiz.addChoices(new Choice(choicesObj.getString("b"), correctChoice.equals("b")));
                quiz.addChoices(new Choice(choicesObj.getString("c"), correctChoice.equals("c")));
                quiz.addChoices(new Choice(choicesObj.getString("d"), correctChoice.equals("d")));
            }

            quizList.add(quiz);
        }

        return quizList;
    }

    private static JSONArray readJsonFile(Context context, int rawResource, String entityName) throws Exception {
        String jsonStr = readRawResource(context, rawResource);
        JSONObject jObj = new JSONObject(jsonStr);
        JSONArray jsonArry = jObj.getJSONArray(entityName);
        return jsonArry;
    }

    private static String readRawResource(Context context, int rawResource) throws IOException {
        InputStream is = context.getResources().openRawResource(rawResource);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                Log.e(DataGenerator.class.toString(), "Failed to read JSON from raw resource.", e);
            }
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        String jsonString = writer.toString();
        return jsonString;
    }

}
