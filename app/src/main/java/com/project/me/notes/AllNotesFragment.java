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

import com.project.me.notes.model.Note;
import com.project.me.notes.model.Notification;
import com.project.me.notes.model.Tag;
import com.tubb.smrv.SwipeMenuLayout;
import com.tubb.smrv.listener.SimpleSwipeSwitchListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllNotesFragment extends Fragment {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notes, container, false);
        realm = Realm.getDefaultInstance();
        sml = (SwipeMenuLayout) v.findViewById(R.id.sml);

        recyclerView = (RecyclerView) v.findViewById(R.id.smContentView);
        //noteList = new ArrayList<>();
        notesCardAdapter = new NotesCardAdapter(realm.where(Note.class).findAll());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);
//        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notesCardAdapter);

        prepareNotes();




        return v;
    }

    private void prepareNotes(){

        final Tag t = new Tag("tag1", R.color.colorAccent);
        t.setId(1);
        final Tag t2 = new Tag("tag2", R.color.colorTest);
        t2.setId(2);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(t);
                realm.copyToRealmOrUpdate(t2);
            }
        });
        final Notification notification = new Notification(null, true);
        notification.setId(1);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(notification);
            }
        });

        final Note n = new Note(1343805819061L, "new 1", "text1text", t, null, null, true, false, false);
        n.setId(1);
        //noteList.add(n);
        final Note n2 = new Note(1343805819061L, "new 2", "text234234234text", t2, null, null, true, true, false);
        n2.setId(2);
        //noteList.add(n);
        final Note n3 = new Note(1343805819061L, "new 3", "texttqrgqrgqrg1text", t, null, notification, false, true, false);
        n3.setId(3);
       // noteList.add(n);
        final Note n4 = new Note(1343805819061L, "new 4", "textdqfgqgqehg1text", t2, null, null, false, false, false);
        n4.setId(4);
       // noteList.add(n);
        final Note n5 = new Note(1343805819061L, "new 5", "text1qhqehqerhhqtext", t, null, notification, false, true, false);
        n5.setId(5);
     //   noteList.add(n);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(n);
                realm.copyToRealmOrUpdate(n2);
                realm.copyToRealmOrUpdate(n3);
                realm.copyToRealmOrUpdate(n4);
                realm.copyToRealmOrUpdate(n5);
            }
        });
        //noteList = realm.where(Note.class).findAll();
        notesCardAdapter.notifyDataSetChanged();
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
