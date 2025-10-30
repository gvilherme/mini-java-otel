package br.com.gtechnologia.mini_java_otel.domain.ports.in;

import br.com.gtechnologia.mini_java_otel.domain.model.Money;
import reactor.core.publisher.Mono;

public interface CheckoutUseCase {
    Mono<Money> checkout(int items);
}
