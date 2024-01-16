package comp3350.studyplus.presentation.course;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.studyplus.MainActivity;
import comp3350.studyplus.R;
import comp3350.studyplus.logic.ICourseHandler;
import comp3350.studyplus.logic.CourseHandler;
import comp3350.studyplus.objects.Course;

public class AddCourse extends AppCompatActivity {
    private ICourseHandler courseHandler;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_course);

        Intent intent = getIntent();

        courseHandler = new CourseHandler();

        EditText courseId = findViewById(R.id.add_course_id_field);
        EditText courseName = findViewById(R.id.add_course_name_field);
        Button createCourseBtn = findViewById(R.id.add_course_btn);

        createCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newCourseId = courseId.getText().toString();
                String newCourseName = courseName.getText().toString();

                Course newCourse = courseHandler.createCourse(newCourseId, newCourseName);
                if (newCourse != null) {
                    if (!courseHandler.ifCourseExists(newCourse)) {
                        courseHandler.addCourse(newCourse);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Course added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Course already exists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Course cannot be added. \nBoth fields must contain text and have 100 characters or less.\"", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
