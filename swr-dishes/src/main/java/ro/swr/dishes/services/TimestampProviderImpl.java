package ro.swr.dishes.services;

import java.time.Instant;

public class TimestampProviderImpl implements TimestampProvider{
    @Override
    public Instant getTime() {
        return Instant.now();
    }
}
