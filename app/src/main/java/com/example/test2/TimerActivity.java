package com.example.test2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import com.example.test2.db.AppDatabase;
import com.example.test2.db.SessionEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;

public class TimerActivity extends AppCompatActivity {

    private TextView title, timerView, calorieView;
    private Button startBtn, pauseBtn, stopBtn;

    private String activityName;
    private double kcalPerSec;

    private boolean running = false;
    private long baseUptime = 0L, accumulated = 0L;

    private final Handler ui = new Handler(Looper.getMainLooper());
    private final Runnable tick = new Runnable() {
        @Override public void run() {
            long activeMs = accumulated + (running ? (SystemClock.uptimeMillis() - baseUptime) : 0);
            long sec = activeMs / 1000;
            timerView.setText(format(sec));
            calorieView.setText(String.format(Locale.US, "%.1f kcal", sec * kcalPerSec));
            ui.postDelayed(this, 200);
        }
    };

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // دریافت ورودی از صفحه قبل
        activityName = getIntent().getStringExtra("name");
        double kcalPerHour = getIntent().getDoubleExtra("kcalPerHour", 0.0);
        kcalPerSec = kcalPerHour / 3600.0;

        // findViewById
        title       = findViewById(R.id.title);
        timerView   = findViewById(R.id.timerView);
        calorieView = findViewById(R.id.calorieView);
        startBtn    = findViewById(R.id.startBtn);
        pauseBtn    = findViewById(R.id.pauseBtn);
        stopBtn     = findViewById(R.id.stopBtn);

        if (activityName != null) title.setText(activityName);

        startBtn.setOnClickListener(v -> onStartClicked());
        pauseBtn.setOnClickListener(v -> onPauseOrResume());
        stopBtn.setOnClickListener(v -> onStopClicked());

        ui.post(tick);
    }

    private void onStartClicked() {
        if (!running) {
            running = true;
            baseUptime = SystemClock.uptimeMillis();
        }
    }

    private void onPauseOrResume() {
        if (running) { // Pause
            accumulated += SystemClock.uptimeMillis() - baseUptime;
            running = false;
        } else {       // Resume
            running = true;
            baseUptime = SystemClock.uptimeMillis();
        }
    }

    private void onStopClicked() {
        if (running) {
            accumulated += SystemClock.uptimeMillis() - baseUptime;
            running = false;
        }

        long sec = accumulated / 1000;
        double kcal = sec * kcalPerSec;

        // ذخیره در Room
        SessionEntity s = new SessionEntity();
        s.title = "WSI Session";
        s.activityName = activityName;
        s.date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
        s.startTime = new SimpleDateFormat("HH:mm:ss", Locale.US)
                .format(new Date(System.currentTimeMillis() - accumulated));
        s.durationSec = sec;
        s.calories = kcal;

        Executors.newSingleThreadExecutor().execute(
                () -> AppDatabase.get(this).sessionDao().insert(s)
        );

        finish();
    }

    private static String format(long totalSec) {
        long h = totalSec / 3600, m = (totalSec % 3600) / 60, s = totalSec % 60;
        return (h > 0)
                ? String.format(Locale.US, "%02d:%02d:%02d", h, m, s)
                : String.format(Locale.US, "%02d:%02d", m, s);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        ui.removeCallbacks(tick);
    }
}
