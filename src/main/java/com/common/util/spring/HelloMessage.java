package com.common.util.spring;

/**
 * Created by zhoucg on 2019-03-14.
 */
public class HelloMessage {


    private String message;

    private String time;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "HelloMessage{" +
                "message='" + message + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
