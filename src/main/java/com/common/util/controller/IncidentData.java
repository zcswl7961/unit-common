package com.common.util.controller;

import java.util.Date;
import java.util.List;

/**
 * @author: zhoucg
 * @date: 2019-06-19
 */
public class IncidentData {

    private String id;
    private String tenant;
    private String name;
    private String alias;
    private String parentId = null;
    private int subNum = 0;
    private int severity;
    private String source;
    private int status;
    private String entityName;
    private String entityAddr;
    private int count;
    private Date firstOccurTime;
    private Date lastOccurTime;
    private Date closeTime;
    private String description;
    private String orderId;
    private String orderFlowNum;
    private String resolveMessage;
    private String closeMessage;
    private boolean isNotify;
    private String resObjectId;
    private String classCode;
    private List<Property> properties;
    private List<Property> tags;
    private String appKey;
    private String owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getSubNum() {
        return subNum;
    }

    public void setSubNum(int subNum) {
        this.subNum = subNum;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityAddr() {
        return entityAddr;
    }

    public void setEntityAddr(String entityAddr) {
        this.entityAddr = entityAddr;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getFirstOccurTime() {
        return firstOccurTime;
    }

    public void setFirstOccurTime(Date firstOccurTime) {
        this.firstOccurTime = firstOccurTime;
    }

    public Date getLastOccurTime() {
        return lastOccurTime;
    }

    public void setLastOccurTime(Date lastOccurTime) {
        this.lastOccurTime = lastOccurTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderFlowNum() {
        return orderFlowNum;
    }

    public void setOrderFlowNum(String orderFlowNum) {
        this.orderFlowNum = orderFlowNum;
    }

    public String getResolveMessage() {
        return resolveMessage;
    }

    public void setResolveMessage(String resolveMessage) {
        this.resolveMessage = resolveMessage;
    }

    public String getCloseMessage() {
        return closeMessage;
    }

    public void setCloseMessage(String closeMessage) {
        this.closeMessage = closeMessage;
    }

    public boolean isNotify() {
        return isNotify;
    }

    public void setNotify(boolean notify) {
        isNotify = notify;
    }

    public String getResObjectId() {
        return resObjectId;
    }

    public void setResObjectId(String resObjectId) {
        this.resObjectId = resObjectId;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<Property> getTags() {
        return tags;
    }

    public void setTags(List<Property> tags) {
        this.tags = tags;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "IncidentData{" +
                "id='" + id + '\'' +
                ", tenant='" + tenant + '\'' +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", parentId='" + parentId + '\'' +
                ", subNum=" + subNum +
                ", severity=" + severity +
                ", source='" + source + '\'' +
                ", status=" + status +
                ", entityName='" + entityName + '\'' +
                ", entityAddr='" + entityAddr + '\'' +
                ", count=" + count +
                ", firstOccurTime=" + firstOccurTime +
                ", lastOccurTime=" + lastOccurTime +
                ", closeTime=" + closeTime +
                ", description='" + description + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderFlowNum='" + orderFlowNum + '\'' +
                ", resolveMessage='" + resolveMessage + '\'' +
                ", closeMessage='" + closeMessage + '\'' +
                ", isNotify=" + isNotify +
                ", resObjectId='" + resObjectId + '\'' +
                ", classCode='" + classCode + '\'' +
                ", properties=" + properties +
                ", tags=" + tags +
                ", appKey='" + appKey + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
