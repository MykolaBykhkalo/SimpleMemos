package com.mnemonicsdev.android.simplememos.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.mnemonicsdev.android.simplememos.App;
import com.mnemonicsdev.android.simplememos.model.Note;
import com.mnemonicsdev.android.simplememos.adapter.NoteRecyclerViewAdapter;
import com.mnemonicsdev.android.simplememos.R;
import com.mnemonicsdev.android.simplememos.viewmodel.NoteViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteRecyclerViewAdapter adapter;
    private NoteViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        if (adapter.getNotes() != null) {
            List<Note>assess = App.getInstance().getDatabase().noteDao().getAllNotes();
            Toast.makeText(MainActivity.this, "onC db size: " + assess.size(), Toast.LENGTH_SHORT).show();

            adapter.notifyDataSetChanged();
        }
    }

    private void init() {
        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);


    }

    public void AddNoteOnClick(View view) {

        startActivityForResult(new Intent(this, ManageNoteActivity.class), ManageNoteActivity.ADD_NEW_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ManageNoteActivity.ADD_NEW_NOTE_REQUEST:
                if (resultCode == ManageNoteActivity.RESULT_NOTE_CREATED) {
                    Note receivedNote = (Note) data.getExtras().getSerializable(ManageNoteActivity.DATA_KEY);
                    if (adapter.getNotes().add(receivedNote)) {
                        adapter.notifyItemInserted(adapter.getNotes().size() - 1);

                        viewModel.SaveNoteInDatabase(receivedNote);
                    }

                } else if (resultCode == ManageNoteActivity.RESULT_CREATING_DENIED) {
                    Toast.makeText(this, "creating Denied", Toast.LENGTH_LONG).show();
                }
                break;

            case ManageNoteActivity.EDIT_NOTE_REQUEST:
                if (resultCode == ManageNoteActivity.RESULT_NOTE_EDITED) {
                    Note receivedNote = (Note) data.getExtras().getSerializable(ManageNoteActivity.DATA_KEY);
                    int receivedNotePosition = data.getIntExtra(ManageNoteActivity.NOTE_POSITION_KEY, -1);
                    adapter.getNotes().set(receivedNotePosition, receivedNote);
                    adapter.notifyItemChanged(receivedNotePosition);


                    viewModel.UpdateNoteInDatabase(receivedNote);
                } else if (resultCode == ManageNoteActivity.RESULT_EDITING_DENIED) {
                    Toast.makeText(this, "editing Denied", Toast.LENGTH_LONG).show();
                } else if (resultCode == ManageNoteActivity.RESULT_NOTE_REMOVED) {

                    List<Note>asses = App.getInstance().getDatabase().noteDao().getAllNotes();
                    Toast.makeText(MainActivity.this, "BfRm db size: " + asses.size(), Toast.LENGTH_SHORT).show();

                    final Note receivedNote = (Note) data.getExtras().getSerializable(ManageNoteActivity.DATA_KEY);
                    final int receivedNotePosition = data.getIntExtra(ManageNoteActivity.NOTE_POSITION_KEY, -1);
                    adapter.getNotes().remove(receivedNotePosition); // Видалення з ліста
                    adapter.notifyItemRemoved(receivedNotePosition);
                    viewModel.RemoveNoteFromDatabaseById(receivedNote.getId()); //Видалення з БД

                    asses = App.getInstance().getDatabase().noteDao().getAllNotes();
                    Toast.makeText(MainActivity.this, "onRm db size: " + asses.size(), Toast.LENGTH_SHORT).show();


                    Snackbar.make(findViewById(R.id.main_activity_root), "Note removed", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    viewModel.SaveNoteInDatabase(receivedNote);
                                    adapter.getNotes().add(receivedNotePosition, receivedNote);
                                    //adapter.notifyItemInserted(receivedNotePosition);
                                    adapter.notifyDataSetChanged();
                                    List<Note>asses = App.getInstance().getDatabase().noteDao().getAllNotes();
                                    Toast.makeText(MainActivity.this, "AfUd db size: " + asses.size(), Toast.LENGTH_SHORT).show();



                                }
                            })
                            .show();

                }
        }
    }


}

