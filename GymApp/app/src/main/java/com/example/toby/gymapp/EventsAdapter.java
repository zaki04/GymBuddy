package com.example.toby.gymapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by aleks on 12.12.2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    private Context mCtx;
    private List<Event> eventList;

    public EventsAdapter(Context mCtx, List<Event> eventList){
        this.mCtx = mCtx;
        this.eventList = eventList;
    }


    @Override
    public EventsAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.event_list, null);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventsAdapter.EventViewHolder holder, int position) {

        Event event = eventList.get(position);

        //String place = event.getGym() + " " + event.getDate();

        holder.tvEventListTitle.setText(event.getTitle());
        holder.tvEventListDescription.setText(event.getDescription());
        holder.tvEventListGym.setText(event.getGym());
        holder.tvEventListDate.setText(event.getDate());
        holder.tvEventListTime.setText(event.getTime());
        holder.tvEventListCreator.setText(event.getCreator());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvEventListTitle, tvEventListDescription, tvEventListDate, tvEventListTime, tvEventListGym, tvEventListCreator;;

        public EventViewHolder(View itemView) {
            super(itemView);

            tvEventListTitle = itemView.findViewById(R.id.tvEventListTitle);
            tvEventListDescription = itemView.findViewById(R.id.tvEventListDescription);
            tvEventListDate = itemView.findViewById(R.id.tvEventListDate);
            tvEventListTime = itemView.findViewById(R.id.tvEventListTime);
            tvEventListGym = itemView.findViewById(R.id.tvEventListGym);
            tvEventListCreator = itemView.findViewById(R.id.tvEventListCreator);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == itemView.getId()){
                //Toast.makeText(view.getContext(), "You clicked the event", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mCtx, ShowEventActivity.class);
                intent.putExtra("clickedTitle", tvEventListTitle.getText().toString());
                intent.putExtra("clickedDescription", tvEventListDescription.getText().toString());
                intent.putExtra("clickedGym", tvEventListGym.getText().toString());
                intent.putExtra("clickedDate", tvEventListDate.getText().toString());
                intent.putExtra("clickedTime", tvEventListTime.getText().toString());
                intent.putExtra("clickedCreator", tvEventListCreator.getText().toString());
                mCtx.startActivity(intent);
            }
        }
    }
}
