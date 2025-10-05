package com.example.test2.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface SessionDao {
    @Insert long insert(SessionEntity s);

    @Query("SELECT * FROM sessions ORDER BY id DESC")
    List<SessionEntity> listAll();

    @Query("DELETE FROM sessions")
    void deleteAll();
}