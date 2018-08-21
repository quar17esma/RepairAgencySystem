package com.quar17esma.entity;

import com.quar17esma.enums.Status;

import java.time.LocalDate;

public class Application {
    private long id;
    private Status status;
    private int price;
    private String product;
    private String repairType;
    private LocalDate createDate;
    private LocalDate processDate;
    private LocalDate completeDate;
    private String declineReason;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getProcessDate() {
        return processDate;
    }

    public void setProcessDate(LocalDate processDate) {
        this.processDate = processDate;
    }

    public LocalDate getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(LocalDate completeDate) {
        this.completeDate = completeDate;
    }

    public String getDeclineReason() {
        return declineReason;
    }

    public void setDeclineReason(String declineReason) {
        this.declineReason = declineReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", status=" + status +
                ", price=" + price +
                ", product='" + product + '\'' +
                ", repairType='" + repairType + '\'' +
                ", createDate=" + createDate +
                ", processDate=" + processDate +
                ", completeDate=" + completeDate +
                ", declineReason='" + declineReason + '\'' +
                '}';
    }
}
