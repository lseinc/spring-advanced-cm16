package com.lse.spring.example.ws;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.config.annotation.EnableWs;

import javax.xml.ws.Endpoint;

@EnableWs
@Configuration
public class WSConfig {

    @Bean
    public ServletRegistrationBean servletRegistrationBean(ApplicationContext context) {
        CXFServlet jaxwsServlet = new CXFServlet();
        jaxwsServlet.setBus(springBus());
        return new ServletRegistrationBean(jaxwsServlet, "/jaxws/*");
    }

    @Bean(name = "cxf")
    public SpringBus springBus() {
        SpringBus bus = new SpringBus();
        return bus;
    }

    @Bean
    public AuditWS auditWSService() {
        AuditWSImpl svc = new AuditWSImpl();
        return svc;
    }

    @Bean
    public Endpoint auditWSEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), auditWSService());
        endpoint.publish("/auditWS");
        return endpoint;
    }


    @Bean
    Jaxb2Marshaller auditWSMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.lse.spring.example.ws");
        return marshaller;
    }

    @Bean
    WebServiceTemplate auditWSClient() {
        WebServiceTemplate wsc = new WebServiceTemplate();
        wsc.setMarshaller(auditWSMarshaller());
        wsc.setUnmarshaller(auditWSMarshaller());
        return wsc;
    }
}
