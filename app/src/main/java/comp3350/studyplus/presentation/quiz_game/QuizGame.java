package comp3350.studyplus.presentation.quiz_game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import comp3350.studyplus.MainActivity;
import comp3350.studyplus.R;
import comp3350.studyplus.logic.IQuestionHandler;
import comp3350.studyplus.logic.QuestionHandler;
import comp3350.studyplus.logic.QuizHandler;
import comp3350.studyplus.logic.IQuizHandler;
import comp3350.studyplus.objects.Question;


public class QuizGame extends AppCompatActivity {
    private EditText inputAnswer;
    private TextView cueCard;
    private TextView totalQuestion;
    private TextView totalCorrect;
    private TextView quizTimerText;
    private CountDownTimer quizTimer;
    private Button submitButton;
    private static final int MILLISEC_IN_SEC = 1000;
    private static final int SEC_IN_MINUTE = 60;
    private int secPerQuestion;
    private int totalQuestionsSelected;
    private IQuizHandler quizHandler;
    private IQuestionHandler questionHandler;
    private boolean lastResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_game);

        questionHandler = new QuestionHandler();
        List<Question> questionList = questionHandler.getQuestions();

        inputAnswer = (EditText) findViewById(R.id.input_answer);
        cueCard = (TextView) findViewById(R.id.cue_card);
        totalQuestion = (TextView)findViewById(R.id.total_question);
        totalCorrect = (TextView)findViewById(R.id.total_correct);;
        quizTimerText = (TextView)findViewById(R.id.quiz_timer);;
        submitButton = (Button) findViewById(R.id.submit_btn);

        Intent intent = getIntent();
        secPerQuestion = intent.getIntExtra("secondPerQuestion", 0);
        totalQuestionsSelected = intent.getIntExtra("totalQuestionsSelected", 0);

        quizHandler = new QuizHandler(questionList, totalQuestionsSelected);
        lastResult = true;
        nextQuestion();

        startTimer(getTimerLength() * MILLISEC_IN_SEC);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userAnswer = inputAnswer.getText().toString();
                String answerMessage = getAnswerMessage(userAnswer);
                Toast.makeText(QuizGame.this, answerMessage, Toast.LENGTH_SHORT).show();

                if (!nextQuestion()) {
                    timerEnd();
                    endMessageDialog();
                }
                inputAnswer.setText(null);
            }
        });
    }

    public void endMessageDialog(){

        AlertDialog.Builder scoreReview = new AlertDialog.Builder(QuizGame.this);
        scoreReview.setTitle("End of Quiz!");
        scoreReview.setMessage("Total Correct: " + quizHandler.getCorrectAnswers() + "/" +
                quizHandler.getTotalQuestion() + "\nFinal Score: " + quizHandler.getFinalScore() + "%");
        scoreReview.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        scoreReview.show();
    }

    public void startTimer(long timerLength) {
        quizTimer = new CountDownTimer(timerLength, MILLISEC_IN_SEC) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondLeft = millisUntilFinished / MILLISEC_IN_SEC;
                long minutes = secondLeft / SEC_IN_MINUTE;
                long seconds = secondLeft % SEC_IN_MINUTE;
                quizTimerText.setText("Time Left: " + String.format("%d:%02d", minutes, seconds));
            }
            @Override
            public void onFinish() {
                endMessageDialog();
            }
        }.start();
    }

    public void timerEnd() {
        quizTimer.cancel();
        quizTimerText.setText("Time Finished");
    }
    @Override
    public void onPause(){
        super.onPause();
        quizTimer.cancel();
        this.getViewModelStore().clear();
    }

    private void setNextQuestion() {
        cueCard.setText(quizHandler.getCurrentQuestion().getQuestion());
    }

    private void setTotalQuestion() {
        totalQuestion.setText("Question: " + quizHandler.getCurrentQuestionIndex() +  "/" + quizHandler.getTotalQuestion());
    }

    private void setTotalCorrect() {
        totalCorrect.setText("Correct Answers: " + quizHandler.getCorrectAnswers());
    }

    public boolean nextQuestion() {
        boolean hasNextQuestion = quizHandler.getNextQuestion();
        if(hasNextQuestion) {
            setTotalQuestion();
            setTotalCorrect();
            setNextQuestion();
        }
        return hasNextQuestion;
    }

    public String getAnswerMessage(String userAnswer) {
        lastResult = quizHandler.validateAnswer(userAnswer);
        if(lastResult) {
            return "Correct!";
        }
        return "Incorrect";
    }

    public long getTimerLength() {
        return quizHandler.getTotalQuestion() * secPerQuestion;
    }
}