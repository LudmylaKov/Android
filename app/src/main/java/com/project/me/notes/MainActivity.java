package com.project.me.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.me.notes.model.ConstantType;
import com.project.me.notes.model.Media;
import com.project.me.notes.model.Note;
import com.project.me.notes.model.Tag;
import com.tubb.smrv.SwipeMenuLayout;
import com.tubb.smrv.listener.SimpleSwipeSwitchListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Realm realm;

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
                Intent intent = new Intent(MainActivity.this, SingleNoteActivity.class);
                startActivity(intent);
            }
        });


        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }
    ViewPagerAdapter adapter;
    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

//        adapter.addFragment(new AllNotesFragment(), "All");
//        adapter.addFragment(new AllNotesFragment(), "Textnotes");
//        adapter.addFragment(new AllNotesFragment(), "Medianotes");
        adapter.addFragment(AllNotesFragment.newInstance(ConstantType.All_NOTES), ConstantType.All_NOTES);
        adapter.addFragment(AllNotesFragment.newInstance(ConstantType.TEXT_NOTES), ConstantType.TEXT_NOTES);
        adapter.addFragment(AllNotesFragment.newInstance(ConstantType.MEDIA_NOTES), ConstantType.MEDIA_NOTES);
//        adapter.addFragment(new NoteFragment(), "Picnotes");
        Log.i("allfragment", "defoult");
        viewPager.setAdapter(adapter);
    }

    private static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final FragmentManager mFragmentManager;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            mFragmentManager = manager;
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


        public void replaceFragment(Fragment fragment, String title, int index) {
           /* mFragmentList.remove(index);
            mFragmentList.add(index, fragment);
            mFragmentTitleList.remove(index);
            mFragmentTitleList.add(index, title);
            // do the same for the title
            Log.i("allfragment", "abdc");
            notifyDataSetChanged();*/
            Log.i("allfragment", String.valueOf(mFragmentList.size()));
            mFragmentManager.beginTransaction().remove(mFragmentList.get(index)).commit();
           // mFragmentAtPos0 = NextFragment.newInstance();
            Log.i("allfragment", "replace");
            mFragmentList.remove(index);
            mFragmentTitleList.remove(index);
            mFragmentList.add(index, fragment);
            mFragmentTitleList.add(index, title);
            notifyDataSetChanged();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
        @Override
        public int getItemPosition(Object object)
        {
            if (object instanceof AllNotesFragment)
                return POSITION_NONE;
            return POSITION_UNCHANGED;
        }
    }


    //menu start
    String search;
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
                search = (String) searchView.getQuery();
                adapter.replaceFragment(AllNotesFragment.newInstance("search"+search), search, 0);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:

                return true;
            case R.id.noteAll:
                adapter.replaceFragment(AllNotesFragment.newInstance(ConstantType.All_NOTES), ConstantType.All_NOTES, 0);
                adapter.replaceFragment(AllNotesFragment.newInstance(ConstantType.TEXT_NOTES), ConstantType.TEXT_NOTES, 1);
                adapter.replaceFragment(AllNotesFragment.newInstance(ConstantType.MEDIA_NOTES), ConstantType.MEDIA_NOTES, 2);
                return true;
            case R.id.deleteAll:
                final RealmResults<Note> results = realm.where(Note.class).findAll();
                final RealmResults<Media> resultMedia = realm.where(Media.class).findAll();
                final RealmResults<Tag> resultTag = realm.where(Tag.class).findAll();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        // Delete all matches
                        results.deleteAllFromRealm();
                        resultMedia.deleteAllFromRealm();
                        resultTag.deleteAllFromRealm();
                    }
                });

                return true;
            case R.id.sortByABC:

                adapter.replaceFragment(AllNotesFragment.newInstance(ConstantType.All_NOTES_ABC), ConstantType.All_NOTES, 0);
                adapter.replaceFragment(AllNotesFragment.newInstance(ConstantType.TEXT_NOTES_ABC), ConstantType.TEXT_NOTES, 1);
                adapter.replaceFragment(AllNotesFragment.newInstance(ConstantType.MEDIA_NOTES_ABC), ConstantType.MEDIA_NOTES, 2);
                Log.i("allfragment", "abdc");

                //viewPager.setAdapter(adapter1);

                //tabLayout.setupWithViewPager(viewPager);
                return true;
            case R.id.sortByDate:

                adapter.replaceFragment(AllNotesFragment.newInstance(ConstantType.All_NOTES_DESC), ConstantType.All_NOTES, 0);
                adapter.replaceFragment(AllNotesFragment.newInstance(ConstantType.TEXT_NOTES_DESC), ConstantType.TEXT_NOTES, 1);
                adapter.replaceFragment(AllNotesFragment.newInstance(ConstantType.MEDIA_NOTES_DESC), ConstantType.MEDIA_NOTES, 2);


                return true;
            case R.id.tags:
                final AlertDialog alert;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //alert = builder.create();
                LayoutInflater inflater = this.getLayoutInflater();
                View layout =  inflater.inflate(R.layout.dialog_choose_tag, null);
                alert = builder.setView(layout)
                        .show();
                //LinearLayout linearLayout2 = (LinearLayout) findViewById(R.layout.dialog_choose_tag);
                LinearLayout linearLayout = (LinearLayout) layout.findViewById(R.id.choose_tag);
                //linearLayout.setOrientation(LinearLayout.VERTICAL);
                RealmResults<Tag> tags = realm.where(Tag.class).findAll();
                for(int i = 0; i < tags.size(); ++i){
                    TextView textView = new TextView(this);
                    textView.setText(tags.get(i).getTagName());
                    linearLayout.addView(textView, new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen.medium_tag_size));
                    textView.setPadding(10, 10, 10, 10);
                    textView.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
                    Drawable mDrawable = ContextCompat.getDrawable(this, R.drawable.ic_tag_blue_24dp).mutate();
                    mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(tags.get(i).getColorValue()),
                            PorterDuff.Mode.SRC_IN));
                    textView.setCompoundDrawablesWithIntrinsicBounds(mDrawable, null, null, null);
                    textView.setCompoundDrawablePadding(10);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView tw = (TextView) v;

                            adapter.replaceFragment(AllNotesFragment.newInstance(tw.getText().toString()), tw.getText().toString(), 0);
//                            viewPager.setAdapter(adapter2);
//                            tabLayout.setupWithViewPager(viewPager);
                            Log.i("allfragment", tw.getText().toString());
                            alert.dismiss();
                        }
                    });
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //menu end

}
