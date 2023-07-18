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
    List<EventSourceCountByStatus> eventSourceCountByStatuses;

    public EverificationDataResponse(List<EventSourceCountByStatus> eventSourceCountByStatuses) {
//        this.everificationEntities = eventSourceCountByStatuses.stream()
//                .collect(Collectors.toMap(EventSourceCountByStatus::getStatus, EventSourceCountByStatus::getCount));

        this.eventSourceCountByStatuses = eventSourceCountByStatuses;
    }
}


