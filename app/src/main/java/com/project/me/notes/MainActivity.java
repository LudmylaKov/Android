package com.project.me.notes;

import android.support.design.widget.FloatingActionButton;
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
import android.view.View;

import com.tubb.smrv.SwipeMenuLayout;
import com.tubb.smrv.listener.SimpleSwipeSwitchListener;

import java.util.ArrayList;
import java.util.List;

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

        FloatingActionButton mNewNote = (FloatingActionButton) findViewById(R.id.new_note);
        mNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO open new note activity
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
        adapter.addFragment(new AllNotesFragment(), "All");
        adapter.addFragment(new AllNotesFragment(), "Textnotes");
        adapter.addFragment(new AllNotesFragment(), "Audionotes");
        adapter.addFragment(new AllNotesFragment(), "Videonotes");
//        adapter.addFragment(new NoteFragment(), "Picnotes");
        viewPager.setAdapter(adapter);
    }

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
    //menu start

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_new_note, menu);
        return true;
    }
    public void AddNewNote(MenuItem item) {
    }

    //menu end

}
