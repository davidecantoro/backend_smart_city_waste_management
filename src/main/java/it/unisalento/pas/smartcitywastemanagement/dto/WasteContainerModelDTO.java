package it.unisalento.pas.smartcitywastemanagement.dto;

import java.util.List;

public class WasteContainerModelDTO {

    private String id;
    private double width;
    private double height;
    private double depth;
    private int cargoVolume;
    private String brandName;
    private String modelName;
    private List<String> compliantWith;
    private String madeOf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public int getCargoVolume() {
        return cargoVolume;
    }

    public void setCargoVolume(int cargoVolume) {
        this.cargoVolume = cargoVolume;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public List<String> getCompliantWith() {
        return compliantWith;
    }

    public void setCompliantWith(List<String> compliantWith) {
        this.compliantWith = compliantWith;
    }

    public String getMadeOf() {
        return madeOf;
    }

    public void setMadeOf(String madeOf) {
        this.madeOf = madeOf;
    }
}
