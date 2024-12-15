package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;

public class InterestFragment extends Fragment {

    private List<String> interests = new ArrayList<>();
    private int selectedCount = 0;
    private final int maxSelection = 5;
    private List<String> selectedInterestList = new ArrayList<>();
    private String[] selectedInterest;
    private Button nextButton;
    private ImageButton backButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_interest, container, false);
        
        loadInterestsfromFile();

        FlexboxLayout flexboxLayout = v.findViewById(R.id.flexbox_layout);
        nextButton = v.findViewById(R.id.next_button);
        backButton = v.findViewById(R.id.back_button);

        for (String interest : interests) {
            TextView tagView = createTagView(interest, flexboxLayout);
            flexboxLayout.addView(tagView);
        }

        nextButton.setEnabled(false);

        backButton.setOnClickListener(view -> {
            navigateBack();
        });

        nextButton.setOnClickListener(view -> {
            selectedInterest = selectedInterestList.toArray(new String[0]);
            navigateFragment(new LookingforFragment());
        });
        return v;
    }

    private TextView createTagView(String interest, FlexboxLayout flexboxLayout) {
        TextView tag = new TextView(getContext());
        tag.setText(interest);
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

        tag.setOnClickListener(view -> toggleSelection(tag, interest));

        return tag;
    }

    private void toggleSelection(TextView tag, String interest) {
        if (tag.isSelected()) {
            tag.setSelected(false);
            tag.setBackgroundResource(R.drawable.rounded_border);
            selectedCount --;
            selectedInterestList.remove(interest);
        } else if (selectedCount < maxSelection) {
            tag.setSelected(true);
            tag.setBackgroundResource(R.drawable.rounded_border_selected);
            selectedCount ++;
            selectedInterestList.add(interest);
        }
        updateButton();
    }

    private void updateButton() {
        Button continueButton = getView() != null ? getView().findViewById(R.id.next_button) : null;
        if (continueButton != null) {
            continueButton.setText("Next (" + selectedCount + "/" + maxSelection + ")");
            continueButton.setEnabled(selectedCount > 0);
        }
    }

    private void loadInterestsfromFile() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("interests.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                interests.add(line.trim());
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

    private void navigateFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}