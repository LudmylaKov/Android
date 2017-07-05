package com.project.me.notes;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.project.me.notes.model.Note;
import com.tubb.smrv.SwipeMenuLayout;
import com.tubb.smrv.listener.SimpleSwipeSwitchListener;
import com.tubb.smrv.listener.SwipeSwitchListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        final SwipeMenuLayout sml = (SwipeMenuLayout) findViewById(R.id.sml);
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
        /*Realm.init(this);
        Realm realm = Realm.getDefaultInstance();


        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note = realm.createObject(Note.class);
                note.setId(0);
                note.setText("new text");
                note.setTitle("title");
                note.setTimestamp((new Date()).getTime());//чи правильно?
                ArrayList<String> tags = new ArrayList<String>();
                tags.add("firstTag");
                tags.add("secondTag");
                note.setTags(tags);

            }
        },
                    new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    // Transaction was a success.
                }
            },
                    new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    // Transaction failed and was automatically canceled.
                }

            }
        );*/


    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NoteFragment(), "All");
        adapter.addFragment(new NoteFragment(), "Textnotes");
        adapter.addFragment(new NoteFragment(), "Audionotes");
        adapter.addFragment(new NoteFragment(), "Videonotes");
        adapter.addFragment(new NoteFragment(), "Picnotes");
        viewPager.setAdapter(adapter);
    }

    //menu start

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_note, menu);
        return true;
    }
    public void AddNewNote(MenuItem item) {
    }

    //menu end

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
