package comp3350.studyplus.presentation.course;

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
import comp3350.studyplus.logic.CourseHandler;
import comp3350.studyplus.logic.ICourseHandler;
import comp3350.studyplus.objects.Course;
import comp3350.studyplus.objects.QuestionTag;


public class UpdateCourse extends AppCompatActivity {

    private ICourseHandler courseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        courseHandler = new CourseHandler();

        setContentView(R.layout.activity_update_course);

        Intent intent = getIntent();

        EditText courseName = findViewById(R.id.update_course_name_field);
        TextView title = findViewById(R.id.update_course_title);

        String courseIdStr= intent.getStringExtra("courseId");
        String courseNameStr= intent.getStringExtra("courseName");

        title.setText("Updating Course: " + courseIdStr);
        courseName.setText(courseNameStr);

        Course oldCourse = courseHandler.getCourse(courseIdStr);

        Button updateCourseBtn = findViewById(R.id.update_course_btn);
        updateCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newCourseName = courseName.getText().toString();
                QuestionTag oldCourseTag = oldCourse.getQuestionTag();

                Course newCourse = courseHandler.createCourse(courseIdStr, newCourseName, oldCourseTag);
                if (newCourse != null) {
                    if(!courseHandler.ifNewCourseIdExists(oldCourse, newCourse)) {
                        courseHandler.updateCourse(oldCourse, newCourse);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Course updated", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Course ID already exists", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Course cannot be updated. \nBoth fields must contain text and have 100 characters or less.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}


