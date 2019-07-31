package com.mnemonicsdev.android.simplememos.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mnemonicsdev.android.simplememos.R;
import com.mnemonicsdev.android.simplememos.model.Note;

public class ManageNoteActivity extends AppCompatActivity {

    public static final int ADD_NEW_NOTE_REQUEST = 128;
    public static final int EDIT_NOTE_REQUEST = 129;
    public static final int RESULT_NOTE_CREATED = 1;
    public static final int RESULT_NOTE_EDITED = 2;
    public static final int RESULT_NOTE_REMOVED = 3;
    public static final int RESULT_CREATING_DENIED = 4;
    public static final int RESULT_EDITING_DENIED = 5;
    public static final String DATA_KEY = "data_key";
    public static final String NOTE_POSITION_KEY = "note position key";

    private EditText noteTitleEdit;
    private EditText noteTextEdit;
    private Button removeNoteButton;

    private Note note;
    private int notePosition = -1;
    private int successResultCode = - 1;
    private int failureResultCode = - 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_note);

        init();

        if (getIntent().getExtras() != null){
            //розрізняємо ексшени.
            //Якщо є Exstras - значить чи подія на редагування
            successResultCode = RESULT_NOTE_EDITED;
            failureResultCode = RESULT_EDITING_DENIED;
            removeNoteButton.setVisibility(View.VISIBLE);

            note = (Note) getIntent().getSerializableExtra(DATA_KEY);
            notePosition = getIntent().getIntExtra(NOTE_POSITION_KEY, -1);

            noteTitleEdit.setText(note.getTitle());
            noteTextEdit.setText(note.getText());
        }else {
            successResultCode = RESULT_NOTE_CREATED;
            failureResultCode = RESULT_CREATING_DENIED;
            removeNoteButton.setVisibility(View.GONE);
            note = new Note();
        }


    }

    private void init() {
        noteTitleEdit = findViewById(R.id.note_title_edit);
        noteTextEdit = findViewById(R.id.note_text_edit);
        removeNoteButton = findViewById(R.id.remove_note_button);
    }


    public void SaveNoteOnClick(View view) {
        Intent resultIntent = new Intent();

        note.setTitle(noteTitleEdit.getText().toString());
        note.setText(noteTextEdit.getText().toString());

        resultIntent.putExtra(DATA_KEY, note);
        resultIntent.putExtra(NOTE_POSITION_KEY, notePosition);
        setResult(successResultCode, resultIntent);
        finish();
    }

    public void RemoveNoteOnClick(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(NOTE_POSITION_KEY, notePosition);
        resultIntent.putExtra(DATA_KEY, note);
        setResult(RESULT_NOTE_REMOVED, resultIntent);
        finish();
    }



    @Override
    public void onBackPressed() {
        setResult(failureResultCode, new Intent());
        finish();
    }


}
