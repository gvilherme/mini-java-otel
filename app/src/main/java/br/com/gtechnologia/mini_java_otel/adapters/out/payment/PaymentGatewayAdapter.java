package br.com.gtechnologia.mini_java_otel.adapters.out.payment;

import br.com.gtechnologia.mini_java_otel.domain.model.Money;
import br.com.gtechnologia.mini_java_otel.domain.ports.out.PaymentGateway;
import reactor.core.publisher.Mono;

import java.util.Random;

public class PaymentGatewayAdapter implements PaymentGateway {
    private final Random rnd = new Random();


    @Override
    public Mono<PaymentGateway.Status> authorize(Money amount) {
        return Mono.just(rnd.nextInt(6) == 0 ? Status.DECLINED : PaymentGatewayAdapter.Status.APPROVED);
    }
}