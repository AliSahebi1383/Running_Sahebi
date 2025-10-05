package com.example.test2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.test2.db.SessionEntity;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.VH> {
    private final List<SessionEntity> data;
    public HistoryAdapter(List<SessionEntity> d){ data = d; }

    static class VH extends RecyclerView.ViewHolder {
        TextView title, activity, meta, duration, kcal;
        VH(@NonNull View v){
            super(v);
            title = v.findViewById(R.id.title);
            activity = v.findViewById(R.id.activity);
            meta = v.findViewById(R.id.meta);
            duration = v.findViewById(R.id.duration);
            kcal = v.findViewById(R.id.kcal);
        }
    }

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int t) {
        View v = LayoutInflater.from(p.getContext()).inflate(R.layout.row_history, p, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int i) {
        SessionEntity s = data.get(i);
        h.title.setText(s.title);
        h.activity.setText(s.activityName);
        h.meta.setText(s.date + " • " + s.startTime);
        h.duration.setText((s.durationSec/60) + "m " + (s.durationSec%60) + "s");
        h.kcal.setText(String.format(java.util.Locale.US, "%.1f kcal", s.calories));
    }

    @Override public int getItemCount() { return data.size(); }
}
