package com.project.me.notes;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.project.me.notes.model.ConstantType;
import com.project.me.notes.model.Media;
import com.project.me.notes.model.Note;
import com.project.me.notes.model.Tag;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static android.R.attr.type;

public class SingleNoteActivity extends AppCompatActivity {
    private static final int ACTIVITY_CHOOSE_FILE = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2;

    private TextView currentDate;
    private EditText titleOfNote;
    private EditText textOfNote;
    private TextView notification;
    private TextView tagOfNote;
    private ImageView attachmentImage;
    private VideoView attachmentVideo;

    ImageView textColorView;

    private Note thisNote;
    Realm realm;
    Integer id = -1;
    Integer fontSize;
    String textColor;
    Tag tagNew;
    ArrayList<Media> mediaNew;
    Boolean createdNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_note);
        Toolbar toolbarNote = (Toolbar) findViewById(R.id.toolbar_note);
        setSupportActionBar(toolbarNote);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initialViews();
        //Realm.init(this);
        realm = Realm.getDefaultInstance();
        //thisNote = new Note();
        tagNew = new Tag();
        mediaNew = new ArrayList<Media>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("ID");
            thisNote = realm.where(Note.class).equalTo("id", id).findFirst();
            createdNote = false;
            initialFieldsById();
        }
        else{
            thisNote = new Note();
            thisNote.setFontSize(ConstantType.FONT_SIZE_MEDIUM);
            fontSize = thisNote.getFontSize();
            textColor = ConstantType.TEXT_COLOR_BLACK;

            createdNote = true;
            initialFieldsNew();
        }


        /*Drawable mDrawable = this.getResources().getDrawable(R.drawable.ic_tag_black_24dp);
        mDrawable.setColorFilter(new
                PorterDuffColorFilter(Color.parseColor(thisNote.getTag().getColorValue()), PorterDuff.Mode.MULTIPLY));
*/
        ImageView textSize = (ImageView) findViewById(R.id.textSize);
        textSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpMenuForFontSize(v);
            }
        });
        textColorView = (ImageView) findViewById(R.id.textColor);
        textColorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow popupwindow_obj = popupDisplay();
                popupwindow_obj.showAsDropDown(v, -55, 15);
            }
        });
        setColorOfTextColorView(textColor);

        /*textColor.setColorFilter(new
                        PorterDuffColorFilter(Color.parseColor(ConstantType.TAG_COLOR1),
                PorterDuff.Mode.MULTIPLY));*/



    }
    PopupWindow popupWindow;

    @TargetApi(21)
    public PopupWindow popupDisplay()
    {

        popupWindow = new PopupWindow(this);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_color_picker, null);
        // inflate your layout or dynamically add view


        //Button item = (Button) view.findViewById(R.id.button1);

        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setElevation(15);


        Drawable mDrawable1 = ContextCompat.getDrawable(this, R.drawable.ic_lens_black_24dp).mutate();
        Drawable mDrawable2 = ContextCompat.getDrawable(this, R.drawable.ic_lens_black_24dp).mutate();
        Drawable mDrawable3 = ContextCompat.getDrawable(this, R.drawable.ic_lens_black_24dp).mutate();
        Drawable mDrawable4 = ContextCompat.getDrawable(this, R.drawable.ic_lens_black_24dp).mutate();
        Drawable mDrawable5 = ContextCompat.getDrawable(this, R.drawable.ic_lens_black_24dp).mutate();
        Drawable mDrawable6 = ContextCompat.getDrawable(this, R.drawable.ic_lens_black_24dp).mutate();

        ImageView CPBlack = (ImageView) view.findViewById(R.id.color_picker_black);
        mDrawable1.setColorFilter(new PorterDuffColorFilter(Color.parseColor(ConstantType.TEXT_COLOR_BLACK), PorterDuff.Mode.SRC_IN));
        CPBlack.setImageDrawable(mDrawable1);
        ImageView CPGray = (ImageView) view.findViewById(R.id.color_picker_gray);
        mDrawable2.setColorFilter(new PorterDuffColorFilter(Color.parseColor(ConstantType.TEXT_COLOR_GRAY), PorterDuff.Mode.SRC_IN));
        CPGray.setImageDrawable(mDrawable2);
        ImageView CPGreen = (ImageView) view.findViewById(R.id.color_picker_green);
        mDrawable3.setColorFilter(new PorterDuffColorFilter(Color.parseColor(ConstantType.TEXT_COLOR_GREEN), PorterDuff.Mode.SRC_IN));
        CPGreen.setImageDrawable(mDrawable3);
        ImageView CPBlue = (ImageView) view.findViewById(R.id.color_picker_blue);
        mDrawable4.setColorFilter(new PorterDuffColorFilter(Color.parseColor(ConstantType.TEXT_COLOR_BLUE), PorterDuff.Mode.SRC_IN));
        CPBlue.setImageDrawable(mDrawable4);
        ImageView CPRed = (ImageView) view.findViewById(R.id.color_picker_red);
        mDrawable5.setColorFilter(new PorterDuffColorFilter(Color.parseColor(ConstantType.TEXT_COLOR_RED), PorterDuff.Mode.SRC_IN));
        CPRed.setImageDrawable(mDrawable5);
        ImageView CPPink = (ImageView) view.findViewById(R.id.color_picker_pink);
        mDrawable6.setColorFilter(new PorterDuffColorFilter(Color.parseColor(ConstantType.TEXT_COLOR_PINK), PorterDuff.Mode.SRC_IN));
        CPPink.setImageDrawable(mDrawable6);

        //mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(thisNote.getTag().getColorValue()),


        // textColor1.setBackgroundColor(Color.CYAN);
        switch(textColor){
            case ConstantType.TEXT_COLOR_BLACK:
                CPBlack.setBackgroundColor(Color.parseColor(ConstantType.TAG_COLOR_DEFAULT));
                break;
            case ConstantType.TEXT_COLOR_GRAY:
                CPGray.setBackgroundColor(Color.parseColor(ConstantType.TAG_COLOR_DEFAULT));
                break;
            case ConstantType.TEXT_COLOR_GREEN:
                CPGreen.setBackgroundColor(Color.parseColor(ConstantType.TAG_COLOR_DEFAULT));
                break;
            case ConstantType.TEXT_COLOR_BLUE:
                CPBlue.setBackgroundColor(Color.parseColor(ConstantType.TAG_COLOR_DEFAULT));
                break;
            case ConstantType.TEXT_COLOR_RED:
                CPRed.setBackgroundColor(Color.parseColor(ConstantType.TAG_COLOR_DEFAULT));
                break;
            case ConstantType.TEXT_COLOR_PINK:
                CPPink.setBackgroundColor(Color.parseColor(ConstantType.TAG_COLOR_DEFAULT));
                break;
        }

        return popupWindow;
    }

    private void initialFieldsById() {
        setTextSize(thisNote.getFontSize());
        setTextColor(thisNote.getTextColor());
        fontSize = thisNote.getFontSize();
        textColor = thisNote.getTextColor();

        String dateString = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(new Date(thisNote.getTimeStamp()));
        currentDate.setText(dateString);
        titleOfNote.setText(thisNote.getTitle());
        textOfNote.setText(thisNote.getText());


        if(thisNote.getTag()!=null){
           /* tagOfNote.setText(thisNote.getTag().getTagName());
            //tagOfNote.setBackgroundColor(Color.parseColor(thisNote.getTag().getColorValue()));
            tagOfNote.setVisibility(View.VISIBLE);
            Drawable mDrawable = ContextCompat.getDrawable(this, R.drawable.ic_tag_blue_24dp).mutate();
            mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(thisNote.getTag().getColorValue()),
                    PorterDuff.Mode.SRC_IN));
            tagOfNote.setCompoundDrawablesWithIntrinsicBounds(mDrawable, null, null, null);*/

            tagNew.setId(thisNote.getTag().getId());
            tagNew.setTagName(thisNote.getTag().getTagName());
            tagNew.setColorValue(thisNote.getTag().getColorValue());
            addTagToView(tagNew);
        }

        if(thisNote.isPicture()||thisNote.isVideo()){
            RealmList<Media> mediaThis = thisNote.getMedia();
            for(int i = 0; i < mediaThis.size();++i){
                Media media = new Media();
                media.setId(mediaThis.get(i).getId());
                media.setFilePath(mediaThis.get(i).getFilePath());
                media.setFileType(mediaThis.get(i).getFileType());
                mediaNew.add(media);
                if (mediaThis.get(i).getFileType() == ConstantType.TYPE_PICTURE) {

                    attachmentImage.setImageURI(Uri.fromFile(new File(mediaThis.get(i).getFilePath())));
                    Log.i("urimagge",Uri.parse(mediaThis.get(i).getFilePath()).toString());
                }
                if(mediaThis.get(i).getFileType() == ConstantType.TYPE_VIDEO){
                    addVideoToView(mediaThis.get(i).getFilePath());
                }
            }
            //attachmentImage.setImageURI(Uri.parse("content://com.android.providers.downloads.documents/document/6"));

        }

    }

    private void addVideoToView(String filePath) {
        //Uri myVideoUri= Uri.fromFile(new File(filePath));
        Uri myVideoUri= Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ert);
        try {

            attachmentVideo.setVideoURI(myVideoUri);

        MediaController mediaController = new MediaController(this);
        attachmentVideo.setMediaController(mediaController);
        mediaController.setMediaPlayer(attachmentVideo);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        Log.i("urimagge", myVideoUri.toString());
        //attachmentVideo.setKeepScreenOn(true);
       // attachmentVideo.setVideoPath("file:///storage/emulated/0/Download/Pair Of Adorable Kittens.mp4");
        attachmentVideo.start();
        attachmentVideo.requestFocus();
    }


    private void initialFieldsNew() {
        Calendar currentTime = Calendar.getInstance();
        long time3 = currentTime.getTimeInMillis();
        currentDate.setText(DateFormat.format("HH:mm dd/MM/yyyy", currentTime).toString());

        thisNote.setTimeStamp(time3);

        setTextSize(thisNote.getFontSize());
        setTextColor(textColor);

        addVideoToView("ert");
    }

    private void initialViews() {
        currentDate = (TextView) findViewById(R.id.currentDate);
        titleOfNote = (EditText) findViewById(R.id.titleNote);
        textOfNote = (EditText) findViewById(R.id.textNote);
        notification = (TextView) findViewById(R.id.notificationNote);
        tagOfNote = (TextView) findViewById(R.id.tagNote);
        attachmentImage = (ImageView) findViewById(R.id.attachment_image);
        attachmentVideo = (VideoView) findViewById(R.id.attachment_video);

        //currentDate.setText(DateFormat.format("HH:mm dd/MM/yyyy", new Date()).toString());
        //long time = System.currentTimeMillis();
        //long time2 = currentTime.get(Calendar.ZONE_OFFSET)+currentTime.get(Calendar.DST_OFFSET);


    }

    private void setTextSize(int fontSize) {
        switch (fontSize){
            case ConstantType.FONT_SIZE_SMALL:
                currentDate.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.small_date_size));
                titleOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.small_title_size));
                textOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.small_text_size));
                notification.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.small_notification_size));
                tagOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.small_tag_size));
                //thisNote.setFontSize(ConstantType.FONT_SIZE_SMALL);
                fontSize = thisNote.getFontSize();
                break;
            case ConstantType.FONT_SIZE_MEDIUM:
                currentDate.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.medium_date_size));
                titleOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.medium_title_size));
                textOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.medium_text_size));
                notification.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.medium_notification_size));
                tagOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.medium_tag_size));
                //thisNote.setFontSize(ConstantType.FONT_SIZE_MEDIUM);
                fontSize = thisNote.getFontSize();
                break;
            case ConstantType.FONT_SIZE_LARGE:
                currentDate.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.large_date_size));
                titleOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.large_title_size));
                textOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.large_text_size));
                notification.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.large_notification_size));
                tagOfNote.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.large_tag_size));
                //thisNote.setFontSize(ConstantType.FONT_SIZE_LARGE);
                fontSize = thisNote.getFontSize();
                break;
        }
    }

    //POPUP MENU Font Size
    private void showPopUpMenuForFontSize(View v) {
        final PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenu_font_size);
        if(currentDate.getTextSize()==getResources().getDimension(R.dimen.small_date_size)){
            popupMenu.getMenu().findItem(R.id.font_small).setChecked(true);
        }
        if(currentDate.getTextSize()==getResources().getDimension(R.dimen.medium_date_size)){
            popupMenu.getMenu().findItem(R.id.font_medium).setChecked(true);
        }
        if(currentDate.getTextSize()==getResources().getDimension(R.dimen.large_date_size)){
            popupMenu.getMenu().findItem(R.id.font_large).setChecked(true);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.font_small:
                        setTextSize(ConstantType.FONT_SIZE_SMALL);
                        popupMenu.getMenu().findItem(R.id.font_large).setChecked(false);
                        popupMenu.getMenu().findItem(R.id.font_medium).setChecked(false);
                        item.setChecked(true);
                        break;
                    case R.id.font_medium:
                        setTextSize(ConstantType.FONT_SIZE_MEDIUM);
                        popupMenu.getMenu().findItem(R.id.font_large).setChecked(false);
                        popupMenu.getMenu().findItem(R.id.font_small).setChecked(false);
                        item.setChecked(true);
                        break;
                    case R.id.font_large:
                        setTextSize(ConstantType.FONT_SIZE_LARGE);
                        popupMenu.getMenu().findItem(R.id.font_small).setChecked(false);
                        popupMenu.getMenu().findItem(R.id.font_medium).setChecked(false);
                        item.setChecked(true);
                        break;
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
                chooseOrAddTagDialog();
                return true;

            case R.id.action_add_file:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                    return true;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.choose_type)
                        .setItems(R.array.choose_file, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                switch(which){
                                    case 0:
                                        addFile(ConstantType.TYPE_PICTURE);
                                        //Toast.makeText(SingleNoteActivity.this, "picture", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        addFile(ConstantType.TYPE_VIDEO);
                                        //Toast.makeText(SingleNoteActivity.this, "video", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        addFile(ConstantType.TYPE_AUDIO);
                                        //Toast.makeText(SingleNoteActivity.this, "audio", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        }).show();


                return true;

            case R.id.action_delete_note:
                // delete and return to main activity
                if(id!=-1) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            // remove a single object
                            thisNote.deleteFromRealm();
                        }
                    });
                }

                finish();
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
    int mediaType;
    private void addFile(int type) {

        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        switch(type){
            case ConstantType.TYPE_PICTURE:
                chooseFile.setType("image/*");
                break;
            case ConstantType.TYPE_VIDEO:
                chooseFile.setType("video/*");
                break;
            case ConstantType.TYPE_AUDIO:
                chooseFile.setType("audio/*");
                break;
        }
       // chooseFile.setType("image/*");
        intent = Intent.createChooser(chooseFile, "Choose a file");
        mediaType = type;
        Log.i("urimagge", String.valueOf(mediaType));
        startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            /*Intent chooseFile;
            Intent intent;
            chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
            chooseFile.setType("image*//*");
            intent = Intent.createChooser(chooseFile, "Choose a file");
            startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);*/
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.choose_type)
                    .setItems(R.array.choose_file, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                            switch(which){
                                case 0:
                                    addFile(ConstantType.TYPE_PICTURE);
                                    //Toast.makeText(SingleNoteActivity.this, "picture", Toast.LENGTH_SHORT).show();
                                    break;
                                case 1:
                                    addFile(ConstantType.TYPE_VIDEO);
                                    //Toast.makeText(SingleNoteActivity.this, "video", Toast.LENGTH_SHORT).show();
                                    break;

                            }
                        }
                    }).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        String path = "";
