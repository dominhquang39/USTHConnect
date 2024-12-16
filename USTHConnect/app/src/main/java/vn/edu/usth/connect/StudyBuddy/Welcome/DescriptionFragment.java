package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import vn.edu.usth.connect.R;

public class DescriptionFragment extends Fragment {

    private EditText description;
    private ImageButton backButton;
    private Button nextButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_description, container, false);
        description = v.findViewById(R.id.input_description);
        backButton = v.findViewById(R.id.back_button);
        nextButton = v.findViewById(R.id.next_button);

        backButton.setOnClickListener(view -> {
            navigateBack();
        });

        nextButton.setOnClickListener(view -> {
            navigateFragment(new GenderFragment());
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