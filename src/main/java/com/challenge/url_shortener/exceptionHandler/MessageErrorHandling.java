package com.challenge.url_shortener.exceptionHandler;

import java.util.Date;

public class MessageErrorHandling {
    private Date timeStamp;
    private Integer status;
    private String message;

    public MessageErrorHandling(Date timeStamp, Integer status, String message){
        this.timeStamp = timeStamp;
        this.status = status;
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
