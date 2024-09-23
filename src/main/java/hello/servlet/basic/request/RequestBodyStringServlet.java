package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name="requestBodyStringServlet",urlPatterns="/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();// 바이트 형식으로 전송 받음
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // string 으로 변환

     /*
    message body에 가능한 값들:

    1. Plain Text:
        - 요청 본문: Hello, World!
        - messageBody에 저장되는 값: "Hello, World!"

    2. JSON:
        - 요청 본문: {"username": "kim", "age": 25}
        - messageBody에 저장되는 값: {"username": "kim", "age": 25}
          (문자열로 저장되지만, 이 자체로는 JSON 데이터입니다)

    3. HTML Form 데이터 (POST 요청):
        - 요청 본문: username=kim&age=25 (기본 폼 전송 방식인 application/x-www-form-urlencoded)
        - messageBody에 저장되는 값: "username=kim&age=25"
 */

        System.out.println("messageBody = " + messageBody);

        response.getWriter().write("ok");


    }
}
