package com.mnemonicsdev.android.simplememos;

import com.mnemonicsdev.android.simplememos.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteStorage {
    private static NoteStorage instance;

    private List<Note>notes;

    public static NoteStorage getInstance()
    {
        if (instance == null) instance = new NoteStorage();
        return instance;
    }

    private NoteStorage() {
        notes = new ArrayList<>();
    }

    public static void setInstance(NoteStorage instance) {
        NoteStorage.instance = instance;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
