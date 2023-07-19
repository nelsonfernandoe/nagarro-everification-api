package com.nagarro.everification.service;

import com.nagarro.everification.exception.EverificationNotFoundException;
import com.nagarro.everification.model.EventSource;
import com.nagarro.everification.payload.request.EventSourcePatchRequest;
import com.nagarro.everification.payload.response.EverificationResponse;
import com.nagarro.everification.repository.EventSourceRepository;
import com.nagarro.everification.to.EventSourceCountByStatus;
import com.nagarro.everification.to.EverificationTo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {EventSourceServiceImpl.class})
@ExtendWith(SpringExtension.class)
class EventSourceServiceImplTest {
    @MockBean
    private EventSourceRepository eventSourceRepository;

    @Autowired
    private EventSourceServiceImpl eventSourceServiceImpl;

    /**
     * Method under test: {@link EventSourceServiceImpl#findById(Long)}
     */
    @Test
    void testFindById() throws EverificationNotFoundException {
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
        when(eventSourceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(eventSource, eventSourceServiceImpl.findById(1L));
        verify(eventSourceRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link EventSourceServiceImpl#findById(Long)}
     */
    @Test
    void testFindById2() throws EverificationNotFoundException {
        when(eventSourceRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(EverificationNotFoundException.class, () -> eventSourceServiceImpl.findById(1L));
        verify(eventSourceRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link EventSourceServiceImpl#findAll(Pageable)}
     */
    @Test
    void testFindAll() {
        PageImpl<EventSource> pageImpl = new PageImpl<>(new ArrayList<>());
        when(eventSourceRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        Page<EventSource> actualFindAllResult = eventSourceServiceImpl.findAll(null);
        assertSame(pageImpl, actualFindAllResult);
        assertTrue(actualFindAllResult.toList().isEmpty());
        verify(eventSourceRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link EventSourceServiceImpl#getByStatus(String)}
     */
    @Test
    void testGetByStatus() throws EverificationNotFoundException {
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

        ArrayList<EventSource> eventSourceList = new ArrayList<>();
        eventSourceList.add(eventSource);
        Optional<List<EventSource>> ofResult = Optional.of(eventSourceList);
        EverificationResponse response = new EverificationResponse(eventSourceList);
        when(eventSourceRepository.findByStatus(Mockito.<String>any())).thenReturn(ofResult);

        EverificationTo expected = response.everificationEntities.get(0);
        EverificationTo actual = eventSourceServiceImpl.getByStatus("Status").everificationEntities.get(0);
        assertSame(expected.id(), actual.id());
        verify(eventSourceRepository).findByStatus(Mockito.<String>any());

    }

    /**
     * Method under test: {@link EventSourceServiceImpl#getByStatus(String)}
     */
    @Test
    void testGetByStatus2() throws EverificationNotFoundException {
        when(eventSourceRepository.findByStatus(Mockito.<String>any())).thenReturn(Optional.empty());
        assertThrows(EverificationNotFoundException.class, () -> eventSourceServiceImpl.getByStatus("Status"));
        verify(eventSourceRepository).findByStatus(Mockito.<String>any());
    }

    /**
     * Method under test: {@link EventSourceServiceImpl#statusCount()}
     */
    @Test
    void testStatusCount() {
        List<EventSourceCountByStatus> list = new ArrayList<>();
        list.add(new EventSourceCountByStatus("s1", 1L));
        when(eventSourceRepository.getCountByStatus()).thenReturn(list);

        Long actual = eventSourceServiceImpl.statusCount().getEventSourceCountByStatuses().get(0).getCount();
        assertEquals(1L, actual);

        verify(eventSourceRepository).getCountByStatus();
    }

    /**
     * Method under test: {@link EventSourceServiceImpl#assignUser(Long)}
     */
    @Test
    @WithMockUser(username = "foo")
    void testAssignUser() throws EverificationNotFoundException {
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

        when(eventSourceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(eventSourceRepository.save(Mockito.<EventSource>any())).thenReturn(eventSource);
        EventSource actual = eventSourceServiceImpl.assignUser(1L);
        assertEquals("foo", actual.getLockedBy());
        assertEquals("foo", actual.getUpdatedBy());
        verify(eventSourceRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link EventSourceServiceImpl#assignUser(Long)}
     */
    @Test
    void testAssignUser2() throws EverificationNotFoundException {
        when(eventSourceRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(EverificationNotFoundException.class, () -> eventSourceServiceImpl.assignUser(1L));
        verify(eventSourceRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link EventSourceServiceImpl#patchEventSource(EventSourcePatchRequest)}
     */
    @Test
    @WithMockUser(username = "foo")
    void testPatchEventSource() throws EverificationNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getName()" because "auth" is null
        //       at com.nagarro.everification.service.EventSourceServiceImpl.getCurrentUsername(EventSourceServiceImpl.java:95)
        //       at com.nagarro.everification.service.EventSourceServiceImpl.patchEventSource(EventSourceServiceImpl.java:86)
        //   See https://diff.blue/R013 to resolve this issue.

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
        when(eventSourceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(eventSourceRepository.save(Mockito.<EventSource>any())).thenReturn(eventSource);
        EventSourcePatchRequest patchRequest = new EventSourcePatchRequest(1L,
                "Outcome",
                "Extension",
                "Contact Person",
                "Customer Called On",
                "Comment",
                "Status");
        EventSource actual = eventSourceServiceImpl.patchEventSource(patchRequest);
        assertEquals(patchRequest.outcome(), actual.getOutcome());
        assertEquals(patchRequest.extension(), actual.getExtension());
        assertEquals(patchRequest.contactPerson(), actual.getContactPerson());
        assertEquals(patchRequest.customerCalledOn(), actual.getCustomerCalledOn());
        assertEquals(patchRequest.comment(), actual.getComment());
        assertEquals("foo", actual.getUpdatedBy());
        verify(eventSourceRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link EventSourceServiceImpl#patchEventSource(EventSourcePatchRequest)}
     */
    @Test
    void testPatchEventSource2() throws EverificationNotFoundException {
        when(eventSourceRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(EverificationNotFoundException.class, () -> eventSourceServiceImpl.patchEventSource(new EventSourcePatchRequest(1L, "Outcome", "Extension", "Contact Person", "Customer Called On", "Comment", "Status")));
        verify(eventSourceRepository).findById(Mockito.<Long>any());
    }
}

