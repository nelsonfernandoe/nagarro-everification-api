package com.nagarro.everification.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nagarro.everification.to.EventSourceCountByStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EverificationDataResponse {
//    @JsonProperty(value = "everificationData")
//    Map<String, Long> everificationEntities;

    @JsonProperty(value = "everificationData")
    private List<EventSourceCountByStatus> eventSourceCountByStatuses;

    public EverificationDataResponse(List<EventSourceCountByStatus> eventSourceCountByStatuses) {
        this.eventSourceCountByStatuses = eventSourceCountByStatuses;
    }

    public List<EventSourceCountByStatus> getEventSourceCountByStatuses() {
        return eventSourceCountByStatuses;
    }

    public void setEventSourceCountByStatuses(List<EventSourceCountByStatus> eventSourceCountByStatuses) {
        this.eventSourceCountByStatuses = eventSourceCountByStatuses;
    }
}


