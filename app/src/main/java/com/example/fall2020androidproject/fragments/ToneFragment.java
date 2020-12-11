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
 * The ToneFragment responsible for controlling the ToneGenerator
 * @author Zachary Allard
 */
public class ToneFragment extends Fragment {
    Button sineButton;
    Button triangleButton;
    Button sawtoothButton;
    Button pulseButton;

    SeekBar volume;
    SeekBar pitch;
    float frequency = 440;

    public static final float MIN_FREQ = 110;
    public static final float MAX_FREQ = 880;

    public static ToneGenerator toneGenerator;
    public static final ToneGenerator.Tone[] tone = {null};

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
        MainActivity.fab.setOnClickListener(new OneShotListener());
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volume = (float) progress / 100; // Making the range 0-1
                toneGenerator.setVolume(volume);
                // Restart the tone if play_mode is continuous
                if(getActivity().getPreferences(MODE_PRIVATE).getInt(getString(R.string.key_play_mode), -1) == PLAY_MODES.CONTINUOUS){
                    startTone(frequency);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        pitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Setting range from A2 to A5
                frequency = (((progress * MAX_FREQ) - MIN_FREQ) + MIN_FREQ) / 100f;
                if(frequency <= MIN_FREQ){
                    frequency = MIN_FREQ;
                }

                if(getActivity().getPreferences(MODE_PRIVATE).getInt(getString(R.string.key_play_mode), -1) == PLAY_MODES.CONTINUOUS){
                    startTone(frequency);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
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

    class OneShotListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            playTone(frequency, 1000);
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
    public static void playTone(float frequency, int duration){
        stopTone(); // Stops a tone if it is playing
        tone[0] = toneGenerator.play(frequency, duration);
    }

    /**
     * Plays a tone indefinitely
     * @param frequency The frequency of the tone in Hz
     */
    public static void startTone(float frequency){
        stopTone(); // Stops a tone if it is playing
        tone[0] = toneGenerator.start(frequency);
    }

    /**
     * Stops any tone that is currently playing
     */
    public static void stopTone(){
        // Ending tone if there are any playing
        if(tone[0] != null){
            tone[0].end();
        }
    }
}