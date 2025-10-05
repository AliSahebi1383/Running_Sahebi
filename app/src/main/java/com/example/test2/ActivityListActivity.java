package com.example.test2;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.test2.model.ActivityItem;
import java.util.ArrayList;
import java.util.List;


public class ActivityListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView recycler = findViewById(R.id.recycler);
        Button historyBtn = findViewById(R.id.historyBtn);

        List<ActivityItem> items = new ArrayList<>();
        items.add(new ActivityItem("دویدن آرام", 480));
        items.add(new ActivityItem("پیاده‌روی تند", 300));
        items.add(new ActivityItem("دوچرخه ثابت", 420));
        items.add(new ActivityItem("طناب‌زنی", 700));

        ActivityListAdapter adapter = new ActivityListAdapter(items, it -> {
            Intent i = new Intent(this, TimerActivity.class);
            i.putExtra("name", it.getName());
            i.putExtra("kcalPerHour", it.getKcalPerHour());
            startActivity(i);
        });

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        historyBtn.setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, HistoryActivity.class));
        });
    }
}
