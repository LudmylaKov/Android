package com.project.me.notes;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.me.notes.model.ConstantType;
import com.project.me.notes.model.Media;
import com.project.me.notes.model.Note;
import com.project.me.notes.model.Tag;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class SingleNoteActivity extends AppCompatActivity {
    private static final int ACTIVITY_CHOOSE_FILE = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2;

    private TextView currentDate;
    private EditText titleOfNote;
    private EditText textOfNote;
    private TextView notification;
    private TextView tagOfNote;
    private ImageView attachmentImage;

    private Note thisNote;
    Realm realm;
    Integer id = -1;
    Integer fontSize, textColor;
    Tag tagNew;
    ArrayList<Media> mediaNew;

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
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("ID");
            thisNote = realm.where(Note.class).equalTo("id", id).findFirst();
            //TODO get note by id from realm
            initialFieldsById();
        }
        else{
            thisNote = new Note();
            thisNote.setFontSize(ConstantType.FONT_SIZE_MEDIUM);
            fontSize = thisNote.getFontSize();
            mediaNew = new ArrayList<Media>();
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
        ImageView textColor = (ImageView) findViewById(R.id.textColor);
        textColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*LayoutInflater inflater = SingleNoteActivity.this.getLayoutInflater();
                new AlertDialog.Builder(SingleNoteActivity.this)
                        .setTitle("Choose text color")
                        .setView(inflater.inflate(R.layout.dialog_color_picker, null))
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //dialog.dismiss();

                            }
                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //dialog.dismiss();

                    }
                }).show();*/
            }
        });
        /*textColor.setColorFilter(new
                        PorterDuffColorFilter(Color.parseColor(ConstantType.TAG_COLOR1),
                PorterDuff.Mode.MULTIPLY));*/



    }



    private void initialFieldsById() {
        setTextSize(thisNote.getFontSize());

        fontSize = thisNote.getFontSize();

        String dateString = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(new Date(thisNote.getTimeStamp()));
        currentDate.setText(dateString);
        titleOfNote.setText(thisNote.getTitle());
        textOfNote.setText(thisNote.getText());


        if(thisNote.getNotification()!=null&&thisNote.getNotification().isNotification()){
            String dateNotificationString = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(new Date(thisNote.getNotification().getDateNotification()));
            notification.setText(dateNotificationString);
            notification.setVisibility(View.VISIBLE);
        }

        if(thisNote.getTag()!=null){
            tagOfNote.setText(thisNote.getTag().getTagName());
            //tagOfNote.setBackgroundColor(Color.parseColor(thisNote.getTag().getColorValue()));
            tagOfNote.setVisibility(View.VISIBLE);
            Drawable mDrawable = ContextCompat.getDrawable(this, R.drawable.ic_tag_blue_24dp).mutate();
            mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(thisNote.getTag().getColorValue()),
                    PorterDuff.Mode.SRC_IN));
            tagOfNote.setCompoundDrawablesWithIntrinsicBounds(mDrawable, null, null, null);
        }

        if(thisNote.isPicture()){
            RealmList<Media> mediaThis = thisNote.getMedia();
            for(int i = 0; i < mediaThis.size();++i){
                if (mediaThis.get(i).getFileType() == ConstantType.TYPE_PICTURE) {

                    attachmentImage.setImageURI(Uri.fromFile(new File(mediaThis.get(i).getFilePath())));
                    Log.i("urimagge",Uri.parse(mediaThis.get(i).getFilePath()).toString());
                }
            }
            //attachmentImage.setImageURI(Uri.parse("content://com.android.providers.downloads.documents/document/6"));

        }

    }
    private void initialFieldsNew() {
        Calendar currentTime = Calendar.getInstance();
        long time3 = currentTime.getTimeInMillis();
        currentDate.setText(DateFormat.format("HH:mm dd/MM/yyyy", currentTime).toString());

        thisNote.setTimeStamp(time3);

        setTextSize(thisNote.getFontSize());
    }

    private void initialViews() {
        currentDate = (TextView) findViewById(R.id.currentDate);
        titleOfNote = (EditText) findViewById(R.id.titleNote);
        textOfNote = (EditText) findViewById(R.id.textNote);
        notification = (TextView) findViewById(R.id.notificationNote);
        tagOfNote = (TextView) findViewById(R.id.tagNote);
        attachmentImage = (ImageView) findViewById(R.id.attachment_image);

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
                Intent chooseFile;
                Intent intent;
                chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
                chooseFile.setType("image/*");
                intent = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
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
                onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent chooseFile;
            Intent intent;
            chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
            chooseFile.setType("image/*");
            intent = Intent.createChooser(chooseFile, "Choose a file");
            startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        String path = "";
//        if (requestCode == ACTIVITY_CHOOSE_FILE) {
        final Uri uri = data.getData();

        path = ImageFilePath.getPath(this, uri); // should the path be here in this string
        System.out.print("Path  = " + path);
        Log.i("urimagge", path);
        attachmentImage.setImageURI(uri);

        Media image = new Media();
        image.setFileType(ConstantType.TYPE_PICTURE);
        image.setFilePath(path);
        thisNote.setPicture(true);
        mediaNew.add(image);

//        }
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
                              if(TextUtils.isEmpty(titleOfNote.getText().toString().trim())){
                                  note.setTitle(textOfNote.getText().toString().substring(0, 19));
                              }
                              else{
                                  note.setTitle(titleOfNote.getText().toString());
                              }

                              note.setTimeStamp(thisNote.getTimeStamp());

                              if(tagNew!=null){
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

                              note.setTextColor(textOfNote.getCurrentTextColor());
                              note.setFontSize(fontSize);
                              note.setPicture(thisNote.isPicture());

                              //add media
                              // increment index
                              if(mediaNew.size()!=0)
                                  for(int i = 0; i < mediaNew.size(); ++i){
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

                              /*RealmList<Media> list = thisNote.getMedia();
                              if (!list.isManaged()) { // if the 'list' is managed, all items in it is also managed
                                  RealmList<Media> managedImageList = new RealmList<>();
                                  for (Media item : list) {
                                      if (item.isManaged()) {
                                          managedImageList.add(item);
                                      } else {
                                          managedImageList.add(realm.copyToRealm(item));
                                      }
                                  }
                                  list = managedImageList;
                              }
                              note.setMedia(list);*/
                              //note.setMedia(thisNote.getMedia());


                              //realm.insertOrUpdate(note);

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

                    }
            );
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
}
