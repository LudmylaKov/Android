package com.project.me.notes;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.me.notes.model.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sazumi on 25.07.2017.
 */

public class NotesCardAdapter extends RecyclerView.Adapter<NotesCardAdapter.MyViewHolder> {
    private Context mContext;
    private List<Note> notes;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public VerticalTextView tag;
        public TextView title, text, date;
        public ImageView video, audio, notification;

        public MyViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            text = (TextView) view.findViewById(R.id.text);
            date = (TextView) view.findViewById(R.id.date);
            tag = (VerticalTextView) view.findViewById(R.id.tag);
            video = (ImageView) view.findViewById(R.id.video);
            audio = (ImageView) view.findViewById(R.id.audio);
            notification = (ImageView) view.findViewById(R.id.notification);
        }
    }

    public NotesCardAdapter(Context mContext, List<Note> notes){
        this.mContext = mContext;
        this.notes = notes;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_grid_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position){
        Note note = notes.get(position);

       // ColorDrawable color = new ColorDrawable(note.getTag().getColorValue());
        holder.tag.setText(note.getTag().getTagName());
        holder.tag.setBackgroundResource(note.getTag().getColorValue());

        holder.title.setText(note.getTitle());
        holder.text.setText(note.getText());


        String dateString = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(note.getTimeStamp()));
        holder.date.setText(dateString);

        if(note.getNotification()!=null && note.getNotification().isNotification()){
            holder.notification.setVisibility(View.VISIBLE);
        }
        if(note.isVideo()){
            holder.video.setVisibility(View.VISIBLE);
        }

        if(note.isAudio()){
            holder.audio.setVisibility(View.VISIBLE);
        }

        if(note.isAudio()&&!note.isVideo()){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            holder.audio.setLayoutParams(params);
        }


        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO open this note
            }
        });
    }
    @Override
    public int getItemCount() {
        return notes.size();
    }
}
