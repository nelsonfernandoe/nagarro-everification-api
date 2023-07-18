package com.nagarro.everification.controller;

import com.nagarro.everification.exception.EverificationNotFoundException;
import com.nagarro.everification.model.EventSource;
import com.nagarro.everification.payload.response.EverificationDataResponse;
import com.nagarro.everification.payload.response.EverificationResponse;
import com.nagarro.everification.repository.EventSourceRepository;
import com.nagarro.everification.to.EventSourceCountByStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "*")
//for Angular Client (withCredentials)
@CrossOrigin(originPatterns = "*", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/event-source")
public class EventSourceController {

    @Autowired
    EventSourceRepository eventSourceRepository;

    @GetMapping("")
    public Page<EventSource> findAll(Pageable pageable) {
        return eventSourceRepository.findAll(pageable);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EventSource> findById(@PathVariable Long id) throws EverificationNotFoundException {
        EventSource es = eventSourceRepository.findById(id).orElseThrow(() -> new EverificationNotFoundException("Everification data Not Found with id: " + id));
        return ResponseEntity.ok().body(es);
    }

    @GetMapping("/getByStatus")
    public ResponseEntity<EverificationResponse> getByStatus(@RequestParam String status) throws EverificationNotFoundException {

        /*Get data based on status */
        String derivedStatus = status.isEmpty() ? "Unassigned" : status;

        List<EventSource> eventSources = eventSourceRepository.findByStatus(derivedStatus).orElseThrow(() -> new EverificationNotFoundException("Everification data Not Found with status: " + derivedStatus));

        EverificationResponse body = new EverificationResponse(eventSources);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/statusCount")
    public ResponseEntity<EverificationDataResponse> statusCount() {
        List<EventSourceCountByStatus> eventSources = eventSourceRepository.getCountByStatus();

        EverificationDataResponse body = new EverificationDataResponse(eventSources);
        return ResponseEntity.ok().body(body);
    }

}
