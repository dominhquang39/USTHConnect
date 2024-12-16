package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.edu.usth.connect.R;

public class SubjectFragment extends Fragment {

    private List<String> fav_subjects = new ArrayList<>();
    private int selectedSubjectCount = 0;
    private final int maxSubjectSelection = 5;
    private List<String> selectedSubjectList = new ArrayList<>();
    private String[] selectedSubject;
    private String[] locationStudy;
    private String[] timeStudy;
    private Spinner spinnerLocation, spinnerTime;
    private Button nextButton;
    private ImageButton backButton;
    private String hintSubject = "Choose your favourite subject";
    private String hintLocation = "Favourite location to study";
    private String hintTime = "Favourite time to study";
    private String selectedLocation, selectedtime = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        locationStudy = getResources().getStringArray(R.array.location_study);
        timeStudy = getResources().getStringArray(R.array.time_study);
        View v = inflater.inflate(R.layout.fragment_subject_fragment, container, false);

        loadSubjectsfromFile();

        FlexboxLayout flexboxLayout = v.findViewById(R.id.flexbox_layout);

        for (String subject : fav_subjects) {
            TextView tagView = createTagView(subject, flexboxLayout);
            flexboxLayout.addView(tagView);
        }

        spinnerLocation = v.findViewById(R.id.select_where_study);
        spinnerTime = v.findViewById(R.id.select_when_study);
        backButton = v.findViewById(R.id.back_button);
        nextButton = v.findViewById(R.id.next_button);
        nextButton.setEnabled(false);

        backButton.setOnClickListener(view -> {
            navigateBack();
        });

        nextButton.setOnClickListener(view -> {
            selectedSubject = selectedSubjectList.toArray(new String[0]);

        });

        List<String> locationList = Arrays.asList(locationStudy);
        CustomSpinnerAdapter adapterLocation = new CustomSpinnerAdapter(
                requireContext(),
                locationList,
                hintLocation
        );
        spinnerLocation.setAdapter(adapterLocation);

        List<String> timeList = Arrays.asList(timeStudy);
        CustomSpinnerAdapter adapterTime = new CustomSpinnerAdapter(
                requireContext(),
                timeList,
                hintTime
        );
        spinnerTime.setAdapter(adapterTime);

        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedLocation = adapterView.getItemAtPosition(i).toString();
                checkIfSelectionAreMade();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedtime = "";
                checkIfSelectionAreMade();
            }
        });
        return v;
    }

    private TextView createTagView(String subject, FlexboxLayout flexboxLayout) {
        TextView tag = new TextView(getContext());
        tag.setText(subject);
        tag.setTextSize(14f);
        tag.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
        tag.setBackgroundResource(R.drawable.rounded_border);
        tag.setPadding(16,8,16,8);

        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 8, 8, 8);
        tag.setLayoutParams(params);

        tag.setOnClickListener(view -> toggleSelection(tag, subject));

        return tag;
    }

    private void toggleSelection(TextView tag, String subject) {
        if (tag.isSelected()) {
            tag.setSelected(false);
            tag.setBackgroundResource(R.drawable.rounded_border);
            selectedSubjectCount --;
            selectedSubjectList.remove(subject);
        } else if (selectedSubjectCount < maxSubjectSelection) {
            tag.setSelected(true);
            tag.setBackgroundResource(R.drawable.rounded_border_selected);
            selectedSubjectCount ++;
            selectedSubjectList.add(subject);
        }
        checkIfSelectionAreMade();
    }

    private void checkIfSelectionAreMade() {
        if (!selectedLocation.isEmpty() && !selectedtime.isEmpty() && selectedSubjectCount > 0) {
            nextButton.setEnabled(true);
        } else {
            nextButton.setEnabled(false);
        }
    }

    private void loadSubjectsfromFile() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("subjects.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                fav_subjects.add(line.trim());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateBack() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}