//        if (requestCode == ACTIVITY_CHOOSE_FILE) {
        final Uri uri = data.getData();
        switch(mediaType){
            case ConstantType.TYPE_PICTURE:
                path = ImageFilePath.getPath(this, uri); // should the path be here in this string
                attachmentImage.setImageURI(uri);

                Media image = new Media();
                image.setFileType(ConstantType.TYPE_PICTURE);
                image.setFilePath(path);
                thisNote.setPicture(true);
                mediaNew.add(image);
                break;
            case ConstantType.TYPE_VIDEO:
                path = ImageFilePath.getPath(this, uri); // should the path be here in this string
                addVideoToView(path);
                break;
            case ConstantType.TYPE_AUDIO:
                path = uri.getPath(); // should the path be here in this string
                break;
        }
        Log.i("urimagge", String.valueOf(mediaType));
        //path = ImageFilePath.getPath(this, uri); // should the path be here in this string
       // System.out.print("Path  = " + path);
        Log.i("urimagge", path);


//        }
    }
    private void chooseOrAddTagDialog() {
        final AlertDialog alert;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //alert = builder.create();
        LayoutInflater inflater = this.getLayoutInflater();
        View layout =  inflater.inflate(R.layout.dialog_choose_tag, null);
        alert = builder.setView(layout)
                .setPositiveButton(R.string.create_new_tag, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        createNewTagDialog();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // LoginDialogFragment.this.getDialog().cancel();
                    }
                }).show();
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
                    tagNew = new Tag();
                    tagNew.setTagName(tw.getText().toString());
                    Tag tag = realm.where(Tag.class).equalTo("tagName", tagNew.getTagName()).findFirst();
                    tagNew.setId(tag.getId());
                    tagNew.setColorValue(tag.getColorValue());
                    addTagToView(tagNew);

                    alert.dismiss();
                }
            });
        }
    }

    Tag tagCreated;
    private void createNewTagDialog() {

        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        final View layout =  inflater.inflate(R.layout.dialog_create_new_tag, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final AlertDialog alert = new AlertDialog.Builder(this)
                .setView(layout)
                // Add action buttons
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.cancel, null)
                .create();
        final EditText ed = (EditText)layout.findViewById(R.id.tagNameInserted);
        ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        alert.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                Log.i("dfgh", "iop");
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                Log.i("dfgh", "fds");
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        Log.i("dfgh", ed.getText().toString());
                        if(TextUtils.isEmpty(ed.getText().toString().trim())){
                            Toast.makeText(SingleNoteActivity.this, "Tag name is empty", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                            tagCreated = new Tag();
                            tagCreated.setTagName(ed.getText().toString());

                            //Dismiss once everything is OK.

                            alert.dismiss();
                            addColorToTag();
                        }
                    }
                });
            }
        });
        alert.show();

    }

    private void addColorToTag() {
// Get the layout inflater

        LayoutInflater inflater = this.getLayoutInflater();
        final View layout =  inflater.inflate(R.layout.dialog_tag_color_picker, null);
        Drawable mDrawable1 = ContextCompat.getDrawable(this, R.drawable.ic_lens_tag_36dp).mutate();
        Drawable mDrawable2 = ContextCompat.getDrawable(this, R.drawable.ic_lens_tag_36dp).mutate();
        Drawable mDrawable3 = ContextCompat.getDrawable(this, R.drawable.ic_lens_tag_36dp).mutate();
        Drawable mDrawable4 = ContextCompat.getDrawable(this, R.drawable.ic_lens_tag_36dp).mutate();
        Drawable mDrawable5 = ContextCompat.getDrawable(this, R.drawable.ic_lens_tag_36dp).mutate();
        Drawable mDrawable6 = ContextCompat.getDrawable(this, R.drawable.ic_lens_tag_36dp).mutate();

        ImageView CPBlack = (ImageView) layout.findViewById(R.id.tag_color_picker_green);
        mDrawable1.setColorFilter(new PorterDuffColorFilter(Color.parseColor(ConstantType.TAG_COLOR_LIGHT_GREEN), PorterDuff.Mode.SRC_IN));
        CPBlack.setImageDrawable(mDrawable1);
        ImageView CPGray = (ImageView) layout.findViewById(R.id.tag_color_picker_yellow);
        mDrawable2.setColorFilter(new PorterDuffColorFilter(Color.parseColor(ConstantType.TAG_COLOR_YELLOW), PorterDuff.Mode.SRC_IN));
        CPGray.setImageDrawable(mDrawable2);
        ImageView CPGreen = (ImageView) layout.findViewById(R.id.tag_color_picker_blue);
        mDrawable3.setColorFilter(new PorterDuffColorFilter(Color.parseColor(ConstantType.TAG_COLOR_BLUE), PorterDuff.Mode.SRC_IN));
        CPGreen.setImageDrawable(mDrawable3);
        ImageView CPBlue = (ImageView) layout.findViewById(R.id.tag_color_picker_red);
        mDrawable4.setColorFilter(new PorterDuffColorFilter(Color.parseColor(ConstantType.TAG_COLOR_RED), PorterDuff.Mode.SRC_IN));
        CPBlue.setImageDrawable(mDrawable4);
        ImageView CPRed = (ImageView) layout.findViewById(R.id.tag_color_picker_pink);
        mDrawable5.setColorFilter(new PorterDuffColorFilter(Color.parseColor(ConstantType.TAG_COLOR_PINK), PorterDuff.Mode.SRC_IN));
        CPRed.setImageDrawable(mDrawable5);
        ImageView CPPink = (ImageView) layout.findViewById(R.id.tag_color_picker_orange);
        mDrawable6.setColorFilter(new PorterDuffColorFilter(Color.parseColor(ConstantType.TAG_COLOR_ORANGE), PorterDuff.Mode.SRC_IN));
        CPPink.setImageDrawable(mDrawable6);

        //mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(thisNote.getTag().getColorValue()),

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final AlertDialog alert = new AlertDialog.Builder(this)
                .setView(layout)
                // Add action buttons
                .setPositiveButton(R.string.done, null)
                .setNegativeButton(R.string.cancel, null)
                .create();

        alert.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                Log.i("dfgh", "iop");
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                Log.i("dfgh", "fds");
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {


                        if(tagCreated.getColorValue()==null){
                            Toast.makeText(SingleNoteActivity.this, "Choose color", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            createNewTag();

                            //Dismiss once everything is OK.
                            alert.dismiss();
                        }
                    }
                });
            }
        });
        alert.show();

    }

    private void createNewTag() {
        tagNew = new Tag();
        tagNew.setId(-1);
        tagNew.setTagName(tagCreated.getTagName());
        tagNew.setColorValue(tagCreated.getColorValue());
        addTagToView(tagNew);
        tagCreated = null;
    }

    private void addTagToView(Tag tagNew) {
        tagOfNote.setText(tagNew.getTagName());
        //tagOfNote.setBackgroundColor(Color.parseColor(thisNote.getTag().getColorValue()));
        tagOfNote.setVisibility(View.VISIBLE);
        Drawable mDrawable = ContextCompat.getDrawable(this, R.drawable.ic_tag_blue_24dp).mutate();
        mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(tagNew.getColorValue()),
                PorterDuff.Mode.SRC_IN));
        tagOfNote.setCompoundDrawablesWithIntrinsicBounds(mDrawable, null, null, null);
    }



    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Save this note?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveData();
                        dialog.dismiss();
                        SingleNoteActivity.super.onBackPressed();
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO return to previous note content
                dialog.dismiss();
                SingleNoteActivity.super.onBackPressed();
            }
        }).show();
        return;
    }

    private void saveData() {
        //text and title empty
        if (TextUtils.isEmpty(titleOfNote.getText().toString().trim())&&TextUtils.isEmpty(textOfNote.getText().toString().trim())) {
            return;
        }
        else {
            realm.executeTransactionAsync(new Realm.Transaction() {
                          @Override
                          public void execute(Realm realm) {
                              if (createdNote) {
                                  // increment index
                                  Number currentIdNum = realm.where(Note.class).max("id");
                                  int nextId;
                                  if (currentIdNum == null) {
                                      nextId = 1;
                                  } else {
                                      nextId = currentIdNum.intValue() + 1;
                                  }

                                  Note note = realm.createObject(Note.class, nextId);
                                  // note.setId(nextId);
                                  note.setText(textOfNote.getText().toString());
                                  if (TextUtils.isEmpty(titleOfNote.getText().toString().trim())) {
                                      note.setTitle(textOfNote.getText().toString().substring(0, 19));
                                  } else {
                                      note.setTitle(titleOfNote.getText().toString());
                                  }

                                  note.setTimeStamp(thisNote.getTimeStamp());

                                  if (tagNew != null) {
                                      currentIdNum = realm.where(Tag.class).max("id");

                                      if (currentIdNum == null) {
                                          nextId = 1;
                                      } else {
                                          nextId = currentIdNum.intValue() + 1;
                                      }
                                      Tag tag = realm.createObject(Tag.class, nextId);
                                      //tag.setId(nextId);
                                      tag.setTagName(tagNew.getTagName());
                                      tag.setColorValue(tagNew.getColorValue());//TODO save bg tag color
                                      // realm.insertOrUpdate(tag);
                                      note.setTag(tag);
                                  }

                                  note.setTextColor(textColor);
                                  note.setFontSize(fontSize);

                                  note.setPicture(thisNote.isPicture());

                                  //add media
                                  // increment index
                                  if (mediaNew.size() != 0)
                                      for (int i = 0; i < mediaNew.size(); ++i) {
                                          currentIdNum = realm.where(Media.class).max("id");
                                          if (currentIdNum == null) {
                                              nextId = 1;
                                          } else {
                                              nextId = currentIdNum.intValue() + 1;
                                          }

                                          Media media = realm.createObject(Media.class, nextId);
                                          media.setFileType(mediaNew.get(i).getFileType());
                                          media.setFilePath(mediaNew.get(i).getFilePath());
                                          note.getMedia().add(media);
                                      }
                              } else {
                                  thisNote.setText(textOfNote.getText().toString());
                                  if (TextUtils.isEmpty(titleOfNote.getText().toString().trim())) {
                                      thisNote.setTitle(textOfNote.getText().toString().substring(0, 19));
                                  } else {
                                      thisNote.setTitle(titleOfNote.getText().toString());
                                  }


                                  if (tagNew != null) {
                                      if (tagNew.getId() == -1) {
                                          Number currentIdNum = realm.where(Tag.class).max("id");
                                          Integer nextId;
                                          if (currentIdNum == null) {
                                              nextId = 1;
                                          } else {
                                              nextId = currentIdNum.intValue() + 1;
                                          }
                                          Tag tag = realm.createObject(Tag.class, nextId);
                                          tag.setTagName(tagNew.getTagName());
                                          tag.setColorValue(tagNew.getColorValue());
                                          // realm.insertOrUpdate(tag);
                                          thisNote.setTag(tag);
                                      } else {
                                          thisNote.setTag(tagNew);
                                          //tag.setId(nextId);
                                      }
                                  }


                                  thisNote.setTextColor(textColor);
                                  thisNote.setFontSize(fontSize);

                                  thisNote.setPicture(thisNote.isPicture());

                                  //add media
                                  // increment index

                                  thisNote.setMedia();
                                  if (mediaNew.size() != 0)
                                      for (int i = 0; i < mediaNew.size(); ++i) {
                                          Number currentIdNum;
                                          Integer nextId;
                                          currentIdNum = realm.where(Media.class).max("id");
                                          if (currentIdNum == null) {
                                              nextId = 1;
                                          } else {
                                              nextId = currentIdNum.intValue() + 1;
                                          }

                                          Media media = realm.createObject(Media.class, nextId);
                                          media.setFileType(mediaNew.get(i).getFileType());
                                          media.setFilePath(mediaNew.get(i).getFilePath());
                                          thisNote.getMedia().add(media);
                                      }
                              }


                          }

                      },
                    new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            // Transaction was a success.
                            Log.d("realm", "save succes");
                            Toast.makeText(SingleNoteActivity.this, "save", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            // Transaction failed and was automatically canceled.
                            Log.d("realm", error.getMessage());
                           // Toast.makeText(getParent(), "fauil", Toast.LENGTH_LONG).show();
                        }

                    });
        }
    }

    /*private boolean notEmptyTextField(EditText editText){
        if(!TextUtils.isEmpty(editText.getText())&&editText.getText().toString().trim().length() > 0){
            return true;
        }
        else return false;
    }*/
    @Override

    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void colorClicked(View view) {
        switch(view.getId()){
            case R.id.color_picker_black:
                changeTextColor(ConstantType.TEXT_COLOR_BLACK);
                break;
            case R.id.color_picker_gray:
                changeTextColor(ConstantType.TEXT_COLOR_GRAY);
                break;
            case R.id.color_picker_green:
                changeTextColor(ConstantType.TEXT_COLOR_GREEN);
                break;
            case R.id.color_picker_blue:
                changeTextColor(ConstantType.TEXT_COLOR_BLUE);
                break;
            case R.id.color_picker_red:
                changeTextColor(ConstantType.TEXT_COLOR_RED);
                break;
            case R.id.color_picker_pink:
                changeTextColor(ConstantType.TEXT_COLOR_PINK);
                break;
        }
    }
    public void changeTextColor(String color){
        setTextColor(color);
        textColor = color;
        setColorOfTextColorView(textColor);
        popupWindow.dismiss();
    }

    public void setTextColor(String textColor) {
        titleOfNote.setTextColor(Color.parseColor(textColor));
        textOfNote.setTextColor(Color.parseColor(textColor));
    }

    public void setColorOfTextColorView(String colorOfTextColorView) {
        Drawable mDrawable = ContextCompat.getDrawable(this, R.drawable.ic_lens_black_24dp).mutate();
        mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(colorOfTextColorView), PorterDuff.Mode.SRC_IN));
        textColorView.setImageDrawable(mDrawable);
    }

    public void tagColorClicked(View view) {
        view.setBackgroundColor(Color.parseColor(ConstantType.TAG_COLOR_DEFAULT));
        switch(view.getId()){
            case R.id.tag_color_picker_green:
                choosenTagColor(ConstantType.TAG_COLOR_LIGHT_GREEN);
                break;
            case R.id.tag_color_picker_yellow:
                choosenTagColor(ConstantType.TAG_COLOR_YELLOW);
                break;
            case R.id.tag_color_picker_blue:
                choosenTagColor(ConstantType.TAG_COLOR_BLUE);
                break;
            case R.id.tag_color_picker_red:
                choosenTagColor(ConstantType.TAG_COLOR_RED);
                break;
            case R.id.tag_color_picker_pink:
                choosenTagColor(ConstantType.TAG_COLOR_PINK);
                break;
            case R.id.tag_color_picker_orange:
                choosenTagColor(ConstantType.TAG_COLOR_ORANGE);
                break;
        }
    }

    private void choosenTagColor(String tagColor) {
        tagCreated.setColorValue(tagColor);
    }

    public void tagDeletion(View view) {
        TextView tw = (TextView) view;
        tw.setVisibility(View.INVISIBLE);
        tagNew = null;
    }
}
