package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private final DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public void response200Header(String contentType, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n"); // Response status line
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8 \r\n"); // Response Header
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n"); // Response Header
            dos.writeBytes("\r\n"); // Response Blank line
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
}
