package comp3350.studyplus.presentation.question;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.studyplus.MainActivity;
import comp3350.studyplus.R;
import comp3350.studyplus.logic.IQuestionHandler;
import comp3350.studyplus.logic.QuestionHandler;
import comp3350.studyplus.objects.Question;

public class UpdateQuestion extends AppCompatActivity {

    private IQuestionHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new QuestionHandler();

        setContentView(R.layout.activity_update_question);

        Intent intent = getIntent();

        TextView title = findViewById(R.id.text_update_question);
        EditText question = findViewById(R.id.update_question_field);
        EditText answer = findViewById(R.id.update_answer_field);

        String courseId = intent.getStringExtra("courseId");
        String courseName = intent.getStringExtra("courseName");
        String questionStr = intent.getStringExtra("question");
        String answerStr = intent.getStringExtra("answer");
        String questionId = intent.getStringExtra("questionId");

        title.setText("Update Question in " + courseId);
        question.setText(questionStr);
        answer.setText(answerStr);

        Button updateBtn = findViewById(R.id.button_question_update);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newQuestionStr = question.getText().toString();
                String newAnswerStr = answer.getText().toString();

                Question updatedQuestion = handler.updateQuestion(questionId, newQuestionStr, newAnswerStr);
                if (updatedQuestion != null) {
                    Toast.makeText(getApplicationContext(), "Question updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DisplayQuestion.class);
                    intent.putExtra("courseId" , courseId);
                    intent.putExtra("courseName" , courseName);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "New question cannot be updated. \nBoth fields must contain text and have 250 characters or less.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
