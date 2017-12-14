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
 *
 * This class will manage and display the data from RecyclerView
 *
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    // Define context used to inflate the layout
    private Context mCtx;

    // We store all the events in a list
    private List<Event> eventList;

    // Get the context and event list with the constructor
    public EventsAdapter(Context mCtx, List<Event> eventList){
        this.mCtx = mCtx;
        this.eventList = eventList;
    }

    // Override the method that will return a new instance of the ViewHolder
    @Override
    public EventsAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate and return teh ViewHolder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.event_list, null);
        return new EventViewHolder(view);
    }

    // Override the method that will bind the data to the view holder
    @Override
    public void onBindViewHolder(EventsAdapter.EventViewHolder holder, int position) {

        // Get the event of the specified position
        Event event = eventList.get(position);

        // Bind the data with the view holder views
        holder.tvEventListTitle.setText(event.getTitle());
        holder.tvEventListDescription.setText(event.getDescription());
        holder.tvEventListGym.setText(event.getGym());
        holder.tvEventListDate.setText(event.getDate());
        holder.tvEventListTime.setText(event.getTime());
        holder.tvEventListCreator.setText(event.getCreator());
    }

    // Override the method that will return the size of the list
    @Override
    public int getItemCount() {
        return eventList.size();
    }

    // ???
    class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvEventListTitle, tvEventListDescription, tvEventListDate, tvEventListTime,
                tvEventListGym, tvEventListCreator;;

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

        // Override onClick method that is called when a view has been clicked
        @Override
        public void onClick(View view) {
            if(view.getId() == itemView.getId()){

                // Create an intent to pass data from EventsAdapter to Show Event Activity
                Intent intent = new Intent(mCtx, ShowEventActivity.class);
                // Save the data in the intent
                intent.putExtra("clickedTitle", tvEventListTitle.getText().toString());
                intent.putExtra("clickedDescription", tvEventListDescription.getText().toString());
                intent.putExtra("clickedGym", tvEventListGym.getText().toString());
                intent.putExtra("clickedDate", tvEventListDate.getText().toString());
                intent.putExtra("clickedTime", tvEventListTime.getText().toString());
                intent.putExtra("clickedCreator", tvEventListCreator.getText().toString());
                // Start the show event activity
                mCtx.startActivity(intent);
            }
        }
    }
}
