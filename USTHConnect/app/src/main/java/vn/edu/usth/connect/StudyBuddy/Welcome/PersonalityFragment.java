package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

import vn.edu.usth.connect.R;

public class PersonalityFragment extends Fragment {

    private Spinner spinner;
    private String[] personality;
    private String hint = "Select your personality";
    private String selectedPersonality = "";
    private Button nextButton;
    private ImageButton backButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        personality = getResources().getStringArray(R.array.personality);
        View v = inflater.inflate(R.layout.fragment_personality, container, false);
        spinner = v.findViewById(R.id.select_personality);
        backButton = v.findViewById(R.id.back_button);
        nextButton = v.findViewById(R.id.next_button);

        nextButton.setEnabled(false);

        backButton.setOnClickListener(view -> {
            navigateBack();
        });

        nextButton.setOnClickListener(view -> {
            navigateFragment(new InterestFragment());
        });
        List<String> majorList = Arrays.asList(personality);
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(
                requireContext(),
                majorList,
                hint
        );
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPersonality = adapterView.getItemAtPosition(i).toString();
                nextButton.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedPersonality = hint;
                nextButton.setEnabled(false);
            }
        });
        return v;
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