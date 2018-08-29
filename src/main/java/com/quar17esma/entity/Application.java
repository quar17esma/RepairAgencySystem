package com.quar17esma.entity;

import com.quar17esma.enums.Status;

import java.time.LocalDate;

public class Application {
    private long id;
    private User user;
    private Status status;
    private int price;
    private String product;
    private String repairType;
    private LocalDate createDate;
    private LocalDate processDate;
    private LocalDate completeDate;
    private String declineReason;
    private  Feedback feedback;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
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
                ", user=" + user +
                ", status=" + status +
                ", price=" + price +
                ", product='" + product + '\'' +
                ", repairType='" + repairType + '\'' +
                ", createDate=" + createDate +
                ", processDate=" + processDate +
                ", completeDate=" + completeDate +
                ", declineReason='" + declineReason + '\'' +
                ", feedback=" + feedback +
                '}';
    }

    public static class Builder {
        private  Application application;

        public Builder() {
            this.application = new Application();
        }

        public Application build() {
            return application;
        }

        public Builder setId(long id) {
            application.setId(id);
            return this;
        }

        public Builder setUser(User user) {
            application.setUser(user);
            return this;
        }

        public Builder setStatus(Status status) {
            application.setStatus(status);
            return this;
        }

        public Builder setPrice(int price) {
            application.setPrice(price);
            return this;
        }

        public Builder setProduct(String product) {
            application.setProduct(product);
            return this;
        }

        public Builder setRepairType(String repairType) {
            application.setRepairType(repairType);
            return this;
        }

        public Builder setDeclineReason(String declineReason) {
            application.setDeclineReason(declineReason);
            return this;
        }

        public Builder setCreateDate(LocalDate createDate) {
            application.setCreateDate(createDate);
            return this;
        }

        public Builder setProcessDate(LocalDate processDate) {
            application.setProcessDate(processDate);
            return this;
        }

        public Builder setCompleteDate(LocalDate completeDate) {
            application.setCompleteDate(completeDate);
            return this;
        }
        
        public Builder setFeedback(Feedback feedback) {
            application.setFeedback(feedback);
            return this;
        }
    }
}
