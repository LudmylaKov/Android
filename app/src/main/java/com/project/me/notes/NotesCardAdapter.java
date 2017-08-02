package com.project.me.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.me.notes.model.Note;

import java.util.List;

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
        holder.tag.setText(note.getTags().getTagName());
        holder.title.setText(note.getTitle());
        holder.text.setText(note.getText());
        holder.date.setText(note.getTimestamp().toString());
        if(note.getNotification()!=null){
            holder.notification.setVisibility(View.VISIBLE);
        }
        //TODO showing video, audio, notification icons

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
