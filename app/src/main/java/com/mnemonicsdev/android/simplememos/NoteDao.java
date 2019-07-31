package com.mnemonicsdev.android.simplememos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mnemonicsdev.android.simplememos.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    List<Note> getAllNotes();

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Query("DELETE FROM note WHERE note.id == :id")
    void delete(long id);

    @Delete
    void delete(Note note);

}
