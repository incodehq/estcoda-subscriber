package org.incode.estcodasubscriber.spi;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;

import com.google.common.collect.MapMaker;
import com.google.common.io.Resources;

@Component
public class JaxbService {
    private static Map<Class<?>, JAXBContext> jaxbContextByClass = (new MapMaker()).concurrencyLevel(10).makeMap();

    public <T> T fromXml(Reader reader, Class<T> dtoClass) {
        Unmarshaller un = null;

        try {
            un = jaxbContextFor(dtoClass).createUnmarshaller();
            return (T) un.unmarshal(reader);
        } catch (JAXBException var4) {
            throw new RuntimeException(var4);
        }
    }

    public <T> T fromXml(Class<?> contextClass, String resourceName, Charset charset, Class<T> dtoClass) throws IOException {
        URL url = Resources.getResource(contextClass, resourceName);
        String s = Resources.toString(url, charset);
        return fromXml(new StringReader(s), dtoClass);
    }

    public <T> String toXml(T dto) {
        CharArrayWriter caw = new CharArrayWriter();
        toXml(dto, caw);
        return caw.toString();
    }

    public <T> void toXml(T dto, Writer writer) {
        Marshaller m = null;

        try {
            Class<?> aClass = dto.getClass();
            m = jaxbContextFor(aClass).createMarshaller();
            m.setProperty("jaxb.formatted.output", Boolean.TRUE);
            m.marshal(dto, writer);
        } catch (JAXBException var4) {
            throw new RuntimeException(var4);
        }
    }

    public <T> JAXBContext jaxbContextFor(Class<T> dtoClass) {
        JAXBContext jaxbContext = (JAXBContext)jaxbContextByClass.get(dtoClass);
        if (jaxbContext == null) {
            try {
                jaxbContext = JAXBContext.newInstance(new Class[]{dtoClass});
            } catch (JAXBException var3) {
                throw new RuntimeException(var3);
            }

            jaxbContextByClass.put(dtoClass, jaxbContext);
        }

        return jaxbContext;
    }
}
