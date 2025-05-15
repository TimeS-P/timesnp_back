package com.charly.timesnp_back.logs;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.filter.ThresholdFilter;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class LogbackConfigurer implements ApplicationListener<ApplicationStartedEvent> {

    private final JpaLogAppender jpaLogAppender;

    public LogbackConfigurer(JpaLogAppender jpaLogAppender) {
        this.jpaLogAppender = jpaLogAppender;
    }

    /**
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

        // Start the JpaLogAppender
        jpaLogAppender.setContext(lc);
        jpaLogAppender.start();

        ThresholdFilter tf = new ThresholdFilter();
        tf.setLevel("INFO"); // Only log INFO and above
        tf.start();

        AsyncAppender asyncAppender = new AsyncAppender();
        asyncAppender.setContext(lc);
        asyncAppender.setName("ASYNC_JPA");
        asyncAppender.addFilter(tf);
        asyncAppender.addAppender(jpaLogAppender);
        asyncAppender.start();

        lc.getLogger(Logger.ROOT_LOGGER_NAME)
                .addAppender(asyncAppender);
    }
}
