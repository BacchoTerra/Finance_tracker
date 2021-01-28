package com.bacchoterra.financetracker.model;

public class StockInformation {

    private boolean user_displayable;
    private boolean success;
    private int status;
    private Data data;
    private String error_message;

    public boolean isUser_displayable() {
        return user_displayable;
    }

    public void setUser_displayable(boolean user_displayable) {
        this.user_displayable = user_displayable;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
