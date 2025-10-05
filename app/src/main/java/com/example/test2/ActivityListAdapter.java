package com.example.test2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test2.model.ActivityItem;

import java.util.List;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.VH> {

    public interface OnClick { void onItem(ActivityItem item); }

    private final List<ActivityItem> data;
    private final OnClick onClick;

    public ActivityListAdapter(List<ActivityItem> data, OnClick onClick) {
        this.data = data;
        this.onClick = onClick;
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView name, kcal;
        VH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            kcal = itemView.findViewById(R.id.kcal);
        }
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_activity, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ActivityItem it = data.get(position);
        holder.name.setText(it.getName());
        holder.kcal.setText(Math.round(it.getKcalPerHour()) + " kcal/h");
        holder.itemView.setOnClickListener(v -> onClick.onItem(it));
    }

    @Override
    public int getItemCount() { return data.size(); }
}
