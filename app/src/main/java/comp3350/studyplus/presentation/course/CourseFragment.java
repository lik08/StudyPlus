package comp3350.studyplus.presentation.course;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import comp3350.studyplus.databinding.FragmentCourseBinding;
import comp3350.studyplus.logic.CourseHandler;
import comp3350.studyplus.logic.ICourseHandler;
import comp3350.studyplus.objects.Course;

public class CourseFragment extends Fragment {

    private FragmentCourseBinding binding;
    private ICourseHandler courseHandler;
    private FloatingActionButton addCourseBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CourseViewModel courseViewModel =
                new ViewModelProvider(this).get(CourseViewModel.class);
        courseHandler = new CourseHandler();

        binding = FragmentCourseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        List<Course> courses = (List<Course>)courseViewModel.getCourses();
        final RecyclerView recyclerViewCourse = binding.courseList;
        CourseAdapter adapter = new CourseAdapter(courses, getContext());
        addCourseBtn = binding.floatingAddCourseBtn;
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddCourse.class);
                getContext().startActivity(intent);
            }
        });

        recyclerViewCourse.setAdapter(adapter);
        recyclerViewCourse.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}