package com.vrtools.me.util;

import org.apache.tika.Tika;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

public final class ResponseUtil {

    private static final Tika tika;

    static {
        tika = new Tika();
    }

    private ResponseUtil() {
    }

    public static HttpHeaders prepareResponseHeadersForDownload(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(tika.detect(fileName)));
        headers.setAccessControlExposeHeaders(Collections.singletonList("Content-Disposition"));
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
            .filename(fileName, StandardCharsets.UTF_8)
            .build();
        headers.setContentDisposition(contentDisposition);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return headers;
    }


}
