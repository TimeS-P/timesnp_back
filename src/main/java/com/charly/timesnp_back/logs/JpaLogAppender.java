package com.charly.timesnp_back.logs;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.charly.timesnp_back.models.LogEntry;
import com.charly.timesnp_back.services.LogService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Component
public class JpaLogAppender extends AppenderBase<ILoggingEvent> {
    private final LogService logService;

    public JpaLogAppender(LogService logService) {
        this.logService = logService;
    }

    /**
     * @param iLoggingEvent
     */
    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        LogEntry entry = new LogEntry(
                UUID.randomUUID(),
                Instant.ofEpochMilli(iLoggingEvent.getTimeStamp()),
                iLoggingEvent.getLevel().toString(),
                iLoggingEvent.getLoggerName(),
                iLoggingEvent.getFormattedMessage(),
                iLoggingEvent.getMDCPropertyMap().get("user"),
                iLoggingEvent.getMDCPropertyMap().get("ip"),
                iLoggingEvent.getThreadName()
        );
        logService.save(entry);
    }
}
