package com.example.test2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.test2.db.AppDatabase;
import com.example.test2.db.SessionEntity;
import java.util.List;
import java.util.concurrent.Executors;

public class HistoryActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        RecyclerView r = findViewById(R.id.recycler);
        r.setLayoutManager(new LinearLayoutManager(this));

        Executors.newSingleThreadExecutor().execute(() -> {
            List<SessionEntity> all = AppDatabase.get(this).sessionDao().listAll();
            runOnUiThread(() -> r.setAdapter(new HistoryAdapter(all)));
        });
    }
}
