package com.nagarro.everification.controller;

import com.nagarro.everification.exception.EverificationException;
import com.nagarro.everification.exception.EverificationNotFoundException;
import com.nagarro.everification.model.EventSource;
import com.nagarro.everification.payload.request.EventSourcePatchRequest;
import com.nagarro.everification.payload.response.EverificationDataResponse;
import com.nagarro.everification.payload.response.EverificationResponse;
import com.nagarro.everification.service.EventSourceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {EventSourceController.class})
@ExtendWith(SpringExtension.class)
class EventSourceControllerTest {
    @Autowired
    private EventSourceController eventSourceController;

    @MockBean
    private EventSourceService eventSourceService;

    /**
     * Method under test: {@link EventSourceController#findAll(Pageable)}
     */
    @Test
    void testFindAll() throws EverificationException {
        PageImpl<EventSource> pageImpl = new PageImpl<>(new ArrayList<>());
        when(eventSourceService.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        Page<EventSource> actualFindAllResult = eventSourceController.findAll(null);
        assertSame(pageImpl, actualFindAllResult);
        assertTrue(actualFindAllResult.toList()
                .isEmpty());
        verify(eventSourceService).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link EventSourceController#findById(Long)}
     */
    @Test
    void testFindById() throws Exception {
        Date createdOn = mock(Date.class);
        when(createdOn.getTime()).thenReturn(10L);
        Timestamp updatedOn = mock(Timestamp.class);
        when(updatedOn.getTime()).thenReturn(10L);

        EventSource eventSource = new EventSource();
        eventSource.setAccountCurrency("GBP");
        eventSource.setAccountInfoMkr("3");
        eventSource.setAccountName("Dr Jane Doe");
        eventSource.setBeneficiaryName("Beneficiary Name");
        eventSource.setBusinessKey("Business Key");
        eventSource.setComment("Comment");
        eventSource.setContactPerson("Contact Person");
        eventSource.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        eventSource.setCreatedOn(createdOn);
        eventSource.setCustInfoMkr("Cust Info Mkr");
        eventSource.setCustomerCalledOn("Customer Called On");
        eventSource.setDcpReference("Dcp Reference");
        eventSource.setDebitAccountNumber("42");
        eventSource.setExtension("Extension");
        eventSource.setId(1L);
        eventSource.setLockedBy("Locked By");
        eventSource.setOutcome("Outcome");
        eventSource.setPriority("Priority");
        eventSource.setSourceBu("Source Bu");
        eventSource.setStatus("Status");
        eventSource.setTransactionAmount(10.0d);
        eventSource.setTransactionCurrency("GBP");
        eventSource.setUpdatedBy("2020-03-01");
        eventSource.setUpdatedOn(updatedOn);
        when(eventSourceService.findById(Mockito.<Long>any())).thenReturn(eventSource);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/event-source/{id}", 1L);
        MockMvcBuilders.standaloneSetup(eventSourceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"businessKey\":\"Business Key\",\"priority\":\"Priority\",\"sourceBu\":\"Source Bu\",\"dcpReference\":\"Dcp"
                                        + " Reference\",\"accountName\":\"Dr Jane Doe\",\"transactionCurrency\":\"GBP\",\"transactionAmount\":10.0,\"lockedBy"
                                        + "\":\"Locked By\",\"debitAccountNumber\":\"42\",\"accountCurrency\":\"GBP\",\"beneficiaryName\":\"Beneficiary"
                                        + " Name\",\"custInfoMkr\":\"Cust Info Mkr\",\"accountInfoMkr\":\"3\",\"outcome\":\"Outcome\",\"extension\":\"Extension"
                                        + "\",\"contactPerson\":\"Contact Person\",\"customerCalledOn\":\"Customer Called On\",\"comment\":\"Comment\","
                                        + "\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\",\"createdOn\":10,\"updatedOn\":10,\"updatedBy\":\"2020-03-01\","
                                        + "\"status\":\"Status\"}"));
    }

    /**
     * Method under test: {@link EventSourceController#getByStatus(String)}
     */
    @Test
    void testGetByStatus() throws Exception {
        when(eventSourceService.getByStatus(Mockito.<String>any()))
                .thenReturn(new EverificationResponse(new ArrayList<>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/event-source/getByStatus")
                .param("status", "foo");
        MockMvcBuilders.standaloneSetup(eventSourceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"everifications\":[]}"));
    }

    /**
     * Method under test: {@link EventSourceController#statusCount()}
     */
    @Test
    void testStatusCount() throws Exception {
        when(eventSourceService.statusCount()).thenReturn(new EverificationDataResponse(new ArrayList<>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/event-source/statusCount");
        MockMvcBuilders.standaloneSetup(eventSourceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"everificationData\":[]}"));
    }

    /**
     * Method under test: {@link EventSourceController#updateEventSource(EventSourcePatchRequest)}
     */
    @Test
    void testUpdateEventSource() throws EverificationException, EverificationNotFoundException {
        EventSourcePatchRequest request = new EventSourcePatchRequest(1L, "Outcome",
                "Extension", "Contact Person", "Customer Called On",
                "Comment", "Status");
        EventSource eventSource = new EventSource();
        eventSource.setAccountCurrency("GBP");
        eventSource.setAccountInfoMkr("3");
        eventSource.setAccountName("Dr Jane Doe");
        eventSource.setBeneficiaryName("Beneficiary Name");
        eventSource.setBusinessKey("Business Key");
        eventSource.setComment("Comment");
        eventSource.setContactPerson("Contact Person");
        eventSource.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        eventSource.setCreatedOn(mock(Date.class));
        eventSource.setCustInfoMkr("Cust Info Mkr");
        eventSource.setCustomerCalledOn("Customer Called On");
        eventSource.setDcpReference("Dcp Reference");
        eventSource.setDebitAccountNumber("42");
        eventSource.setExtension("Extension");
        eventSource.setId(1L);
        eventSource.setLockedBy("Locked By");
        eventSource.setOutcome("Outcome");
        eventSource.setPriority("Priority");
        eventSource.setSourceBu("Source Bu");
        eventSource.setStatus("Status");
        eventSource.setTransactionAmount(10.0d);
        eventSource.setTransactionCurrency("GBP");
        eventSource.setUpdatedBy("2020-03-01");
        eventSource.setUpdatedOn(mock(Timestamp.class));
        Optional<EventSource> ofResult = Optional.of(eventSource);

        when(eventSourceService.patchEventSource(Mockito.any())).thenReturn(eventSource);
        ResponseEntity<EventSource> actual = eventSourceController.updateEventSource(request);
        assertSame(actual.getBody(), eventSource);

        verify(eventSourceService).patchEventSource(Mockito.any());
    }

    /**
     * Method under test: {@link EventSourceController#assignUser(Long)}
     */
    @Test
    void testAssignUser() throws Exception {
        Date createdOn = mock(Date.class);
        when(createdOn.getTime()).thenReturn(10L);
        Timestamp updatedOn = mock(Timestamp.class);
        when(updatedOn.getTime()).thenReturn(10L);

        EventSource eventSource = new EventSource();
        eventSource.setAccountCurrency("GBP");
        eventSource.setAccountInfoMkr("3");
        eventSource.setAccountName("Dr Jane Doe");
        eventSource.setBeneficiaryName("Beneficiary Name");
        eventSource.setBusinessKey("Business Key");
        eventSource.setComment("Comment");
        eventSource.setContactPerson("Contact Person");
        eventSource.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        eventSource.setCreatedOn(createdOn);
        eventSource.setCustInfoMkr("Cust Info Mkr");
        eventSource.setCustomerCalledOn("Customer Called On");
        eventSource.setDcpReference("Dcp Reference");
        eventSource.setDebitAccountNumber("42");
        eventSource.setExtension("Extension");
        eventSource.setId(1L);
        eventSource.setLockedBy("Locked By");
        eventSource.setOutcome("Outcome");
        eventSource.setPriority("Priority");
        eventSource.setSourceBu("Source Bu");
        eventSource.setStatus("Status");
        eventSource.setTransactionAmount(10.0d);
        eventSource.setTransactionCurrency("GBP");
        eventSource.setUpdatedBy("2020-03-01");
        eventSource.setUpdatedOn(updatedOn);
        when(eventSourceService.assignUser(Mockito.<Long>any())).thenReturn(eventSource);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/v1/event-source/{id}/assign", 1L);
        MockMvcBuilders.standaloneSetup(eventSourceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"businessKey\":\"Business Key\",\"priority\":\"Priority\",\"sourceBu\":\"Source Bu\",\"dcpReference\":\"Dcp"
                                        + " Reference\",\"accountName\":\"Dr Jane Doe\",\"transactionCurrency\":\"GBP\",\"transactionAmount\":10.0,\"lockedBy"
                                        + "\":\"Locked By\",\"debitAccountNumber\":\"42\",\"accountCurrency\":\"GBP\",\"beneficiaryName\":\"Beneficiary"
                                        + " Name\",\"custInfoMkr\":\"Cust Info Mkr\",\"accountInfoMkr\":\"3\",\"outcome\":\"Outcome\",\"extension\":\"Extension"
                                        + "\",\"contactPerson\":\"Contact Person\",\"customerCalledOn\":\"Customer Called On\",\"comment\":\"Comment\","
                                        + "\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\",\"createdOn\":10,\"updatedOn\":10,\"updatedBy\":\"2020-03-01\","
                                        + "\"status\":\"Status\"}"));
    }
}

