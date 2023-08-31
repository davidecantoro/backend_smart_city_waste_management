package it.unisalento.pas.smartcitywastemanagement.dto;

import java.util.Date;

public class WasteBinAlertDTO {
    private String binId;
    private Double fillingLevel;
    private Date timestamp;
    private boolean isAlarm;

    public boolean getIsAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(boolean isAlarm) {
        this.isAlarm = isAlarm;
    }

    public String getBinId() {
        return binId;
    }

    public void setBinId(String binId) {
        this.binId = binId;
    }

    public Double getFillingLevel() {
        return fillingLevel;
    }

    public void setFillingLevel(Double fillingLevel) {
        this.fillingLevel = fillingLevel;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}