package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="responseJsonServlet",urlPatterns="/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper=new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Content-Type : application/json
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8"); // 한글을 보낼 수 있도록 하기 위해서 인코딩 설정

        HelloData helloData=new HelloData();
        helloData.setUsername("kim");
        helloData.setAge(20);

        //{"username":"kim","age":20}
        String result=objectMapper.writeValueAsString(helloData);// helloData 객체를 json 으로 만들어서 전송
        response.getWriter().write(result);
    }
}
