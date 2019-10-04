package org.incode.estcodasubscriber.spi.rest.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.relay-rest")
@Data
public class RelayRestConfig {

    private final RestClient restClient = new RestClient();

    @Data
    public static class RestClient {
        /**
         * The base URL of the webapp that hosts the CODA REST APIs.
         */
        private String base = "http://localhost:8080/coda/";
        /**
         * The user name to invoke the {@link #getBase() REST API}
         */
        private String username = "alex";
        /**
         * The corresponding password for the {@link #getUsername() user}.
         */
        private String password = "pass";
    }

}
