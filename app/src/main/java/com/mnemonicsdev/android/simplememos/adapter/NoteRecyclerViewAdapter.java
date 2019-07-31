package com.mnemonicsdev.android.simplememos.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mnemonicsdev.android.simplememos.NoteStorage;
import com.mnemonicsdev.android.simplememos.R;
import com.mnemonicsdev.android.simplememos.activities.MainActivity;
import com.mnemonicsdev.android.simplememos.activities.ManageNoteActivity;
import com.mnemonicsdev.android.simplememos.model.Note;

import java.util.ArrayList;
import java.util.List;

import static com.mnemonicsdev.android.simplememos.R.id.note_title;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.NoteViewHolder> {


    private Context ownerActivityContext;
    private List<Note> notes;

    public List<Note> getNotes() {
        return notes;
    }

    public NoteRecyclerViewAdapter(Context context) {

        ownerActivityContext = context;
        notes = NoteStorage.getInstance().getNotes();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int holderType) {
        return new NoteViewHolder(LayoutInflater.from(viewGroup.getContext()), viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int position) {
        noteViewHolder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView noteTitle;

        public NoteViewHolder(LayoutInflater from, ViewGroup viewGroup) {
            super(from.inflate(R.layout.list_item_view, viewGroup, false));
            init();
        }

        private void init() {
            itemView.setOnClickListener(this);
            noteTitle = itemView.findViewById(note_title);
        }

        public void bind(Note note){
            noteTitle.setText(note.getTitle());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(itemView.getContext(), ManageNoteActivity.class);

            intent.putExtra(ManageNoteActivity.DATA_KEY, notes.get(getAdapterPosition()));
            intent.putExtra(ManageNoteActivity.NOTE_POSITION_KEY, getAdapterPosition());

            ((Activity)ownerActivityContext).startActivityForResult(intent, ManageNoteActivity.EDIT_NOTE_REQUEST);
        }
    }
}
