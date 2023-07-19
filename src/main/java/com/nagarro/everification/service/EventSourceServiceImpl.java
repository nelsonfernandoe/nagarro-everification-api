package com.nagarro.everification.service;

import com.nagarro.everification.exception.EverificationNotFoundException;
import com.nagarro.everification.model.EventSource;
import com.nagarro.everification.payload.request.EventSourcePatchRequest;
import com.nagarro.everification.payload.response.EverificationDataResponse;
import com.nagarro.everification.payload.response.EverificationResponse;
import com.nagarro.everification.repository.EventSourceRepository;
import com.nagarro.everification.to.EventSourceCountByStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class EventSourceServiceImpl implements EventSourceService {

    @Autowired
    EventSourceRepository eventSourceRepository;

    @Override
    public EventSource findById(Long id) throws EverificationNotFoundException {
        return eventSourceRepository.findById(id)
                .orElseThrow(() -> new EverificationNotFoundException("Everification data Not Found with id: " + id));
    }

    @Override
    public Page<EventSource> findAll(Pageable pageable) {
        return eventSourceRepository.findAll(pageable);
    }

    @Override
    public EverificationResponse getByStatus(String status) throws EverificationNotFoundException {
        /*Get data based on status */
        String derivedStatus = status.isEmpty() ? "Unassigned" : status;

        List<EventSource> eventSources = eventSourceRepository
                .findByStatus(derivedStatus)
                .orElseThrow(() -> new EverificationNotFoundException("Everification data Not Found with status: " + derivedStatus));
        return new EverificationResponse(eventSources);

    }

    @Override
    public EverificationDataResponse statusCount() {
        List<EventSourceCountByStatus> eventSources = eventSourceRepository.getCountByStatus();
        return new EverificationDataResponse(eventSources);
    }

    @Override
    public EventSource assignUser(Long id) throws EverificationNotFoundException {
        EventSource eventSource = eventSourceRepository.findById(id)
                .orElseThrow(() -> new EverificationNotFoundException("Everification data Not Found with id: " + id));

        String currentUsername = getCurrentUsername();
        eventSource.setLockedBy(currentUsername);

        /* add audit details */
        eventSource.setUpdatedBy(currentUsername);
        Timestamp updatedOn = Timestamp.from(Instant.now());
        eventSource.setUpdatedOn(updatedOn);

        return eventSourceRepository.save(eventSource);
    }

    @Override
    public EventSource patchEventSource(EventSourcePatchRequest patchRequest) throws EverificationNotFoundException {
        EventSource eventSource = eventSourceRepository.findById(patchRequest.id())
                .orElseThrow(() -> new EverificationNotFoundException("Everification data Not Found with id: " + patchRequest.id()));

        /* update the user input to entity obj */
        eventSource.setOutcome(patchRequest.outcome());
        eventSource.setExtension(patchRequest.extension());
        eventSource.setContactPerson(patchRequest.contactPerson());
        eventSource.setCustomerCalledOn(patchRequest.customerCalledOn());
        eventSource.setComment(patchRequest.comment());
        eventSource.setStatus(patchRequest.status());

        /* add audit details */
        eventSource.setUpdatedBy(getCurrentUsername());
        Timestamp updatedOn = Timestamp.from(Instant.now());
        eventSource.setUpdatedOn(updatedOn);

        return eventSourceRepository.save(eventSource);
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
