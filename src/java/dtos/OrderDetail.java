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
public class OrderDetail {
    private Watch watch;
    private Order order;
    private int quantity;

    public OrderDetail() {
    }

    public OrderDetail(Watch watch, int quantity) {
        this.watch = watch;
        this.quantity = quantity;
    }

    public OrderDetail(Watch watch, Order order, int quantity) {
        this.watch = watch;
        this.order = order;
        this.quantity = quantity;
    }

    public Watch getWatch() {
        return watch;
    }

    public void setWatch(Watch watch) {
        this.watch = watch;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
