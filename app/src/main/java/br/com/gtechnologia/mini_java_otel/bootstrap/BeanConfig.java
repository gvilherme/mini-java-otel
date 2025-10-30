package br.com.gtechnologia.mini_java_otel.bootstrap;

import br.com.gtechnologia.mini_java_otel.adapters.out.payment.PaymentGatewayAdapter;
import br.com.gtechnologia.mini_java_otel.application.CheckoutService;
import br.com.gtechnologia.mini_java_otel.domain.ports.in.CheckoutUseCase;
import br.com.gtechnologia.mini_java_otel.domain.ports.out.PaymentGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public PaymentGateway paymentGateway() {
        return new PaymentGatewayAdapter();
    }


    @Bean
    public CheckoutUseCase checkoutUseCase(PaymentGateway gateway) {
        return new CheckoutService(gateway);
    }
}
