package nz.travel.townguide.model;

public class Choice {


    private String choiceDesc;

    private boolean isCorrect;

    public Choice(String choiceDesc, boolean isCorrect) {
        this.choiceDesc = choiceDesc;
        this.isCorrect = isCorrect;
    }

    public String getChoiceDesc() {
        return choiceDesc;
    }

    public void setChoiceDesc(String choiceDesc) {
        this.choiceDesc = choiceDesc;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
