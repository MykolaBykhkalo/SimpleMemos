package com.mnemonicsdev.android.simplememos;

import android.app.Application;
import android.arch.persistence.room.Room;

public class App extends Application {

    public static App instance;

    private NoteDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, NoteDatabase.class, "database")
                .allowMainThreadQueries()
                .build();

        NoteStorage.getInstance().getNotes().addAll(database.noteDao().getAllNotes());
        //Хз чи може бути нулл, вірогідно є вшита обробка, що повертає пустий ліст замість нулла
    }

    public static App getInstance() {
        return instance;
    }

    public NoteDatabase getDatabase() {
        return database;
    }

}
