package ro.swr.dishes.services;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public interface TimestampProvider {

    Instant getTime();
}
