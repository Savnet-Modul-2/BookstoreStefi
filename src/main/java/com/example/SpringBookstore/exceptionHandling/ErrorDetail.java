package com.example.SpringBookstore.exceptionHandling;

import java.time.LocalDateTime;

public class ErrorDetail {
    private Integer statusCode;
    private String errorTitle;
    private String errorMessage;
    private LocalDateTime timeStamp;

    public ErrorDetail(Integer statusCode, String errorTitle, String errorMessage, LocalDateTime timeStamp) {
        this.statusCode = statusCode;
        this.errorTitle = errorTitle;
        this.errorMessage = errorMessage;
        this.timeStamp = timeStamp;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public void setErrorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
