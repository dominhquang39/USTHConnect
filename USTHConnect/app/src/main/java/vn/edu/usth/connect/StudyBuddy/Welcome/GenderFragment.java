package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import vn.edu.usth.connect.R;


public class GenderFragment extends Fragment {
    private Button maleButton, femaleButton, other_gender_button, nextButton;
    private ImageButton backButton;
    private String selectedGender = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gender, container, false);
        maleButton = v.findViewById(R.id.male);
        femaleButton = v.findViewById(R.id.female);
        other_gender_button = v.findViewById(R.id.other_gender);
        nextButton = v.findViewById(R.id.next_button);
        backButton = v.findViewById(R.id.back_button);

        nextButton.setEnabled(false);

        backButton.setOnClickListener(view -> {
            navigateBack();
        });
        nextButton.setOnClickListener(view -> {
            navigateFragment(new MajorFragment());
        });
        maleButton.setOnClickListener(view -> handleGenderSelect("male"));
        femaleButton.setOnClickListener(view -> handleGenderSelect("female"));
        other_gender_button.setOnClickListener(view -> handleGenderSelect("prefer not to say"));
        return v;
    }

    private void handleGenderSelect(String gender) {
        maleButton.setBackgroundResource(R.drawable.rounded_border);
        femaleButton.setBackgroundResource(R.drawable.rounded_border);
        other_gender_button.setBackgroundResource(R.drawable.rounded_border);

        switch (gender) {
            case "male":
                maleButton.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "female":
                femaleButton.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "prefer not to say":
                other_gender_button.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
        }

        selectedGender = gender;
        nextButton.setEnabled(true);
    }

    private void navigateBack() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    private void navigateFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}