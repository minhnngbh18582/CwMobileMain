package com.example.cwmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DB_CWMOBILE";
    private static final String TABLE_TRIP = "Trip";
    private static final String TABLE_EXPENSES = "Expenses";

    public static final String TRIP_ID = "id";
    public static final String TRIP_NAME = "trip_name";
    public static final String TRIP_DESTINATION = "trip_destination";
    public static final String TRIP_DATE = "trip_date";
    public static final String TRIP_RISK = "trip_risk";
    public static final String TRIP_DESCRIPTION = "trip_description";
    public static final String TRIP_PARTICIPANTS = "trip_participants";
    public static final String TRIP_ESTIMATEDCOST = "trip_estimatedCost";

    public static final String EXPENSES_ID = "expenses_id";
    public static final String EXPENSES_TYPE = "expenses_type";
    public static final String EXPENSES_AMOUNT = "expenses_amount";
    public static final String EXPENSES_TIME = "expenses_time";
    public static final String EXPENSES_ADDITIONALCOMMENTS = "expenses_additionalComments";

    private SQLiteDatabase database;

    private static final String TRIP_TABLE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s INTEGER, " +
                    "   %s INTEGER)",
            TABLE_TRIP, TRIP_ID, TRIP_NAME, TRIP_DESTINATION, TRIP_DATE, TRIP_RISK, TRIP_DESCRIPTION, TRIP_PARTICIPANTS, TRIP_ESTIMATEDCOST);

    private static final String EXPENSES_TABLE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s INTEGER, " +
                    "   %s TEXT, " +
                    "   %s INTEGER, " +
                    "   %s TEXT, " +
                    "   %s TEXT)",
            TABLE_EXPENSES, EXPENSES_ID, TRIP_ID, EXPENSES_TYPE, EXPENSES_AMOUNT, EXPENSES_TIME, EXPENSES_ADDITIONALCOMMENTS);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 5);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TRIP_TABLE_CREATE);
        db.execSQL(EXPENSES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);

        Log.v(this.getClass().getName(), DATABASE_NAME + " database upgrade to version " +
                newVersion + " - old data lost");
        onCreate(db);
    }

    public long insertTrip(String trip_name, String trip_destination, String trip_date, String trip_risk, String trip_description, int trip_participants, int trip_estimatedCost) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(TRIP_NAME, trip_name);
        rowValues.put(TRIP_DESTINATION, trip_destination);
        rowValues.put(TRIP_DATE, trip_date);
        rowValues.put(TRIP_RISK, trip_risk);
        rowValues.put(TRIP_DESCRIPTION, trip_description);
        rowValues.put(TRIP_PARTICIPANTS, trip_participants);
        rowValues.put(TRIP_ESTIMATEDCOST, trip_estimatedCost);
        return database.insertOrThrow(TABLE_TRIP, null, rowValues);
    }

    public long updateTrip(Trip trip) {
        database = getReadableDatabase();

        ContentValues rowValues = new ContentValues();

        rowValues.put(TRIP_NAME, trip.getTripName());
        rowValues.put(TRIP_DESTINATION, trip.getTripDestination());
        rowValues.put(TRIP_DATE, trip.getTripDate());
        rowValues.put(TRIP_RISK, trip.getTripRisk());
        rowValues.put(TRIP_DESCRIPTION, trip.getTripDescription());
        rowValues.put(TRIP_PARTICIPANTS, trip.getTripParticipants());
        rowValues.put(TRIP_ESTIMATEDCOST, trip.getTripEstimatedCost());
        return database.update(TABLE_TRIP, rowValues, TRIP_ID + "=?", new String[] {String.valueOf(trip.getTripId())});
    }

    public int deleteTrip(int id){
        database = getReadableDatabase();
        return database.delete(TABLE_TRIP,TRIP_ID+"=?",new String[] {String.valueOf(id)});
    }

    public List<Trip> getTrips(){
        Cursor cursor = database.query(TABLE_TRIP, new String[] {TRIP_ID, TRIP_NAME, TRIP_DESTINATION, TRIP_DATE, TRIP_RISK, TRIP_DESCRIPTION, TRIP_PARTICIPANTS, TRIP_ESTIMATEDCOST},
                null, null, null, null, "id");

        List<Trip> results = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String TripName = cursor.getString(1);
            String TripDestination = cursor.getString(2);
            String TripDate = cursor.getString(3);
            String TripRisk = cursor.getString(4);
            String TripDescription = cursor.getString(5);
            int TripParticipants = cursor.getInt(6);
            int TripEstimatedCost = cursor.getInt(7);

            Trip trip = new Trip();
            trip.setTripId(id);
            trip.setTripName(TripName);
            trip.setTripDestination(TripDestination);
            trip.setTripDate(TripDate);
            trip.setTripRisk(TripRisk);
            trip.setTripDescription(TripDescription);
            trip.setTripParticipants(TripParticipants);
            trip.setTripEstimatedCost(TripEstimatedCost);

            results.add(trip);

            cursor.moveToNext();
        }
        return  results;
    }

    public long insertExpenses(String expenses_type, int trip_id, int expenses_amount, String expenses_time, String expenses_additionalComments) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(EXPENSES_TYPE, expenses_type);
        rowValues.put(TRIP_ID, trip_id);
        rowValues.put(EXPENSES_AMOUNT, expenses_amount);
        rowValues.put(EXPENSES_TIME, expenses_time);
        rowValues.put(EXPENSES_ADDITIONALCOMMENTS, expenses_additionalComments);

        return database.insertOrThrow(TABLE_EXPENSES, null, rowValues);
    }

    public List<Expense> getExpenses(int id){
        Cursor cursor = database.query(TABLE_EXPENSES, new String[] {EXPENSES_ID, EXPENSES_TYPE, TRIP_ID, EXPENSES_AMOUNT, EXPENSES_TIME, EXPENSES_ADDITIONALCOMMENTS},
                String.valueOf(id), null, null, null, EXPENSES_TIME);

        List<Expense> results = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            if(id == cursor.getInt(2)){
                int ExpensesId = cursor.getInt(0);
                String ExpensesType = cursor.getString(1);
                int tripId = cursor.getInt(2);
                int ExpensesAmount = cursor.getInt(3);
                String ExpensesTime = cursor.getString(4);
                String ExpensesAdditionalComments = cursor.getString(5);

                Expense expense = new Expense();
                expense.setExpensesId(ExpensesId);
                expense.setExpensesType(ExpensesType);
                expense.setTripId(tripId);
                expense.setExpensesAmount(ExpensesAmount);
                expense.setExpensesTime(ExpensesTime);
                expense.setExpensesAdditionalComments(ExpensesAdditionalComments);

                results.add(expense);
            }

            cursor.moveToNext();
        }
        return  results;
    }

}