package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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

                /**
                 * step1 - 사용자 요청을 메인 Thread가 처리하도록 한다.
                 */
                try (InputStream is = clientSocket.getInputStream(); OutputStream os = clientSocket.getOutputStream();) {
                    /*
                     * line by line으로 읽기위해 InputStream을 BufferedReader로 변환
                     * inputStream을 UTF_8 형식으로 설정하여 InputStreamReader로 변환
                     * 변환된 InputStreamReader를 다시 BufferedReader로 변환
                     */
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                    DataOutputStream dos = new DataOutputStream(os);

                    String line;
                    while ((line = br.readLine()) != "") {
                        System.out.println("line = " + line);
                        /**
                         * HttpRequest
                         * - RequestLine : (GET /calculate?operand1=11&operator=*&operand2=55 Http/1.1)
                         *      - HttpMethod : GET
                         *      - path : /calculate?operand1=11&operator=*&operand2=55
                         *      - queryString : ?operand1=11&operator=*&operand2=55
                         * - Header
                         * - Body
                         */

                    }

                }
            }
        }
    }
}
