package org.example;

import java.util.Objects;

/**
 * - RequestLine : (GET /calculate?operand1=11&operator=*&operand2=55 Http/1.1)
 *      - HttpMethod : GET
 *      - path : /calculate?operand1=11&operator=*&operand2=55
 *      - queryString : ?operand1=11&operator=*&operand2=55
 */
public class RequestLine {

    private final String httpMethod;
    private final String urlPath;
    private QueryStrings queryString;

    public RequestLine(String requestLine) {
        String[] tokens = requestLine.split(" ");
        this.httpMethod = tokens[0];
        String[] urlPathTokens = tokens[1].split("\\?");
        this.urlPath = urlPathTokens[0];
        if (urlPathTokens.length == 2) {
            this.queryString = new QueryStrings(urlPathTokens[1]);
        }
    }

    public RequestLine(String httpMethod, String urlPath, String queryString) {
        this.httpMethod = httpMethod;
        this.urlPath = urlPath;
        this.queryString = new QueryStrings(queryString);
    }

    public boolean isGetRequest() {
        return this.httpMethod.equals("GET");
    }

    public boolean matchPath(String requestPath) {
        return this.urlPath.equals(requestPath);
    }

    public QueryStrings getQueryStrings() {
        return this.queryString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(httpMethod, that.httpMethod) && Objects.equals(urlPath, that.urlPath) && Objects.equals(queryString, that.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, urlPath, queryString);
    }
}
