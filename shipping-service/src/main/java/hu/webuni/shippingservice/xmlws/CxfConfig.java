package hu.webuni.shippingservice.xmlws;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CxfConfig {

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        SpringBus bus = new SpringBus();

        Map<String, Object> properties = new HashMap<>();
        properties.put("org.apache.cxf.interceptor.FaultListener", new CustomFaultListener());

        bus.setProperties(properties);
        return bus;
    }
}


