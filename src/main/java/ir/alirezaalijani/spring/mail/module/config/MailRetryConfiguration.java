package ir.alirezaalijani.spring.mail.module.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.retry.RetryOperations;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MailRetryConfiguration {

    @Value("${my-spring.mail.retry.initial-interval-ms:1000}")
    private Long initialIntervalMs;
    @Value("${my-spring.mail.retry.max-interval-ms:10000}")
    private Long maxIntervalMs;
    @Value("${my-spring.mail.retry.multiplier:2.0}")
    private Double multiplier;
    @Value("${my-spring.mail.retry.maxAttempts:3}")
    private Integer maxAttempts;
    @Value("${my-spring.mail.retry.sleep-time-ms:2000}")
    private Long sleepTimeMs;

    @Bean(name = "my-spring-mail-retry")
    public RetryOperations retryOperations(){
        RetryTemplate retryTemplate = new RetryTemplate();
        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
        retryableExceptions.put(MailException.class,true);
        ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
        exponentialBackOffPolicy.setInitialInterval(initialIntervalMs);
        exponentialBackOffPolicy.setMaxInterval(maxIntervalMs);
        exponentialBackOffPolicy.setMultiplier(multiplier);

        retryTemplate.setBackOffPolicy(exponentialBackOffPolicy);

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(maxAttempts,retryableExceptions);

        retryTemplate.setRetryPolicy(simpleRetryPolicy);

        return retryTemplate;
    }
}
