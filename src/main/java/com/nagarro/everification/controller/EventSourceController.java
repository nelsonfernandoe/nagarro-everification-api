package com.nagarro.everification.controller;

import com.nagarro.everification.exception.EverificationNotFoundException;
import com.nagarro.everification.model.EventSource;
import com.nagarro.everification.payload.request.EventSourcePatchRequest;
import com.nagarro.everification.payload.response.EverificationDataResponse;
import com.nagarro.everification.payload.response.EverificationResponse;
import com.nagarro.everification.service.EventSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(originPatterns = "*", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/event-source")
public class EventSourceController {

    @Autowired
    EventSourceService eventSourceService;

    @GetMapping("")
    public Page<EventSource> findAll(Pageable pageable) {
        return eventSourceService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventSource> findById(@PathVariable Long id) throws EverificationNotFoundException {
        return ResponseEntity.ok().body(eventSourceService.findById(id));
    }

    @GetMapping("/getByStatus")
    public ResponseEntity<EverificationResponse> getByStatus(@RequestParam String status) throws EverificationNotFoundException {
        return ResponseEntity.ok().body(eventSourceService.getByStatus(status));
    }

    @GetMapping("/statusCount")
    public ResponseEntity<EverificationDataResponse> statusCount() {
        return ResponseEntity.ok().body(eventSourceService.statusCount());
    }

    @PatchMapping()
    public ResponseEntity<EventSource> updateEventSource(@RequestBody EventSourcePatchRequest eventSourceReq,
                                                         Principal currentPrincipal) throws EverificationNotFoundException {
        System.out.println("currentPrincipal = " + currentPrincipal);
        EventSource body = eventSourceService.patchEventSource(eventSourceReq);
        return ResponseEntity.ok().body(body);
    }

}
