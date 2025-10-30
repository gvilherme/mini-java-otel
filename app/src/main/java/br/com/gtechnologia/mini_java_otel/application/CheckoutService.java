// java
package br.com.gtechnologia.mini_java_otel.application;

import br.com.gtechnologia.mini_java_otel.domain.model.Money;
import br.com.gtechnologia.mini_java_otel.domain.ports.in.CheckoutUseCase;
import br.com.gtechnologia.mini_java_otel.domain.ports.out.PaymentGateway;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Random;

public class CheckoutService implements CheckoutUseCase {

    private final PaymentGateway gateway;
    private final Tracer tracer = GlobalOpenTelemetry.getTracer("br.com.gtechnologia.mini_java_otel.application.CheckoutService");
    private final Random rnd = new Random();

    public CheckoutService(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Mono<Money> checkout(int items) {
        return Mono.defer(() -> {
            Span span = tracer.spanBuilder("checkout.process")
                    .setAttribute("app.items", items)
                    .startSpan();

            var total = Money.of(items * 9.99, "BRL");
            span.setAttribute("app.total", total.amount().doubleValue());

            // run the blocking pricing simulation on boundedElastic
            return Mono.fromCallable(() -> {
                        simulatePricing(items);
                        return total;
                    })
                    .subscribeOn(Schedulers.boundedElastic())
                    .flatMap(t -> gateway.authorize(t)
                            .flatMap(status -> {
                                span.setAttribute("payment.status", status.name());
                                if (status == PaymentGateway.Status.DECLINED) {
                                    span.setStatus(StatusCode.ERROR, "Payment declined");
                                    return Mono.error(new RuntimeException("Payment declined"));
                                }
                                return Mono.just(t);
                            })
                    )
                    .doOnError(span::recordException)
                    .doFinally(signal -> span.end());
        });
    }

    private void simulatePricing(int items) {
        Span span = tracer.spanBuilder("pricing.calculate").startSpan();
        try {
            span.setAttribute("pricing.items", items);
            busyWait(50, 120);
        } finally {
            span.end();
        }
    }

    private void busyWait(int minMs, int maxMs) {
        int delay = minMs + rnd.nextInt(Math.max(1, maxMs - minMs));
        try { Thread.sleep(delay); } catch (InterruptedException ignored) {}
    }
}
