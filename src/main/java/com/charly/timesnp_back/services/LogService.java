package com.charly.timesnp_back.services;

import com.charly.timesnp_back.models.LogEntry;
import com.charly.timesnp_back.repositories.LogEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogEntryRepository logEntryRepository;

    @Transactional
    public void save(LogEntry logEntry) {
        logEntryRepository.save(logEntry);
    }

}
