package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vn.edu.usth.connect.R;

public class WelcomeFragment extends Fragment {

    private Button nextButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);
        nextButton = v.findViewById(R.id.create_new_account);

        nextButton.setOnClickListener(view -> {
            Fragment nameFragment = new NameFragment();
            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(android.R.id.content, nameFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        return v;
    }
}