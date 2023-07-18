package com.nagarro.everification.repository;

import com.nagarro.everification.model.EventSource;
import com.nagarro.everification.to.EventSourceCountByStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventSourceRepository extends JpaRepository<EventSource, Long> {

    Optional<List<EventSource>> findByStatus(String status);

    @Query("select new com.nagarro.everification.to.EventSourceCountByStatus(es.status, count(es)) from EventSource es group by es.status")
            public List<EventSourceCountByStatus> getCountByStatus();
}
