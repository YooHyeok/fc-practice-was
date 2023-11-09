package org.example;

import org.example.calculator.domain.Calculator;
import org.example.calculator.domain.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientRequestHandler implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(ClientRequestHandler.class);
    private final Socket clientSocket;

    public ClientRequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        logger.info("[ClientRequestHandler] new client {} started", Thread.currentThread().getName());
        try {
            CustomWebApplicationServer.clientRequestHandler(this.clientSocket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
