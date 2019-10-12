package org.incode.ixnsubscriber.spi.rest.impl;

import javax.ws.rs.core.Response;

import org.incode.ixnsubscriber.spi.RelayStatus;

public interface JaxRsResponse {

    int getStatus();

    Response.Status.Family getFamily();

    <T> T readEntity(final Class<T> entityType);

    default RelayStatus toRelayStatus() {
        switch (getFamily()) {
            case INFORMATIONAL:
            case SUCCESSFUL:
                return RelayStatus.OK;
            case REDIRECTION:
            case CLIENT_ERROR:
            case SERVER_ERROR:
            case OTHER:
            default:
                return RelayStatus.FAILED;
        }
    }

    class Default implements JaxRsResponse {

        private final Response response;

        public Default(final Response response) {
            this.response = response;
        }

        @Override
        public int getStatus() {
            return response.getStatus();
        }

        @Override
        public Response.Status.Family getFamily() {
            return response.getStatusInfo().getFamily();
        }

        @Override
        public <T> T readEntity(final Class<T> entityType) {
            return response.readEntity(entityType);
        }
    }

    class ForTesting implements JaxRsResponse {

        private final int status;
        private final Response.Status.Family statusFamily;
        private final Object entity;

        public ForTesting(final int status, final Object entity) {
            this.status = status;
            this.statusFamily = Response.Status.Family.familyOf(status);
            this.entity = entity;
        }

        @Override
        public int getStatus() {
            return status;
        }

        @Override
        public Response.Status.Family getFamily() {
            return statusFamily;
        }

        @Override
        public <T> T readEntity(final Class<T> entityType) {
            return (T) entity;
        }
    }

}
