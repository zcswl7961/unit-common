package com.common.util;

import java.util.List;

/**
 *
 * @author zhoucg
 * @date 2018-11-22
 */
public class GJSON {

    /**
     * 使用GsonFormat工具根据json自动生成对应的实体类
     * alt+s
     */


    /**
     * data : [{"dataName":"地面气象要素日数据","time":"2018-06-05 12：00","broadcastFilecount":"156","broadcastFilesize":"23456","broadcaseIsdelay":"否","resvFilecount":"156","resvFilesize":"23456","sendFilecount":"156","sendFilesize":"23456","sendRate":"342"},{"dataName":"地面气象要素日数据","time":"2018-06-05 12：00","broadcastFilecount":"156","broadcastFilesize":"23456","broadcaseIsdelay":"否","resvFilecount":"156","resvFilesize":"23456","sendFilecount":"156","sendFilesize":"23456","sendRate":"342"},{"dataName":"地面气象要素日数据","time":"2018-06-05 12：00","broadcastFilecount":"156","broadcastFilesize":"23456","broadcaseIsdelay":"否","resvFilecount":"156","resvFilesize":"23456","sendFilecount":"156","sendFilesize":"23456","sendRate":"342"},{"dataName":"地面气象要素日数据","time":"2018-06-05 12：00","broadcastFilecount":"156","broadcastFilesize":"23456","broadcaseIsdelay":"否","resvFilecount":"156","resvFilesize":"23456","sendFilecount":"156","sendFilesize":"23456","sendRate":"342"},{"dataName":"地面气象要素日数据","time":"2018-06-05 12：00","broadcastFilecount":"156","broadcastFilesize":"23456","broadcaseIsdelay":"否","resvFilecount":"156","resvFilesize":"23456","sendFilecount":"156","sendFilesize":"23456","sendRate":"342"},{"dataName":"地面气象要素日数据","time":"2018-06-05 12：00","broadcastFilecount":"156","broadcastFilesize":"23456","broadcaseIsdelay":"否","resvFilecount":"156","resvFilesize":"23456","sendFilecount":"156","sendFilesize":"23456","sendRate":"342"},{"dataName":"地面气象要素日数据","time":"2018-06-05 12：00","broadcastFilecount":"156","broadcastFilesize":"23456","broadcaseIsdelay":"否","resvFilecount":"156","resvFilesize":"23456","sendFilecount":"156","sendFilesize":"23456","sendRate":"342"},{"dataName":"地面气象要素日数据","time":"2018-06-05 12：00","broadcastFilecount":"156","broadcastFilesize":"23456","broadcaseIsdelay":"否","resvFilecount":"156","resvFilesize":"23456","sendFilecount":"156","sendFilesize":"23456","sendRate":"342"},{"dataName":"地面气象要素日数据","time":"2018-06-05 12：00","broadcastFilecount":"156","broadcastFilesize":"23456","broadcaseIsdelay":"否","resvFilecount":"156","resvFilesize":"23456","sendFilecount":"156","sendFilesize":"23456","sendRate":"342"}]
     * statusCode : 200
     * message : success
     */

    private int statusCode;
    private String message;
    private List<DataBean> data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * dataName : 地面气象要素日数据
         * time : 2018-06-05 12：00
         * broadcastFilecount : 156
         * broadcastFilesize : 23456
         * broadcaseIsdelay : 否
         * resvFilecount : 156
         * resvFilesize : 23456
         * sendFilecount : 156
         * sendFilesize : 23456
         * sendRate : 342
         */

        private String dataName;
        private String time;
        private String broadcastFilecount;
        private String broadcastFilesize;
        private String broadcaseIsdelay;
        private String resvFilecount;
        private String resvFilesize;
        private String sendFilecount;
        private String sendFilesize;
        private String sendRate;

        public String getDataName() {
            return dataName;
        }

        public void setDataName(String dataName) {
            this.dataName = dataName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getBroadcastFilecount() {
            return broadcastFilecount;
        }

        public void setBroadcastFilecount(String broadcastFilecount) {
            this.broadcastFilecount = broadcastFilecount;
        }

        public String getBroadcastFilesize() {
            return broadcastFilesize;
        }

        public void setBroadcastFilesize(String broadcastFilesize) {
            this.broadcastFilesize = broadcastFilesize;
        }

        public String getBroadcaseIsdelay() {
            return broadcaseIsdelay;
        }

        public void setBroadcaseIsdelay(String broadcaseIsdelay) {
            this.broadcaseIsdelay = broadcaseIsdelay;
        }

        public String getResvFilecount() {
            return resvFilecount;
        }

        public void setResvFilecount(String resvFilecount) {
            this.resvFilecount = resvFilecount;
        }

        public String getResvFilesize() {
            return resvFilesize;
        }

        public void setResvFilesize(String resvFilesize) {
            this.resvFilesize = resvFilesize;
        }

        public String getSendFilecount() {
            return sendFilecount;
        }

        public void setSendFilecount(String sendFilecount) {
            this.sendFilecount = sendFilecount;
        }

        public String getSendFilesize() {
            return sendFilesize;
        }

        public void setSendFilesize(String sendFilesize) {
            this.sendFilesize = sendFilesize;
        }

        public String getSendRate() {
            return sendRate;
        }

        public void setSendRate(String sendRate) {
            this.sendRate = sendRate;
        }
    }
}
