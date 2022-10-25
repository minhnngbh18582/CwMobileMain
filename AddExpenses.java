package com.example.cwmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddExpenses extends AppCompatActivity {

    EditText edtExpenseType, edtExpenseAmount, edtExpenseTime, edtExpenseAdditionalComments;
    Button btnCreateExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        Intent intent = getIntent();

        edtExpenseType = findViewById(R.id.editTextExpenseType);
        edtExpenseAmount = findViewById(R.id.editTextExpenseAmount);
        edtExpenseTime = findViewById(R.id.editTextExpenseTime);
        edtExpenseAdditionalComments = findViewById(R.id.editTextExpenseAdditionalComments);

        btnCreateExpense = findViewById(R.id.buttonCreateExpense);
        btnCreateExpense.setOnClickListener(view -> {

            String expenseType = edtExpenseType.getText().toString();
            int tripId = intent.getIntExtra("id", 0);
            int expenseAmount = Integer.parseInt(edtExpenseAmount.getText().toString());
            String expenseTime = edtExpenseTime.getText().toString();
            String expenseAdditionalComments = edtExpenseAdditionalComments.getText().toString();

            DatabaseHelper dbHelper = new DatabaseHelper(this);
            long expense_id = dbHelper.insertExpenses(expenseType, tripId, expenseAmount, expenseTime, expenseAdditionalComments);

            Toast.makeText(this, String.valueOf(expense_id) +  expenseType + "created!",Toast.LENGTH_SHORT).show();

            Intent intent1 = new Intent(AddExpenses.this, MainActivity.class);
            startActivity(intent1);
        });
    }
}