package comp3350.studyplus.presentation.quiz_game;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import comp3350.studyplus.MainActivity;
import comp3350.studyplus.R;
import comp3350.studyplus.databinding.FragmentStartQuizGameBinding;
import comp3350.studyplus.logic.QuestionHandler;

public class StartQuizFragment extends Fragment {

    private FragmentStartQuizGameBinding binding;

    QuestionHandler questionHandler;
    private EditText inputTotalQuestion;
    private EditText inputTimePerQuestion;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStartQuizGameBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        questionHandler = new QuestionHandler();
        inputTotalQuestion = binding.totalQuestionSelect;
        inputTimePerQuestion = binding.timePerQuestion;

        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalQuestionsSelected = 0;
                int secondPerQuestion = 0;

                if(!TextUtils.isEmpty(inputTotalQuestion.getText().toString())) {
                    totalQuestionsSelected = Integer.parseInt(inputTotalQuestion.getText().toString());
                }
                if(!TextUtils.isEmpty(inputTimePerQuestion.getText().toString())) {
                    secondPerQuestion = Integer.parseInt(inputTimePerQuestion.getText().toString());
                }

                if(secondPerQuestion > 0 && totalQuestionsSelected > 0 && questionHandler.getQuestionByIndex(totalQuestionsSelected-1) != null) {
                    Intent intent = new Intent(getContext(), QuizGame.class);
                    intent.putExtra("secondPerQuestion" , secondPerQuestion);
                    intent.putExtra("totalQuestionsSelected" , totalQuestionsSelected);
                    startActivity(intent);
                } else if (questionHandler.getQuestionByIndex(totalQuestionsSelected-1) == null) {
                    if(questionHandler.getQuestions().size() == 0) {
                        Toast.makeText(getContext(), "Add Some Questions!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Max " + questionHandler.getQuestions().size() + " Questions Allowed"
                                , Toast.LENGTH_SHORT).show();
                    }
                } else if (secondPerQuestion <= 0) {
                    Toast.makeText(getContext(), "Choose Positive Time.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
