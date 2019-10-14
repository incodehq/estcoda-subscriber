package org.incode.ixnsubscriber.webapp;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.zipkin.ZipkinTracer;
import org.incode.ixnsubscriber.webapp.config.AppConfig;
import org.springframework.stereotype.Component;

@Component
public class TracingRoute extends RouteBuilder {

    final AppConfig appConfig;

    public TracingRoute(final AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public void configure() {

        switch (appConfig.getTracing().getTracingType()) {
            case ZIPKIN:
                ZipkinTracer zipkin = new ZipkinTracer();
                AppConfig.Tracing.Zipkin zipkinConfig = appConfig.getTracing().getZipkin();
                zipkin.setEndpoint(zipkinConfig.getEndpoint());
                zipkin.setServiceName(zipkinConfig.getServiceName());
                zipkin.setRate(zipkinConfig.getRate());
                zipkin.setIncludeMessageBodyStreams(zipkinConfig.isIncludeMessageBodyStreams());

                // add zipkin to CamelContext
                zipkin.init(getContext());
                break;
        }

    }

}
