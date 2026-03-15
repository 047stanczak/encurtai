package com.encurtai.validation;

public class UrlNormalize {
    public static String normalize(String url){

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        return url;
    }
}
