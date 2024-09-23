package hello.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name="requestBodyJsonServlet",urlPatterns="/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper=new ObjectMapper();
    // json 형식을 파싱하기 위힌 객체

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream(); // 바이트 스트림으로 데이터를 받는다
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);// 바이트 데이터를 string 으로 변환
        System.out.println("messageBody = " + messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        /*
        messageBody 에 담긴 json 데이터를 HelloData 객체러 변환한다
        objectMapper 는 객체를 json 으로 변환하거나 json 을 객체로 변환하는 경우에 사용
        여기서는 json 데이터를 HelloData.class 로 변환하기 때문에 json 의 구조가 HelloData 클래스의 구조와 일치해야 한다
         */

        System.out.println("helloData.getUsername = " + helloData.getUsername());
        System.out.println("helloData.getAge = " + helloData.getAge());
        // HelloData 객체에 맞게 값을 가지고 옴, json 형식만 파싱 가능

        response.getWriter().write("ok"); // 브라우저 화면에 ok 출력
    }
}
