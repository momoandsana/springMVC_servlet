package hello.servlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="helloServlet",urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        // http 요청이 오면 was(서블릿 컨테이너)가 request response 객체를 만들어서 서블릿한테 전달
        System.out.println("request = " + request);
        System.out.println("response = " + response);
        /*
        ServletRequest-> was 서버가 이런거의 구현체를 만들어(RequestFacade)
        request = org.apache.catalina.connector.RequestFacade@1e48989f
        response = org.apache.catalina.connector.ResponseFacade@7d25dd66
         */

        String username = request.getParameter("username");
        //http://localhost:8080/hello?username=lee , 요청 메시지를 받는 상황
        System.out.println("username = " + username);

        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        // 여기까지는 헤더에 들어가는 내용
        response.getWriter().write("hello "+username);
        // 여기는 body 에 들어가는 내용
    }
}
