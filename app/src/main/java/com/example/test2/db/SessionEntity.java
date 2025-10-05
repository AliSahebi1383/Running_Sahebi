package com.example.test2.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sessions")
public class SessionEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String title;
    public String activityName;
    public String date;        // yyyy-MM-dd
    public String startTime;   // HH:mm:ss
    public long durationSec;   // ثانیه
    public double calories;    // کیلوکالری
}
