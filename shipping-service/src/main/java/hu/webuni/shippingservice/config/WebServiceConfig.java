package hu.webuni.shippingservice.config;

import hu.webuni.shippingservice.xmlws.ShippingXmlWs;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServiceConfig {

    @Autowired
    private Bus bus;
    @Autowired
    private ShippingXmlWs shippingXmlWs;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, shippingXmlWs);
        endpoint.publish("/shipping");
        return endpoint;
    }
}
