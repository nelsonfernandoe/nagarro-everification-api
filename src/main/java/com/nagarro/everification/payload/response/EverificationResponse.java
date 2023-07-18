package com.nagarro.everification.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nagarro.everification.model.EventSource;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EverificationResponse {
    @JsonProperty(value = "everifications")
    List<EverificationEntity> everificationEntities;

    public EverificationResponse(List<EventSource> eventSources) {
        this.everificationEntities = eventSources.stream().map(es -> new EverificationEntity(
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

record EverificationEntity(Long id,
                           String businessKey,
                           String priority,
                           String sourceBu,
                           String dcpReference,
                           String accountName,
                           String transactionCurrency,
                           Double transactionAmount,
                           String lockedBy,
                           Date createdOn) {
}
