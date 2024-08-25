package com.hsbc.ecommerce.model;

import java.util.Date;

public class DeliverySchedule {
    private int scheduleId;
    private Date scheduledDeliveryDate;
    private int orderId;
    private int productId;
    private String customerName;
    private String address;
    private String status;

    // Constructor
    public DeliverySchedule() {
    }

    public DeliverySchedule(int scheduleId, Date scheduledDeliveryDate, int orderId, int productId, String customerName, String address, String status) {
        this.scheduleId = scheduleId;
        this.scheduledDeliveryDate = scheduledDeliveryDate;
        this.orderId = orderId;
        this.productId = productId;
        this.customerName = customerName;
        this.address = address;
        this.status = status;
    }

    // Getters and Setters
    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Date getScheduledDeliveryDate() {
        return scheduledDeliveryDate;
    }

    public void setScheduledDeliveryDate(Date scheduledDeliveryDate) {
        this.scheduledDeliveryDate = scheduledDeliveryDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Additional methods for convenience
    @Override
    public String toString() {
        return "DeliverySchedule{" +
                "scheduleId=" + scheduleId +
                ", scheduledDeliveryDate=" + scheduledDeliveryDate +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", customerName='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
