package com.zcswl.flink.watermarks;

/**
 * //station1,18688822219,18684812319,10,1595158485855
 * @author zhoucg
 * @date 2021-03-03 22:48
 */
public class StationLog {

    private String stationID;   //基站ID
    private String from;		//呼叫放
    private String to;			//被叫方
    private long duration;		//通话的持续时间
    private long callTime;		//通话的呼叫时间
    public StationLog(String stationID, String from,
                      String to, long duration,
                      long callTime) {
        this.stationID = stationID;
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.callTime = callTime;
    }
    public String getStationID() {
        return stationID;
    }
    public void setStationID(String stationID) {
        this.stationID = stationID;
    }
    public long getCallTime() {
        return callTime;
    }
    public void setCallTime(long callTime) {
        this.callTime = callTime;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public long getDuration() {
        return duration;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }
}
