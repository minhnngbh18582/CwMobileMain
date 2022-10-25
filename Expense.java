package com.example.cwmobile;

public class Expense
{
    protected int expensesId;
    protected String expensesType;
    protected int tripId;
    protected int expensesAmount;
    protected String expensesTime;
    protected String expensesAdditionalComments;

    public int getExpensesId() {
        return expensesId;
    }
    public void setExpensesId(int expensesId){
        this.expensesId = expensesId;
    }
    public String getExpensesType() {
        return expensesType;
    }
    public void setExpensesType(String expensesType){
        this.expensesType = expensesType;
    }
    public int getTripId() {
        return tripId;
    }
    public void setTripId(int tripId){
        this.tripId = tripId;
    }
    public int getExpensesAmount() {
        return expensesAmount;
    }
    public void setExpensesAmount(int expensesAmount){
        this.expensesAmount = expensesAmount;
    }
    public String getExpensesTime() {
        return expensesTime;
    }
    public void setExpensesTime(String expensesTime){
        this.expensesTime = expensesTime;
    }
    public String getExpensesAdditionalComments() {
        return expensesAdditionalComments;
    }
    public void setExpensesAdditionalComments(String expensesAdditionalComments){
        this.expensesAdditionalComments = expensesAdditionalComments;
    }
}
