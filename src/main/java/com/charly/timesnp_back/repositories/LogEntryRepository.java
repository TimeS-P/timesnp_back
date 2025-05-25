package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LogEntryRepository extends JpaRepository<LogEntry, UUID> {
}
