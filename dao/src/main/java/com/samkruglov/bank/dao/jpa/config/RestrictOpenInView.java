package com.samkruglov.bank.dao.jpa.config;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import static java.util.Map.of;

/**
 * @implNote best practice is to declare all relationships as lazy, disable open in view,
 * and prefetch on a per-query basis
 * @see <a href="https://vladmihalcea.com/the-open-session-in-view-anti-pattern/">Open In View is an anti-pattern</a>
 */
@Component
public class RestrictOpenInView implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        event.getEnvironment().getPropertySources()
                .addFirst(new MapPropertySource("jpa", of("spring.jpa.open-in-view", "false")));
    }
}