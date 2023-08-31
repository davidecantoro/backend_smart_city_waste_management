package it.unisalento.pas.smartcitywastemanagement.dto;

import java.util.Date;

public class MeasureWasteBinDTO {
    private String id;
    private Date timestamp;
    private String idBin;
    private Double fillingLevel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getIdBin() {
        return idBin;
    }

    public void setIdBin(String idBin) {
        this.idBin = idBin;
    }

    public Double getFillingLevel() {
        return fillingLevel;
    }

    public void setFillingLevel(Double fillingLevel) {
        this.fillingLevel = fillingLevel;
    }
}
