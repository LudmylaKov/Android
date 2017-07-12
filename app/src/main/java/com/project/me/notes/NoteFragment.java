package com.project.me.notes;


import android.os.Bundle;
import android.support.annotation.Nullable;
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

    private SwipeMenuLayout sml;

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
//        FloatingActionButton mNewNote = (FloatingActionButton) v.findViewById(R.id.new_note);
//        mNewNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //open new note activity
//            }
//        });
        sml = (SwipeMenuLayout) v.findViewById(R.id.sml);
        return v;
    }
}
