package com.example.fall2020androidproject.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
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
    Button sineButton;
    Button triangleButton;
    Button sawtoothButton;
    Button pulseButton;

    SeekBar volume;
    SeekBar pitch;

    public static ToneGenerator toneGenerator;
    public final ToneGenerator.Tone[] tone = {null};

    public enum PLAY_MODES{;
        public static final int CONTINUOUS = 1;
        public static final int ONE_SHOT = 2;
        public static final int HOLD = 3;
    }

    public ToneFragment() {
        // Required empty public constructor
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

        toneGenerator = new ToneGenerator();

        // Assigning the controls
        sineButton = view.findViewById(R.id.shapeSine);
        triangleButton = view.findViewById(R.id.shapeTriangle);
        sawtoothButton = view.findViewById(R.id.shapeSawtooth);
        pulseButton = view.findViewById(R.id.shapePulse);

        volume = view.findViewById(R.id.volume);
        pitch = view.findViewById(R.id.pitch);

        // Listeners
        MainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playTone(440, 1000);
            }
        });
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volume = (float) progress / 100; // Making the range 0-1
                toneGenerator.setVolume(volume);
                // Restart the tone if play_mode is continuous
                if(getActivity().getPreferences(MODE_PRIVATE).getInt(getString(R.string.key_play_mode), -1) == PLAY_MODES.CONTINUOUS){
                    startTone(440);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sineButton.setOnClickListener(new ButtonSelectorListener());
        triangleButton.setOnClickListener(new ButtonSelectorListener());
        sawtoothButton.setOnClickListener(new ButtonSelectorListener());
        pulseButton.setOnClickListener(new ButtonSelectorListener());

        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        if(sharedPreferences.getBoolean(getString(R.string.key_show_button), true)){
            MainActivity.fab.show();
        }

        return view;
    }

    /**
     * A listener for the wave shape buttons
     * This allows regular buttons to function similarly to RadioButtons
     */
    class ButtonSelectorListener implements View.OnClickListener{

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.shapeSine:
                    setSelectedButton(sineButton);
                    toneGenerator.setWaveForm(ToneGenerator.SINE);
                    break;
                case R.id.shapeTriangle:
                    setSelectedButton(triangleButton);
                    toneGenerator.setWaveForm(ToneGenerator.TRIANGLE);
                    break;
                case R.id.shapeSawtooth:
                    setSelectedButton(sawtoothButton);
                    toneGenerator.setWaveForm(ToneGenerator.SAWTOOTH);
                    break;
                case R.id.shapePulse:
                    setSelectedButton(pulseButton);
                    toneGenerator.setWaveForm(ToneGenerator.PULSE);
                    break;
                default:
                    Log.d("Selector", "Unrecognized shape button");
                    break;
            }
        }
    }

    /**
     * Method to set the selected button to cut down on duplicate code
     * @param button The button to selected
     */
    public void setSelectedButton(Button button){
        ColorStateList selected = ContextCompat.getColorStateList(getContext(), R.color.licc_dark_blue);
        ColorStateList unselected = ContextCompat.getColorStateList(getContext(), R.color.licc_blue);

        // Unset all buttons
        sineButton.setBackgroundTintList(unselected);
        triangleButton.setBackgroundTintList(unselected);
        sawtoothButton.setBackgroundTintList(unselected);
        pulseButton.setBackgroundTintList(unselected);

        // Now set the selected button
        button.setBackgroundTintList(selected);
    }

    /**
     * Plays a tone for a specified duration
     * @param frequency The frequency of the tone in Hz
     * @param duration The length of the tone in ms
     */
    public void playTone(float frequency, int duration){
        stopTone(); // Stops a tone if it is playing
        tone[0] = toneGenerator.play(frequency, duration);
    }

    /**
     * Plays a tone indefinitely
     * @param frequency The frequency of the tone in Hz
     */
    public void startTone(float frequency){
        stopTone(); // Stops a tone if it is playing
        tone[0] = toneGenerator.start(frequency);
    }

    /**
     * Stops any tone that is
     */
    public void stopTone(){
        // Ending tone if there are any playing
        if(tone[0] != null){
            tone[0].end();
        }
    }
}