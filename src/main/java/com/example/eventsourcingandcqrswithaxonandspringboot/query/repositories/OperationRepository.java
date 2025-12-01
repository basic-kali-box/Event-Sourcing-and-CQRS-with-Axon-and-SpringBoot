package com.example.eventsourcingandcqrswithaxonandspringboot.query.repositories;

import com.example.eventsourcingandcqrswithaxonandspringboot.query.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findByAccountId(String accountId);
}
