package me.ferdz.kittengartenserver;

import com.google.gson.Gson;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by 1452284 on 2016-09-29.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JSONMapper implements MessageBodyWriter<Object>, MessageBodyReader<Object> {
    private static final String UTF_8 = "UTF-8";
    private static final Gson gson = new Gson();

    public Object readFrom(Class<Object> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        InputStreamReader streamReader = null;

        try {
            streamReader = new InputStreamReader(inputStream, UTF_8);
            Type jsonType;

            if(aClass.equals(type)) {
                jsonType = aClass;
            } else {
                jsonType = type;
            }

            return gson.fromJson(streamReader, jsonType);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            streamReader.close();
        }

        return null;
    }

    public void writeTo(Object o, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, UTF_8);

        try {
            gson.toJson(o, type, writer);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    public long getSize(Object o, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }
}