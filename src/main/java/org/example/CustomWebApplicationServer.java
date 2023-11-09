package org.example;

import org.example.calculator.domain.Calculator;
import org.example.calculator.domain.PositiveNumber;
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
//                clientRequestHandler(clientSocket);

                /**
                 * step2 - 사용자의 요청이 들어올 때마다 Thread를 새로 생성해서 사용자 요청을 처리하도록 한다.
                 */
                new Thread(new ClientRequestHandler(clientSocket)).start();

                /**
                 * 사용자가 요청이 들어올 때 마다 스레드를 새로 생성하기 때문에 문제가 발생한다.
                 * 스레드는 생성될 때 마다 독립적인 스택 메모리 공간을 할당받는다.
                 * 이렇게 메모리를 할당받는 작업은 상당히 비싼 작업이므로 사용자의 요청이 있을 때마다
                 * 스레드를 생성하게 된다면 성능이 매우 저하된다.
                 * 즉, 요청이 몰리게 되면 스레드를 굉장히 많이 생성하게 되므로 메모리 할당작업 처럼 비싼 작업이 많이 발생한다.
                 * 그렇기 때문에 상당히 퍼포먼스 측면에서 좋지 않고, 또한 동시 접속자 수가 많아질 경우에 많은 스레드가 생성된다.
                 * 스레드가 많아지게 되면 CPU 컨텍스트 스위칭 횟수도 증가되고 cpu와 메모리 사용량이 굉장히 증가하게 된다.
                 * 최악의 경우 서버의 리소스가 감당하지 못해서 서버가 다운될 수 있는 가능성 또한 존재한다.
                 * 따라서 사용자의 요청이 들어올 때마다 스레드를 생성하는것이 아니라
                 * 스레드를 이미 고정된 갯수만큼 생성해두고 이를 재활용 하는 스레드풀 개념을 적용하여
                 * 보다 안정적인 서비스가 가능하도록 해야한다.
                 */

            }
        }
    }

    public static void clientRequestHandler(Socket clientSocket) throws IOException {
        try (InputStream is = clientSocket.getInputStream(); OutputStream os = clientSocket.getOutputStream();) {
            /*
             * line by line으로 읽기위해 InputStream을 BufferedReader로 변환
             * inputStream을 UTF_8 형식으로 설정하여 InputStreamReader로 변환
             * 변환된 InputStreamReader를 다시 BufferedReader로 변환
             */
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            DataOutputStream dos = new DataOutputStream(os);

            /*String line;
            while ((line = br.readLine()) != "") {
                System.out.println("line = " + line);
            }*/
            /**
             * HttpRequest
             * - RequestLine : (GET /calculate?operand1=11&operator=*&operand2=55 Http/1.1)
             *      - HttpMethod : GET
             *      - path : /calculate?operand1=11&operator=*&operand2=55
             *      - queryString : ?operand1=11&operator=*&operand2=55
             * - Header
             * - Body
             */
            HttpRequest httpRequest = new HttpRequest(br);
            if (httpRequest.isGetRequest() && httpRequest.matchPath("/calculate")) {
                QueryStrings queryStrings = httpRequest.getQueryStrings();

                int operand1 = Integer.parseInt(queryStrings.getValue("operand1"));
                String operator = queryStrings.getValue("operator");
                int operand2 = Integer.parseInt(queryStrings.getValue("operand2"));

                int result = Calculator.calculate(new PositiveNumber(operand1), operator, new PositiveNumber(operand2));
                byte[] body = String.valueOf(result).getBytes(); // byte 값을 구하기 위해 result값을 String으로 랩핑
                HttpResponse response = new HttpResponse(dos);
                response.response200Header("application/json", body.length);
                response.responseBody(body);
            }
        }
    }
}
