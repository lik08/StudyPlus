package comp3350.studyplus.presentation.question;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.studyplus.R;
import comp3350.studyplus.logic.CourseHandler;
import comp3350.studyplus.logic.ICourseHandler;
import comp3350.studyplus.logic.IQuestionHandler;
import comp3350.studyplus.logic.QuestionHandler;
import comp3350.studyplus.objects.Question;
import comp3350.studyplus.objects.QuestionTag;

public class AddQuestion extends AppCompatActivity {
    private IQuestionHandler questionHandler;
    private ICourseHandler courseHandler;
    Context context;
    Button createQuestionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_question);

        Intent intent = getIntent();

        // Connects to the logic layer and get the next available question id number
        questionHandler = new QuestionHandler();
        courseHandler = new CourseHandler();

        String courseId = intent.getStringExtra("courseId");
        String courseName = intent.getStringExtra("courseName");
        QuestionTag courseTag = courseHandler.getCourse(courseId).getQuestionTag();

        // get the fields and button ready
        TextView title = findViewById(R.id.text_add_question);
        EditText inputQuestion = findViewById(R.id.add_question_field);
        EditText inputAnswer = findViewById(R.id.add_answer_field);
        Button createQuestionButton = findViewById(R.id.button_question_submit);

        title.setText("Add Question to " + courseId);

        // when user clicks submit
        createQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = inputQuestion.getText().toString();
                String answer = inputAnswer.getText().toString();

                // true if question created and added to db
                Question newQuestion = questionHandler.createQuestion(question, answer);

                if (newQuestion != null) {
                    questionHandler.addQuestionTag(newQuestion, courseTag);
                    Intent intent = new Intent(getApplicationContext(), DisplayQuestion.class);
                    intent.putExtra("courseId" , courseId);
                    intent.putExtra("courseName" , courseName);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "New question added.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "New question cannot be added. \nBoth fields must contain text and have 250 characters or less.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
