package com.project.me.notes;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.me.notes.model.ConstantType;
import com.project.me.notes.model.Note;
import com.project.me.notes.model.Notification;
import com.project.me.notes.model.Tag;
import com.tubb.smrv.SwipeMenuLayout;
import com.tubb.smrv.listener.SimpleSwipeSwitchListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllNotesFragment extends Fragment {

    private String title;
    private SwipeMenuLayout sml;
    private RecyclerView recyclerView;
    private NotesCardAdapter notesCardAdapter;
    private RealmResults<Note> noteList;
    Realm realm;

    public AllNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public static AllNotesFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);

        AllNotesFragment fragment = new AllNotesFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            title = bundle.getString("title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notes, container, false);
        realm = Realm.getDefaultInstance();
        sml = (SwipeMenuLayout) v.findViewById(R.id.sml);
        prepareNotes();
        recyclerView = (RecyclerView) v.findViewById(R.id.smContentView);

        readBundle(getArguments());
        switch (title){
            case ConstantType.All_NOTES:
                notesCardAdapter = new NotesCardAdapter(realm.where(Note.class).findAll().sort("timeStamp"));
                break;
            case ConstantType.TEXT_NOTES:
                notesCardAdapter = new NotesCardAdapter(realm.where(Note.class).not()
                        .beginGroup()
                        .equalTo("isVideo", true)
                        .or().equalTo("isAudio", true)
                        .endGroup()
                        .findAll());
                break;
            case ConstantType.MEDIA_NOTES:
                notesCardAdapter = new NotesCardAdapter(realm.where(Note.class).equalTo("isVideo", true)
                        .or().equalTo("isAudio", true).findAll());
                break;
        }

        //notesCardAdapter = new NotesCardAdapter(realm.where(Note.class).findAll());
        notesCardAdapter.setmContext(this.getContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);
//        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notesCardAdapter);






        return v;
    }

    private void prepareNotes(){

        final Tag t = new Tag("tag1", ConstantType.TAG_COLOR_LIGHT_GREEN);
        t.setId(1);
        final Tag t2 = new Tag("tag2", ConstantType.TAG_COLOR_PINK);
        t2.setId(2);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(t);
                realm.insertOrUpdate(t2);
            }
        });

        Log.i("allnotesfrag", realm.where(Tag.class).equalTo("id", 1).findFirst().getTagName());

        final Notification notification = new Notification(null, true);
        notification.setId(1);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(notification);
            }
        });

        final Note n = new Note(1343805819061L, "new 1", "text1text", t, null, null, true, false, false);
        n.setId(1);
        n.setFontSize(ConstantType.FONT_SIZE_MEDIUM);
        //noteList.add(n);
        final Note n2 = new Note(1343805819061L, "new 2", "text234234234text", t2, null, null, true, true, false);
        n2.setId(2);
        n2.setFontSize(ConstantType.FONT_SIZE_MEDIUM);
        //noteList.add(n);
        final Note n3 = new Note(1343805819061L, "new 3", "texttqrgqrgqrg1text", t, null, notification, false, true, false);
        n3.setId(3);
        n3.setFontSize(ConstantType.FONT_SIZE_MEDIUM);
        // noteList.add(n);
        final Note n4 = new Note(1343805819061L, "new 4", "textdqfgqgqehg1text", t2, null, null, false, false, false);
        n4.setId(4);
        n4.setFontSize(ConstantType.FONT_SIZE_MEDIUM);
        // noteList.add(n);
        final Note n5 = new Note(1343805819061L, "new 5", "text1qhqehqerhhqtext", t, null, notification, false, true, false);
        n5.setId(5);
        n5.setFontSize(ConstantType.FONT_SIZE_MEDIUM);
        //   noteList.add(n);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(n);
                realm.insertOrUpdate(n2);
                realm.insertOrUpdate(n3);
                realm.insertOrUpdate(n4);
                realm.insertOrUpdate(n5);
            }
        });
        Log.i("allnotesfrag", realm.where(Note.class).equalTo("id", 2).findFirst().getTag().getTagName());

        noteList = realm.where(Note.class).findAll();
        // noteList.addChangeListener(RealmChangeListener(RealmResults<Note> n));
        //notesCardAdapter = new NotesCardAdapter(realm.where(Note.class).findAll());
        //notesCardAdapter.setItems(noteList);
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override

    public void onDestroy() {
        super.onDestroy();
        recyclerView.setAdapter(null);
        realm.close();
    }
}

