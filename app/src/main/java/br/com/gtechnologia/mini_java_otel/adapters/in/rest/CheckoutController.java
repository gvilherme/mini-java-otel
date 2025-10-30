package br.com.gtechnologia.mini_java_otel.adapters.in.rest;

import br.com.gtechnologia.mini_java_otel.domain.ports.in.CheckoutUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class CheckoutController {


    private final CheckoutUseCase useCase;


    public CheckoutController(CheckoutUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/checkout")
    public Mono<Map<String, Object>> checkout(@RequestParam(defaultValue = "42") int items) {
        return useCase.checkout(items).map(total -> Map.of("status", "OK", "items", items, "total", total.amount(), "currency", total.currency()));
    }
}
