package com.mnemonicsdev.android.simplememos.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.provider.ContactsContract;

import com.mnemonicsdev.android.simplememos.App;
import com.mnemonicsdev.android.simplememos.NoteDao;
import com.mnemonicsdev.android.simplememos.NoteDatabase;
import com.mnemonicsdev.android.simplememos.NoteStorage;
import com.mnemonicsdev.android.simplememos.model.Note;

import java.util.List;

public class NoteViewModel extends ViewModel {

    NoteStorage noteStorage = NoteStorage.getInstance();
    NoteDatabase db = App.getInstance().getDatabase();

    public NoteViewModel() {
    }


   public List<Note> getNotes(){
        return noteStorage.getNotes();
    }


    public void SaveNoteInDatabase(Note note){
        db.noteDao().insert(note);
    }

    public void UpdateNoteInDatabase(Note note){
        db.noteDao().update(note);
    }

    public void RemoveNoteFromDatabase(Note note){
        db.noteDao().delete(note);
    }
    public void RemoveNoteFromDatabaseById(long id){
        db.noteDao().delete(id);
    }

}
