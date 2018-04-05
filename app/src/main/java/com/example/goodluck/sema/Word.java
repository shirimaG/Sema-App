package com.example.goodluck.sema;

/**
 * Created by GOODLUCK on 3/23/2018.
 */

public class Word {

    //Default translation  for the word
    private String mDefaultTranslation;

    //Swahili translation of the word
    private String mSwahilitranslation;

    //Image for the list item
    private int mImageResourceId;

    //NOTE: this decleration has nothing to do with WordAdapter class
    //Audio resource ID for each word
    private int mAudioResourceId;

    /**
     * this constructor is to hold only phrases in our app
     *
     * @param defaultTranslation
     * @param swahiliTranslation
     * @param audioResourceId
     */
    public Word(String defaultTranslation, String swahiliTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mSwahilitranslation = swahiliTranslation;
        mAudioResourceId = audioResourceId;
    }

    /**
     * this constuctor is to hold the rest attributes
     *
     * @param defaultTranslation
     * @param swahiliTranslation
     * @param imageResourceId
     * @param audioResourceId
     */
    public Word(String defaultTranslation, String swahiliTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mSwahilitranslation = swahiliTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Default Translation method
     *
     * @return String
     */
    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Swahili Translation method
     *
     * @return String
     */
    public String getmSwahilitranslation() {
        return mSwahilitranslation;
    }

    /**
     * Image fetch method
     *
     * @return int
     */
    public int getmImageResourceId() {
        return mImageResourceId;
    }

    /***
     * AudioResource definition
     * @return int
     */
    public int getmAudioResourceId() {
        return mAudioResourceId;
    }
}
