package com.example.cwmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCreateTrip = findViewById(R.id.buttonCreateTrip);
        btnCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTrip.class);
                startActivity(intent);
            }
        });

        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);

        List<Trip> trips = dbHelper.getTrips();
        TripListViewAdapter tripListViewAdapter = new TripListViewAdapter(trips);
//        ArrayAdapter<Trip> adapter = new ArrayAdapter<Trip>(this,
//                android.R.layout.simple_list_item_1,trips);
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(tripListViewAdapter);

        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            Trip selectedTrip = trips.get(i);
            Intent intent = new Intent(this, TripDetail.class);

            intent.putExtra("id",selectedTrip.getTripId());
            intent.putExtra("trip_name",selectedTrip.getTripName());
            intent.putExtra("trip_destination",selectedTrip.getTripDestination());
            intent.putExtra("trip_date",selectedTrip.getTripDate());
            intent.putExtra("trip_risk",selectedTrip.getTripRisk());
            intent.putExtra("trip_description",selectedTrip.getTripDescription());
            intent.putExtra("trip_participants",selectedTrip.getTripParticipants());
            intent.putExtra("trip_estimatedCost",selectedTrip.getTripEstimatedCost());

            startActivity(intent);
        });
    }

    class TripListViewAdapter extends BaseAdapter{
        final List<Trip> listTrip;
        TripListViewAdapter(List<Trip> listTrip) {
            this.listTrip = listTrip;
        }

        @Override
        public int getCount() {
            return listTrip.size();
        }

        @Override
        public Object getItem(int position) {
            return listTrip.get(position);
        }

        @Override
        public long getItemId(int position) {
            return listTrip.get(position).tripId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View viewTrip;
            if (convertView == null) {
                viewTrip = View.inflate(parent.getContext(), R.layout.line_trip, null);
            } else viewTrip = convertView;

            Trip trip = (Trip) getItem(position);
            TextView txtViewTripName = viewTrip.findViewById(R.id.textViewTripName);
            TextView txtViewTripDestination = viewTrip.findViewById(R.id.textViewTripDestination);
            TextView txtViewTripDate = viewTrip.findViewById(R.id.textViewTripDate);
            TextView txtViewTripParticipants = viewTrip.findViewById(R.id.textViewTripParticipants);

            txtViewTripName.setText(String.format(trip.getTripName()));
            txtViewTripDestination.setText(String.format("Address: " + trip.getTripDestination()));
            txtViewTripDate.setText(String.format("Date: " + trip.getTripDate()));
            txtViewTripParticipants.setText(String.format("Participants: " + trip.getTripParticipants()));

            return viewTrip;
        }
    }
}