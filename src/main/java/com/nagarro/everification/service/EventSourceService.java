package com.nagarro.everification.service;

import com.nagarro.everification.exception.EverificationNotFoundException;
import com.nagarro.everification.model.EventSource;
import com.nagarro.everification.payload.request.EventSourcePatchRequest;
import com.nagarro.everification.payload.response.EverificationDataResponse;
import com.nagarro.everification.payload.response.EverificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventSourceService {

    EventSource findById(Long id) throws EverificationNotFoundException;

    Page<EventSource> findAll(Pageable pageable);

    EverificationResponse getByStatus(String status) throws EverificationNotFoundException;

    EverificationDataResponse statusCount();

    EventSource patchEventSource(EventSourcePatchRequest patchRequest) throws EverificationNotFoundException;
}
