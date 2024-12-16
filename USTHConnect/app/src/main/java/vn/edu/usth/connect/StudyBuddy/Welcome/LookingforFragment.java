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

public class LookingforFragment extends Fragment {

    private ImageButton backButton;
    private Button lookingFor_chat, lookingFor_share_knowledge, lookingFor_supporter, vid_call, phone_call, text_message, f2f, nextButton;
    private String selectedLookingFor = "";
    private String selectedCommunicationStyle = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lookingfor, container, false);
        lookingFor_chat = v.findViewById(R.id.chit_chat);
        lookingFor_share_knowledge = v.findViewById(R.id.share_knowledge);
        lookingFor_supporter = v.findViewById(R.id.study_supporter);
        vid_call = v.findViewById(R.id.video_call);
        phone_call = v.findViewById(R.id.phone_call);
        text_message = v.findViewById(R.id.text_message);
        f2f = v.findViewById(R.id.face_to_face);
        backButton = v.findViewById(R.id.back_button);
        nextButton = v.findViewById(R.id.next_button);

        nextButton.setEnabled(false);

        backButton.setOnClickListener(view -> {
            navigateBack();
        });

        nextButton.setOnClickListener(view -> {
            navigateFragment(new SubjectFragment());
        });
        lookingFor_chat.setOnClickListener(view -> handleLookingFor("Chit-chatting"));
        lookingFor_share_knowledge.setOnClickListener(view -> handleLookingFor("Share knowledge"));
        lookingFor_supporter.setOnClickListener(view -> handleLookingFor("Study supporter"));


        vid_call.setOnClickListener(view -> handleCommunicationStyle("Video call"));
        phone_call.setOnClickListener(view -> handleCommunicationStyle("Phone call"));
        text_message.setOnClickListener(view -> handleCommunicationStyle("Text message"));
        f2f.setOnClickListener(view -> handleCommunicationStyle("In-person (face-to-face)"));
        return v;
    }

    private void handleCommunicationStyle(String communicationStyle) {
        vid_call.setBackgroundResource(R.drawable.rounded_border);
        phone_call.setBackgroundResource(R.drawable.rounded_border);
        text_message.setBackgroundResource(R.drawable.rounded_border);
        f2f.setBackgroundResource(R.drawable.rounded_border);

        switch (communicationStyle) {
            case "Video call":
                vid_call.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "Phone call":
                phone_call.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "Text message":
                text_message.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "In-person (face-to-face)":
                f2f.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
        }

        selectedCommunicationStyle = communicationStyle;
        checkIfSelectionAreMade();
    }

    private void handleLookingFor(String lookingfor) {
        lookingFor_chat.setBackgroundResource(R.drawable.rounded_border);
        lookingFor_share_knowledge.setBackgroundResource(R.drawable.rounded_border);
        lookingFor_supporter.setBackgroundResource(R.drawable.rounded_border);

        switch (lookingfor) {
            case "Chit-chatting":
                lookingFor_chat.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "Share knowledge":
                lookingFor_share_knowledge.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "Study supporter":
                lookingFor_supporter.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
        }

        selectedLookingFor = lookingfor;
        checkIfSelectionAreMade();
    }

    private void checkIfSelectionAreMade() {
        if (!selectedLookingFor.isEmpty() && !selectedCommunicationStyle.isEmpty()) {
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
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}