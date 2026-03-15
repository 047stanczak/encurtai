package com.encurtai.validation;

import org.springframework.stereotype.Component;

@Component
public class UrlNormalize {
    public static String normalize(String url){

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        return url;
    }
}
