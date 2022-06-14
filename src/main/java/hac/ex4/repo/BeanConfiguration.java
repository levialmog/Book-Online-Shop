package hac.ex4.repo;

import hac.ex4.beans.ShoppingCart;
import hac.ex4.listeners.SessionListenerCounter;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.SessionScope;


@Configuration
public class BeanConfiguration {

    @Bean
    public ServletListenerRegistrationBean<SessionListenerCounter> sessionListenerWithMetrics() {
        ServletListenerRegistrationBean<SessionListenerCounter> listenerRegBean = new ServletListenerRegistrationBean<>();

        listenerRegBean.setListener(new SessionListenerCounter());
        return listenerRegBean;
    }

    @Bean
    @SessionScope
    public ShoppingCart sessionBeanExample () {
        return new ShoppingCart();
    }

    @Bean
    @ApplicationScope
    public ShoppingCart applicationBean () {
        return new ShoppingCart();
    }
}
