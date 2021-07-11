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
public class Order{
    private int orderId;
    private String customerId, orderDay, address;
    private int totalCost;
    private boolean payment;

    public Order() {
    }

    public Order(int orderId) {
        this.orderId = orderId;
    }

    public Order(String customerId, String orderDay, String address, int totalCost, boolean payment) {
        this.customerId = customerId;
        this.orderDay = orderDay;
        this.address = address;
        this.totalCost = totalCost;
        this.payment = payment;
    }

    public Order(int orderId, String customerId, String orderDay, String address, int totalCost, boolean payment) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDay = orderDay;
        this.address = address;
        this.totalCost = totalCost;
        this.payment = payment;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderDay() {
        return orderDay;
    }

    public void setOrderDay(String orderDay) {
        this.orderDay = orderDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

   
}
