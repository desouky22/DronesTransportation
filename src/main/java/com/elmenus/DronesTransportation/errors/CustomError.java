package com.elmenus.DronesTransportation.errors;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.Map;

@Component
public class CustomError extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> response = super.getErrorAttributes(webRequest, options);

        response.put("success", false);
        response.put("status", response.get("error"));
        response.put("details", Collections.singletonList(response.get("errors")));
        response.remove("errors");
        response.remove("error");
        response.remove("trace");

        return response;
    }
}
