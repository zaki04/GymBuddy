package com.example.toby.gymapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        String place = event.getGym() + " " + event.getDate();

        holder.tvEventListTitle.setText(event.getTitle());
        holder.tvEventListDescription.setText(event.getDescription());
        holder.tvEventListPlace.setText(place);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder{
        TextView tvEventListTitle, tvEventListDescription, tvEventListPlace;

        public EventViewHolder(View itemView) {
            super(itemView);

            tvEventListTitle = itemView.findViewById(R.id.tvEventListTitle);
            tvEventListDescription = itemView.findViewById(R.id.tvEventListDescription);
            tvEventListPlace = itemView.findViewById(R.id.tvEventListPlace);
        }
    }
}
