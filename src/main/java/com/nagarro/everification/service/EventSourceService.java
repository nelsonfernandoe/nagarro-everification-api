package com.nagarro.everification.service;

import com.nagarro.everification.exception.EverificationException;
import com.nagarro.everification.exception.EverificationNotFoundException;
import com.nagarro.everification.model.EventSource;
import com.nagarro.everification.payload.request.EventSourcePatchRequest;
import com.nagarro.everification.payload.response.EverificationDataResponse;
import com.nagarro.everification.payload.response.EverificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventSourceService {

    EventSource findById(Long id) throws EverificationNotFoundException, EverificationException;

    Page<EventSource> findAll(Pageable pageable) throws EverificationException;

    EverificationResponse getByStatus(String status) throws EverificationNotFoundException, EverificationException;

    EverificationDataResponse statusCount() throws EverificationException;

    EventSource assignUser(Long id) throws EverificationNotFoundException, EverificationException;

    EventSource patchEventSource(EventSourcePatchRequest patchRequest) throws EverificationNotFoundException, EverificationException;

}
