package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * QueryString 일급 컬렉션 클래스
 */
public class QueryStrings {
    private List<QueryString> queryStrings;

    public QueryStrings(String queryStringLine) {
        String[] queryStreamTokenstokens = queryStringLine.split("&");

        /*Arrays.stream(queryStreamTokenstokens)
                .forEach(queryString -> {
                    String[] keyValueToken = queryString.split("=");
                    if (keyValueToken.length != 2) {
                        throw new IllegalArgumentException("잘못된 QueryString 포맷을 가진 문자열 입니다.");
                    }
                    this.queryStrings.add(new QueryString(keyValueToken[0], keyValueToken[1]));
                });*/

        this.queryStrings = Arrays.stream(queryStreamTokenstokens)
                .map(queryString -> {
                    String[] keyValueToken = queryString.split("=");
                    if (keyValueToken.length != 2) {
                        throw new IllegalArgumentException("잘못된 QueryString 포맷을 가진 문자열 입니다.");
                    }
                    return new QueryString(keyValueToken[0], keyValueToken[1]);
                }).collect(Collectors.toList());
    }

    public String getValue(String key) {
        return queryStrings.stream()
                .filter(queryString -> queryString.exists(key))
//                .map(QueryString::getValue)
                .findAny()
                .orElse(null)
                .getValue();
    }
}
