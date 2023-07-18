package com.nagarro.everification.to;

//public record EventSourceCountByStatus (String status, Long count){
//}

public class EventSourceCountByStatus {

    private String status;
    private final Long count;

    public EventSourceCountByStatus(String status, Long count) {
        this.status = status;
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCount() {
        return count;
    }
}