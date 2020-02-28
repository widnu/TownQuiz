package nz.travel.townguide.model;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

    private int id;

    private String question;

    private String feedback;

    private String backgroundImage;

    private List<Choice> choices;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public List<Choice> getChoices() {
        if (this.choices == null) {
            this.choices = new ArrayList<>();
        }
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public void addChoices(Choice choice) {
        this.getChoices().add(choice);
    }

}
