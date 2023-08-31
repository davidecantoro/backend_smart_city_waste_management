package it.unisalento.pas.smartcitywastemanagement.dto;

public class WasteContainerDTO {

    private String id;
    private String type;
    private String refWasteContainerModel;
    private String serialNumber;
    private Double latitude;
    private Double longitude;
    private String via;
    private Double fillingLevel;

    public Double getFillingLevel() {
        return fillingLevel;
    }

    public void setFillingLevel(Double fillingLevel) {
        this.fillingLevel = fillingLevel;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefWasteContainerModel() {
        return refWasteContainerModel;
    }

    public void setRefWasteContainerModel(String refWasteContainerModel) {
        this.refWasteContainerModel = refWasteContainerModel;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}

