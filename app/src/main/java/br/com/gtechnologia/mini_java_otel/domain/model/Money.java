package br.com.gtechnologia.mini_java_otel.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public record Money(BigDecimal amount, String currency) {
    public Money {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(currency);
    }
    public static Money of(double value, String currency) {
        return new Money(BigDecimal.valueOf(value), currency);
    }
}