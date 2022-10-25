package com.example.cwmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTrip extends AppCompatActivity {

    EditText edtTripName, edtTripDestination, edtTripDate, edtTripRisk, edtTripDescription, edtTripParticipants, edtTripEstimatedCost;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        edtTripName = findViewById(R.id.editTextTripName);
        edtTripDestination = findViewById(R.id.editTextTripDestination);
        edtTripDate = findViewById(R.id.editTextTripDate);
        edtTripRisk = findViewById(R.id.editTextTripRisk);
        edtTripDescription = findViewById(R.id.editTextTripDescription);
        edtTripParticipants = findViewById(R.id.editTextTripParticipants);
        edtTripEstimatedCost = findViewById(R.id.editTextTripEstimatedCost);

        btnCreate = findViewById(R.id.buttonCreate);


        btnCreate.setOnClickListener(view -> {

            if( edtTripName.getText().toString().length() == 0 ){
                edtTripName.setError( "Trip name is required!" );
            }
            if( edtTripDestination.getText().toString().length() == 0 ){
                edtTripDestination.setError( "Destination is required!" );
            }
            if( edtTripDate.getText().toString().length() == 0 ){
                edtTripDate.setError( "Date is required!" );
            }
            if( edtTripRisk.getText().toString().length() == 0 ){
                edtTripRisk.setError( "Risk is required!" );
            }
            if( edtTripDescription.getText().toString().length() == 0 ){
                edtTripDescription.setError( "Description is required!" );
            }

            if(edtTripName.getText().toString().length() > 0
                    && edtTripDestination.getText().toString().length() > 0
                    && edtTripDate.getText().toString().length() > 0
                    && edtTripRisk.getText().toString().length() > 0
                    && edtTripDescription.getText().toString().length() > 0){
                String tripName = edtTripName.getText().toString();
                String tripDestination = edtTripDestination.getText().toString();
                String tripDate = edtTripDate.getText().toString();
                String tripRisk = edtTripRisk.getText().toString();
                String tripDescription = edtTripDescription.getText().toString();
                int tripParticipants = Integer.parseInt(edtTripParticipants.getText().toString());
                int tripEstimatedCost = Integer.parseInt(edtTripEstimatedCost.getText().toString());

                DatabaseHelper database = new DatabaseHelper(this);
                database.insertTrip(tripName, tripDestination, tripDate, tripRisk, tripDescription, tripParticipants, tripEstimatedCost);

                Toast.makeText(this,"Saved " + tripName + " successfully!" ,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddTrip.this, MainActivity.class);
                startActivity(intent);


            }

        });
    }
}