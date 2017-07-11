package com.project.me.notes;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tubb.smrv.SwipeMenuLayout;
import com.tubb.smrv.listener.SimpleSwipeSwitchListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment {


    public NoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_notes, container, false);
        FloatingActionButton mNewNote = (FloatingActionButton) v.findViewById(R.id.new_note);
        mNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open new note activity
            }
        });
        final SwipeMenuLayout sml = (SwipeMenuLayout) v.findViewById(R.id.sml);
        sml.setSwipeListener(new SimpleSwipeSwitchListener() {
            @Override
            public void beginMenuClosed(SwipeMenuLayout swipeMenuLayout) {
                Log.e("main", "left menu closed");
            }

            @Override
            public void beginMenuOpened(SwipeMenuLayout swipeMenuLayout) {
                Log.e("main", "left menu opened");
            }

            @Override
            public void endMenuClosed(SwipeMenuLayout swipeMenuLayout) {
                Log.e("main", "right menu closed");
            }

            @Override
            public void endMenuOpened(SwipeMenuLayout swipeMenuLayout) {
                Log.e("main", "right menu opened");
            }
        });

        return v;
    }

}
