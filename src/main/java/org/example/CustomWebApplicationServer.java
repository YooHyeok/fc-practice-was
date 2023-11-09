package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CustomWebApplicationServer {

    private final int port;

    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }


    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port.", port);
            Socket clientSocket;
            logger.info("[CustomWebApplicationServer] wating for client.");

            /**
             * accept() : 클라이언트 요청 전까지 블로킹 (스레드 대기상태) - 클라이언트 연결 요청시 톹신할 Socket생성후 리턴
             */
            while ((clientSocket = serverSocket.accept()) != null) {
                logger.info("[CustomWebApplicationServer] client connected."); /*연결됨*/
            }
        }
    }
}
