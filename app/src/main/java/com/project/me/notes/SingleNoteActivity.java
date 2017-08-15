package com.project.me.notes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.me.notes.model.Note;

import java.util.Calendar;
import java.util.Date;

public class SingleNoteActivity extends AppCompatActivity {
    private TextView mCurrentDate;
    private EditText mTitleOfNote;
    private EditText mTextOfNote;
    private TextView mNotification;
    private TextView mTagOfNote;

    private Note thisNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_note);
        Toolbar toolbarNote = (Toolbar) findViewById(R.id.toolbar_note);
        setSupportActionBar(toolbarNote);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initialViews();



        ImageView textSize = (ImageView) findViewById(R.id.textSize);
        textSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpMenuForFontSize(v);
            }
        });
    }

    private void initialViews() {
        mCurrentDate = (TextView)findViewById(R.id.currentDate);
        mTitleOfNote = (EditText) findViewById(R.id.titleNote);
        mTextOfNote = (EditText) findViewById(R.id.textNote);
        mNotification = (TextView) findViewById(R.id.notificationNote);
        mTagOfNote = (TextView) findViewById(R.id.tagNote);

        thisNote = new Note();
        //mCurrentDate.setText(DateFormat.format("HH:mm dd/MM/yyyy", new Date()).toString());
        //long time = System.currentTimeMillis();
        //long time2 = currentTime.get(Calendar.ZONE_OFFSET)+currentTime.get(Calendar.DST_OFFSET);

        Calendar currentTime = Calendar.getInstance();
        long time3 = currentTime.getTimeInMillis();
        mCurrentDate.setText(DateFormat.format("HH:mm dd/MM/yyyy", currentTime).toString());

        thisNote.setTimeStamp(time3);
    }

    //POPUP MENU
    private void showPopUpMenuForFontSize(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenu_font_size);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getGroupId()) {
                    case R.id.font_small:
                        mCurrentDate.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.small_date_size));
                        mTitleOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.small_title_size));
                        mTextOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.small_text_size));
                        mNotification.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.small_notification_size));
                        mTagOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.small_tag_size));
                        item.setChecked(true);
                        return true;
                    case R.id.font_medium:
                        mCurrentDate.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.medium_date_size));
                        mTitleOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.medium_title_size));
                        mTextOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.medium_text_size));
                        mNotification.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.medium_notification_size));
                        mTagOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.medium_tag_size));
                        item.setChecked(true);
                        return true;
                    case R.id.font_large:
                        mCurrentDate.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.large_date_size));
                        mTitleOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.large_title_size));
                        mTextOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.large_text_size));
                        mNotification.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.large_notification_size));
                        mTagOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.large_tag_size));
                        item.setChecked(true);
                        return true;
                }
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {

            }
        });
        popupMenu.show();
    }


    //MENU
    private ShareActionProvider mShareActionProvider;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_new_note, menu);
        MenuItem shareItem = menu.findItem(R.id.action_share_note);
        /*ShareActionProvider myShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        Intent myShareIntent = new Intent(Intent.ACTION_SEND);
        myShareIntent.setType("image*//*");
        myShareIntent.putExtra(Intent.EXTRA_STREAM, myImageUri);
        myShareActionProvider.setShareIntent(myShareIntent);*/
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        setShareIntent(createShareIntent());
        // Return true to display menu

        return true;
    }
    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
    private Intent createShareIntent() {
        //get text og the note
        EditText text = (EditText) findViewById(R.id.textNote);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                text.getText());
        //TODO add share of media data and  https://developer.android.com/training/sharing/send.html
        return shareIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_tag:
                // open dialog to add tag and chose it color
                return true;

            case R.id.action_add_file:
                // open dialog to chose file (mic, pic, vid, au)
                return true;

            case R.id.action_delete_note:
                // delete and return to main activity
                return true;
            case R.id.action_share_note:
                // open dialog to share the note
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.

                saveData();
                return super.onOptionsItemSelected(item);

        }
    }

    private void saveData() {
        if(!TextUtils.isEmpty(mTitleOfNote.getText())){

        }
    }
}
