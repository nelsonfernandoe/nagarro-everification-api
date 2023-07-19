package com.nagarro.everification.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nagarro.everification.model.EventSource;
import com.nagarro.everification.to.EverificationTo;

import java.util.List;
import java.util.stream.Collectors;

public class EverificationResponse {
    @JsonProperty(value = "everifications")
    public
    List<EverificationTo> everificationEntities;

    public EverificationResponse(List<EventSource> eventSources) {
        this.everificationEntities = eventSources.stream().map(es -> new EverificationTo(
                es.getId(),
                es.getBusinessKey(),
                es.getPriority(),
                es.getSourceBu(),
                es.getDcpReference(),
                es.getAccountName(),
                es.getTransactionCurrency(),
                es.getTransactionAmount(),
                es.getLockedBy(),
                es.getCreatedOn())).collect(Collectors.toList());
    }
}

