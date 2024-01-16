package comp3350.studyplus.presentation.course;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.studyplus.MainActivity;
import comp3350.studyplus.R;
import comp3350.studyplus.objects.Course;
import comp3350.studyplus.logic.ICourseHandler;
import comp3350.studyplus.logic.CourseHandler;
import comp3350.studyplus.presentation.question.DisplayQuestion;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private ICourseHandler courseHandler;
    private List<Course> courses;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout parent;
        TextView nameTextView;
        ImageView updateCourseBtn;
        ImageView delCourseBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.course_list_item);
            nameTextView = itemView.findViewById(R.id.course_list_item_name);
            updateCourseBtn = itemView.findViewById(R.id.update_course_btn);
            delCourseBtn = itemView.findViewById(R.id.delete_course_btn);

            courseHandler = new CourseHandler();

            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(context, DisplayQuestion.class);
                    intent.putExtra("courseId" , courses.get(pos).getCourseId());
                    intent.putExtra("courseName" , courses.get(pos).getCourseName());
                    context.startActivity(intent);
                }
            });

            updateCourseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(context , UpdateCourse.class);
                    intent.putExtra("courseId" , courses.get(pos).getCourseId());
                    intent.putExtra("courseName" , courses.get(pos).getCourseName());
                    context.startActivity(intent);
                }
            });

            delCourseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    courseHandler.deleteCourse(courses.get(pos));
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    Toast.makeText(context, "Course deleted successfully", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    public CourseAdapter(List<Course> courseList, Context context){
        this.courses = courseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View courseView = inflater.inflate(R.layout.course_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(courseView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Course course = courses.get(position);

        TextView textView = viewHolder.nameTextView;
        textView.setText(course.getCourseId());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}
