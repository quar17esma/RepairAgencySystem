package com.quar17esma.entity;

import java.time.LocalDateTime;

public class Feedback {
    private long id;
    private Application application;
    private LocalDateTime dateTime;
    private String comment;
    private int mark;

    public Feedback() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feedback feedback = (Feedback) o;

        return id == feedback.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", application=" + application +
                ", dateTime=" + dateTime +
                ", comment='" + comment + '\'' +
                ", mark=" + mark +
                '}';
    }

    public static class Builder {
        private  Feedback feedback;

        public Builder() {
            this.feedback = new Feedback();
        }

        public Feedback build() {
            return feedback;
        }

        public Builder setId(long id) {
            feedback.setId(id);
            return this;
        }

        public Builder setApplication(Application application) {
            feedback.setApplication(application);
            return this;
        }

        public Builder setDateTime(LocalDateTime dateTime) {
            feedback.setDateTime(dateTime);
            return this;
        }

        public Builder setComment(String comment) {
            feedback.setComment(comment);
            return this;
        }

        public Builder setMark(int mark) {
            feedback.setMark(mark);
            return this;
        }
    }
}
