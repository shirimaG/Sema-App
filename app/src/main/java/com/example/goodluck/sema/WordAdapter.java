package com.example.goodluck.sema;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GOODLUCK on 3/23/2018.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    /** Resource ID for the background color for this list of words
     * NOTE: this has nothing to do with Word class
     */
    private  int mColorsResourceId;


    public WordAdapter(Context context, ArrayList<Word> words, int colorsResourceId) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        mColorsResourceId=colorsResourceId;
    }

    /**
     * this method calls the View layout we created, in our case list_item
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //check if existing view can  be used
        View listItemView = convertView;
        if (listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
            R.layout.list_item,parent,false);
        }

        // Get the {@link Word} object located at this position in the list
        Word currentWord = getItem(position);

        //Find the textView in the view item.xml for english translation
        TextView defaultTextview = listItemView.findViewById(R.id.english);

        // Get the DefaultTranslation from the current Word  object and
        // set this text on the english TextView
        defaultTextview.setText(currentWord.getmDefaultTranslation());

        //Find the textView in the view item.xml for Swahili translation
        TextView swahiliTextView = listItemView.findViewById(R.id.swahili);

        // Get the DefaultTranslation from the current Word  object and
        // set this text on the english TextView
        swahiliTextView.setText(currentWord.getmSwahilitranslation());

        //Find the ImageView in the view item.xml for ImageResourceId
        ImageView imageView = listItemView.findViewById(R.id.image_view);
        imageView.setImageResource(currentWord.getmImageResourceId());

        /***
         * set the theme color for the list item
         * REMEMBER TO NOT INCLUDE THIS CODE DURING CODING BEFORE
         */
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that resource ID map to
        int color = ContextCompat.getColor(getContext(),mColorsResourceId);
        textContainer.setBackgroundColor(color);


        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
