package comp3350.studyplus.presentation.question;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import comp3350.studyplus.MainActivity;
import comp3350.studyplus.R;
import comp3350.studyplus.logic.CourseHandler;
import comp3350.studyplus.logic.DisplayQuestionTextIterator;
import comp3350.studyplus.logic.ICourseHandler;
import comp3350.studyplus.objects.QuestionTag;

public class DisplayQuestion extends AppCompatActivity {

    private ICourseHandler courseHandler;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_question);

        Intent intent = getIntent();

        courseHandler = new CourseHandler();

        String courseId = intent.getStringExtra("courseId");
        String courseName = intent.getStringExtra("courseName");

        QuestionTag courseTag = courseHandler.getCourse(courseId).getQuestionTag();

        DisplayQuestionTextIterator displayQuestionTextIterator = new DisplayQuestionTextIterator(courseTag);

        final TextView courseView = findViewById(R.id.title_course);
        courseView.setText(courseName);

        final TextView questionView = findViewById(R.id.cue_card_text);

        questionView.setText(displayQuestionTextIterator.setText());

        Button flipBtn = (Button)findViewById(R.id.flip_btn);
        Button nextBtn = (Button)findViewById(R.id.next_btn);
        Button backBtn = (Button)findViewById(R.id.back_btn);
        ImageButton backToCourse = (ImageButton)findViewById(R.id.back_to_courses);
        FloatingActionButton addQuestionBtn = (FloatingActionButton)findViewById(R.id.add_question_btn);
        ImageButton deleteBtn = (ImageButton)findViewById(R.id.delete_btn);
        ImageButton updateBtn = (ImageButton)findViewById(R.id.update_btn);

        flipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionView.setText(displayQuestionTextIterator.flipDisplay());
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionView.setText(displayQuestionTextIterator.getNextQuestion());
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionView.setText(displayQuestionTextIterator.getPrevQuestion());
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currQuestion = displayQuestionTextIterator.getCurrQuestionStr();
                String currAnswer = displayQuestionTextIterator.getCurrAnswerStr();
                if(!displayQuestionTextIterator.isEmpty()) {
                    String currQuestionId = displayQuestionTextIterator.getCurrQuestionId().toString();
                    Intent intent = new Intent(getApplicationContext() , UpdateQuestion.class);
                    intent.putExtra("question" , currQuestion);
                    intent.putExtra("answer" , currAnswer);
                    intent.putExtra("questionId", currQuestionId);
                    intent.putExtra("courseId" , courseId);
                    intent.putExtra("courseName" , courseName);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(DisplayQuestion.this, "No Cue Cards to Edit!", Toast.LENGTH_LONG).show();
                }

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            //From: https://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-on-android
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteConfirmation = new AlertDialog.Builder(DisplayQuestion.this);
                deleteConfirmation.setMessage("Are you sure you want to delete this cue card?");
                deleteConfirmation.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                deleteConfirmation.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!displayQuestionTextIterator.deleteCurrentQuestion()) {
                            Toast.makeText(DisplayQuestion.this, "No Cue Cards to Delete!", Toast.LENGTH_LONG).show();
                        }
                        Intent intent = new Intent(getApplicationContext(), DisplayQuestion.class);
                        intent.putExtra("courseId" , courseId);
                        intent.putExtra("courseName" , courseName);
                        startActivity(intent);
                    }
                });
                deleteConfirmation.show();
            }
        });

        addQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddQuestion.class);
                intent.putExtra("courseId" , courseId);
                intent.putExtra("courseName" , courseName);
                startActivity(intent);
            }
        });

        backToCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}