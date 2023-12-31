package com.nagarro.everification.service;

import com.nagarro.everification.exception.EverificationException;
import com.nagarro.everification.exception.EverificationNotFoundException;
import com.nagarro.everification.model.EventSource;
import com.nagarro.everification.payload.request.EventSourcePatchRequest;
import com.nagarro.everification.payload.response.EverificationDataResponse;
import com.nagarro.everification.payload.response.EverificationResponse;
import com.nagarro.everification.repository.EventSourceRepository;
import com.nagarro.everification.to.EventSourceCountByStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOG = LoggerFactory.getLogger(EventSourceServiceImpl.class);

    @Autowired
    private EventSourceRepository eventSourceRepository;

    @Override
    public EventSource findById(Long id) throws EverificationNotFoundException, EverificationException {
        try {
            return eventSourceRepository.findById(id)
                    .orElseThrow(() -> new EverificationNotFoundException("Everification data Not Found with id: " + id));
        } catch (EverificationNotFoundException e) {
            LOG.error("Resource not found. Please try again later.", e);
            throw new EverificationNotFoundException("Resource not found. Please try again later.", e);
        } catch (Exception e) {
            LOG.error("Some error occurred. Please try again later.", e);
            throw new EverificationException("Some error occurred. Please try again later.", e);
        }
    }

    @Override
    public Page<EventSource> findAll(Pageable pageable) throws EverificationException {
        try {
            return eventSourceRepository.findAll(pageable);
        } catch (Exception e) {
            LOG.error("Some error occurred. Please try again later.", e);
            throw new EverificationException("Some error occurred. Please try again later.", e);
        }
    }

    @Override
    public EverificationResponse getByStatus(String status) throws EverificationNotFoundException, EverificationException {
        try {
            LOG.info("Event source requested by status: {}", status);

            /*Get data based on status */
            String derivedStatus = status.isEmpty() ? "unassigned" : status;

            List<EventSource> eventSources = eventSourceRepository
                    .findByStatus(derivedStatus)
                    .orElseThrow(() -> new EverificationNotFoundException("Everification data Not Found with status: " + derivedStatus));
            return new EverificationResponse(eventSources);
        } catch (EverificationNotFoundException e) {
            LOG.error("Resource not found. Please try again later.", e);
            throw new EverificationNotFoundException("Resource not found. Please try again later.", e);
        } catch (Exception e) {
            LOG.error("Some error occurred. Please try again later.", e);
            throw new EverificationException("Some error occurred. Please try again later.", e);
        }

    }

    @Override
    public EverificationDataResponse statusCount() throws EverificationException {
        try {
            LOG.info("Event source status count requested");
            List<EventSourceCountByStatus> eventSources = eventSourceRepository.getCountByStatus();
            return new EverificationDataResponse(eventSources);
        } catch (Exception e) {
            LOG.error("Some error occurred. Please try again later.", e);
            throw new EverificationException("Some error occurred. Please try again later.", e);
        }
    }

    @Override
    public EventSource assignUser(Long id) throws EverificationNotFoundException, EverificationException {
        try {
            LOG.info("Event source id {} assigned to current user requested", id);

            EventSource eventSource = eventSourceRepository.findById(id)
                    .orElseThrow(() -> new EverificationNotFoundException("Everification data Not Found with id: " + id));

            String currentUsername = getCurrentUsername();
            eventSource.setLockedBy(currentUsername);

            /* add audit details */
            eventSource.setUpdatedBy(currentUsername);
            Timestamp updatedOn = Timestamp.from(Instant.now());
            eventSource.setUpdatedOn(updatedOn);

            return eventSourceRepository.save(eventSource);
        } catch (EverificationNotFoundException e) {
            LOG.error("Resource not found. Please try again later.", e);
            throw new EverificationNotFoundException("Resource not found. Please try again later.", e);
        } catch (Exception e) {
            LOG.error("Some error occurred. Please try again later.", e);
            throw new EverificationException("Some error occurred. Please try again later.", e);
        }
    }

    @Override
    public EventSource patchEventSource(EventSourcePatchRequest patchRequest) throws EverificationNotFoundException, EverificationException {
        try {
            LOG.info("Event source id {} is requested for updating", patchRequest.id());

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
        } catch (EverificationNotFoundException e) {
            LOG.error("Resource not found. Please try again later.", e);
            throw new EverificationNotFoundException("Resource not found. Please try again later.", e);
        } catch (Exception e) {
            LOG.error("Some error occurred. Please try again later.", e);
            throw new EverificationException("Some error occurred. Please try again later.", e);
        }
    }

    private String getCurrentUsername() throws EverificationException {
        try {
            Authentication auth = SecurityContextHolder.getContext()
                    .getAuthentication();
            return auth.getName();
        } catch (Exception e) {
            LOG.error("Some error occurred. Please try again later.", e);
            throw new EverificationException("Some error occurred. Please try again later.", e);
        }
    }
}
