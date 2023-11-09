package org.example;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private final RequestLine requestLine;
//    private final HttpHeaders httpHeaders;
//    private final Body body;

    public HttpRequest(BufferedReader br) throws IOException {
        this.requestLine = new RequestLine(br.readLine()); /* 첫번째 line 문자열 전달 */
    }

    public boolean isGetRequest() {
        return this.requestLine.isGetRequest();
    }

    public boolean matchPath(String requestPath) {
        return this.requestLine.matchPath(requestPath);
    }

    public QueryStrings getQueryStrings() {
        return this.requestLine.getQueryStrings();
    }

}
