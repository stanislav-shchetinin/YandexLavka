package ru.yandex.yandexlavka.rate.limiter;

import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RateLimiterAspect {
    private final RateLimiter rateLimiter = RateLimiter.create(10);

    @Around("@annotation(RateLimited)")
    public Object limitRate(ProceedingJoinPoint joinPoint) throws Throwable {
        if (rateLimiter.tryAcquire()) {
            return joinPoint.proceed();
        } else {
            throw new RateLimiterException();
        }
    }
}
