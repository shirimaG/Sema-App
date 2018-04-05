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
public class FamilyFragment extends Fragment {

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


    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_family, container, false);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("Father", "Baba", R.drawable.family_father,
                R.raw.family_father));
        words.add(new Word("Mother", "Mama", R.drawable.family_mother,
                R.raw.family_mother));
        words.add(new Word("Son", "Mtoto wa kiume", R.drawable.family_son,
                R.raw.family_son));
        words.add(new Word("Daughter", "Mtoto wa kike", R.drawable.family_daughter,
                R.raw.family_daughter));
        words.add(new Word("Older Brother", "Kaka mkubwa", R.drawable.family_older_brother,
                R.raw.family_older_brother));
        words.add(new Word("Younger Brother", "Kaka mdogo", R.drawable.family_younger_brother,
                R.raw.family_younger_brother));
        words.add(new Word("Older Sister", "Dada mkubwa", R.drawable.family_older_sister,
                R.raw.family_older_sister));
        words.add(new Word("Grand Mother", "Bibi", R.drawable.family_grandmother,
                R.raw.family_grandmother));
        words.add(new Word("Grand Father", "Babu", R.drawable.family_grandfather,
                R.raw.family_grandfather));

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.familyBackground);

        ListView familyList = (ListView) rootView.findViewById(R.id.family_list_fragment);

        familyList.setAdapter(adapter);

        familyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
