/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author Tan Nguyen
 */
public class Watch {
    private String watchId, watchName, manufacturer, description, urlImage;
    private int price;
    private int quantity;
    private boolean sale;

    public Watch() {
    }

    public Watch(String watchId) {
        this.watchId = watchId;
    }

    public Watch(String watchId, String watchName, String manufacturer, String description, String urlImage, int price, int quantity, boolean sale) {
        this.watchId = watchId;
        this.watchName = watchName;
        this.manufacturer = manufacturer;
        this.description = description;
        this.urlImage = urlImage;
        this.price = price;
        this.quantity = quantity;
        this.sale = sale;
    }

    public String getWatchId() {
        return watchId;
    }

    public void setWatchId(String watchId) {
        this.watchId = watchId;
    }

    public String getWatchName() {
        return watchName;
    }

    public void setWatchName(String watchName) {
        this.watchName = watchName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }
    
    
}  
