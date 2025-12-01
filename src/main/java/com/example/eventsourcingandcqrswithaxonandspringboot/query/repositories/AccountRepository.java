package com.example.eventsourcingandcqrswithaxonandspringboot.query.repositories;

import com.example.eventsourcingandcqrswithaxonandspringboot.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
