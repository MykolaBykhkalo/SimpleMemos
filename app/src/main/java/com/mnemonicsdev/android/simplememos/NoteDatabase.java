package com.mnemonicsdev.android.simplememos;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mnemonicsdev.android.simplememos.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
