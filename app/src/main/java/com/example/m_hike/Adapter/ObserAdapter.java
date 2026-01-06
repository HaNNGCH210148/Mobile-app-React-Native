package com.example.m_hike.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hike.Model.ObserList;
import com.example.m_hike.R;

import java.util.List;

public class ObserAdapter extends RecyclerView.Adapter<ObserAdapter.ObservationViewHolder> {

    private List<ObserList> observationList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(ObserList observation);
        void onDeleteClick(ObserList observation);
    }

    public ObserAdapter(List<ObserList> observationList, OnItemClickListener listener) {
        this.observationList = observationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ObservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_observation, parent, false);
        return new ObservationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationViewHolder holder, int position) {
        ObserList observation = observationList.get(position);

        holder.txtObservation.setText("Observation "+ (position + 1));
        holder.txtDay.setText("Day: " + observation.getDay());
        holder.txtTime.setText("Time: " + observation.getTime());
        holder.txtComment.setText(
                "Comments: " + (observation.getComment().isEmpty() ? "No comments" : observation.getComment())
        );

        holder.btnEditObser.setOnClickListener(v -> listener.onEditClick(observation));
        holder.btnDeleteObser.setOnClickListener(v -> listener.onDeleteClick(observation));


    }

    @Override
    public int getItemCount() {
        return observationList != null ? observationList.size() : 0;
    }

    public void setObservations(List<ObserList> list) {
        this.observationList = list;
        notifyDataSetChanged();
    }

    // ViewHolder
    static class ObservationViewHolder extends RecyclerView.ViewHolder {
        TextView txtObservation, txtDay, txtTime, txtComment;
        Button btnDeleteObser, btnEditObser;

        public ObservationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtObservation = itemView.findViewById(R.id.txtObservation);
            txtDay = itemView.findViewById(R.id.txtDay);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtComment = itemView.findViewById(R.id.txtComment);
            btnDeleteObser = itemView.findViewById(R.id.btnDeleteObser);
            btnEditObser = itemView.findViewById(R.id.btnEditObser);
        }
    }
}
