package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import vn.edu.usth.connect.R;

public class MajorFragment extends Fragment {

    private Spinner spinner;
    private Button b1, b2, b3, nextButton;
    private ImageButton backButton;
    private String[] majors;
    private String hint = "Select your major";
    private String selectedYear = "";
    private String selectedMajor = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        majors = getResources().getStringArray(R.array.major);
        View v = inflater.inflate(R.layout.fragment_major, container, false);
        spinner = v.findViewById(R.id.select_major);
        b1 = v.findViewById(R.id.year_b1);
        b2 = v.findViewById(R.id.year_b2);
        b3 = v.findViewById(R.id.year_b3);
        backButton = v.findViewById(R.id.back_button);
        nextButton = v.findViewById(R.id.next_button);
        nextButton.setEnabled(false);

        backButton.setOnClickListener(view -> {
            navigateBack();
        });
        nextButton.setOnClickListener(view -> {
            navigateFragment(new PersonalityFragment());
        });

        List<String> majorList = Arrays.asList(majors);
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(
                requireContext(),
                majorList,
                hint
        );
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMajor = adapterView.getItemAtPosition(i).toString();
                checkIfSelectionAreMade();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedMajor = hint;
                checkIfSelectionAreMade();
            }
        });

        b1.setOnClickListener(view -> selectYear("b1"));
        b2.setOnClickListener(view -> selectYear("b2"));
        b3.setOnClickListener(view -> selectYear("b3"));
        return v;
    }

    private void selectYear(String year) {
        b1.setBackgroundResource(R.drawable.rounded_border);
        b2.setBackgroundResource(R.drawable.rounded_border);
        b3.setBackgroundResource(R.drawable.rounded_border);

        switch (year) {
            case "b1":
                b1.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "b2":
                b2.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "b3":
                b3.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
        }

        selectedYear = year;
        checkIfSelectionAreMade();
    }

    private void checkIfSelectionAreMade() {
        if (!selectedMajor.equals(hint) && !selectedYear.isEmpty()) {
            nextButton.setEnabled(true);
        } else {
            nextButton.setEnabled(false);
        }
    }

    private void navigateBack() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    private void navigateFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}