package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.dao.TechnologyDao;
import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.model.Technology;

@Database(entities = {Technology.class}, version = 1)
public abstract class TechnologiesDatabase extends RoomDatabase {

    public abstract TechnologyDao technologyDao();

    private static TechnologiesDatabase instance;

    public static TechnologiesDatabase getInstance(final Context context) {
        if (instance == null) {
           synchronized (TechnologiesDatabase.class) {
               if (instance == null) {
                   RoomDatabase.Builder builder = Room.databaseBuilder(context,
                           TechnologiesDatabase.class, "technologies.db");

                   instance = (TechnologiesDatabase) builder.build();
               }
           }
        }

        return instance;
    }
}
