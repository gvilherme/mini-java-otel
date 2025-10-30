package br.com.gtechnologia.mini_java_otel.domain.ports.out;

import br.com.gtechnologia.mini_java_otel.domain.model.Money;
import reactor.core.publisher.Mono;

public interface PaymentGateway {
    enum Status { APPROVED, DECLINED }
    Mono<Status> authorize(Money amount);
}
