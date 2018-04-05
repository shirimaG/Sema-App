package com.example.goodluck.sema;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    //MediaPlayer decleration
    private MediaPlayer mediaPlayer;

    /***
     * This Listener gets triggered when the MediaPlayer has completed playing te Audio
     * This method is declared and defined globally for easy reference
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    /**
     * Handles audio focus when playing a sound file
     */
    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };


    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_numbers, container, false);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //ArrayList of Colors from custom Word class instead of Default String class which accept one listItem
        final ArrayList<Word> words = new ArrayList<Word>();

        //the list of Numbers and its Translation
        words.add(new Word("One", "Moja", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("Two", "Mbili", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("Three", "Tatu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("Four", "Nne", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("Five", "Tano", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("Six", "Sita", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("Seven", "Saba", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("Eight", "Nane", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("Nine", "Tisa", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("Ten", "Kumi", R.drawable.number_ten, R.raw.number_ten));

        //Using Custom WordAdapter to hold data into the ListView
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.numbersBackground);

        //Call a ListView xmlLayout to handle the Items in ArrayList
        ListView numbersList = (ListView) rootView.findViewById(R.id.numbers_list_fragment);

        numbersList.setAdapter(adapter);

        numbersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);

                /***
                 * before the media player just initialized we have to release resorces
                 * note this method called and defined after the media player method
                 */
                releaseMediaPlayer();

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.

                    //Find media player file by ID

                    mediaPlayer = MediaPlayer.create(getActivity(), word.getmAudioResourceId());
                    mediaPlayer.start();

                    /***
                     * Asynchronous method is called when the event is complete
                     * Remember we defined it globally before calling it here
                     */
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }

            }
        });
        return rootView;
    }

    /***
     * this onstop method is used to kill the activity when is no longer visible
     * note it is set when the media player is set
     */
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}
