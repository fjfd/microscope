package com.vipshop.microscope.query.jsonp;

import com.vipshop.microscope.query.result.BasicResult;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.util.JSONWrappedObject;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import java.io.IOException;
import java.util.Map;

public class MappingJsonpHttpMessageConverter extends MappingJacksonHttpMessageConverter {

    private String prefixCallback;
    private String suffixCallback;

    @SuppressWarnings("rawtypes")
    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        String callback = null;
        if (object instanceof BasicResult) {
            callback = ((BasicResult) object).getCallback();
            if (SuperString.isBlank(callback)) {
                super.writeInternal(object, outputMessage);
                return;
            }
        } else if (object instanceof Map) {
            callback = (String) ((Map) object).get("callback");
            if (SuperString.isBlank(callback)) {
                super.writeInternal(object, outputMessage);
                return;
            }
        } else {
            super.writeInternal(object, outputMessage);
            return;
        }

        JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
        JsonGenerator generator = super.getObjectMapper().getJsonFactory().createJsonGenerator(outputMessage.getBody(), encoding);
        JSONWrappedObject jsonWrappedObject = new JSONWrappedObject(callback + "(", ");", object);
        super.getObjectMapper().writeValue(generator, jsonWrappedObject);
    }

    public String getPrefixCallback() {
        return prefixCallback;
    }

    public void setPrefixCallback(String prefixCallback) {
        this.prefixCallback = prefixCallback;
    }

    public String getSuffixCallback() {
        return suffixCallback;
    }

    public void setSuffixCallback(String suffixCallback) {
        this.suffixCallback = suffixCallback;
    }

}
