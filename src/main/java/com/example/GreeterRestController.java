package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by gablegar on 1/13/17.
 */

@RestController
@RequestMapping("/api")
@ConfigurationProperties(prefix = "greeting")
public class GreeterRestController {

    private String saying;

    private String backendServiceHost;

    private int backendServicePort;

    private RestTemplate template = new RestTemplate();

    @RequestMapping(method = RequestMethod.GET, value = "/greeting", produces = "text/plain")
    public String hola() throws UnknownHostException { String hostname = null;
        String backendServiceUrl = String.format("http://%s:%d/api/backend?greeting={greeting}",backendServiceHost,backendServicePort);
        System.out.println("Sending to: " + backendServiceUrl);
        BackendDTO backendResponse = template.getForObject(backendServiceUrl, BackendDTO.class, saying);
        return backendResponse.getGreeting() + " at host: " + backendResponse.getIp();
    }

    public String getSaying() {
        return saying;
    }

    public void setSaying(String saying) {
        this.saying = saying;
    }

    public String getBackendServiceHost() {
        return backendServiceHost;
    }

    public void setBackendServiceHost(String backendServiceHost) {
        this.backendServiceHost = backendServiceHost;
    }

    public int getBackendServicePort() {
        return backendServicePort;
    }

    public void setBackendServicePort(int backendServicePort) {
        this.backendServicePort = backendServicePort;
    }
}
