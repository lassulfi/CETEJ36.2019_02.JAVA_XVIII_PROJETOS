package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.model.Technology;

@Dao
public interface TechnologyDao {

    @Insert
    long insert(Technology technology);

    @Delete
    void delete(Technology technology);

    @Update
    void update(Technology technology);

    @Query("SELECT * FROM technologies WHERE id = :id")
    Technology findById(long id);

    @Query("SELECT * FROM technologies ORDER BY name ASC")
    List<Technology> findAll();
}
