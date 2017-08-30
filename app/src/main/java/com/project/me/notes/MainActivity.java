package com.project.me.notes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
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

import io.realm.Realm;
import io.realm.RealmConfiguration;

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

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton mNewNote = (FloatingActionButton) findViewById(R.id.new_note);
        mNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO передати флаг, що це нова нотатка
                Intent intent = new Intent(MainActivity.this, SingleNoteActivity.class);
                startActivity(intent);
            }
        });


        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getDefaultInstance();

        /*Realm.init(this)
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
        adapter.addFragment(AllNotesFragment.newInstance(Tegs.ALL.toString()), "All");
        /*adapter.addFragment(new AllNotesFragment(), "Textnotes");
        adapter.addFragment(new AllNotesFragment(), "Audionotes");
        adapter.addFragment(new AllNotesFragment(), "Videonotes");*/
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_all_notes, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                String search = (String) searchView.getQuery();
                //TODO search in database and show list of notes
                //https://www.google.com.ua/search?q=android+search+in+toolbar&oq=android+search+&aqs=chrome.4.69i57j69i60l3j0l2.10207j0j4&sourceid=chrome&ie=UTF-8
                return false;
            }
        });


       /* // Define the listener
        OnActionExpandListener expandListener = new OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when action item collapses
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        };

        // Get the MenuItem for the action item
        MenuItem actionMenuItem = menu.findItem(R.id.myActionItem);

        // Assign the listener to that action item
        MenuItemCompat.setOnActionExpandListener(actionMenuItem, expandListener);

        // Any other things you have to do when creating the options menu…*/


        return true;
        // return super.onCreateOptionsMenu(menu);
    }


    //menu end

}
