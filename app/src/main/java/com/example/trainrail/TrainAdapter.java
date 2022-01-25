package com.example.trainrail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.ArtistViewHolder> {

    private Context mCtx;
    private List<Train> trainList;
    private ItemClickListener clickListener;

    public TrainAdapter(Context mCtx, List<Train> trainList) {
        this.mCtx = mCtx;
        this.trainList = trainList;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_trains, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Train train = trainList.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy, hh:mm aaa");

        try {
            Date date = dateFormat.parse(train.date);
            holder.textViewDate.setText("Journey Date : " + dateFormat.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.textViewTrainName.setText(train.travelsName);
        holder.textViewTrainNumber.setText("Train Number : " + train.trainNumber);

        holder.textViewFrom.setText("From : " + train.from);
        holder.textViewTo.setText("To : " + train.to);
        holder.textViewCondition.setText("Train Condition: " + train.trainCondition);
    }

    @Override
    public int getItemCount() {
        return trainList.size();
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewTrainName, textViewTrainNumber, textViewDate, textViewFrom,textViewTo,textViewCondition;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTrainName = itemView.findViewById(R.id.text_view_trainName);
            textViewTrainNumber = itemView.findViewById(R.id.text_view_trainNumber);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewFrom = itemView.findViewById(R.id.text_view_from);
            textViewTo = itemView.findViewById(R.id.text_view_to);
            textViewCondition = itemView.findViewById(R.id.text_view_condition);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}