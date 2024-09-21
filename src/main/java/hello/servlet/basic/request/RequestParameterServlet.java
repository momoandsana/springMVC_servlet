package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

/**
 * 1. 파라미터 전송 기능
 * http://localhost:8080/request-param?username=hello&age=20
 * 2. 동일한 파라미터 전송 가능
 * http://localhost:8080/request-param?username=hello&username=kim&age=20
 */
@WebServlet(name="requestParameterServlet",urlPatterns = "/request-param")
public class RequestParameterServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<String> parameterNames = request.getParameterNames();
        // 파라미터 이름들 모두 조회 user,name 에 대한 정보를 얻을 수 있음
//        System.out.println("RequestParameterServlet.service");
//        String username=request.getParameter("username"); // 단일 파라미터 조회
//        Map<String,String[]> parameterMap=request.getParameterMap();
//        // 파라미터를 map 으로 조회
//        String[] usernames=request.getParameterValues("username"); // 복수 파라미터 조회

        System.out.println("[전체 파라미터 조회] - 시작");
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName-> System.out.println(paramName+
                        " = "+request.getParameter(paramName)));
        System.out.println("[전체 파라미터 조회] - 끝");

        System.out.println("[단일 파라미터 조회] - 시작");
        String username = request.getParameter("username");
        System.out.println("[단일 파라미터 조회] - 끝");

        System.out.println("[이름이 같은 복수 파라미터 조회]");
        String[] usernames = request.getParameterValues("username");// 파라미터가 여러 개이기 때문에 리스트  형식으로 나옴
        for(String name:usernames)
        {
            System.out.println("username = "+name);
        }
        response.getWriter().write("ok");
        // 중복된 파라미터가 있는 상황에서 단일 파라미터 조회하면 앞에 있는 파라미터가 나옴
    }

}
