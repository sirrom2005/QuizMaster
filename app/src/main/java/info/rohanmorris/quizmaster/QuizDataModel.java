package info.rohanmorris.quizmaster;

import java.util.List;

/**
 * Created by Rohan on
 * 3/16/18.
 */

public class QuizDataModel {
    private int response_code;
    private List<Result> results;

    public QuizDataModel(int response_code, List<Result> results) {
        this.response_code = response_code;
        this.results = results;
    }

    int getResponseCode(){ return response_code; }

    List<Result> getResults(){
        return results;
    }

    protected class Result{
        private String category;
        private String question;
        private String correct_answer;
        private String[] incorrect_answers;

        public Result(String category, String question, String correct_answer, String[] incorrect_answers) {
            this.category = category;
            this.question = question;
            this.correct_answer = correct_answer;
            this.incorrect_answers = incorrect_answers;
        }

        String getQuestion() {
            return question;
        }

        String getCorrectAnswer() {return correct_answer;}

        String getCategory(){
            return category;
        }

        String[] getIncorrectAnswers(){
            return incorrect_answers;
        }
    }
}