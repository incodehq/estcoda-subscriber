package org.incode.ixnsubscriber.dispatcher.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.cxfrs")
@Data
public class AppCxfrsConfig {

    /**
     * The scheme part of the URL endpoint, eg "http" within "http://localhost:9090/est2coda/memberInteractionsQueue".
     *
     * The Url is defined in parts to any individual part to be overridden individually.
     */
    private String scheme = "http";

    /**
     * The host name part of the URL endpoint, eg "localhost" within
     * "http://localhost:9090/est2coda/memberInteractionsQueue".
     *
     * The Url is defined in parts to any individual part to be overridden individually.
     */
    private String host = "localhost";

    /**
     * The port part of the URL endpoint, eg "9090" within "http://localhost:9090/est2coda/memberInteractionsQueue".
     *
     * The Url is defined in parts to any individual part to be overridden individually.
     */
    private int port = 8080;

    /**
     * The context path part of the URL endpoint, eg "est2coda" within
     * "http://localhost:9090/est2coda/memberInteractionsQueue".
     *
     * The Url is defined in parts to any individual part to be overridden individually.
     */
    private String contextPath = "est2coda";

    /**
     * The suffix part of the URL endpoint, being everything after the {@link #getContextPath() context path},
     * eg "memberInteractionsQueue" within "http://localhost:9090/est2coda/memberInteractionsQueue".
     *
     * The Url is defined in parts to any individual part to be overridden individually.
     */
    private String uriSuffix = "memberInteractionsQueue";

    /**
     * For testing
     */
    private boolean logOnly = false;
}
