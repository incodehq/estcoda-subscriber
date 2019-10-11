package org.incode.estcodasubscriber.spi.rest.impl;

import lombok.Setter;

import java.net.URI;
import java.time.Duration;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.incode.estcodasubscriber.spi.Relay;
import org.incode.estcodasubscriber.spi.RelayStatus;
import org.incode.estcodasubscriber.spi.JaxbService;
import org.incode.estcodasubscriber.spi.rest.config.RelayRestConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

@Component
public class RelayRest implements Relay {

    private final JaxbService jaxbService;
    final ClientBuilder clientBuilder = ClientBuilder.newBuilder();

    @Setter
    String base;

    @Setter
    Integer portOverride;

    @Setter
    String username;

    @Setter
    String password;

    @Setter
    String uriPrefix;

    @Setter
    String uriSuffix;

    @Setter
    Duration connectionTimeout;

    @Setter
    Duration receiveTimeout;

    URI uri;

    public RelayRest(JaxbService jaxbService, RelayRestConfig config) {

        this.jaxbService = jaxbService;

        this.base = config.getBase();
        this.username = config.getUsername();
        this.password = config.getPassword();
        this.uriSuffix = config.getUriSuffix();
        this.connectionTimeout = config.getConnectionTimeout();
        this.receiveTimeout = config.getReceiveTimeout();
    }

    @PostConstruct
    public void init() {
        this.uri = UriBuilder.fromUri(this.base + this.uriSuffix).build();

    }

    @Override
    public RelayStatus handle(InteractionDto interactionDto) {
        final String xml = jaxbService.toXml(interactionDto);
        final JaxRsResponse jaxRsResponse = postXml(uri, xml);
        return jaxRsResponse.toRelayStatus();
    }

    private JaxRsResponse postXml(URI uri, String body) {
        final Client client = this.clientBuilder.build();

        try {
            final WebTarget webTarget = client.target(uri);

            final Invocation.Builder invocationBuilder = webTarget.request();
            if(username != null && password != null) {
                addBasicAuth(username, password, invocationBuilder);
            }

            final Entity<String> entity = Entity.entity(body, MediaType.APPLICATION_XML_TYPE);

            final Invocation invocation = invocationBuilder.buildPost(entity);

            final Response response = invocation.invoke();
            return new JaxRsResponse.Default(response);
        } finally {
            closeQuietly(client);
        }
    }

    private Invocation.Builder addBasicAuth(
            final String username,
            final String password,
            final Invocation.Builder invocationBuilder) {
        return invocationBuilder.header("Authorization", "Basic " + encode(username, password));
    }


    private static MediaType mediaTypeFor(final Class<?> dtoClass, final String reprType) {
        return new MediaType("application", "xml",
                ImmutableMap.of(
                        "profile", "urn:org.restfulobjects:repr-types/"
                                + reprType,
                        "x-ro-domain-type", dtoClass.getName()));
    }

    private static String encode(final String username, final String password) {
        return org.apache.cxf.common.util.Base64Utility.encode(asBytes(username, password));
    }

    private static byte[] asBytes(final String username, final String password) {
        return String.format("%s:%s", username, password).getBytes();
    }

    private static void closeQuietly(final Client client) {
        if (client == null) {
            return;
        }
        try {
            client.close();
        } catch(Exception ex) {
            // ignore so as to avoid overriding any pending exceptions in calling 'finally' block.
        }
    }

}
