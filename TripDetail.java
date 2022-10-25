package com.example.cwmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TripDetail extends AppCompatActivity {

    TextView txtTripDetailName, txtTripDetailDestination, txtTripDetailDate, txtTripDetailRisk,
            txtTripDetailDescription, txtTripDetailParticipants, txtTripDetailEstimatedCost;
    ImageView iconEdit, iconDelete;
    EditText txtEditTripName, txtEditTripDestination, txtEditTripRisk, txtEditTripDescription,
            txtEditTripDate, txtEditTripParticipants, txtEditTripEstimatedCost;
    Button btnSave, btnAddInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        DatabaseHelper dbHelper = new DatabaseHelper(TripDetail.this);

        Intent intent = getIntent();

        int tripId = intent.getIntExtra("id", 0);
        String tripName = intent.getStringExtra("trip_name");
        String tripDestination = intent.getStringExtra("trip_destination");
        String tripDate = intent.getStringExtra("trip_date");
        String tripRisk = intent.getStringExtra("trip_risk");
        String tripDescription = intent.getStringExtra("trip_description");
        int tripParticipants = intent.getIntExtra("trip_participants", 0);
        int tripEstimatedCost = intent.getIntExtra("trip_estimatedCost", 0);


        txtTripDetailName = findViewById(R.id.textViewTripDetailName);
        txtTripDetailDestination = findViewById(R.id.textViewTripDetailDestination);
        txtTripDetailDate = findViewById(R.id.textViewTripDetailDate);
        txtTripDetailRisk = findViewById(R.id.textViewTripDetailRisk);
        txtTripDetailDescription = findViewById(R.id.textViewTripDetailDescription);
        txtTripDetailParticipants = findViewById(R.id.textViewTripDetailParticipants);
        txtTripDetailEstimatedCost = findViewById(R.id.textViewTripDetailEstimatedCost);

        txtTripDetailName.setText(tripName);
        txtTripDetailDestination.setText("Destination: " + tripDestination);
        txtTripDetailDate.setText("Date: " + tripDate);
        txtTripDetailRisk.setText("Risk: " + tripRisk);
        txtTripDetailDescription.setText("Description: " + tripDescription);
        txtTripDetailParticipants.setText("Participants: " + tripParticipants);
        txtTripDetailEstimatedCost.setText("Estimated Cost: " + tripEstimatedCost);

        iconEdit = findViewById(R.id.imageViewEdit);
        iconDelete = findViewById(R.id.imageViewDelete);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.edit_trip);

        iconEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogEditTrip(tripId, tripName, tripDestination, tripDate, tripRisk, tripDescription, tripParticipants, tripEstimatedCost);
            }
        });

        iconDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteTrip(tripId);

                Intent intent = new Intent(TripDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnAddInvoice = findViewById(R.id.buttonAddCost);
        btnAddInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripDetail.this, AddExpenses.class);

                intent.putExtra("id", tripId);

                startActivity(intent);
            }
        });

        List<Expense> expenses = dbHelper.getExpenses(tripId);
//        int tripIdDetail = expenses.getTripId();
        TripDetail.ExpenseListViewAdapter expenseListViewAdapter = new TripDetail.ExpenseListViewAdapter(expenses);
        ListView listView = findViewById(R.id.listviewExpenses);
        listView.setAdapter(expenseListViewAdapter);
    }

    public void DialogEditTrip(int id, String tripName, String tripDestination, String tripDate, String tripRisk, String tripDescription, int tripParticipants, int tripEstimatedCost) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        dialog.setContentView(R.layout.edit_trip);

        DatabaseHelper dbHelper = new DatabaseHelper(TripDetail.this);

        txtEditTripName = dialog.findViewById(R.id.editTxtTripName);
        txtEditTripDestination = dialog.findViewById(R.id.editTxtTripDestination);
        txtEditTripDate = dialog.findViewById(R.id.editTxtTripDate);
        txtEditTripRisk = dialog.findViewById(R.id.editTxtTripRisk);
        txtEditTripDescription = dialog.findViewById(R.id.editTxtTripDescription);
        txtEditTripParticipants = dialog.findViewById(R.id.editTxtTripParticipants);
        txtEditTripEstimatedCost = dialog.findViewById(R.id.editTxtTripEstimatedCost);

        txtEditTripName.setText(tripName);
        txtEditTripDestination.setText(tripDestination);
        txtEditTripDate.setText(tripDate);
        txtEditTripRisk.setText(tripRisk);
        txtEditTripDescription.setText(tripDescription);
        txtEditTripParticipants.setText("" + tripParticipants);
        txtEditTripEstimatedCost.setText("" + tripEstimatedCost);

        btnSave = dialog.findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(view -> {
            String editTripName = txtEditTripName.getText().toString();
            String editTripDestination = txtEditTripDestination.getText().toString();
            String editTripDate = txtEditTripDate.getText().toString();
            String editTripRisk = txtEditTripRisk.getText().toString();
            String editTripDescription = txtEditTripDescription.getText().toString();
            int editTripParticipants = Integer.parseInt(txtEditTripParticipants.getText().toString());
            int editTripEstimatedCost = Integer.parseInt(txtEditTripEstimatedCost.getText().toString());

            Trip trip = new Trip();
            trip.setTripId(id);
            trip.setTripName(editTripName);
            trip.setTripDestination(editTripDestination);
            trip.setTripDate(editTripDate);
            trip.setTripRisk(editTripRisk);
            trip.setTripDescription(editTripDescription);
            trip.setTripParticipants(editTripParticipants);
            trip.setTripEstimatedCost(editTripEstimatedCost);

            dbHelper.updateTrip(trip);

            Toast.makeText(this, "Updated!!!", Toast.LENGTH_SHORT).show();

            dialog.dismiss();

            Intent intent = new Intent(TripDetail.this, MainActivity.class);
            startActivity(intent);
        });
        dialog.show();
    }

    class ExpenseListViewAdapter extends BaseAdapter {
        final List<Expense> listExpense;
        ExpenseListViewAdapter(List<Expense> listExpense) {
            this.listExpense = listExpense;
        }

        @Override
        public int getCount() {
            return listExpense.size();
        }

        @Override
        public Object getItem(int position) {
            return listExpense.get(position);
        }

        @Override
        public long getItemId(int position) {
            return listExpense.get(position).expensesId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View viewExpense;
            if (convertView == null) {
                viewExpense = View.inflate(parent.getContext(), R.layout.line_expense, null);
            } else viewExpense = convertView;

            Intent intent = getIntent();
            int tripId = intent.getIntExtra("id", 0);

            Expense expense = (Expense) getItem(position);

            TextView txtViewExpenseType = viewExpense.findViewById(R.id.textViewExpenseType);
            TextView txtViewExpenseAmount = viewExpense.findViewById(R.id.textViewExpenseAmount);
            TextView txtViewExpenseTime = viewExpense.findViewById(R.id.textViewExpenseTime);
            TextView txtViewAdditionalComments = viewExpense.findViewById(R.id.textViewAdditionalComments);

            txtViewExpenseType.setText(String.format(expense.getExpensesType()));
            txtViewExpenseAmount.setText(String.format("Amount: " + expense.getExpensesAmount()));
            txtViewExpenseTime.setText(String.format("Time: " + expense.getExpensesTime()));
            txtViewAdditionalComments.setText(String.format("Note: " + expense.getExpensesAdditionalComments()));

            return viewExpense;
        }
    }
}