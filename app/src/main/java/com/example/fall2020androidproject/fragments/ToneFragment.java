package com.example.fall2020androidproject.fragments;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fall2020androidproject.MainActivity;
import com.example.fall2020androidproject.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToneFragment extends Fragment {
    public enum PLAY_MODES{;
        public static final int CONTINUOUS = 1;
        public static final int ONE_SHOT = 2;
        public static final int HOLD = 3;
    }

    public ToneFragment() {
        // Required empty public constructor
    }
    


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ToneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToneFragment newInstance() {
        ToneFragment fragment = new ToneFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tone, container, false);

        TextView title = view.findViewById(R.id.toneTitle);
        TextView instructions = view.findViewById(R.id.toneDescription);
        // Hiding the title and instructions when in landscape mode
        title.setVisibility((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? View.GONE : View.VISIBLE);
        instructions.setVisibility((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? View.GONE : View.VISIBLE);

        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        if(sharedPreferences.getBoolean(getString(R.string.key_show_button), true)){
            MainActivity.fab.show();
        }

        return view;
    }
}